/**
 * File Name: UserAuthenticator.java<br>
 * I declare that this assignment is my own work and that all material
 * previously written or published in any source by any other person has been
 * duly acknowledged in the assignment. I have not submitted this work, or a
 * significant part thereof, previously as part of any academic program. In
 * submitting this assignment I give permission to copy it for assessment
 * purposes only. Jean-francois Nepton<br>
 * COMP 348 Network Programming<br>
 * Cordinator: Richard Huntrods<br>
 * Student ID# 2358976<br>
 * Created: Jan 25, 2015
 */
package utils;

import javax.mail.PasswordAuthentication;

public class UserAuthenticator extends javax.mail.Authenticator {

	private String user;

	private String password;

	@SuppressWarnings("unused")
	private UserAuthenticator() {
		super();
		setUser("default");
		setPassword("default");
	}

	/**
	 * @param username
	 * @param password
	 */
	public UserAuthenticator(String username, String password) {
		super();
		setUser(username);
		setPassword(password);
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(getUser(), getPassword());
	}
}
