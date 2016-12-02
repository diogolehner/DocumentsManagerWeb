package br.com.crypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;

public class EncriptaDecriptaRSA {

	public static final String ALGORITHM = "RSA";

	/**
	 * Local da chave privada no sistema de arquivos.
	 */
	public static final String PATH_CHAVE_PRIVADA = "D:/MasterKey/User/";

	/**
	 * Local da chave p�blica no sistema de arquivos.
	 */
	public static final String PATH_CHAVE_PUBLICA = "D:/MasterKey/User/";

	/**
	 * Gera a chave que cont�m um par de chave Privada e P�blica usando 1025
	 * bytes. Armazena o conjunto de chaves nos arquivos private.key e
	 * public.key
	 */
	public static void geraChave(String usuario) {
		try {
			final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
			keyGen.initialize(1024);
			KeyPair key = keyGen.generateKeyPair();

			File chavePrivadaFile = new File(PATH_CHAVE_PRIVADA + usuario + "/private.key");
			File chavePublicaFile = new File(PATH_CHAVE_PUBLICA + usuario + "/public.key");
			

			// Cria os arquivos para armazenar a chave Privada e a chave Publica
			if (chavePrivadaFile.getParentFile() != null) {
				chavePrivadaFile.getParentFile().mkdirs();
			}

			chavePrivadaFile.createNewFile();

			if (chavePublicaFile.getParentFile() != null) {
				chavePublicaFile.getParentFile().mkdirs();
			}

			chavePublicaFile.createNewFile();

			// Salva a Chave P�blica no arquivo
			ObjectOutputStream chavePublicaOS = new ObjectOutputStream(new FileOutputStream(chavePublicaFile));
			chavePublicaOS.writeObject(key.getPublic());
			chavePublicaOS.close();
			
			// Salva a Chave Privada no arquivo
			ObjectOutputStream chavePrivadaOS = new ObjectOutputStream(new FileOutputStream(chavePrivadaFile));
			chavePrivadaOS.writeObject(key.getPrivate());
			chavePrivadaOS.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void geraChaveCadastroInicial(String usuario, String password) {
		try {
			final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
			keyGen.initialize(1024);
			final KeyPair key = keyGen.generateKeyPair();

			File chavePrivadaFile = new File(PATH_CHAVE_PRIVADA + usuario + "/private.key");
			File chavePublicaFile = new File(PATH_CHAVE_PUBLICA + usuario + "/public.key");
			File chavePublicaFileKDC = new File(PATH_CHAVE_PUBLICA + "/KDC/" + usuario + "/public.key");

			// Cria os arquivos para armazenar a chave Privada e a chave Publica
			if (chavePrivadaFile.getParentFile() != null) {
				chavePrivadaFile.getParentFile().mkdirs();
			}

			chavePrivadaFile.createNewFile();

			if (chavePublicaFile.getParentFile() != null) {
				chavePublicaFile.getParentFile().mkdirs();
			}

			chavePublicaFile.createNewFile();
			
			if (chavePublicaFileKDC.getParentFile() != null) {
				chavePublicaFileKDC.getParentFile().mkdirs();
			}

			chavePublicaFileKDC.createNewFile();

			// Salva a Chave P�blica no arquivo
			ObjectOutputStream chavePublicaOS = new ObjectOutputStream(new FileOutputStream(chavePublicaFile));
			chavePublicaOS.writeObject(key.getPublic());
			chavePublicaOS.close();
			
			chavePublicaFileKDC.createNewFile();

			// Salva a Chave P�blica no kdc
			ObjectOutputStream chavePublicaOSKDC = new ObjectOutputStream(new FileOutputStream(chavePublicaFileKDC));
			chavePublicaOSKDC.writeObject(key.getPublic());
			chavePublicaOSKDC.close();
			
			// Salva a Chave Privada no arquivo
			ObjectOutputStream chavePrivadaOS = new ObjectOutputStream(new FileOutputStream(chavePrivadaFile));
			chavePrivadaOS.writeObject(key.getPrivate());
			chavePrivadaOS.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verifica se o par de chaves P�blica e Privada j� foram geradas.
	 */
	public static boolean verificaSeExisteChavesNoSO() {

		File chavePrivada = new File(PATH_CHAVE_PRIVADA);
		File chavePublica = new File(PATH_CHAVE_PUBLICA);

		if (chavePrivada.exists() && chavePublica.exists()) {
			return true;
		}

		return false;
	}

	/**
	 * Criptografa o texto puro usando chave p�blica.
	 */
	public static byte[] criptografa(String texto, PublicKey chave) {
		byte[] cipherText = null;

		try {
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			// Criptografa o texto puro usando a chave P�lica
			cipher.init(Cipher.ENCRYPT_MODE, chave);
			cipherText = cipher.doFinal(texto.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cipherText;
	}
	

	/**
	 * Decriptografa o texto puro usando chave privada.
	 */
	public static String decriptografa(byte[] texto, PrivateKey chave) {
		byte[] dectyptedText = null;

		try {
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			// Decriptografa o texto puro usando a chave Privada
			cipher.init(Cipher.DECRYPT_MODE, chave);
			dectyptedText = cipher.doFinal(texto);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new String(dectyptedText);
	}

	public static byte[] encryptWithPublicKey(String usuario, String texto) {
		ObjectInputStream inputStream = null;
		byte[] textoCriptografado = null;
		try {
			inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PUBLICA + usuario + "/public.key"));
			final PublicKey chavePublica = (PublicKey) inputStream.readObject();
			textoCriptografado = criptografa(texto, chavePublica);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return textoCriptografado;
	}
	
	public static byte[] encryptMessageWithPublicKey(String usuario, String texto) {
		ObjectInputStream inputStream = null;
		byte[] textoCriptografado = null;
		try {
			inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PUBLICA + "/KDC/" + usuario + "/public.key"));
			final PublicKey chavePublica = (PublicKey) inputStream.readObject();
			textoCriptografado = criptografa(texto, chavePublica);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return textoCriptografado;
	}

	public static String decryptWithPrivateKey(byte[] textoCriptografado, String usuario) {
		ObjectInputStream inputStream = null;
		String textoPuro = null;
		try {
			inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PUBLICA + usuario + "/private.key"));
			final PrivateKey chavePrivada = (PrivateKey) inputStream.readObject();
			textoPuro = decriptografa(textoCriptografado, chavePrivada);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return textoPuro;
	}
	
	/**
	 * Testa o Algoritmo
	 */
	public static void main(String[] args) {

		try {
			// Verifica se j� existe um par de chaves, caso contr�rio gera-se as
			// chaves..
			// if (!verificaSeExisteChavesNoSO()) {
			// M�todo respons�vel por gerar um par de chaves usando o algoritmo
			// RSA e
			// armazena as chaves nos seus respectivos arquivos.
			geraChave("Diogo");
			// }

			final String msgOriginal = "Exemplo de mensagem";

			// Criptografa a Mensagem usando a Chave P�blica
			final byte[] textoCriptografado = encryptWithPublicKey("Diogo", msgOriginal);

			// Decriptografa a Mensagem usando a Chave Pirvada
			final String textoPuro = decryptWithPrivateKey(textoCriptografado.toString().getBytes(), "Diogo");

			// Imprime o texto original, o texto criptografado e
			// o texto descriptografado.
			System.out.println("Mensagem Original: " + msgOriginal);
			System.out.println("Mensagem Criptografada: " + textoCriptografado.toString());
			System.out.println("Mensagem Decriptografada: " + textoPuro);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}