/**
 * Classe statica che definisce le operazioni fondamentali da compiere sui segnali discreti come 
 * le operazioni di convoluzione e filtraggio
 * 
 * Nota: la convoluzione e' un operazione sovraccarica, invocarla opportunamente
 * @author Antonio Tedeschi, Davide Brutti
 *
 */
package it.sp4te.signalprocessing;

import it.sp4te.domain.*;

public class SignalProcessor {

	/**
	 * NB La funzione length degli array restuisce la lunghezza dell'array che non 
	 * coincide con la numerazione degli indici!
	 * pertanto per applicare correttamente la formula della convoluzione � necessario prestare un po' 
	 * di attenzione agli indici con cui andiamo a lavorare per definire l�indice j per il ciclo for 
	 * della traslazione temporale in maniera dinamica in modo da saltare i casi in cui l�indice 
	 * j sia maggiore delle dimensione delle sequenze
	 */
	
	public static double[] convoluzione(double[] v1, double[] v2){	
		//definizione della lunghezza del vettore finale contenente i risultati della convoluzione
		int finalLength = v1.length + v2.length-1;
		double[] result = new double[finalLength];
		
		//inizializzazione delle variabili temporali
		int upperBound = 0;
		int lowerBound = 0;
		
		//calcolo della convoluzione
		for(int k=0; k<finalLength; k++) {
			upperBound = Math.min(k, v2.length-1);
			lowerBound = Math.max(0, k - v1.length+1);
			for(int j=lowerBound; j<=upperBound; j++)
				result[k] += (v1[k-j]*v2[j]);
		}
		return result;
	}
	
	/**
	 * Convoluzione tra due vettori di numeri complessi
	 */
	public static Complex[] convoluzione(Complex[] v1, Complex[] v2){
		
		//definizione della lunghezza del vettore finale contenente i risultati della convoluzione
		int finalLength = v1.length + v2.length-1;
		Complex[] result = new Complex[finalLength];
		
		//inizializzazione delle variabili temporali
		int upperBound = 0;
		int lowerBound = 0;
		
		//calcolo della convoluzione
		for(int k=0; k<finalLength; k++) {
			upperBound = Math.min(k, v2.length-1);
			lowerBound = Math.max(0, k - v1.length+1);
			result[k] = new Complex(0,0);
			for(int j=lowerBound; j<=upperBound; j++)
				result[k] = result[k].somma(v1[k-j].prodotto(v2[j]));			
		}
		return result;
		
	}
	
	/**
	 * Crea un nuovo segnale rappresentante il filtro passa-basso
	 * NOTA BENE: il numero di campioni che deve essere passato deve essere dispari
	 * Ottimizzare il metodo come richiesto nell'homework
	 */
	//Metodo LPF
	public static Signal lowPassFilter(double band, int numCampioni) {
		
		Complex[] values = new Complex[numCampioni];
		int simmetria = (numCampioni) / 2;
		
		for(int n = - simmetria; n <= simmetria; n++){
			double realval = 2* band * SignalUtils.sinc(n, 2 * band);
			values[n + simmetria] = new Complex(realval, 0);
		}
		
		Signal lpf = new Signal(values);
		
		return lpf;
	}
	
	//Metodo LPF Intelligente che data la banda ne calcola il numero dei campioni
	public static Signal lowPassFilter(double band) {
		
		//Calcolo i campioni per prendere 9 lobi della Sinc
		double temp=(1/2*band)*10;
		temp++;
		int numCampioni=(int)temp;
				
		Complex[] values = new Complex[numCampioni];
		int simmetria = (numCampioni) / 2;
		
		for(int n = - simmetria; n <= simmetria; n++){
			double realval = 2* band * SignalUtils.sinc(n, 2 * band);
			values[n + simmetria] = new Complex(realval, 0);
		}
		
		Signal lpf = new Signal(values);
		
		return lpf;
	}
	
