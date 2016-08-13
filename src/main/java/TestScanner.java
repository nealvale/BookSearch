import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class TestScanner {
	private static String BG_GREEN = "\u001B[42m";
	public static final String RED = "\u001B[31m";
	public static final String BLUE = "\u001B[34m";
	private static String ASCII_ESC = "\033[0m";
	public static void main(String... args) {
		Scanner sc = new Scanner(System.in);
		boolean stop = false;
		try{
		while (!stop) {

			System.out.println(RED + "==== Please select a book by typing the id ====\n" + ASCII_ESC);

			long id = sc.nextLong();
			sc.nextLine();
			System.out.println(RED + "==== Please enter the id of the field you would like to edit ====  "+id + ASCII_ESC);
			System.out.print(BG_GREEN + "\t" + "Title" + " : " + ASCII_ESC + "[ Test ]\n");
			
			String input = sc.nextLine();
			if(StringUtils.isNotEmpty(input)) System.out.println(" Printing stuff ------ " + input);
		}
		}finally{
			sc.close();
		}
	}

}
