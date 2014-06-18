package it.sp4te.signalprocessing;

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
	public void calcolaDetection() throws Exception{
		System.out.println("Calcolo della detection di un segnale con snr "+this.snr+" e errore 10^("+this.errore+")");
		//Calcolo il vettore energie del segnale dato in input
		double[] energie = this.calcolaVettoreEnergia(1000);
		System.out.println("Lunghezza del vettore energie: "+energie.length);
		SignalUtils.scriviDouble("C:/Users/Davide/Downloads/Sequenze_SDR_2014/Sequenza_1/SE.txt", energie);

		//Mi calcolo questa benedetta Soglia
		calcolaSoglia();
		System.out.println("");
		System.out.println("Soglia calcolata: "+this.soglia);
		
		int contatoreSoglia=0;
		for(double d: energie){
			if(d>this.soglia)
				contatoreSoglia++;
		}
		
		this.detection=contatoreSoglia/energie.length;
		setDetection(this.detection*100);
		System.out.println("Probabilità di detection calcolata: "+this.detection+"%");
		
	}
	
	//
	// Metodo di supporto per il calcolo del valore soglia a partire dall SNR
	//
	public void calcolaSoglia() throws Exception{
		
		//La probabilità di falso allarme è un parametro prefissato
		double falsoAllarme = Math.pow(10, this.errore);
		
		//Dichiaro un sacco di variabili che mi potrebbero servire
		double varianzaVettoreEnergia, media=0, temp, mediaVettoreEnergia;
		double[] VER = calcolaVettoreEnergiaRumore(1000);
		double[] VVE = new double[VER.length];
		int i=0;

		//Calcolo la media del vettore Energia di un Rumore
		for(double d: VER){
			media=media+d;
		}
		mediaVettoreEnergia=media/VER.length;
		System.out.println("mediaVettoreEnergia: "+mediaVettoreEnergia);
		//Calcolo la varianza del vettore Energia di un Rumore
		for(double d: VER){
			temp=d-media;
			temp=Math.pow(temp, 2);
			VVE[i]=temp;
			i++;
		}
		media=0;
		for(double d: VVE){
			media=media+d;
		}
		varianzaVettoreEnergia=media/VVE.length;
		
		this.soglia=mediaVettoreEnergia + (2*Math.sqrt(varianzaVettoreEnergia)) * SignalUtils.InvErf(falsoAllarme);
	}
	
	//
	// Metodo di supporto 
	// Calcolo il vettore energia del segnale in ingresso
	//
	public double[] calcolaVettoreEnergia(int blocchi){
		
		double[] vettoreEnergie = new double[blocchi];
		double[] vettorone = new double[1000000];
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
		//Roba varia da Implementare
		
		return vettoreEnergie;
	}
	
	//
	// Metodo di supporto 
	// Calcolo il vettore energie dei rumore generati
	//
	public double[] calcolaVettoreEnergiaRumore(int blocchi){
		
		double[] vettoreEnergieRumore = new double[blocchi];
		int i;
		
		for(i=0;i<blocchi;i++){
			vettoreEnergieRumore[i]=calcolaEnergiaRumore();
		}
		
		return vettoreEnergieRumore;
	}
	
	//
	// Metodo di supporto 
	// Calcola l'energia di un rumore di 1000 campioni
	//
	public double calcolaEnergiaRumore(){
		
		//Calcolo la potenza del rumore a partire dall SNR
		double potenza, SNRlin=Math.pow(10, this.snr/10), temp;
		potenza=1/SNRlin;
		int i;
		
		Noise rumore = new Noise(this.snr,1000,potenza);
		double energia=0;
		
		for(i=0;i<rumore.getParteReale().length;i++){
			temp=Math.sqrt( Math.pow(rumore.getParteReale()[i],2) + Math.pow(rumore.getParteImmaginaria()[i],2));
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
	
}
