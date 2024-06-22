package com.javaCursos.Techflix;

import com.javaCursos.Techflix.Model.DatosEpisodio;
import com.javaCursos.Techflix.Model.DatosSerie;
import com.javaCursos.Techflix.Model.DatosTemporada;
import com.javaCursos.Techflix.Principal.EjemploStreams;
import com.javaCursos.Techflix.Principal.Principal;
import com.javaCursos.Techflix.Service.ConsumoAPI;
import com.javaCursos.Techflix.Service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TechflixApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(TechflixApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.muestraElMenu();
		/*EjemploStreams ejemplo = new EjemploStreams();
		ejemplo.muestraEjemplo();
*/
	}
}
