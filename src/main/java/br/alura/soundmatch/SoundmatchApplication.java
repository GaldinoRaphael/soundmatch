package br.alura.soundmatch;

import br.alura.soundmatch.principal.Principal;
import br.alura.soundmatch.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SoundmatchApplication implements CommandLineRunner {
	@Autowired
	ArtistaRepository repositorio;

	public static void main(String[] args) {
		SpringApplication.run(SoundmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorio);
		principal.menu();
	}
}
