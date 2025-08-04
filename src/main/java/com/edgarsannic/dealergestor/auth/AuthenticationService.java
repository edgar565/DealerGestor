package com.edgarsannic.dealergestor.auth;

import com.edgarsannic.dealergestor.auth.dto.AuthRequest;
import com.edgarsannic.dealergestor.auth.dto.AuthResponse;
import com.edgarsannic.dealergestor.auth.dto.RegisterRequest;
import com.edgarsannic.dealergestor.domain.entity.CompanyEntity;
import com.edgarsannic.dealergestor.domain.entity.CompanyUserEntity;
import com.edgarsannic.dealergestor.domain.repository.CompanyRepository;
import com.edgarsannic.dealergestor.domain.repository.CompanyUserRepository;
import com.edgarsannic.dealergestor.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final CompanyUserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            CompanyUserRepository userRepository,
            CompanyRepository companyRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        CompanyEntity company = companyRepository.findByNameCompany(request.getCompanyName())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        CompanyUserEntity user = new CompanyUserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        user.setRole(request.getRole());
        user.setCompanyEntity(company);

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse authenticate(AuthRequest request, String companyName) {
        CompanyEntity company = companyRepository.findByNameCompany(companyName)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        CompanyUserEntity user = userRepository.findByUsernameAndCompanyEntity(request.getUsername(), company)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado en esta empresa"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
