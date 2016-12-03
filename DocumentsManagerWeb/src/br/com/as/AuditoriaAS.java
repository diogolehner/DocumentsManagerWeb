package br.com.as;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import br.com.controller.AuditoriaController;
import br.com.controller.UsuarioController;
import br.com.dto.AuditoriaDTO;
import br.com.entities.Auditoria;
import br.com.entities.Usuario;
import br.com.provider.EntityManager;
import br.com.types.TipoAuditoria;

public final class AuditoriaAS {
	
	private AuditoriaAS(){
		
	}
	
	public static void gerarAuditoria(Long idUser, TipoAuditoria tipo){
		EntityManagerFactory emf = EntityManager.getFactory();
		AuditoriaController auditoriaController = new AuditoriaController(emf);
		UsuarioController usuarioController = new UsuarioController(emf);
		Usuario usuario;
		
		try {
			usuario = usuarioController.findUsuario(idUser);
			switch (tipo) {
			case LOGON:
				auditoriaController.create(new Auditoria("Logou no sistema", usuario.getPessoa()));
				break;
				
			case LOGOUT:
				auditoriaController.create(new Auditoria("Saiu do sistema", usuario.getPessoa()));
				break;
				
			case ACESSO_MANAGER:
				auditoriaController.create(new Auditoria("Acesso ao cadastro de permissão", usuario.getPessoa()));
				break;
				
			case CADASTRO_MANAGER:
				auditoriaController.create(new Auditoria("Cadastrou permissão", usuario.getPessoa()));
				break;
				
			case ACESSO_DOCUMENTO:
				auditoriaController.create(new Auditoria("Acesso ao cadastro de documento", usuario.getPessoa()));
				break;
				
			case CADASTRO_CODUMENTO:
				auditoriaController.create(new Auditoria("Cadastrou documento", usuario.getPessoa()));
				break;	
				
			case ACESSO_MENSAGEM:
				auditoriaController.create(new Auditoria("Acesso as mensagens", usuario.getPessoa()));
				break;	
				
			case NOVO_USUARIO:
				auditoriaController.create(new Auditoria("Cadastro novo usuário", usuario.getPessoa()));
				break;		
				
			}
			
		} catch (Exception e) {}
	}
	
	public static AuditoriaDTO getAuditoria() throws Exception{
		EntityManagerFactory emf = EntityManager.getFactory();
		AuditoriaController auditoriaController = new AuditoriaController(emf);
		List<Auditoria> lAuditoria = new ArrayList<>();
		
		try {
			lAuditoria = auditoriaController.getAuditoria();
		} catch (Exception e) {
			return null;
		}
		
		StringBuilder auditoria = new StringBuilder();
		for (Auditoria umaAud : lAuditoria) {
			if	(auditoria.toString().isEmpty()){
				auditoria.append(umaAud.getAcao()).append(" às ").append(umaAud.getData()).append(" por ").append(umaAud.getPessoa().getNome());
			}else{
				auditoria.append("\n").append(umaAud.getAcao()).append(" às ").append(umaAud.getData()).append(" por ").append(umaAud.getPessoa().getNome());
			}
		}
		
		return new AuditoriaDTO(auditoria.toString());
	}
	
}
