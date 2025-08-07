package com.lovankydev.spring_project_crud_with_sqlserver.service;

import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.AuthenticationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.IntrospectRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.InvalidatedTokenRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.RefreshRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.AuthenticationResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.IntrospectResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.entity.InvalidatedToken;
import com.lovankydev.spring_project_crud_with_sqlserver.entity.User;
import com.lovankydev.spring_project_crud_with_sqlserver.exception.AppException;
import com.lovankydev.spring_project_crud_with_sqlserver.exception.ErrorCode;
import com.lovankydev.spring_project_crud_with_sqlserver.repository.InvalidatedTokenRepository;
import com.lovankydev.spring_project_crud_with_sqlserver.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    // This method is used to authenticate a user by checking their username and password.
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUserName(request.getUserName());

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(authenticated)
                .build();
    }

    // This method is used to introspect a JWT token to check its validity and expiration.
    public IntrospectResponse introspectService(IntrospectRequest request) throws JOSEException, ParseException {

        String token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (AppException exception) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }


    SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        if (token == null || token.isEmpty()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        //Parse the token
        SignedJWT signedJWT = SignedJWT.parse(token);

        //Create a verifier with the same key used to sign the token
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        boolean verified = signedJWT.verify(verifier);

        // Check if the token is expired
        Date expirationTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getExpirationTime()
                .toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        if (!(verified && expirationTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if (invalidatedTokenRepository
                .existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    // This method generates a JWT token for the user.
    private String generateToken(User user) {

        // Create the JWS header with the algorithm used for signing
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Create the JWT claims set with user information
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("lovankydev.com")
                .claim("scope", buildScope(user))
                .jwtID(UUID.randomUUID().toString())
                .expirationTime(new Date(
                        // Set the expiration time to 1 hour from now
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .issueTime(new Date())
                .build();

        // Create the payload with the JWT claims set
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        // Create the JWS object with the header and payload and sign it with the MACSigner
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Could not sign the JWT token", e);
            throw new RuntimeException(e);
        }
    }

    // This method builds the scope string from the user's roles.
    String buildScope(User user) {
        StringJoiner scope = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                scope.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions()
                            .forEach(permission -> {
                                scope.add(permission.getName());
                            });
                }
            });
        }
        return scope.toString().trim();
    }


    // This is the logout method service
    public void logoutService(InvalidatedTokenRequest request) throws ParseException, JOSEException {

        String token = request.getToken();
        SignedJWT signedJWT = verifyToken(token, true);

        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = new InvalidatedToken(jit, expiryTime);

        invalidatedTokenRepository.save(invalidatedToken);

    }

    // This is the method for refresh token
    public AuthenticationResponse refreshTokenService(RefreshRequest request) throws ParseException, JOSEException {

        String token = request.getToken();

        SignedJWT signedJWT = verifyToken(token, true);

        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        String userName = signedJWT.getJWTClaimsSet().getSubject();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .tokenId(jit)
                .expiryTime(expiryTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);

        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        return AuthenticationResponse.builder()
                .token(generateToken(user))
                .authenticated(true)
                .build();

    }
}
