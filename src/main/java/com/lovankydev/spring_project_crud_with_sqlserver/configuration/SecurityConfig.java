package com.lovankydev.spring_project_crud_with_sqlserver.configuration;

import com.lovankydev.spring_project_crud_with_sqlserver.enums.Roles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;


import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwt.signerKey}")
    private String signerKey;

    private final String[] PUBLIC_ENDPOINTS = {
            "/users",
            "/auth/token",
            "/auth/introspect"
    };
    private final String[] ADMIN_ENDPOINTS = {
            "/users"
    };


    // This method is used to configure the security filter chain for the application.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        // Configure the security filter chain to allow public access to certain endpoints,
        httpSecurity.authorizeHttpRequests(request -> {
            request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll();
            request.requestMatchers(HttpMethod.GET, ADMIN_ENDPOINTS).hasRole(Roles.ADMIN.name());
            request.anyRequest().authenticated() ;
        });

        // Configure the application to use JWT for authentication.
        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
                        .jwtAuthenticationConverter( jwtAuthenticationConverter())

        ));

        // Disable CSRF protection for the application.
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }


    // This method is used to create a JwtDecoder bean that will decode JWT tokens using the specified signing key.
    @Bean
    JwtDecoder jwtDecoder() {

        // Create a SecretKeySpec using the signer key and the HS512 algorithm.
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();

    }

    // This method is used to convert JWT tokens into Spring Security Authentication objects.
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter (){
        // Create a JwtAuthenticationConverter to convert JWT tokens into Authentication objects.
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        // Set the authority prefix for the granted authorities.
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
    

}