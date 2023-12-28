package com.ReactiveProgramming.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection= "UserInfo")
public class UserInfo {
  
	@JsonProperty("id")
	private int id;
	@JsonProperty("fName")
	private String fName;
	@JsonProperty("lName")
	private String lName;
	@JsonProperty("profession")
	private String profession;
	@JsonProperty("college")
	private String college;
}
