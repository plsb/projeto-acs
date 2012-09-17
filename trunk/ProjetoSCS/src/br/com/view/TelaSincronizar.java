package br.com.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.jdom2.Element;
import org.jdom2.JDOMException;

import br.com.control.Banco;
import br.com.control.CarregarXML;
import br.com.control.Mensagem;
import br.com.scs.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TelaSincronizar extends Activity implements OnClickListener{
	
	  private Button  btnImportarXmls,btnVisulizarUsuarios,btnVoltar;
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
			
			String msg = "";
			
			xml = new CarregarXML();	
			
			try {
				if (!(xml.carregar("usuarios.xml") == null)){		
					//I M P O R T A � � O   D E   U S U � R I O S
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
						c.put("USU_ATIVO", element.getChildText("ativo"));
						c.put("USU_FL_ADMIN", 1);						
						cAux = bd.consulta("usuarios", new String[] { "*" }, "USU_MATRICULA = ? ",  new String[] { element.getChildText("codigo") }, null, null, null, null);
						cAux.moveToFirst();						
						if (cAux.getCount() > 0){
							bd.atualizarDadosTabela("usuarios",Integer.valueOf(cAux.getString(cAux.getColumnIndex("_ID")).toString()),c);													
						}else{								
							bd.inserirRegistro("usuarios", c);							
						}//Fim else
					}//Fim while
					msg = msg + "Usu�rios - SIM\n";
					cAux.close();
					bd.fechaBanco();
				} else {
					msg = msg + "Usu�rios - N�O\n";
					Toast.makeText(this, "Arquivo XML de usu�rios n�o encontrado ou vazio!", Toast.LENGTH_LONG).show();
				}//Fim else
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JDOMException e) {
				e.printStackTrace();
			}
			
			//I M P O R T A � � O   D O S   L O G R A D O U R O S			
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
					msg = msg + "Logradouros - N�O\n";
					Toast.makeText(this, "Arquivo XML de logradouros n�o encontrado ou vazio!", Toast.LENGTH_LONG).show();
				}//Fim else
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JDOMException e) {
				e.printStackTrace();
			}
			
			Mensagem.exibeMessagem(this, "Importados:", msg);	
			
		}//Fim do Onclick do Bot�o Importar Usu�rio
				
		
		if (v==btnVisulizarUsuarios){
			
		}//Fim do M�todo OnClick da Importa��o de Ruas
		
		if (v == btnVoltar){
			finish();
		}
		
	}//Fim do M�todo onClick
	
	public void CarregarObjetos(){
		btnImportarXmls = (Button) findViewById(R.telaSincrozinar.btnImportarXmls);
		btnImportarXmls.setOnClickListener(this);
		btnVisulizarUsuarios = (Button) findViewById(R.telaSincrozinar.btnVisualizarUsuarios);
		btnVisulizarUsuarios.setOnClickListener(this);
		btnVoltar       = (Button) findViewById(R.telaSincrozinar.btnVoltar);
		btnVoltar.setOnClickListener(this);
	}

}
