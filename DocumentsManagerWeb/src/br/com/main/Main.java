package br.com.main;

import br.com.constantes.SystemConstants;
import br.com.crypt.CryptoUtils;
import br.com.crypt.RSACripto;
import br.com.io.GerenciaArquivo;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		main.inserirPessoa();
	}
	
	private void inserirPessoa(){
		RSACripto rsa = new RSACripto();
		rsa.createNewKeys();
		
		rsa.getPrivateKey();
		rsa.getPublicKey();
		
		
//		System.out.println(ReaderTxt.getKeyMaster());
//		String chave = SystemConstants.chaveMestre;
//		String texto = "texto a criptografar";
//		
//		try {
//			String cripto = CryptoUtils.criptografaAES(texto, chave); 
//			System.out.println("xripto: " + CryptoUtils.criptografaAES(texto, chave));
//			System.out.println("decript: " + CryptoUtils.descriptografaAES(cripto, chave));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("DocumentsManagerWeb");
//		PessoaController pessoaController = new PessoaController(factory);
//		
//		List<Pessoa> lPessoas = pessoaController.findPessoaEntities();
//		
//		for (Pessoa umaPessoa : lPessoas) {
//			System.out.println(umaPessoa.getNome());
//		}
		
//		Pessoa pessoa = new Pessoa("Samara Timm", "111.222.333-44");
//		
//		PessoaController pessoaController = new PessoaController(factory);
//		pessoaController.create(pessoa);
		
		
		
	}

}
