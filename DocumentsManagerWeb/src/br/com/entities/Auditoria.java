package br.com.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity 
@Table(name="TB_USUARIO")
@NamedQueries({
    @NamedQuery(name = "audFind", query = "select aud from Auditoria aud order by aud.data desc "),
    })
public class Auditoria implements Serializable{
	private static final long serialVersionUID = -8828611186402285334L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AUD_ID")
    private Long id;

	@Column(name="AUD_ACAO", length=4000, nullable=false)
	private String acao;
	
	@Column(name="AUD_DATA", length=60, nullable=true)
	private String data;
	
	@ManyToOne (targetEntity = Pessoa.class, fetch = FetchType.EAGER)
	@JoinColumn (name = "AUD_PESID", nullable=true)
	private Pessoa pessoa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
}
