package hackathon.backend.iplanner;

import hackathon.backend.iplanner.model.PlanningRooms;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IPlannerApplication.class, args);
		System.out.println("I am working");
		PlanningRooms planningRooms = new PlanningRooms();
	}
}
