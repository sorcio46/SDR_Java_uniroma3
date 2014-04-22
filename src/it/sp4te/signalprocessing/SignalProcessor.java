/**
 * Classe statica che definisce le operazioni fondamentali da compiere sui segnali discreti come 
 * le operazioni di convoluzione e filtraggio
 * 
 * Nota: la convoluzione � un operazione sovraccarica, invocarla opportunamente
 * @author Antonio Tedeschi
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
	 *
	 * @param v1
	 * @param v2
	 * @return
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
	 *  
	 * @param v1
	 * @param v2
	 * @return
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
	 * 
	 * @param band
	 * @param numCampioni
	 * @return Segnale discreto
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
		
		double temp=(2 * band * 10);
		int numCampioni=(int)temp;
		if(numCampioni%2==0)
			numCampioni++;
		Complex[] values = new Complex[numCampioni];
		int simmetria = (numCampioni) / 2;
		
		for(int n = - simmetria; n <= simmetria; n++){
			double realval = 2* band * SignalUtils.sinc(n, 2 * band);
			values[n + simmetria] = new Complex(realval, 0);
		}
		
		Signal lpf = new Signal(values);
		
		return lpf;
	}
	
	/*
	//Metodo BPF-Tipo1 Intelligente che non sembra funzionare quindi per il momento è commentato
	public static Signal bandPassFilter1(double bandl, double bandh) {
		double band=bandh-bandl;
		//double fc=bandh-(band/2);
		double temp=(2*band*10);
		int numCampioni=(int)temp;
		if(numCampioni%2==0)
			numCampioni++;
		Complex[] values = new Complex[numCampioni];
		int simmetria = (numCampioni) / 2;
		
		//Un filtro passa banda con lower band Bl e upper band Bh
		//non e' altro che la differenza di due filtr sinc
		for(int n = - simmetria; n <= simmetria; n++){
			double realval = 2* bandh * sinc(n, bandh) -2* bandl * sinc(n, bandl);
			values[n + simmetria] = new Complex(realval, 0);
		}
		Signal lpf = new Signal(values);
		return lpf;
	}
	*/
	
	//Metodo BPF-Tipo2 Intelligente
	public static Signal bandPassFilter2(double band, double fc) {
		double temp=(2 * band * 10);
		int numCampioni=(int)temp;
		if(numCampioni%2==0)
			numCampioni++;
		Complex[] values = new Complex[numCampioni];
		int simmetria = (numCampioni) / 2;
		
		//Un filtro passa banda di banda band centrato su frequenza fc
		//non e' altro che la moltiplicazione di una sinc per un coseno
		for(int n = - simmetria; n <= simmetria; n++){
			double realval = 2* band * SignalUtils.sinc(n, 2 * band) * Math.cos(2 * fc * Math.PI * n);
			values[n + simmetria] = new Complex(realval, 0);
		}

		Signal lpf = new Signal(values);
		return lpf;
	}
	
	//Metodo scritto da uno Sfaticato
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
	 * @param segnaleIn
	 * @param rispImpulsivaFiltro
	 * @return
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
		System.out.println("F1= "+F1+" F2="+F2);
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
}
