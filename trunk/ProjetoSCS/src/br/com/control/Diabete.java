package br.com.control;

import android.content.ContentValues;
import android.content.Context;

public class Diabete {
	
	private static Banco _bd;	
	ContentValues c = new ContentValues();
	
	public String HASH                   = "";
	public String DT_VISITA              = "";
	public String FL_FAZ_DIETA           = "";
	public String FL_FAZ_EXCERCICIOS     = "";
	public String FL_USA_INSULINA        = "";
	public String FL_USA_HIPOGLICEMIANTE = "";
	public String DT_ULTIMA_VISITA       = "";																		 
	public String OBSERVACAO             = "";
	
	public void Limpar(){
		HASH                   = "";
		DT_VISITA              = "";
		FL_FAZ_DIETA           = "";
		FL_FAZ_EXCERCICIOS     = "";
		FL_USA_INSULINA        = "";
		FL_USA_HIPOGLICEMIANTE = "";
		DT_ULTIMA_VISITA       = "";																		 
		OBSERVACAO             = "";
	}
	
	public boolean Inserir(Context contexto){
		try{
			_bd = new Banco(contexto);
			
			c.put("HASH", HASH);   
			c.put("DT_VISITA",DT_VISITA);			
			c.put("FL_FAZ_DIETA",FL_FAZ_DIETA);		        		
			c.put("FL_FAZ_EXCERCICIOS",FL_FAZ_EXCERCICIOS);
			c.put("FL_USA_INSULINA",FL_USA_INSULINA);
			c.put("FL_USA_HIPOGLICEMIANTE",FL_USA_HIPOGLICEMIANTE);
			c.put("DT_ULTIMA_VISITA",DT_ULTIMA_VISITA);
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

}
