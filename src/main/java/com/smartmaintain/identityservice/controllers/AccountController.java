package com.smartmaintain.identityservice.controllers;

import com.smartmaintain.identityservice.dto.LoginRequest;
import com.smartmaintain.identityservice.dto.LoginResponse;
import com.smartmaintain.identityservice.entities.*;
import com.smartmaintain.identityservice.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtEncoder jwtEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        System.out.println("--- Jarrebna Login b: " + request.email() + " ---");
        try {
            // 1. Authentification
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );


            String role = auth.getAuthorities().iterator().next().getAuthority();


            JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(Instant.now())
                    .subject(auth.getName())
                    .claim("role", role)
                    .expiresAt(Instant.now().plusSeconds(36000))
                    .build();


            String token = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

            return ResponseEntity.ok(new LoginResponse(token, role));

        } catch (Exception e) {
            System.out.println("--- failed-LOGIN: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Les identifications sont erronées");
        }
    }

    @PostMapping("/admin")
    public Admin createAdmin(@RequestBody Admin admin) { return accountService.saveAdmin(admin); }

    @PostMapping("/client")
    public Client createClient(@RequestBody Client client) { return accountService.saveClient(client); }

    @PostMapping("/manager")
    public Manager createManager(@RequestBody Manager manager) { return accountService.saveManager(manager); }

    @PostMapping("/ingenieur")
    public Ingenieur createIngenieur(@RequestBody Ingenieur ingenieur) { return accountService.saveIngenieur(ingenieur); }

    @PostMapping("/operateur")
    public Operateur createOperateur(@RequestBody Operateur operateur) { return accountService.saveOperateur(operateur); }
}