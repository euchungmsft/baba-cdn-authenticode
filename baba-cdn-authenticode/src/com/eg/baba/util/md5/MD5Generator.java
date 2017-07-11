package com.eg.baba.util.md5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MD5Generator {

	public static void main(String[] args) {

		// TODO Auto-generated method stub

		String uri;
		String key;

		// Test
		
		key = "aliyuncdnexp1234";
		uri = "/video/standard/1K.html";

		String str = MD5Generator.getAuthStringA(uri, 1444435200L, key);
		System.out.println(str);
		System.out.println(str.equals("1444435200-0-0-80cd3862d699b7118eed99103f2a3a4f"));

		// Sample for Type A

		String str1 = MD5Generator.getAuthStringA(uri, key); // with 1 day expiry
		System.out.println(str1);

		// Sample for Type B

		uri = "/4/44/44c0909bcfc20a01afaf256ca99a8b8b.mp3";

		System.out.println(getAuthStringB(uri, key));
		System.out.println(getAuthStringB(uri, "201508150800", key));

		// Sample for Type C

		uri = "/test.flv";

		System.out.println(getAuthStringC1(uri, key));
		System.out.println(getAuthStringC1(uri, "55CE8100", key));
		System.out.println(getMDStringC(uri, "55CE8100", key));

	}

	/**
	 * Gets authenticode in Type C format 1 for the URL authentication with 1 day expiry
	 * 
	 * @param uri URI to download
	 * @param privateKey key string defined from the CDN console 
	 * @return encoded string as URI
	 */ 
	private static String getAuthStringC1(String uri, String privateKey) {
		// TODO Auto-generated method stub

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1); // 1 day expiry
		
		return getAuthStringC1(uri, Long.toHexString(cal.getTimeInMillis() / 1000), privateKey);
	}

	/**
	 * Gets authenticode in Type C format 1 for the URL authentication
	 * 
	 * @param uri URI to download
	 * @param timestamp UNIX timestamp in hex (eg. 55CE8100 = Saturday, August 15, 2015 9:00:01 AM GMT+09:00)
	 * @param privateKey key string defined from the CDN console 
	 * @return encoded string as URI
	 */
	private static String getAuthStringC1(String uri, String timestamp, String privateKey) {
		// TODO Auto-generated method stub

		return "/" + getMDStringC(uri, timestamp, privateKey) + "/" + timestamp + uri;
	}

	/**
	 * Gets MD5 string to pass to the URL parameter
	 * 
	 * @param uri URI to download
	 * @param timestamp UNIX timestamp in hex (eg. 55CE8100 = Saturday, August 15, 2015 9:00:01 AM GMT+09:00)
	 * @param privateKey key string defined from the CDN console 
	 * @return MD5 string as URI 
	 */
	private static String getMDStringC(String uri, String timestamp, String privateKey) {
		// TODO Auto-generated method stub

		String source = privateKey + uri + timestamp; // PrivateKey+URI+Timestamp
		return getMDString(source);
	}

	/**
	 * Gets authenticode in Type B for the URL authentication with 1800 S of expiry from now
	 * 
	 * @param uri URI to download
	 * @param privateKey key string defined from the CDN console 
	 * @return encoded string to pass with URL parameter 
	 */
	private static String getAuthStringB(String uri, String privateKey) {
		// TODO Auto-generated method stub

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");

		return getAuthStringB(uri, format.format(cal.getTime()), privateKey);
	}

	/**
	 * Gets authenticode in Type B for the URL authentication 
	 * 
	 * @param uri URI to download
	 * @param timestamp timestamp in 'yyyyMMddHHmm' format with 1800 S of expiry 
	 * @param privateKey key string defined from the CDN console 
	 * @return encoded string as URI 
	 */
	private static String getAuthStringB(String uri, String timestamp, String privateKey) {
		// TODO Auto-generated method stub

		String source = privateKey + timestamp + uri; // PrivateKey+Timestamp+URI
		// System.out.println(source);
		return "/" + timestamp + "/" + getMDString(source) + uri;
	}

	/**
	 * Gets authenticode in Type A for the URL authentication with 1 day expiry
	 *  
	 * @param uri URI to download
	 * @param privateKey key string defined from the CDN console 
	 * @return encoded string to pass with URL parameter 
	 */
	private static String getAuthStringA(String uri, String privateKey) {
		// TODO Auto-generated method stub

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.add(Calendar.DAY_OF_MONTH, 1); // 1 day expiry
		// System.out.println(cal.getTime());
		long timestamp = cal.getTimeInMillis() / 1000;

		return getAuthStringA(uri, timestamp, privateKey);
	}

	/**
	 * Gets authenticode in Type A for the URL authentication 
	 * 
	 * @param uri URI to download
	 * @param timestamp time stamp presents the expiry of the access as long in seconds (not millis)
	 * @param privateKey key string defined from the CDN console 
	 * @return encoded string to pass with URL parameter 
	 */
	private static String getAuthStringA(String uri, long timestamp, String privateKey) {
		// TODO Auto-generated method stub

		String source = uri + "-" + timestamp + "-0-0-" + privateKey; // URI-Timestamp-rand-uid-PrivateKey
		// System.out.println(source);
		return timestamp + "-0-0-" + getMDString(source);
	}

	/**
	 * Encodes to MD5  
	 * 
	 * @param source string to MD5 encode 
	 * @return MD5 string. null on exception
	 */
	private static String getMDString(String source) {
		// TODO Auto-generated method stub

		MessageDigest md;

		try {

			md = MessageDigest.getInstance("MD5");
			byte mdbytes[] = md.digest(source.getBytes("UTF-8"));

			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < mdbytes.length; i++) {
				sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();

		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
