package it.sp4te.controllerio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import it.sp4te.domain.Signal;

public class ControllerIO {
	public static Signal leggi(String pathIn) throws IOException {
		Signal letto=new Signal();
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
        return letto;
    }
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
