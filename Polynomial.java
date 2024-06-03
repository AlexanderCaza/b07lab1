import java.lang.reflect.Constructor;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
	//fields
	double[] coefficients;
	int[] exponents;
	
	//methods
	public Polynomial() {
		this.coefficients = new double[1];
		this.coefficients[0] = 0;
		this.exponents = new int[1];
		this.exponents[0] = 0;
	}
	
	public Polynomial(double[] coeffs, int[] expons) {
		this.coefficients = new double[coeffs.length];
		this.coefficients = coeffs;
		this.exponents = new int[expons.length];
		this.exponents = expons;
	}
	
	public Polynomial(File file) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader(file));
		String line = input.readLine();
		String[] terms = line.split("(?=[+-])");
		double[] new_coefficients = new double[terms.length];
		int[] new_exponents = new int[terms.length];
		for (int i = 0; i < terms.length; i++) {
			String[] components = terms[i].split("x");
			if (components.length == 1) {
				new_coefficients[i] = Double.parseDouble(components[0]);
				new_exponents[i] = 0;
			}
			else if (components[0].charAt(0) == '+') {
				new_coefficients[i] = Double.parseDouble(components[0].substring(1));
				new_exponents[i] = Integer.parseInt(components[i]);
			} else {
				new_coefficients[i] = Double.parseDouble(components[0]);
				new_exponents[i] = Integer.parseInt(components[1]);
			}
		}
		input.close();
		this.coefficients = new_coefficients;
		this.exponents = new_exponents;
	}
	
	public void saveToFile(String file_name) throws IOException {
		FileWriter output_writer = new FileWriter(file_name);
		String output = "";
		for (int i = 0; i < coefficients.length; i++) {
			if (exponents[i] == 0) {
				output = output + Double.toString(coefficients[i]);
			}
			else {
				if (coefficients[i] >= 0) {
					output += "+";
				}
				output = output + coefficients[i] + "x" + exponents[i];
			}
		}
		output_writer.write(output);
		output_writer.close();
	}
	
	public Polynomial add(Polynomial poly) {
		int size = this.exponents.length + poly.exponents.length;
		double[] new_coefficients = new double[size];
		int[] new_exponents = new int[size];
		int position = 0;
		for (int i = 0; i < this.exponents.length; i++) {
			for (int k = 0; k <= position; k++) {
				if (k == position) {
					// Not in existing exponents
					new_coefficients[position] = this.coefficients[i];
					new_exponents[position] = this.exponents[i];
					position++;
					break;
				}
				else if (this.exponents[i] == new_exponents[k]) {
					new_coefficients[k] += this.coefficients[i];
					break;
				}
			}
		}
		for (int i = 0; i < poly.exponents.length; i++) {
			for (int k = 0; k <= position; k++) {
				if (k == position) {
					// Not in existing exponents
					new_coefficients[position] = poly.coefficients[i];
					new_exponents[position] = poly.exponents[i];
					position++;
					break;
				}
				else if (poly.exponents[i] == new_exponents[k]) {
					new_coefficients[k] += poly.coefficients[i];
					break;
				}
			}
		}
		int new_size = position;
		double[] product_coefficients = trim_array_double(new_coefficients, new_size);
		int[] product_exponents = trim_array_int(new_exponents, new_size);
		Polynomial result = new Polynomial(product_coefficients, product_exponents);
		result.remove_redundant_exponents();
		return result;
	}
	
	public double evaluate(double x) {
		double total = 0;
		for (int i = 0; i < coefficients.length; i++) {
			total += (coefficients[i] * Math.pow(x, exponents[i]));
		}
		return total;
	}
	
	public boolean hasRoot(double x) {
		return (this.evaluate(x) == 0.0);
	}
	
	public Polynomial multiply(Polynomial poly) {
		int size = this.exponents.length * poly.exponents.length;
		double[] new_coefficients = new double[size];
		int[] new_exponents = new int[size];
		int position = 0;
		for (int i = 0; i < this.exponents.length; i++) {
			for (int j = 0; j < poly.exponents.length; j++) {
				int temp_exponent = this.exponents[i] + poly.exponents[j];
				double temp_coefficient = this.coefficients[i] * poly.coefficients[j];
				for (int k = 0; k <= position; k++) {
					if (k == position) {
						// Not in existing exponents
						new_coefficients[position] = temp_coefficient;
						new_exponents[position] = temp_exponent;
						position++;
						break;
					}
					else if (temp_exponent == new_exponents[k]) {
						new_coefficients[k] += temp_coefficient;
						break;
					}
				}
			}
		}
		double[] product_coefficients = trim_array_double(new_coefficients, position);
		int[] product_exponents = trim_array_int(new_exponents, position);
		Polynomial result = new Polynomial(product_coefficients, product_exponents);
		return result;
	}
	
	public void remove_redundant_exponents() {
		int size = this.coefficients.length;
/* 		for (int i = size - 1; i > 0; i--) {
			if (coefficients[i] == 0.0) {
				double[] new_coefficients = new double[i];
				int[] new_exponents = new int[i];
				for (int j = 0; j < i; i++) {
					new_coefficients[j] = this.coefficients[j];
					new_exponents[j] = this.exponents[j];
				}
				this.coefficients = new_coefficients;
				this.exponents = new_exponents;
			}
			else {
				break;
			}
		} */
		for (int i = 0; i < size; i++) {
			System.out.println(i);
			if (this.coefficients[i] == 0.0) {
				double[] new_coefficients = new double[size - 2];
				int[] new_exponents = new int[size - 2];
				int j = 0;
				int k = 0;
				while (j < size) {
					if (k != i && k < size - 2) {
						System.out.println(i);
						new_coefficients[k] = coefficients[i];
						new_exponents[k] = exponents[i];
						k++;
					}
					j++;
				}
				this.coefficients = new double[size - 2];
				this.coefficients = new_coefficients;
				this.exponents = new int[size - 2];
				this.exponents = new_exponents;
				size = size - 1;
			}
		}
		return;
	}
	
	public int[] trim_array_int(int[] array, int new_size) {
		int[] new_array = new int[new_size];
		for (int i = 0; i < new_size; i++) {
			new_array[i] = array[i];
		}
		return new_array;
	}

	public double[] trim_array_double(double[] array, int new_size) {
		double[] new_array = new double[new_size];
		for (int i = 0; i < new_size; i++) {
			new_array[i] = array[i];
		}
		return new_array;
	}
	
}

