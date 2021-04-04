package br.com.unamaproject.server.enums;

public enum PerfilAcesso {
	
	ADMIN(1, "ROLE_ADMIN"),
	USUARIO(2, "ROLE_USUARIO");
	
	private int cod;
	private String descricao;
	
	private PerfilAcesso(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static PerfilAcesso toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for (PerfilAcesso p : PerfilAcesso.values()) {
			if (cod.equals(p.getCod())) {
				return p;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
