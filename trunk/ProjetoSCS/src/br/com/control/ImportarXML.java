package br.com.control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.JDOMException;

import br.com.scs.MainActivity;
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
		
		try{
		
		boolean retorno = false;
		
		xml = new CarregarXML(); 
		bd = new Banco(this);
		Cursor cUsu = null;
		Cursor cBairro = null;
		Cursor cRuas = null;
		
		if(VerificaVersoesTabelas.jaVerificouAtualizacao == false){
			VerificaVersoesTabelas pg = VerificaVersoesTabelas.verificaVersoesTabelas(ImportarXML.this);
			VerificaVersoesTabelas.jaVerificouAtualizacao = true;
		}
		
		try {
			if (!(xml.carregar("scs.xml","usuario") == null)){		
				//I M P O R T A Ç Ã O   D E   U S U Á R I O S
				@SuppressWarnings("rawtypes")
				List<Element> usuarios = xml.carregar("scs.xml","usuario");
				bd.open();				
				ContentValues c = new ContentValues();	
				
				for(Element usuario : usuarios){
					c.clear();				
					c.put("USU_MATRICULA", usuario.getChildText("codigoUsuario"));
					c.put("USU_NOME", usuario.getChildText("nomeUsuario"));
					c.put("USU_LOGIN", usuario.getChildText("loginUsuario"));
					c.put("USU_SENHA", usuario.getChildText("senhaUsuario"));
					c.put("USU_COORDENADOR", usuario.getChildText("codigoUsuario"));
					c.put("USU_ATIVO", usuario.getChildText("ativoUsuario").trim());
					c.put("USU_FL_ADMIN", 1);
					cUsu = bd.consulta("usuarios", new String[] { "*" }, "USU_MATRICULA = ? ",  new String[] { usuario.getChildText("codigoUsuario") }, null, null, null, null);
					cUsu.moveToFirst();						
					if (cUsu.getCount() > 0){
						bd.atualizarDadosTabela("usuarios",Integer.valueOf(cUsu.getString(cUsu.getColumnIndex("_ID")).toString()),c);													
					}else{								
						bd.inserirRegistro("usuarios", c);							
					}//Fim else
				}	
				msg = msg + "Usuários - SIM\n";
				if(cUsu != null){
					cUsu = null;
				}
				retorno = true;
			} else {
				msg = msg + "Usuários - NÃO\n";
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
		
		//I M P O R T A Ç Ã O   D O S   B A I R R O S					
		try {
			if (!(xml.carregar("scs.xml","bairro") == null)){
				@SuppressWarnings("rawtypes")
				List<Element> bairros = xml.carregar("scs.xml","bairro");
				bd.open();
				ContentValues c = new ContentValues();					
				for(Element bairro : bairros){
					c.clear();				
					c.put("COD_RET", bairro.getChildText("codigoBairro"));
					c.put("DESCRICAO", bairro.getChildText("descricaoBairro"));
					c.put("CEP", bairro.getChildText("cepBairro"));
					cBairro = bd.consulta("usuarios", new String[] { "*" }, "USU_MATRICULA = ? ",  new String[] { bairro.getChildText("codigoBairro") }, null, null, null, null);
					cBairro.moveToFirst();						
					if (cBairro.getCount() > 0){
						bd.atualizarDadosTabela("bairros",Integer.valueOf(cBairro.getString(cBairro.getColumnIndex("_ID")).toString()),c);													
					}else{								
						bd.inserirRegistro("bairros", c);							
					}//Fim else
				}//Fim for
					msg = msg + "Bairros - SIM\n";
					if (cBairro != null){
						cBairro = null;
					}
					retorno = true;
				} else {
					msg = msg + "Bairros - NÃO\n";
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
		try {
			if (!(xml.carregar("scs.xml","rua") == null)){
				@SuppressWarnings("rawtypes")
				List<Element> ruas = xml.carregar("scs.xml","rua");	
				bd.open();
				ContentValues c = new ContentValues();					
				for(Element rua : ruas){				
					c.clear();					
					c.put("COD_RET", rua.getChildText("codigoRua"));					
					c.put("DESCRICAO", rua.getChildText("descricaoRua"));
					c.put("COD_MICROAREA", rua.getChildText("codigoMicroareaRua"));
					c.put("COD_AREA", rua.getChildText("codigoAreaRua"));
					c.put("COD_SEGMENTO", rua.getChildText("codigoSegmentoRua"));					
					c.put("USU_VINCULADO", rua.getChildText("codigoAgenteRua"));
					c.put("COD_BAIRRO", rua.getChildText("codigoBairroRua"));
					cRuas = bd.consulta("ruas", new String[] { "*" }, "COD_RET = ? ",  new String[] { rua.getChildText("codigoRua") }, null, null, null, null);
					cRuas.moveToFirst();
					
					if (cRuas.getCount() > 0){  
						bd.atualizarDadosTabela("ruas",Integer.valueOf(cRuas.getString(cRuas.getColumnIndex("_ID")).toString()),c);																			   						
					}else{						
						bd.inserirRegistro("ruas", c);						
					}//Fim else
				}//Fim while
				msg = msg + "Logradouros - SIM\n";
				if (cRuas != null){
					cRuas = null;
				}
				retorno = true;
			} else {
				msg = msg + "Logradouros - NÃO\n";
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
		
		bd.fechaBanco();
		
		return retorno;
		
		}catch(Exception e){
			Mensagem.exibeMessagem(this, "Erro", e.getMessage());
			System.out.println("Erro - "+e.getMessage());
			return false;
		}
		
	}//Fim do Método ImportarXml
			
	

}
