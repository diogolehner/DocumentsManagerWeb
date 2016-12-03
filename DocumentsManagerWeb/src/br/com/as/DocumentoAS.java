package br.com.as;

import java.util.ArrayList;
import java.util.List;

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
	
	public static StatusRespostaDTO gravaMensagem(DocumentoDTO documentoDTO, Long usuarioLogadoID){
		EntityManagerFactory emf = EntityManager.getFactory();
		StatusRespostaDTO resposta = new StatusRespostaDTO();
		DocumentoController documentoController = new DocumentoController(emf);
		UsuarioController usuarioController = new UsuarioController(emf);
		Documento documento;
		Usuario usuario;
		Usuario usuarioLogado;
		Long usuarioID;
		String message;
		
		try {
			usuarioID = Long.valueOf(documentoDTO.getUsuarioID().subSequence(0, documentoDTO.getUsuarioID().indexOf("#")).toString());
			usuario = usuarioController.findUsuario(usuarioID);
			usuarioLogado = usuarioController.findUsuario(usuarioLogadoID);
			message = usuarioLogado.getPessoa().getNome() + ": " + documentoDTO.getMensagem();
			documento = new Documento(CriptoRSA.encriptText(usuarioID.toString(), message), usuario.getPessoa());
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
		List<Documento> lDocumento = new ArrayList<>();
		DocumentoDTO documentoDTO = new DocumentoDTO();
		Usuario usuario;
		
		try {
			usuario = usuarioController.findUsuario(usuarioID);
			lDocumento = documentoController.getDocumento(usuario.getPessoa().getId());
		} catch (Exception e) {
			return null;
		}
		
		StringBuilder mensagens = new StringBuilder();
		for (Documento umDoc : lDocumento) {
			if	(mensagens.toString().isEmpty()){
				mensagens.append(CriptoRSA.decript(usuario.getId().toString(), umDoc.getConteudo(), CriptoAES.descriptografaAES(usuario.getPassword())));
			}else{
				mensagens.append("\n").append(CriptoRSA.decript(usuario.getId().toString(), umDoc.getConteudo(), CriptoAES.descriptografaAES(usuario.getPassword())));
			}
		}
		
		documentoDTO.setMensagem(mensagens.toString());
		documentoDTO.setUsuarioID(null);
		
		return documentoDTO;
	}

}
