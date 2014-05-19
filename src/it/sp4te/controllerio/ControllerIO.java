package it.sp4te.controllerio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ControllerIO {
	public static void leggi() throws IOException {

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
	public static void scrivi() throws IOException {

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
