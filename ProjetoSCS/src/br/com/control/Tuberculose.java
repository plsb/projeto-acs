package br.com.control;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;

public class Tuberculose {
	
	private static Banco _bd;	
	ContentValues c = new ContentValues();
	
	public String HASH               = "";
	public String FL_MEDIC_DIARIA    = "";
	public String FL_REACOES_IND     = "";
	public String FL_EXAME_ESCARRO   = "";
	public String COMUNIC_EXAMINADOS = "";
	public String MENOR_BCG 		 = "";
	public String OBSERVACAO         = "";
	
	public void Limpar(){
		HASH			   = "";
		//DT_VISITA 		   = "";
		FL_MEDIC_DIARIA	   = "";
		FL_REACOES_IND	   = "";
		FL_EXAME_ESCARRO   = "";
		COMUNIC_EXAMINADOS = "";
		MENOR_BCG 		   = "";
		OBSERVACAO 		   = "";
	}
	
	public boolean Inserir(Context contexto){
		
		try{
			_bd = new Banco(contexto);
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
			c.clear();
			c.put("HASH", HASH);   
			c.put("DT_VISITA",formatador.format(new Date(System.currentTimeMillis())));
			c.put("DT_ATUALIZACAO",formatador.format(new Date(System.currentTimeMillis())));
			c.put("FL_MEDIC_DIARIA",FL_MEDIC_DIARIA);		        		
			c.put("FL_REACOES_IND",FL_REACOES_IND);
			c.put("FL_EXAME_ESCARRO",FL_EXAME_ESCARRO);
			c.put("COMUNIC_EXAMINADOS",COMUNIC_EXAMINADOS);
			c.put("MENOR_BCG",MENOR_BCG);
			c.put("OBSERVACAO",OBSERVACAO);
			
			_bd.open();
			_bd.inserirRegistro("tuberculose", c);
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
			c.put("FL_MEDIC_DIARIA",FL_MEDIC_DIARIA);		        		
			c.put("FL_REACOES_IND",FL_REACOES_IND);
			c.put("FL_EXAME_ESCARRO",FL_EXAME_ESCARRO);
			c.put("COMUNIC_EXAMINADOS",COMUNIC_EXAMINADOS);
			c.put("MENOR_BCG",MENOR_BCG);
			c.put("OBSERVACAO",OBSERVACAO);
			_bd.open();
			_bd.atualizarDadosTabela("tuberculose",indice, c);
			_bd.fechaBanco();
			Limpar();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
}
