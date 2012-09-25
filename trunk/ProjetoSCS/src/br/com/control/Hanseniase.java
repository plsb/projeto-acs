package br.com.control;

import android.content.ContentValues;
import android.content.Context;

public class Hanseniase {
	
	private static Banco _bd;	
	ContentValues c = new ContentValues();
	
	public String HASH = ""; 		
	public String DT_ULTIMA_CONSULTA = "";		
	public String DT_ULTIMA_DOSE  = "";	
	public String TOMA_MEDICACAO = "";
	public String AUTO_CUIDADOS = "";
	public int COMUNICANTES_EXAMINADOS = 0;		   
	public String DT_VISITA  = "";			
	public int COMUNICANTES_BCG = 0;	
	public String OBS = "";
	public int NUMERO_COMUNICANTES = 0;		


    
    public boolean Inserir(Context contexto){
		try{
			_bd = new Banco(contexto);
			
			c.put("HASH", HASH); 		
			c.put("DT_ULTIMA_CONSULTA",DT_ULTIMA_CONSULTA);		
			c.put("DT_ULTIMA_DOSE",DT_ULTIMA_DOSE);
			c.put("TOMA_MEDICACAO",TOMA_MEDICACAO);
			c.put("AUTO_CUIDADOS",AUTO_CUIDADOS);
			c.put("COMUNICANTES_EXAMINADOS",COMUNICANTES_EXAMINADOS);	   
			c.put("DT_VISITA",DT_VISITA);			
			c.put("COMUNICANTES_BCG",COMUNICANTES_BCG);		        		
			c.put("NUMERO_COMUNICANTES",NUMERO_COMUNICANTES);
			c.put("OBSERVACAO",OBS);
			
			_bd.open();
			_bd.inserirRegistro("hanseniase", c);
			_bd.fechaBanco();
			Limpar();
			return true;
		}catch(Exception e){
			return false;
		}
	}
    
    public void Limpar(){
    	HASH 				     = ""; 		
    	DT_ULTIMA_CONSULTA 	     = "";		
    	DT_ULTIMA_DOSE           = "";	
    	DT_ULTIMA_DOSE           = "";
    	TOMA_MEDICACAO 		     = "";
    	AUTO_CUIDADOS 		     = "";	
    	OBS						 = "";
    	COMUNICANTES_EXAMINADOS  = 0;			
    	DT_VISITA 	             = "";		        		
    	COMUNICANTES_BCG 		 = 0;		
    	NUMERO_COMUNICANTES      = 0;	
 
    }
}
