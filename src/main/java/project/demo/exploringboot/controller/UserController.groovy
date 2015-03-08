package project.demo.exploringboot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.actuate.metrics.CounterService
import org.springframework.boot.actuate.metrics.GaugeService
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.access.annotation.Secured
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import project.demo.exploringboot.dao.UserRepositoryMongo
import project.demo.exploringboot.model.User


@RestController
@RequestMapping("/user")
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableAutoConfiguration
//@EnableMongoRepositories("project.demo.exploringboot")
public class UserController extends WebSecurityConfigurerAdapter {

	@Autowired
	UserRepositoryMongo userRepositoryMongo
	
	@Autowired
	GaugeService gaugeService
	
	@Autowired 
	CounterService counterService
	
	@Value('${application.metrics.dbExecutionTimeKey}') 
	String dbExecutionKey

	@Secured(['ROLE_USER'])
	@RequestMapping(value="/{id}", method = [RequestMethod.GET]) 
	def get(@PathVariable Long id) {
		def start = new Date().time
		counterService.increment id ? "queries.by.id.$id" : "queries.without.id"
		def result = id ? userRepositoryMongo.findOne(id) : userRepositoryMongo.findAll()
		gaugeService.submit dbExecutionKey, new Date().time - start
		result
	}

	@RequestMapping(method = [RequestMethod.POST])
	@Secured(['ROLE_ADMIN'])
	def create(@RequestBody User user) {
		userRepositoryMongo.save user
		user
	}

	@Override
	void configure(AuthenticationManagerBuilder auth) {
		auth.inMemoryAuthentication().withUser "user" password "password" roles "USER" and() withUser "admin" password "password" roles "USER", "ADMIN"
	}

	@Override
	void configure(HttpSecurity http) throws Exception {
		BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint()
		entryPoint.realmName = "Spring Boot"
		http.exceptionHandling().authenticationEntryPoint(entryPoint)
		http.requestMatchers().antMatchers("/**").anyRequest().and().httpBasic().and().anonymous().disable().csrf().disable()
	}

	public static void main(String[] args) {
		SpringApplication.run UserController, args
	}
}