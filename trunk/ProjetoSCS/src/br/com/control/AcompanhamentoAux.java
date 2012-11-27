package br.com.control;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;

public class AcompanhamentoAux {
	
	private static Banco _bd;
	
	ContentValues c = new ContentValues();
	
	public String FL_HOSPITALIZADA;
	public String MOTIVO_HOSPITALIZACAO;
	public String FL_DOENTE;
	public String DESC_DOENCA;
	public String OBS;	
	public String HASH;
	
	public void Limpar() {
		FL_HOSPITALIZADA	  = "";
		MOTIVO_HOSPITALIZACAO = "";
		FL_DOENTE             = "";
		DESC_DOENCA		      = "";		
		OBS 	         	  = "";
		HASH                  = "";
	}
	
	public boolean Inserir(Context contexto){
		try{
			_bd = new Banco(contexto);
			
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
						
			c.clear();
			c.put("HASH", HASH);   
			c.put("DT_VISITA",formatador.format(new Date(System.currentTimeMillis())));
			c.put("DT_ATUALIZACAO",formatador.format(new Date(System.currentTimeMillis())));		
			c.put("FL_HOSPITALIZADA",FL_HOSPITALIZADA);		        		
			c.put("MOTIVO_HOSPITALIZACAO",MOTIVO_HOSPITALIZACAO);
			c.put("FL_DOENTE",FL_DOENTE);
			c.put("DESC_DOENCA",DESC_DOENCA);
			c.put("OBSERVACAO",OBS);
			
			_bd.open();
			_bd.inserirRegistro("acompanhamento", c);
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
			c.put("FL_HOSPITALIZADA",FL_HOSPITALIZADA);		        		
			c.put("MOTIVO_HOSPITALIZACAO",MOTIVO_HOSPITALIZACAO);
			c.put("FL_DOENTE",FL_DOENTE);
			c.put("DESC_DOENCA",DESC_DOENCA);
			c.put("OBSERVACAO",OBS);
			_bd.open();
			_bd.atualizarDadosTabela("acompanhamento",indice, c);
			_bd.fechaBanco();
			Limpar();
			return true;
		}catch(Exception e){
			return false;
		}
	}

}
