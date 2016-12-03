package br.com.crypt;

/**
 * Classe que criptografa e descritografa atr�ves de subtra��o de char.
 * 
 * @author Helinton P. Steffens
 *
 */
public class OneTimePad implements IOneTimePad{

	@Override
	public String criptografar(String frase, String key) {
		return modificarFrase(frase, key, true);
	}

	@Override
	public String descriptografar(String fraseCriptografada, String key) {
		return modificarFrase(fraseCriptografada,key, false);
	}
	
	private static String modificarFrase(String frase, String chave,boolean criptografar){
		String key = OneTimePadUtils.igualarChave(frase, chave);
		String palavraCriptografada = "";
		for (int i = 0; i < frase.length(); i++) {
			char letra = frase.charAt(i);
			char cifra = key.charAt(i);
			
			if (criptografar) {
				palavraCriptografada +=  String.valueOf((char)(letra + cifra -48 ));
			}else{
				palavraCriptografada += String.valueOf((char)(letra - cifra + 48 ));
			}
		}
		return palavraCriptografada;
	}
	
	

	
}
