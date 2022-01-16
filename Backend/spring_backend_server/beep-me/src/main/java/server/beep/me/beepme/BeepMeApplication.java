package server.beep.me.beepme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BeepMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeepMeApplication.class, args);
	}

}

