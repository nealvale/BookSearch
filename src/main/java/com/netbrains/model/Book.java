
package com.netbrains.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries(value = {
		@NamedNativeQuery(name = "findByAnyTag", query = "Select * from Book where concat(author, title, description) like :search", resultClass = Book.class) })
public class Book implements Serializable {

	private static final long serialVersionUID = 753534366248232841L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	String author;
	String title;
	String description;

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Book(String author, String title, String description) {
		super();
		this.author = author;
		this.title = title;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getValue(String label) {
		if (label.equalsIgnoreCase("title"))
			return title;
		if (label.equalsIgnoreCase("author"))
			return author;
		if (label.equalsIgnoreCase("description"))
			return description;
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Book [id=").append(id).append(", ");
		if (author != null)
			builder.append("author=").append(author).append(", ");
		if (title != null)
			builder.append("title=").append(title).append(", ");
		if (description != null)
			builder.append("description=").append(description);
		builder.append("]");
		return builder.toString();
	}

}
