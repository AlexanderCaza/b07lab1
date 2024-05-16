
public class Polynomial {
	//fields
	double[] coefficients;
	
	//methods
	public Polynomial() {
		coefficients = new double[]{0};
	}
	
	public Polynomial(double coeffs[]) {
		coefficients = new double[coeffs.length];
		coefficients = coeffs;
	}
	
	public Polynomial add(Polynomial poly) {
		int num_coefficients = 0;
		if (coefficients == null) { num_coefficients = poly.coefficients.length; }
		else { num_coefficients = Math.max(this.coefficients.length, poly.coefficients.length); }
		double[] new_coeffs = new double[num_coefficients];
		for (int i = 0; i < num_coefficients; i++) {
			if (i >= this.coefficients.length) {
				new_coeffs[i] = poly.coefficients[i];
			}
			else if (i >= poly.coefficients.length) {
				new_coeffs[i] = this.coefficients[i];
			}
			else {
				new_coeffs[i] = this.coefficients[i] + poly.coefficients[i];
			}
		}
		Polynomial new_polynomial = new Polynomial(new_coeffs);
		return new_polynomial;
	}
	
	public double evaluate(double x) {
		double total = 0;
		for (int i = 0; i < coefficients.length; i++) {
			total += (coefficients[i] * Math.pow(x, i));
		}
		return total;
	}
	
	public boolean hasRoot(int x) {
		return (this.evaluate(x) == 0.0);
	}
}
