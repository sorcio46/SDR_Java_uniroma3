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
	//Metoto per eseguire l'interpolazione
	public static Signal interpolazione(int F1, Signal signalIN){
		//Calcolo banda
		double B=1/(2.0*F1);
		//Genero il filtro e faccio la convoluzione
		Signal interpolatore= Filter.filtroInterpolatore(B,F1);
		Signal interpolato=Convolution.convoluzione(signalIN, interpolatore);
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
		Signal signalOUT;
		Complex[] valoriIn=signalIn.values;
			for(n=0;n<valoriIn.length;n++){
				Complex c = new Complex(0,Math.PI * 2 * deltaF * n);
				c=c.exp();
				Complex z=valoriIn[n].prodotto(c);
				valoriIn[n]=z;
			}
		signalOUT=new Signal(valoriIn);
		return signalOUT;
	}
	
	//Metodo da Implementare per l'Homework 3
	//Metodo per l'implemetazione del Digital Down Converter
	public static Signal DDC(Signal signalIn, double deltaF, double bandLPF, int F2){
		
		System.out.println("INIZIO DDC");
		
		Signal signalOUT;
		//Applico il selettore di canale al segnale
		signalOUT=selettoreCanale(signalIn,deltaF);
		//Genero il filtro passa basso
		Signal filter=Filter.lowPassFilter(bandLPF);
		//Applico il filtro passa basso
		signalOUT=Convolution.convoluzione(signalOUT,filter);
		//BASTA SOLO LA DECIMAZIONE (?)
		signalOUT=decimazione(F2,signalOUT);
		return signalOUT;
	}
	
	//Metodo da Implementare per l'Homework 3
	//Metodo per l'implemetazione del Demodulatore
	//Useremo il derivatore numerico per implementarlo
	public static Signal demodulatore(Signal signalIn){
		Signal signalOUT=new Signal();
		int i,j=0;
		Complex temp;
		Complex[] valori=new Complex[signalIn.values.length];
		Complex[] valoriRI=new Complex[valori.length];
		
		System.out.println("INIZIO demodulatore");
		
		//Faccio lo shift dei dati
		valori[0]=signalIn.values[signalIn.values.length-1];
		for(i=1;i<signalIn.values.length;i++){
			valori[i]=signalIn.values[j];
			j++;
		}
		
		//Faccio il rapporto incrementale
		for(i=0;i<valori.length;i++){	
			valori[i]=valori[i].coniugato();
			temp=signalIn.values[i].prodotto(valori[i]);
			valoriRI[i]=temp;
		}
		signalOUT=new Signal(valoriRI);

		//Faccio il calcolo delle frequenze
		for(Complex c:signalOUT.values){
			c.setFase(Math.atan(c.getImmaginaria()/c.getReale()));
		}
		
		return signalOUT;
	}

}
