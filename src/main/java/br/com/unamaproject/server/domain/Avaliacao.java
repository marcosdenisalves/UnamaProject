package br.com.unamaproject.server.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "avaliacao")
public class Avaliacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer qtdEstrelas;
	
	@Column(columnDefinition = "text")
	private String comentario;
	
	private LocalDateTime dataAvaliacao;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "laboratorio_id")
	private Laboratorio laboratorio;
	
	public Avaliacao() {
	}

	public Avaliacao(Integer id, Integer qtdEstrelas, String comentario,
			LocalDateTime dataAvaliacao, Usuario usuario, Laboratorio laboratorio) {
		this.id = id;
		this.qtdEstrelas = qtdEstrelas;
		this.comentario = comentario;
		this.dataAvaliacao = dataAvaliacao;
		this.usuario = usuario;
		this.laboratorio = laboratorio;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQtdEstrelas() {
		return qtdEstrelas;
	}

	public void setQtdEstrelas(Integer qtdEstrelas) {
		this.qtdEstrelas = qtdEstrelas;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public LocalDateTime getDataAvaliacao() {
		return dataAvaliacao;
	}

	public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Avaliacao other = (Avaliacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}