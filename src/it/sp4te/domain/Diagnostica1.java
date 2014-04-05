package it.sp4te.domain;

public class Diagnostica1 {
	public static void main(String[] args){
		double a1=4;
		double b1=1;
		double a2=1;
		double b2=2;
		//esempio di come creare due numeri complessi e applicare il metodo somma
		Complex a = new Complex(a1, b1);
		Complex b = new Complex(a2, b2);
		Complex e = new Complex(1, 1);
		Complex f = new Complex(2, 2);
		
		Complex c = a.somma(b);
		Complex d = e.somma(f);
		
		System.out.println(c.toString());
		System.out.println(d.toString());
		
		c.conversioneCP();
		d.conversioneCP();
		System.out.println(c.toString());
		System.out.println(d.toString());
	}
}
