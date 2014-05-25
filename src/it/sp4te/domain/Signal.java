/**
 * Classe che modella i segnali nel discreto
 * @author Antonio Tedeschi, Davide Brutti
 *
 */

package it.sp4te.domain;

import java.util.List;

public class Signal {
	
	public Complex[] values;

	public Signal(Complex[] values) {
		this.values = values;
	}

	//Metodo per generare un segnale a partire da una lista di Double
	public Signal(List<Double> values){
		//Inizializzo le variabili
		int campioni=values.size(), i=0, j=0;
		Complex c;
		double re=0.0, im=0.0;
		campioni=campioni/2;
		Complex[] vettoreCampioni= new Complex[campioni];
		//Creo il numero complesso dalla lista in runtime
		for(double v:values){
			if(i%2==0){
				re=v;
				i++;
			}
			else{
				im=v;
				i++;
				c=new Complex(re,im);
				//Aggiungo il numero complesso al vettore dei campioni
				vettoreCampioni[j]=c;
				j++;
			}
		}
		this.values = vettoreCampioni;
	}
	
	public Signal(){
		
	}
	
	/**** GETTER & SETTER ****/
	
	public Complex[] getValues() {
		return values;
	}

	public void setValues(Complex[] values) {
		this.values = values;
	}

	public String toString(){
		String s = "";
		for(int i=0; i<this.getLength(); i++)
			s+=this.values[i].toString()+"\n";
		return s;
	}
	
	/**
	 * Metodo per ottenere la lunghezza del segnale e quindi il numero di campioni del segnale
	 * @return
	 */
	public int getLength(){
		return this.values.length;
	}
	
	public int hashCode() {
		return this.values.hashCode();
	}

	
	public boolean equals(Object o) {

		boolean equals = true;
		Signal s = (Signal)o;

		if(!(this.values.length == s.getLength()))
			return equals = false;

		for(int i=0; i<this.values.length; i++ )
			if(!this.values[i].equals(s.getValues()[i]))
				return equals = false;

		return equals;

	}
	
}
