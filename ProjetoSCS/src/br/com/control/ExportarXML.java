/************************************************/
/** Projeto SCS - Sistema de Controle de Sa�de **/
/************ Desenvolvido por: *****************/
/********** Lucas de Souza Sales ****************/
/******* Karlos Kelvin Alves dos Santos *********/
/************* Pedro Luis Saraiva ***************/
/************************************************/
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
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class ExportarXML extends Activity {
	
	public static boolean Residencias = false;
	
	private Banco bd;
	private ProgressDialog mprogressDialog;
	private Handler mhandler;
	
	private Element SCS;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.geraxml);	
		
		bd = new Banco(this);
		
		SCS = new Element("SCS");	
		
		mhandler = new Handler();
		mprogressDialog = new ProgressDialog(this);
        mprogressDialog.setCancelable(true);
        mprogressDialog.setMessage("Exportando Dados...");
 
        mprogressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			
        mprogressDialog.setProgress(0);
        mprogressDialog.setMax(13);
        
        mprogressDialog.show();
        
        
        new Thread() {
            public void run() {
                
                try{
                	
                	if (ExportarResidencias() == true){
                		mprogressDialog.incrementProgressBy(1);
                	}
                	if (ExportarFamiliares() == true){
                		mprogressDialog.incrementProgressBy(1);
                	}
                	if (ExportarVacinas() == true){
                		mprogressDialog.incrementProgressBy(1);
                	}
                	if (ExportarAgendamentos() == true){
                		mprogressDialog.incrementProgressBy(1);
                	}
                	if (ExportarAcompanhamentoGestante() == true){
                		mprogressDialog.incrementProgressBy(1);
                	}
                	if (ExportarAcompanhamentoHanseniase() == true){
                		mprogressDialog.incrementProgressBy(1);
                	}
                	if (ExportarAcompanhamentoDiabete() == true){
                		mprogressDialog.incrementProgressBy(1);
                	}
                	if (ExportarAcompanhamentoHipertensao() == true){
                		mprogressDialog.incrementProgressBy(1);
                	}
                	if (ExportarAcompanhamentoTuberculose() == true){
                		mprogressDialog.incrementProgressBy(1);
                	}
                	if (ExportarAcompCriancas() == true){
                		mprogressDialog.incrementProgressBy(1);
                	}
                	if (ExportarVisitas() == true) {
                		mprogressDialog.incrementProgressBy(1);
                	}
                	if (ApagaAgendamentos() == true) {
                		mprogressDialog.incrementProgressBy(1);
                	}
                	if (ExportarAcompPadrao() == true) {
                		mprogressDialog.incrementProgressBy(1);
                	}
                	
                	
                	/****************** CRIANDO O XML ******************/
                	Document doc = new Document();
            		doc.setRootElement(SCS);	
            		
            		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            		
            		try {  
                        Writer out = new BufferedWriter(new OutputStreamWriter(  
                            new FileOutputStream("/sdcard/scs_exportacao_"+telephonyManager.getDeviceId()+".xml"), "UTF8"));  
                          
                        XMLOutputter xout = new XMLOutputter();  
                        xout.output(doc,out);  
                          
                        System.out.println("XML criado com sucesso!");  
                    } catch (UnsupportedEncodingException e) {  
                        e.printStackTrace();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }
            	
            		/****************** FIM DA CRIA��O DO XML ******************/
            		
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }
                 
                //Exibe mensagem apenas informando o fim da execu��o da thread
                mhandler.post(new Runnable() {
                    public void run() {
                    	Toast.makeText(getApplicationContext(), "Exporta��o Conclu�da!", Toast.LENGTH_SHORT).show();
                    	//Mensagem.exibeMessagem(getApplication(), "Importados:", msg);
                    }
                });                                       
                //encerra progress dialog
                mprogressDialog.dismiss();
                finish();
            }
        }.start();
        
	}    
	
	public boolean ApagaAgendamentos(){
		try{
			bd.open();
			bd.ExecutaSql("delete from agendamento where 1 = 1");
			bd.fechaBanco();
			return true;
		}catch(Exception e){
			Mensagem.exibeMessagem(this, "SCS - ERRO", "Erro no m�todo ApagaAgendamento:\n"+e.getMessage());
			return false;
		}
		
	}
	
	public boolean ExportarResidencias(){
		
		try{			
			Cursor csr = null;
			bd.open();
			csr = bd.consulta("residencia", new String[] {"*"}, null, null, null, null, "_ID", "");
			csr.moveToFirst();
			
			if (csr.getCount()>0){ 
				do{	      
					Element RESIDENCIA       = new Element("RESIDENCIAS");				
					Element ID           	 = new Element("ID");				
					Element ENDERECO     	 = new Element("ENDERECO");
					Element NUMERO       	 = new Element("NUMERO");
					Element CODBAIRRO      	 = new Element("CODBAIRRO");
					Element SEG_TERRIT   	 = new Element("SEGTERRITORIAL");
					Element AREA 		 	 = new Element("AREA");
					Element MICROAREA    	 = new Element("MICROAREA");
					Element DATA_CADASTRO	 = new Element("DATACADASTRO");
					Element TIPO_CASA    	 = new Element("TIPOCASA");
					Element DEST_LIXO     	 = new Element("DESTLIXO");
					Element TRAT_AGUA    	 = new Element("TRATAMENTOAGUA");
					Element ABAST_AGUA   	 = new Element("ABASTECIMENTOAGUA");
					Element DEST_FEZES   	 = new Element("DESTFEZES");
					Element CASO_DOENCA      = new Element("CASODOENCA");
					Element CASO_DOENCAOUTRO = new Element("CASODOENCAOUTRO");
					Element TIPO_CASA_OUTRO  = new Element("TIPOCASAOUTRO");
					Element MEIO_COMUNICACAO = new Element("MEIOCOMUNICACAO");
					Element PART_GRUPOS		 = new Element("PARTICIPAGRUPO");
					Element MEIO_TRANSPORTE  = new Element("MEIOTRANSPORTE");
					Element FL_PLANO_SAUDE   = new Element("FL_PLANO_SAUDE");
					Element PES_COBERTAS     = new Element("NUM_PESSOAS_COBERTAS");
					Element NOME_PLANO_SAUDE = new Element("NOME_PLANO_SAUDE");
					Element MCOMUNIC_OUTROS  = new Element("MEIOCOMUNICACAO_OUTROS");
					Element MTRANSP_OUTROS   = new Element("MEIOTRANSPORTE_OUTROS");
					Element PGRUPOS_OUTROS   = new Element("PARTICIPAGRUPO_OUTROS");
					Element POSSUI_ENERGIA   = new Element("POSSUI_ENERGIA_ELETRICA");
					Element NUM_COMODOS      = new Element("NUM_COMODOS");
					Element NUMERO_FAMILIA   = new Element("NUMERO_FAMILIA");
					Element COMPLEMENTO      = new Element("COMPLEMENTO");
					Element FL_APTO_BENEF    = new Element("FL_APTO_BENEFICIO");
					Element NOME_BENEFICIO   = new Element("NOME_BENEFICIO");
					
					RESIDENCIA.addContent(ID.setText(csr.getString(csr.getColumnIndex("_ID")).toString()));		
					RESIDENCIA.addContent(ENDERECO.setText(csr.getString(csr.getColumnIndex("COD_ENDERECO")).toString()));
					RESIDENCIA.addContent(NUMERO.setText(csr.getString(csr.getColumnIndex("NUMERO")).toString()));
					RESIDENCIA.addContent(CODBAIRRO.setText(csr.getString(csr.getColumnIndex("COD_BAIRRO")).toString()));
					RESIDENCIA.addContent(SEG_TERRIT.setText(csr.getString(csr.getColumnIndex("SEG_TERRIT")).toString()));
					RESIDENCIA.addContent(AREA.setText(csr.getString(csr.getColumnIndex("AREA")).toString()));
					RESIDENCIA.addContent(MICROAREA.setText(csr.getString(csr.getColumnIndex("MICROAREA")).toString()));
					RESIDENCIA.addContent(DATA_CADASTRO.setText(csr.getString(csr.getColumnIndex("DATA_CADASTRO")).toString()));
					RESIDENCIA.addContent(TIPO_CASA.setText(csr.getString(csr.getColumnIndex("TIPO_CASA")).toString()));
					RESIDENCIA.addContent(TIPO_CASA_OUTRO.setText(csr.getString(csr.getColumnIndex("TIPO_CASA_OUTROS")).toString()));
					RESIDENCIA.addContent(DEST_LIXO.setText(csr.getString(csr.getColumnIndex("DEST_LIXO")).toString()));
					RESIDENCIA.addContent(TRAT_AGUA.setText(csr.getString(csr.getColumnIndex("TRAT_AGUA")).toString()));
					RESIDENCIA.addContent(ABAST_AGUA.setText(csr.getString(csr.getColumnIndex("ABAST_AGUA")).toString()));
					RESIDENCIA.addContent(DEST_FEZES.setText(csr.getString(csr.getColumnIndex("DEST_FEZES")).toString()));
					RESIDENCIA.addContent(CASO_DOENCA.setText(csr.getString(csr.getColumnIndex("CASO_DOENCA")).toString()));
					RESIDENCIA.addContent(CASO_DOENCAOUTRO.setText(csr.getString(csr.getColumnIndex("CASO_DOENCA_OUTROS")).toString()));
					RESIDENCIA.addContent(MEIO_COMUNICACAO.setText(csr.getString(csr.getColumnIndex("MEIO_COMUNICACAO")).toString()));
					RESIDENCIA.addContent(PART_GRUPOS.setText(csr.getString(csr.getColumnIndex("PART_GRUPOS")).toString())); 
					RESIDENCIA.addContent(MEIO_TRANSPORTE.setText(csr.getString(csr.getColumnIndex("MEIO_TRANSPORTE")).toString()));
					RESIDENCIA.addContent(FL_PLANO_SAUDE.setText(csr.getString(csr.getColumnIndex("POSSUI_PLANO")).toString()));
					RESIDENCIA.addContent(POSSUI_ENERGIA.setText(csr.getString(csr.getColumnIndex("FL_ENERGIA")).toString().trim()));
					RESIDENCIA.addContent(NUM_COMODOS.setText(csr.getString(csr.getColumnIndex("NUM_COMODOS")).toString().trim()));					
					RESIDENCIA.addContent(NUMERO_FAMILIA.setText(csr.getString(csr.getColumnIndex("COD_FAMILIA")).toString().trim()));
					RESIDENCIA.addContent(COMPLEMENTO.setText(csr.getString(csr.getColumnIndex("COMPLEMENTO")).toString().trim()));
					RESIDENCIA.addContent(FL_APTO_BENEF.setText(csr.getString(csr.getColumnIndex("FL_APTO_BENEFICIO")).toString().trim()));
					RESIDENCIA.addContent(NOME_BENEFICIO.setText(csr.getString(csr.getColumnIndex("NOME_BENEFICIO")).toString().trim()));
					RESIDENCIA.addContent(PES_COBERTAS.setText((csr.getString(csr.getColumnIndex("POSSUI_PLANO")).toString().trim().equals("N") || 
																csr.getString(csr.getColumnIndex("NUM_PESSOAS_COM_PLANO")).toString().trim().equals("null") ||
																csr.getString(csr.getColumnIndex("NUM_PESSOAS_COM_PLANO")).toString().trim().equals("") ? "" : csr.getString(csr.getColumnIndex("NUM_PESSOAS_COM_PLANO")).toString())));
					RESIDENCIA.addContent(NOME_PLANO_SAUDE.setText((csr.getString(csr.getColumnIndex("POSSUI_PLANO")).toString().trim().equals("N") || 
																	csr.getString(csr.getColumnIndex("NOME_PLANO_SAUDE")).toString().trim().equals("null") ||
																	csr.getString(csr.getColumnIndex("NOME_PLANO_SAUDE")).toString().trim().equals("") ? "" : csr.getString(csr.getColumnIndex("NOME_PLANO_SAUDE")).toString()))); 
					RESIDENCIA.addContent(MCOMUNIC_OUTROS.setText((csr.getString(csr.getColumnIndex("MEIO_COMUNICACAO")).toString().trim().equals("Outro") ? csr.getString(csr.getColumnIndex("MEIO_COMUNICACAO_OUTRO")).toString() : "" )));
					RESIDENCIA.addContent(MTRANSP_OUTROS.setText((csr.getString(csr.getColumnIndex("MEIO_TRANSPORTE")).toString().trim().equals("Outro") ? csr.getString(csr.getColumnIndex("MEIO_TRANSPORTE_OUTRO")).toString() : "" )));
					RESIDENCIA.addContent(PGRUPOS_OUTROS.setText((csr.getString(csr.getColumnIndex("PART_GRUPOS")).toString().trim().equals("Outro") ? csr.getString(csr.getColumnIndex("PART_GRUPOS_OUTRO")).toString() : "" )));
					
					SCS.addContent(RESIDENCIA);
					
				}while (csr.moveToNext());
			}//Fim if		
			csr.close();
			bd.fechaBanco();
			return true;
		}catch(Exception e){
			Log.i("Erro Exportando Resid�ncias:", e.getMessage());
			return false;
		}
	}
	
	public boolean ExportarVisitas() {
		
		try{			
			Cursor csr = null;
			bd.open();
			csr = bd.consulta("visita", new String[] {"*"}, null, null, null, null, "_ID", "");
			csr.moveToFirst();
			
			if (csr.getCount()>0){
				do{	      
					Element VISITA           = new Element("VISITA");				
					Element ID           	 = new Element("ID");				
					Element DATA     	 	 = new Element("DATA");
					Element HORA             = new Element("HORA");
					Element AGENTE      	 = new Element("COD_AGENTE");
					Element ENDERECO   	     = new Element("ENDERECO");
					Element NUMERO 		 	 = new Element("NUMERO");
					Element COMPLEMENTO    	 = new Element("COMPLEMENTO");
					Element FL_CASA_FECHADA  = new Element("FL_CASA_FECHADA");
					Element FL_VISITA_CONF	 = new Element("FL_VISITA_CONFIRMADA");
					
					
					VISITA.addContent(ID.setText(csr.getString(csr.getColumnIndex("_ID")).toString()));		
					VISITA.addContent(DATA.setText(csr.getString(csr.getColumnIndex("DATA")).toString()));
					VISITA.addContent(HORA.setText(csr.getString(csr.getColumnIndex("HORA")).toString()));
					VISITA.addContent(AGENTE.setText(csr.getString(csr.getColumnIndex("AGENTE")).toString()));
					VISITA.addContent(ENDERECO.setText(csr.getString(csr.getColumnIndex("ENDERECO")).toString()));
					VISITA.addContent(NUMERO.setText(csr.getString(csr.getColumnIndex("NUMERO")).toString()));
					VISITA.addContent(COMPLEMENTO.setText(csr.getString(csr.getColumnIndex("COMPLEMENTO")).toString()));
					VISITA.addContent(FL_CASA_FECHADA.setText(csr.getString(csr.getColumnIndex("FL_CASA_FECHADA")).toString()));
					VISITA.addContent(FL_VISITA_CONF.setText(csr.getString(csr.getColumnIndex("VISITA_CONFIRMADA")).toString()));
					
					
					SCS.addContent(VISITA);
					
				}while (csr.moveToNext());
			}//Fim if		
			csr.close();
			bd.fechaBanco();
			return true;
		}catch(Exception e){
			Log.i("Erro Exportando Visitas:", e.getMessage());
			return false;
		}
	}//Fim do M�todo ExportarFamiliar
	
	public boolean ExportarFamiliares(){
		
		try{			
			Cursor csr = null;
			bd.open();
			csr = bd.consulta("residente", new String[] {"*"}, null, null, null, null, "_ID", "");
			csr.moveToFirst();
			
			if (csr.getCount()>0){
				do{	      
					Element FAMILIAR         = new Element("FAMILIAR");				
					Element ID           	 = new Element("ID");				
					Element NOME     	 	 = new Element("NOME");
					Element COD_ENDERECO     = new Element("COD_ENDERECO");
					Element NUMERO      	 = new Element("NUMERO");
					Element DTNASCIMENTO   	 = new Element("DTNASCIMENTO");
					Element SEXO 		 	 = new Element("SEXO");
					Element FREQ_ESCOLA    	 = new Element("FREQ_ESCOLA");
					Element ALFABETIZADO  	 = new Element("ALFABETIZADO");
					Element OCUPACAO	 	 = new Element("OCUPACAO");
					Element FL_HANSENIASE    = new Element("HANSENIASE");
					Element FL_HIPERTENSAO   = new Element("HIPERTENSAO");
					Element FL_GESTANTE    	 = new Element("GESTANTE");
					Element FL_TUBERCULOSE   = new Element("TUBERCULOSE");
					Element FL_ALCOLISMO   	 = new Element("ALCOOLISMO");
					Element FL_CHAGAS        = new Element("CHAGAS");
					Element FL_DEFICIENTE	 = new Element("DEFICIENTE");
					Element FL_MALARIA 		 = new Element("MALARIA");
					Element FL_DIABETE 		 = new Element("DIABETE");
					Element FL_EPILETICO	 = new Element("EPILETICO");
					Element HASH  			 = new Element("HASH");
					Element DATA_ATUALIZACAO = new Element("DTATUALIZACAO");
					Element NOME_PAI         = new Element("NOME_PAI");
					Element NOME_MAE         = new Element("NOME_MAE");
					Element COMPLEMENTO      = new Element("COMPLEMENTO");
					Element FL_OBITO         = new Element("FL_OBITO");
					Element FL_MUDOU_SE      = new Element("FL_MUDOU_SE");
					Element MOTIVO_OBITO     = new Element("MOTIVO_OBITO");
					Element INFO_OBITO       = new Element("INFO_OBITO");
					
					FAMILIAR.addContent(ID.setText(csr.getString(csr.getColumnIndex("_ID")).toString()));		
					FAMILIAR.addContent(NOME.setText(csr.getString(csr.getColumnIndex("NOME")).toString()));
					FAMILIAR.addContent(COD_ENDERECO.setText(csr.getString(csr.getColumnIndex("COD_ENDERECO")).toString()));
					FAMILIAR.addContent(NUMERO.setText(csr.getString(csr.getColumnIndex("NUMERO")).toString()));
					FAMILIAR.addContent(DTNASCIMENTO.setText(csr.getString(csr.getColumnIndex("DTNASCIMENTO")).toString()));
					FAMILIAR.addContent(SEXO.setText(csr.getString(csr.getColumnIndex("SEXO")).toString()));
					FAMILIAR.addContent(FREQ_ESCOLA.setText(csr.getString(csr.getColumnIndex("FREQ_ESCOLA")).toString()));
					FAMILIAR.addContent(ALFABETIZADO.setText(csr.getString(csr.getColumnIndex("ALFABETIZADO")).toString()));
					FAMILIAR.addContent(OCUPACAO.setText(csr.getString(csr.getColumnIndex("OCUPACAO")).toString()));
					FAMILIAR.addContent(FL_HANSENIASE.setText(csr.getString(csr.getColumnIndex("FL_HANSENIASE")).toString()));
					FAMILIAR.addContent(FL_HIPERTENSAO.setText(csr.getString(csr.getColumnIndex("FL_HIPERTENSAO")).toString()));
					FAMILIAR.addContent(FL_GESTANTE.setText(csr.getString(csr.getColumnIndex("FL_GESTANTE")).toString()));
					FAMILIAR.addContent(FL_TUBERCULOSE.setText(csr.getString(csr.getColumnIndex("FL_TUBERCULOSE")).toString()));
					FAMILIAR.addContent(FL_ALCOLISMO.setText(csr.getString(csr.getColumnIndex("FL_ALCOLISMO")).toString()));
					FAMILIAR.addContent(FL_CHAGAS.setText(csr.getString(csr.getColumnIndex("FL_CHAGAS")).toString()));
					FAMILIAR.addContent(FL_DEFICIENTE.setText(csr.getString(csr.getColumnIndex("FL_DEFICIENTE")).toString()));
					FAMILIAR.addContent(FL_MALARIA.setText(csr.getString(csr.getColumnIndex("FL_MALARIA")).toString()));
					FAMILIAR.addContent(FL_DIABETE.setText(csr.getString(csr.getColumnIndex("FL_DIABETE")).toString()));
					FAMILIAR.addContent(FL_EPILETICO.setText(csr.getString(csr.getColumnIndex("FL_EPILETICO")).toString())); 
					FAMILIAR.addContent(HASH.setText(csr.getString(csr.getColumnIndex("HASH")).toString()));
					FAMILIAR.addContent(NOME_PAI.setText(csr.getString(csr.getColumnIndex("NOME_PAI")).toString()));
					FAMILIAR.addContent(NOME_MAE.setText(csr.getString(csr.getColumnIndex("NOME_MAE")).toString()));
					FAMILIAR.addContent(DATA_ATUALIZACAO.setText(csr.getString(csr.getColumnIndex("DATA_ATUALIZACAO")).toString()));
					FAMILIAR.addContent(COMPLEMENTO.setText(csr.getString(csr.getColumnIndex("COMPLEMENTO")).toString()));
					FAMILIAR.addContent(FL_OBITO.setText(csr.getString(csr.getColumnIndex("FL_FALECEU")).toString()));
					FAMILIAR.addContent(FL_MUDOU_SE.setText(csr.getString(csr.getColumnIndex("FL_MUDOU_SE")).toString()));
					FAMILIAR.addContent(INFO_OBITO.setText(csr.getString(csr.getColumnIndex("INF_OBITO")).toString()));
					FAMILIAR.addContent(MOTIVO_OBITO.setText(csr.getString(csr.getColumnIndex("MOTIVO_OBITO")).toString()));
					
					SCS.addContent(FAMILIAR);
					
				}while (csr.moveToNext());
			}//Fim if		
			csr.close();
			bd.fechaBanco();
			return true;
		}catch(Exception e){
			Log.i("Erro Exportando Familiares:", e.getMessage());
			return false;
		}
	}//Fim do M�todo ExportarFamiliar
	
	public boolean ExportarAcompCriancas(){
		
		try{			
			Cursor csr = null;
			bd.open();
			csr = bd.consulta("crianca", new String[] {"*"}, null, null, null, null, "_ID", "");
			csr.moveToFirst();
			
			if (csr.getCount()>0){
				do{	      
					Element CRIANCA       = new Element("CRIANCA");				
					Element HASH          = new Element("HASH");				
					Element DT_VISITA     = new Element("DT_VISITA");
					Element ALTURA 		  = new Element("ALTURA");
					Element PESO  		  = new Element("PESO");
					Element PER_CEFALICO  = new Element("PER_CEFALICO");
					Element APGAR5 		  = new Element("APGAR5");
					Element TP_PARTO      = new Element("TP_PARTO");
					Element SITUACAO  	  = new Element("SITUACAO");
					Element OBSERVACAO    = new Element("OBSERVACAO");
					
					CRIANCA.addContent(HASH.setText(csr.getString(csr.getColumnIndex("HASH")).toString()));		
					CRIANCA.addContent(DT_VISITA.setText(csr.getString(csr.getColumnIndex("DT_VISITA")).toString()));
					CRIANCA.addContent(ALTURA.setText(csr.getString(csr.getColumnIndex("ALTURA")).toString()));
					CRIANCA.addContent(PESO.setText(csr.getString(csr.getColumnIndex("PESO")).toString()));
					CRIANCA.addContent(PER_CEFALICO.setText(csr.getString(csr.getColumnIndex("PER_CEFALICO")).toString()));
					CRIANCA.addContent(APGAR5.setText(csr.getString(csr.getColumnIndex("APGAR5")).toString()));
					CRIANCA.addContent(TP_PARTO.setText(csr.getString(csr.getColumnIndex("TP_PARTO")).toString()));
					CRIANCA.addContent(SITUACAO.setText(csr.getString(csr.getColumnIndex("SITUACAO")).toString()));
					CRIANCA.addContent(OBSERVACAO.setText(csr.getString(csr.getColumnIndex("OBSERVACAO")).toString()));
					
					SCS.addContent(CRIANCA);
					
				}while (csr.moveToNext());
			}//Fim if		
			csr.close();
			bd.fechaBanco();
			return true;
		}catch(Exception e){
			Log.i("Erro Exportando Crian�as:", e.getMessage());
			return false;
		}
	}//Fim do Método ExportarVacinas
	
	public boolean ExportarVacinas(){
		
		try{			
			Cursor csr = null;
			bd.open();
			csr = bd.consulta("vacinas", new String[] {"*"}, null, null, null, null, "_ID", "");
			csr.moveToFirst();
			
			if (csr.getCount()>0){
				do{	      
					Element VACINA        = new Element("VACINA");				
					Element HASH          = new Element("HASH");				
					Element TIPO_VACINA   = new Element("TIPO_VACINA");
					Element DOSE_APLICADA = new Element("DOSE_APLICADA");
					Element DT_APLICACAO  = new Element("DT_APLICACAO");
					Element DT_CADASTRO   = new Element("DT_CADASTRO");
					Element LOTE 		  = new Element("LOTE");
					Element FL_APLICADA   = new Element("FL_APLICADA");
					Element TIPO  	      = new Element("TIPO");
					
					VACINA.addContent(HASH.setText(csr.getString(csr.getColumnIndex("HASH")).toString()));		
					VACINA.addContent(TIPO_VACINA.setText(csr.getString(csr.getColumnIndex("TIPO_VACINA")).toString()));
					VACINA.addContent(DOSE_APLICADA.setText(csr.getString(csr.getColumnIndex("DOSE_APLICADA")).toString()));
					VACINA.addContent(DT_APLICACAO.setText(csr.getString(csr.getColumnIndex("DT_APLICACAO")).toString()));
					VACINA.addContent(DT_CADASTRO.setText(csr.getString(csr.getColumnIndex("DT_CADASTRO")).toString()));
					VACINA.addContent(LOTE.setText(csr.getString(csr.getColumnIndex("LOTE")).toString()));
					VACINA.addContent(FL_APLICADA.setText(csr.getString(csr.getColumnIndex("FL_APLICADA")).toString()));
					VACINA.addContent(TIPO.setText(csr.getString(csr.getColumnIndex("TIPO")).toString()));
					
					SCS.addContent(VACINA);
					
				}while (csr.moveToNext());
			}//Fim if		
			csr.close();
			bd.fechaBanco();
			return true;
		}catch(Exception e){
			Log.i("Erro Exportando Vacinas:", e.getMessage());
			return false;
		}
	}//Fim do Método ExportarVacinas
	
	public boolean ExportarAcompanhamentoGestante(){
		
		try{			
			Cursor csr = null;
			bd.open();
			csr = bd.consulta("gestacao", new String[] {"*"}, null, null, null, null, "_ID", "");
			csr.moveToFirst();
			
			if (csr.getCount()>0){
				do{	      
					Element GESTANTE           = new Element("GESTANTE");				
					Element HASH               = new Element("HASH");				
					Element DT_VISITA   	   = new Element("DT_VISITA");
					Element DT_ATUALIZACAO     = new Element("DT_ATUALIZACAO");
					Element DT_ULTIMA_REGRA    = new Element("DT_ULTIMA_REGRA");
					Element DT_PROVAVEL_PARTO  = new Element("DT_PROVAVEL_PARTO");
					Element EST_NUTRICIONAL    = new Element("EST_NUTRICIONAL");
					Element MES_GESTACAO   	   = new Element("MES_GESTACAO");
					Element DT_PRE_NATAL  	   = new Element("DT_PRE_NATAL");
					Element DT_ULTIMA_CONSULTA = new Element("DT_ULTIMA_CONSULTA");
					Element RESULTADO_GESTACAO = new Element("RESULTADO_GESTACAO");
					Element OBSERVACAO  	   = new Element("OBSERVACAO");
					Element MAIS6GES    	   = new Element("MAIS6GESTACOES");
					Element MAIS36ANOS         = new Element("MAIS36ANOS");
					Element SAGRAMENTO         = new Element("SANGRAMENTO");
					Element DIABETES           = new Element("DIABETES");
					Element NATIMORTO          = new Element("NATIMORTO");
					Element MENOS20ANOS        = new Element("MENOS20ANOS");
					Element EDEMA              = new Element("EDEMA");
					Element PRESSAOALTA        = new Element("PRESSAOALTA");
					Element FL_ACOMP_FINAL     = new Element("FL_ACOMP_FINAL");
					Element DT_CONS_PUERBIO    = new Element("DT_CONSULTA_PUERBIO");
					
					
					GESTANTE.addContent(HASH.setText(csr.getString(csr.getColumnIndex("HASH")).toString()));		
					GESTANTE.addContent(DT_VISITA.setText(csr.getString(csr.getColumnIndex("DT_VISITA")).toString()));
					GESTANTE.addContent(DT_ATUALIZACAO.setText(csr.getString(csr.getColumnIndex("DT_ATUALIZACAO")).toString()));
					GESTANTE.addContent(DT_ULTIMA_REGRA.setText(csr.getString(csr.getColumnIndex("DT_ULTIMA_REGRA")).toString()));
					GESTANTE.addContent(DT_PROVAVEL_PARTO.setText(csr.getString(csr.getColumnIndex("DT_PROVAVEL_PARTO")).toString()));
					GESTANTE.addContent(EST_NUTRICIONAL.setText(csr.getString(csr.getColumnIndex("EST_NUTRICIONAL")).toString()));
					GESTANTE.addContent(MES_GESTACAO.setText(csr.getString(csr.getColumnIndex("MES_GESTACAO")).toString()));
					GESTANTE.addContent(DT_PRE_NATAL.setText(csr.getString(csr.getColumnIndex("DT_PRE_NATAL")).toString()));
					GESTANTE.addContent(DT_ULTIMA_CONSULTA.setText(csr.getString(csr.getColumnIndex("DT_ULTIMA_CONSULTA")).toString()));
					GESTANTE.addContent(MAIS6GES.setText(csr.getString(csr.getColumnIndex("FATORES_RISCO")).toString().substring(0, 1)));
					GESTANTE.addContent(MAIS36ANOS.setText(csr.getString(csr.getColumnIndex("FATORES_RISCO")).toString().substring(1, 2)));
					GESTANTE.addContent(SAGRAMENTO.setText(csr.getString(csr.getColumnIndex("FATORES_RISCO")).toString().substring(2, 3)));
					GESTANTE.addContent(DIABETES.setText(csr.getString(csr.getColumnIndex("FATORES_RISCO")).toString().substring(3, 4)));
					GESTANTE.addContent(NATIMORTO.setText(csr.getString(csr.getColumnIndex("FATORES_RISCO")).toString().substring(4, 5)));
					GESTANTE.addContent(MENOS20ANOS.setText(csr.getString(csr.getColumnIndex("FATORES_RISCO")).toString().substring(5, 6)));
					GESTANTE.addContent(EDEMA.setText(csr.getString(csr.getColumnIndex("FATORES_RISCO")).toString().substring(6, 7)));
					GESTANTE.addContent(PRESSAOALTA.setText(csr.getString(csr.getColumnIndex("FATORES_RISCO")).toString().substring(7, 8)));
					GESTANTE.addContent(RESULTADO_GESTACAO.setText(csr.getString(csr.getColumnIndex("RESULTADO_GESTACAO")).toString()));
					GESTANTE.addContent(OBSERVACAO.setText(csr.getString(csr.getColumnIndex("OBSERVACAO")).toString()));
					GESTANTE.addContent(FL_ACOMP_FINAL.setText(csr.getString(csr.getColumnIndex("FL_ACOMP_FINAL")).toString()));
					GESTANTE.addContent(DT_CONS_PUERBIO.setText(csr.getString(csr.getColumnIndex("DT_CONSULTA_PUERBIO")).toString()));
					
					SCS.addContent(GESTANTE);
					
				}while (csr.moveToNext());
			}//Fim if		
			csr.close();
			bd.fechaBanco();
			return true;
		}catch(Exception e){
			Log.i("Erro Exportando Acompanhamento de Gestante:", e.getMessage());
			return false;
		}
	}//Fim do Método ExportarAcompanhamentoGestante
	
	public boolean ExportarAcompanhamentoHanseniase(){
		
		try{			
			Cursor csr = null;
			bd.open();
			csr = bd.consulta("hanseniase", new String[] {"*"}, null, null, null, null, "_ID", "");
			csr.moveToFirst();
			
			if (csr.getCount()>0){
				do{	      
					Element HANSENIASE         		= new Element("HANSENIASE");				
					Element HASH               		= new Element("HASH");				
					Element DT_VISITA   	   		= new Element("DT_VISITA");
					Element DT_ATUALIZACAO          = new Element("DT_ATUALIZACAO");
					Element DT_ULTIMA_CONSULTA      = new Element("DT_ULTIMA_CONSULTA");
					Element DT_ULTIMA_DOSE          = new Element("DT_ULTIMA_DOSE");
					Element TOMA_MEDICACAO          = new Element("TOMA_MEDICACAO");
					Element AUTO_CUIDADOS   	    = new Element("AUTO_CUIDADOS");
					Element COMUNICANTES_EXAMINADOS = new Element("COMUNICANTES_EXAMINADOS");					
					Element COMUNICANTES_BCG        = new Element("COMUNICANTES_BCG");
					Element OBSERVACAO              = new Element("OBSERVACAO");
					
					HANSENIASE.addContent(HASH.setText(csr.getString(csr.getColumnIndex("HASH")).toString()));		
					HANSENIASE.addContent(DT_VISITA.setText(csr.getString(csr.getColumnIndex("DT_VISITA")).toString()));
					HANSENIASE.addContent(DT_ATUALIZACAO.setText(csr.getString(csr.getColumnIndex("DT_ATUALIZACAO")).toString()));
					HANSENIASE.addContent(DT_ULTIMA_CONSULTA.setText(csr.getString(csr.getColumnIndex("DT_ULTIMA_CONSULTA")).toString()));
					HANSENIASE.addContent(DT_ULTIMA_DOSE.setText(csr.getString(csr.getColumnIndex("DT_ULTIMA_DOSE")).toString()));
					HANSENIASE.addContent(TOMA_MEDICACAO.setText(csr.getString(csr.getColumnIndex("TOMA_MEDICACAO")).toString()));
					HANSENIASE.addContent(AUTO_CUIDADOS.setText(csr.getString(csr.getColumnIndex("AUTO_CUIDADOS")).toString()));
					HANSENIASE.addContent(COMUNICANTES_EXAMINADOS.setText(csr.getString(csr.getColumnIndex("COMUNICANTES_EXAMINADOS")).toString()));
					HANSENIASE.addContent(COMUNICANTES_BCG.setText(csr.getString(csr.getColumnIndex("COMUNICANTES_BCG")).toString()));										
					HANSENIASE.addContent(OBSERVACAO.setText(csr.getString(csr.getColumnIndex("OBSERVACAO")).toString()));
					
					SCS.addContent(HANSENIASE);
					
				}while (csr.moveToNext());
			}//Fim if		
			csr.close();
			bd.fechaBanco();
			return true;
		}catch(Exception e){
			Log.i("Erro Exportando Acompanhamento de Hanseniase:", e.getMessage());
			return false;
		}
	}//Fim do M�todo ExportarAcompanhamentoHanseniase
	 
	public boolean ExportarAcompanhamentoDiabete(){
		
		try{			
			Cursor csr = null;
			bd.open();
			csr = bd.consulta("diabete", new String[] {"*"}, null, null, null, null, "_ID", "");
			csr.moveToFirst();
			
			if (csr.getCount()>0){
				do{	      
					Element DIABETE         	   = new Element("DIABETE");				
					Element HASH               	   = new Element("HASH");				
					Element DT_VISITA   	   	   = new Element("DT_VISITA");
					Element DT_ATUALIZACAO         = new Element("DT_ATUALIZACAO");
					Element FL_FAZ_DIETA      	   = new Element("FAZ_DIETA");
					Element FL_FAZ_EXCERCICIOS     = new Element("FAZ_EXCERCICIOS");
					Element FL_USA_INSULINA        = new Element("USA_INSULINA");
					Element FL_USA_HIPOGLICEMIANTE = new Element("USA_HIPOGLICEMIANTE");
					Element DT_ULTIMA_VISITA       = new Element("DT_ULTIMA_VISITA");					
					Element OBSERVACAO             = new Element("OBSERVACAO");
					
					DIABETE.addContent(HASH.setText(csr.getString(csr.getColumnIndex("HASH")).toString()));		
					DIABETE.addContent(DT_VISITA.setText(csr.getString(csr.getColumnIndex("DT_VISITA")).toString()));
					DIABETE.addContent(DT_ATUALIZACAO.setText(csr.getString(csr.getColumnIndex("DT_ATUALIZACAO")).toString()));
					DIABETE.addContent(FL_FAZ_DIETA.setText(csr.getString(csr.getColumnIndex("FL_FAZ_DIETA")).toString().trim()));
					DIABETE.addContent(FL_FAZ_EXCERCICIOS.setText(csr.getString(csr.getColumnIndex("FL_FAZ_EXCERCICIOS")).toString().trim()));
					DIABETE.addContent(FL_USA_INSULINA.setText(csr.getString(csr.getColumnIndex("FL_USA_INSULINA")).toString().trim()));
					DIABETE.addContent(FL_USA_HIPOGLICEMIANTE.setText(csr.getString(csr.getColumnIndex("FL_USA_HIPOGLICEMIANTE")).toString().trim()));
					DIABETE.addContent(DT_ULTIMA_VISITA.setText(csr.getString(csr.getColumnIndex("DT_ULTIMA_VISITA")).toString()));
					DIABETE.addContent(OBSERVACAO.setText(csr.getString(csr.getColumnIndex("OBSERVACAO")).toString()));										
					
					SCS.addContent(DIABETE);
					
				}while (csr.moveToNext());
			}//Fim if		
			csr.close();
			bd.fechaBanco();
			return true;
		}catch(Exception e){
			Log.i("Erro Exportando Acompanhamento de Diabete:", e.getMessage());
			return false;
		}
	}//Fim do M�todo ExportarAcompanhamentoDiabete
	
	public boolean ExportarAcompanhamentoHipertensao(){
		
		try{			
			Cursor csr = null;
			bd.open();
			csr = bd.consulta("hipertensao", new String[] {"*"}, null, null, null, null, "_ID", "");
			csr.moveToFirst();
			
			if (csr.getCount()>0){
				do{	      
					Element HIPERTENSAO       = new Element("HIPERTENSAO");				
					Element HASH              = new Element("HASH");				
					Element DT_VISITA   	  = new Element("DT_VISITA");
					Element DT_ATUALIZACAO    = new Element("DT_ATUALIZACAO");
					Element FL_FAZ_DIETA      = new Element("FAZ_DIETA");
					Element FL_TOMA_MEDICACAO = new Element("TOMA_MEDICACAO");
					Element FL_FAZ_EXERCICIOS = new Element("FAZ_EXERCICIOS");
					Element FL_FUMANTE        = new Element("FUMANTE");
					Element PRESSAO_ARTERIAL  = new Element("PRESSAO_ARTERIAL");
					Element DT_ULTIMA_VISITA  = new Element("DT_ULTIMA_VISITA");					
					Element OBSERVACAO        = new Element("OBSERVACAO");
					
					HIPERTENSAO.addContent(HASH.setText(csr.getString(csr.getColumnIndex("HASH")).toString()));		
					HIPERTENSAO.addContent(DT_VISITA.setText(csr.getString(csr.getColumnIndex("DT_VISITA")).toString()));
					HIPERTENSAO.addContent(DT_ATUALIZACAO.setText(csr.getString(csr.getColumnIndex("DT_ATUALIZACAO")).toString()));
					HIPERTENSAO.addContent(FL_FAZ_DIETA.setText(csr.getString(csr.getColumnIndex("FL_FAZ_DIETA")).toString()));
					HIPERTENSAO.addContent(FL_TOMA_MEDICACAO.setText(csr.getString(csr.getColumnIndex("FL_TOMA_MEDICACAO")).toString()));
					HIPERTENSAO.addContent(FL_FAZ_EXERCICIOS.setText(csr.getString(csr.getColumnIndex("FL_FAZ_EXERCICIOS")).toString()));
					HIPERTENSAO.addContent(FL_FUMANTE.setText(csr.getString(csr.getColumnIndex("FL_FUMANTE")).toString()));
					HIPERTENSAO.addContent(PRESSAO_ARTERIAL.setText(csr.getString(csr.getColumnIndex("PRESSAO_ARTERIAL")).toString()));
					HIPERTENSAO.addContent(DT_ULTIMA_VISITA.setText(csr.getString(csr.getColumnIndex("DT_ULTIMA_VISITA")).toString()));
					HIPERTENSAO.addContent(OBSERVACAO.setText(csr.getString(csr.getColumnIndex("OBSERVACAO")).toString()));										
					
					SCS.addContent(HIPERTENSAO);
					
				}while (csr.moveToNext());
			}//Fim if		
			csr.close();
			bd.fechaBanco();
			return true;
		}catch(Exception e){
			Log.i("Erro Exportando Acompanhamento de Hanseniase:", e.getMessage());
			return false;
		}
	}//Fim do Método ExportarAcompanhamentoHipertensao
	
	public boolean ExportarAcompanhamentoTuberculose(){
		
		try{			
			Cursor csr = null;
			bd.open();
			csr = bd.consulta("tuberculose", new String[] {"*"}, null, null, null, null, "_ID", "");
			csr.moveToFirst();
			
			if (csr.getCount()>0){
				do{	      
					Element TUBERCULOSE        = new Element("TUBERCULOSE");				
					Element HASH               = new Element("HASH");				
					Element DT_VISITA   	   = new Element("DT_VISITA");
					Element DT_ATUALIZACAO     = new Element("DT_ATUALIZACAO");
					Element FL_MEDIC_DIARIA    = new Element("MEDIC_DIARIA");
					Element FL_REACOES_IND     = new Element("REACOES_INDESEJADAS");
					Element FL_EXAME_ESCARRO   = new Element("EXAME_ESCARRO");
					Element COMUNIC_EXAMINADOS = new Element("COMUNIC_EXAMINADOS");
					Element MENOR_BCG  		   = new Element("MENOR_BCG");					
					Element OBSERVACAO         = new Element("OBSERVACAO");
					Element DT_ULTIMA_VISITA   = new Element("DT_ULTIMA_VISITA");				
					
					TUBERCULOSE.addContent(HASH.setText(csr.getString(csr.getColumnIndex("HASH")).toString()));		
					TUBERCULOSE.addContent(DT_VISITA.setText(csr.getString(csr.getColumnIndex("DT_VISITA")).toString()));
					TUBERCULOSE.addContent(DT_ATUALIZACAO.setText(csr.getString(csr.getColumnIndex("DT_ATUALIZACAO")).toString()));
					TUBERCULOSE.addContent(FL_MEDIC_DIARIA.setText(csr.getString(csr.getColumnIndex("FL_MEDIC_DIARIA")).toString()));
					TUBERCULOSE.addContent(FL_REACOES_IND.setText(csr.getString(csr.getColumnIndex("FL_REACOES_IND")).toString()));
					TUBERCULOSE.addContent(FL_EXAME_ESCARRO.setText(csr.getString(csr.getColumnIndex("FL_EXAME_ESCARRO")).toString()));
					TUBERCULOSE.addContent(COMUNIC_EXAMINADOS.setText(csr.getString(csr.getColumnIndex("COMUNIC_EXAMINADOS")).toString()));
					TUBERCULOSE.addContent(MENOR_BCG.setText(csr.getString(csr.getColumnIndex("MENOR_BCG")).toString()));
					TUBERCULOSE.addContent(OBSERVACAO.setText(csr.getString(csr.getColumnIndex("OBSERVACAO")).toString()));										
					TUBERCULOSE.addContent(DT_ULTIMA_VISITA.setText(csr.getString(csr.getColumnIndex("DT_ULTIMA_CONSULTA")).toString()));
					
					SCS.addContent(TUBERCULOSE);
					
				}while (csr.moveToNext());
			}//Fim if		
			csr.close();
			bd.fechaBanco();
			return true;
		}catch(Exception e){
			Log.i("Erro Exportando Acompanhamento de Tuberculose:", e.getMessage());
			return false;
		}
	}//Fim do M�todo ExportarAcompanhamentoTuberculose
	
	public boolean ExportarAgendamentos(){
		
		try{			
			Cursor csr = null;
			bd.open();
			csr = bd.consulta("agendamento", new String[] {"*"}, null, null, null, null, "_ID", "");
			csr.moveToFirst();
			
			if (csr.getCount()>0){
				do{	      
					Element AGENDAMENTO      = new Element("AGENDAMENTO");				
					Element HASH             = new Element("HASH");				
					//Element DT_AGENDAMENTO   = new Element("DT_REGISTRO");
					Element FL_URGENTE       = new Element("URGENTE");
					Element TIPO_AGENDAMENTO = new Element("TIPO_AGENDAMENTO");
					Element DESCRICAO   	 = new Element("DESCRICAO");
					Element DATA             = new Element("DT_AGENDAMENTO"); 
					Element HORA             = new Element("HORA_AGENDAMENTO");
					Element PROFISSIONAL     = new Element("PROFISSIONAL");
					
					AGENDAMENTO.addContent(HASH.setText(csr.getString(csr.getColumnIndex("HASH")).toString()));		
					//AGENDAMENTO.addContent(DT_AGENDAMENTO.setText(csr.getString(csr.getColumnIndex("DT_REGISTRO")).toString()));
					AGENDAMENTO.addContent(FL_URGENTE.setText(csr.getString(csr.getColumnIndex("FL_URGENTE")).toString()));
					AGENDAMENTO.addContent(TIPO_AGENDAMENTO.setText(csr.getString(csr.getColumnIndex("TIPO_AGENDAMENTO")).toString()));
					AGENDAMENTO.addContent(DESCRICAO.setText(csr.getString(csr.getColumnIndex("DESCRICAO")).toString()));
					AGENDAMENTO.addContent(DATA.setText(csr.getString(csr.getColumnIndex("DT_AGENDAMENTO")).toString()));
					AGENDAMENTO.addContent(HORA.setText(csr.getString(csr.getColumnIndex("HR_AGENDAMENTO")).toString()));
					AGENDAMENTO.addContent(PROFISSIONAL.setText(csr.getString(csr.getColumnIndex("PROFISSIONAL")).toString()));
					
					SCS.addContent(AGENDAMENTO);
					
				}while (csr.moveToNext());
			}//Fim if		
			csr.close();
			bd.fechaBanco();
			return true;
		}catch(Exception e){
			Log.i("Erro Exportando Agendamentos:", e.getMessage());
			return false;
		}
	}//Fim do M�todo ExportarAgendamento
	
	public boolean ExportarAcompPadrao(){
		
		try{			
			Cursor csr = null;
			bd.open();
			csr = bd.consulta("acompanhamento", new String[] {"*"}, null, null, null, null, "_ID", "");
			csr.moveToFirst();
			
			if (csr.getCount()>0){
				do{	      
					Element ACOMPPADRAO           = new Element("ACOMPPADRAO");				
					Element HASH                  = new Element("HASH");				
					Element DT_VISITA             = new Element("DT_VISITA");
					Element FL_HOSPITALIZADA      = new Element("FL_HOSPITALIZADA");
					Element MOTIVO_HOSPITALIZACAO = new Element("MOTIVO_HOSPITALIZACAO");
					Element FL_DOENTE   	      = new Element("FL_DOENTE");
					Element DESC_DOENCA           = new Element("DESC_DOENCA"); 
					Element OBSERVACAO            = new Element("OBSERVACAO");					
					
					ACOMPPADRAO.addContent(HASH.setText(csr.getString(csr.getColumnIndex("HASH")).toString()));		
					ACOMPPADRAO.addContent(DT_VISITA.setText(csr.getString(csr.getColumnIndex("DT_VISITA")).toString()));
					ACOMPPADRAO.addContent(FL_HOSPITALIZADA.setText(csr.getString(csr.getColumnIndex("FL_HOSPITALIZADA")).toString()));
					ACOMPPADRAO.addContent(MOTIVO_HOSPITALIZACAO.setText(csr.getString(csr.getColumnIndex("MOTIVO_HOSPITALIZACAO")).toString()));
					ACOMPPADRAO.addContent(FL_DOENTE.setText(csr.getString(csr.getColumnIndex("FL_DOENTE")).toString()));
					ACOMPPADRAO.addContent(DESC_DOENCA.setText(csr.getString(csr.getColumnIndex("DESC_DOENCA")).toString()));
					ACOMPPADRAO.addContent(OBSERVACAO.setText(csr.getString(csr.getColumnIndex("OBSERVACAO")).toString()));					
					
					SCS.addContent(ACOMPPADRAO);
					
				}while (csr.moveToNext());
			}//Fim if		
			csr.close();
			bd.fechaBanco();
			return true;
		}catch(Exception e){
			Log.i("Erro Exportando Acompanhamentos:", e.getMessage());
			return false;
		}
	}//Fim do M�todo ExportarAgendamento
	

}
