package br.com.as;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import br.com.controller.PessoaController;
import br.com.controller.UsuarioController;
import br.com.crypt.CryptoUtils;
import br.com.crypt.EncriptaDecriptaRSA;
import br.com.dto.StatusRespostaDTO;
import br.com.dto.UsuarioDTO;
import br.com.dto.UsuarioPermissaoDTO;
import br.com.entities.Pessoa;
import br.com.entities.Usuario;
import br.com.provider.EntityManager;
import br.com.types.TipoNivelUsuario;

public final class UsuarioAS {
	
	private UsuarioAS(){
		
	}
	
	public static StatusRespostaDTO inserirUsuario(UsuarioDTO usuarioDTO){
		EntityManagerFactory emf = EntityManager.getFactory();
		StatusRespostaDTO resposta = new StatusRespostaDTO();
		PessoaController pessoaController = new PessoaController(emf);
		UsuarioController usuarioController = new UsuarioController(emf);
		Pessoa pessoa;
		Usuario usuario;
		
		try {
			pessoa = new Pessoa(usuarioDTO.getNome(), usuarioDTO.getDocumento());
			pessoa = pessoaController.create(pessoa);
			usuario = new Usuario(CryptoUtils.criptografaAES(usuarioDTO.getLogon()), CryptoUtils.criptografaAES(usuarioDTO.getPassword()), pessoa);
			usuarioController.create(usuario);
			resposta.setStatus("OK");
			resposta.setMensagem("Usu�rio cadastrado com sucesso!");
			EncriptaDecriptaRSA.geraChaveCadastroInicial(usuario.getId().toString(), usuario.getPassword());
			return resposta;
		} catch (Exception e) {
			resposta.setStatus("Erro");
			resposta.setMensagem(e.getMessage());
		}
		
		return resposta;
	}
	
	public static StatusRespostaDTO gravaPermissao(UsuarioPermissaoDTO usuarioDTO){
		EntityManagerFactory emf = EntityManager.getFactory();
		StatusRespostaDTO resposta = new StatusRespostaDTO();
		UsuarioController usuarioController = new UsuarioController(emf);
		Usuario usuario;
		Long usuarioID;
		
		try {
			usuarioID = Long.valueOf(usuarioDTO.getLogon().subSequence(0, usuarioDTO.getLogon().indexOf("#")).toString());
			usuario = usuarioController.findUsuario(usuarioID);
			switch (usuarioDTO.getPermissao()) {
			case "Admin":
				usuario.setNivelUsuario(TipoNivelUsuario.ADMIN_1);
				break;
			case "Usuario":
				usuario.setNivelUsuario(TipoNivelUsuario.USER_1);
				break;
			case "Solicitante":
				usuario.setNivelUsuario(TipoNivelUsuario.REQUESTER_1);
				break;	
			default:
				break;
			}
			usuarioController.edit(usuario);
			resposta.setStatus("OK");
			resposta.setMensagem("Permiss�o cadastrado com sucesso!");
			return resposta;
		} catch (Exception e) {
			resposta.setStatus("Erro");
			resposta.setMensagem(e.getMessage());
		}
		
		return resposta;
	}
	
//	public static StatusRespostaDTO alterarUsuario(Integer idUsuario, UsuarioDTO usuario) {
//		StatusRespostaDTO resposta = new StatusRespostaDTO();
//		try {
//			FacadeUsuario.alterarUsuario(idUsuario, usuario.getNome(), new BigInteger(usuario.getCpf().toString()), usuario.getEmail(), usuario.getUserName(), usuario.getPassword(), usuario.getPerfilInvestidor(), usuario.getFormaOperacao(),usuario.getProfissao());
//			resposta.setStatus("OK");
//			resposta.setMensagem("Usu�rio alterado com sucesso!");
//			
//			return resposta;
//		}catch (UsuarioException e) {
//			resposta.setStatus("Erro");
//			resposta.setMensagem(e.getError());
//		} catch (Exception e) {
//			resposta.setStatus("Erro");
//			resposta.setMensagem(e.getMessage());
//		}
//		
//		return resposta;
//	}
	
	public static Usuario getUsuario(String username, String password){
		EntityManagerFactory emf = EntityManager.getFactory();
		UsuarioController usuarioController = new UsuarioController(emf);
		
		try {
			return usuarioController.getUsuario(username, password);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static List<UsuarioDTO> getUsuario() throws Exception{
		EntityManagerFactory emf = EntityManager.getFactory();
		UsuarioController usuarioController = new UsuarioController(emf);
		List<Usuario> lUser = new ArrayList<Usuario>();
		List<UsuarioDTO> lUserDTO = new ArrayList<UsuarioDTO>();
		
		try {
			lUser = usuarioController.getUsuarios();
		} catch (Exception e) {
			return null;
		}
		
		if	(!lUser.isEmpty()){
			for (Usuario usuario : lUser) {
				lUserDTO.add(new UsuarioDTO(usuario));
			}
		}
		
		return lUserDTO;
	}
	
//	public static Usuario getUsuario(long codigoUsuario) throws Exception {
//		IAcaoBolsa acao = connect();
//		return acao.getUsuario(codigoUsuario);
//	}
	
//	public static Long getTimeExpiracao(Long codigoUsuario){
//		Long expira = 0l;
//		try {
//			UsuarioDTO usuario = getUsuarioLogado(codigoUsuario.intValue());
//			if (usuario != null) {
//				if (usuario.getPerfilInvestidor()== EnTipoInvestidor.CONSERVADOR.getCodigo()) {
//					expira = 1800000l;//Cada 30 Minutos
//				}else if (usuario.getPerfilInvestidor() == EnTipoInvestidor.MODERADO.getCodigo()) {
//					expira = 600000l;//Cada 10 Minutos
//				} else if (usuario.getPerfilInvestidor() == EnTipoInvestidor.AGRESSIVO.getCodigo()){
//					expira = 60000l;//Cada Minuto
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return expira;
//	}

//	private static IAcaoBolsa connect() throws MalformedURLException {
//		URL url = new URL("http://127.0.0.1:9876/entities?wsdl");
//		QName qname = new QName("http://entities/", "AcaoBolsaService");
//		Service ws = Service.create(url, qname);
//		IAcaoBolsa acao = ws.getPort(IAcaoBolsa.class);
//		return acao;
//	}

}
