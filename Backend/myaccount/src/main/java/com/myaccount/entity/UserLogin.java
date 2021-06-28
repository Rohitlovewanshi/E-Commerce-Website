package com.myaccount.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UserLoginCredential")
public class UserLogin implements Serializable {

	@Id
	@Size(max = 40)
	@NotNull
	@Column(unique = true)
	private String username;// user unique username for login process

	@NotNull
	private String password;// user unique password for login process which will be encrypt using security boot encoder

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "u_id", referencedColumnName = "uid")
	private UserDetail userdetails;// one to one user detail mapping for entering more detail about user
}