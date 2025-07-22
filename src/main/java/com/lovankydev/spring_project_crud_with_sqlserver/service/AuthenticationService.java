package com.lovankydev.spring_project_crud_with_sqlserver.service;

import aj.org.objectweb.asm.commons.TryCatchBlockSorter;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY ;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUserName(request.getUserName());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String token = generateToken(request.getUserName());
        return  AuthenticationResponse.builder()
                .token(token)
                .authenticated(authenticated)
                .build();
    }

    public IntrospectResponse introspectService(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        if (token == null || token.isEmpty()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        //Create a verifier with the same key used to sign the token
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes()) ;
        //Parse the token
        SignedJWT signedJWT = SignedJWT.parse(token);

        boolean verified = signedJWT.verify(verifier) ;
        // Check if the token is expired
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        return IntrospectResponse.builder()
                .valid(verified && expirationTime.after(new Date()) )
                .build();
    }

    private String generateToken(String userName ) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userName)
                .issuer("lovankydev.com")
                .claim("Welcome", "Hello my murse")
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .issueTime(new Date())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Could not sign the JWT token", e);
            throw new RuntimeException(e);
        }
    }
}
