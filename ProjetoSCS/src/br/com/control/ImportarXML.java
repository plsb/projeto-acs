package br.com.control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.jdom2.Element;
import org.jdom2.JDOMException;

import br.com.scs.R;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class ImportarXML extends Activity {
	
	private CarregarXML xml;
	private Banco bd;
	
	String msg = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.geraxml);
		
		if (ImportarXmls()==true){
			Mensagem.exibeMessagem(this, "Importados:", msg,2000);	
		}else{
			Mensagem.exibeMessagem(this, "Erro:", msg,2000);
		}
		
		new Handler().postDelayed(new Runnable() {
			
			public void run() {
				finish();
			}
		}, 2000);
		
	}//Fim do OnCreate	
	
	public boolean ImportarXmls(){
		
		boolean retorno = false;
		
		xml = new CarregarXML();    	
		
		try {
			if (!(xml.carregar("usuarios.xml") == null)){		
				//I M P O R T A Ç Ã O   D E   U S U Á R I O S
				@SuppressWarnings("rawtypes")
				Iterator it = xml.carregar("usuarios.xml");
				Cursor cAux = null;
				bd = new Banco(this);
				bd.open();				
				ContentValues c = new ContentValues();									
				
				while (it.hasNext()){
					Element element = (Element) it.next();						
					c.clear();				
					c.put("USU_MATRICULA", element.getChildText("codigo"));
					c.put("USU_NOME", element.getChildText("nome"));
					c.put("USU_LOGIN", element.getChildText("login"));
					c.put("USU_SENHA", element.getChildText("senha"));
					c.put("USU_COORDENADOR", element.getChildText("codigo"));
					c.put("USU_ATIVO", element.getChildText("ativo").trim());
					c.put("USU_FL_ADMIN", 1);						
					cAux = bd.consulta("usuarios", new String[] { "*" }, "USU_MATRICULA = ? ",  new String[] { element.getChildText("codigo") }, null, null, null, null);
					cAux.moveToFirst();						
					if (cAux.getCount() > 0){
						bd.atualizarDadosTabela("usuarios",Integer.valueOf(cAux.getString(cAux.getColumnIndex("_ID")).toString()),c);													
					}else{								
						bd.inserirRegistro("usuarios", c);							
					}//Fim else
				}//Fim while
				msg = msg + "Usuários - SIM\n";
				cAux.close();
				bd.fechaBanco();
				retorno = true;
			} else {
				msg = msg + "Usuários - NÃO\n";
				Toast.makeText(this, "Arquivo XML de usuários não encontrado ou vazio!", Toast.LENGTH_LONG).show();
				retorno = true;
			}//Fim else
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			retorno = false;
		} catch (IOException e) {
			e.printStackTrace();
			retorno = false;
		} catch (JDOMException e) {
			e.printStackTrace();
			retorno = false;
		}
		
		//I M P O R T A Ç Ã O   D O S   L O G R A D O U R O S			
		xml = new CarregarXML();	
		
		try {
			if (!(xml.carregar("logradouros.xml") == null)){
				@SuppressWarnings("rawtypes")
				Iterator it = xml.carregar("logradouros.xml");
				Cursor cAux = null;
				bd = new Banco(this);
				bd.open();
				ContentValues c = new ContentValues();					
				while (it.hasNext()){
					Element element = (Element) it.next();						
					c.clear();				
					c.put("COD_RET", element.getChildText("codigo"));
					c.put("DESCRICAO", element.getChildText("descricao"));
					c.put("BAIRRO", element.getChildText("bairro"));
					c.put("USU_VINCULADO", element.getChildText("usuvinculado"));					
					cAux = bd.consulta("ruas", new String[] { "*" }, "COD_RET = ? ",  new String[] { element.getChildText("codigo") }, null, null, null, null);
					cAux.moveToFirst();
					
					if (cAux.getCount() > 0){
						bd.atualizarDadosTabela("ruas",Integer.valueOf(cAux.getString(cAux.getColumnIndex("_ID")).toString()),c);																			   						
					}else{						
						bd.inserirRegistro("ruas", c);						
					}//Fim else
				}//Fim while
				msg = msg + "Logradouros - SIM\n";
				cAux.close();
				bd.fechaBanco();
				retorno = true;
			} else {
				msg = msg + "Logradouros - NÃO\n";
				Toast.makeText(this, "Arquivo XML de logradouros não encontrado ou vazio!", Toast.LENGTH_LONG).show();
				retorno = true;
			}//Fim else
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			retorno = false;
		} catch (IOException e) {
			e.printStackTrace();
			retorno = false;
		} catch (JDOMException e) {
			e.printStackTrace();
			retorno = false;
		}
		
		//I M P O R T A R   R E S I D E N C I A S
		try {
			if (!(xml.carregar("residencias.xml") == null)){
				msg = msg + "Residências - SIM\n";
				retorno = true;
			}else{
				msg = msg + "Residências - NÃO\n";
				retorno = true;
			}
		} catch (FileNotFoundException e) {		
			e.printStackTrace();
			retorno = false;
		} catch (IOException e) {
			e.printStackTrace();
			retorno = false;
		} catch (JDOMException e) {
			e.printStackTrace();
			retorno = false;
		}
		
		return retorno;
		
	}//Fim do Método ImportarXml
			
	

}
