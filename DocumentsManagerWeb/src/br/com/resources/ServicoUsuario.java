package br.com.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import br.com.as.DocumentoAS;
import br.com.as.UsuarioAS;
import br.com.dto.DocumentoDTO;
import br.com.dto.StatusRespostaDTO;
import br.com.dto.UsuarioDTO;
import br.com.dto.UsuarioPermissaoDTO;

@Path("/user")
public class ServicoUsuario {


	@Context
	private HttpHeaders httpRequest;
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusRespostaDTO inserirUsuario(UsuarioDTO usuarioDTO){
		return UsuarioAS.inserirUsuario(usuarioDTO);
	}
	
	@GET
	@Path("/recuperausuarios")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UsuarioDTO> getUsuarioLogada() throws Exception{
		List<UsuarioDTO> lUsuario = UsuarioAS.getUsuario();
		return lUsuario;
	}
	
	@POST
	@Path("/gravapermissao")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusRespostaDTO gravaPermissao(UsuarioPermissaoDTO usuario){
		return UsuarioAS.gravaPermissao(usuario);
	}
	
	@POST
	@Path("/gravamensagem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusRespostaDTO gravaPermissao(DocumentoDTO documento){
		return DocumentoAS.gravaMensagem(documento);
	}
	
	@GET
	@Path("/getmensagem")
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentoDTO getDocumentoDTO() throws Exception{
		Long usuarioID = Long.valueOf(httpRequest.getRequestHeader("idUsuario").get(0));
		DocumentoDTO documentoDTO = DocumentoAS.getMensagem(usuarioID, "1");
		return documentoDTO;
	}
	
}
