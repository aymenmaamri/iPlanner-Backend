package hackathon.backend.iplanner;

import hackathon.backend.iplanner.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class IPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IPlannerApplication.class, args);
		System.out.println("I am working");
	}
}
