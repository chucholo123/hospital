package com.hospital.backendHospital.config;

import com.hospital.backendHospital.auth.repository.Token;
import com.hospital.backendHospital.auth.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final TokenRepository tokenRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(http -> {
                    // AUTH CONTROLLER
                    http.requestMatchers("/auth/**").permitAll();

                    //RECEPTIONIST CONTROLLER
                    http.requestMatchers("/api/v1/receptionists/**").hasRole("ADMIN");

                    // PATIENT CONTROLLER
                    http.requestMatchers(HttpMethod.GET, "/api/v1/patients").hasAnyRole("ADMIN", "DOCTOR");
                    http.requestMatchers(HttpMethod.GET, "/api/v1/patients/search").hasAnyRole("ADMIN", "DOCTOR");
                    http.requestMatchers(HttpMethod.POST, "/api/v1/patients/create").hasRole("PATIENT");
                    http.requestMatchers(HttpMethod.PATCH, "/api/v1/patients/update/me").hasRole("PATIENT");
                    http.requestMatchers(HttpMethod.PATCH, "/api/v1/patients/{id}/deactivate").hasRole("ADMIN");

                    // DOCTOR CONTROLLER
                    http.requestMatchers(HttpMethod.GET, "/api/v1/doctors").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.POST, "/api/v1/doctors").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PATCH, "/api/v1/doctors/{id}/update").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PATCH, "/api/v1/doctors/{id}/deactivate").hasRole("ADMIN");

                    // SPECIALTY CONTROLLER
                    http.requestMatchers(HttpMethod.GET, "/api/v1/specialties").hasAnyRole("ADMIN", "DOCTOR");
                    http.requestMatchers(HttpMethod.GET, "/api/v1/specialties/search").hasAnyRole("ADMIN", "DOCTOR");
                    http.requestMatchers(HttpMethod.POST, "/api/v1/specialties").hasRole("ADMIN");

                    // DOCTOR SCHEDULE CONTROLLER
                    http.requestMatchers(HttpMethod.GET, "/api/v1/doctorSchedules").hasAnyRole("ADMIN", "DOCTOR", "PATIENT");
                    http.requestMatchers(HttpMethod.GET, "/api/v1/doctorSchedules/doctors/{id}").hasAnyRole("ADMIN", "DOCTOR", "PATIENT");
                    http.requestMatchers(HttpMethod.POST, "/api/v1/doctorSchedules").hasRole("ADMIN");

                    // APPOINTMENT CONTROLLER
                    http.requestMatchers(HttpMethod.GET, "/api/v1/appointments/patients/{id}").hasAnyRole("ADMIN", "DOCTOR", "PATIENT");
                    http.requestMatchers(HttpMethod.GET, "/api/v1/appointments/patients/me").hasRole("PATIENT");
                    http.requestMatchers(HttpMethod.GET, "/api/v1/appointments/patients/{id}/active").hasAnyRole("ADMIN", "DOCTOR", "PATIENT");
                    http.requestMatchers(HttpMethod.GET, "/api/v1/appointments/patients/active/me").hasRole("PATIENT");
                    http.requestMatchers(HttpMethod.POST, "/api/v1/appointments").hasRole("PATIENT");
                    http.requestMatchers(HttpMethod.PATCH, "/api/v1/appointments/{id}/complete").hasRole("DOCTOR");
                    http.requestMatchers(HttpMethod.PATCH, "/api/v1/appointments/{id}/cancel").hasRole("PATIENT");

                    // MEDICAL RECORD CONTROLLER
                    http.requestMatchers(HttpMethod.GET, "/api/v1/medicalRecords/patients/{id}").hasAnyRole("DOCTOR", "PATIENT");
                    http.requestMatchers(HttpMethod.GET, "/api/v1/medicalRecords/patients/me").hasAnyRole("DOCTOR", "PATIENT");
                    http.requestMatchers(HttpMethod.GET, "/api/v1/medicalRecords/appointments/{id}").hasAnyRole("DOCTOR", "PATIENT");
                    http.requestMatchers(HttpMethod.POST, "/api/v1/medicalRecords").hasRole("DOCTOR");

                    // MEDICAL SUPPLIES
                    http.requestMatchers("/api/v1/medicalSupplies/**").hasRole("ADMIN");

                    // SUPPLY MOVEMENTS
                    http.requestMatchers("/api/v1/supplyMovements/**").hasRole("ADMIN");

                    http.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();

                    // Cualquier otra solicitud requiere autenticación
                    http.anyRequest().authenticated();
                })

                .logout(logout -> logout.logoutUrl("/auth/logout")
                        .addLogoutHandler(this::logout)
                        .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext())))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of(
                "https://enchanting-growth-production.up.railway.app",
                "http://enchanting-growth-production.up.railway.app",
                "http://localhost:*",
                "https://localhost:*"
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization", "Content-Type", "Content-Disposition"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    private void logout(
            final HttpServletRequest request, final HttpServletResponse response,
            final Authentication authentication
    ) {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        final String jwt = authHeader.substring(7);
        final Token storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
