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
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

public class ImportarXML extends Activity {
	
	private CarregarXML xml;
	private Banco bd;
	
	Cursor cUsuario,cBairro,cRuas,cResidencia,cFamiliar = null;
	
	String msg = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.geraxml);
				
		xml = new CarregarXML(); 
		bd = new Banco(this);		
		
		try{
			ImportaRuas();
			ImportaBairros();
			ImportaUsuarios();
			ImportaResidencias(); 
			ImportaFamiliar();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		Mensagem.exibeMessagem(this, "Importados:", msg,2000);	
					
		new Handler().postDelayed(new Runnable() {		
			public void run() {
				finish();
			}
		}, 2000);
		
	}//Fim do OnCreate	
	
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
	}//Fim do Método ImportaBairros
	
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
					cRuas = bd.consulta("ruas", new String[] { "_ID" }, "COD_RET = "+rua.getChildText("codigoRua").trim(), null, null, null, null, null);										
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
	}//Fim do Método ImportaRuas
	
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
										
					cResidencia = bd.consulta("residencia", new String[] { "_ID" }, "COD_ENDERECO = ? and NUMERO = ? ",  new String[] { residencia.getChildText("codigoRuaResidencia").trim(), residencia.getChildText("num_residenciaResidencia").trim() }, null, null, null, null);
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
			System.out.println("Erro Importando Usuários: "+e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro Importando Usuários: "+e.getMessage());
		} catch (JDOMException e) {
			System.out.println("Erro Importando Usuários: "+e.getMessage());
		}
		
	}//Fim do Método Residências
	
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
					c.put("HASH", familiar.getChildText("idMD5Familiar"));
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
			System.out.println("Erro Importando Usuários: "+e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro Importando Usuários: "+e.getMessage());
		} catch (JDOMException e) {
			System.out.println("Erro Importando Usuários: "+e.getMessage());
		}
		
	}//Fim do Método Familiar
			
	@Override
	protected void onDestroy() {
		xml      	= null;
		cUsuario 	= null;
		cBairro  	= null;
		cRuas 	    = null;
		cResidencia = null;
		cFamiliar   = null;
		super.onDestroy();
	}

}
