/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package naughtyclock;

import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author richard
 */
public class Main {

    public static void setReg(String aReg) {
        reg = aReg;
    }
    
           private static String reg = "Unregistered";
        static String version = "Naughty Clock V1.1 (c) Fantastic Software";

    public static String getReg() {
        return reg;
    }

    public static String getVersion() {
        return version;
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
 // Get the native look and feel class name
    String nativeLF = UIManager.getSystemLookAndFeelClassName();
    
    // Install the look and feel
    try {
        UIManager.setLookAndFeel(nativeLF);
    } catch (Exception e) {}
        
        
        
        try {

            Properties properties = Utility.getConfig();
            int runs = Integer.valueOf(properties.getProperty("runs"));

            System.out.println("Runs:" + runs);
            System.out.println("Java "+System.getProperties().getProperty("java.vm.version")+" Arch:"+System.getProperties().getProperty("os.arch"));
            runs++;
            properties.setProperty("runs", "" + runs);
            Utility.saveConfig(properties);

//            try {
//                String code = properties.getProperty("serial");
//
//                String result = Utility.checkCode(code);
//                if (!result.startsWith("NaughtyClock V1")) {
//                    throw new Exception("Wrong version");
//                }
//                setReg(result);
//            } catch (Exception exception) {
//                if (runs >= 8) {
//                    new NagDialog(null, 35);
//                }
//            }




            //   JFrame frame = new MyJFrame();


            JFrame frame = new MyJFrame();

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception, "Error!", JOptionPane.ERROR_MESSAGE);
            exception.printStackTrace();
        }
    }
}
