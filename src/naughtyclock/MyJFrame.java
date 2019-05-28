/*
 * MyJFrame.java
 *
 * Created on December 3, 2007, 5:14 PM
 */
package naughtyclock;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.ToolTipManager;


/**
 *
 * @author  richard
 */
public class MyJFrame extends javax.swing.JFrame {

    public int elapsedTime = 0;
    private boolean halfSecond = true;
    private boolean tensFlag = false;
    private int spaceAtTop,  heightofButtons;
  //  Application osx = new DefaultApplication();
    BufferedImage icon = new BufferedImage(128, 128, BufferedImage.TYPE_4BYTE_ABGR);
    Graphics2D ig = icon.createGraphics();

    private void update() {
        if (elapsedTime < 0) {
            elapsedTime = 0;
            countDown.setSelected(false);
            toFront();
            Toolkit.getDefaultToolkit().beep();
           //if (osx.isMac()) {
          //      osx.requestUserAttention(osx.REQUEST_USER_ATTENTION_TYPE_CRITICAL);
         //   } else {
                setAlwaysOnTop(true);
                setAlwaysOnTop(onTopButton.isSelected());
          //  }
        }
        String minutes = "" + elapsedTime / 60;
        String seconds = "" + elapsedTime % 60;
        if (minutes.length() < 2) {
            minutes = "0" + minutes;
        }
        if (seconds.length() < 2) {
            seconds = "0" + seconds;
        }
        timeDisplay.setText(minutes + ":" + seconds);

        //if (osx.isMac()) { // double buffering not needed on mac
        //    timeDisplay.repaint();
        //} else {
        BufferStrategy strategy = timeDisplay.getBufferStrategy();
        Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();
        graphics.clearRect(0, 0, getWidth(), getHeight());
        timeDisplay.paint(graphics);

//        if (osx.isMac() && getWidth() > 300) {
//            graphics.setColor(Color.GRAY);
//            graphics.drawLine(getWidth() - 12, getHeight() - spaceAtTop - 2, getWidth() - 2, getHeight() - spaceAtTop - 11);
//            graphics.drawLine(getWidth() - 8, getHeight() - spaceAtTop - 2, getWidth() - 2, getHeight() - spaceAtTop - 7);
//            graphics.drawLine(getWidth() - 3, getHeight() - spaceAtTop - 2, getWidth() - 2, getHeight() - spaceAtTop - 3);
//        }

        // System.out.println("getWidth="+getWidth()+" Height="+jPanel1.getHeight());

        strategy.show();
        //}

        ig.setFont(new Font("Arial", Font.BOLD, 50));
        ig.setColor(Color.WHITE);
        ig.fillRect(0, 30, 127, 44);
        ig.setColor(Color.BLACK);
        ig.drawRect(0, 30, 127, 44);
        ig.drawString(minutes + ":" + seconds, 0, 70);
    //    osx.setApplicationIconImage(icon);



    //timeDisplay.repaint();
    }

    private void flash() {
        timeDisplay.setText("");
        //  if (osx.isMac()) { // double buffering not needed on mac
        //      timeDisplay.repaint();
        //  } else {

        BufferStrategy strategy = timeDisplay.getBufferStrategy();
        Graphics graphics = strategy.getDrawGraphics();
        graphics.clearRect(0, 0, timeDisplay.getWidth(), timeDisplay.getHeight());
        // timeDisplay.paint(graphics);
        strategy.show();
    //   }
    }

    /** Creates new form MyJFrame */
    public MyJFrame() {
        getRootPane().putClientProperty("Window.style", "small");
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(MyJFrame.class.getResource("NaughtyClock.png")));
        // 
        // Show tool tips quickly
        ToolTipManager.sharedInstance().setInitialDelay(200);

        int frameHeight = getHeight();
        int canvasHeight = jPanel1.getHeight();
        spaceAtTop = frameHeight - canvasHeight;
        heightofButtons = jPanel2.getHeight();
        System.out.println("frame height: " + frameHeight + " canvasheight: " + canvasHeight + " space at top: " + spaceAtTop + "height of buttons: " + heightofButtons);

        //show();
        setVisible(true);
        setAlwaysOnTop(true);

