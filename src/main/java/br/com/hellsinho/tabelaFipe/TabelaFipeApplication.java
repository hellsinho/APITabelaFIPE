package br.com.hellsinho.tabelaFipe;

import br.com.hellsinho.tabelaFipe.Principal.Principal;
import br.com.hellsinho.tabelaFipe.service.ConsumeApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TabelaFipeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TabelaFipeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal menu = new Principal();

		menu.exibeMenu();
	}
}
