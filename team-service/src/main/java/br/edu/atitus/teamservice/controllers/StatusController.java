package br.edu.atitus.teamservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class StatusController {

    @GetMapping()
    public ResponseEntity<String> ping() throws Exception {
        return ResponseEntity.ok("Pong");
    }

}
