package br.edu.atitus.orderservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/ping")
public class StatusController {

    @GetMapping
    public String ping() {
        return "pong";
    }

}
