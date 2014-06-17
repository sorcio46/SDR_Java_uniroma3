package it.sp4te.signalprocessing;

import java.io.IOException;

import it.sp4te.controllerio.*;
import it.sp4te.domain.*;

public class SignalUtils {
	
	//Metodo ottimizzato per il calcolo della funzione sinc
	public static double sinc(double n, double band){
		double res = 0;
		if(n==0)
			res = 1;
		if(n%(1/(band))!=0)
			res = (Math.sin(Math.PI*band*n))/(Math.PI*band*n);				
		return res;
	}
	
	//Metodo per il calcolo del massimo comun divisore
	public static int mcd(int n1, int n2){
		int mcd=-1, i;
		for(i=1;i<=n2;i++){
			if(n1%i==0){
				if(n2%i==0){
					mcd=i;
				}
			}
		}
		return mcd;
	}
	
	//Metodo da Implementare per l'Homework 2
	//Metodo per il calcolo dei parametri per il cambio del tasso di campionamento
	public static int[] calcoloParametriCambio(int T1, int T2){
		int f1, f2, mcd;
		mcd=mcd(T1,T2);
		f2=T2/mcd;
		f1=T1/mcd;
		int[] db = {f1,f2};
		return db;
	}
	
	//Metodo da Implementare per l'Homework 3
	//Metodo per la lettura dei campioni da file
	public static Signal leggiCampioni(String pathIn, int limiteCampioni) throws IOException{
		Signal letto=new Signal();
		letto=ControllerIO.leggi(pathIn,limiteCampioni);
		return letto;
	}
	
	//Metodo da Implementare per l'Homework 3
	//Metodo della scrittura dei campioni su file
	public static void scriviCampioni(String pathOut, Signal s) throws IOException{
		double[] values=new double[s.values.length*2];
		int i=0;
		for(Complex c: s.values){
			values[i]=c.getReale();
			i++;
			values[i]=c.getImmaginaria();
			i++;
		}
		ControllerIO.scrivi(pathOut, values);
	}
	
	//Metodo per la scrittura delle fasi di un campione su File
	public static void scriviFasi(String pathOut, Signal s) throws IOException{
		double[] values=new double[s.values.length*2];
		int i=0;
		for(Complex c: s.values){
			values[i]=c.getFase();
			i++;
		}
		ControllerIO.scriviFasi(pathOut, values);
	}
	
	//Metodo per la scrittura di un vettore di double su file
	public static void scriviDouble(String pathOut, double[] s) throws IOException{
		ControllerIO.scriviFasi(pathOut, s);
	}
}
