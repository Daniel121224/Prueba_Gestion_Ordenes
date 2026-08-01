package com.example.ordenes.controller;


import com.example.ordenes.dto.LoginRequest;
import com.example.ordenes.dto.LoginResponse;
import com.example.ordenes.security.JwtService;
import com.example.ordenes.security.MockUserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {


    private final MockUserService userService;

    private final JwtService jwtService;



    public AuthController(
            MockUserService userService,
            JwtService jwtService
    ) {

        this.userService = userService;
        this.jwtService = jwtService;

    }



    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request
    ) {


        if(!userService.validateUser(
                request.getUsername(),
                request.getPassword()
        )) {

            return ResponseEntity
                    .status(401)
                    .body("Invalid credentials");
        }


        String token =
                jwtService.generateToken(
                        request.getUsername()
                );


        return ResponseEntity.ok(
                new LoginResponse(token)
        );
    }

    @PostMapping("/logout")
	public ResponseEntity<Void> logout() {
	// JWT es stateless, el cliente debe eliminar el token.
	// Solo respondemos 200 OK.
	return ResponseEntity.ok().build();
	}

}