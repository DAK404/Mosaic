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

/**
* Class that will help in launching the program correctly. <BR>
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  000-Mosaic-Launcher      |
* | Class Name  :  Launcher                 |
* | Since       :  0.0.1, 20-July-2014      |
* | Updated on  :  1.7.8, 09-September-2020 |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*
*/
public class Launcher
{
    /**
    * Constructor which is a stub. Constructor has a little use.
    */
    public Launcher()
    {

    }

    /**
    * Method which helps in creating a thread<BR>
    *
    * @param Args []    : An array to possibly accept the command line arguments.
    * @throws Exception : Throws general exceptions which are encountered during program execution
    */
    public static void main(String[] Args)throws Exception
    {
        try
        {
            System.gc();
            System.out.println("Booting Program: Mosaic 2.0");
            new Mosaic.Core.Boot().BootScript();
        }
        catch(Exception E)
        {
            E.printStackTrace();
        }
    }
}
