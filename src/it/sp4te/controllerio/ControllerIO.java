package it.sp4te.controllerio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import it.sp4te.domain.Signal;

public class ControllerIO {
	
	//Metodo per la lettura di un Segnale da file a partire dal percorso del file
	public static Signal leggi(String pathIn) throws IOException {
        Scanner s = null;
        Double val;

        List<Double> campioni=new ArrayList<Double>();
        
        //Genero dal file una lista di Stringhe
        try {
        	File file=new File(pathIn);
        	FileReader fileReader= new FileReader(file);
            s = new Scanner(new BufferedReader(fileReader));
            while(s.hasNextLine()){
            	val=Double.parseDouble(s.next());
            	campioni.add(val);
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
        Scanner s = null;
        try {
            s = new Scanner(new BufferedReader(new FileReader("nomefile.txt")));
            s.useDelimiter("delimitatore_da_inserire");
            
            while (s.hasNext()) {
                System.out.println(s.next());
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
}
