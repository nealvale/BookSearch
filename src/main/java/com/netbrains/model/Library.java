
package com.netbrains.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Library implements Serializable {

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	private static final long serialVersionUID = 753534366248232841L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	String location;
	String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "library", orphanRemoval = true)

    private List<Book> books;

	public Library() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



}
