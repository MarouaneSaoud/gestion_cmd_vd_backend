package com.veri_delice.gestion_cmd_vd_backend.restController;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind . annotation .GetMapping;
import org.springframework.web.bind. annotation .RequestMapping;
import org.springframework.web.bind. annotation .RestController;

@RestController
@RequestMapping( "/api/" )
public  class  SimpleController {

    @PreAuthorize( "hasRole('ADMIN')" )
    @GetMapping( "/admin" )
    public ResponseEntity<String> helloAdmin(){
        return ResponseEntity.ok( "Bonjour Admin" );
    }

    @PreAuthorize( "hasRole('USER')" )
    @GetMapping( "/user" )
    public ResponseEntity<String> helloUser(){
        return ResponseEntity.ok( "Bonjour utilisateur" );
    }
}