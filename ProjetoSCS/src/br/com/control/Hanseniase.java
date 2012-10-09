package br.com.control;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;

public class Hanseniase {
	
	private static Banco _bd;	
	private java.util.Date dtUltimaDose, dtUltimaConsulta = null;
	
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
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
			dtUltimaDose     = formatador.parse(DT_ULTIMA_DOSE);
			dtUltimaConsulta = formatador.parse(DT_ULTIMA_CONSULTA);
			
			c.clear();
			c.put("HASH", HASH); 		
			c.put("DT_ULTIMA_CONSULTA",formatador.format(dtUltimaConsulta));		
			c.put("DT_ULTIMA_DOSE",formatador.format(dtUltimaDose));
			c.put("TOMA_MEDICACAO",TOMA_MEDICACAO);
			c.put("AUTO_CUIDADOS",AUTO_CUIDADOS);
			c.put("COMUNICANTES_EXAMINADOS",COMUNICANTES_EXAMINADOS);	   
			c.put("DT_VISITA",formatador.format(new Date(System.currentTimeMillis())));
			c.put("DT_ATUALIZACAO",formatador.format(new Date(System.currentTimeMillis())));
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
    
    public boolean Atualizar(Context contexto,int indice){
		try{
			_bd = new Banco(contexto);
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
			dtUltimaDose     = formatador.parse(DT_ULTIMA_DOSE);
			dtUltimaConsulta = formatador.parse(DT_ULTIMA_CONSULTA);
			c.clear();
			c.put("HASH", HASH); 		
			c.put("DT_ULTIMA_CONSULTA",formatador.format(dtUltimaConsulta));		
			c.put("DT_ULTIMA_DOSE",formatador.format(dtUltimaDose));
			c.put("TOMA_MEDICACAO",TOMA_MEDICACAO);
			c.put("AUTO_CUIDADOS",AUTO_CUIDADOS);
			c.put("COMUNICANTES_EXAMINADOS",COMUNICANTES_EXAMINADOS);	   			
			c.put("DT_ATUALIZACAO",formatador.format(new Date(System.currentTimeMillis())));
			c.put("COMUNICANTES_BCG",COMUNICANTES_BCG);		        		
			c.put("NUMERO_COMUNICANTES",NUMERO_COMUNICANTES);
			c.put("OBSERVACAO",OBS);
			_bd.open();
			_bd.atualizarDadosTabela("hanseniase",indice, c);
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
    	TOMA_MEDICACAO 		     = "";
    	AUTO_CUIDADOS 		     = "";	
    	OBS						 = "";
    	COMUNICANTES_EXAMINADOS  = 0;			
    	DT_VISITA 	             = "";		        		
    	COMUNICANTES_BCG 		 = 0;		
    	NUMERO_COMUNICANTES      = 0;
    	dtUltimaDose 			 = null; 
    	dtUltimaConsulta         = null;
 
    }
}
