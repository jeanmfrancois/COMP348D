/**
 * File Name: SendEmail.java<br>
 * I declare that this assignment is my own work and that all material
 * previously written or published in any source by any other person has been
 * duly acknowledged in the assignment. I have not submitted this work, or a
 * significant part thereof, previously as part of any academic program. In
 * submitting this assignment I give permission to copy it for assessment
 * purposes only. Jean-francois Nepton<br>
 * COMP 348 Network Programming<br>
 * Cordinator: Richard Huntrods<br>
 * Student ID# 2358976<br>
 * Created: Jan 22, 2015
 */
package emails;

import java.util.HashMap;

import utils.IO;

/**
 * SendEmail is a simple email program that will connect to your preferred email
 * server and send an email to a primary recipient, a list of at least three
 * secondary recipients (cc) and at least three tertiary recipients (bcc). The
 * entire email, including all recipients plus subject and body, should be read
 * from an input file specified on the command line when you start the program,
 * i.e., ‘java SendEmail thisfile.txt’.
 * 
 * @author Jean-francois Nepton
 * @version %I%, %G%
 * @since 1.0
 */
public class SendEmail {

	public static void main(String[] args) {
		System.out.println("Program has executed " + args[0]);
		HashMap<String, String> properties = IO.createProperties(IO.readFile(args[0]), ":");
		properties.toString();
	}
}
