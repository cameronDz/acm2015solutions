package acm2015solutions.windowmanager;

import java.util.ArrayList;
import java.util.List;

public class Screen {

  int width;
  int height;
  List<Window> windows;

  public Screen(int width, int height) {
    this.width = width;
    this.height = height;
    this.windows = new ArrayList<Window>();
  }

  public boolean addWindow(Window newWindow) {
    // check if fit
    this.windows.add(newWindow);
    return true;
  }

  public Window identifyWindow(int x, int y) {
    for (Window w : windows) {
      if (w.covers(x, y)) {
        return w;
      }
    }
    return null;
  }

  public boolean hasHorizontalOverlap(Window window1, Window window2) {
    return ((window1.x <= window2.x) && ((window1.x + window1.width) >= window2.x))
            || ((window1.x <= (window2.x + window2.width)) && ((window1.x + window1.width) <= (window2.x + window2.width)));
  }

  public boolean hasVerticalOverlap(Window window1, Window window2) {
    return ((window1.y <= window2.y) && ((window1.y + window1.height) >= window2.y))
            || ((window1.y <= (window2.y + window2.height)) && ((window1.y + window1.height) >= (window2.y + window2.height)));
  }

  public boolean isResizeLegal(Window curWindow, Window proposedWindow) {
    if ((proposedWindow.width <= curWindow.width) && (proposedWindow.height <= curWindow.height)) {
      return true;
    }else if(((proposedWindow.x + proposedWindow.width)>this.width)||((proposedWindow.y + proposedWindow.height)>this.height)){
      return false;
    }else{
      // find with horizontal overlap
      for (Window w : windows) {
        if ((w != curWindow) && (hasHorizontalOverlap(w, proposedWindow) && hasVerticalOverlap(w, proposedWindow))) {
          return false;
        }
      }
    }
    return true;
  }

  public String doCommand(String command) {
    String[] info = command.split(" ");
    if (info[0].equals("OPEN")) {
      Window newWindow = new Window(Integer.parseInt(info[1]), Integer.parseInt(info[2]), Integer.parseInt(info[3]), Integer.parseInt(info[4]));
      if (!addWindow(newWindow)) {
        return "OPEN - window does not fit";
      }
    } else {
      Window matchingWindow = identifyWindow(Integer.parseInt(info[1]), Integer.parseInt(info[2]));
      if (matchingWindow == null) {
        return info[0] + " - no window at given position";
      }
      if (info[0].equals("CLOSE")) {
        windows.remove(matchingWindow);
      } else if (info[0].equals("RESIZE")) {
        Window proposedWindow = new Window(matchingWindow.x,matchingWindow.y,Integer.parseInt(info[3]),Integer.parseInt(info[4]));
        if (isResizeLegal(matchingWindow, proposedWindow)){
          matchingWindow.height = Integer.parseInt(info[3]);
          matchingWindow.width = Integer.parseInt(info[4]);
        }else{
          return "RESIZE - window does not fit@@"+command;
        }
      } else if (info[0].equals("MOVE")) {

      } else {
        System.out.println("UNEXPECTED COMMAND: '" + command + "'");
      }
    }
    return null;
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append(windows.size() + " window(s):\n");
    for (Window w : windows) {
      buffer.append(w.toString() + "\n");
    }
    return buffer.toString();
  }
}
