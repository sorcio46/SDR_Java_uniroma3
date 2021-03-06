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
	private int precisione;
	
	public EnergyDetector(Signal s, double snr, int e, int precisione){
		this.segnaleIn=s;
		this.snr=snr;
		this.errore=e;
		this.precisione=precisione;
	}

	//
	// Metodo Principale per il calcolo della detection
	//
	public void calcolaDetection() throws Exception{

		System.out.println("Calcolo della detection di un segnale con snr "+this.snr+" e errore 10^("+this.errore+")");
		//Calcolo il vettore energie del segnale dato in input
		long start_time = System.nanoTime();
		double[] energie = this.calcolaVettoreEnergia(1000);
		double mediaEnergiaCampioni=0;
		
		for(Double d:energie){
			mediaEnergiaCampioni = mediaEnergiaCampioni+d;
		}
		mediaEnergiaCampioni=mediaEnergiaCampioni/(double)energie.length;
		
		System.out.println("Lunghezza del vettore energie: "+energie.length);
		System.out.println("Media del vettore energie: "+mediaEnergiaCampioni);
		String s="C:/Users/Davide/Downloads/Sequenze_SDR_2014/Output/Energie_Campioni_Raccolti"+this.snr+".txt";
		SignalUtils.scriviDouble(s, energie);

		//Mi calcolo questa benedetta Soglia
		System.out.println("");
		calcolaSoglia();
		System.out.println("");
		System.out.println("Soglia calcolata: "+this.soglia);
		
		int contatoreSoglia=0;
		for(double d: energie){
			if(d>this.soglia)
				contatoreSoglia++;
		}
		
		this.detection=(double)contatoreSoglia/(double)energie.length;
		setDetection(this.detection*100);
		long end_time = System.nanoTime();
		
		double deltaTempo = (end_time - start_time)/1e6;
		System.out.println("Probabilit� di detection calcolata: "+this.detection+"%"+" in "+deltaTempo+" millisecondi");
	}
	
	//
	// Metodo di supporto per il calcolo del valore soglia a partire dall SNR
	//
	public void calcolaSoglia() throws Exception{
		
		//La probabilit� di falso allarme � un parametro prefissato
		double falsoAllarme = Math.pow(10, this.errore);
		
		//Dichiaro un sacco di variabili che mi potrebbero servire
		double varianzaVettoreEnergia, media=0, temp, mediaVettoreEnergia;
		double[] VER = calcolaVettoreEnergiaRumore(this.precisione);
		double[] VVE = new double[VER.length];
		int i=0;

		//Calcolo la media del vettore Energia di un Rumore
		for(double d: VER){
			media=media+d;
		}
		
		mediaVettoreEnergia=media/(double)VER.length;
		//System.out.println("mediaVettoreEnergia: "+mediaVettoreEnergia);
		
		//Calcolo la varianza del vettore Energia di un Rumore
		for(double d: VER){
			temp=d-mediaVettoreEnergia;
			temp=Math.pow(temp, 2);
			VVE[i]=temp;
			i++;
		}
		media=0;
		for(double d: VVE){
			media=media+d;
		}
		varianzaVettoreEnergia=media/(double)VVE.length;
		
		this.soglia=mediaVettoreEnergia + (2*Math.sqrt(varianzaVettoreEnergia)) * SignalUtils.InvErf(1-(2*falsoAllarme));
	}
	
	//
	// Metodo di supporto 
	// Calcolo il vettore energia del segnale in ingresso
	//
	public double[] calcolaVettoreEnergia(int blocchi){
		
		double[] vettoreEnergie = new double[blocchi];
		double[] vettorone = new double[this.segnaleIn.values.length];
		double val=0;
		int i=0, j=0, n=0;
		
		for(Complex c : this.segnaleIn.values){
			vettorone[i]=Math.pow(c.modulo(), 2);
			i++;
		}
		
		for(i=0;i<blocchi;i++){
			for(j=0;j<1000;j++){
				val=val+vettorone[n];
				n++;
			}
			vettoreEnergie[i]=val;
			val=0;
		}
		
		return vettoreEnergie;
	}
	
	//
	// Metodo di supporto 
	// Calcolo il vettore energie dei rumore generati
	//
	public double[] calcolaVettoreEnergiaRumore(int blocchi) throws IOException{
		
		double[] vettoreEnergieRumore = new double[blocchi];
		int i;
		
		for(i=0;i<blocchi;i++){
			vettoreEnergieRumore[i]=calcolaEnergiaRumore();
		}
		
		String s = "C:/Users/Davide/Downloads/Sequenze_SDR_2014/Output/Energie_Rumore_Generato"+this.snr+".txt";
		SignalUtils.scriviDouble(s, vettoreEnergieRumore);
		
		return vettoreEnergieRumore;
	}
	
	//
	// Metodo di supporto 
	// Calcola l'energia di un rumore generato di 1000 campioni
	//
	public double calcolaEnergiaRumore(){
		
		//Calcolo la potenza del rumore a partire dall SNR
		double temp;
		int i;
		
		Noise rumore = new Noise(this.snr,1000,1);
		double energia=0;
		
		for(i=0;i<rumore.getParteReale().length;i++){
			temp=Math.sqrt( Math.pow(rumore.getParteReale()[i],2) + Math.pow(rumore.getParteImmaginaria()[i],2));
			temp=Math.pow(temp, 2);
			energia=energia+temp;
		}
		
		return energia;
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

	public int getPrecisione() {
		return precisione;
	}

	public void setPrecisione(int precisione) {
		this.precisione = precisione;
	}
	
}
