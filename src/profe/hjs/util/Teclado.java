// Ejemplo de lectura de un car�cter, una l�nea, un n�mero completo

package profe.hjs.util;
import java.io.*;

public class Teclado {

	public static String leeString() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s=null;
		try {
			s = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return s;
	}
	
	public static int leeNumero() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s=null;
		try {
			s = br.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		int i=Integer.parseInt(s);
		return i;
	}
	
		
}