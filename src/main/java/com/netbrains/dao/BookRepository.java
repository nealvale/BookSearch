
package com.netbrains.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.netbrains.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

	List<Book> findByTitleContaining(String title);

	List<Book> findByAuthorContaining(String author);

	List<Book> findByDescriptionContaining(String description);

	Book findById(long id);

}
