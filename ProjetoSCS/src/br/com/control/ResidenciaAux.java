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

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;

import br.com.control.Banco;

@SuppressLint("SimpleDateFormat")
public class ResidenciaAux {
	
	private static Banco _bd;	
	ContentValues c = new ContentValues();
	
	public String UF;
	public String ENDERECO;
	public String NUMERO;
	public String BAIRRO;
	public String CEP;
	public String MUNICIPIO;
	public String SEG_TERRIT;
	public String AREA;
	public String MICROAREA;
	public String COD_FAMILIA;
	public String DATA_CADASTRO;
	public String TIPO_CASA;
	public String DEST_LIXO;
	public String TRAT_AGUA;
	public String ABAST_AGUA;
	public String DEST_FEZES;
	public String CASO_DOENCA;
	public String MEIO_COMUNICACAO;
	public String PART_GRUPOS;
	public String MEIO_TRANSPORTE;
	public String TIPO_CASA_OUTROS;
	public String CASO_DOENCA_OUTROS;
	public String POSSUI_PLANO;
	public String NUM_COM_PLANO;
	public String NOME_PLANO;
	public String MEIO_COMUNICACAO_OUTROS;
	public String PART_GRUPOS_OUTROS;
	public String MEIO_TRANSPORTE_OUTROS;
	public String NUM_COMODOS;
	public String FL_ENERGIA;
	public String COMPLEMENTO;
	public String NOME_BENEFICIO;
	public String FL_APTO_BENEFICIO;
	
	
	public boolean Inserir(Context contexto){
		try{
			_bd = new Banco(contexto);
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
			
			//c.put("UF", UF);
			c.put("ENDERECO", ENDERECO.substring(ENDERECO.indexOf("-")+1));
			c.put("NUMERO", NUMERO.trim());
			c.put("BAIRRO", BAIRRO.substring(BAIRRO.indexOf("-")+1));
			c.put("CEP", CEP);
			c.put("MUNICIPIO", MUNICIPIO);
			c.put("SEG_TERRIT", SEG_TERRIT);
			c.put("AREA", AREA);
			c.put("MICROAREA", MICROAREA);
			c.put("COD_FAMILIA", COD_FAMILIA);
			c.put("TIPO_CASA", TIPO_CASA);
			c.put("DEST_LIXO", DEST_LIXO);
			c.put("TRAT_AGUA", TRAT_AGUA);
			c.put("ABAST_AGUA", ABAST_AGUA);
			c.put("DEST_FEZES", DEST_FEZES);
			c.put("CASO_DOENCA", CASO_DOENCA);
			c.put("MEIO_COMUNICACAO", MEIO_COMUNICACAO);
			c.put("PART_GRUPOS", PART_GRUPOS);
			c.put("MEIO_TRANSPORTE", MEIO_TRANSPORTE);
			c.put("TIPO_CASA_OUTROS", TIPO_CASA_OUTROS);
			c.put("CASO_DOENCA_OUTROS", CASO_DOENCA_OUTROS);
			c.put("COD_BAIRRO", BAIRRO.substring(0,BAIRRO.indexOf("-")));
			c.put("COD_ENDERECO", ENDERECO.substring(0,ENDERECO.indexOf("-")));
			c.put("DATA_CADASTRO", formatador.format(new Date(System.currentTimeMillis())));
			c.put("POSSUI_PLANO", POSSUI_PLANO);
			c.put("NUM_PESSOAS_COM_PLANO", NUM_COM_PLANO);
			c.put("NOME_PLANO_SAUDE", NOME_PLANO);
			c.put("MEIO_TRANSPORTE_OUTRO", MEIO_TRANSPORTE_OUTROS);
			c.put("PART_GRUPOS_OUTRO", PART_GRUPOS_OUTROS);
			c.put("MEIO_COMUNICACAO_OUTRO", MEIO_COMUNICACAO_OUTROS);
			c.put("NUM_COMODOS", NUM_COMODOS);
			c.put("FL_ENERGIA", FL_ENERGIA);
			c.put("COMPLEMENTO", COMPLEMENTO);
			c.put("FL_APTO_BENEFICIO", FL_APTO_BENEFICIO);
			c.put("NOME_BENEFICIO", NOME_BENEFICIO);
			
			_bd.open();
			_bd.inserirRegistro("residencia", c);
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
			
			//c.put("UF", UF);
			c.put("ENDERECO", ENDERECO.substring(ENDERECO.indexOf("-")+1));
			c.put("NUMERO", NUMERO.trim());
			c.put("BAIRRO", BAIRRO.substring(BAIRRO.indexOf("-")+1));
			c.put("CEP", CEP);
			c.put("MUNICIPIO", MUNICIPIO);
			c.put("SEG_TERRIT", SEG_TERRIT);
			c.put("AREA", AREA);
			c.put("MICROAREA", MICROAREA);
			c.put("COD_FAMILIA", COD_FAMILIA);
			c.put("DATA_CADASTRO", DATA_CADASTRO);
			c.put("TIPO_CASA", TIPO_CASA);
			c.put("DEST_LIXO", DEST_LIXO);
			c.put("TRAT_AGUA", TRAT_AGUA);
			c.put("ABAST_AGUA", ABAST_AGUA);
			c.put("DEST_FEZES", DEST_FEZES);
			c.put("CASO_DOENCA", CASO_DOENCA);
			c.put("MEIO_COMUNICACAO", MEIO_COMUNICACAO);
			c.put("PART_GRUPOS", PART_GRUPOS);
			c.put("MEIO_TRANSPORTE", MEIO_TRANSPORTE);
			c.put("TIPO_CASA_OUTROS", TIPO_CASA_OUTROS);
			c.put("CASO_DOENCA_OUTROS", CASO_DOENCA_OUTROS);
			c.put("COD_BAIRRO", BAIRRO.substring(0,BAIRRO.indexOf("-")));
			c.put("COD_ENDERECO", ENDERECO.substring(0,ENDERECO.indexOf("-")));
			c.put("DATA_CADASTRO", formatador.format(new Date(System.currentTimeMillis())));
			c.put("POSSUI_PLANO", POSSUI_PLANO);
			c.put("NUM_PESSOAS_COM_PLANO", NUM_COM_PLANO);
			c.put("NOME_PLANO_SAUDE", NOME_PLANO);
			c.put("MEIO_TRANSPORTE_OUTRO", MEIO_TRANSPORTE_OUTROS);
			c.put("PART_GRUPOS_OUTRO", PART_GRUPOS_OUTROS);
			c.put("MEIO_COMUNICACAO_OUTRO", MEIO_COMUNICACAO_OUTROS);
			c.put("NUM_COMODOS", NUM_COMODOS);
			c.put("FL_ENERGIA", FL_ENERGIA);
			c.put("COMPLEMENTO", COMPLEMENTO);
			c.put("FL_APTO_BENEFICIO", FL_APTO_BENEFICIO);
			c.put("NOME_BENEFICIO", NOME_BENEFICIO);
			
			_bd.open();
			_bd.atualizarDadosTabela("residencia",indice, c);
			_bd.fechaBanco();
			Limpar();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public void Limpar(){	
		ENDERECO		   		= "";
		NUMERO			   		= "";
		BAIRRO			   		= "";
		CEP				   		= "";
		MUNICIPIO		   		= "";
		SEG_TERRIT		   		= "";
		AREA	           		= "";
		MICROAREA		   		= "";
		COD_FAMILIA		   		= "";
		DATA_CADASTRO	   		= "";
		TIPO_CASA		   		= "";
		DEST_LIXO		   		= "";
		TRAT_AGUA		   		= "";
		ABAST_AGUA 		   		= "";
		DEST_FEZES 		   		= "";
		CASO_DOENCA        		= "";
		MEIO_COMUNICACAO   		= "";
		PART_GRUPOS		   		= "";
		MEIO_TRANSPORTE    		= "";
		TIPO_CASA_OUTROS   		= "";
		CASO_DOENCA_OUTROS 		= "";
		POSSUI_PLANO       		= "";
		NUM_COM_PLANO      		= "";
		NOME_PLANO		  		= "";
		MEIO_COMUNICACAO_OUTROS = "";
		PART_GRUPOS_OUTROS      = "";
		MEIO_TRANSPORTE_OUTROS  = "";
		NUM_COMODOS             = "";
		FL_ENERGIA              = "";
		COMPLEMENTO             = "";
		NOME_BENEFICIO          = "";
		FL_APTO_BENEFICIO       = "";
		
	}
}
