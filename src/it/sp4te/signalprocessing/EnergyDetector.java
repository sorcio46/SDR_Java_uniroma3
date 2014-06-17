package it.sp4te.signalprocessing;

import it.sp4te.domain.Signal;

public class EnergyDetector {
	
	private Signal segnaleIn;
	private double snr;
	private int errore;
	private double soglia;
	private double detection;
	
	public EnergyDetector(Signal s, double snr, int e){
		this.segnaleIn=s;
		this.snr=snr;
		this.errore=e;
	}

	//
	// Metodo Principale per il calcolo della detection
	//
	public double calcolaDetection(){
		
		//Roba varia da Implementare
		
		return this.detection;
	}
	
	//
	// Metodo di supporto per il calcolo del valore soglia a partire dall SNR
	//
	public void calcolaSoglia(){
		
		//La probabilità di falso allarme è un parametro prefissato
		double falsoAllarme = 0.001;
		
		//Roba varia da Implementare
		
	}
	
	//
	// Metodo di supporto 
	//
	public double[] calcolaVettoreEnergia(int blocchi){
		
		double[] vettoreEnergie = new double[blocchi];
		
		//Roba varia da Implementare
		
		return vettoreEnergie;
	}
	
	//
	// Medoti Getter e Setter vari
	//
	public Signal getSegnaleIn() {
		return segnaleIn;
	}

	public void setSegnaleIn(Signal segnaleIn) {
		this.segnaleIn = segnaleIn;
	}

	public double getSnr() {
		return snr;
	}

	public void setSnr(double snr) {
		this.snr = snr;
	}

	public int getErrore() {
		return errore;
	}

	public void setErrore(int errore) {
		this.errore = errore;
	}

	public double getSoglia() {
		return soglia;
	}

	public void setSoglia(double soglia) {
		this.soglia = soglia;
	}

	public double getDetection() {
		return detection;
	}

	public void setDetection(double detection) {
		this.detection = detection;
	}
	
}
