package com.myaccount.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.UniqueElements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "UserAccountDetails")
public class UserDetail implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uid; // integer id primary key which will be used as forieng key to relate with user login table
	@Size(max = 30)
	@NotNull
	private String uname;// user name
	@Size(max = 50)
	@Column(unique = true)
	@NotNull
	private String email; // user email
	@Temporal(TemporalType.DATE)
	@Column(name = "dob")
	@NotNull
	private Date dob;// user date of birth
}