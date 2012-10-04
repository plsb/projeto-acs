package br.com.control;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class VacinaAux {
	
	private static Banco _bd;	
	ContentValues c = new ContentValues();
	
	public String HASH         = "";
	public String TP_VACINA    = "";
	public String DS_VACINA    = "";
	public String DT_APLICACAO = "";
	public String TIPO         = "";
	public String LOTE         = "";
	public String FL_APLICADA  = "";
	
	public void Limpar(){
		HASH         = "";
		TP_VACINA    = "";
		DS_VACINA    = "";
		DT_APLICACAO = "";
		TIPO 		 = "";
		LOTE         = "";
		FL_APLICADA  = "";
	}
	
	public boolean Inserir(Context contexto){
		boolean retorno = false;
		try{
			_bd = new Banco(contexto);
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
			c.clear();
			c.put("HASH", HASH);
			c.put("TIPO_VACINA", TP_VACINA);
			c.put("DOSE_APLICADA", DS_VACINA);
			c.put("DT_APLICACAO", DT_APLICACAO);
			c.put("TIPO", TIPO);
			c.put("LOTE", LOTE);
			c.put("FL_APLICADA", FL_APLICADA);
			c.put("DT_CADASTRO", formatador.format(new Date(System.currentTimeMillis())));
			
			try{
				_bd.open();
				Cursor cursor = _bd.consulta("vacinas", new String[] { "_ID, HASH, TIPO_VACINA, DOSE_APLICADA, TIPO " }, "HASH = '"+HASH+
																	   "' AND TIPO_VACINA = '"+TP_VACINA+
																       "' AND DOSE_APLICADA = '"+DS_VACINA+
																       "' AND TIPO = '"+TIPO+"'", null, null, null, null, null);
				cursor.moveToFirst();
				if (cursor.getCount() > 0){
					_bd.atualizarDadosTabela("vacinas",Integer.valueOf(cursor.getString(cursor.getColumnIndex("_ID")).toString().trim()), c);
					cursor.close();
					retorno = true;					
				}else{
					_bd.inserirRegistro("vacinas", c);	
					retorno = true;
				}
				
			}catch(Exception e){
				System.out.println("Erro no método Salvar VacinaAux");
			}
			_bd.fechaBanco();
			Limpar();
		}catch(Exception e){
			retorno = false;
		}
		return retorno;
	}//Fim do Método Inserir
	
	public boolean Atualizar(Context contexto,int indice){
		try{
			_bd = new Banco(contexto);			
			c.clear();
			c.put("HASH", HASH);
			c.put("TIPO_VACINA", TP_VACINA);
			c.put("DOSE_APLICADA", DS_VACINA);
			c.put("DT_APLICACAO", DT_APLICACAO);
			c.put("LOTE", LOTE);
			c.put("TIPO", TIPO);
			c.put("FL_APLICADA", FL_APLICADA);
			_bd.open();
			_bd.atualizarDadosTabela("vacinas",indice, c);
			_bd.fechaBanco();
			Limpar();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	

}
