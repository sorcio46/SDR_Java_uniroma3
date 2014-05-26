/**
 * Classe che modella i numeri Complessi e definisce le operazioni fondamentali come somma, 
 * prodotto, differenza, ecc.
 * @author Antonio, Davide Brutti
 *
 */
package it.sp4te.domain;

public class Complex {

	private double reale;
	private double immaginaria;
	private double fase;
	private double modulo;

	public Complex(double reale, double immaginario){
		this.reale = reale;
		this.immaginaria = immaginario;
	}
	
	/**** GETTER & SETTER ****/
	
	public double getReale() {
		return this.reale;
	}

	public void setReale(double reale) {
		this.reale = reale;
	}
	
	public void setReale(String s) {
		this.reale = Double.parseDouble(s);
	}

	public double getImmaginaria() {
		return this.immaginaria;
	}

	public void setImmaginaria(double immaginaria) {
		this.immaginaria = immaginaria;
	}
	
	public void setImmaginaria(String s) {
		this.immaginaria = Double.parseDouble(s);
	}

	public double getFase() {
		return this.fase;
	}

	public void setFase(double f) {
		this.fase=f;
	}
	
	public double getModulo() {
		return this.modulo;
	}

	public void setModulo(double m) {
		this.modulo=m;
	}
	
	public String toString(){
		String complex = "";
		if(this.getModulo()!=this.abs()){
		if(this.immaginaria == 0)
			complex += this.reale;
		else if(this.immaginaria < 0 && this.reale!=0) 
			complex = this.reale +" "+ this.immaginaria+" j";
		else if(this.reale==0)
			complex = this.immaginaria+" j";
		else
			complex = this.reale+" + "+ this.immaginaria+" j";
		}
		else{
			if(this.fase!=0 && this.modulo!=0){
				complex ="Complesso di fase "+this.fase;
				complex =complex+" e di modulo "+this.modulo;
				}
			else
				complex="zero";
		}
		return complex;
	}
	
	/*************** METODI BASE NUMERI COMPLESSI ***************/
	
	//Operazione MODULO
	public double abs(){
		return Math.sqrt(Math.pow(this.reale,2) + Math.pow(this.immaginaria,2));
	}
	
	//Operazione COMPLESSO CONIUGATO
	public Complex coniugato(){
		Complex c=new Complex(this.reale,-this.immaginaria);
		return c;
	}
	
	//Operazione SOMMA
	public Complex somma(Complex b){
		Complex result = new Complex((this.reale+b.getReale()),
				(this.immaginaria+b.getImmaginaria()));
		return result;
	}
	
	//Operazione DIFFERENZA
	public Complex differenza(Complex b){
		Complex result = new Complex((this.reale -  b.getReale()), 
				(this.immaginaria - b.getImmaginaria()));
		return result;
	}
	
	//Operazione PRODOTTO
	public Complex prodotto(Complex b){
		double reale = this.reale * b.getReale() - this.getImmaginaria() * b.getImmaginaria();
		double immag = this.reale * b.getImmaginaria() + b.getReale() * this.immaginaria;
		return new Complex(reale, immag);
	}
	
	//Operazione PRDOTTO SCALARE
	public Complex prodottoScalare(double s){
		return new Complex(this.reale*s, this.immaginaria*s);
	}
	
	//Operazione RECIPROCO
	public Complex reciproco(){
		double scalare = Math.pow(this.reale,2) + Math.pow(this.immaginaria,2);
		Complex result = new Complex(this.reale/scalare , this.immaginaria/scalare);
		return result;
	}
	
	//Operazione DIVISIONE
	public Complex rapporto(Complex b){
		return this.prodotto(b.reciproco());
	}
	
	//Operazione CONVERSIONE-CP
	//Utile per considerare la forma in eponenziale complesso?
	public void conversioneCP(){
		if(this.reale>0)
			this.fase=Math.atan(this.immaginaria/this.reale);
		else
			this.fase=Math.atan(this.immaginaria/this.reale)+Math.PI;
		this.modulo=Math.sqrt(Math.pow(this.reale,2) + Math.pow(this.immaginaria,2));
	}
	
	//Converte il numero complesso in esponenziale (?)
	public Complex exp(){
		return new Complex(Math.pow(Math.E, this.reale)*Math.cos(this.immaginaria), Math.pow(Math.E, this.reale)*Math.sin(this.immaginaria));
	}
	
	//Operazione POTENZA
	public Complex potenza(Complex b,int n){
		if(b.getModulo()!=b.abs())
			b.conversioneCP();
		b.setModulo(Math.pow(b.getModulo(),n));
		b.setFase(b.getFase()*n);
		return b;
	}
	
	@Override
	public int hashCode() {
		return (int) (this.reale + this.immaginaria);
	}

	@Override
	public boolean equals(Object o) {

		Complex c = (Complex)o;

		if(this.getReale() == c.getReale() && this.getImmaginaria() == c.getImmaginaria())
			return true;

		else 
			return false;

	}	 
}
