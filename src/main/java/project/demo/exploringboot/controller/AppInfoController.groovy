package project.demo.exploringboot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import project.demo.exploringboot.ApplicationProperties

@RestController
@Configuration
@RequestMapping("/appinfo")
@EnableAutoConfiguration
class AppInfoController {

	@Autowired
	ApplicationProperties applicationProperties

	@RequestMapping(method=[RequestMethod.GET]) def get() {
		[
			name: applicationProperties.name,
			version: applicationProperties.version
		]
	}

	@Bean ApplicationProperties applicationProperties() {
		new ApplicationProperties()
	}
}