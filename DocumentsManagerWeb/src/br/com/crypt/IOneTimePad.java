package br.com.crypt;

/**
 * Disponibiliza as op��es da criptografia One Time Pad
 * 
 * @author Helinton P. Steffens
 *
 */
public interface IOneTimePad {

	/**
	 * Criptografa determinada frase
	 * 
	 * @param frase
	 * @return
	 */
	public String criptografar(String frase, String chave);
	
	/**
	 * Descriptografa determinada frase
	 * 
	 * @param fraseCriptografada
	 * @return
	 */
	public String descriptografar(String fraseCriptografada, String chave);
}
