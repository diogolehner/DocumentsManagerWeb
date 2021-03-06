package br.com.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 *  Funcionalidade: Gerenciador de arquivo
 *  3 de dez de 2016
 *	@author Diogo.Lehner
 *
 */
public class GerenciaArquivo {
 
  public static String getKeyMaster() {
    String nome = "D:\\MasterKey\\key.txt";
    String key = null;
    
    try {
      FileReader arq = new FileReader(nome);
      BufferedReader lerArq = new BufferedReader(arq);
      key = lerArq.readLine(); 
      arq.close();
    } catch (IOException e) {
        System.err.printf("Erro ao ler arquivo");
    }
    
    return key;
  }
}

