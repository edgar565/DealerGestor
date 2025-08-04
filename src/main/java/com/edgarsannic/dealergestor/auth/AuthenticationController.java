package com.edgarsannic.dealergestor.auth;

import com.edgarsannic.dealergestor.auth.dto.AuthRequest;
import com.edgarsannic.dealergestor.auth.dto.AuthResponse;
import com.edgarsannic.dealergestor.auth.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request, HttpServletRequest servletRequest) {
        try {
            String subdomain = extractSubdomain(servletRequest.getServerName());
            AuthResponse response = authService.authenticate(request, subdomain);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error durante la autenticación");
        }
    }

    private String extractSubdomain(String serverName) {
        // Ej: empresa1.dealergestor.com -> empresa1
        String[] parts = serverName.split("\\.");
        if (parts.length < 3) {
            throw new RuntimeException("Dominio inválido. Se esperaba un subdominio.");
        }
        return parts[0];
    }
}
