package com.gofar.controller;

import com.gofar.service.AuthService;
import com.gofar.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private AuthService authService;

    @GetMapping("access_token")
    public ResponseEntity<Response> getAuthorizationToken(@RequestParam String username, @RequestParam String password) {
        var response = authService.getAuthorizationAccessToken(username, password);
        var message = Objects.nonNull(response) ? "Success" : "Authentication failed";
        var statusCode = Objects.nonNull(response) ? HttpStatus.OK.toString() : HttpStatus.UNAUTHORIZED.toString();
        return ResponseEntity.ok(new Response(statusCode, response, message));
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
}
