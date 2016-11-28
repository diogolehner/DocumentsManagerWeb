package br.com.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.as.AuntenticacaoAS;
import br.com.dto.TokenDTO;

@Path("/auth")
public class ServicoAutenticacao {
	 
	@POST
	@Path("user/{username}/passoword/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public TokenDTO logon(@PathParam("username") String userName, @PathParam("password") String password){
		return AuntenticacaoAS.logon(userName, password);
	}
	
	@POST
	@Path("token/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logon(@PathParam("token") String token){
		return AuntenticacaoAS.parseJWT(token);
	}

}
