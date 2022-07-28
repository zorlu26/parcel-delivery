package com.parceldelivery.parcelapigateway.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.parceldelivery.parcelapigateway.models.*;
import com.parceldelivery.parcelapigateway.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static com.parceldelivery.parcelapigateway.constants.Constants.API_GATEWAY_PREDICATE;

@RestController
public class JwtAuthenticationController {

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationController(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * * *** NOTE: ***
     * * Api Gateway should match predicate
     * * path to be discoverable in swagger
     */
    @RequestMapping(value = API_GATEWAY_PREDICATE + "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws URISyntaxException {
        AuthenticationStatus status = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        if (!status.getIsAuthenticated()) {
            List<String> details = new ArrayList<>();
            details.add(status.getMessage());
            ErrorResponseDto error = new ErrorResponseDto(new Date(), HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", details, "uri");
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }

        final String token = jwtTokenUtil.generateToken(authenticationRequest.getUsername(),status.getUserId(),status.getRole());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private AuthenticationStatus authenticate(String username, String password) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        URI url = new URI("http://localhost:8085/login");
        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);
        ResponseEntity<LoginResponse> loginResponse= restTemplate.postForEntity(url, loginRequest, LoginResponse.class);
        AuthenticationStatus status;

        if (loginResponse.getBody()==null) {
            status = new AuthenticationStatus(false, "Invalid Username/Password",null,null);
        }
        else {
            status = new AuthenticationStatus(true, "Authentication Successful",loginResponse.getBody().getId(), loginResponse.getBody().getRole());
        }

        return status;
    }
}
