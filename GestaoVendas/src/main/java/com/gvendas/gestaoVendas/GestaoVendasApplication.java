package com.gvendas.gestaoVendas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.gvendas.gestaoVendas.entidades"})
@EnableJpaRepositories(basePackages = {"com.gvendas.gestaoVendas.repositorio"})
@ComponentScan(basePackages = {"com.gvendas.gestaoVendas.controlador", "com.gvendas.gestaoVendas.servico", "com.gvendas.gestaoVendas.excecao"})
@SpringBootApplication
public class GestaoVendasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoVendasApplication.class, args);
	}

}
