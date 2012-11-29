package br.com.control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.JDOMException;

import br.com.scs.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class ImportarXML extends Activity {
	
	private CarregarXML xml;
	private Banco bd;
	private ProgressDialog mprogressDialog;
	private Handler mhandler;
	
	Cursor cUsuario,cBairro,cRuas,cResidencia,cFamiliar,
		   cHan,cHa,cDia,cTb,cGes,cVacina,cCri,cProf,cAcomp,cAgendamentos = null;

	
	String msg = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.geraxml);
				
		xml = new CarregarXML(); 
		bd = new Banco(this);	
		
		mhandler = new Handler();
		mprogressDialog = new ProgressDialog(this);
        mprogressDialog.setCancelable(true);
        mprogressDialog.setMessage("Importando Dados...");
 
        //define o estilo como horizontal que nesse caso signifca que terÃ¡ 
        //barra de progresso/contagem
        mprogressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			
        mprogressDialog.setProgress(0);
        mprogressDialog.setMax(14);
        
        mprogressDialog.show();
        
        new Thread() {
            public void run() {
                
                try{
                	ImportaRuas();
                	mprogressDialog.incrementProgressBy(1);               
        			ImportaBairros();
        			mprogressDialog.incrementProgressBy(1);        			
        			ImportaUsuarios();
        			mprogressDialog.incrementProgressBy(1);
        			ImportaResidencias(); 
        			mprogressDialog.incrementProgressBy(1);
        			ImportaFamiliar();
        			mprogressDialog.incrementProgressBy(1);
        			ImportaHan();
        			mprogressDialog.incrementProgressBy(1);
        			ImportaHa();
        			mprogressDialog.incrementProgressBy(1);
        			ImportaDia();
        			mprogressDialog.incrementProgressBy(1);
        			ImportaTb();
        			mprogressDialog.incrementProgressBy(1);
        			ImportaGes(); 
                    mprogressDialog.incrementProgressBy(1);
                    ImportaVacinas();
                    mprogressDialog.incrementProgressBy(1);
                    ImportaCriancas();
                    mprogressDialog.incrementProgressBy(1);
                    ImportaProfissionais();
                    mprogressDialog.incrementProgressBy(1);
                    ImportaAcompPadrao();
                    mprogressDialog.incrementProgressBy(1);
                    ImportaAgendamento();
                    mprogressDialog.incrementProgressBy(1);
                    
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }
                 
                //Exibe mensagem apenas informando o fim da execução da thread
                mhandler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }
                });            
                 
                //encerra progress dialog
                mprogressDialog.dismiss();  
                finish();
            }
        }.start();
		
	}//Fim do OnCreate	
	
	public void ImportaProfissionais() {
		
		try {			
			if (!(xml.carregar("scs.xml","profissional") == null)){		
				//I M P O R T A Ç Ã O   D E   U S U Á R I O S
				@SuppressWarnings("rawtypes")
				List<Element> profissionais = xml.carregar("scs.xml","profissional");
				bd = bd.open();				
				ContentValues c = new ContentValues();	 
				
				for(Element profissional : profissionais){					
					c.clear();					
					c.put("NOME", profissional.getChildText("nome"));
					c.put("TIPO", profissional.getChildText("tipo"));
					c.put("ESPECIALIDADE", profissional.getChildText("especialidade"));
					c.put("COD_RET", profissional.getChildText("id"));
									
					cProf = bd.consulta("profissional", new String[] { "_ID" }, "COD_RET = "+profissional.getChildText("id"), null, null, null, null, null);
					cProf.moveToFirst();						
					if (cProf.getCount() > 0){
						bd.atualizarDadosTabela("profissional",Integer.valueOf(cProf.getString(cProf.getColumnIndex("_ID")).toString()),c);													
					}else{								
						bd.inserirRegistro("profissional", c);							
					}//Fim else
					cProf.close();
				}	
				msg = msg + "Profissionais - SIM\n";				
				bd.fechaBanco();
			} else {
				msg = msg + "Profissionais - NÃO\n";			
			}//Fim else
		} catch (FileNotFoundException e) {
			System.out.println("Erro Importando Profissionais: "+e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro Importando Profissionais: "+e.getMessage());
		} catch (JDOMException e) {
			System.out.println("Erro Importando Profissionais: "+e.getMessage());
		}
		
	}//Fim do Método ImportarUsuarios
	
	public void ImportaUsuarios(){
		
		try {			
			if (!(xml.carregar("scs.xml","usuario") == null)){		
				//I M P O R T A Ç Ã O   D E   U S U Á R I O S
				@SuppressWarnings("rawtypes")
				List<Element> usuarios = xml.carregar("scs.xml","usuario");
				bd = bd.open();				
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
					cUsuario = bd.consulta("usuarios", new String[] { "_ID" }, "USU_MATRICULA = "+usuario.getChildText("codigoUsuario"), null, null, null, null, null);
					cUsuario.moveToFirst();						
					if (cUsuario.getCount() > 0){
						bd.atualizarDadosTabela("usuarios",Integer.valueOf(cUsuario.getString(cUsuario.getColumnIndex("_ID")).toString()),c);													
					}else{								
						bd.inserirRegistro("usuarios", c);							
					}//Fim else
					cUsuario.close();
				}	
				msg = msg + "Usuários - SIM\n";				
				bd.fechaBanco();
			} else {
				msg = msg + "Usuários - NÃO\n";			
			}//Fim else
		} catch (FileNotFoundException e) {
			System.out.println("Erro Importando Usuários: "+e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro Importando Usuários: "+e.getMessage());
		} catch (JDOMException e) {
			System.out.println("Erro Importando Usuários: "+e.getMessage());
		}
		
	}//Fim do Método ImportarUsuarios
	
	public void ImportaBairros(){
		//I M P O R T A Ç Ã O   D O S   B A I R R O S					
		try {
			
			if (!(xml.carregar("scs.xml","bairro") == null)){
				List<Element> bairros = xml.carregar("scs.xml","bairro");
				bd = bd.open();
				ContentValues c = new ContentValues();					
				for(Element bairro : bairros){					
					c.clear();				
					c.put("COD_RET", bairro.getChildText("codigoBairro"));
					c.put("DESCRICAO", bairro.getChildText("descricaoBairro"));
					c.put("CEP", bairro.getChildText("cepBairro"));
					cBairro = bd.consulta("bairros", new String[] { "_ID" }, "COD_RET = "+bairro.getChildText("codigoBairro").trim(), null, null, null, null, null);
					cBairro.moveToFirst();						
					if (cBairro.getCount() > 0){
						bd.atualizarDadosTabela("bairros",Integer.valueOf(cBairro.getString(cBairro.getColumnIndex("_ID")).toString()),c);													
					}else{								
						bd.inserirRegistro("bairros", c);							
					}//Fim else
					cBairro.close();
				}//Fim for
					msg = msg + "Bairros - SIM\n";
					bd.fechaBanco();
				} else {
					msg = msg + "Bairros - NÃO\n";
				}//Fim else
			} catch (FileNotFoundException e) {
				System.out.println("Erro Importando Bairros: "+e.getMessage());
			} catch (IOException e) {
				System.out.println("Erro Importando Bairros: "+e.getMessage());
			} catch (JDOMException e) {
				System.out.println("Erro Importando Bairros: "+e.getMessage());
			}
	}//Fim do MÃ©todo ImportaBairros
	
	public void ImportaRuas(){
		//I M P O R T A Ç Ã O   D O S   L O G R A D O U R O S		
		try {
			if (!(xml.carregar("scs.xml","rua") == null)){
				List<Element> ruas = xml.carregar("scs.xml","rua"); 
				bd = bd.open();
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
					cRuas = bd.consulta("ruas", new String[] { "_ID" }, "COD_RET = "+rua.getChildText("codigoRua").trim()+" AND USU_VINCULADO = "+rua.getChildText("codigoAgenteRua"), null, null, null, null, null);										
					cRuas.moveToFirst();						
					if (cRuas.getCount() > 0){
						bd.atualizarDadosTabela("ruas",Integer.valueOf(cRuas.getString(cRuas.getColumnIndex("_ID")).toString()),c);													
					}else{								
						bd.inserirRegistro("ruas", c);							
					}//Fim else
					cRuas.close();
				}//Fim for
					msg = msg + "Ruas - SIM\n";
					bd.fechaBanco();
				} else {
					msg = msg + "Ruas - NÃO\n";
				}//Fim else
			} catch (FileNotFoundException e) {
				System.out.println("Erro Importando Ruas: "+e.getMessage());
			} catch (IOException e) {
				System.out.println("Erro Importando Ruas: "+e.getMessage());
			} catch (JDOMException e) {
				System.out.println("Erro Importando Ruas: "+e.getMessage());				
			}
	}//Fim do MÃ©todo ImportaRuas	
	
	public void ImportaResidencias(){
		
		try {
			if (!(xml.carregar("scs.xml","residencia") == null)){		
				
				List<Element> residencias = xml.carregar("scs.xml","residencia");
				bd = bd.open();				
				ContentValues c = new ContentValues();	
				
				SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
				
				for(Element residencia : residencias){
					
					c.clear();					
					c.put("ENDERECO", residencia.getChildText("nomeRuaResicencia")); 
					c.put("NUMERO", residencia.getChildText("num_residenciaResidencia"));
					c.put("BAIRRO", residencia.getChildText("nomeBairroResidencia"));
					c.put("COD_BAIRRO", residencia.getChildText("codigoBairroResidencia").trim());
					c.put("COD_ENDERECO", residencia.getChildText("codigoRuaResidencia").trim());
					c.put("CEP", residencia.getChildText("cep"));
					c.put("MUNICIPIO",residencia.getChildText("codIBGEResidencia")+"-"+residencia.getChildText("nomeMunicipioResidencia"));
					c.put("SEG_TERRIT", residencia.getChildText("segmentoResidencia").trim());
					c.put("AREA", residencia.getChildText("areaResidencia").trim());
					c.put("MICROAREA", residencia.getChildText("microareaResidencia").trim());
					c.put("DATA_CADASTRO", formatador.format(new Date(System.currentTimeMillis())));
					c.put("TIPO_CASA", residencia.getChildText("tipocasaResidencia"));
					c.put("TIPO_CASA_OUTROS", residencia.getChildText("outrotipocasaResidencia"));
					c.put("DEST_LIXO", residencia.getChildText("destlixoResidencia").trim());
					c.put("TRAT_AGUA", residencia.getChildText("tatamentoaguaResidencia").trim());
					c.put("ABAST_AGUA", residencia.getChildText("abastecimentoaguaResidencia").trim());
					c.put("DEST_FEZES", residencia.getChildText("destfezesResidencia").trim());
					c.put("CASO_DOENCA", residencia.getChildText("casodoencaResidencia").trim());
					c.put("CASO_DOENCA_OUTROS", residencia.getChildText("ourtocasodoencaResidencia").trim()); 
					c.put("MEIO_COMUNICACAO", residencia.getChildText("meiocomunicacaoResidencia").trim());
					c.put("PART_GRUPOS", residencia.getChildText("participagrupoResidencia").trim());
					c.put("MEIO_TRANSPORTE", residencia.getChildText("meiotransporteResidencia").trim());					
					c.put("POSSUI_PLANO", ((residencia.getChildText("possuiplanosaude").trim().equals("N") || residencia.getChildText("possuiplanosaude").trim().equals("") || residencia.getChildText("possuiplanosaude").trim().equals("null")) ? "N" : residencia.getChildText("possuiplanosaude").trim()));
					c.put("NUM_PESSOAS_COM_PLANO", ((residencia.getChildText("possuiplanosaude").trim().equals("N") || residencia.getChildText("possuiplanosaude").trim().equals("") || residencia.getChildText("possuiplanosaude").trim().equals("null")) ? "0": residencia.getChildText("numeropessoascobertasplanosaude").trim()));
					c.put("MEIO_COMUNICACAO_OUTRO", (residencia.getChildText("meiocomunicacaoResidencia").toString().trim().equals("Outro") ? residencia.getChildText("outromeiocomunicacao").toString() : ""));
					c.put("PART_GRUPOS_OUTRO", (residencia.getChildText("participagrupoResidencia").toString().trim().equals("Outro") ? residencia.getChildText("outroparticipagrupo").toString() : ""));
					c.put("MEIO_TRANSPORTE_OUTRO", (residencia.getChildText("meiotransporteResidencia").toString().trim().equals("Outro") ? residencia.getChildText("outromeiotransporte").toString() : ""));
					c.put("FL_ENERGIA", residencia.getChildText("possuienergiaeletrica"));
					c.put("NUM_COMODOS", residencia.getChildText("numerocomodos"));
					c.put("COD_FAMILIA", residencia.getChildText("numero_familia"));
					c.put("COMPLEMENTO", residencia.getChildText("complemento"));
					c.put("FL_APTO_BENEFICIO", residencia.getChildText("utiliza_beneficio"));
					c.put("NOME_BENEFICIO", residencia.getChildText("nomebeneficio")); 
										
					cResidencia = bd.consulta("residencia", new String[] { "_ID" }, "COD_ENDERECO = ? and NUMERO = ? and COMPLEMENTO = ? ",  
																					new String[] { residencia.getChildText("codigoRuaResidencia").trim(), 
																								   residencia.getChildText("num_residenciaResidencia").trim(), 
																								   residencia.getChildText("complemento")}, null, null, null, null);
					cResidencia.moveToFirst();						
					if (cResidencia.getCount() > 0){
						bd.atualizarDadosTabela("residencia",Integer.valueOf(cResidencia.getString(cResidencia.getColumnIndex("_ID")).toString()),c);													
					}else{								
						bd.inserirRegistro("residencia", c); 							
					}//Fim else
					cResidencia.close();
				}	
				msg = msg + "Residências - SIM\n";				
				bd.fechaBanco();
			} else {
				msg = msg + "Residências - NÃO\n";			
			}//Fim else
		} catch (FileNotFoundException e) {
			System.out.println("Erro Importando ResidÃªncias: "+e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro Importando ResidÃªncias: "+e.getMessage());
		} catch (JDOMException e) {
			System.out.println("Erro Importando ResidÃªncias: "+e.getMessage());
		}
		
	}//Fim do Método ImportaResidencia
	
	public void ImportaFamiliar(){
		
		int cont = 0;
		
		try {
			if (!(xml.carregar("scs.xml","familiar") == null)){		
				
				List<Element> familiares = xml.carregar("scs.xml","familiar");
				bd = bd.open();				
				ContentValues c = new ContentValues();	
				
				SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
				
				for(Element familiar : familiares){
					
					c.clear();					
					c.put("NOME", familiar.getChildText("nomeFamiliar"));
					c.put("ENDERECO", familiar.getChildText("ruaFamiliar"));
					c.put("NUMERO", familiar.getChildText("numeroFamiliar"));
					c.put("DTNASCIMENTO", familiar.getChildText("dataNascimentoFamiliar"));
					c.put("FREQ_ESCOLA", familiar.getChildText("freqEscFamiliar"));
					c.put("SEXO", familiar.getChildText("sexoFamiliar"));
					c.put("ALFABETIZADO", familiar.getChildText("alfabetizadoFamiliar"));
					c.put("OCUPACAO", familiar.getChildText("ocupacaoFamiliar"));
					c.put("FL_HANSENIASE", familiar.getChildText("hanseniaseFamiliar"));
					c.put("FL_HIPERTENSAO", familiar.getChildText("hipertensaoFamiliar"));
					c.put("FL_GESTANTE", familiar.getChildText("gestanteFamiliar"));
					c.put("FL_TUBERCULOSE", familiar.getChildText("tuberculoseFamiliar"));
					c.put("FL_ALCOLISMO", familiar.getChildText("alcolismoFamiliar"));
					c.put("FL_CHAGAS", familiar.getChildText("chagasFamiliar"));
					c.put("FL_DEFICIENTE", familiar.getChildText("deficienciaFamiliar"));
					c.put("FL_MALARIA", familiar.getChildText("malariaFamiliar"));
					c.put("FL_DIABETE", familiar.getChildText("diabetesFamiliar"));
					c.put("FL_EPILETICO", familiar.getChildText("epilepsiaFamiliar"));			
					c.put("COD_ENDERECO", familiar.getChildText("codigoRuaFamiliar"));
					c.put("HASH", familiar.getChildText("idMD5Familiar").trim());
					c.put("NOME_PAI", familiar.getChildText("nomepai"));
					c.put("NOME_MAE", familiar.getChildText("nomemae"));
					c.put("COMPLEMENTO", familiar.getChildText("complemento"));
					c.put("FL_FALECEU", familiar.getChildText("obito"));
					c.put("FL_MUDOU_SE", familiar.getChildText("mudou_se"));
					c.put("DATA_ATUALIZACAO", formatador.format(new Date(System.currentTimeMillis())));
										
					cFamiliar = bd.consulta("residente", new String[] { "_ID" }, "HASH = ? ",  new String[] { familiar.getChildText("idMD5Familiar").trim() }, null, null, null, null);
					cFamiliar.moveToFirst();						
					if (cFamiliar.getCount() > 0){
						bd.atualizarDadosTabela("residente",Integer.valueOf(cFamiliar.getString(cFamiliar.getColumnIndex("_ID")).toString()),c);													
					}else{								
						bd.inserirRegistro("residente", c);							
					}//Fim else
					cFamiliar.close();
				}	
				msg = msg + "Familiares - SIM\n";				
				bd.fechaBanco();
			} else {
				msg = msg + "Familiares - NÃO\n";			
			}//Fim else
		} catch (FileNotFoundException e) {
			System.out.println("Erro Importando Familiares: "+e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro Importando Familiares: "+e.getMessage());
		} catch (JDOMException e) {
			System.out.println("Erro Importando Familiares: "+e.getMessage());
		}
		
	}//Fim do Método Familiar
	

	public void ImportaAcompPadrao(){ 
		//I M P O R T A Ç Ã O   D O S  A C O M P A N H A M E N T O S  H A N S E N I A S E		
		try {
			if (!(xml.carregar("scs.xml","acomppadrao") == null)){
				List<Element> Lista_Acomp = xml.carregar("scs.xml","acomppadrao"); 
				bd = bd.open();
				ContentValues c = new ContentValues();					
				for(Element Acomp : Lista_Acomp){
					c.clear();				
					c.put("HASH", Acomp.getChildText("idfamiliar").trim());					
					c.put("DT_VISITA", Acomp.getChildText("datavisita"));
					c.put("DT_ATUALIZACAO", Acomp.getChildText("datavisita"));
					c.put("FL_HOSPITALIZADA", Acomp.getChildText("hospitalizada"));
					c.put("MOTIVO_HOSPITALIZACAO", Acomp.getChildText("motivo_hospitalizacao"));					
					c.put("FL_DOENTE", Acomp.getChildText("doente"));
					c.put("DESC_DOENCA", Acomp.getChildText("qual_doenca"));
					c.put("OBSERVACAO", Acomp.getChildText("observacao"));
					
					cAcomp = bd.consulta("acompanhamento", new String[] { "_ID,HASH,DT_VISITA" }, "HASH = '"+Acomp.getChildText("idfamiliar").trim()+"' AND DT_VISITA = '"+Acomp.getChildText("datavisita").trim()+"'", null, null, null, null, null);										
					cAcomp.moveToFirst();						
					if (cAcomp.getCount() > 0){
						bd.atualizarDadosTabela("acompanhamento",Integer.valueOf(cAcomp.getString(cAcomp.getColumnIndex("_ID")).toString()),c);													
					}else{								
						bd.inserirRegistro("acompanhamento", c);							
					}//Fim else
					cAcomp.close();
				}//Fim for
					msg = msg + "Acompanhamento - SIM\n";
					bd.fechaBanco();
				} else {
					msg = msg + "Acompanhamento - NÃO\n";
				}//Fim else
			} catch (FileNotFoundException e) {
				System.out.println("Erro Importando Acompanhamento Padrão: "+e.getMessage());
			} catch (IOException e) {
				System.out.println("Erro Importando Acompanhamento Padrão: "+e.getMessage());
			} catch (JDOMException e) {
				System.out.println("Erro Importando Acompanhamento Padrão: "+e.getMessage());				
			}
	}//Fim do Método Importahan

	public void ImportaAgendamento(){
		//I M P O R T A Ç Ã O   D O S   A G E N D A M E N T O S		
		try {
			if (!(xml.carregar("scs.xml","agendamento") == null)){
				List<Element> agendamentos = xml.carregar("scs.xml","agendamento"); 
				bd = bd.open();
				ContentValues c = new ContentValues();					
				for(Element agendamento : agendamentos){
					c.clear();				
					c.put("HASH", agendamento.getChildText("idfamiliar"));					
					c.put("DT_REGISTRO", "");
					c.put("FL_URGENTE", agendamento.getChildText("urgente"));
					c.put("TIPO_AGENDAMENTO", agendamento.getChildText("tpconsulta"));
					c.put("PROFISSIONAL", agendamento.getChildText("profissional"));					
					c.put("DT_AGENDAMENTO", agendamento.getChildText("dtagendamento"));
					c.put("HR_AGENDAMENTO", agendamento.getChildText("hora"));
					c.put("DESCRICAO", agendamento.getChildText("descricao"));
					cAgendamentos = bd.consulta("agendamento", new String[] { "_ID, HASH,PROFISSIONAL" }, "HASH = '"+agendamento.getChildText("idfamiliar").trim()+"' AND PROFISSIONAL = '"+agendamento.getChildText("profissional")+"' AND DT_AGENDAMENTO = '"+agendamento.getChildText("dtagendamento")+"' ", null, null, null, null, null);										
					cAgendamentos.moveToFirst();						
					if (cAgendamentos.getCount() > 0){
						bd.atualizarDadosTabela("agendamento",Integer.valueOf(cAgendamentos.getString(cAgendamentos.getColumnIndex("_ID")).toString()),c);													
					}else{								
						bd.inserirRegistro("agendamento", c);							
					}//Fim else
					cAgendamentos.close();
				}//Fim for
					msg = msg + "Agendamento - SIM\n";
					bd.fechaBanco();
				} else {
					msg = msg + "Agendamento - NÃO\n";
				}//Fim else
			} catch (FileNotFoundException e) {
				System.out.println("Erro Importando Agendamentos: "+e.getMessage());
			} catch (IOException e) {
				System.out.println("Erro Importando Agendamentos: "+e.getMessage());
			} catch (JDOMException e) {
				System.out.println("Erro Importando Agendamentos: "+e.getMessage());				
			}
	}//Fim do Metódo Importar Agendamentos

	
	
	public void ImportaHan(){
		//I M P O R T A Ç Ã O   D O S  A C O M P A N H A M E N T O S  H A N S E N I A S E		
		try {
			if (!(xml.carregar("scs.xml","hanseniase") == null)){
				List<Element> Lista_Han = xml.carregar("scs.xml","hanseniase"); 
				bd = bd.open();
				ContentValues c = new ContentValues();					
				for(Element Han : Lista_Han){
					c.clear();				
					c.put("HASH", Han.getChildText("idmd5familiar").trim());					
					c.put("DT_VISITA", Han.getChildText("dtvisita"));
					c.put("DT_ATUALIZACAO", Han.getChildText("dtvisita"));
					c.put("DT_ULTIMA_CONSULTA", Han.getChildText("dtultconsulta"));
					c.put("DT_ULTIMA_DOSE", Han.getChildText("dtutdosesupervisionada"));					
					c.put("TOMA_MEDICACAO", Han.getChildText("tmmedicacaodiaria"));
					c.put("AUTO_CUIDADOS", Han.getChildText("fzautoscuidados"));
					c.put("COMUNICANTES_EXAMINADOS", Han.getChildText("comexaminados"));
					c.put("COMUNICANTES_BCG", Han.getChildText("cmrecebbcg"));
					c.put("OBSERVACAO", Han.getChildText("observacoes"));
					c.put("NUMERO_COMUNICANTES", 0);
					cHan = bd.consulta("hanseniase", new String[] { "_ID,HASH,DT_VISITA" }, "HASH = '"+Han.getChildText("idmd5familiar").trim()+"' AND DT_VISITA = '"+Han.getChildText("dtvisita").trim()+"'", null, null, null, null, null);										
					cHan.moveToFirst();						
					if (cHan.getCount() > 0){
						bd.atualizarDadosTabela("hanseniase",Integer.valueOf(cHan.getString(cHan.getColumnIndex("_ID")).toString()),c);													
					}else{								
						bd.inserirRegistro("hanseniase", c);							
					}//Fim else
					cHan.close();
				}//Fim for
					msg = msg + "Hanseníase - SIM\n";
					bd.fechaBanco();
				} else {
					msg = msg + "Hanseníase - NÃO\n";
				}//Fim else
			} catch (FileNotFoundException e) {
				System.out.println("Erro Importando Acompanhamento Hanseniase: "+e.getMessage());
			} catch (IOException e) {
				System.out.println("Erro Importando Acompanhamento Hanseniase: "+e.getMessage());
			} catch (JDOMException e) {
				System.out.println("Erro Importando Acompanhamento Hanseniase: "+e.getMessage());				
			}
	}//Fim do Método Importahan
	
	public void ImportaCriancas(){
		//I M P O R T A Ç Ã O   D O S  A C O M P A N H A M E N T O S  C R I A N Ç A		
		try {
			if (!(xml.carregar("scs.xml","acompcrianca") == null)){
				List<Element> Lista_Cri = xml.carregar("scs.xml","acompcrianca"); 
				bd = bd.open();
				ContentValues c = new ContentValues();					
				for(Element Cri : Lista_Cri){
					c.clear();				
					c.put("HASH", Cri.getChildText("idmd5familiar").trim());					
					c.put("DT_VISITA", Cri.getChildText("dtvisita"));
					c.put("DT_ATUALIZACAO", Cri.getChildText("dtvisita"));
					c.put("ALTURA", Cri.getChildText("altura"));
					c.put("PESO", Cri.getChildText("peso"));					
					c.put("PER_CEFALICO", Cri.getChildText("perimetrocefalico"));
					c.put("APGAR5", Cri.getChildText("apgar"));
					c.put("TP_PARTO", Cri.getChildText("tipoparto"));
					c.put("SITUACAO", Cri.getChildText("situacao"));
					c.put("OBSERVACAO", Cri.getChildText("obs"));					
					cCri = bd.consulta("crianca", new String[] { "_ID,HASH,DT_VISITA" }, "HASH = '"+Cri.getChildText("idmd5familiar").trim()+"' AND DT_VISITA = '"+Cri.getChildText("dtvisita").trim()+"'", null, null, null, null, null);										
					cCri.moveToFirst();						
					if (cCri.getCount() > 0){
						bd.atualizarDadosTabela("crianca",Integer.valueOf(cCri.getString(cCri.getColumnIndex("_ID")).toString()),c);													
					}else{								
						bd.inserirRegistro("crianca", c);							
					}//Fim else
					cCri.close();
				}//Fim for
					msg = msg + "Crianças - SIM\n";
					bd.fechaBanco();
				} else {
					msg = msg + "Crianças - NÃO\n";
				}//Fim else
			} catch (FileNotFoundException e) {
				System.out.println("Erro Importando Acompanhamento Criança: "+e.getMessage());
			} catch (IOException e) {
				System.out.println("Erro Importando Acompanhamento Criança: "+e.getMessage());
			} catch (JDOMException e) {
				System.out.println("Erro Importando Acompanhamento Criança: "+e.getMessage());				
			}
	}//Fim do Método Importahan	
	
	public void ImportaHa(){
		//I M P O R T A Ç Ã O   D O S  A C O M P A N H A M E N T O S  H I P E R T E N S Ã O		
		try {
			if (!(xml.carregar("scs.xml","hipertensao") == null)){
				List<Element> Lista_Ha = xml.carregar("scs.xml","hipertensao"); 
				bd = bd.open();
				ContentValues c = new ContentValues();					
				for(Element Ha : Lista_Ha){
					c.clear();				
					c.put("HASH", Ha.getChildText("idmd5familiar").trim());					
					c.put("DT_VISITA", Ha.getChildText("dtvisita"));
					c.put("DT_ATUALIZACAO", Ha.getChildText("dtvisita"));
					c.put("FL_FAZ_DIETA", Ha.getChildText("fzdieta").trim());
					c.put("FL_TOMA_MEDICACAO", Ha.getChildText("tmmedicacao").trim());					
					c.put("FL_FAZ_EXERCICIOS", Ha.getChildText("fzexfisicos").trim());
					c.put("PRESSAO_ARTERIAL", Ha.getChildText("pressaoarterial"));
					c.put("DT_ULTIMA_VISITA", Ha.getChildText("dtultvisita"));
					c.put("OBSERVACAO", Ha.getChildText("obs"));
					cHa = bd.consulta("hipertensao", new String[] { "_ID,HASH,DT_VISITA" }, "HASH = '"+Ha.getChildText("idmd5familiar").trim()+"' AND DT_VISITA = '"+Ha.getChildText("dtvisita").trim()+"'", null, null, null, null, null);										
					cHa.moveToFirst();						
					if (cHa.getCount() > 0){
						bd.atualizarDadosTabela("hipertensao",Integer.valueOf(cHa.getString(cHa.getColumnIndex("_ID")).toString()),c);													
					}else{								
						bd.inserirRegistro("hipertensao", c);							
					}//Fim else
					cHa.close();
				}//Fim for
					msg = msg + "Hipertensão - SIM\n";
					bd.fechaBanco();
				} else {
					msg = msg + "Hipertensão - NÃO\n";
				}//Fim else
			} catch (FileNotFoundException e) {
				System.out.println("Erro Importando Acompanhamento HipertensÃ£o: "+e.getMessage());
			} catch (IOException e) {
				System.out.println("Erro Importando Acompanhamento HipertensÃ£o: "+e.getMessage());
			} catch (JDOMException e) {
				System.out.println("Erro Importando Acompanhamento HipertensÃ£o: "+e.getMessage());				
			}
	}//Fim do MÃ©todo ImportaHa
	
	
	public void ImportaDia(){
		//I M P O R T A Ç Ã O   D O S  A C O M P A N H A M E N T O S  D I A B E T E S		
		try {
			if (!(xml.carregar("scs.xml","diabetes") == null)){
				List<Element> Lista_Ha = xml.carregar("scs.xml","diabetes"); 
				bd = bd.open();
				ContentValues c = new ContentValues();					
				for(Element Ha : Lista_Ha){
					c.clear();				
					c.put("HASH", Ha.getChildText("idmd5familiar").trim());					
					c.put("DT_VISITA", Ha.getChildText("dtvisita"));
					c.put("DT_ATUALIZACAO", Ha.getChildText("dtvisita"));
					c.put("FL_FAZ_DIETA", Ha.getChildText("fzdieta").trim());
					c.put("FL_FAZ_EXCERCICIOS", Ha.getChildText("fzexfisicos"));					
					c.put("FL_USA_INSULINA", Ha.getChildText("usinsulina").trim());
					c.put("FL_USA_HIPOGLICEMIANTE", Ha.getChildText("tmhipoglicoral"));
					c.put("DT_ULTIMA_VISITA", Ha.getChildText("dtultvisita"));
					c.put("OBSERVACAO", Ha.getChildText("obs"));
					cHa = bd.consulta("diabete", new String[] { "_ID,HASH,DT_VISITA" }, "HASH = '"+Ha.getChildText("idmd5familiar").trim()+"' AND DT_VISITA = '"+Ha.getChildText("dtvisita").trim()+"'", null, null, null, null, null);										
					cHa.moveToFirst();						
					if (cHa.getCount() > 0){
						bd.atualizarDadosTabela("diabete",Integer.valueOf(cHa.getString(cHa.getColumnIndex("_ID")).toString()),c);													
					}else{								
						bd.inserirRegistro("diabete", c);							
					}//Fim else
					cHa.close();
				}//Fim for
					msg = msg + "Diabete - SIM\n";
					bd.fechaBanco();
				} else {
					msg = msg + "Diabete - NÃO\n";
				}//Fim else
			} catch (FileNotFoundException e) {
				System.out.println("Erro Importando Acompanhamento Diabete: "+e.getMessage());
			} catch (IOException e) {
				System.out.println("Erro Importando Acompanhamento Diabete: "+e.getMessage());
			} catch (JDOMException e) {
				System.out.println("Erro Importando Acompanhamento Diabete: "+e.getMessage());				
			}
	}//Fim do MÃ©todo ImportaHa
	
	
	public void ImportaGes(){
		//I M P O R T A Ç Ã O   D O S  A C O M P A N H A M E N T O S  G E S T A N T E S	
		try {
			if (!(xml.carregar("scs.xml","gestante") == null)){
				List<Element> Lista_Ges = xml.carregar("scs.xml","gestante"); 
				bd = bd.open();
				ContentValues c = new ContentValues();					
				for(Element Ges : Lista_Ges){
					c.clear();				
					c.put("HASH", Ges.getChildText("idmd5familiar").trim());					
					c.put("DT_VISITA", Ges.getChildText("dtvisita"));
					c.put("DT_ATUALIZACAO", Ges.getChildText("dtvisita"));
					c.put("DT_ULTIMA_REGRA", Ges.getChildText("dtultregra"));
					c.put("DT_PROVAVEL_PARTO", Ges.getChildText("dtprovavelparto"));					
					c.put("DT_CONSULTA_PUERBIO", Ges.getChildText("dtconspuerbio"));
					c.put("EST_NUTRICIONAL", Ges.getChildText("estnutricional"));
					c.put("MES_GESTACAO", Ges.getChildText("mesgestacao"));
					c.put("DT_PRE_NATAL", Ges.getChildText("dtconsulprenatal"));
					c.put("FATORES_RISCO", Ges.getChildText("fr6mgestacao")+Ges.getChildText("fr36oumais")+Ges.getChildText("frsangramento")+Ges.getChildText("frdiabetes")+
										   Ges.getChildText("frnatrimaborto")+Ges.getChildText("frmeno20anos")+Ges.getChildText("fredema")+Ges.getChildText("frpressaoalta"));
					c.put("RESULTADO_GESTACAO","");
					c.put("OBSERVACAO", Ges.getChildText("obs"));
					cGes = bd.consulta("gestacao", new String[] { "_ID,HASH,DT_VISITA" }, "HASH = '"+Ges.getChildText("idmd5familiar").trim()+"' AND DT_VISITA = '"+Ges.getChildText("dtvisita").trim()+"'", null, null, null, null, null);										
					cGes.moveToFirst();						
					if (cGes.getCount() > 0){
						bd.atualizarDadosTabela("gestacao",Integer.valueOf(cGes.getString(cGes.getColumnIndex("_ID")).toString()),c);													
					}else{								
						bd.inserirRegistro("gestacao", c);							
					}//Fim else
					cGes.close();
				}//Fim for
					msg = msg + "Gestantes - SIM\n";
					bd.fechaBanco();
				} else {
					msg = msg + "Gestantes - NÃO\n";
				}//Fim else
			} catch (FileNotFoundException e) {
				System.out.println("Erro Importando Acompanhamento Gestantes: "+e.getMessage());
			} catch (IOException e) {
				System.out.println("Erro Importando Acompanhamento Gestantes: "+e.getMessage());
			} catch (JDOMException e) {
				System.out.println("Erro Importando Acompanhamento Gestantes: "+e.getMessage());				
			}
	}//Fim do Método ImportaGes

	
	public void ImportaTb(){
		//I M P O R T A Ç Ã O   D O S  A C O M P A N H A M E N T O S  T U B E R C U L O S E		
		try {
			if (!(xml.carregar("scs.xml","tuberculose") == null)){
				List<Element> Lista_Tb = xml.carregar("scs.xml","tuberculose"); 
				bd = bd.open();
				ContentValues c = new ContentValues();					
				for(Element Tb : Lista_Tb){
					c.clear();				
					c.put("HASH", Tb.getChildText("idmd5familiar").trim());					
					c.put("DT_VISITA", Tb.getChildText("dtvisita"));
					c.put("DT_ATUALIZACAO", Tb.getChildText("dtvisita"));
					c.put("FL_MEDIC_DIARIA", Tb.getChildText("tmmeddiaria"));
					c.put("FL_REACOES_IND", Tb.getChildText("recindesej"));					
					c.put("FL_EXAME_ESCARRO", Tb.getChildText("exescar"));
					c.put("COMUNIC_EXAMINADOS", Tb.getChildText("comexami"));
					c.put("MENOR_BCG", Tb.getChildText("mn5anoscombcg"));
					c.put("OBSERVACAO", Tb.getChildText("obs"));
					cTb = bd.consulta("tuberculose", new String[] { "_ID, HASH, DT_VISITA" }, "HASH = '"+Tb.getChildText("idmd5familiar").trim()+"' AND DT_VISITA = '"+Tb.getChildText("dtvisita").trim()+"'", null, null, null, null, null);										
					cTb.moveToFirst();						
					if (cTb.getCount() > 0){
						bd.atualizarDadosTabela("tuberculose",Integer.valueOf(cTb.getString(cTb.getColumnIndex("_ID")).toString()),c);													
					}else{								
						bd.inserirRegistro("tuberculose", c);							
					}//Fim else
					cTb.close();
				}//Fim for
					msg = msg + "Tuberculose - SIM\n";
					bd.fechaBanco();
				} else {
					msg = msg + "Tuberculose - NÃO\n";
				}//Fim else
			} catch (FileNotFoundException e) {
				System.out.println("Erro Importando Acompanhamento Tuberculose: "+e.getMessage());
			} catch (IOException e) {
				System.out.println("Erro Importando Acompanhamento Tuberculose: "+e.getMessage());
			} catch (JDOMException e) {
				System.out.println("Erro Importando Acompanhamento Tuberculose: "+e.getMessage());				
			}
	}//Fim do Método ImportaTb
	
	public void ImportaVacinas(){
		
		//I M P O R T A Ç Ã O   D O S  A C O M P A N H A M E N T O S  V A C I N A S 	
		try {
			if (!(xml.carregar("scs.xml","vacinas") == null)){
				List<Element> Lista_Vacinas = xml.carregar("scs.xml","vacinas"); 
				bd = bd.open();
				ContentValues c = new ContentValues();		
				
				SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
				
				for(Element Vacina : Lista_Vacinas){
					c.clear();				
					c.put("HASH", Vacina.getChildText("idfamiliar").trim());					
					c.put("TIPO_VACINA", Vacina.getChildText("tipovacina"));
					c.put("DOSE_APLICADA", Vacina.getChildText("doseaplicada").trim());
					c.put("DT_APLICACAO", Vacina.getChildText("dataaplicacao"));
					c.put("DT_CADASTRO", formatador.format(new Date(System.currentTimeMillis())));					
					c.put("LOTE", Vacina.getChildText("lotevacina"));
					c.put("FL_APLICADA", Vacina.getChildText("aplicada")); 
					c.put("TIPO", Vacina.getChildText("tipo").trim());
					cVacina = bd.consulta("vacinas", new String[] { "_ID, HASH, TIPO_VACINA, DOSE_APLICADA, TIPO " }, "HASH = '"+Vacina.getChildText("idfamiliar").trim()+
																													  "' AND TIPO_VACINA = '"+Vacina.getChildText("tipovacina")+
																													  "' AND DOSE_APLICADA = '"+Vacina.getChildText("doseaplicada").trim()+
																													  "' AND TIPO = '"+Vacina.getChildText("tipo").trim()+"'", null, null, null, null, null);										
					cVacina.moveToFirst();						
					if (cVacina.getCount() > 0){
						bd.atualizarDadosTabela("vacinas",Integer.valueOf(cVacina.getString(cVacina.getColumnIndex("_ID")).toString()),c);													
					}else{								
						bd.inserirRegistro("vacinas", c);							
					}//Fim else
					cVacina.close();
				}//Fim for
					msg = msg + "Vacinas - SIM\n";
					bd.fechaBanco();
			} else {
				msg = msg + "Vacinas - NÃO\n";
			}//Fim else
		} catch (FileNotFoundException e) {
			System.out.println("Erro Importando Acompanhamento Vacinas: "+e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro Importando Acompanhamento Vacinas: "+e.getMessage());
		} catch (JDOMException e) {
			System.out.println("Erro Importando Acompanhamento Vacinas: "+e.getMessage());				
		}
		
	}//Fim do Método ImportaVacinas
	
			
	@Override
	protected void onDestroy() {		
		xml      	= null;
		cUsuario 	= null;
		cBairro  	= null;
		cRuas 	    = null;
		cResidencia = null;
		cFamiliar   = null;
		cHan		= null;
		cHa			= null;
		cGes		= null;
		cDia		= null;
		cTb			= null;
		cVacina     = null;
		super.onDestroy();		
	}

}
