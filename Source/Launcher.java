/*
 *****************************************************
 *                                                   *
 * ! IMPORTANT ! DO NOT DELETE COMMENT ! IMPORTANT ! *
 *                                                   *
 *****************************************************
 *                                                   *
 *            THIS CODE IS RELEASE READY.            *
 *                                                   *
 *       THIS CODE HAS BEEN TESTED HEAVILY AND       *
 *       CONSIDERED STABLE. THIS MODULE HAS NO       *
 *       KNOWN ISSUES. CONSIDERED RELEASE READY      *
 *                                                   *
 *****************************************************
 */

import java.io.*;
import Core.Boot;

public class Launcher
{
	public static void main(String[] Args)throws Exception
	{
		System.gc();
		System.out.println("Booting Program: Mosaic 2.0");
		Boot a=new Boot(true);
		a.BootScript();
	}
}
