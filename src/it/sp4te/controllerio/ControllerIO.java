package it.sp4te.controllerio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import it.sp4te.domain.Signal;

public class ControllerIO {
	
	//Metodo per la lettura di un Segnale da file a partire dal percorso del file
	public static Signal leggi(String pathIn, int limiteCampioni) throws IOException {
        Scanner s = null;
        double val;
        limiteCampioni=limiteCampioni*2;
        Double[] campioni=new Double[limiteCampioni];
        int heapSizeLimiter = 0;
        //Genero dal file una lista di Stringhe
        try {
        	FileReader fileReader= new FileReader(pathIn);
            s = new Scanner(new BufferedReader(fileReader));
            while(s.hasNextLine() && heapSizeLimiter<limiteCampioni){
            	val=Double.parseDouble(s.next());
            	campioni[heapSizeLimiter]=val;
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
	
	//Metodo per la scrittura su file di un segnale
	public static void scrivi(String pathOut, double[] values) throws IOException {
		int i=0;
		String s;
        try{
        	//Creo il file
        	File file=new File(pathOut);
        	//Controllo se esiste, nel caso ci sia lo cancella e lo ricrea
			if (!file.exists())
				file.createNewFile();
			else{
				file.delete();
				file=new File(pathOut);
			}
			//Inizializzo il FileWriter e il BufferedWriter
			FileWriter fileWriter= new FileWriter(file.getAbsolutePath());
        	BufferedWriter b=new BufferedWriter(fileWriter);
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
        	b.close();
        	System.out.println("Segnale salvato con successo sul file:");
        	System.out.println(file.getAbsolutePath());
        }
        catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	//Metodo per la scrittura su file delle fasi di un segnale
	public static void scriviFasi(String pathOut, double[] values) throws IOException {
		@SuppressWarnings("unused")
		int i=0;
		String s;
        try{
        	//Creo il file
        	File file=new File(pathOut);
        	//Controllo se esiste, nel caso ci sia lo cancella e lo ricrea
			if (!file.exists())
				file.createNewFile();
			else{
				file.delete();
				file=new File(pathOut);
			}
			//Inizializzo il FileWriter e il BufferedWriter
			FileWriter fileWriter= new FileWriter(file.getAbsolutePath());
        	BufferedWriter b=new BufferedWriter(fileWriter);
        	for(double val:values){
        		s=String.valueOf(val);
        		b.write(s);
        		b.write("\n");
        		i++;
        	}
        	b.close();
        	System.out.println("Segnale salvato con successo sul file:");
        	System.out.println(file.getAbsolutePath());
        }
        catch (IOException e) {
			e.printStackTrace();
		}
    }
}
