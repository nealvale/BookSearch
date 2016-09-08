package com.netbrains.console;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.netbrains.model.Book;
import com.netbrains.model.Library;
import com.netbrains.services.BookServiceImpl;

@Component
public class BookSearchConsole implements CommandLineRunner {
	private static String BG_GREEN = "\u001B[42m";
	public static final String RED = "\u001B[31m";
	public static final String BLUE = "\u001B[34m";
	private static String ASCII_ESC = "\033[0m";
	private static Map<Long, Book> bookMap = new HashMap<Long, Book>();
	private static final String[] labels = { "title", "author", "description", "library" };

	@Autowired
	BookServiceImpl bookService;

	private static Scanner stdin = new Scanner(System.in);
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void run(String... args) throws Exception {
		boolean stop = false;
		Map<String, String> values;
		while (!stop) {
			printMenu();
			int userInput = stdin.nextInt();
			switch (userInput) {
			case 1: // View all
				printAllBooks();
				break;
			case 2: // Add a book
				values = new HashMap<String, String>();
				System.out.println(BG_GREEN + RED + "==== Please enter the following information ====\n" + ASCII_ESC);
				for (String label : labels) {
					promptForAdd(stdin, label, values);
				}
				bookService.saveBook(popBook(values));
				printAllBooks();
				break;
			case 3: // Edit existing one
				values = new HashMap<String, String>();
				System.out.println(RED + "==== Please select a book by typing the id ====\n" + ASCII_ESC);
				printAllBooks();
				long id = stdin.nextLong();
				stdin.nextLine();
				Book book = bookMap.get(id);
				for (String label : labels) {
					stdin.reset();
					promptForEdit(stdin, label, values, book);
				}
				book = popBook(book, values);
				bookService.saveBook(book);
				break;
			case 4:
				System.out.println(RED + "==== Please enter the search tag ====" + ASCII_ESC);
				searchForBooks();
				break;
			case 5:
				System.out.println(RED + "==== Please enter the library number  ====" + ASCII_ESC);
				long libid=stdin.nextLong();
				List<Book> books=bookService.findBooksByLibId(libid);
				for(Book b: books){
					System.out.println("------"+b);
				}
				break;
			case 6:
				stop = true;
				break;
			default:
				break;
			}

		}
	}

	private void searchForBooks() {
		String input = new String();
		while (StringUtils.isEmpty(input)) {
			input = stdin.nextLine();
		}
		List<Book> books = bookService.findBooksByPattern("%" + input.trim() + "%");
		for (Book book : books) {
			System.out.println(BG_GREEN + " [" + book.getId() + "] " + ASCII_ESC + book.getTitle() + " : " + book.getDescription());
		}
		if (CollectionUtils.isEmpty(books))
			System.out.println(RED + " [No books found for the search criteria]" + ASCII_ESC);
	}

	private void printAllBooks() {
		bookMap.clear();
		System.out.println(BG_GREEN + RED + "List of all books in the catalog " + ASCII_ESC);
		for (Book book : bookService.findAllBooksInTheCatalog()) {
			bookMap.put(book.getId(), book);
			System.out.println(RED + "\t [" + book.getId() + "] " + ASCII_ESC + RED + book.getTitle() + ASCII_ESC);
		}
	}

	private static void printMenu() {
		System.out.println(BG_GREEN + BLUE + "==== Book Manager ====\n" + ASCII_ESC);
		System.out.println("\t 1) View all books\n");
		System.out.println("\t 2) Add a book\n");
		System.out.println("\t 3) Edit a book\n");
		System.out.println("\t 4) Search for a book\n");
		System.out.println("\t 5) Save and exit\n");
		System.out.println("Choose [1 -5] : ");
	}

	private static Map<String, String> promptForAdd(Scanner stdin, String label, Map<String, String> values) {
		System.out.print("\t" + BG_GREEN + label + " : " + ASCII_ESC + "\n");
		String input = new String();
		while (StringUtils.isEmpty(input)) {
			input = stdin.nextLine();
		}
		values.put(label, input.toString());
		return values;
	}

	private static void promptForEdit(Scanner stdin, String label, Map<String, String> values, Book book) {
		System.out.print(BG_GREEN + "\t" + label + " : " + ASCII_ESC + "[" + book.getValue(label) + "]");
		String input = stdin.nextLine();
		values.put(label,(StringUtils.isEmpty(input)? null:input));
	}

	private  Book popBook(Map<String, String> values) {
		Book book = new Book(values.get(labels[1]), values.get(labels[0]), values.get(labels[2]));
		
		book.setLibrary(bookService.findLibraryById(1));
		return book;
	}

	private  Book popBook(Book book, Map<String, String> values) {
		if (values.get(labels[1]) != null)
			book.setAuthor(values.get(labels[1]));
		if (values.get(labels[0]) != null)
			book.setTitle(values.get(labels[0]));
		if (values.get(labels[2]) != null)
			book.setDescription(values.get(labels[2]));
		if (values.get(labels[3]) != null)
			book.setLibrary(bookService.findLibraryById(Long.parseLong(values.get(labels[3]))));
		return book;
	}
}
