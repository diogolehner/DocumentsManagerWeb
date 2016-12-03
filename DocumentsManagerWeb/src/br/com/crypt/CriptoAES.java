package br.com.crypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import br.com.constantes.SystemConstants;
import br.com.types.TipoAlgoritmoHash;

/**
 * Classe utilizada para criptografia AES
 * @author Diogo Lehner
 *
 */
public class CriptoAES {
	
	public static String criptografaAES(String valor) throws Exception{
		return criptografaAES(valor, SystemConstants.chaveMestre);
	}
	
	public static String criptografaAES(String valor, String chave) throws Exception{
		String arqCriptografado = ""; 
		byte[] senha = HashUtil.geraHash(chave,TipoAlgoritmoHash.MD5);
		try {
			arqCriptografado = fromHex(encode(nullPadString(valor).getBytes(),senha));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
						
		return arqCriptografado;
	}
	
	public static String descriptografaAES(String valor) throws Exception {
		return descriptografaAES(valor, SystemConstants.chaveMestre);
	}
	
	public static String descriptografaAES(String valor, String chave) throws Exception {
		String descripto = "";
		String senha = fromHex(HashUtil.geraHash(chave, TipoAlgoritmoHash.MD5));

		try {
			descripto = new String(decode(toHex(valor), toHex(senha)));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}

		return descripto.trim();
	}

	private static byte[] decode(byte[] input, byte[] key) throws NoSuchAlgorithmException,
		InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
		NoSuchPaddingException {
	
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(input);
		return decrypted;
	}
	
	public static byte[] toHex(String valor) {
		int len = valor.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(valor.charAt(i), 16) << 4) + Character.digit(valor.charAt(i + 1), 16));
		}
		return data;
	}
	
	public static String fromHex(byte[] hexadecimal) {
	      StringBuffer sb = new StringBuffer();
	      for (int i=0; i < hexadecimal.length; i++) {
	          sb.append( Integer.toString( ( hexadecimal[i] & 0xff ) + 0x100, 16).substring( 1 ) );
	      }
	      return sb.toString();
	}
	
	private static byte[] encode(byte[] input, byte[] key)throws NoSuchAlgorithmException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException,	NoSuchPaddingException {
		
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(input);
		return encrypted;
	}
	
	private static String nullPadString(String original) {
	      StringBuffer output = new StringBuffer(original);
	      int remain = output.length() % 16;
	      if (remain != 0) {
	          remain = 16 - remain;
	          for (int i = 0; i < remain; i++) {
	              output.append((char) 0);
	          }
	      }
	      return output.toString();
	 }
}
