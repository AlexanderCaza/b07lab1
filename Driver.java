import java.lang.reflect.Constructor;
import java.io.File;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Driver {
	public static void main(String [] args) {
		// Testing Constructors
		Polynomial p = new Polynomial();
		if (p.coefficients[0] != 0.0) {
			System.out.println("Fail: Empty Constructor coefficient not 0!");
		}
		if (p.exponents[0] != 0) {
			System.out.println("Fail: Empty Constructor exponent not 0!");
		}
		double[] c1 = {-1, 0, -4.2};
		int[] c2 = {0, 2, 3};
		Polynomial p2 = new Polynomial(c1, c2);
		if (p2.coefficients[0] != -1) {
			System.out.println("Fail: Full Constructor coefficient #1 not -1!");
		}
		if (p2.coefficients[1] != 0) {
			System.out.println("Fail: Full Constructor coefficient #2 not 0!");
		}
		if (p2.coefficients[2] != -4.2) {
			System.out.println("Fail: Full Constructor coefficient #3 not -4.2!");
		}
		if (p2.exponents[2] != 3) {
			System.out.println("Fail: Full Constructor exponent #3 not 3!");
		}
		// File Saving and Reading
		
		// File myfile = new File("testing.txt");
		try {
			File myfile = new File("testing.txt");
			if (myfile.createNewFile()) {
				System.out.println("File created: " + myfile.getName());
				p2.saveToFile("testing.txt");
				Polynomial p_read = new Polynomial(myfile);
				System.out.println("Coefficients:");
				System.out.println(p_read.coefficients[0]);
				System.out.println(p_read.coefficients[1]);
				System.out.println(p_read.coefficients[2]);
				System.out.println("Exponents:");
				System.out.println(p_read.exponents[0]);
				System.out.println(p_read.exponents[1]);
				System.out.println(p_read.exponents[2]);
			} else {
				System.out.println("File already exists.");
				System.out.println("File created: " + myfile.getName());
				p2.saveToFile("testing.txt");
				Polynomial p_read = new Polynomial(myfile);
				System.out.println("Coefficients:");
				System.out.println(p_read.coefficients[0]);
				System.out.println(p_read.coefficients[1]);
				System.out.println(p_read.coefficients[2]);
				System.out.println("Exponents:");
				System.out.println(p_read.exponents[0]);
				System.out.println(p_read.exponents[1]);
				System.out.println(p_read.exponents[2]);
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		// Evaluating:
		double[] d23= {-3, 1, 0};
		int[] d24 = {1, 2, 3};
		Polynomial p5 = new Polynomial(d23, d24);
		// -3x1+1x2+0x3
		double result = p5.evaluate(5);
		if (result != 10) {
			System.out.println("Fail: Result not 10");
		}
		// hasRoot:
		System.out.println("hasRoot: 3 = " + p5.hasRoot(3));
		
		// Add
		double[] d43 = {-4, 1, 1};
		int[] d44 = {1, 2, 3};
		Polynomial p55 = new Polynomial(d43, d44);
		double[] d5 = {3, 5, 2};
		int[] d6 = {1, 2, 5};
		Polynomial p6 = new Polynomial(d5, d6);
		// -1x + 6x2 + x3 + 2x5
		Polynomial p4 = p6.add(p55);
		System.out.println("Coefficients:");
		System.out.println(p4.coefficients[0]);
		System.out.println(p4.coefficients[1]);
		System.out.println(p4.coefficients[2]);
		System.out.println(p4.coefficients[3]);
		System.out.println("Exponents:");
		System.out.println(p4.exponents[0]);
		System.out.println(p4.exponents[1]);
		System.out.println(p4.exponents[2]);
		System.out.println(p4.exponents[3]);

		// Multiply
		Polynomial p8 = p6.multiply(p4);
		System.out.println("Coefficients:");
		System.out.println(p8.coefficients[0]);
		System.out.println(p8.coefficients[1]);
		System.out.println(p8.coefficients[2]);
		System.out.println(p8.coefficients[3]);
		System.out.println("Exponents:");
		System.out.println(p8.exponents[0]);
		System.out.println(p8.exponents[1]);
		System.out.println(p8.exponents[2]);
		System.out.println(p8.exponents[3]);
	}
}