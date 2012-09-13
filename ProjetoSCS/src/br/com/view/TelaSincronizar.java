package br.com.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.jdom2.Element;
import org.jdom2.JDOMException;

import br.com.control.Banco;
import br.com.control.CarregarXML;
import br.com.scs.R;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TelaSincronizar extends Activity implements OnClickListener{
	
	  private Button  btnImportarUsuario,btnImportarRuas,btnVoltar;
	  private Banco   bd;
	
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.telasincronizar);
	        
	        CarregarObjetos();	        
	  }

	public void onClick(View v) {
		
		if (v==btnImportarUsuario){
			CarregarXML xml = new CarregarXML();	
			
			try {
				if (!(xml.carregar("usuarios.xml") == null)){
					
					@SuppressWarnings("rawtypes")
					Iterator it = xml.carregar("usuarios.xml");
					Cursor cAux = null;
					bd = new Banco(this);
					bd.open();
					//bd.ExecutaSql("delete from usuarios");
					ContentValues c = new ContentValues();
					
					while (it.hasNext()){
						Element element = (Element) it.next();						
						c.clear();				
						c.put("USU_MATRICULA", element.getChildText("codigo"));
						c.put("USU_NOME", element.getChildText("nome"));
						c.put("USU_LOGIN", element.getChildText("login"));
						c.put("USU_SENHA", element.getChildText("senha"));
						c.put("USU_ATIVO", "S");
						c.put("USU_FL_ADMIN", 1);						
						cAux = bd.consulta("usuarios", new String[] { "*" }, "USU_MATRICULA = ? ",  new String[] { element.getChildText("codigo") }, null, null, null, null);
						cAux.moveToFirst();
						
						if (cAux.getCount() > 0){
							bd.atualizarDadosTabela("usuarios",Integer.valueOf(cAux.getString(cAux.getColumnIndex("_ID")).toString()),c);
							Toast.makeText(this,"Atualizado - "+	element.getChildText("nome"), Toast.LENGTH_SHORT).show();
						}else{						
							bd.inserirRegistro("usuarios", c);
							Toast.makeText(this,"Importado - "+	element.getChildText("nome"), Toast.LENGTH_SHORT).show();
						}//Fim else
					}//Fim while
					Toast.makeText(this,"Importação Concluída!", Toast.LENGTH_LONG).show();
					bd.fechaBanco();
				} else {
					Toast.makeText(this, "Arquivo XML não encontrado ou vazio!", Toast.LENGTH_LONG).show();
				}//Fim else
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JDOMException e) {
				e.printStackTrace();
			}
		}//Fim do Onclick do Botão Importar Usuário
		
		if (v==btnImportarRuas){
			CarregarXML xml = new CarregarXML();	
			
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
						    Toast.makeText(this,"Atualizado - "+	element.getChildText("descricao"), Toast.LENGTH_SHORT).show();
						}else{						
							bd.inserirRegistro("ruas", c);
							Toast.makeText(this,"Importado - "+	element.getChildText("descricao"), Toast.LENGTH_SHORT).show();
						}//Fim else
					}//Fim while
					bd.fechaBanco();
				} else {
					Toast.makeText(this, "Arquivo XML não encontrado ou vazio!", Toast.LENGTH_LONG).show();
				}//Fim else
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JDOMException e) {
				e.printStackTrace();
			}
		}//Fim do Método OnClick da Importação de Ruas
		
		if (v == btnVoltar){
			finish();
		}
		
	}//Fim do Método onClick
	
	public void CarregarObjetos(){
		btnImportarUsuario = (Button) findViewById(R.telaSincrozinar.btnImportarUsuario);
		btnImportarUsuario.setOnClickListener(this);
		btnImportarRuas    = (Button) findViewById(R.telaSincrozinar.btnImportarRuas);
		btnImportarRuas.setOnClickListener(this);
		btnVoltar          = (Button) findViewById(R.telaSincrozinar.btnVoltar);
		btnVoltar.setOnClickListener(this);
	}

}
