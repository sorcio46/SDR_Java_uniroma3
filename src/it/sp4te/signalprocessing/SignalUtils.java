package it.sp4te.signalprocessing;

public class SignalUtils {
	
	/**
	 * Calcola il valore della sinc nel punto x.
	 * Se x=0 allora torna 1
	 * Nota: sinc(x) = (sin(pi*x))/(pi*x)
	 * @param x
	 * @return il valore della sinc per il punto x
	 */
	//Metodo ottimizzato per il calcolo della funzione sinc
	public static double sinc(double n, double band){
		double res = 0;
		if(n==0)
			res = 1;
		else{
			if(n%(1/band)!=0)
				res = (Math.sin(Math.PI*band*n))/(Math.PI*band*n);				
		}
		return res;
	}
	
	//Metodo per il calcolo del massimo comun divisore
	//basato sull algoritmo di Euclide
	public static int mcd(int n1, int n2){
		int h=0;
		if(n1==n2)
			return n2;
		if(n2>n1){
			h=n1;
			n1=n2;
			n2=h;
		}
		return mcd(n1-n2,n2);
	}
	
	//Metodo da fare per l'Homework 2
	//Metodo per il calcolo dei parametri per il cambio del tasso di campionamento
	public static int[] calcoloParametriCambio(int T1, int T2){
		int f1, f2, mcd;
		mcd=mcd(T1,T2);
		f2=T2/mcd;
		f1=T1/mcd;
		int[] db = {f1,f2};
		return db;
	}
}
