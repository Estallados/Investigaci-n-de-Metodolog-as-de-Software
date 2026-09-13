package co.edu.unbosque.metodologiaespiral.configuration;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.unbosque.metodologiaespiral.entity.Usuario;
import co.edu.unbosque.metodologiaespiral.repository.UsuarioRepository;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Autowired
	private UsuarioRepository userRepo;

	@Bean
	CommandLineRunner initDatabase() {
		return args -> {

			Optional<Usuario> found = userRepo.findByCorreo(("admin"));

			if (found.isPresent()) {

				log.info("Admin already exists, skipping admin creating...");

			} else {

				Usuario usuario = new Usuario(("admin"), "admi123");

				userRepo.save(usuario);

				log.info("Preloading admin user");
			}
		};
	}

	public LoadDatabase() {
	}
}
