package it.sp4te.signalprocessing;

import java.io.IOException;

import it.sp4te.domain.Complex;
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
	public double calcolaDetection() throws IOException{
		
		double[] energie = this.calcolaVettoreEnergia(1000);
		System.out.println(energie.length);
		for(double d: energie){
			System.out.println(d);
		}
		SignalUtils.scriviDouble("C:/Users/Davide/Downloads/Sequenze_SDR_2014/Sequenza_1/SE.txt", energie);
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
		double[] vettorone = new double[1000000];
		double val=0;
		int i=0, j=0, n=0;
		
		for(Complex c : this.segnaleIn.values){
			c.conversioneCP();
			vettorone[i]=Math.pow(c.getModulo(), 2);
			i++;
		}
		
		for(i=0;i<blocchi;i++){
			for(j=0;j<1000;j++){
				val=val+vettorone[n];
				n++;
			}
			vettoreEnergie[i]=val;
		}
		//Roba varia da Implementare
		
		return vettoreEnergie;
	}
	
	//
	// Metodi Getter e Setter vari
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
