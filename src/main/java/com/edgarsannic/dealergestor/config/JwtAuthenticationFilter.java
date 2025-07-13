package com.edgarsannic.dealergestor.config;

import com.edgarsannic.dealergestor.domain.entity.CompanyUserEntity;
import com.edgarsannic.dealergestor.domain.repository.CompanyUserRepository;
import com.edgarsannic.dealergestor.jwt.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;
    private final CompanyUserRepository companyUserRepository;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   CompanyUserRepository companyUserRepository) {
        this.jwtService = jwtService;
        this.companyUserRepository = companyUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        // 1) Omitir la ruta de login (y cualquier otra pública)
        if ("/auth/login".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        String username;
        try {
            username = jwtService.extractUsername(jwt);
        } catch (JwtException ex) {
            logger.warn("JWT inválido en {}: {}", path, ex.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        // 2) Solo si no hay ya autenticación
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CompanyUserEntity user = companyUserRepository.findByUsername(username);
            if (user != null && jwtService.validateToken(jwt, user)) {
                // 3) Mapear roles a GrantedAuthority
                GrantedAuthority authority =
                        new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                List.of(authority)
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
