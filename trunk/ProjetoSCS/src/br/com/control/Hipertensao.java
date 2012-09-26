package br.com.control;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;

public class Hipertensao {

	private static Banco _bd;	
	ContentValues c = new ContentValues();
	
	public String HASH              = "";
	//public String DT_VISITA         = "";
	public String FL_FAZ_DIETA      = "";
	public String FL_TOMA_MEDICACAO = "";
	public String FL_FAZ_EXERCICIOS = "";
	public String PRESSAO_ARTERIAL  = "";
	public String DT_ULTIMA_VISITA  = "";																		 
	public String OBSERVACAO        = "";
	
	public void Limpar(){
		HASH              = "";
		//DT_VISITA         = "";
		FL_FAZ_DIETA      = "";
		FL_TOMA_MEDICACAO = "";
		FL_FAZ_EXERCICIOS = "";
		PRESSAO_ARTERIAL  = "";
		DT_ULTIMA_VISITA  = "";																		 
		OBSERVACAO        = "";
	}//Fim do Método Limpar
	
	public boolean Inserir(Context contexto){
		try{
			_bd = new Banco(contexto);
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
			c.clear();
			c.put("HASH", HASH);   
			c.put("DT_VISITA",formatador.format(new Date(System.currentTimeMillis())));
			c.put("DT_ATUALIZACAO",formatador.format(new Date(System.currentTimeMillis())));		
			c.put("FL_FAZ_DIETA",FL_FAZ_DIETA);		        		
			c.put("FL_TOMA_MEDICACAO",FL_TOMA_MEDICACAO);
			c.put("FL_FAZ_EXERCICIOS",FL_FAZ_EXERCICIOS);
			c.put("PRESSAO_ARTERIAL",PRESSAO_ARTERIAL);
			c.put("DT_ULTIMA_VISITA",DT_ULTIMA_VISITA);
			c.put("OBSERVACAO",OBSERVACAO);
			
			_bd.open();
			_bd.inserirRegistro("hipertensao", c);
			_bd.fechaBanco();
			Limpar();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public boolean Atualizar(Context contexto,int indice){
		try{
			_bd = new Banco(contexto);
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
			c.clear();
			c.put("HASH", HASH);   
			c.put("DT_ATUALIZACAO",formatador.format(new Date(System.currentTimeMillis())));		
			c.put("FL_FAZ_DIETA",FL_FAZ_DIETA);		        		
			c.put("FL_TOMA_MEDICACAO",FL_TOMA_MEDICACAO);
			c.put("FL_FAZ_EXERCICIOS",FL_FAZ_EXERCICIOS);
			c.put("PRESSAO_ARTERIAL",PRESSAO_ARTERIAL);
			c.put("DT_ULTIMA_VISITA",DT_ULTIMA_VISITA);
			c.put("OBSERVACAO",OBSERVACAO);
			_bd.open();
			_bd.atualizarDadosTabela("hipertensao",indice, c);
			_bd.fechaBanco();
			Limpar();
			return true;
		}catch(Exception e){
			return false;
		}
	}
}
