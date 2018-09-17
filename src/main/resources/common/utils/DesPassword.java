package /packageName/.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;

/**
 * 密码的加密解密类	--原来楼上框架中加密解密类反编译过来的
 */
public class DesPassword {

	String salt = "1#2$3%4(5)6@7!poeeww$3%4(5)djjkkldss";

	private String getURLDecoderdecode(String encPass) {
		String urlEncPass = "";
		try {
			urlEncPass = URLDecoder.decode(encPass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return urlEncPass;
	}

	private String deCrypt(byte[] base64RawPass, String spKey) {
		String rawPass = "";
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("DESede");
			byte[] key = getEnKey(spKey);
			DESedeKeySpec dks = new DESedeKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey sKey = keyFactory.generateSecret(dks);
			cipher.init(2, sKey);
			byte[] ciphertext = cipher.doFinal(base64RawPass);
			rawPass = new String(ciphertext, "UTF-16LE");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rawPass;
	}

	private byte[] getEnKey(String spKey) {
		byte[] enKey = (byte[]) null;
		try {
			byte[] desKey1 = md5(spKey);
			enKey = new byte[24];
			int i = 0;
			while ((i < desKey1.length) && (i < 24)) {
				enKey[i] = desKey1[i];
				i++;
			}
			if (i < 24) {
				enKey[i] = 0;
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enKey;
	}

	private byte[] md5(String spKey) {
		byte[] desKey1 = (byte[]) null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			desKey1 = md5.digest(spKey.getBytes("GBK"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desKey1;
	}

	private byte[] Encrypt(byte[] bytesRawPass, byte[] enKey) {
		byte[] bytesEncPass = (byte[]) null;
		try {
			DESedeKeySpec dks = new DESedeKeySpec(enKey);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey key = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(1, key);
			bytesEncPass = cipher.doFinal(bytesRawPass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytesEncPass;
	}

	private String getBase64Encode(byte[] bytesEncPass) {
		String base64EncPass = "";
		try {
			BASE64Encoder base64en = new BASE64Encoder();
			base64EncPass = base64en.encode(bytesEncPass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return base64EncPass;
	}

	private String filter(String base64EncPass) {
		String filterEncrypt = "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < base64EncPass.length(); i++) {
			int asc = base64EncPass.charAt(i);
			if ((asc != 10) && (asc != 13))
				sb.append(base64EncPass.subSequence(i, i + 1));
		}
		filterEncrypt = new String(sb);
		return filterEncrypt;
	}

	private String getURLEncode(String filterEncrypt) {
		String encPass = "";
		try {
			encPass = URLEncoder.encode(filterEncrypt);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return encPass;
	}
	/**
	 * 加密
	 */
	public String encodePassword(String rawPass) throws Exception {
		String encPass = "";
		try {
			byte[] enKey = getEnKey(salt);
			byte[] bytesRawPass = rawPass.getBytes("UTF-16LE");
			byte[] bytesEncPass = Encrypt(bytesRawPass, enKey);
			String base64EncPass = getBase64Encode(bytesEncPass);
			String filterEncrypt = filter(base64EncPass);
			encPass = getURLEncode(filterEncrypt);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return encPass;
	}

	/**
	 * 解密
	 */
	public String decodePassword(String encPass) {
		String rawPass = "";
		try {
			String urlEncPass = getURLDecoderdecode(encPass);
			BASE64Decoder base64Decode = new BASE64Decoder();
			byte[] base64RawPass = base64Decode.decodeBuffer(urlEncPass);
			rawPass = deCrypt(base64RawPass, salt);
		} catch (Exception e) {
			throw new RuntimeException(
					"org.loushang.bsp.permit.encoding.DesPasswordEncoder类中decodePassword方法:DES对外提供的解密方法出错，请检查要解密的密码是不是由本系统加密的");
		}
		return rawPass;
	}

	/**********************************************************************************************************************/
	private static char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D',
        'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
        'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
        'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
        'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
        '4', '5', '6', '7', '8', '9', '+', '/', };

	private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59,
        60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
        10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1,
        -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37,
        38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1,
        -1, -1 };

	/**
	* 解密
	* @param str
	* @return
	*/
	public static byte[] decode(String str) {
		byte[] data = str.getBytes();
		int len = data.length;
		ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
		int i = 0;
		int b1, b2, b3, b4;

		while (i < len) {
	        do {
	                b1 = base64DecodeChars[data[i++]];
	        } while (i < len && b1 == -1);
	        if (b1 == -1) {
	                break;
	        }

	        do {
	                b2 = base64DecodeChars[data[i++]];
	        } while (i < len && b2 == -1);
	        if (b2 == -1) {
	                break;
	        }
	        buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

	        do {
	                b3 = data[i++];
	                if (b3 == 61) {
	                        return buf.toByteArray();
	                }
	                b3 = base64DecodeChars[b3];
	        } while (i < len && b3 == -1);
	        if (b3 == -1) {
	                break;
	        }
	        buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

	        do {
	                b4 = data[i++];
	                if (b4 == 61) {
	                        return buf.toByteArray();
	                }
	                b4 = base64DecodeChars[b4];
	        } while (i < len && b4 == -1);
	        if (b4 == -1) {
	        	break;
	        }
	        buf.write((int) (((b3 & 0x03) << 6) | b4));
		}
		return buf.toByteArray();
	}
	/**********************************************************************************************************************/

	public static void main(String[] args) throws Exception {

		System.out.println(new String(decode("eDgxMTExMg==")));

		/packageName/.common.utils.DesPassword desPassword = new /packageName/.common.utils.DesPassword();
		/**
		 * 原密：1 ； 加密： eiyWkwBuD0Y%3D
		 * 原密：123； 加密： 0iaxr0jLX3Q%3D
		 * 原密：12dcd3； 加密：rSoB%2FiTTgaRPwa7qZiXYUQ%3D%3D
		 */
		String decodePassword = desPassword.decodePassword("eiyWkwBuD0Y%3D");
		System.out.println("解密后的密码为："+decodePassword);

		String encodePassword = desPassword.encodePassword("admin");
		System.out.println("加密后的密码为："+encodePassword);

	}

}