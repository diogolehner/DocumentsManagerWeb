package br.com.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name="TB_PESSOA")
public class Pessoa implements Serializable{
	private static final long serialVersionUID = 8632602738859409306L;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PES_ID")
    private Long id;

	@Column(name="PES_NOME", length=255, nullable=false)
	private String nome;
	
	@Column(name="PES_DOCUMENTO", length=20, nullable=false)
	private String documento;
	
	public Pessoa() {
		// TODO Auto-generated constructor stub
	}
	
	public Pessoa(String nome, String documento) {
		this.nome = nome;
		this.documento = documento;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Long getId() {
		return id;
	}
}
