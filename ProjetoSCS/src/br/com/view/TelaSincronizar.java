package br.com.view;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Iterator;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.XMLOutputter;

import br.com.control.Banco;
import br.com.control.CarregarXML;
import br.com.control.ExportarXML;
import br.com.control.ImportarXML;
import br.com.control.Mensagem;
import br.com.scs.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TelaSincronizar extends Activity implements OnClickListener{
	
	  private Button  btnImportarXmls,btnVisulizarUsuarios,btnVoltar,btnExportarVisitas,btnConfigWS;
	  private Banco   bd;
	  private CarregarXML xml;
	
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        setContentView(R.layout.telasincronizar);
	        
	        CarregarObjetos();	
	  }  
	

	@SuppressLint("ParserError")
	public void onClick(View v) {
		
		if (v==btnImportarXmls){	
			
			Intent i = new Intent(this, ImportarXML.class);
			startActivity(i);
			
			/*String msg = "";
			
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
				} else {
					msg = msg + "Usuários - NÃO\n";
					Toast.makeText(this, "Arquivo XML de usuários não encontrado ou vazio!", Toast.LENGTH_LONG).show();
				}//Fim else
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JDOMException e) {
				e.printStackTrace();
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
				} else {
					msg = msg + "Logradouros - NÃO\n";
					Toast.makeText(this, "Arquivo XML de logradouros não encontrado ou vazio!", Toast.LENGTH_LONG).show();
				}//Fim else
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JDOMException e) {
				e.printStackTrace();
			}
			
			Mensagem.exibeMessagem(this, "Importados:", msg);	*/
			
		}//Fim do Onclick do Botão Importar Usuário
				
		
		if (v==btnVisulizarUsuarios){
			Intent i = new Intent(this, Lista_Usuarios.class);
			startActivity(i);
		}//Fim do Método OnClick da Importação de Ruas
		
		if (v == btnVoltar){
			finish();
		}
		
		if (v == btnExportarVisitas){
			Intent i = new Intent(this, ExportarXML.class);
			ExportarXML.Residencias = true;
			startActivity(i);			
		}	
		if (v == btnConfigWS){
			Mensagem.exibeMessagem(this, "Web Service", "Em Desenvolvimento");
		}
		
	}//Fim do Método onClick
	
	public void CarregarObjetos(){
		btnImportarXmls      = (Button) findViewById(R.telaSincrozinar.btnImportarXmls);
		btnVisulizarUsuarios = (Button) findViewById(R.telaSincrozinar.btnVisualizarUsuarios);
		btnVoltar            = (Button) findViewById(R.telaSincrozinar.btnVoltar);
		btnExportarVisitas   = (Button) findViewById(R.telaSincrozinar.btnExportarXML);
		btnConfigWS			 = (Button) findViewById(R.telaSincrozinar.btnConfigWS);
		btnImportarXmls.setOnClickListener(this);		
		btnVisulizarUsuarios.setOnClickListener(this);		
		btnVoltar.setOnClickListener(this);		
		btnExportarVisitas.setOnClickListener(this);
		btnConfigWS.setOnClickListener(this);
	}

}
