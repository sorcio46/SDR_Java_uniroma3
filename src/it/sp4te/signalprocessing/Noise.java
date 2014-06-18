package it.sp4te.signalprocessing;

import java.util.Random;

public class Noise {
	private double varianza;
	private int length;
	private double[] parteReale;
	private double[] parteImmaginaria;
	
	public Noise(double snr, int length, double potenza) { 
	 
		Random campione = null; 
		double divisore = Math.pow(10, (snr/10)); 
		this.varianza = (potenza / divisore); 
		this.length = length; 
		 
		this.parteReale = new double[length]; 
		for (int i = 0; i < this.length; i++) { 
			campione = new Random(); 
			parteReale[i] = campione.nextGaussian() * Math.sqrt(varianza/2); 
		} 
		 
		this.parteImmaginaria = new double[length]; 
		for (int i = 0; i < this.length; i++) { 
			campione = new Random(); 
			parteImmaginaria[i] = campione.nextGaussian() * Math.sqrt(varianza/2); 
		} 
	}

	
	// Metodi Getter e Setter
	public double getVarianza() {
		return varianza;
	}

	public void setVarianza(double varianza) {
		this.varianza = varianza;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public double[] getParteReale() {
		return parteReale;
	}

	public void setParteReale(double[] parteReale) {
		this.parteReale = parteReale;
	}

	public double[] getParteImmaginaria() {
		return parteImmaginaria;
	}

	public void setParteImmaginaria(double[] parteImmaginaria) {
		this.parteImmaginaria = parteImmaginaria;
	}
	
}
