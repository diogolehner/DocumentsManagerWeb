package br.com.crypt;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

/**
 * Classe que  criptografa e descritografa atr�ves da opera��o XOR.
 * 
 * @author Helinton P. Steffens
 *
 */
public class OneTimePadXor implements IOneTimePad{
	
	@Override
	public String criptografar(String frase, String chave) {
		String key = OneTimePadUtils.igualarChave(frase, chave);
		String palavra = "";
		
			byte[] fraseHaCriptografar = Base64.encodeBase64(frase.getBytes());
			byte[] keyHaCriptografar = Base64.encodeBase64(key.getBytes());
			
			byte[] palavraCriptografada = new byte[fraseHaCriptografar.length];
			for (int i = 0; i < fraseHaCriptografar.length; i++) {
				palavraCriptografada[i] = (byte) ((fraseHaCriptografar[i] ^ keyHaCriptografar[i])+ 65);
			}
			
			for (byte b : palavraCriptografada) {
				palavra +=  String.valueOf((char)(b));
			}
		return palavra;
	}

	@Override
	public String descriptografar(String fraseCriptografada, String chave) {
	String key = OneTimePadUtils.igualarChave(fraseCriptografada, chave);
	String palavra = "";
		
			byte[] fraseHaCriptografar = new byte[fraseCriptografada.length()];
			for (int i = 0; i < fraseCriptografada.length(); i++) {
				fraseHaCriptografar[i] = (byte) (fraseCriptografada.charAt(i) - 65);
			}
			
			byte[] keyHaCriptografar = Base64.encodeBase64(key.getBytes());
			
			byte[] palavraCriptografada = new byte[fraseHaCriptografar.length];
			for (int i = 0; i < fraseHaCriptografar.length; i++) {
				palavraCriptografada[i] = (byte) ((fraseHaCriptografar[i] ^ keyHaCriptografar[i]));
			}
			
			for (byte b : palavraCriptografada) {
				palavra +=  String.valueOf((char)(b));
			}
			
		return palavra;
	}

}
