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
@Table(name="TB_DOCUMENTO")
@NamedQueries({
    @NamedQuery(name = "documentFind", query = "select doc from Documento doc join doc.pessoa pes where pes.id = :pessoaID "),
    })
public class Documento implements Serializable{
	private static final long serialVersionUID = -8828611186402285334L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DOC_ID")
    private Long id;

	@Column(name="DOC_CONTEUDO", length=4000, nullable=true)
	private byte[] conteudo;
	
	@ManyToOne (targetEntity = Pessoa.class, fetch = FetchType.EAGER)
	@JoinColumn (name = "DOC_PESID", nullable=true)
	private Pessoa pessoa;
	
	public Documento() {}
	
	public Documento(byte[] conteudo, Pessoa pessoa) {
		this.conteudo = conteudo;
		this.pessoa = pessoa;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public byte[] getConteudo() {
		return conteudo;
	}

	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
