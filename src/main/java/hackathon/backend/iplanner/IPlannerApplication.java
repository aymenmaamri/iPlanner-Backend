package hackathon.backend.iplanner;

import hackathon.backend.iplanner.data.PlanningRooms;
import hackathon.backend.iplanner.data.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan(basePackages = {"hackathon.backend.iplanner.data", "hackathon.backend.iplanner.controller","hackathon.backend.iplanner.service"})
public class IPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IPlannerApplication.class, args);
		System.out.println("I am working");
		PlanningRooms planningRooms = new PlanningRooms();
	}
}
