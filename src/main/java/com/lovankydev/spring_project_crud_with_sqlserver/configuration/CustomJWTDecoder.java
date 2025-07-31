package com.lovankydev.spring_project_crud_with_sqlserver.configuration;

import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.IntrospectRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.IntrospectResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.websocket.Decoder;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Component
public class CustomJWTDecoder implements JwtDecoder {

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Autowired
    AuthenticationService authenticationService;
    NimbusJwtDecoder nimbusJwtDecoder = null ;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            IntrospectResponse response = authenticationService.introspectService(new IntrospectRequest(token));
            if(!response.isValid()){
               throw new JwtException("Token is invalid.");
            }
        }catch (JwtException | ParseException e){
            throw new JwtException(e.getMessage());
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        if(Objects.isNull(nimbusJwtDecoder)){
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
            nimbusJwtDecoder =NimbusJwtDecoder
                    .withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        System.out.println(nimbusJwtDecoder);

        return nimbusJwtDecoder.decode(token);
    }

}
