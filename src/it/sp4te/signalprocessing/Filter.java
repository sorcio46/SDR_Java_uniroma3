package it.sp4te.signalprocessing;

import it.sp4te.domain.Complex;
import it.sp4te.domain.Signal;

public class Filter {
	
	/**
	 * Crea un nuovo segnale rappresentante il filtro passa-basso
	 * NOTA BENE: il numero di campioni che deve essere passato deve essere dispari
	 * Ottimizzare il metodo come richiesto nell'homework
	 */
	// Metodo LPF
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
	
	//
	// Metodo LPF Intelligente che data la banda ne calcola il numero dei campioni
	// All'aumentare della Banda, la sinc generata si stringe, quindi mi servono meno campioni
	//
	public static Signal lowPassFilter(double band) {
		
		//Calcolo i campioni per prendere 9 lobi della Sinc
		double s=2*band;
		double temp=10/s;
		int numCampioni=(int)temp;
		if(numCampioni%2==0)
			numCampioni++;
		//System.out.println("Numero campioni filtro LPF= "+numCampioni);		
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
	
}
