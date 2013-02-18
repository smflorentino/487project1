package org;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;
public class FileCleaner {
	public static void main(String[] args) {
		FileCleaner f = new FileCleaner();
		try {
			f.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void start() throws IOException {
		String fileName = JOptionPane.showInputDialog("Enter file name to clean");
		Scanner sc = new Scanner(fileName);
		FileWriter fs=  new FileWriter(fileName+"clean");
		BufferedWriter out = new BufferedWriter(fs);
		
		String line;
		while(sc.hasNextLine()) {
			line=sc.nextLine();
			if(this.isValidLine(line)) {
				out.write(line);
			}
		}
		out.close();
	}
	
	private boolean isValidLine(String s) {
		char c;
		for(int i=0;i<s.length();i++) {
			c=Character.toLowerCase(s.charAt(i));
			if(c<'a' || c>'z') {
				if(c<'0' || c>'9') {
					return false;
				}
			}
		}
		return true;
	}
}
