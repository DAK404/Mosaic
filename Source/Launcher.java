/*
*****************************************************
*# PROTOTYPE CODE/CONCEPT # PROTOTYPE CODE/CONCEPT #*
*****************************************************
*                                                   *
* ! IMPORTANT ! DO NOT DELETE COMMENT ! IMPORTANT ! *
*                                                   *
*****************************************************
*                                                   *
*                     WARNING                       *
*                                                   *
*            THIS IS A PROTOTYPE CODE.              *
*                                                   *
*         DO NOT INCLUDE THIS IN RELEASES.          *
*                                                   *
*       THIS CODE HAS NOT BEEN TESTED AND IS        *
*       CONSIDERED UNSTABLE. COMPILE & DEBUG        *
*       RECOMMENDED. REPORT WITH LOGS & INFO.       *
*                                                   *
*****************************************************
*/

import java.io.*;
import Core.Boot;

public class Launcher
{
  public static void main(String[] Args)throws Exception
  {
    System.out.println("Booting Program: Mosaic 2.0");
    Boot a=new Boot(true);
    a.BootRoutine();
  }
}
