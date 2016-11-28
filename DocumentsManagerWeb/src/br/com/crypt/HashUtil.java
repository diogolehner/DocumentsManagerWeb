package br.com.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.types.TipoAlgoritmoHash;

public class HashUtil {
	
	public static byte[] geraHash(String senha, TipoAlgoritmoHash algoritmo) throws Exception{
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(algoritmo.toString());
			return md.digest(senha.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new Exception(e);
		}
	}	

}
