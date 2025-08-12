package com.infotek.shopfloor_live.config;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

  @Value("${app.api.key:dev-key-123}")
  private String apiKey;

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    var config = new CorsConfiguration();
    config.setAllowCredentials(true);
    // Si solo trabajas en local:
    config.setAllowedOrigins(List.of("http://localhost:4200"));
    // Si en el futuro necesitas comodines, usa allowedOriginPatterns:
    // config.setAllowedOriginPatterns(List.of("http://localhost:*"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    List<String> publicPaths = List.of("/h2-console", "/ws", "/api/kpi"); // Rutas públicas
    return http
    	.cors(cors -> {})     // ← aplica el CORS anterior
        .csrf(csrf -> csrf.disable())
        .headers(h -> h.frameOptions(f -> f.disable())) // para H2 console
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/h2-console/**").permitAll()
            .requestMatchers("/ws/**").permitAll()
            .requestMatchers("/api/kpi/**").permitAll() // Permite el acceso al endpoint de KPI
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers("/","/index.html","/assets/**").permitAll()
            .anyRequest().authenticated()
        )
        // Pasa la lista de rutas públicas al filtro
        .addFilterBefore(new ApiKeyFilter(apiKey, publicPaths), UsernamePasswordAuthenticationFilter.class)
        .build();
  }
  
  static class ApiKeyFilter extends OncePerRequestFilter {
    private final String expected;
    private final List<String> publicPaths;

    ApiKeyFilter(String expected, List<String> publicPaths) {
      this.expected = expected;
      this.publicPaths = publicPaths;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
        throws ServletException, IOException {
      String path = req.getRequestURI();

      // Permite las peticiones OPTIONS para el pre-vuelo de CORS
      if (HttpMethod.OPTIONS.matches(req.getMethod())) {
        chain.doFilter(req, res);
        return;
      }
      
      // Si la ruta es pública, salta la validación de la API key
      if (publicPaths.stream().anyMatch(path::startsWith)) {
        chain.doFilter(req, res);
        return;
      }

      String key = req.getHeader("X-API-KEY");

      if (key != null && key.trim().equals(expected.trim())) {
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
              "api-user",
              null,
              Collections.emptyList()
          );
          SecurityContextHolder.getContext().setAuthentication(authentication);
      } else {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType("application/json");
        res.getWriter().write("{\"title\":\"Unauthorized\",\"detail\":\"Missing or invalid API key\"}");
        return;
      }
      
      chain.doFilter(req, res);
    }
  }
}
