package it.sp4te.controllerio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import it.sp4te.domain.Signal;

public class ControllerIO {
	
	//Metodo per la lettura di un Segnale da file a partire dal percorso del file
	public static Signal leggi(String pathIn, int limiteCampioni) throws IOException {
        Scanner s = null;
        Double val;
        List<Double> campioni=new ArrayList<Double>();
        int heapSizeLimiter = 0;
        //Genero dal file una lista di Stringhe
        try {
        	System.out.println("OK1");
        	FileReader fileReader= new FileReader(pathIn);
        	System.out.println("OK2");
            s = new Scanner(new BufferedReader(fileReader));
            System.out.println("OK3");
            limiteCampioni=limiteCampioni/2;
            while(s.hasNextLine() && heapSizeLimiter<limiteCampioni){
            	val=Double.parseDouble(s.next());
            	campioni.add(val);
            	heapSizeLimiter++;
            }
            s.close();
        } 
        catch(Exception e){
        	s.close();
        }

        //Creo il segnale a partire dall ArrayList dei campioni
        Signal letto=new Signal(campioni);
        
        return letto;
    }
	
	//Metodo per la scrittura su file
	public static void scrivi(String pathOut, double[] values) throws IOException {
		BufferedWriter b = null;
		int i=0;
		String s;
        try{
        	File file=new File(pathOut);
        	FileWriter fileWriter= new FileWriter(file);
        	b=new BufferedWriter(fileWriter);
        	for(double val:values){
        		s=String.valueOf(val);
        		if(i%2==0){
        			b.write(s);
        			b.write("	");
        			i++;
        		}
        		else{
        			b.write(s);
        			b.write("\n");
        			i++;
        		}
        	}
        	
        }
        catch(Exception e){
        	b.close();
        }
    }
}