	//Metodo BPF base
	public static Signal BandPassFilter(double band, double f1, double f2){
		double temp=(1/2*band)*10;
		temp++;
		int numCampioni=(int)temp;
				
		Complex[] values = new Complex[numCampioni];
		int simmetria = (numCampioni) / 2;
		
		for(int n = - simmetria; n <= simmetria; n++){
			double realval = band * SignalUtils.sinc(n, band) * 2 * Math.cos(2 * Math.PI * n * ((f1+f2)/2));
			values[n + simmetria] = new Complex(realval, 0);
		}
		
		Signal bpf = new Signal(values);
		
		return bpf;
	}
	
	//Metodo BPF scritto da uno Sfaticato
	public static Signal LazyBandPassFilter(double band, double offset){
		double reale;
		Signal l=lowPassFilter(band);
		int simmetria=l.getLength()/2;
		for(int n = 0; n < l.getLength(); n++){
			reale=l.getValues()[n].getReale()*Math.cos(2* Math.PI * offset * n-simmetria);
			l.getValues()[n].setReale(reale);
		}
		return l;
	}
	
	/**
	 * Operazione di convoluzione fra segnali:
	 * implementa un'operazione di convoluzione discreta fra due segnali passati come parametro.
	 * Presuppone che il segnale d'ingresso abbia parte reale e immaginaria non nulle 
	 * e che il filtro abbia solo parte reale.
	 */
	public static Signal convoluzione(Signal segnaleIn, Signal rispImpulsivaFiltro){
		
		Complex[] values = convoluzione(segnaleIn.getValues(), rispImpulsivaFiltro.getValues());
		Signal signal = new Signal(values);
		
		return signal;
		
	}
	
	//Metodo da Implementare per l'Homework 2
	//Metoto per esegure l'espansione (gli serve F1)
	public static Signal espansione(int F1, Signal signalIN){
		Complex[] sequenzaIN=signalIN.values;
		Complex[] espansa=new Complex[sequenzaIN.length * F1];
		int j=0, i;
		for(i=0 ; i<espansa.length ; i++){
			if(i%F1==0){
				espansa[i]=sequenzaIN[j];
				j++;
			}
			else{
				espansa[i]=new Complex(0,0);
			}
		}
		Signal esp=new Signal(espansa);
		return esp;
	}
	
	//Metodo da Implementare per l'Homework 2
	//Metoto per la generazione per il filtro Interpolatore
	public static Signal filtroInterpolatore(double band, int F1){
		double temp=(2 * F1 * band * 10);
		int numCampioni=(int)temp;
		if(numCampioni%2==0)
			numCampioni++;
		Complex[] values = new Complex[numCampioni];
		int simmetria = (numCampioni) / 2;
		
		for(int n = - simmetria; n <= simmetria; n++){
			double realval = 2* F1 * band * SignalUtils.sinc(n, 2 * band);
			values[n + simmetria] = new Complex(realval, 0);
		}
		
		Signal lpf = new Signal(values);
		
		return lpf;
	}
	
	//Metodo da Implementare per l'Homework 2
	//Metoto per eseguire l'interpolazione
	public static Signal interpolazione(int F1, Signal signalIN){
		//Calcolo banda
		double B=1/(2.0*F1);
		//Genero il filtro e faccio la convoluzione
		Signal interpolatore= filtroInterpolatore(B,F1);
		Signal interpolato=convoluzione(signalIN, interpolatore);
		System.out.println(interpolatore.toString());
		//Taglio le code
		Complex[] val= new Complex [signalIN.values.length];
		int i, j=0, n=(interpolatore.values.length);
		n=(n-1)/2;
		for(i=n; i< interpolato.values.length -n; i++){
			val[j]=interpolato.values[i];
			j++;
		}
		return interpolato= new Signal(val);
	}
	
	//Metodo da Implementare per l'Homework 2
	//Metoto per esegure la decimazione (gli serve F1)
	public static Signal decimazione(int F2, Signal signalIN){
		Complex[] interpolato=signalIN.values;
		int lengthDec= interpolato.length/F2;
		Complex[] decimato= new Complex[lengthDec];
		int i, j=0;
		for(i=0;i<interpolato.length;i++){
			if(i%F2 == 0 && j<lengthDec){
				decimato[j]=interpolato[i];
				j++;
			}
		}
		Signal deci=new Signal(decimato);
		return deci;
	}
	
