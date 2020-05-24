import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.spec.KeySpec;
import java.io.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

class FractalDecryption
{
  String Pass, pw, Key;
  Console console=System.console();
	public static void main(String[] Args)
	{
		      System.out.println("The early builds are encrypted and cannot be accessed without the password and key.");
      	System.out.println("To decrpyt the build, please enter the Password and the key.");
      	System.out.println("--------------------");
      	System.out.print("Password: ");
      	pw=String.valueOf(console.readPassword());
      	System.out.print("Key: ");
      	Key=String.valueOf(console.readPassword());
      	Pass=pw+Key;
      	System.out.println("Attempting to decrypt file....");
	Decr();
	}
      
	void Decr() throws Exception
	{
		try
		{
			String curDir = System.getProperty("user.dir");
			String newDir=curDir+"/";
			/* Description:
			* Read the salt
			* User must transfer salt, iv and password to the recipient securely
			*/
			FileInputStream saltFis = new FileInputStream(newDir+"salt.enc");
			byte[] salt = new byte[8];
			saltFis.read(salt);
			saltFis.close();

			// Reading the iv
			FileInputStream ivFis = new FileInputStream(newDir+"iv.enc");
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
			FileInputStream fis = new FileInputStream(newDir+"LatestRelease.LOCK");
			FileOutputStream fos = new FileOutputStream(newDir+"LatestRelease.zip");
			byte[] in = new byte[64];
			int read;
			while ((read = fis.read(in)) != -1) {
				byte[] output = cipher.update(in, 0, read);
				if (output != null)
				fos.write(output);
			}

			byte[] output = cipher.doFinal();
			if (output != null)
			fos.write(output);
			fis.close();
			fos.flush();
			fos.close();
		}
		catch(Exception e)
		{
			System.out.println("The entered password is incorrect. Please try again.");
		}
	}
}
