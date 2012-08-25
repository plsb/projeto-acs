package br.com.control;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;
import br.com.control.Banco;
import br.com.view.TelaResidencia;

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
	
	
	public boolean Inserir(Context contexto){
		try{
			_bd = new Banco(contexto);
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
			
			c.put("UF", UF);
			c.put("ENDERECO", ENDERECO);
			c.put("NUMERO", NUMERO);
			c.put("BAIRRO", BAIRRO);
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
			c.put("DATA_CADASTRO", formatador.format(new Date(System.currentTimeMillis())));
			_bd.open();
			_bd.inserirRegistro("residencia", c);
			_bd.fechaBanco();
			Limpar();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public void Limpar(){
		UF = "";
		ENDERECO = "";
		NUMERO = "";
		BAIRRO = "";
		CEP = "";
		MUNICIPIO = "";
		SEG_TERRIT = "";
		AREA = "";
		MICROAREA = "";
		COD_FAMILIA = "";
		DATA_CADASTRO = "";
		TIPO_CASA = "";
		DEST_LIXO = "";
		TRAT_AGUA = "";
		ABAST_AGUA = "";
		DEST_FEZES = "";
		CASO_DOENCA = "";
		MEIO_COMUNICACAO = "";
		PART_GRUPOS = "";
		MEIO_TRANSPORTE = "";
		
	}
}
