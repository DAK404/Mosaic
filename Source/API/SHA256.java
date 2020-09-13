package API;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.*;

public final class SHA256 {

    private final Charset UTF_8 = StandardCharsets.UTF_8;

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

    private final String bytesToHex(byte[] bytes) 
	{
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) 
		{
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public final String encodedString(String pp)
    {
		String algorithm = "SHA3-256";
		byte[] shaInBytes = digest(pp.getBytes(UTF_8), algorithm);
        return bytesToHex(shaInBytes);
    }
}