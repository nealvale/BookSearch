package com.netbrains;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.netbrains.services.BookServiceImpl;

@SpringBootApplication
public class BookSearchApplication {

	private static final Logger log = LoggerFactory.getLogger(BookSearchApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookSearchApplication.class, args);
	}

	public static int exit(ApplicationContext context, ExitCodeGenerator... exitCodeGenerators) {
		BookServiceImpl bookService = (BookServiceImpl) context.getBean("BookServiceImpl");

		return 0;
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public org.h2.tools.Server h2WebConsoleServer() throws SQLException {
		return org.h2.tools.Server.createWebServer("-web","-browser", "-webAllowOthers", "-webDaemon", "-webPort", "8082");
	}
	

}
