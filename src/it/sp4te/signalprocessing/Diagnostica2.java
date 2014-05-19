package it.sp4te.signalprocessing;

import it.sp4te.domain.*;
import it.sp4te.signalprocessing.SignalProcessor;

public class Diagnostica2 {
	public static void homework1(){
		Complex[] vet1 = {new Complex(3,0), new Complex(2,0), new Complex(1,0)};
		Complex[] vet2 = {new Complex(1,0), new Complex(2,0), new Complex(1,0), new Complex(3,0)};
			
		//Esempio convoluzione tra reali
		double[] v1 = {3,2,1};
		double[] v2 = {1,1,2,1};
		double[] v3 = SignalProcessor.convoluzione(v1, v2);
		System.out.println("------------------");
		System.out.println("CONVOLUZIONE REALI");
		System.out.println("------------------");
		for(int i= 0; i< v3.length;i++)
			System.out.println(v3[i]);
	
		//Esempio convoluzione tra Complessi		
		System.out.println("\n----------------------");
		System.out.println("CONVOLUZIONE COMPLESSI");
		System.out.println("----------------------");
		Complex[] vet3 = SignalProcessor.convoluzione(vet1, vet2);
		for(int i= 0; i< vet3.length;i++)
			System.out.println(vet3[i].toString());
		
		//Esempio di filtraggio (convoluzione tra un segnale e il filtro passa-basso)
	    Signal s = new Signal(vet1);
	    Signal lpf= SignalProcessor.lowPassFilter(0.25, 5);
		Signal lpe= SignalProcessor.lowPassFilter(0.33);
	    Signal f1 = SignalProcessor.convoluzione(s, lpf);
		Signal f2 = SignalProcessor.convoluzione(s, lpe);
		System.out.println("\n-------------");
		System.out.println("FILTRAGGIO LPF");
		System.out.println("--------------");
		
		System.out.println("Dato il segnale:");
		System.out.println(s.toString());
		
		System.out.println("Dato il Filtro normale:");
		System.out.println(lpf.toString());
		System.out.println("Filtrato NORMALE");
		System.out.println(f1.toString());
		
		System.out.println("Dato il Filtro intelligente:");
		System.out.println(lpe.toString());;
		System.out.println("Filtrato INTELLIGENTE");
		System.out.println(f2.toString());
		
		Signal z = new Signal(vet2);
		
		//Esempio di filtraggio passa banda
		System.out.println("\n--------------");
		System.out.println("FILTRAGGIO BPF");
		System.out.println("--------------");

		System.out.println("Dato il segnale:");
		System.out.println(z.toString());
		
		Signal lz = SignalProcessor.BandPassFilter(1.25,8.65,9.85);
		System.out.println("");
		Signal f4 = SignalProcessor.convoluzione(z, lz);
		
		
		System.out.println("Filtro LAZY");
		System.out.println(lz.toString());
		System.out.println("Filtrato LAZY");
		System.out.println(f4.toString());
		
	}
	public static void homework2(){
		Complex[] vet2 = {new Complex(1,0), new Complex(2,0), new Complex(1,0), new Complex(3,0)};
		Complex[] vet1 = {new Complex(1,0), new Complex(2,0), new Complex(3,0)};
		Signal z = new Signal(vet2);
		Signal f = new Signal(vet1);
		
		Signal cambio=SignalProcessor.cambioTassoCampionamento(18, 24, z);
		System.out.print("\n");
		Signal cambio2=SignalProcessor.cambioTassoCampionamento(3, 5, f);
		System.out.println("Con tasso di campionamento portato da Tc=24 a Tc=18");
		System.out.println(cambio.toString());
		System.out.println("Con tasso di campionamento portato da Tc=3 a Tc=5");
		System.out.println(cambio2.toString());
	}
	public static void homework3(){
		//Roba per il terzo Homework;
	}
	public static void main (String[] args){
		//Esecuzione Diagnostica per Homework 1
        //homework1();
        //Esecuzione Diagnostica per Homework 2
        //homework2();
		//Esecuzione Diagnostica per Homework 3
        homework3();
	}
}
