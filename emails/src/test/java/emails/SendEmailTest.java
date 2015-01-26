/**
 * File Name: SendEmailTest.java<br>
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

import org.junit.Test;

import com.jfbuilds.tme4.emails.SendEmail;

/**
 * SendEmailTest (description of class)
 * <p>
 * (description of core fields)
 * <p>
 * (description of core methods)
 * 
 * @author Jean-francois Nepton
 * @version %I%, %G%
 * @since 1.0
 */
public class SendEmailTest {

	/**
	 * Test method for
	 * {@link com.jfbuilds.tme4.emails.SendEmail#main(java.lang.String[])} .
	 */
	@Test
	public void testMain() {
		// fail("Not yet implemented"); // TODO
		SendEmail.main(new String[] { "send-email-example.txt" });
	}
}
