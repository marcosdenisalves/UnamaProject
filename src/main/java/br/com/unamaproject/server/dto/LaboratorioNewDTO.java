package br.com.unamaproject.server.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class LaboratorioNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String nome;

	@NotEmpty
	private String imgUrl;
	private String descricao;

	public LaboratorioNewDTO(String nome, String imgUrl,  String descricao) {
		this.nome = nome;
		this.imgUrl = imgUrl;
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
