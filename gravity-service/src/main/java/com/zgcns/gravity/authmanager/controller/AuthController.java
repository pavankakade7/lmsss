package com.zgcns.gravity.authmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zgcns.gravity.authmanager.service.CustomUserDetailsService;
import com.zgcns.gravity.authmanager.utilities.JwtUtil;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	/*
	 * @PostMapping("/login") public ResponseEntity<?>
	 * createAuthenticationToken(@RequestBody AuthRequest authRequest) { try {
	 * Authentication authentication = authenticationManager.authenticate( new
	 * UsernamePasswordAuthenticationToken(authRequest.getUsername(),
	 * authRequest.getPassword()));
	 * 
	 * final UserDetails userDetails =
	 * userDetailsService.loadUserByUsername(authRequest.getUsername()); final
	 * Set<String> roles = userDetails.getAuthorities().stream()
	 * .map(Object::toString) .collect(Collectors.toSet());
	 * 
	 * final String jwt = jwtUtil.generateToken(userDetails, roles);
	 * 
	 * return ResponseEntity.ok(new AuthResponse(jwt)); } catch
	 * (BadCredentialsException e) { return
	 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).
	 * body("Invalid username or password"); } catch (Exception e) { return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	 * .body("An error occurred during authentication"); } } }
	 */
	@GetMapping("/msg")
	public String getTestMsg() {
		return "docker is working fine";
	}

	// @PostMapping("/login")
	// public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
	// 	try {
	// 		Authentication authentication = authenticationManager.authenticate(
	// 				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

	// 		final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
	// 		final Set<String> roles = userDetails.getAuthorities().stream().map(Object::toString)
	// 				.collect(Collectors.toSet());

	// 		final String jwt = jwtUtil.generateToken(userDetails, roles);

	// 		return ResponseEntity.ok(new AuthResponse(jwt));
	// 	} catch (BadCredentialsException e) {
	// 		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	// 	} catch (Exception e) {
	// 		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	// 				.body("An error occurred during authentication");
	// 	}
	// }


	//////// Pavan /////////

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

			final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
			final Set<String> roles = userDetails.getAuthorities().stream().map(Object::toString)
					.collect(Collectors.toSet());

			final String jwt = jwtUtil.generateToken(userDetails, roles);

			return ResponseEntity.ok(new AuthResponse(jwt));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred during authentication");
		}
	}
}

class AuthRequest {
	private String username;
	private String password;

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}

class AuthResponse {
	private String jwt;

	public AuthResponse(String jwt) {
		this.setJwt(jwt);
	}

	/**
	 * @return the jwt
	 */
	public String getJwt() {
		return jwt;
	}

	/**
	 * @param jwt the jwt to set
	 */
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
}