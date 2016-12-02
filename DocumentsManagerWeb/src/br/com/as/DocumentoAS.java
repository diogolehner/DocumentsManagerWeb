package br.com.as;

import javax.persistence.EntityManagerFactory;

import br.com.controller.DocumentoController;
import br.com.controller.UsuarioController;
import br.com.crypt.EncriptaDecriptaRSA;
import br.com.dto.DocumentoDTO;
import br.com.dto.StatusRespostaDTO;
import br.com.entities.Documento;
import br.com.entities.Usuario;
import br.com.provider.EntityManager;

public final class DocumentoAS {
	
	private DocumentoAS(){
		
	}
	
	public static StatusRespostaDTO gravaMensagem(DocumentoDTO documentoDTO){
		EntityManagerFactory emf = EntityManager.getFactory();
		StatusRespostaDTO resposta = new StatusRespostaDTO();
		DocumentoController documentoController = new DocumentoController(emf);
		UsuarioController usuarioController = new UsuarioController(emf);
		Documento documento;
		Usuario usuario;
		Long usuarioID;
		
		try {
			usuarioID = Long.valueOf(documentoDTO.getUsuarioID().subSequence(0, documentoDTO.getUsuarioID().indexOf("#")).toString());
			usuario = usuarioController.findUsuario(usuarioID);
			documento = new Documento(EncriptaDecriptaRSA.encryptMessageWithPublicKey(usuarioID.toString(), documentoDTO.getMensagem()), usuario.getPessoa());
			documentoController.create(documento);
			resposta.setStatus("OK");
			resposta.setMensagem("Documento cadastrado/enviado com sucesso!");
			return resposta;
		} catch (Exception e) {
			resposta.setStatus("Erro");
			resposta.setMensagem(e.getMessage());
		}
		
		return resposta;
	}
	
	
	public static DocumentoDTO getMensagem(Long usuarioID) throws Exception{
		EntityManagerFactory emf = EntityManager.getFactory();
		DocumentoController documentoController = new DocumentoController(emf);
		UsuarioController usuarioController = new UsuarioController(emf);
		Documento documento = new Documento();
		DocumentoDTO documentoDTO = new DocumentoDTO();
		Usuario usuario;
		
		try {
			usuario = usuarioController.findUsuario(usuarioID);
			documento = documentoController.getDocumento(usuario.getPessoa().getId());
		} catch (Exception e) {
			return null;
		}
		
		//documentoDTO.setMensagem(EncriptaDecriptaRSA.decryptWithPrivateKey(documento.getConteudo(), usuario.getId().toString()));
		documentoDTO.setMensagem("teste");
		documentoDTO.setUsuarioID(null);
		
		return documentoDTO;
	}

}
