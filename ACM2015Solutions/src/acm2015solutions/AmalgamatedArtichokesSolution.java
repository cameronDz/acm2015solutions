/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Cameron
 */
public class AmalgamatedArtichokesSolution {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String input = "input.txt";
        try {
            // read file
            BufferedReader buf = new BufferedReader(new FileReader(input));
            ArrayList<int []> intArray = new ArrayList<>();
            // get data from input.text
            try {
                String line;
                // break up each line into an array list of int arrays
                while ((line = buf.readLine()) != null) {
                    String[] tokens = line.split(" ");
                    int[] ary = new int[tokens.length];int j = 0;
                    for (String token : tokens){
                        ary[j++] = Integer.parseInt(token); 
                    }
                    intArray.add(ary);
                }
                
                // initialize AA variables from input file
                int p = intArray.get(0)[0];
                int a = intArray.get(0)[1];
                int b = intArray.get(0)[2];
                int c = intArray.get(0)[3];
                int d = intArray.get(0)[4];
                int n = intArray.get(0)[5];
                
                // variables used in determiniting difference
                double dif = 0;
                double curHi = 0; // hi
                double curMax = 0;
                double curLo = 0;
                // loop through all k terms
                for(int k = 0; k < n; k++) {
                    double price = p * (Math.sin((a * k + b)) + Math.cos((c * k + d)) + 2);
                    System.out.println(price);
                    if( k == 0 ) {
                        curHi = price;
                        curLo = price;
                        curMax = price;
                    } else {
                        // check for new max
                        if(curMax < price ) {
                            curMax = price;
                        }
                        // check max price dif with cur diff
                        if(curMax - price > dif) {
                            curHi = curMax;
                            curLo = price;
                            dif = curHi - curLo;
                        // checks for new low
                        } 
                        if( curLo > price ) {
                            curLo = price;
                            dif = curHi - price;
                        }
                    }
                }
                System.out.print(dif);
                // write results to file
                BufferedWriter wrt = null;
                try {
                    //create a temporary file
                    String out = "output.txt";
                    File f = new File(out);

                    wrt = new BufferedWriter(new FileWriter(f));
                    wrt.write(Double.toString(dif));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        // Close the writer regardless of what happens...
                        wrt.close();
                    } catch (Exception e) {
                    }
                }
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
    }
}
