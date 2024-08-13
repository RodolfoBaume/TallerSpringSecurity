package com.tallerMecanico.dto;

public class PasswordDto {
	private String currentPassword;
	private String newPassword;
	
	public PasswordDto() {
		super();
	}


	public PasswordDto(String currentPassword, String newPassword) {
		super();
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
	}


	public String getCurrentPassword() {
		return currentPassword;
	}


	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}


	public String getNewPassword() {
		return newPassword;
	}


	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}





	
}
