package com.lovankydev.spring_project_crud_with_sqlserver.service;

import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.AuthenticationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.IntrospectRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.AuthenticationResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.IntrospectResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.entity.User;
import com.lovankydev.spring_project_crud_with_sqlserver.exception.AppException;
import com.lovankydev.spring_project_crud_with_sqlserver.exception.ErrorCode;
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

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

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
        if (token == null || token.isEmpty()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        //Create a verifier with the same key used to sign the token
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        //Parse the token
        SignedJWT signedJWT = SignedJWT.parse(token);

        boolean verified = signedJWT.verify(verifier);
        // Check if the token is expired
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        return IntrospectResponse.builder()
                .valid(verified && expirationTime.after(new Date()))
                .build();
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
                .expirationTime(new Date(
                        // Set the expiration time to 1 hour from now
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
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
}
