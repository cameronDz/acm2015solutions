package acm2015solutions.windowmanager;

import java.util.ArrayList;
import java.util.List;

public class Screen {

   int width;
   int height;
   List<Window> windows;

   public Screen(int width, int height) {
      this.width = width; // zero index
      this.height = height; // zero index
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
      // Check window 2 overlap left side
      boolean overlapLeft = (window1.x <= window2.x) && ((window1.x + window1.width) >= window2.x);
      boolean overlapRight = (window1.x <= (window2.x + window2.width)) && (window1.x >= window2.x);
      return overlapLeft || overlapRight;
   }

   public boolean hasVerticalOverlap(Window window1, Window window2) {
      boolean overlapTop = (window1.y <= window2.y) && ((window1.y + window1.height) >= window2.y);
      boolean overlapBottom = (window1.y <= (window2.y + window2.height)) && (window1.y >= window2.y);
      return overlapTop || overlapBottom;
   }

   public boolean isResizeLegal(Window curWindow, Window proposedWindow) {
      if ((proposedWindow.width <= curWindow.width) && (proposedWindow.height <= curWindow.height)) {
         return true;
      } else if (((proposedWindow.x + proposedWindow.width) > this.width) || ((proposedWindow.y + proposedWindow.height) > this.height)) {
         return false;
      } else {
         for (Window w : windows) {
            if ((w != curWindow) && (hasHorizontalOverlap(w, proposedWindow) && hasVerticalOverlap(w, proposedWindow))) {
               return false;
            }
         }
      }
      return true;
   }

   // Only one of dx/dy can be different than zero and must be either positive or negative 1
   public boolean canPush(int dx, int dy, Window passedWindow) {
      //Check if hits wall
      if ((dx > 0) && ((passedWindow.x + passedWindow.width + 1) > this.width)) {
         return false;
      } else if ((dx < 0) && ((passedWindow.x - 1) < 0)) {
         return false;
      } else if ((dy > 0) && ((passedWindow.y + passedWindow.height + 1) > this.height)) {
         return false;
      } else if ((dy < 0) && ((passedWindow.y + passedWindow.height - 1) < 0)) {
         return false;
      } else {
         Window potentialWindow = new Window(passedWindow.x + dx, passedWindow.y + dy, passedWindow.width, passedWindow.height);
         // Check that all touched windows can be pushed
         for (Window w : windows) {
            if ((w != passedWindow) && (hasHorizontalOverlap(w, potentialWindow) && hasVerticalOverlap(w, potentialWindow))) {
               // recursively see if touched window can be pushed
               if (!canPush(dx, dy, w)) {
                  return false;
               }
            }
         }
      }
      return true;
   }

   // Only one of dx/dy can be different than zero and must be either positive or negative 1  
   // if successful returns 1 otherwise returns zero
   public boolean pushOne(int dx, int dy, Window passedWindow) {
      if (!canPush(dx, dy, passedWindow)) {
         return false;
      } else {
         passedWindow.x += dx;
         passedWindow.y += dy;
         for (Window w : windows) {
            if ((w != passedWindow) && (hasHorizontalOverlap(w, passedWindow) && hasVerticalOverlap(w, passedWindow))) {
               pushOne(dx, dy, w);
            }
         }
         return true;
      }
   }

   public int push(int dx, int dy, Window passedWindow) {
      int dActual = 0;
      if (Math.abs(dx) > 0) {
         int stepX = dx / Math.abs(dx);  // either positive or negative 1
         for (int i = 0; i < Math.abs(dx); i++) {
            if (pushOne(stepX, 0, passedWindow)) {
               dActual++;
            } else {
               return dActual;
            }
         }
      }else if (Math.abs(dy) > 0) {
         int stepY = dy / Math.abs(dy);  // either positive or negative 1
         for (int i = 0; i < Math.abs(dy); i++) {
            if (pushOne(0, stepY, passedWindow)) {
               dActual++;
            } else {
               return dActual;
            }
         }
      }
      return dActual;
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
            Window proposedWindow = new Window(matchingWindow.x, matchingWindow.y, Integer.parseInt(info[3]), Integer.parseInt(info[4]));
            if (isResizeLegal(matchingWindow, proposedWindow)) {
               matchingWindow.height = Integer.parseInt(info[3]);
               matchingWindow.width = Integer.parseInt(info[4]);
            } else {
               return "RESIZE - window does not fit";
            }
         } else if (info[0].equals("MOVE")) {
            int dx = Integer.parseInt(info[3]);
            int dy = Integer.parseInt(info[4]);
            int dActual = push(dx,dy,matchingWindow);
            if (dActual < Math.max(Math.abs(dx),Math.abs(dy))){
               return "MOVE - moved "+dActual+" instead of "+Math.max(Math.abs(dx),Math.abs(dy));
            }
         } else {
            //System.out.println("UNEXPECTED COMMAND: '" + command + "'");
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
