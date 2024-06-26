package com.tallerMecanico.dto;

// Devuelve token y tipo
public class AuthRespuestaDto {
	private String accessToken;
	private String tokenType = "Bearer ";
	
	public AuthRespuestaDto(String accessToken) {
		this.accessToken = accessToken;
	}

	
	
	public AuthRespuestaDto() {
	}



	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
	
}
