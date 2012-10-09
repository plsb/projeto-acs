package br.com.control;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;

public class Diabete {
	
	private static Banco _bd;	
	private java.util.Date dtUltimaVisita = null;
	ContentValues c = new ContentValues();
	
	public String HASH                   = "";
	public String FL_FAZ_DIETA           = "";
	public String FL_FAZ_EXCERCICIOS     = "";
	public String FL_USA_INSULINA        = "";
	public String FL_USA_HIPOGLICEMIANTE = "";
	public String DT_ULTIMA_VISITA       = "";																		 
	public String OBSERVACAO             = "";
	
	public void Limpar(){
		HASH                   = "";
		FL_FAZ_DIETA           = "";
		FL_FAZ_EXCERCICIOS     = "";
		FL_USA_INSULINA        = "";
		FL_USA_HIPOGLICEMIANTE = "";
		DT_ULTIMA_VISITA       = "";																		 
		OBSERVACAO             = "";
		dtUltimaVisita         = null;
	}
	
	public boolean Inserir(Context contexto){
		try{
			_bd = new Banco(contexto);
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
			dtUltimaVisita = formatador.parse(DT_ULTIMA_VISITA);
			
			c.clear();
			c.put("HASH", HASH);   
			c.put("DT_VISITA",formatador.format(new Date(System.currentTimeMillis())));
			c.put("DT_ATUALIZACAO",formatador.format(new Date(System.currentTimeMillis())));		
			c.put("FL_FAZ_DIETA",FL_FAZ_DIETA);		        		
			c.put("FL_FAZ_EXCERCICIOS",FL_FAZ_EXCERCICIOS);
			c.put("FL_USA_INSULINA",FL_USA_INSULINA);
			c.put("FL_USA_HIPOGLICEMIANTE",FL_USA_HIPOGLICEMIANTE);
			c.put("DT_ULTIMA_VISITA",formatador.format(dtUltimaVisita));
			c.put("OBSERVACAO",OBSERVACAO);			
			_bd.open();
			_bd.inserirRegistro("diabete", c);
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
			dtUltimaVisita = formatador.parse(DT_ULTIMA_VISITA);
			c.clear();
			c.put("HASH", HASH);   
			c.put("DT_ATUALIZACAO",formatador.format(new Date(System.currentTimeMillis())));		
			c.put("FL_FAZ_DIETA",FL_FAZ_DIETA);		        		
			c.put("FL_FAZ_EXCERCICIOS",FL_FAZ_EXCERCICIOS);
			c.put("FL_USA_INSULINA",FL_USA_INSULINA);
			c.put("FL_USA_HIPOGLICEMIANTE",FL_USA_HIPOGLICEMIANTE);
			c.put("DT_ULTIMA_VISITA",formatador.format(dtUltimaVisita));
			c.put("OBSERVACAO",OBSERVACAO);
			_bd.open();
			_bd.atualizarDadosTabela("diabete",indice, c);
			_bd.fechaBanco();
			Limpar();
			return true;
		}catch(Exception e){
			return false;
		}
	}

}
