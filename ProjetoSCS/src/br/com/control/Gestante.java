package br.com.control;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;

public class Gestante {
	
	private static Banco _bd;	
	ContentValues c = new ContentValues();
	
	public String HASH = ""; 		
	public String DT_ULTIMA_REGRA = "";	
	public String DT_PROVAVEL_PARTO  = "";	
	public String DT_CONSULTA_PUERBIO = "";
	public String DT_PRE_NATAL = "";
	public String TIPO_VACINA = "";		   
	public String DT_VACINA = "";			
	public String EST_NUTRICIONAL = "";		        		
	public String FATORES_RISCO = "";		
	public String RESULTADO_GESTACAO = "";	
	public String OBSERVACAO = "";
	public String MES_GESTACAO = "";
    
    public boolean Inserir(Context contexto){
		try{
			_bd = new Banco(contexto);
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
			c.clear();
			c.put("HASH", HASH); 		
			c.put("DT_ULTIMA_REGRA",DT_ULTIMA_REGRA);		
			c.put("DT_PROVAVEL_PARTO",DT_PROVAVEL_PARTO);
			c.put("DT_CONSULTA_PUERBIO",DT_CONSULTA_PUERBIO);
			c.put("DT_PRE_NATAL",DT_PRE_NATAL);
			c.put("TIPO_VACINA",TIPO_VACINA);	   
			c.put("DT_VACINA",DT_VACINA);			
			c.put("EST_NUTRICIONAL",EST_NUTRICIONAL);		        		
			c.put("FATORES_RISCO",FATORES_RISCO);		
			c.put("RESULTADO_GESTACAO",RESULTADO_GESTACAO);	
			c.put("OBSERVACAO",OBSERVACAO);
			c.put("MES_GESTACAO",MES_GESTACAO);
			c.put("DT_VISITA", formatador.format(new Date(System.currentTimeMillis())));
			c.put("DT_ATUALIZACAO", formatador.format(new Date(System.currentTimeMillis())));
			
			_bd.open();
			_bd.inserirRegistro("gestacao", c);
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
			c.put("DT_ULTIMA_REGRA",DT_ULTIMA_REGRA);		
			c.put("DT_PROVAVEL_PARTO",DT_PROVAVEL_PARTO);
			c.put("DT_CONSULTA_PUERBIO",DT_CONSULTA_PUERBIO);
			c.put("DT_PRE_NATAL",DT_PRE_NATAL);
			c.put("TIPO_VACINA",TIPO_VACINA);	   
			c.put("DT_VACINA",DT_VACINA);			
			c.put("EST_NUTRICIONAL",EST_NUTRICIONAL);		        		
			c.put("FATORES_RISCO",FATORES_RISCO);		
			c.put("RESULTADO_GESTACAO",RESULTADO_GESTACAO);	
			c.put("OBSERVACAO",OBSERVACAO);
			c.put("MES_GESTACAO",MES_GESTACAO);
			c.put("DT_ATUALIZACAO", formatador.format(new Date(System.currentTimeMillis())));
			_bd.open();
			_bd.atualizarDadosTabela("gestacao",indice, c);
			_bd.fechaBanco();
			Limpar();
			return true;
		}catch(Exception e){
			return false;
		}
	}
    
    public void Limpar(){
    	HASH 				= ""; 		
        DT_ULTIMA_REGRA 	= "";		
        DT_PROVAVEL_PARTO   = "";	
        DT_CONSULTA_PUERBIO = "";
        DT_PRE_NATAL 		= "";
        TIPO_VACINA 		= "";		   
        DT_VACINA 			= "";			
        EST_NUTRICIONAL 	= "";		        		
        FATORES_RISCO 		= "";		
        RESULTADO_GESTACAO  = "";	
        OBSERVACAO 			= "";
        MES_GESTACAO		= "";
    }
}
