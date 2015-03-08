package project.demo.exploringboot

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@ConfigurationProperties("application")
class ApplicationProperties {

	String name
	String version

	final Metrics metrics = new Metrics() 
	
	static class Metrics {
		String dbExecutionTimeKey
	}
}