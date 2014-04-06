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
	 * Calcola il valore della sinc nel punto x.
	 * Se x=0 allora torna 1
	 * Nota: sinc(x) = (sin(pi*x))/(pi*x)
	 * @param x
	 * @return il valore della sinc per il punto x
	 */
	//Metodo ottimizzato
	public static double sinc(double n, double band){
		double res = 0;
		if(n==0)
			res = 1;
		else{
			if(n%(1/2*band)==0)
				res=0;
			else
				res = (Math.sin(Math.PI*band*n))/(Math.PI*band*n);
		}
		return res;
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
			double realval = 2* band * sinc(n, 2 * band);
			values[n + simmetria] = new Complex(realval, 0);
		}
		
		Signal lpf = new Signal(values);
		
		return lpf;
	}
	
	//Metodo LPF Intelligente
	public static Signal lowPassFilter(double band) {
		//4 campioni a sinistra + 4 campioni a destra
		//+1 campione centrale = 9 lobi
		double temp=1/band +1;
		int numCampioni=(int) temp;
		Complex[] values = new Complex[numCampioni];
		int simmetria = (numCampioni) / 2;
		
		for(int n = - simmetria; n <= simmetria; n++){
			double realval = 2* band * sinc(n, 2 * band);
			values[n + simmetria] = new Complex(realval, 0);
		}
		
		Signal lpf = new Signal(values);
		
		return lpf;
	}
	
	//Metodo BPF Intelligente
	public static Signal bandPassFilter(double bandl, double bandh) {
		double band=bandh-bandl;
		//double fc=bandh-(band/2);
		double temp=1/band +1;
		int numCampioni=(int) temp;
		Complex[] values = new Complex[numCampioni];
		int simmetria = (numCampioni) / 2;
		
		//Un filtro passa banda con lower band Bl e upper band Bh
		//non e' altro che la differenza di due filtr sinc
		for(int n = - simmetria; n <= simmetria; n++){
			//Formula principale
			double realval1 = 2* bandh * sinc(n, bandh) -2* bandl * sinc(n, bandl);
			//Formula secondaria
			//double realval2 = 2* band * sinc(n, 2 * band) * Math.cos(fc * n);
			values[n + simmetria] = new Complex(realval1, 0);
		}
		Signal lpf = new Signal(values);
		return lpf;
	}
	
	//Metodo Sfaticato
	public static Signal LazyBandPassFilter(double offset, double band){
		Signal l=lowPassFilter(band);
		double temp=1/band +1, reale;
		int numCampioni=(int) temp;
		int simmetria = (numCampioni) / 2;
		for(int n = - simmetria; n <= simmetria; n++){
			reale=l.getValues()[n + simmetria].getReale()*Math.cos(Math.PI * offset * n);
			l.getValues()[n + simmetria].setReale(reale);
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
}
