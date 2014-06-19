package it.sp4te.signalprocessing;

//
// Classe utile al solo scopo di simulare un segnale modulato QPSK
// per analizzare in uno scenario controllato il calcolo della soglia
//
public class Segnale{
	private int length;
	private double[] reale;
	private double[] immaginaria;
	
	public Segnale(int length) { 
 
		this.length = length; 
		this.reale = new double[length]; 
		this.immaginaria = new double[length]; 
 
		for (int i = 0; i < this.length; i++) { 
			double v = Math.random(); 
			if (v < 0.5) 
				this.reale[i] = v/Math.sqrt(this.length); 
			else 
				this.reale[i] = -v/Math.sqrt(this.length); 
 
			double p = Math.random(); 
			if (p < 0.5) 
				this.immaginaria[i] = p/Math.sqrt(this.length); 
			else 
				this.immaginaria[i] = -p/Math.sqrt(this.length); 
		} 
	}

	//Metodi Getter e Setter vari
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public double[] getReale() {
		return reale;
	}

	public void setReale(double[] reale) {
		this.reale = reale;
	}

	public double[] getImmaginaria() {
		return immaginaria;
	}

	public void setImmaginaria(double[] immaginaria) {
		this.immaginaria = immaginaria;
	}
	
	
}