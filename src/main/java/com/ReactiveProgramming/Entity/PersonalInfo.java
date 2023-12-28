package com.ReactiveProgramming.Entity;

import org.springframework.data.annotation.Id;
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
@Document(collection = "PersonalInfo")
public class PersonalInfo {
	
	
	@Id
	@JsonProperty("id")
	private int id;
	@JsonProperty("profession")
	private String profession;
	@JsonProperty("graduatedIn")
	private String graduatedIn;
	@JsonProperty("college")
	private String college;
	
	
	
}
