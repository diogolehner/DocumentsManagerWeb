package br.com.resources;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

@Provider
public class CheckRequestFilter implements ContainerRequestFilter {

	public static final String AUTHENTICATION_HEADER = "Authorization";
	
	@Context
	private HttpHeaders httpRequest;
	
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		if (!request.getRequestUri().getPath().contains("/auth/") &&
				!request.getRequestUri().getPath().contains("/user/create")) {
			String authCredentials = request.getHeaderValue(AUTHENTICATION_HEADER);
	
//			Response response = BOAutenticacao.parseJWT(authCredentials);
//	
//			if (Status.OK.getStatusCode()  !=  response.getStatus()) {
//				throw new ContainerException((Exception) response.getEntity());
//			}
//			httRequest.getRequestHeaders().add("idUsuarioLogado", ((TokenDTO)response.getEntity()).getIdUsuario());
//			httRequest.getRequestHeaders().add("userName", ((TokenDTO)response.getEntity()).getNomeUsuario());
			
		}
		return request;
	}

}
