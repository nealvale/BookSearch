package com.netbrains;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.netbrains.services.BookServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookSearchApplicationTests {

	@Autowired
	BookServiceImpl service;

	@Test
	public void contextLoads() {
		service.findBooksByPattern("%the%");
	}

}
