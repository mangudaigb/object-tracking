package com.gb.trace.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@RestController
@RequestMapping("/api/flags")
public class LocationController {

    @GetMapping
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaderNames();

        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            String value = request.getHeader(header);
            System.out.println("Name: " + header + "\t Value: " + value);
        }

        return ResponseEntity.ok().build();
    }
}
