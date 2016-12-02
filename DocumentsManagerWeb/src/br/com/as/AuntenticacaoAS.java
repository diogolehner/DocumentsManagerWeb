package br.com.as;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.bind.DatatypeConverter;

import org.joda.time.LocalDateTime;

import br.com.constantes.SystemConstants;
import br.com.crypt.CryptoUtils;
import br.com.dto.TokenDTO;
import br.com.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

public final class AuntenticacaoAS {
	
	private AuntenticacaoAS(){
		
	}
	
	public static TokenDTO logon(String userName, String password){
		try{
			Usuario usuario = UsuarioAS.getUsuario(CryptoUtils.criptografaAES(userName), CryptoUtils.criptografaAES(password));
			return createJWT(usuario.getId(), userName, usuario.getNivelUsuario().getCodigo(), 1800000l);
		}catch(Exception e){
			ResponseBuilder builder = null;
			builder = Response.status(Response.Status.UNAUTHORIZED);
			throw new WebApplicationException(builder.build());
		}
	}
	
	public static TokenDTO createJWT(Long id, String nome, String permissao, long ttlMillis) {

		//The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		//We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SystemConstants.chaveMestre);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		//Let's set the JWT Claims
		String jwt = Jwts.builder().setId(UUID.randomUUID().toString()).compact();

		JwtBuilder builder = Jwts.builder().setId(jwt)
				.setIssuedAt(now)
				.claim("id", id)
				.claim("nome", nome)
				.signWith(signatureAlgorithm, signingKey);

		//if it has been specified, let's add the expiration
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		
		TokenDTO tokenDTO = new TokenDTO();
		tokenDTO.setToken(builder.compact());
		tokenDTO.setIdUsuario(id.toString());
		tokenDTO.setNomeUsuario(nome);
		tokenDTO.setPermissao(permissao);

		//Builds the JWT and serializes it to a compact, URL-safe string
		return tokenDTO;
	}
	
	//Sample method to validate and read the JWT
	public static Response parseJWT(String jwt) {
		//This line will throw an exception if it is not a signed JWS (as expected)
		Claims claims;        
		try{
			claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SystemConstants.chaveMestre))
					.parseClaimsJws(jwt).getBody();
			LocalDateTime dataHoraExpiracao = new LocalDateTime(claims.getExpiration().getTime());
			if (dataHoraExpiracao.isBefore(new LocalDateTime())) {
				return Response.status(401).type(MediaType.APPLICATION_JSON).entity(new Exception("O token informado est� expirado")).build();
			}
			
		}catch(MalformedJwtException e){
			return Response.status(401).type(MediaType.APPLICATION_JSON).entity(new Exception("O token informado � inv�lido")).build();
		}
		
		TokenDTO tokenDTO = new TokenDTO();
		tokenDTO.setIdUsuario(((Integer)claims.get("id")).toString());
		tokenDTO.setNomeUsuario((String)claims.get("nome"));
		
		return Response.ok(tokenDTO).build();
	}

}
