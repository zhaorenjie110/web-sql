package org.websql.db

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@CompileStatic
class DbApplication {

	static void main(String[] args) {
		SpringApplication.run(DbApplication, args)
	}

}
