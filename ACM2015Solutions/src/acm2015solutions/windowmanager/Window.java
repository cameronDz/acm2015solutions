package acm2015solutions.windowmanager;

public class Window {
  int x;
  int y;
  int width;
  int height;
  public Window(int x, int y, int width, int height){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }
  
  public boolean covers(int xPos, int yPos){
    return ((this.x <= xPos)&&(xPos<=(this.x+this.width))&&
        (this.y <= yPos)&&(yPos<=(this.y+this.height)));
  }
  
  public String toString(){
    return x+" "+y+" "+width+" "+height;
  }
}
