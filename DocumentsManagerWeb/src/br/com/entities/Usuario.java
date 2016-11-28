package br.com.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.types.TipoNivelUsuario;

@Entity 
@Table(name="TB_USUARIO")
@NamedQueries({
    @NamedQuery(name = "usuarioFindUserPass", query = "select usu from Usuario usu where usu.logon = :logon and usu.password = :password "),
    @NamedQuery(name = "usuarioFindUser", query = "select usu from Usuario usu join usu.pessoa pes order by pes.nome "),
    })
public class Usuario implements Serializable{
	private static final long serialVersionUID = -8828611186402285334L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USU_ID")
    private Long id;

	@Column(name="USU_LOGON", length=255, nullable=false)
	private String logon;
	
	@Column(name="USU_PASSWORD", length=255, nullable=false)
	private String password;
	
	@Column (name = "USU_NIVUSU", nullable=true, length=20)
	@Enumerated(EnumType.STRING)
	private TipoNivelUsuario nivelUsuario = TipoNivelUsuario.REQUESTER_1;
	
	@ManyToOne (targetEntity = Pessoa.class, fetch = FetchType.EAGER)
	@JoinColumn (name = "USU_PESID", nullable=true)
	private Pessoa pessoa;
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	
	public Usuario(String logon, String password, Pessoa pessoa) {
		this.logon = logon;
		this.password = password;
		this.pessoa = pessoa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogon() {
		return logon;
	}

	public void setLogon(String logon) {
		this.logon = logon;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TipoNivelUsuario getNivelUsuario() {
		return nivelUsuario;
	}

	public void setNivelUsuario(TipoNivelUsuario nivelUsuario) {
		this.nivelUsuario = nivelUsuario;
	}
	
}
