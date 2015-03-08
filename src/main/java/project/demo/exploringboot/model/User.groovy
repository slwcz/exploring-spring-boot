package project.demo.exploringboot.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class User {

	@Id
	@GeneratedValue
	Long id;

	String username;
	String firstName;
	String lastName;
	Date createdDate;
	Date lastAccessed;
	Boolean isActive = Boolean.TRUE;
}