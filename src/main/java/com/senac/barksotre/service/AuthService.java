package com.senac.barksotre.service;

import com.senac.barksotre.dto.auth.LoginRequestDTO;
import com.senac.barksotre.dto.auth.TokenResponseDTO;
import com.senac.barksotre.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	public TokenResponseDTO login(LoginRequestDTO dto) {
		var authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(dto.email().toLowerCase(), dto.senha()));
		String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());
		return new TokenResponseDTO(token, "Bearer", jwtService.getExpirationMs());
	}
}
