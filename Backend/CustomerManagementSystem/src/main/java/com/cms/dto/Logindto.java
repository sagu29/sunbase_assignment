package com.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Logindto {
	private String login_id;
	private String password;
}
