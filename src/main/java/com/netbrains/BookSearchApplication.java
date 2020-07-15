package com.netbrains;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import com.netbrains.dao.LibraryRepository;
import com.netbrains.model.Library;
import com.netbrains.services.BookServiceImpl;

@SpringBootApplication
public class BookSearchApplication {

	private static final Logger log = LoggerFactory.getLogger(BookSearchApplication.class);
	@Autowired
	LibraryRepository libraryRepository;

	public static void main(String[] args) {
		SpringApplication.run(BookSearchApplication.class, args);
	}

	public static int exit(ApplicationContext context, ExitCodeGenerator... exitCodeGenerators) {
		BookServiceImpl bookService = (BookServiceImpl) context.getBean("BookServiceImpl");

		return 0;
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public org.h2.tools.Server h2WebConsoleServer() throws SQLException {
		return org.h2.tools.Server.createWebServer("-web","-browser", "-webAllowOthers", "-webDaemon", "-webPort", "8083");
	}
	

	//@PostConstruct
	public void populateLibrary(){
		List<Library> listOfLibs=null;
		try {
			listOfLibs = (List<Library>) libraryRepository.findAll();
		} catch (InvalidDataAccessResourceUsageException e) {

		}
		if(CollectionUtils.isEmpty(listOfLibs)){
			Library lib=new Library();
			lib.setLocation("HERITAGE PARK");lib.setDescription("HERITAGE PARK IRVINE");
			libraryRepository.save(lib);
			lib=new Library();
			lib.setLocation("UNIV PARK");lib.setDescription("UNIV PARK IRVINE");
			libraryRepository.save(lib);
		}
	}
}
