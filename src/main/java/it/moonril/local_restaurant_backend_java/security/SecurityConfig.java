package it.moonril.local_restaurant_backend_java.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin(http -> http.disable())
                .csrf(http -> http.disable())
                .sessionManagement(http -> http.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
//                        .requestMatchers("/users/**").permitAll() I don't think I need it
                            .requestMatchers(HttpMethod.GET, "/dishes/**").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/dishes/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/dishes/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.PATCH, "/dishes/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/dishes/**").hasAuthority("ADMIN")

                            .requestMatchers(HttpMethod.POST, "/bookings/**").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/bookings/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/bookings/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.PATCH, "/bookings/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/bookings/**").hasAuthority("ADMIN")
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
