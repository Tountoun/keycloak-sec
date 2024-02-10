package com.gofar.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofar.utils.AuthResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@Slf4j
public class AuthService {

    private RestTemplate restTemplate;

    @Value("${authorization.url}")
    private String authUrl;


    @SneakyThrows
    public AuthResponse getAuthorizationAccessToken(String username, String password) {
        AuthResponse authResponse = null;
        MultiValueMap<String, String> requestData = new LinkedMultiValueMap<>();
        requestData.add("grant_type", "password");
        requestData.add("client_id", "keycloak-sec-client");
        requestData.add("username", username);
        requestData.add("password", password);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestData, httpHeaders);
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = null;
        try {
             response = restTemplate.postForEntity(authUrl, httpEntity, String.class);
            HttpStatusCode statusCode = response.getStatusCode();
            if (statusCode.isSameCodeAs(HttpStatus.OK)) {
                authResponse = mapper.readValue(response.getBody(), AuthResponse.class);
            }
        } catch (HttpClientErrorException e) {
            log.debug("Error while retrieving authorization variables {}", e.getMessage());
        }
        return authResponse;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
