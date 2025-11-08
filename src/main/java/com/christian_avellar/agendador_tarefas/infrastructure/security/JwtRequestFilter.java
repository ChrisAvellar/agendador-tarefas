package com.christian_avellar.agendador_tarefas.infrastructure.security;



import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            final String token = authorizationHeader.substring(7);
            final String username = jwtUtil.extrairEmailToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Carrega o usuário do microserviço de usuários (opcional)
                UserDetails userDetails = userDetailsService.carregaDadosUsuario(username, authorizationHeader);

                if (jwtUtil.validateToken(token, username)) {

                    // 👇 extrai roles do token
                    Claims claims = jwtUtil.extractClaims(token);
                    List<String> roles = claims.get("roles", List.class);

                    if (roles == null || roles.isEmpty()) {
                        roles = List.of("ROLE_USER"); // default
                    }

                    var authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    System.out.println("✅ Token válido para: " + username);
                    System.out.println("Authorities: " + authorities);
                }
            }
        }

        chain.doFilter(request, response);
    }
}
