/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package naughtyclock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author richard
 */
public class Utility {

//    public static String checkCode(String regCode) throws IOException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, InvalidKeySpecException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException {
//        regCode = regCode.replaceAll("\n","");
//        regCode = regCode.replaceAll("\r","");
//        System.out.println(regCode);
//
//        byte[] encryptedData = new sun.misc.BASE64Decoder().decodeBuffer(regCode);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        //PublicKey publicKey = keyFactory.generatePublic(new RSAPublicKeySpec(new BigInteger("108162489888887285726359071719536636443950516440407147069856454111661454961420714081884160520345928650309534172228851518299001594324566665546810920078666907228435117260188857657556427627150033403842407021470439449466516121186385812176875045201058627033826870926928673170245420871116142784979633581215484196663"), new BigInteger("65537")));
//         PublicKey publicKey = keyFactory.generatePublic(new RSAPublicKeySpec(new BigInteger("1696308979423718858527789972788320112738283306120813395128211018345015568926972947778939776303752591127353469880645224191879913785209906786786295851966381632193837927721015958980119128549722071797734419282894159506413570728965892997162736420135504698250314810880800708492864734563152513847769273808035457680052085477362104487955039423359690291311522663008111712723457750708996820314653755573926747649527147541803806968831378317020044910407350489812210736277441537"), new BigInteger("65537")));
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.DECRYPT_MODE, publicKey);
//        byte[] decryptedData = cipher.doFinal(encryptedData);
//        String s = new String(decryptedData, "UTF-8");
//        return s;
//    }

    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            
            Properties properties = getConfig();
            int runs = Integer.valueOf(properties.getProperty("runs"));

            System.out.println("Runs:"+runs);
            runs++;
            properties.setProperty("runs", ""+runs);
            saveConfig(properties);

            generateKeys();


           


        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

    public static Properties getConfig() throws FileNotFoundException, IOException {
        File file = new File(System.getProperty("user.home"), ".naughtyclock");
        Properties properties = new Properties();
        if (file.exists()) {
            FileInputStream i = new FileInputStream(file);
            properties.loadFromXML(i);
            i.close();
        } else {
            properties.setProperty("runs", "0");
            saveConfig(properties);
        }


        return properties;
    }

    public static void saveConfig(Properties properties) throws FileNotFoundException, IOException {
    
            File file = new File(System.getProperty("user.home"), ".naughtyclock");
            if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
                try {
                    Process p = Runtime.getRuntime().exec("attrib -H \"" + file.getPath() + "\"");
                    p.waitFor();
                } catch (Exception x) {
                    System.out.println(x.toString());
                }
            }
            FileOutputStream o = new FileOutputStream(file);
            properties.storeToXML(o, null);
            o.close();
            if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
                try {
                    Runtime.getRuntime().exec("attrib +H \"" + file.getPath() + "\"");
                } catch (Exception x) {
                    System.out.println(x.toString());
                }
            }
      
        
    }

    public static void generateKeys() throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1536);
        KeyPair keyPair = keyGen.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        //  System.out.println(privateKey);
        //  System.out.println(publicKey);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec keySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);

        System.out.println("PRIV MODULUS:" + keySpec.getModulus());
        System.out.println("PRIV EXPONENT:" + keySpec.getPrivateExponent());

        RSAPublicKeySpec keySpec2 = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        System.out.println("PUB MODULUS:" + keySpec2.getModulus());
        System.out.println("PUB EXPONENT:" + keySpec2.getPublicExponent());
    }
}
