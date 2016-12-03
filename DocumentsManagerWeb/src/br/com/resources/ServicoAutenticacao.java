package br.com.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.as.AuditoriaAS;
import br.com.as.AuntenticacaoAS;
import br.com.dto.TokenDTO;
import br.com.types.TipoAuditoria;

@Path("/auth")
public class ServicoAutenticacao {
	
	@Context
	private HttpHeaders httpRequest;
	
	@POST
	@Path("user/{username}/passoword/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public TokenDTO logon(@PathParam("username") String userName, @PathParam("password") String password){
		TokenDTO tokenDTO = AuntenticacaoAS.logon(userName, password);
		AuditoriaAS.gerarAuditoria(Long.valueOf(tokenDTO.getIdUsuario()), TipoAuditoria.LOGON);
		
		return tokenDTO;
	}
	
	@POST
	@Path("token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logon(@PathParam("token") String token){
		return AuntenticacaoAS.parseJWT(token);
	}

}
