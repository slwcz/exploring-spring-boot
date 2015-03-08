package project.demo.exploringboot.dao

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import project.demo.exploringboot.model.User

@Repository
public interface UserRepositoryMongo extends MongoRepository<User, Long> { 
	
	
}