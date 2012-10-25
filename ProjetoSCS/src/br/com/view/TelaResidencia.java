package br.com.view;

import java.util.ArrayList;

import br.com.control.Banco;
import br.com.control.Mensagem;
import br.com.control.ResidenciaAux;
import br.com.control.Sessao;
import br.com.scs.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class TelaResidencia extends Activity implements OnClickListener {
	
	Spinner  SpUF, SpMunicipio,SpEndereco; //TAB1
	EditText EdtTipoCasa; //TAB2
    EditText EdtCasoDoenca,EdtNumPessoas,EdtNomePlano, EdtMeioComunic, EdtGruposComunit, EdtMeioTransp; //TAB3
	EditText Edtbairro, EdtCep, EdtNumero, EdtSegTerritorial, EdtArea, EdtMicArea; //TAB1
	Spinner  SpTipoCasa, SpDestinoLixo, SpTratamentoAgua, SpDestFezesUrina, SpAbastecimentoAgua; //TAB2	
    Spinner  SpCasoDoente, SpMeiosComunicacao, SpGruposComunitarios, SpTransporteUtilizado; //TAB3  
    RadioButton RbSim, RbNao;
    
    Button btnVoltar, btnSalvar;
    
    private TabHost th;
    
    public static int ID = 0;
    
    Banco bd = null;
    Cursor c = null;
    private static String codUser;    
	
	ArrayList<String> TipoCasa			  = new ArrayList<String>();
	ArrayList<String> DestinoLixo		  = new ArrayList<String>();
	ArrayList<String> TratamentoAgua	  = new ArrayList<String>();
	ArrayList<String> DestFezesUrina	  = new ArrayList<String>();
	ArrayList<String> AbastAgua			  = new ArrayList<String>();
	ArrayList<String> Municipio			  = new ArrayList<String>();
	ArrayList<String> CasoDoente		  = new ArrayList<String>();
	ArrayList<String> MeiosComunicacao	  = new ArrayList<String>();
	ArrayList<String> GruposComunitarios  = new ArrayList<String>();
	ArrayList<String> TransporteUtilizado = new ArrayList<String>();
	ArrayList<String> Enderecos           = new ArrayList<String>();
	ArrayList<String> Segmento            = new ArrayList<String>();
	ArrayList<String> Area                = new ArrayList<String>();
	ArrayList<String> MicroArea           = new ArrayList<String>();
	ArrayList<String> CodBairro           = new ArrayList<String>();	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.telaresidencia);	
		
		//SpUF                  = (Spinner)  findViewById(R.imovel.SpUF);
		SpMunicipio           = (Spinner) 	  findViewById(R.imovel.SpMunicipio);
		SpEndereco            = (Spinner) 	  findViewById(R.imovel.SpEndereco);
		Edtbairro 		      = (EditText)	  findViewById(R.imovel.edtBairro);
		EdtCep			      = (EditText)	  findViewById(R.imovel.edtCEP);
		EdtNumero		      = (EditText)	  findViewById(R.imovel.edtNumero);
		EdtSegTerritorial     = (EditText)	  findViewById(R.imovel.edtSegTerritorial);
		EdtArea			      = (EditText)	  findViewById(R.imovel.edtArea);
		EdtMicArea			  = (EditText)	  findViewById(R.imovel.edtMicroArea);
		EdtTipoCasa           = (EditText)	  findViewById(R.imovel.EdtTipoCasa);
		EdtCasoDoenca		  = (EditText)	  findViewById(R.imovel.EdtCasoDoente);
		EdtNumPessoas         = (EditText)	  findViewById(R.imovel.EdtNumPessoasCobertas);
		EdtNomePlano          = (EditText)	  findViewById(R.imovel.EdtNomePlano);
		EdtMeioComunic        = (EditText)	  findViewById(R.imovel.EdtMeioComunicacao);
		EdtGruposComunit      = (EditText)	  findViewById(R.imovel.EdtGrupoComunitario);
		EdtMeioTransp         = (EditText)	  findViewById(R.imovel.EdtMeioTransporte); 
		SpTipoCasa   	      = (Spinner) 	  findViewById(R.imovel.SpTipoCasa);		
		SpDestinoLixo    	  = (Spinner) 	  findViewById(R.imovel.SpDestinoLixo);
		SpTratamentoAgua 	  = (Spinner) 	  findViewById(R.imovel.SpTratamentoAgua);
		SpDestFezesUrina      = (Spinner) 	  findViewById(R.imovel.SpDestFezesUrina);
		SpAbastecimentoAgua	  = (Spinner) 	  findViewById(R.imovel.SpAbastecimentoAgua);	
		SpCasoDoente 		  = (Spinner) 	  findViewById(R.imovel.SpCasoDoente);
	    SpMeiosComunicacao 	  = (Spinner) 	  findViewById(R.imovel.SpMeioComunicacao);
	    SpGruposComunitarios  = (Spinner)  	  findViewById(R.imovel.SpGrupoComunitario);
	    SpTransporteUtilizado = (Spinner)     findViewById(R.imovel.SpMeioTransporte);	
	    RbSim				  = (RadioButton) findViewById(R.imovel.RbSim);
	    RbNao				  = (RadioButton) findViewById(R.imovel.RbNao);
	    btnVoltar             = (Button)      findViewById(R.imovel.btnVoltarFamiliar);
	    btnSalvar             = (Button)      findViewById(R.imovel.btnSalvarFamiliar);
	    btnVoltar.setOnClickListener(this);
	    btnSalvar.setOnClickListener(this);
		RbSim.setOnClickListener(this);
		RbNao.setOnClickListener(this);
	    
		th = (TabHost) findViewById(R.imovel.tabhost);
        th.setup();
        TabSpec ts;
        
        //Primeria Aba
        ts = th.newTabSpec("tag1");
        ts.setContent(R.imovel.tabCadastroResidencia);
        ts.setIndicator("Residencia",getResources().getDrawable(R.drawable.casa));
        setOpcoesSpinnersTab1("","","");
        PreencheCampos(String.valueOf(ID), 1);
        th.addTab(ts);
        
        //Segunda Aba
        ts = th.newTabSpec("tag2");
        ts.setContent(R.imovel.tabMoradiaSaneamento);        
        ts.setIndicator("Moradia/Saneamento",getResources().getDrawable(R.drawable.saneamento));
		setOpcoesSpinnerTab2("","","","","");
		PreencheCampos(String.valueOf(ID), 2);
        th.addTab(ts);
        
        //Terceira Aba
        ts = th.newTabSpec("tag3");
        ts.setContent(R.imovel.tabOutras);        
        ts.setIndicator("Outros",getResources().getDrawable(R.drawable.outros));
        setOpcoesSpinnerTab3("","","","",false,"","","","","");
        PreencheCampos(String.valueOf(ID), 3);
        th.addTab(ts);	
      
		
	}
	
	public void PreencheCampos(String pID,int pTab){ 
		try{
			try{
				bd = new Banco(this); 
				bd.open();
				c = bd.consulta("residencia", new String[]{"*"},"_ID = "+pID, null, null, null, null, null);
				c.moveToFirst();
				if (c.getCount() > 0){
					if (pTab == 1){
						setOpcoesSpinnersTab1(c.getString(c.getColumnIndex("MUNICIPIO")).toString(), 
											  c.getString(c.getColumnIndex("COD_ENDERECO")).toString()+"-"+												
											  c.getString(c.getColumnIndex("ENDERECO")).toString(), 
											  c.getString(c.getColumnIndex("BAIRRO")).toString());
						EdtCep.setText(c.getString(c.getColumnIndex("CEP")).toString());
						EdtNumero.setText(c.getString(c.getColumnIndex("NUMERO")).toString());
						EdtSegTerritorial.setText(c.getString(c.getColumnIndex("SEG_TERRIT")).toString());
						EdtArea.setText(c.getString(c.getColumnIndex("AREA")).toString());
						EdtMicArea.setText(c.getString(c.getColumnIndex("MICROAREA")).toString());
					}else if(pTab == 2){
						setOpcoesSpinnerTab2(c.getString(c.getColumnIndex("TIPO_CASA")).toString(), 
											 c.getString(c.getColumnIndex("DEST_LIXO")).toString(), 
											 c.getString(c.getColumnIndex("TRAT_AGUA")).toString(), 
											 c.getString(c.getColumnIndex("DEST_FEZES")).toString(), 
											 c.getString(c.getColumnIndex("ABAST_AGUA")).toString());
					}else if(pTab == 3){
						setOpcoesSpinnerTab3(c.getString(c.getColumnIndex("CASO_DOENCA")).toString(), 
										     c.getString(c.getColumnIndex("MEIO_COMUNICACAO")).toString(), 
										     c.getString(c.getColumnIndex("PART_GRUPOS")).toString(), 
										     c.getString(c.getColumnIndex("MEIO_TRANSPORTE")).toString(),
										     (c.getString(c.getColumnIndex("POSSUI_PLANO")).toString().trim().equals("S") ? true : false),
										     (c.getString(c.getColumnIndex("POSSUI_PLANO")).toString().trim().equals("S") ? c.getString(c.getColumnIndex("NUM_PESSOAS_COM_PLANO")).toString() : ""),
										     (c.getString(c.getColumnIndex("POSSUI_PLANO")).toString().trim().equals("S") ? c.getString(c.getColumnIndex("NOME_PLANO_SAUDE")).toString() : ""),
										     (c.getString(c.getColumnIndex("MEIO_TRANSPORTE_OUTRO")).toString().trim().length() < 1 ? "" : c.getString(c.getColumnIndex("MEIO_TRANSPORTE_OUTRO")).toString()),
										     (c.getString(c.getColumnIndex("MEIO_COMUNICACAO_OUTRO")).toString().trim().length() < 1 ? "" : c.getString(c.getColumnIndex("MEIO_COMUNICACAO_OUTRO")).toString()),
										     (c.getString(c.getColumnIndex("PART_GRUPOS_OUTRO")).toString().trim().length() < 1 ? "" : c.getString(c.getColumnIndex("PART_GRUPOS_OUTRO")).toString()));					
					} 
					
				}
			}catch(Exception e){
				Log.i("Erro no método PreencheCampos", e.getMessage());
			}
		}finally{
			if (c != null){
				c.close();
			}
			if (bd != null){
				bd.fechaBanco();
			}
		}
	}//Fim do M�todo PreencheCampos
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.telaresidencia, menu);        
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.menu_gravar:
			if (validaCampos()==true){
				PreparaInsercao();
			}
			break;
		}
		return true;
	}
	
	public boolean validaCampos(){
		
		boolean retorno = false;
		String msgPendecias = "";
		
		if (EdtNumero.getText().toString().length() == 0){
			EdtNumero.setError("Informe o Número da Casa!");
			retorno = false;
		}else{
			retorno = true;
		}
		if (EdtSegTerritorial.getText().toString().length() == 0){
			EdtSegTerritorial.setError("Informe o Seguimento Territorial!");
			retorno = false;
		}else{
			retorno = true;
		}
		if (EdtArea.getText().toString().length() == 0){
			EdtArea.setError("Informe o Código da área!");
			retorno = false;
		}else{
			retorno = true;
		}
		if (EdtMicArea.getText().toString().length() == 0){
			EdtMicArea.setError("Informe o Código da Microarea!");
			retorno = false;
		}else{
			retorno = true;
		}
		if (SpMunicipio.getItemAtPosition(SpMunicipio.getSelectedItemPosition()).toString().trim().equals("Selecione")){			
			msgPendecias += "-> Selecione o município!\n";
			retorno = false;
		}else{
			retorno = true;
		}
		if (SpTipoCasa.getItemAtPosition(SpTipoCasa.getSelectedItemPosition()).toString().trim().equals("Selecione")){			
			msgPendecias += "-> Selecione o tipo de casa!\n";
			retorno = false;
		}else{
			retorno = true;
		}
		if (SpDestinoLixo.getItemAtPosition(SpDestinoLixo.getSelectedItemPosition()).toString().trim().equals("Selecione")){			
			msgPendecias += "-> Selecione o destino do lixo!\n";
			retorno = false;
		}else{
			retorno = true;
		}
		if (SpAbastecimentoAgua.getItemAtPosition(SpAbastecimentoAgua.getSelectedItemPosition()).toString().trim().equals("Selecione")){			
			msgPendecias += "-> Selecione o tipo de abastecimento de água!\n";
			retorno = false;
		}else{
			retorno = true;
		}		
		if (SpTratamentoAgua.getItemAtPosition(SpTratamentoAgua.getSelectedItemPosition()).toString().trim().equals("Selecione")){			
			msgPendecias += "-> Selecione o tipo de tratamento de água!\n";
			retorno = false;
		}else{
			retorno = true;
		}
		if (SpDestFezesUrina.getItemAtPosition(SpDestFezesUrina.getSelectedItemPosition()).toString().trim().equals("Selecione")){			
			msgPendecias += "-> Selecione o destino das fezes e urina!\n";
			retorno = false;
		}else{
			retorno = true;
		}
		if (SpCasoDoente.getItemAtPosition(SpCasoDoente.getSelectedItemPosition()).toString().trim().equals("Selecione")){			
			msgPendecias += "-> Selecione o que procura em caso de doença!\n";
			retorno = false;
		}else{
			retorno = true;
		}
		if (SpMeiosComunicacao.getItemAtPosition(SpMeiosComunicacao.getSelectedItemPosition()).toString().trim().equals("Selecione")){			
			msgPendecias += "-> Selecione o meio de comunicação mais utilizado!\n";
			retorno = false;
		}else{
			retorno = true;
		}
		if (SpGruposComunitarios.getItemAtPosition(SpGruposComunitarios.getSelectedItemPosition()).toString().trim().equals("Selecione")){			
			msgPendecias += "-> Selecione se participa de grupos comunitários!\n";
			retorno = false;
		}else{
			retorno = true;
		}
		if (SpTransporteUtilizado.getItemAtPosition(SpTransporteUtilizado.getSelectedItemPosition()).toString().trim().equals("Selecione")){			
			msgPendecias += "-> Selecione o meio de transporte mais utilizado!\n";
			retorno = false;
		}else{
			retorno = true;
		}
		
		if (msgPendecias.trim().length() > 0){
			Mensagem.exibeMessagem(this, "Pendências", msgPendecias);
		}
		
		return retorno;
	}
	
	private void setOpcoesSpinnersTab1(String pCidade, String pEndereco,String pBairro){
		//setOpcoesUF(pUF);
		setOpcoesMunicipios(pCidade);
		setOpcoesEnderecos(pEndereco, pBairro);
	}

	public void setOpcoesSpinnerTab2(String pTpCasa, String pDestLixo, String pTratAgua, String pDestFezes,String pAbastAgua){
		setOpcoesTipoCasa(pTpCasa);
		setOpcoesDestLixo(pDestLixo);	
		setOpcoesTratAgua(pTratAgua);
		setOpcoesDestFezes(pDestFezes);
		setOpcoesAbastAgua(pAbastAgua);
	}
	
	private void setOpcoesSpinnerTab3(String pCasoDoente, String pMeiosComunic, String pGrupoComunit,String pTransporteUtilizado,
									  boolean pTem_plano,String pNum_Com_Plano,String pNome_Plano, String pMTransp, String pMComunic, String pGpComunit){
		setOpcoesCasoDoente(pCasoDoente);
		setOpcoesMeiosComunicacao(pMeiosComunic);
		setOpcoesGruposComunitarios(pGrupoComunit);
		setOpcoesTransporteUtilizado(pTransporteUtilizado);
		EdtMeioComunic.setText(pMComunic);
		EdtMeioTransp.setText(pMTransp);
		EdtGruposComunit.setText(pGpComunit);
		if (pTem_plano == true){
			RbSim.setChecked(true);
			EdtNumPessoas.setText(pNum_Com_Plano);
			EdtNomePlano.setText(pNome_Plano);
		}else{
			RbNao.setChecked(true);
		}
	}
	
	private void setOpcoesEnderecos(String pEnd,String pBairro){
		Enderecos.clear();
		Segmento.clear();
		Area.clear();
		MicroArea.clear();		
		if ((pEnd.length()>0)&&(pBairro.length()>0)){
			Enderecos.add(pEnd);
		}
		Banco bd = null;
		Cursor csr = null;
		codUser = Sessao.SESSAO.getMatriculaUsuario(this);
		try{
			try{
				bd = new Banco(this);
				bd.open();
				if (codUser.equals("0000"))
					csr = bd.consulta("ruas", new String[]{"*"}, null, null, null, null, null, null);
				else	
					csr = bd.consulta("ruas", new String[]{"*"}, "USU_VINCULADO = ? ", new String[] {codUser}, null, null, null, null);
				csr.moveToFirst();
				for (int i = 0;i < csr.getCount(); i++){
					Enderecos.add(csr.getString(csr.getColumnIndex("COD_RET")).toString()+"-"+csr.getString(csr.getColumnIndex("DESCRICAO")).toString());
					Segmento.add(csr.getString(csr.getColumnIndex("COD_SEGMENTO")).toString());
					Area.add(csr.getString(csr.getColumnIndex("COD_AREA")).toString());
					MicroArea.add(csr.getString(csr.getColumnIndex("COD_MICROAREA")).toString());
					CodBairro.add(csr.getString(csr.getColumnIndex("COD_BAIRRO")).toString());
					csr.moveToNext();
				}
				
				PreencheSpinner(SpEndereco, Enderecos);
			}catch(Exception e){
				Log.i("Método SetOpcoesEndereco", e.getMessage());
			}
		}finally{
			if (csr != null){
				csr.close();
			}
			if (bd != null){
				bd.fechaBanco();
			}
		}
	}
	
	private void setOpcoesCasoDoente(String pDoente){
		CasoDoente.clear();
		if (pDoente.length()>0){
			CasoDoente.add(pDoente);
		}
		CasoDoente.add("Selecione");
		CasoDoente.add("Hospital");
		CasoDoente.add("Posto de Saude");
		CasoDoente.add("Auto Medicar");
		CasoDoente.add("Outro");			
		PreencheSpinner(SpCasoDoente, CasoDoente);
	}
	
	private void setOpcoesMeiosComunicacao(String pMeiosComunic){
		MeiosComunicacao.clear();
		if (pMeiosComunic.length()>0){
			MeiosComunicacao.add(pMeiosComunic);
		}
		MeiosComunicacao.add("Selecione");
		MeiosComunicacao.add("Telefone Fixo");
		MeiosComunicacao.add("Celular");
		MeiosComunicacao.add("Internet");
		MeiosComunicacao.add("Outro");		
		PreencheSpinner(SpMeiosComunicacao, MeiosComunicacao);
	}
	
	private void setOpcoesGruposComunitarios(String pGrupoComunit){
		GruposComunitarios.clear();
		if (pGrupoComunit.length() > 0){
			GruposComunitarios.add(pGrupoComunit);
		}
		GruposComunitarios.add("Selecione");
		GruposComunitarios.add("Cooperativa");
		GruposComunitarios.add("Grupo Religioso");
		GruposComunitarios.add("Associacoes");
		GruposComunitarios.add("Outro");
		PreencheSpinner(SpGruposComunitarios, GruposComunitarios);
	}
	
	private void setOpcoesTransporteUtilizado(String pTranspUtilizado){
		TransporteUtilizado.clear();
		if (pTranspUtilizado.length()>0){
			TransporteUtilizado.add(pTranspUtilizado);
		}
		TransporteUtilizado.add("Selecione");
		TransporteUtilizado.add("Carro Proprio");
		TransporteUtilizado.add("Carroca");
		TransporteUtilizado.add("Taxi");
		TransporteUtilizado.add("Onibus");
		TransporteUtilizado.add("Metro");
		TransporteUtilizado.add("Moto");
		TransporteUtilizado.add("Bicicleta");
		TransporteUtilizado.add("Outro");
		PreencheSpinner(SpTransporteUtilizado, TransporteUtilizado);
		
	}
	
	private void setOpcoesMunicipios(String pMunicipio){
		Municipio.clear();
		if (pMunicipio.length()>0){
			Municipio.add(pMunicipio);
		}
		Municipio.add("Selecione");
		Municipio.add("2304202-CRATO");
		Municipio.add("2307304-JUAZEIRO DO NORTE");		
		PreencheSpinner(SpMunicipio, Municipio);
	}
	
	private void setOpcoesTipoCasa(String pTipoCasa){
		TipoCasa.clear();
		if (pTipoCasa.length()>0){
			TipoCasa.add(pTipoCasa);
		}
		TipoCasa.add("Selecione");
		TipoCasa.add("Tijolo/Adobe");
		TipoCasa.add("Taipa Revestida");
		TipoCasa.add("Taipa Nao Revestida");
		TipoCasa.add("Madeira");
		TipoCasa.add("Material Aproveitado");
		TipoCasa.add("Outro");		
		PreencheSpinner(SpTipoCasa, TipoCasa);
	}
	
	private void setOpcoesDestLixo(String pDepLixo){
		DestinoLixo.clear();
		if (pDepLixo.length()>0){
			DestinoLixo.add(pDepLixo);
		}
		DestinoLixo.add("Selecione");
		DestinoLixo.add("Coletado");
		DestinoLixo.add("Queimado/Enterrado");
		DestinoLixo.add("Ceu Aberto");		
		PreencheSpinner(SpDestinoLixo, DestinoLixo);
	}
	
	private void setOpcoesTratAgua(String pTratAgua){
		TratamentoAgua.clear();
		if (pTratAgua.length()>0){
			TratamentoAgua.add(pTratAgua);
		}
		TratamentoAgua.add("Selecione");
		TratamentoAgua.add("Filtracao");
		TratamentoAgua.add("Fervura");
		TratamentoAgua.add("Cloracao");
		TratamentoAgua.add("Sem Tratamento");		
		PreencheSpinner(SpTratamentoAgua, TratamentoAgua);
	}
	
	private void setOpcoesDestFezes(String pDestFezes){
		DestFezesUrina.clear();
		if (pDestFezes.length()>0){
			DestFezesUrina.add(pDestFezes);
		}
		DestFezesUrina.add("Selecione");
		DestFezesUrina.add("Sistema de Esgoto");
		DestFezesUrina.add("Fossa");
		DestFezesUrina.add("Ceu Aberto");		
		PreencheSpinner(SpDestFezesUrina, DestFezesUrina);
	}
	
	private void setOpcoesAbastAgua(String pAbastecAgua){
		AbastAgua.clear();
		if (pAbastecAgua.length()>0){
			AbastAgua.add(pAbastecAgua);
		}
		AbastAgua.add("Selecione");
		AbastAgua.add("Rede Publica");
		AbastAgua.add("Poco ou Nascente");
		AbastAgua.add("Outro");
		PreencheSpinner(SpAbastecimentoAgua, AbastAgua);
	}
	
	public void PreparaInsercao(){
		ResidenciaAux r = new ResidenciaAux();
		r.ENDERECO		  	      = SpEndereco.getItemAtPosition(SpEndereco.getSelectedItemPosition()).toString();
		r.NUMERO		  	      = EdtNumero.getText().toString();
		r.BAIRRO		  	      = Edtbairro.getText().toString();
		r.CEP			  	      = EdtCep.getText().toString();
		r.MUNICIPIO		  	      = SpMunicipio.getItemAtPosition(SpMunicipio.getSelectedItemPosition()).toString();
		r.SEG_TERRIT	  	      = EdtSegTerritorial.getText().toString();
		r.AREA			  	      = EdtArea.getText().toString();
		r.MICROAREA		  	      = EdtMicArea.getText().toString();
		r.COD_FAMILIA	  	      = "";
		r.TIPO_CASA		  	      = SpTipoCasa.getItemAtPosition(SpTipoCasa.getSelectedItemPosition()).toString();
		r.DEST_LIXO		  	      = SpDestinoLixo.getItemAtPosition(SpDestinoLixo.getSelectedItemPosition()).toString();
		r.TRAT_AGUA 	  	      = SpTratamentoAgua.getItemAtPosition(SpTratamentoAgua.getSelectedItemPosition()).toString();
		r.ABAST_AGUA 	 	 	  = SpAbastecimentoAgua.getItemAtPosition(SpAbastecimentoAgua.getSelectedItemPosition()).toString();
		r.DEST_FEZES	  		  = SpDestFezesUrina.getItemAtPosition(SpDestFezesUrina.getSelectedItemPosition()).toString();
		r.CASO_DOENCA	   		  = SpCasoDoente.getItemAtPosition(SpCasoDoente.getSelectedItemPosition()).toString();
		r.MEIO_COMUNICACAO 		  = SpMeiosComunicacao.getItemAtPosition(SpMeiosComunicacao.getSelectedItemPosition()).toString();
		r.PART_GRUPOS	   		  = SpGruposComunitarios.getItemAtPosition(SpGruposComunitarios.getSelectedItemPosition()).toString();
		r.MEIO_TRANSPORTE   	  = SpTransporteUtilizado.getItemAtPosition(SpTransporteUtilizado.getSelectedItemPosition()).toString();
		r.TIPO_CASA_OUTROS   	  = EdtTipoCasa.getText().toString();
		r.CASO_DOENCA_OUTROS 	  = EdtCasoDoenca.getText().toString();
		r.MEIO_COMUNICACAO_OUTROS = EdtMeioComunic.getText().toString();
		r.MEIO_TRANSPORTE_OUTROS  = EdtMeioTransp.getText().toString();
		r.PART_GRUPOS_OUTROS      = EdtGruposComunit.getText().toString();
		
		if (RbSim.isChecked()){
			r.POSSUI_PLANO  = "S";
			r.NUM_COM_PLANO = EdtNumPessoas.getText().toString().trim();
			r.NOME_PLANO    = EdtNomePlano.getText().toString().trim(); 
		}else{
			r.POSSUI_PLANO  = "N";
			r.NUM_COM_PLANO = "";
			r.NOME_PLANO    = "";
		}
		
		if (this.ID == 0){		
			if (r.Inserir(TelaResidencia.this)==true){
				Mensagem.exibeMessagem(this, "SCS", "Sucesso ao Gravar!",2000);
				new Handler().postDelayed(new Runnable() {		
					public void run() {
						finish();
					}
				}, 2000);
			}else{
				Mensagem.exibeMessagem(this, "SCS", "Erro ao Gravar!");
			}
		}else{
			if (r.Atualizar(TelaResidencia.this, this.ID) == true){
				Mensagem.exibeMessagem(this, "SCS", "Sucesso ao Gravar!",2000);
				ClearID();
				new Handler().postDelayed(new Runnable() {		
					public void run() {
						finish();
					}
				}, 2000);
			}else{
				Mensagem.exibeMessagem(this, "SCS", "Erro ao Atualizar!");
			}
		}
	}
	
	public void ClearID(){
		this.ID = 0;
	}
	
	@Override
	protected void onDestroy() {
		if (this.ID > 0){
			ClearID();
		}
		super.onDestroy();
	}
	
	public void PreencheSpinner(final Spinner s,ArrayList<String> a){
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, a);		
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;		
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		s.setAdapter(spinnerArrayAdapter);		
		
		s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
				
				if (s == SpEndereco){
					if (SpEndereco.getItemAtPosition(posicao).toString().trim().length()>0){
						String codRua = SpEndereco.getItemAtPosition(posicao).toString().substring(0,
										SpEndereco.getItemAtPosition(posicao).toString().indexOf("-"));
						
						PreencheBairro(CodBairro.get(posicao));
						EdtSegTerritorial.setText(Segmento.get(posicao));
						EdtArea.setText(Area.get(posicao));
						EdtMicArea.setText(MicroArea.get(posicao));
					}
				}
				
				if (s == SpTipoCasa){
					if (SpTipoCasa.getItemAtPosition(posicao).toString().equals("Outro")){						
						EdtTipoCasa.setEnabled(true);						
					}else{
						EdtTipoCasa.setEnabled(false); 						
					}
				}
				
				if (s == SpCasoDoente){
					if (SpCasoDoente.getItemAtPosition(posicao).toString().equals("Outro")){						
						EdtCasoDoenca.setEnabled(true);					
					}else{
						EdtCasoDoenca.setEnabled(false);					
					}
				}
				
				if (s == SpMeiosComunicacao){
					if (SpMeiosComunicacao.getItemAtPosition(posicao).toString().equals("Outro")){						
						EdtMeioComunic.setEnabled(true);					
					}else{
						EdtMeioComunic.setEnabled(false);					
					}
				}
				
				if (s == SpGruposComunitarios){
					if (SpGruposComunitarios.getItemAtPosition(posicao).toString().equals("Outro")){						
						EdtGruposComunit.setEnabled(true);					
					}else{
						EdtGruposComunit.setEnabled(false);					
					}
				}
				
				if (s == SpTransporteUtilizado){
					if (SpTransporteUtilizado.getItemAtPosition(posicao).toString().equals("Outro")){						
						EdtMeioTransp.setEnabled(true);					
					}else{
						EdtMeioTransp.setEnabled(false);					
					}
				}
				
			}

			public void onNothingSelected(AdapterView<?> parent) {
				
			}
			
		});
	}
	
	public void PreencheBairro(String _codBairro){
		Banco bd = null;
		Cursor csr = null;
		bd = new Banco(this);		
		
		//P R E E N C H E   B A I R R O
		try{
			try{				
				bd.open();
				csr = bd.consulta("BAIRROS", new String[]{"*"}, 
								  "COD_RET = "+_codBairro.trim(),null, null, null, null, null);
				csr.moveToFirst();
				Edtbairro.setText(csr.getString(csr.getColumnIndex("COD_RET")).toString().trim()+"-"+csr.getString(csr.getColumnIndex("DESCRICAO")).toString());
				EdtCep.setText(csr.getString(csr.getColumnIndex("CEP")).toString());
			}catch(Exception e){
				Log.i("Método PreencheBairro", e.getMessage());
			}
		}finally{
			if (csr != null){
				csr.close();
			}
			if (bd != null){
				bd.fechaBanco();
			}
		}
		
	}

	public void onClick(View v) {
		if (v == btnSalvar){
			if (validaCampos()==true){
				PreparaInsercao();
			}
		}
		if (v == btnVoltar){
			finish();
		}
		if (v == RbSim){
			EdtNumPessoas.setEnabled(true);
			EdtNomePlano.setEnabled(true);
		}
		if (v == RbNao){
			EdtNumPessoas.setEnabled(false);
			EdtNomePlano.setEnabled(false);
		}
		
	}
	
}//Fim da Classe
