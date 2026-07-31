package py.una.pol.PoliBank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private PoliBankUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilitamos CSRF porque la API es stateless (sin sesión de formularios)
            .csrf(csrf -> csrf.disable())
            // Habilitar CORS (usa la configuración de CorsConfig)
            .cors(Customizer.withDefaults())
            // Sin sesión HTTP: cada request lleva credenciales vía Basic Auth
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                // El registro es público: el usuario todavía no tiene credenciales
                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                // Preflight CORS (OPTIONS) debe pasar sin autenticación
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // Todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )
            // Autenticación HTTP Basic (usuario:contraseña en cabecera Authorization)
            .httpBasic(Customizer.withDefaults())
            .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
