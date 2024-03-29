/************************************************/
/** Projeto SCS - Sistema de Controle de Sa�de **/
/************ Desenvolvido por: *****************/
/********** Lucas de Souza Sales ****************/
/******* Karlos Kelvin Alves dos Santos *********/
/************* Pedro Luis Saraiva ***************/
/************************************************/
package br.com.control;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;

@SuppressLint("SimpleDateFormat")
public class Gestante {
	
	private static Banco _bd;	
	private java.util.Date dtUltimaRegra     = null;
	private java.util.Date dtProvavelParto   = null;
	private java.util.Date dtPreNatal        = null;
	private java.util.Date dtConsultaPuerbio = null;
	private java.util.Date dtUltimaConsulta  = null;
	
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
	public String DT_ULTIMA_CONSULTA  = "";
	public String FL_ACOMP_FINAL      = "";
    
    public boolean Inserir(Context contexto) {
		try{
			_bd = new Banco(contexto);
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
			dtUltimaRegra     = formatador.parse(DT_ULTIMA_REGRA);
	    	dtProvavelParto   = formatador.parse(DT_PROVAVEL_PARTO);
	    	dtPreNatal        = formatador.parse(DT_PRE_NATAL);
	    	dtUltimaConsulta  = formatador.parse(DT_ULTIMA_CONSULTA);
	    	dtConsultaPuerbio = formatador.parse(DT_CONSULTA_PUERBIO);			
			c.clear();
			c.put("HASH", HASH); 		
			c.put("DT_ULTIMA_REGRA",formatador.format(dtUltimaRegra));		
			c.put("DT_PROVAVEL_PARTO",formatador.format(dtProvavelParto));
			c.put("DT_CONSULTA_PUERBIO",formatador.format(dtConsultaPuerbio));
			c.put("DT_PRE_NATAL",formatador.format(dtPreNatal));
			c.put("DT_ULTIMA_CONSULTA",formatador.format(dtUltimaConsulta));
			c.put("EST_NUTRICIONAL",EST_NUTRICIONAL);		        		
			c.put("FATORES_RISCO",FATORES_RISCO);		
			c.put("RESULTADO_GESTACAO",RESULTADO_GESTACAO);	
			c.put("OBSERVACAO",OBSERVACAO);
			c.put("MES_GESTACAO",MES_GESTACAO);
			c.put("FL_ACOMP_FINAL",FL_ACOMP_FINAL);
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

	public boolean Atualizar(Context contexto,int indice) {
		try{
			_bd = new Banco(contexto);
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
			dtUltimaRegra     = formatador.parse(DT_ULTIMA_REGRA);
	    	dtProvavelParto   = formatador.parse(DT_PROVAVEL_PARTO);
	    	dtPreNatal        = formatador.parse(DT_PRE_NATAL);
	    	dtUltimaConsulta  = formatador.parse(DT_ULTIMA_CONSULTA);
	    	dtConsultaPuerbio = formatador.parse(DT_CONSULTA_PUERBIO);	    	
			c.clear();
			c.put("HASH", HASH); 		
			c.put("DT_ULTIMA_REGRA",formatador.format(dtUltimaRegra));		
			c.put("DT_PROVAVEL_PARTO",formatador.format(dtProvavelParto));
			c.put("DT_CONSULTA_PUERBIO",formatador.format(dtConsultaPuerbio));
			c.put("DT_PRE_NATAL",formatador.format(dtPreNatal));
			c.put("DT_ULTIMA_CONSULTA",formatador.format(dtUltimaConsulta));
			c.put("EST_NUTRICIONAL",EST_NUTRICIONAL);		        		
			c.put("FATORES_RISCO",FATORES_RISCO);		
			c.put("RESULTADO_GESTACAO",RESULTADO_GESTACAO);	
			c.put("OBSERVACAO",OBSERVACAO);
			c.put("MES_GESTACAO",MES_GESTACAO);
			c.put("FL_ACOMP_FINAL",FL_ACOMP_FINAL);
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
    
    public void Limpar() {
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
        FL_ACOMP_FINAL      = "";
        dtUltimaRegra       = null;
    	dtProvavelParto     = null;
    	dtPreNatal          = null;
    	dtConsultaPuerbio   = null;
    	dtUltimaConsulta    = null;
    }
}