        ActionListener tickPerformer = new ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (!halfSecond) {
                    if (flashButton.isSelected()) {
                        flash();
                    }
                    halfSecond = true;
                    return;
                }
                halfSecond = false;
                if (countUp.isSelected()) {
                    elapsedTime++;
                    tensFlag = false;
                } else if (countDown.isSelected()) {
                    elapsedTime--;
                    tensFlag = false;
                }
                update();
            }
        };
        /*    
        ActionListener flashPerformer = new ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (goButton.isSelected() && naughtyButton.isSelected()){
        if(timeDisplay.getForeground().equals(Color.WHITE))
        timeDisplay.setForeground(Color.RED);
        else
        timeDisplay.setForeground(Color.WHITE);
        }else{
        timeDisplay.setForeground(Color.BLACK);
        }
        }
        };
         */


        addComponentListener(new ComponentAdapter() {

            public void componentResized(ComponentEvent event) {
                // int spaceAtTop = 50;
                if (getWidth() > 300) {

                    jPanel2.setVisible(true);


                    setSize(getWidth(), (int) (getWidth() / 3.4) + spaceAtTop);
                    //     timeDisplay.setFont(new Font("",0,(int)((getHeight()-spaceAtTop)*1.2)));
                    timeDisplay.setFont(new Font("Arial", Font.BOLD, (int) ((getWidth()) / 2.6)));

                    //   setSize((int)((getHeight()-50)*3.5),getHeight());
                    timeDisplay.setVerticalPosition((int) ((getHeight() - spaceAtTop) * 0.97));
                } else {
                    jPanel2.setVisible(false);


                    setSize(getWidth(), (int) (getWidth() / 3.4) + (spaceAtTop - heightofButtons));

                    timeDisplay.setFont(new Font("Arial", Font.BOLD, (int) ((getWidth()) / 2.6)));

                    timeDisplay.setVerticalPosition((int) ((getHeight() - (spaceAtTop - heightofButtons)) * 0.97));


                }

                update();


            //System.out.println(getHeight());
            }
        });


        timeDisplay.setFont(new Font("", 0, 100));
        new Timer(500, tickPerformer).start();

        //  new Timer(500, flashPerformer).start();



        setSize(500, 500);
        timeDisplay.setBackground(Color.WHITE);
        // if (!osx.isMac()) {
        timeDisplay.createBufferStrategy(3);
        // }
        System.out.println(timeDisplay.getBufferStrategy());
        // jPanel1.setFocusable(false);
        timeDisplay.setFocusable(false);
        requestFocusInWindow();

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        timeDisplay = new naughtyclock.clockCanvas();
        jPanel2 = new javax.swing.JPanel();
        countUp = new javax.swing.JToggleButton(){       public Point getToolTipLocation(MouseEvent e) {         return new Point(20, 0);       }     };
        countDown = new javax.swing.JToggleButton(){
            public Point getToolTipLocation(MouseEvent e) {
                return new Point(20, 0);
            }
        };
        forwardOne = new javax.swing.JButton(){       public Point getToolTipLocation(MouseEvent e) {         return new Point(20, 0);       }     };
        backOne = new javax.swing.JButton(){       public Point getToolTipLocation(MouseEvent e) {         return new Point(20, 0);       }     };
        flashButton = new javax.swing.JToggleButton(){       public Point getToolTipLocation(MouseEvent e) {         return new Point(20, 0);       }     };
        onTopButton = new javax.swing.JToggleButton(){       public Point getToolTipLocation(MouseEvent e) {         return new Point(20, 0);       }     };
        jButton1 = new javax.swing.JButton(){       public Point getToolTipLocation(MouseEvent e) {         return new Point(20, 0);       }     };

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Naughty Clock");
        setLocationByPlatform(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        timeDisplay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                timeDisplayMouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(timeDisplay, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, timeDisplay, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
        );

        jPanel2.setFocusable(false);

        countUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/naughtyclock/uparrowred.png"))); // NOI18N
        countUp.setToolTipText("Count up");
        countUp.setFocusable(false);
        countUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                countUpActionPerformed(evt);
            }
        });

        countDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/naughtyclock/downarrowgreen.png"))); // NOI18N
        countDown.setToolTipText("Count down");
        countDown.setFocusable(false);
        countDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                countDownActionPerformed(evt);
            }
        });

        forwardOne.setText("+1");
        forwardOne.setToolTipText("Add one minute");
        forwardOne.setFocusable(false);
        forwardOne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardOneActionPerformed(evt);
            }
        });

        backOne.setText("-1");
        backOne.setToolTipText("Remove one minute");
        backOne.setFocusable(false);
        backOne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backOneActionPerformed(evt);
            }
        });

        flashButton.setText("Flash");
        flashButton.setToolTipText("Flash clock");
        flashButton.setFocusable(false);

        onTopButton.setSelected(true);
        onTopButton.setText("On Top");
        onTopButton.setToolTipText("Keep window on top of other windows");
        onTopButton.setFocusable(false);
        onTopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onTopButtonActionPerformed(evt);
            }
        });

        jButton1.setText("About");
        jButton1.setToolTipText("About NaughtyClock");
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        countUp.putClientProperty("JButton.buttonType", "gradient");
        countDown.putClientProperty("JButton.buttonType", "gradient");
        forwardOne.putClientProperty("JButton.buttonType", "gradient");
        backOne.putClientProperty("JButton.buttonType", "gradient");
        flashButton.putClientProperty("JButton.buttonType", "gradient");
        onTopButton.putClientProperty("JButton.buttonType", "gradient");
        jButton1.putClientProperty("JButton.buttonType", "gradient");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(countUp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, 0)
                .add(countDown, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(2, 2, 2)
                .add(forwardOne, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 53, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(backOne, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(flashButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(onTopButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton1)
                .addContainerGap(395, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(countUp)
            .add(countDown)
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(backOne, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(forwardOne, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(onTopButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(flashButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void countUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_countUpActionPerformed
        countDown.setSelected(false);
        if (countUp.isSelected()) {
            //  flashButton.setSelected(true);
            timeDisplay.setForeground(Color.RED);
        } else {
            timeDisplay.setForeground(Color.BLACK);
        // flashButton.setSelected(false);
        }

}//GEN-LAST:event_countUpActionPerformed

    private void onTopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onTopButtonActionPerformed
        setAlwaysOnTop(onTopButton.isSelected());
}//GEN-LAST:event_onTopButtonActionPerformed

    private void countDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_countDownActionPerformed
        countUp.setSelected(false);
        if (countDown.isSelected()) {
            // flashButton.setSelected(false);
            timeDisplay.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_countDownActionPerformed

    private void forwardOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardOneActionPerformed
        int seconds = elapsedTime % 60;
        elapsedTime += (60 - seconds);
        update();

    }//GEN-LAST:event_forwardOneActionPerformed

    private void backOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backOneActionPerformed
        int seconds = elapsedTime % 60;
        if (seconds == 0) {
            seconds = 60;
        }
        elapsedTime -= seconds;
        update();
    }//GEN-LAST:event_backOneActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JOptionPane.showMessageDialog(this, Main.getVersion() + "\n" + Main.getReg(), "About", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        System.out.println("exit");
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed


        int key = evt.getKeyCode();

        switch (key) {
            case KeyEvent.VK_UP:
                countUp.doClick();
                break;
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_DOWN:
                countDown.doClick();
                break;
            case KeyEvent.VK_EQUALS:
                forwardOne.doClick();
                break;
            case KeyEvent.VK_MINUS:
                backOne.doClick();
                break;
        }
        if (key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) {
            int n = key - KeyEvent.VK_0;
            if (tensFlag) {
                elapsedTime = elapsedTime * 10 + n * 60;
                tensFlag = false;
            } else {
                elapsedTime = 60 * n;

                tensFlag = true;
            }
            update();
        }

    }//GEN-LAST:event_formKeyPressed

private void timeDisplayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_timeDisplayMouseClicked
    countDown.doClick();
}//GEN-LAST:event_timeDisplayMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MyJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backOne;
    private javax.swing.JToggleButton countDown;
    private javax.swing.JToggleButton countUp;
    private javax.swing.JToggleButton flashButton;
    private javax.swing.JButton forwardOne;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToggleButton onTopButton;
    private naughtyclock.clockCanvas timeDisplay;
    // End of variables declaration//GEN-END:variables
}
