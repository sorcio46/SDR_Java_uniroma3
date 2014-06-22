package it.sp4te.signalprocessing;

import it.sp4te.domain.Complex;
import it.sp4te.domain.Signal;

public class Convolution {
	
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
}
