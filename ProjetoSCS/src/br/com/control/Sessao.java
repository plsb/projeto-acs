package br.com.control;

public class Sessao {
	
	private String COD_USUARIO = "";
	private String SEN_USUARIO = "";
	static  Sessao SESSAO;
	
	public void setUsuario(String pCOD_USU,String pSEN_USU){
		if ((pCOD_USU != "")&&(pSEN_USU != "")){
			this.COD_USUARIO = pCOD_USU;
			this.SEN_USUARIO = pSEN_USU;
		}
	}
	
	public boolean TemUsuario(){
		if ((this.COD_USUARIO != "")&&(this.SEN_USUARIO != "")){
			return true;
		}else{
			return false;
		}
	}
	
	public static Sessao getSessao(){
		if (SESSAO == null){
			SESSAO = new Sessao();
			return SESSAO;
		}else{
			return SESSAO;
		}
	}
	
	public boolean FinalizaUsuario(){
		this.COD_USUARIO = "";
		this.SEN_USUARIO = "";
		if ((this.COD_USUARIO == "")&&(this.SEN_USUARIO == "")){
			return true;
		}else{
			return false;
		}
	}
	
	public String getCodUsuario(){
		return this.COD_USUARIO;
	}
	
	public String getMatriculaUsuario(){
		return this.COD_USUARIO;
	}

}
