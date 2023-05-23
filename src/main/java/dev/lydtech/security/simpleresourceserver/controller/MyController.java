package dev.lydtech.security.simpleresourceserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class MyController {
    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint(Principal principal) {
        log.info("/public called");

        String message = getPrincipalName(principal);
        return ResponseEntity.ok("Hello from Public endpoint - " + message);
    }

    @GetMapping("/user")
    public ResponseEntity<String> userEndpoint(Principal principal) {
        log.info("/user called");

        String message = getPrincipalName(principal);
        return ResponseEntity.ok("Hello from User endpoint - " + message);
    }

    @GetMapping("/admin")
    public ResponseEntity<String> adminEndpoint(Principal principal) {
        log.info("/admin called");

        String message = getPrincipalName(principal);
        return ResponseEntity.ok("Hello from Admin endpoint - " + message);
    }

    private static String getPrincipalName(Principal principal) {
        return Optional.ofNullable(principal)
                .map(Principal::getName)
                .orElse("Not available");
    }
}
