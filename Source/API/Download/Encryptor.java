package API.Download;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.io.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

class Encryptor {
    private String nm, Pass, pw, Key, cpw, cKey;
    private static String Username = "";
    String newDir = "";
    Console console = System.console();

    protected void encr(String U) throws Exception {
        Username = U;
        Encryptor en = new Encryptor();
        en.welcome();
    }
    private void welcome() throws Exception {

        System.out.println("DISCLAIMER: FILE CANNOT BE RECOVERED IF THE CREDENTIALS ARE LOST.\n");
        System.out.print("Enter the name of the file to be encrypted (with extension): ");
        nm = console.readLine();
        newDir = "./Users/" + Username + "/Files/" + nm + "/";

        File f = new File(newDir + nm);
        if (f.exists() == false) {
            System.out.println("The file cannot be found. Please try again.");
            console.readLine();
            return;
        } else {
            System.out.println("Enter the password and key to encrypt the file.");
            System.out.print("Password: ");
            pw = String.valueOf(console.readPassword());
            System.out.print("Security Key: ");
            Key = String.valueOf(console.readPassword());
            System.out.println("Re-Enter to confirm: ");
            System.out.print("Password: ");
            cpw = String.valueOf(console.readPassword());
            System.out.print("Security Key: ");
            cKey = String.valueOf(console.readPassword());
            if (cpw.equals(pw) & cKey.equals(Key)) {
                Pass = pw + Key;
                Encr();
            } else {
                System.out.println("Credentials do not match.");
                console.readLine();
            }
        }
    }
    void Encr() throws Exception {
        String curDir = System.getProperty("user.dir");

        //File to be encrypted
        FileInputStream inFile = new FileInputStream(newDir + nm);
        //Encrypted Output of the fractal file
        FileOutputStream outFile = new FileOutputStream(newDir + nm + ".LOCK");
        /* Description:
         * Password, iv and salt should be transferred to the other end securely
         * salt is for encoding, written to file and must be transferred to the recipient securely for decryption
         */
        byte[] salt = new byte[8];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        FileOutputStream saltOutFile = new FileOutputStream(newDir + "salt.enc");
        saltOutFile.write(salt);
        saltOutFile.close();
        SecretKeyFactory factory = SecretKeyFactory
            .getInstance("PBKDF2WithHmacSHA1");
        KeySpec keySpec = new PBEKeySpec(Pass.toCharArray(), salt, 65536,
            256);
        SecretKey secretKey = factory.generateSecret(keySpec);
        SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();
        /*Description
         * iv adds randomness to the text and just makes the mechanism more secure
         * used while initializing the cipher
         */
        FileOutputStream ivOutFile = new FileOutputStream(newDir + "iv.enc");
        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        ivOutFile.write(iv);
        ivOutFile.close();

        //file encryption
        byte[] input = new byte[64];
        int bytesRead;

        while ((bytesRead = inFile.read(input)) != -1) {
            byte[] output = cipher.update(input, 0, bytesRead);
            if (output != null)
                outFile.write(output);
        }

        byte[] output = cipher.doFinal();
        if (output != null)
            outFile.write(output);
        inFile.close();
        outFile.flush();
        outFile.close();
        File del = new File(newDir + nm);
        del.delete();
        console.readLine("File Is Encrypted Successfully! Press Enter to Continue.");
    }
}