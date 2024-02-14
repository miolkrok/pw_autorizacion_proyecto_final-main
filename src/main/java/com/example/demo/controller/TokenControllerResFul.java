package com.example.demo.controller;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.security.JwtUtils;
import com.example.demo.service.to.UsuarioTO;

@RestController
@RequestMapping("/tokens")
@CrossOrigin
public class TokenControllerResFul {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping(path = "/obtener", consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<String> getToken(@RequestBody UsuarioTO usuarioTO) {
		try {
			this.authenticated(usuarioTO.getUsername(), usuarioTO.getPassword());
			String token = this.jwtUtils.generateJwtToken(usuarioTO.getUsername());
			return ResponseEntity.ok(token);
		} catch (NoResultException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la entidad en la base de datos.");
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error de autenticación: " + e.getMessage());
		}
	}

	private void authenticated(String username, String password) {
		Authentication authentication = this.authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
