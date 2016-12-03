package br.com.crypt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * Classe utilizada para criptografia RSA
 * @author Diogo.Lehner
 *
 */
public final class CriptoRSA {
	
	/**
	 * Local da chave privada no sistema de arquivos.
	 */
	public static final String PATH_CHAVE_PRIVADA = "D:/MasterKey/User/";

	/**
	 * Local da chave p�blica no sistema de arquivos.
	 */
	public static final String PATH_CHAVE_PUBLICA = "D:/MasterKey/User/";
	
	private CriptoRSA() {

	}
	
	@SuppressWarnings("resource")
	public static String[] geraParChaveCadastroInicial(String usuario, String password) {
		String[] dir = new String[2];
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			kpg.initialize(2048, random);

			KeyPair kp = kpg.generateKeyPair();

			PrivateKey priKey = kp.getPrivate();
			PublicKey pubKey = kp.getPublic();

			IOneTimePad oneTimePadXor = new OneTimePad();
			String chavePrivadaCriptografada = oneTimePadXor.criptografar(Base64.encodeBase64String(priKey.getEncoded()), password);
			
			File arquivoPrivado = new File(PATH_CHAVE_PRIVADA + usuario + "/private.key");
			File arquivoPublico = new File(PATH_CHAVE_PUBLICA + "/KDC/" + usuario + "/public.key");
			
			if (arquivoPublico.exists()) {
				BufferedWriter output = new BufferedWriter(new FileWriter(arquivoPrivado));
	            output.write("");
			}else{
				arquivoPublico.getParentFile().mkdirs();
				arquivoPublico.createNewFile();
			}
			
			if (arquivoPrivado.exists()) {
				BufferedWriter output = new BufferedWriter(new FileWriter(arquivoPublico));
				output.write("");
			}else{
				arquivoPrivado.getParentFile().mkdirs();
				arquivoPrivado.createNewFile();
			}

			Files.write(arquivoPublico.toPath(), Base64.encodeBase64(pubKey.getEncoded()));
			Files.write(arquivoPrivado.toPath(), chavePrivadaCriptografada.getBytes("ISO-8859-1"));

			dir[0] = arquivoPublico.getPath();
			dir[1] = arquivoPrivado.getPath();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dir;
	}

	public static byte[] encriptText(String usuario, String texto) {
		try {
			File filePublic = new File(PATH_CHAVE_PUBLICA + "KDC/" + usuario + "/public.key");
			byte[] chavePublica = Files.readAllBytes(filePublic.toPath());

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");

			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(chavePublica));
			PublicKey pubKey = keyFactory.generatePublic(keySpec);

			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);

			byte[] encodeBase64 = cipher.doFinal(texto.getBytes());

			return Base64.encodeBase64(encodeBase64);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String decript(String usuario, byte[] texto, String password) {
		try {
			File filePrivat = new File(PATH_CHAVE_PRIVADA + usuario + "/private.key");
			byte[] chavePrivada = Files.readAllBytes(filePrivat.toPath());

			IOneTimePad oneTimePadXor = new OneTimePad();
			String chavePrivadaDescriptografada = oneTimePadXor.descriptografar(new String(chavePrivada, "ISO-8859-1"),password);

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.decodeBase64(chavePrivadaDescriptografada)));

			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			return new String(cipher.doFinal(Base64.decodeBase64(texto)));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return "";
	}
}
