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
import android.util.Log;

public class ResidenteAux {
	private static Banco _bd = null;	
	ContentValues c = new ContentValues();
	
	public String NOME;
	public String ENDERECO;
	public String NUMERO;
	public String DTNASCIMENTO;
	public String SEXO;
	public String FREQ_ESCOLA;
	public String ALFABETIZADO;
	public String OCUPACAO;
	public String FL_HANSENIASE;
	public String FL_HIPERTENSAO;
	public String FL_GESTANTE;
	public String FL_TUBERCULOSE;
	public String FL_ALCOLISMO;
	public String FL_CHAGAS;
	public String FL_DEFICIENTE;
	public String FL_MALARIA;
	public String FL_DIABETE;
	public String FL_EPILETICO;
	public String HASH;
	public String NOME_PAI;
	public String NOME_MAE;
	public String COMPLEMENTO;
	public String FL_OBITO;
	public String FL_MUDOU_SE;
	private java.util.Date dtNascimento; 
	
	public boolean Inserir(Context contexto){
		try{
			_bd = new Banco(contexto);
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
			dtNascimento = formatador.parse(DTNASCIMENTO);
			
			c.put("NOME", NOME);
			c.put("ENDERECO", ENDERECO.substring(ENDERECO.indexOf("-")+1));
			c.put("NUMERO", NUMERO);
			c.put("DTNASCIMENTO",formatador.format(dtNascimento));
			c.put("FREQ_ESCOLA", FREQ_ESCOLA);
			c.put("SEXO", SEXO);
			c.put("ALFABETIZADO", ALFABETIZADO);
			c.put("OCUPACAO", OCUPACAO);
			c.put("FL_HANSENIASE", FL_HANSENIASE);
			c.put("FL_HIPERTENSAO", FL_HIPERTENSAO);
			c.put("FL_GESTANTE", FL_GESTANTE);
			c.put("FL_TUBERCULOSE", FL_TUBERCULOSE);
			c.put("FL_ALCOLISMO", FL_ALCOLISMO);
			c.put("FL_CHAGAS", FL_CHAGAS);
			c.put("FL_DEFICIENTE", FL_DEFICIENTE);
			c.put("FL_MALARIA", FL_MALARIA);
			c.put("FL_DIABETE", FL_DIABETE);
			c.put("FL_EPILETICO", FL_EPILETICO);
			c.put("NOME_PAI", NOME_PAI);
			c.put("NOME_MAE", NOME_MAE);
			c.put("COMPLEMENTO", COMPLEMENTO);
			c.put("COD_ENDERECO", ENDERECO.substring(0, ENDERECO.indexOf("-")));
			c.put("HASH", HASH);
			c.put("FL_FALECEU", FL_OBITO);
			c.put("FL_MUDOU_SE", FL_MUDOU_SE);
			c.put("DATA_ATUALIZACAO", formatador.format(new Date(System.currentTimeMillis())));
			
			_bd.open();
			_bd.inserirRegistro("residente", c);
			_bd.fechaBanco();
			Limpar();
			return true;
		}catch(Exception e){
			Log.i("Erro no método Inserir Residente:", e.getMessage());
			return false;
		}
	}
	
	public boolean Atualizar(Context contexto,int indice){
		try{
			_bd = new Banco(contexto);
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
			dtNascimento = formatador.parse(DTNASCIMENTO);
			
			c.put("NOME", NOME);
			c.put("ENDERECO", ENDERECO.substring(ENDERECO.indexOf("-")+1));
			c.put("NUMERO", NUMERO);
			c.put("DTNASCIMENTO",formatador.format(dtNascimento));
			c.put("FREQ_ESCOLA", FREQ_ESCOLA);
			c.put("SEXO", SEXO);
			c.put("ALFABETIZADO", ALFABETIZADO);
			c.put("OCUPACAO", OCUPACAO);
			c.put("FL_HANSENIASE", FL_HANSENIASE);
			c.put("FL_HIPERTENSAO", FL_HIPERTENSAO);
			c.put("FL_GESTANTE", FL_GESTANTE);
			c.put("FL_TUBERCULOSE", FL_TUBERCULOSE);
			c.put("FL_ALCOLISMO", FL_ALCOLISMO);
			c.put("FL_CHAGAS", FL_CHAGAS);
			c.put("FL_DEFICIENTE", FL_DEFICIENTE);
			c.put("FL_MALARIA", FL_MALARIA);
			c.put("FL_DIABETE", FL_DIABETE);
			c.put("FL_EPILETICO", FL_EPILETICO);	
			c.put("NOME_PAI", NOME_PAI);
			c.put("NOME_MAE", NOME_MAE);
			c.put("COMPLEMENTO", COMPLEMENTO);
			c.put("COD_ENDERECO", ENDERECO.substring(0, ENDERECO.indexOf("-")));
			c.put("HASH", HASH);
			c.put("FL_FALECEU", FL_OBITO);
			c.put("FL_MUDOU_SE", FL_MUDOU_SE);
			c.put("DATA_ATUALIZACAO", formatador.format(new Date(System.currentTimeMillis())));
			_bd.open();
			_bd.atualizarDadosTabela("residente",indice, c);
			_bd.fechaBanco();
			Limpar();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public void Limpar(){
		NOME 		   = "";
		ENDERECO 	   = "";
		NUMERO 	       = "";
		DTNASCIMENTO   = "";
		SEXO 		   = "";
		FREQ_ESCOLA    = "";
		ALFABETIZADO   = "";
		OCUPACAO 	   = "";
		FL_HANSENIASE  = "";
		FL_HIPERTENSAO = "";
		FL_GESTANTE    = "";
		FL_TUBERCULOSE = "";
		FL_ALCOLISMO   = "";
		FL_CHAGAS      = "";
		FL_DEFICIENTE  = "";
		FL_MALARIA     = "";
		FL_DIABETE     = "";
		FL_EPILETICO   = "";
		HASH           = "";
		NOME_PAI       = "";
		NOME_MAE       = "";
		COMPLEMENTO    = "";
		FL_OBITO       = "";
		FL_MUDOU_SE    = "";
		DTNASCIMENTO   = null;
	}
	
}
