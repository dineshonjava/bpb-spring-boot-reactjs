package com.dineshonjava.prodos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.dineshonjava.prodos.domain.Product;
import com.dineshonjava.prodos.repository.ProductRepository;

@SpringBootApplication
public class ProdosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdosApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner demoData(ProductRepository repo) {
        return args -> { 
            repo.save(new Product(100, "Samsung A6 plus", "Mobile", "Samsung A6 plus is very nice phone with 24mp front camera", "Samsung"));
            repo.save(new Product(101, "iPhone X plus", "Mobile", "iPhone X plus is very nice phone with 24mp front camera", "Apple"));
            repo.save(new Product(102, "Sony Bravia KLV-50W662F 50 Inch Full HD", "Television", "Sony Bravia is full HD tv", "Sony"));
            repo.save(new Product(103, "Canon EOS 1500D Digital SLR Camera", "DSLR Camera", "Best DSLR camera in the market", "Canon"));
            repo.save(new Product(104, "JBL Cinema 510 5.1 with Powered Subwoofer", "Home Theater Speaker", "This sound system is suitable for the Home Theater", "JBL"));
        };
    }
}

