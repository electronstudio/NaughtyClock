/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package naughtyclock;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author richard
 */
public class clockCanvas extends Canvas{
    
   private String text="00:00";
   private int verticalPosition=50;
   
   
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void paint(Graphics g){
     
        Graphics2D h = (Graphics2D) g;
      //  h.clearRect(0, 0, getWidth(), getHeight()); 
        h.setRenderingHint(RenderingHints.KEY_ANTIALIASING,   // Anti-alias
			    RenderingHints.VALUE_ANTIALIAS_ON);
        h.drawString(text, 0,getVerticalPosition());
     
      
        //g.drawLine(0, 0, 500, 0);
        
    }

    public int getVerticalPosition() {
        return verticalPosition;
    }

    public void setVerticalPosition(int verticalPosition) {
        this.verticalPosition = verticalPosition;
    }
    
}
