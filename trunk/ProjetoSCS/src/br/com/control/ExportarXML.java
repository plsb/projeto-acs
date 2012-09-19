package br.com.control;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import br.com.scs.R;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;

public class ExportarXML extends Activity {
	
	public static boolean Residencias = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.geraxml);
		
		if (Residencias==true){
			if (ExportarResidencias(this)==true){
				Mensagem.exibeMessagem(this,"SCS","Exportação Realizada!",2000);
				
					new Handler().postDelayed(new Runnable() {
					
						public void run() {
							finish();
						}
					}, 2000);
			}
		}
	}	
	
	public boolean ExportarResidencias(Context c){
		
		try{
			Banco bd    = new Banco(c);
			Cursor csr  = null;
			bd.open();
			csr = bd.consulta("residencia", new String[] {"*"}, null, null, null, null, "_ID", "");
			csr.moveToFirst();
			
			if (csr.getCount()>0){
				
				Element RESIDENCIA = new Element("RESIDENCIAS");				
				do{	        	 
					Element DADOS       	 = new Element("DADOS");				
					Element ID           	 = new Element("ID");
					//Element UF           	 = new Element("UF");
					Element ENDERECO     	 = new Element("ENDERECO");
					Element NUMERO       	 = new Element("NUMERO");
					Element BAIRRO       	 = new Element("BAIRRO");
					Element CEP          	 = new Element("CEP");
					Element MUNICIPIO    	 = new Element("MUNICIPIO");
					Element SEG_TERRIT   	 = new Element("SEGTERRITORIAL");
					Element AREA 		 	 = new Element("AREA");
					Element MICROAREA    	 = new Element("MICROAREA");
					Element COD_FAMILIA  	 = new Element("CODFAMILIA");
					Element DATA_CADASTRO	 = new Element("DATACADASTRO");
					Element TIPO_CASA    	 = new Element("TIPOCASA");
					Element DEST_LIXO     	 = new Element("DESTLIXO");
					Element TRAT_AGUA    	 = new Element("TRATAMENTOAGUA");
					Element ABAST_AGUA   	 = new Element("ABASTECIMENTOAGUA");
					Element DEST_FEZES   	 = new Element("DESTFEZES");
					Element CASO_DOENCA      = new Element("CASODOENCA");
					Element MEIO_COMUNICACAO = new Element("MEIOCOMUNICACAO");
					Element PART_GRUPOS		 = new Element("PARTICIPAGRUPO");
					Element MEIO_TRANSPORTE  = new Element("MEIOTRANSPORTE");
					
					DADOS.addContent(ID.setText(csr.getString(csr.getColumnIndex("_ID")).toString()));
					//DADOS.addContent(UF.setText(csr.getString(csr.getColumnIndex("UF")).toString()));
					DADOS.addContent(ENDERECO.setText(csr.getString(csr.getColumnIndex("ENDERECO")).toString()));
					DADOS.addContent(NUMERO.setText(csr.getString(csr.getColumnIndex("NUMERO")).toString()));
					DADOS.addContent(BAIRRO.setText(csr.getString(csr.getColumnIndex("BAIRRO")).toString()));
					DADOS.addContent(CEP.setText(csr.getString(csr.getColumnIndex("CEP")).toString()));
					DADOS.addContent(MUNICIPIO.setText(csr.getString(csr.getColumnIndex("MUNICIPIO")).toString()));
					DADOS.addContent(SEG_TERRIT.setText(csr.getString(csr.getColumnIndex("SEG_TERRIT")).toString()));
					DADOS.addContent(AREA.setText(csr.getString(csr.getColumnIndex("AREA")).toString()));
					DADOS.addContent(MICROAREA.setText(csr.getString(csr.getColumnIndex("MICROAREA")).toString()));
					DADOS.addContent(COD_FAMILIA.setText(csr.getString(csr.getColumnIndex("COD_FAMILIA")).toString()));
					DADOS.addContent(DATA_CADASTRO.setText(csr.getString(csr.getColumnIndex("DATA_CADASTRO")).toString()));
					DADOS.addContent(TIPO_CASA.setText(csr.getString(csr.getColumnIndex("TIPO_CASA")).toString()));
					DADOS.addContent(DEST_LIXO.setText(csr.getString(csr.getColumnIndex("DEST_LIXO")).toString()));
					DADOS.addContent(TRAT_AGUA.setText(csr.getString(csr.getColumnIndex("TRAT_AGUA")).toString()));
					DADOS.addContent(ABAST_AGUA.setText(csr.getString(csr.getColumnIndex("ABAST_AGUA")).toString()));
					DADOS.addContent(DEST_FEZES.setText(csr.getString(csr.getColumnIndex("DEST_FEZES")).toString()));
					DADOS.addContent(CASO_DOENCA.setText(csr.getString(csr.getColumnIndex("CASO_DOENCA")).toString()));
					DADOS.addContent(MEIO_COMUNICACAO.setText(csr.getString(csr.getColumnIndex("MEIO_COMUNICACAO")).toString()));
					DADOS.addContent(PART_GRUPOS.setText(csr.getString(csr.getColumnIndex("PART_GRUPOS")).toString()));
					DADOS.addContent(MEIO_TRANSPORTE.setText(csr.getString(csr.getColumnIndex("MEIO_TRANSPORTE")).toString()));
					
					RESIDENCIA.addContent(DADOS);					
				}while (csr.moveToNext());	
				
				Document doc = new Document();
				doc.setRootElement(RESIDENCIA);	
				
				TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
				
				
				//numSerial = numSerial.trim();
				try {  
		            Writer out = new BufferedWriter(new OutputStreamWriter(  
		                new FileOutputStream("/sdcard/residencias_"+telephonyManager.getDeviceId()+".xml"), "UTF8"));  
		              
		            XMLOutputter xout = new XMLOutputter();  
		            xout.output(doc,out);  
		              
		            System.out.println("XML criado com sucesso!");  
		        } catch (UnsupportedEncodingException e) {  
		            e.printStackTrace();  
		        } catch (IOException e) {  
		            e.printStackTrace();  
		        }
				
			}//Fim if		
			csr.close();
			bd.fechaBanco();
			return true;
		}catch(Exception e){
			Log.i("Erro Exportando Residências:", e.getMessage());
			return false;
		}
	}
	

}
