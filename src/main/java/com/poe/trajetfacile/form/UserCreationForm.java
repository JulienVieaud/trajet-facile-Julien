package com.poe.trajetfacile.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Data // lombok warning
public class UserCreationForm {
	
	@NotNull
	@Size(min=1)
	private String login;
	
	@NotNull
	@Size(min=4)
	private String password;

	private Date birthDate;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
}
