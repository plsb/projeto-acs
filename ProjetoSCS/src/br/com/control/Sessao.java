package br.com.control;

import java.sql.Date;
import java.text.SimpleDateFormat;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class Sessao {
	
	public static  Sessao SESSAO;
	
	public void setUsuario(Context context,String _Matricula,String _Nome){
		
		Banco bd = null;
		Cursor cAux = null;
		try{
			try{
				bd = new Banco(context);
				bd.open();
				if(VerificaVersoesTabelas.jaVerificouAtualizacao == false){
					VerificaVersoesTabelas pg = VerificaVersoesTabelas.verificaVersoesTabelas(context);
					VerificaVersoesTabelas.jaVerificouAtualizacao = true;
				}
				
				String dataAtual;
				SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
		    	dataAtual = data.format(new Date(System.currentTimeMillis()));
				
				ContentValues cv = new ContentValues();
				
				cv.clear();
				cv.put("USU_MATRICULA", _Matricula);
				cv.put("USU_NOME", _Nome);
				cv.put("DATA", dataAtual);
				
				cAux = bd.consulta("sessao", new String[]{ "*" }, null, null, null, null, null, null);
				
				cAux.moveToFirst();
				
				if (cAux.getCount() > 0){			
					bd.atualizarDadosTabela("sessao", 1, cv);
					Log.i("Log:", "Atualizada a sessao");
				} else {
					bd.inserirRegistro("sessao", cv);
					Log.i("Log:", "Inserido em sessao");
				}
			}catch(Exception e){
				Log.i("Método Sessao", e.getMessage());
			}
		}finally{
			if (cAux != null){
				cAux.close();
			}
			if (bd != null){
				bd.fechaBanco();
			}
		}
	}
	
	public boolean TemUsuario(Context context){
		Banco bd = null;
		Cursor cAux = null;
		boolean retorno = false;
		try{
			try{
				bd = new Banco(context);
				bd.open();
				
				if(VerificaVersoesTabelas.jaVerificouAtualizacao == false){
					VerificaVersoesTabelas pg = VerificaVersoesTabelas.verificaVersoesTabelas(context);
					VerificaVersoesTabelas.jaVerificouAtualizacao = true;
				}
				
				String dataAtual;
				SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
		    	dataAtual = data.format(new Date(System.currentTimeMillis()));			
				
				cAux = bd.consulta("sessao", new String[]{ "*" }, null, null, null, null, null, null);
				
				cAux.moveToFirst();
				
				if (cAux.getCount() > 0){			
					retorno = true;
				} else {
					retorno = false;
				}
			}catch(Exception e){
				Log.i("Método Sessao", e.getMessage());
			}
		}finally{
			if (cAux != null){
				cAux.close();
			}
			if (bd != null){
				bd.fechaBanco();
			}
		}
		return retorno;
	}
	
	public static Sessao getSessao(){
		if (SESSAO == null){
			SESSAO = new Sessao();
			return SESSAO;
		}else{
			return SESSAO;
		}
	}
	
	/*public static boolean FinalizaUsuario(){
		this.COD_USUARIO = "";
		this.SEN_USUARIO = "";
		if ((this.COD_USUARIO == "")&&(this.SEN_USUARIO == "")){
			return true;
		}else{
			return false;
		}
	}*/
	
	public String getMatriculaUsuario(Context context){
		Banco bd = null;
		Cursor cAux = null;
		String retorno = "";
		try{
			try{
				bd = new Banco(context);
				bd.open();
				
				cAux = bd.consulta("sessao", new String[]{ "*" }, null, null, null, null, null, null);
				
				cAux.moveToFirst();
				
				if (cAux.getCount() > 0){			
					retorno = cAux.getString(cAux.getColumnIndex("USU_MATRICULA")).toString();
				} else {
					retorno = "NULL";
				}
			}catch(Exception e){
				Log.i("Método Sessao", e.getMessage());
			}
		}finally{
			if (cAux != null){
				cAux.close();
			}
			if (bd != null){
				bd.fechaBanco();
			}
		}
		return retorno;
	}
	
	public String getNomeUsuario(Context context){
		Banco bd = null;
		Cursor cAux = null;
		String retorno = "";
		try{
			try{
				bd = new Banco(context);
				bd.open();
				
				cAux = bd.consulta("sessao", new String[]{ "*" }, null, null, null, null, null, null);
				
				cAux.moveToFirst();
				
				if (cAux.getCount() > 0){			
					retorno = cAux.getString(cAux.getColumnIndex("USU_NOME")).toString();
				} else {
					retorno = "NULL";
				}
			}catch(Exception e){
				Log.i("Método Sessao", e.getMessage());
			}
		}finally{
			if (cAux != null){
				cAux.close();
			}
			if (bd != null){
				bd.fechaBanco();
			}
		}
		return retorno;
	}

}
