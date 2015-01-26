/**
 * File Name: Email.java<br>
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
package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Email (description of class)
 * <p>
 * (description of core fields)
 * <p>
 * (description of core methods)
 * 
 * @author Jean-francois Nepton
 * @version %I%, %G%
 * @since 1.0
 */
public class Email {

	public static final String FROM = "FROM";

	public static final String TO = "TO";

	public static final String CC = "CC";

	public static final String BCC = "BCC";

	public static final String SERVER = "SERVER";

	public static final String PORT = "PORT";

	public static final String USERNAME = "USER";

	public static final String PASSWORD = "PASSWORD";

	public static final String SUBJECT = "SUBJECT";

	public static final String BODY = "BODY";

	public static final String DEFAULT_HOST = "smtp.gmail.com";

	public static final String DEFAULT_HOST_PORT = "465";

	public static final String DEFAULT_TRANSPORT = "smtps";

	/**
	 * @param properties
	 * @param keyName
	 * @return
	 */
	public static String getStringProp(HashMap<String, String> properties, String keyName, String defaultValue) {
		String value = properties.get(keyName);
		if (value == null)
			value = defaultValue;
		return value;
	}

	/**
	 * @param properties
	 * @param keyName
	 * @return
	 */
	public static String getReqStringProp(HashMap<String, String> properties, String keyName) {
		try {
			if (properties.get(keyName) != null)
				return properties.get(keyName);
			else
				throw new NullReqPropException(keyName);
		} catch (NullReqPropException e) {
			e.getMessage();
		}
		return null;
	}

	/**
	 * @param properties
	 * @param keyName
	 * @return
	 */
	public static Address getAddressProp(HashMap<String, String> properties, String keyName) {
		Address address = null;
		try {
			address = new InternetAddress(properties.get(keyName));
		} catch (AddressException e) {
			System.out.println("Malformed address for property [" + keyName + "]");
		}
		return address;
	}

	/**
	 * @param properties
	 * @param keyName
	 * @return
	 */
	public static Address getReqAddressProp(HashMap<String, String> properties, String keyName) {
		try {
			if (properties.get(keyName) != null)
				return new InternetAddress(properties.get(keyName));
			else
				throw new NullReqPropException(keyName);
		} catch (NullReqPropException e) {
			e.getMessage();
		} catch (AddressException e) {
			System.out.println("Malformed address for property [" + keyName + "]");
		}
		return null;
	}

	/**
	 * @param properties
	 * @param keyName
	 * @return
	 */
	public static Address[] getAddressProps(HashMap<String, String> properties, String keyName) {
		String[] addresses;
		List<Address> addressList = new ArrayList<>();
		try {
			addresses = properties.get(keyName).split(",");
			for (String s : addresses)
				addressList.add(new InternetAddress(s.trim()));
		} catch (AddressException e) {
			System.out.println("Malformed address for property [" + keyName + "]");
		}
		return addressList.toArray(new Address[addressList.size()]);
	}

	/**
	 * @param properties
	 * @param keyName
	 * @return
	 */
	public static Address[] getReqAddressProps(HashMap<String, String> properties, String keyName) {
		String[] addresses;
		List<Address> addressList = new ArrayList<>();
		try {
			if (properties.get(keyName) != null) {
				addresses = properties.get(keyName).split(",");
				for (String s : addresses)
					addressList.add(new InternetAddress(s.trim()));
			} else
				throw new NullReqPropException(keyName);
		} catch (NullReqPropException e) {
			e.getMessage();
		} catch (AddressException e) {
			System.out.println("Malformed address for property [" + keyName + "]");
		}
		return addressList.toArray(new Address[addressList.size()]);
	}
}

class NullReqPropException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2296053777338786454L;

	private String missingProperty;

	public NullReqPropException(String missingProperty) {
		this.missingProperty = missingProperty;
	}

	@Override
	public String getMessage() {
		return "Required property [" + missingProperty + "] is not present, email can not be created.";
	}
}