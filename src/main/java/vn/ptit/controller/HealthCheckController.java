package vn.ptit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class HealthCheckController {
    @GetMapping("/")
    public ResponseEntity<?> healthcheck() {
        return ResponseEntity.ok().build();
    }
}

