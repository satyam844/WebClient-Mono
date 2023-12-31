package com.ReactiveProgramming.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection ="User")
public class User {
	
	@Id
	@JsonProperty("id")
	private int id;
	@JsonProperty("fName")
	private String fName;
	@JsonProperty("lName")
	private String lName;
	@JsonProperty("age")
	private int age;
	@JsonProperty("location")
	private String location;
}
