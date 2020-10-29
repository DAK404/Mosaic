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

package API;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** 
* A class to encode a string using the SHA3-256 algorithm
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  A08-Mosaic-SHA256-API    |
* | Class Name  :  SHA256                   |
* | Since       :  0.0.1, 07-August-2020    |
* | Updated on  :  0.1.0, 22-September-2020 |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
public final class SHA256 
{
    
	private final Charset UTF_8 = StandardCharsets.UTF_8;
	
    /**
    * This constructor is a stub.
    */
	public SHA256()
	{
	}
	
	/**
	* Method which gets the String in form of Bytes and instantiates the Java builtin SHA3-256 algorithm
	*/
    private final byte[] digest(byte[] input, String algorithm) 
	{
        MessageDigest md;
        try 
		{
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) 
		{
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input);
        return result;
    }

	/**
	* Method which converts the raw bytes into hex format and appending (02x)
	*/
    private final String bytesToHex(byte[] bytes) 
	{
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) 
		{
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

	/**
	* Method which will convert the plain text string to encoded string and return the value as a string.
	*
	* @param pp : Takes in the String which is to be encoded (Thank you Shreyas, please bear with me xD )
	* @return String : Returns the encoded string using SHA3-256 algorithm.
	*/
    public final String encodedString(String pp)
    {
		String algorithm = "SHA3-256";
		byte[] shaInBytes = digest(pp.getBytes(UTF_8), algorithm);
        return bytesToHex(shaInBytes);
    }
}