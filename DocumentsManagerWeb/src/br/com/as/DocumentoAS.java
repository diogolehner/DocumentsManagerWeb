package br.com.as;

import javax.persistence.EntityManagerFactory;

import br.com.controller.DocumentoController;
import br.com.controller.UsuarioController;
import br.com.crypt.CriptoAES;
import br.com.crypt.CriptoRSA;
import br.com.dto.DocumentoDTO;
import br.com.dto.StatusRespostaDTO;
import br.com.entities.Documento;
import br.com.entities.Usuario;
import br.com.provider.EntityManager;

/**
 * 
 *  Funcionalidade: Classe utilizada para o gerenciamento de documentos
 *  3 de dez de 2016
 *	@author Diogo.Lehner
 *
 */
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
			documento = new Documento(CriptoRSA.encriptText(usuarioID.toString(), documentoDTO.getMensagem()), usuario.getPessoa());
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
		
		documentoDTO.setMensagem(CriptoRSA.decript(usuario.getId().toString(), documento.getConteudo(), CriptoAES.descriptografaAES(usuario.getPassword())));
		documentoDTO.setUsuarioID(null);
		
		return documentoDTO;
	}

}
