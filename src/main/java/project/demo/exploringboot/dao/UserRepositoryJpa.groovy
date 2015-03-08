package project.demo.exploringboot.dao;

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository;

import project.demo.exploringboot.model.User

@Repository
public interface UserRepositoryJpa extends CrudRepository<User, Long> {
	
	
}