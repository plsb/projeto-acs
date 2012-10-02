package br.com.control;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;

public class VacinaAux {
	
	private static Banco _bd;	
	ContentValues c = new ContentValues();
	
	public String HASH         = "";
	public String TP_VACINA    = "";
	public String DS_VACINA    = "";
	public String DT_APLICACAO = "";
	public String TIPO         = "";
	public String LOTE         = "";
	public String FL_APLICADA  = "";
	
	public void Limpar(){
		HASH         = "";
		TP_VACINA    = "";
		DS_VACINA    = "";
		DT_APLICACAO = "";
		TIPO 		 = "";
		LOTE         = "";
		FL_APLICADA  = "";
	}
	
	public boolean Inserir(Context contexto){
		try{
			_bd = new Banco(contexto);
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
			c.clear();
			c.put("HASH", HASH);
			c.put("TIPO_VACINA", TP_VACINA);
			c.put("DOSE_APLICADA", DS_VACINA);
			c.put("DT_APLICACAO", DT_APLICACAO);
			c.put("TIPO", TIPO);
			c.put("LOTE", LOTE);
			c.put("FL_APLICADA", FL_APLICADA);
			c.put("DT_CADASTRO", formatador.format(new Date(System.currentTimeMillis())));
			
			_bd.open();
			_bd.inserirRegistro("vacinas", c);
			_bd.fechaBanco();
			Limpar();
			return true;
		}catch(Exception e){
			return false;
		}
	}//Fim do Método Inserir
	
	public boolean Atualizar(Context contexto,int indice){
		try{
			_bd = new Banco(contexto);			
			c.clear();
			c.put("HASH", HASH);
			c.put("TIPO_VACINA", TP_VACINA);
			c.put("DOSE_APLICADA", DS_VACINA);
			c.put("DT_APLICACAO", DT_APLICACAO);
			c.put("LOTE", LOTE);
			c.put("TIPO", TIPO);
			c.put("FL_APLICADA", FL_APLICADA);
			_bd.open();
			_bd.atualizarDadosTabela("vacinas",indice, c);
			_bd.fechaBanco();
			Limpar();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	

}
