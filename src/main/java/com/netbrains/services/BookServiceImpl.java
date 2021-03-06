
package com.netbrains.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.netbrains.dao.BookRepository;
import com.netbrains.dao.LibraryRepository;
import com.netbrains.model.Book;
import com.netbrains.model.Library;


@Service
public class BookServiceImpl {

	@Autowired
	BookRepository bookRepository;
	@Autowired
	LibraryRepository libRepository;
	
	@Autowired
	EntityManager em;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}

	public List<Book> findAllBooksInTheCatalog() {
		log.info("Finding All Books in the catalog :");

		return (List<Book>) bookRepository.findAll();
	}

	public List<Book> findBooksByAuthor(String author) {
		log.info("Finding All Books in the catalog by author:");

		return bookRepository.findByAuthorContaining(author);
	}

	public List<Book> findAllBooksByDescription(String description) {
		log.info("Finding All Books in the catalog by description:");

		return bookRepository.findByDescriptionContaining(description);
	}

	public List<Book> findAllBooksByTitle(String title) {
		log.info("Finding All Books in the catalog by title:");

		return bookRepository.findByTitleContaining(title);
	}

	public Book findBooksById(long id) {
		log.info("Finding All Books in the catalog :");

		return bookRepository.findById(id);
	}

	public List<Book> findBooksByPattern(String input) {
		Query qry = em.createNamedQuery("findByAnyTag").setParameter("search", input);
		return qry.getResultList();
	}
	
	public Library findLibraryById(long id){
		return libRepository.findById(id);
	}
	@Transactional(value=TxType.REQUIRED)
	public List<Book> findBooksByLibId(long id){
		Library lib=libRepository.findById(id);
		Hibernate.initialize(lib.getBooks());
		return lib.getBooks();
	}
}