package br.com.crypt;

public final class OneTimePadUtils {

	private OneTimePadUtils(){
		
	}
	
	public static String igualarChave(String frase, String chave) {
		String key= "";
		int count = 0;
		for (int i = 0; i < frase.length(); i++) {
			count = i;
			while (count >=  chave.length()) {
				count = count - chave.length();
				
			}
			key += chave.charAt(count);
		}
		return key;
	}
}
