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

package API;

/**
 * Program to display build information 
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 06-May-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: API_01<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404)<BR></p>
 */
public class Information {

	/**
     * This constructor has little use in this program.
     *
     * This constructor is a stub. It doesnt have any usable part of the program.
     */
    public Information() {}

	/**
     * This method displays the information about the build
     *
     * It must generally contain the following details:<BR>
	 * - Program name
	 * - Version
	 * - Kernel Version [optional: with name in brackets]
	 * - Channel
	 * - Build date
	 *
	 * @throws Exception Used to catch general exceptions and error states in program 
     */
    public void AboutProgram() throws Exception {
        ClearScreen();
		System.out.println("    __________________________");
	    System.out.println("   /                         /");
        System.out.println("  / Mosaic : A Nion Project /");
		System.out.println(" /  Build  : Alpha 1.8.3   /");
		System.out.println("/_________________________/");
		System.out.println("      > Alpha Build <     \n");
		return;
    }
	
	/**
     * This method clears the screen.
	 *
	 * Note: it has 2 methods for Windows type builds and for unix type builds
	 *
	 * @throws Exception Used to catch general exceptions and error states in program
	 */
    public void ClearScreen() throws Exception {
		if(System.getProperty("os.name").contains("Windows"))
		{
			//For Windows Builds use this
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		}
		else
		{
			//For Linux/Unix or Mac Builds use this
			System.out.print("\033[H\033[2J");
			System.out.flush();
		}		
		return;
    }
}