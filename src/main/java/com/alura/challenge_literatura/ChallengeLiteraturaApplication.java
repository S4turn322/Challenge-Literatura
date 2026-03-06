package com.alura.challenge_literatura;

import com.alura.challenge_literatura.main.Main;
import com.alura.challenge_literatura.repository.AutorRepository;
import com.alura.challenge_literatura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraturaApplication implements CommandLineRunner {

    @Autowired
    private LibroRepository repository1;
    @Autowired
    private AutorRepository repository2;

    public static void main(String[] args) {
        SpringApplication.run(ChallengeLiteraturaApplication.class, args);
    }

    //@Override
    public void run(String... args) throws Exception {

        Main principal = new Main(repository1, repository2);
        principal.muestraElMenu();


    }

}