	//Metodo da Implementare per l'Homework 2
	//Metodo per il cambio del tasso di campionamento
	public static Signal cambioTassoCampionamento(int T1, int T2, Signal signalIN){
		int F1=SignalUtils.calcoloParametriCambio(T1, T2)[0];
		int F2=SignalUtils.calcoloParametriCambio(T1, T2)[1];
		Complex[] valori= signalIN.values;
		Signal signalOUT=new Signal(valori);
		System.out.println("Dato il segnale"+"\n"+signalOUT.toString()+"\n");
		System.out.println("F1="+F1+" F2="+F2);
		if(F1!=1){
			//Espansione
			System.out.println("Espansione: calcolo in corso");
			signalOUT=espansione(F1, signalOUT);;
			System.out.println(signalOUT.toString()+"\n");
			
			//Interpolazione
			System.out.println("Interpolazione: calcolo in corso");
			signalOUT= interpolazione(F1, signalOUT);
			System.out.println(signalOUT.toString()+"\n");
		}
		if(F2!=1){
			//Decimazione
			System.out.println("Decimazione: calcolo in corso");
			signalOUT=decimazione(F2, signalOUT);
			System.out.println(signalOUT.toString()+"\n");
		}
		return signalOUT;
		
	}
	
	//Metodo da Implementare per l'Homework 3
	//Metodo per il primo blocco del DDC: il selettore di canale
	public static Signal selettoreCanale(Signal signalIn, double deltaF){
		int n;
		double temp;
		Signal signalOUT;
		Complex[] valori=signalIn.values;
		for(n=0;n<valori.length;n++){
			//Converto il numero complesso in coordinate polari
			valori[n].conversioneCP();
			//Calcolo l'esponente per l'eponenziale e^(i*fase)
			temp=Math.PI * 2 * deltaF *n;
			//Moltiplico l'esponente per la fase
			//Considero la rappresentazione secondo la formula di Eulero
			//Dove modulo*e^(i*fase)
			valori[n].setFase(valori[n].getFase()*temp);
		}
		signalOUT=new Signal(valori);
		return signalOUT;
	}
	
	//Metodo da Implementare per l'Homework 3
	//Metodo per l'implemetazione del Digital Down Converter
	public static Signal DDC(Signal signalIn, double deltaF, double bandLPF, int F2){
		Signal signalOUT;
		//Applico il selettore di canale al segnale
		signalOUT=selettoreCanale(signalIn,deltaF);
		//Genero il filtro passa basso
		Signal filter=lowPassFilter(bandLPF);
		//Applico il filtro passa basso
		signalOUT=convoluzione(signalOUT,filter);
		signalOUT=decimazione(F2,signalOUT);
		return signalOUT;
	}
	
	//Metodo da Implementare per l'Homework 3
	//Metodo per l'implemetazione del Demodulatore
	//Useremo il derivatore numerico per implementarlo
	public static Signal demodulatore(Signal signalIn, double deltaF){
		Signal signalOUT=new Signal();
		int i,j=0;
		Complex temp=new Complex(0,0);
		Complex[] valori=new Complex[signalIn.values.length];
		//Faccio lo shift dei dati
		valori[0]=signalIn.values[signalIn.values.length-1];
		for(i=1;i<signalIn.values.length;i++){
			valori[i]=signalIn.values[j];
			j++;
		}
		//Faccio il rapporto incrementale
		for(i=0;i<signalIn.values.length;i++){
			valori[i].coniugato();
			temp.setReale(signalIn.values[i].getReale() * valori[i].getReale());
			temp.setImmaginaria(signalIn.values[i].getImmaginaria() * valori[i].getImmaginaria());
			signalOUT.values[i]=temp;
		}
		//Faccio il calcolo delle frequenze
		for(i=0;i<signalOUT.values.length;i++){
			temp=signalOUT.values[i];
			signalOUT.values[i].setFase(Math.atan(temp.getImmaginaria()/temp.getReale()));
		}
		return signalOUT;
	}

}
