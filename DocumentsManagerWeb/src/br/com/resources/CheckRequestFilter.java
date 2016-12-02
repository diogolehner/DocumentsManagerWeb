package br.com.resources;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.api.container.ContainerException;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

import br.com.as.AuntenticacaoAS;
import br.com.dto.TokenDTO;

@Provider
public class CheckRequestFilter implements ContainerRequestFilter {

	public static final String AUTHENTICATION_HEADER = "Authorization";
	
	@Context
	private HttpHeaders httpRequest;
	
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		if (!request.getRequestUri().getPath().contains("/auth/") && !request.getRequestUri().getPath().contains("/user/create")) {
			String authCredentials = request.getHeaderValue(AUTHENTICATION_HEADER);
	
			Response response = AuntenticacaoAS.parseJWT(authCredentials);
	
			if (Status.OK.getStatusCode()  !=  response.getStatus()) {
				throw new ContainerException((Exception) response.getEntity());
			}
			httpRequest.getRequestHeaders().add("idUsuario", ((TokenDTO)response.getEntity()).getIdUsuario());
			httpRequest.getRequestHeaders().add("userName", ((TokenDTO)response.getEntity()).getNomeUsuario());
			
		}
		return request;
	}

}
