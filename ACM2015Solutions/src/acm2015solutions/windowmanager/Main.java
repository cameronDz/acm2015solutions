package acm2015solutions.windowmanager;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author Chad Williams
 */
public class Main {
  public static void main(String[] args){
    try{
      BufferedReader inReader = new BufferedReader(new FileReader(args[0]));
      StringBuffer outBuffer = new StringBuffer();
      String[] dimensions = inReader.readLine().split(" ");
      Screen screen = new Screen(Integer.parseInt(dimensions[0]),Integer.parseInt(dimensions[1]));
      String line = inReader.readLine();
      int commandNum = 1;
      while (line != null){
        String result = screen.doCommand(line);
        if (result!=null){
          outBuffer.append("Command "+commandNum+": "+result+"\n");
        }
        line = inReader.readLine();
        commandNum++;
      }
      outBuffer.append(screen.toString());
      System.out.println(outBuffer.toString());
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
