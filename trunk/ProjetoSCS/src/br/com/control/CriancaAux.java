/************************************************/
/** Projeto SCS - Sistema de Controle de Saúde **/
/************ Desenvolvido por: *****************/
/********** Lucas de Souza Sales ****************/
/******* Karlos Kelvin Alves dos Santos *********/
/************* Pedro Luis Saraiva ***************/
/************************************************/
package br.com.control;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;

public class CriancaAux {
	
	private static Banco _bd;
	
	ContentValues c = new ContentValues();
	
	public String ALTURA;
	public String PESO;
	public String PER_CEFALICO;
	public String APGAR5;
	public String TP_PARTO;
	public String OBS;
	public String SITUACAO;
	public String HASH;
	
	public void Limpar() {
		ALTURA		 = "";
		PESO		 = "";
		PER_CEFALICO = "";
		APGAR5		 = "";
		TP_PARTO 	 = "";
		OBS 		 = "";
		SITUACAO 	 = "";
		HASH         = "";
	}
	
	public boolean Inserir(Context contexto){
		try{
			_bd = new Banco(contexto);
			
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
						
			c.clear();
			c.put("HASH", HASH);   
			c.put("DT_VISITA",formatador.format(new Date(System.currentTimeMillis())));
			c.put("DT_ATUALIZACAO",formatador.format(new Date(System.currentTimeMillis())));		
			c.put("ALTURA",ALTURA);		        		
			c.put("PESO",PESO);
			c.put("PER_CEFALICO",PER_CEFALICO);
			c.put("APGAR5",APGAR5);
			c.put("TP_PARTO",TP_PARTO);
			c.put("SITUACAO",SITUACAO);
			c.put("OBSERVACAO",OBS);
			
			_bd.open();
			_bd.inserirRegistro("crianca", c);
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
			c.put("ALTURA",ALTURA);		        		
			c.put("PESO",PESO);
			c.put("PER_CEFALICO",PER_CEFALICO);
			c.put("APGAR5",APGAR5);
			c.put("TP_PARTO",TP_PARTO);
			c.put("SITUACAO",SITUACAO);
			c.put("OBSERVACAO",OBS);
			_bd.open();
			_bd.atualizarDadosTabela("crianca",indice, c);
			_bd.fechaBanco();
			Limpar();
			return true;
		}catch(Exception e){
			return false;
		}
	}

}
