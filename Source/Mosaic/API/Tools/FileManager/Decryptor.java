/*
 *****************************************************
 *                                                   *
 * ! IMPORTANT ! DO NOT DELETE COMMENT ! IMPORTANT ! *
 *                                                   *
 *****************************************************
 *                                                   *
 *            THIS CODE IS RELEASE READY.            *
 *                                                   *
 *      THIS CODE HAS BEEN TESTED, REVIEWED AND      *
 *      REVISED. THIS CODE HAS NO KNOWN ISSUES,      *
 *      HENCE IT IS CONSIDERED AS RELEASE READY      *
 *                                                   *
 *****************************************************
 */

package Mosaic.API.Tools.FileManager;

import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
* A class to decrypt any encrypted document
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  BA1-Mosaic-DCRPT-FMGR    |
* | Class Name  :  Decryptor                |
* | Since       :  0.0.1, 07-May-2017       |
* | Updated on  :  0.6.3, 01-October-2020   |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
final class Decryptor {
    private String nm, Pass, pw, Key;
    private String newDir = "";

    Console console = System.console();

    /**
    * Constructor to initialize the username and the current directory path
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    protected void dcr(String dir) throws Exception {
        newDir=dir;
        welcome();
    }

    /**
    * Shows the welcome screen, receiving the credentials for the file to be decrypted
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    private void welcome() throws Exception {
        System.out.println("DISCLAIMER: FILE CANNOT BE RECOVERED IF THE CREDENTIALS ARE LOST.\n");
        System.out.print("Enter the name of the file to be Decrypted: ");
        nm = console.readLine();
        File f = new File(newDir + nm + ".LOCK");
        if (f.exists() == false) {
            System.out.println("The file cannot be found. Please try again.");
            console.readLine();
            return;
        } else {
            System.out.println("Enter the password and key to decrypt the file.");
            System.out.print("Password: ");
            pw = new Mosaic.API.SHA256().encodedString(String.valueOf(console.readPassword()));
            System.out.print("Security Key: ");
            Key = new Mosaic.API.SHA256().encodedString(String.valueOf(console.readPassword()));
            Pass = pw + Key;
            System.out.println("Attempting to decrypt file....");
            Decr();
        }
    }

    /**
    * Method which contains the logic to decrypt the files
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    void Decr() throws Exception {
        try
        {
            FileInputStream saltFis = new FileInputStream(newDir + nm +".salt");
            byte[] salt = new byte[8];
            saltFis.read(salt);
            saltFis.close();

            // Reading the iv
            FileInputStream ivFis = new FileInputStream(newDir + nm + ".iv");
            byte[] iv = new byte[16];
            ivFis.read(iv);
            ivFis.close();

            SecretKeyFactory factory = SecretKeyFactory
                .getInstance("PBKDF2WithHmacSHA1");
            KeySpec keySpec = new PBEKeySpec(Pass.toCharArray(), salt, 65536,
                256);
            SecretKey tmp = factory.generateSecret(keySpec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

            // File decryption, Decrypt and save the file
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
            FileInputStream fis = new FileInputStream(newDir + nm + ".LOCK");
            FileOutputStream fos = new FileOutputStream(newDir + nm);
            byte[] in = new byte[64];
            int read;
            while ((read = fis.read( in )) != -1) {
                byte[] output = cipher.update( in , 0, read);
                if (output != null)
                    fos.write(output);
            }

            byte[] output = cipher.doFinal();
            if (output != null)
                fos.write(output);
            fis.close();
            fos.flush();
            fos.close();
            deleteEnc();
            System.gc();
            System.out.println("File has been successfully decrypted. It can be found at the current directory with the name : "+nm);
        } catch (Exception e) {
            System.out.println("The entered password is incorrect. Please try again.");
            console.readLine();
        }
    }

    /**
    * Method to delete the encoded file right after the decryption
    */
    private void deleteEnc()
    {
        //delete the encrypted files once decrypted.
        String [] fileNames = {
            newDir+nm+".LOCK",
            newDir+nm+".iv",
            newDir+nm+".salt"
        };

        for(int i=0; i<fileNames.length;++i)
        {
            File f=new File(fileNames[i]);
            f.delete();
        }
        return;
    }
}