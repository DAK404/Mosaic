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

class BuildEncryptor
{
	String Pass, pw, Key, cpw, cKey;
	public static void main(String[] Args)
	{
		System.out.println("Enter the password and key to encrypt the build.");
		System.out.print("Password: ");
		pw=String.valueOf(console.readPassword());
		System.out.print("Security Key: ");
		Key=String.valueOf(console.readPassword());
		System.out.println("Re-Enter to confirm: ");
		System.out.print("Password: ");
		cpw=String.valueOf(console.readPassword());
		System.out.print("Security Key: ");
		cKey=String.valueOf(console.readPassword());
		if(cpw.equals(pw) & cKey.equals(Key))
		{
			Pass=pw+Key;
			Encr();
		}
		else
		{
			System.out.println("Credentials do not match.");
		}
	}
	void Encr() throws Exception
	{
		String curDir = System.getProperty("user.dir");
		String newDir=curDir+"/";
		//File to be encrypted
		FileInputStream inFile = new FileInputStream(newDir+"/LatestRelease.zip");
		//Encrypted Output of the fractal file
		FileOutputStream outFile = new FileOutputStream(newDir+"/LatestRelease.LOCK");
		/* Description:
		* Password, iv and salt should be transferred to the other end securely
		* salt is for encoding, written to file and must be transferred to the recipient securely for decryption
		*/
		byte[] salt = new byte[8];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(salt);
		FileOutputStream saltOutFile = new FileOutputStream(newDir+"salt.enc");
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
		FileOutputStream ivOutFile = new FileOutputStream(newDir+"iv.enc");
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

	}
}
