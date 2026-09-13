package co.edu.unbosque.metodologiaespiral;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MetodologiaEspiralApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetodologiaEspiralApplication.class, args);
    }

	@Bean
	public ModelMapper getModelMapper(){
		return new ModelMapper();
	}
}
