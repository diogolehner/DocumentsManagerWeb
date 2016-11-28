package br.com.crypt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
//RSA Cripto Class
/********************************************************/
/** Classe para trabalhar com criptografia tipo RSA */
/** ***************************************************** */
// import java.math.*;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

public class RSACripto {
	private PrivateKey privateKey = null;

	private PublicKey publicKey = null;

	public RSACripto() {
		try {
			java.security.Security
					.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/** Metodo que cria novo par de chaves */
	public void createNewKeys() {
		KeyPairGenerator kpg;
		KeyPair keyPair = null;

		try {
			kpg = KeyPairGenerator.getInstance("RSA");
			// kpg.initialize(1024, new SecureRandom());
			kpg.initialize(1024);
			keyPair = kpg.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println("Erro 2");
		}

		privateKey = keyPair.getPrivate();
		publicKey = keyPair.getPublic();

	};

	/** Metodo que retorna a chave publica */
	public PublicKey getPublicKey() {
		return publicKey;
	};

	/**
	 * Metodo que retorna a chave publica <br>
	 * arrPublicKey[0] = getModulus() <br>
	 * arrPublicKey[1] = getPublicExponent()
	 */
	public void getPublicKey(String[] arrPublicKey) {
		RSAPublicKey rsaKey = null;

		rsaKey = (RSAPublicKey) publicKey;
		arrPublicKey[0] = rsaKey.getModulus().toString();
		arrPublicKey[1] = rsaKey.getPublicExponent().toString();

	};

	/** Metodo que retorna o Modulus da chave publica */
	public String getPublicMod() {
		RSAPublicKey rsaKey = null;

		rsaKey = (RSAPublicKey) publicKey;
		return rsaKey.getModulus().toString();

	};

	/** Metodo que retorna o Expoente da chave publica */
	public String getPublicExpoent() {
		RSAPublicKey rsaKey = null;

		rsaKey = (RSAPublicKey) publicKey;
		return rsaKey.getPublicExponent().toString();

	};

	/** Metodo que retorna a chave privada */
	public PrivateKey getPrivateKey() {
		return privateKey;
	};

	/** Metodo que seta a chave publica */
	public void setPublicKey(String mod, String exp) {
		RSAPublicKeySpec rsapubspec = new RSAPublicKeySpec(new BigInteger(mod),
				new BigInteger(exp));
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = keyFactory.generatePublic(rsapubspec);
		} catch (NoSuchAlgorithmException nae) {
			nae.printStackTrace();
		} catch (Exception ie) {
			ie.printStackTrace();
		}

	};

	/** Metodo que seta a chave privada */
	public void setPrivateKey(String exp, String mod) {

	};

	/** Metodo que encripta um valor string */
	public String encrypt(String stringValue) throws IOException,
			GeneralSecurityException, ClassNotFoundException {
		int iMode;
		String retStr;
		ByteArrayInputStream in = new ByteArrayInputStream(stringValue
				.getBytes());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream stringOut = new PrintStream(out);

		byte[] bytein = stringValue.getBytes();

		Cipher cipher = Cipher.getInstance("RSA");

		try {
			iMode = Cipher.ENCRYPT_MODE;
			cipher.init(iMode, (Key) publicKey);
		} catch (InvalidKeyException e) {
			System.out.println("Chave Invalida");
		} catch (Exception ee) {
			System.out.println(ee.getMessage());
		}

		retStr = new String();

		// retStr = crypt(in, out, cipher);
		retStr = crypt(bytein, cipher);

		String encrypted = stringOut.toString();

		in.close();
		out.close();

		// return encrypted;
		return retStr;

	};

	/** Metodo que decripta um valor string */
	private String decrypt(String stringValue) {
		return new String();
	};

	private String crypt(InputStream in, OutputStream out, Cipher cipher)
			throws IOException, GeneralSecurityException {

		int blockSize = cipher.getBlockSize();
		int outputSize = cipher.getOutputSize(blockSize);
		byte[] inBytes = new byte[blockSize];
		byte[] outBytes = new byte[outputSize];

		int inLength = 0;
		;
		boolean more = true;
		while (more) {
			inLength = in.read(inBytes);
			if (inLength == blockSize) {
				int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
				out.write(outBytes, 0, outLength);
			} else
				more = false;
		}
		if (inLength > 0)
			outBytes = cipher.doFinal(inBytes, 0, inLength);
		else
			outBytes = cipher.doFinal();

		outBytes = cipher.doFinal();
		out.write(outBytes);
		return arr2str(outBytes);

	}

	private String crypt(byte[] in, Cipher cipher) throws IOException,
			GeneralSecurityException {

		byte[] out = cipher.doFinal(in);

		return arr2str(out);

	}

	private String arr2str(byte[] numbers) {
		String hex = "";
		String tmp = null;
		int itmp;
		for (int i = 0; i < numbers.length; i++) {
			tmp = Integer.toHexString(numbers[i]);
			if (tmp.length() == 1) {
				hex += '0' + tmp; // add leading zero
			} else {
				hex += tmp;
			}
		}

		return hex;

	}

	private byte[] str2arr(String hex) {
		int len = hex.length() / 2;
		byte[] numbers = new byte[len];
		numbers[0] = (byte) Integer.parseInt(hex.substring(0, 2), 16);
		// radix 16
		for (int i = 1; i < len; i++) {
			numbers[i] = (byte) Integer.parseInt(hex
					.substring(i * 2, i * 2 + 2), 16);
		}

		return numbers;
	}

}