/**
 * Classe che modella i segnali nel discreto
 * @author Antonio Tedeschi
 *
 */

package it.sp4te.domain;

public class Signal {
	
	public Complex[] values;

	public Signal(Complex[] values) {
		this.values = values;
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
