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

public class Gestante {
	
	private static Banco _bd;	
	private java.util.Date dtUltimaRegra     = null;
	private java.util.Date dtProvavelParto   = null;
	private java.util.Date dtPreNatal        = null;
	private java.util.Date dtConsultaPuerbio = null;
	
	ContentValues c = new ContentValues();
	
	public String HASH                = ""; 		
	public String DT_ULTIMA_REGRA     = "";	
	public String DT_PROVAVEL_PARTO   = "";	
	public String DT_CONSULTA_PUERBIO = "";
	public String DT_PRE_NATAL        = "";		
	public String EST_NUTRICIONAL     = "";		        		
	public String FATORES_RISCO       = "";		
	public String RESULTADO_GESTACAO  = "";	
	public String OBSERVACAO          = "";
	public String MES_GESTACAO        = "";
    
    public boolean Inserir(Context contexto){
		try{
			_bd = new Banco(contexto);
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
			dtUltimaRegra     = formatador.parse(DT_ULTIMA_REGRA);
	    	dtProvavelParto   = formatador.parse(DT_PROVAVEL_PARTO);
	    	dtPreNatal        = formatador.parse(DT_PRE_NATAL);
	    	//dtConsultaPuerbio = formatador.parse(DT_CONSULTA_PUERBIO);			
			c.clear();
			c.put("HASH", HASH); 		
			c.put("DT_ULTIMA_REGRA",formatador.format(dtUltimaRegra));		
			c.put("DT_PROVAVEL_PARTO",formatador.format(dtProvavelParto));
			c.put("DT_CONSULTA_PUERBIO","");//formatador.format(dtConsultaPuerbio));
			c.put("DT_PRE_NATAL",formatador.format(dtPreNatal));
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
			dtUltimaRegra     = formatador.parse(DT_ULTIMA_REGRA);
	    	dtProvavelParto   = formatador.parse(DT_PROVAVEL_PARTO);
	    	dtPreNatal        = formatador.parse(DT_PRE_NATAL);
	    	//dtConsultaPuerbio = formatador.parse(DT_CONSULTA_PUERBIO);	    	
			c.clear();
			c.put("HASH", HASH); 		
			c.put("DT_ULTIMA_REGRA",formatador.format(dtUltimaRegra));		
			c.put("DT_PROVAVEL_PARTO",formatador.format(dtProvavelParto));
			c.put("DT_CONSULTA_PUERBIO","");//formatador.format(dtConsultaPuerbio));
			c.put("DT_PRE_NATAL",formatador.format(dtPreNatal));
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
			System.out.println(e.getMessage());
			return false;
		}
	}
    
    public void Limpar(){
    	HASH 				= ""; 		
        DT_ULTIMA_REGRA 	= "";		
        DT_PROVAVEL_PARTO   = "";	
        DT_CONSULTA_PUERBIO = "";
        DT_PRE_NATAL 		= "";	
        EST_NUTRICIONAL 	= "";		        		
        FATORES_RISCO 		= "";		
        RESULTADO_GESTACAO  = "";	
        OBSERVACAO 			= "";
        MES_GESTACAO		= "";
        dtUltimaRegra       = null;
    	dtProvavelParto     = null;
    	dtPreNatal          = null;
    	dtConsultaPuerbio   = null;
    }
}
