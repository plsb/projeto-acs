package br.com.view;

import java.util.ArrayList;

import br.com.control.Banco;
import br.com.control.ResidenciaAux;
import br.com.control.Sessao;
import br.com.scs.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class TelaResidencia extends Activity {
	
	Spinner  SpUF, SpMunicipio,SpEndereco; //TAB1
	EditText Edtbairro, EdtCep, EdtNumero, EdtSegTerritorial, EdtArea, EdtMicArea; //TAB1
	Spinner  SpTipoCasa, SpDestinoLixo, SpTratamentoAgua, SpDestFezesUrina, SpAbastecimentoAgua; //TAB2	
    Spinner  SpCasoDoente, SpMeiosComunicacao, SpGruposComunitarios, SpTransporteUtilizado; //TAB3
    
    public static int ID = 0;
    
    Banco bd = null;
    Cursor c = null;
    
	
	ArrayList<String> TipoCasa			  = new ArrayList<String>();
	ArrayList<String> DestinoLixo		  = new ArrayList<String>();
	ArrayList<String> TratamentoAgua	  = new ArrayList<String>();
	ArrayList<String> DestFezesUrina	  = new ArrayList<String>();
	ArrayList<String> AbastAgua			  = new ArrayList<String>();
	ArrayList<String> UF				  = new ArrayList<String>();
	ArrayList<String> Municipio			  = new ArrayList<String>();
	ArrayList<String> CasoDoente		  = new ArrayList<String>();
	ArrayList<String> MeiosComunicacao	  = new ArrayList<String>();
	ArrayList<String> GruposComunitarios  = new ArrayList<String>();
	ArrayList<String> TransporteUtilizado = new ArrayList<String>();
	ArrayList<String> Enderecos           = new ArrayList<String>();
	ArrayList<String> Bairros             = new ArrayList<String>();
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.telaresidencia);
		
		//Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		//int largura = display.getWidth();  
        //int altura  = display.getHeight(); 
		
		SpUF                  = (Spinner)  findViewById(R.imovel.SpUF);
		SpMunicipio           = (Spinner)  findViewById(R.imovel.SpMunicipio);
		SpEndereco            = (Spinner)  findViewById(R.imovel.SpEndereco);
		Edtbairro 		      = (EditText) findViewById(R.imovel.edtBairro);
		EdtCep			      = (EditText) findViewById(R.imovel.edtCEP);
		EdtNumero		      = (EditText) findViewById(R.imovel.edtNumero);
		EdtSegTerritorial     = (EditText) findViewById(R.imovel.edtSegTerritorial);
		EdtArea			      = (EditText) findViewById(R.imovel.edtArea);
		EdtMicArea			  = (EditText) findViewById(R.imovel.edtMicroArea);
		SpTipoCasa   	      = (Spinner)  findViewById(R.imovel.SpTipoCasa);
		SpDestinoLixo    	  = (Spinner)  findViewById(R.imovel.SpDestinoLixo);
		SpTratamentoAgua 	  = (Spinner)  findViewById(R.imovel.SpTratamentoAgua);
		SpDestFezesUrina      = (Spinner)  findViewById(R.imovel.SpDestFezesUrina);
		SpAbastecimentoAgua	  = (Spinner)  findViewById(R.imovel.SpAbastecimentoAgua);	
		SpCasoDoente 		  = (Spinner)  findViewById(R.imovel.SpCasoDoente);
	    SpMeiosComunicacao 	  = (Spinner)  findViewById(R.imovel.SpMeioComunicacao);
	    SpGruposComunitarios  = (Spinner)  findViewById(R.imovel.SpGrupoComunitario);
	    SpTransporteUtilizado = (Spinner)  findViewById(R.imovel.SpMeioTransporte);	
		
		TabHost th = (TabHost) findViewById(R.imovel.tabhost);
        th.setup();
        TabSpec ts;
        
        //Primeria Aba
        ts = th.newTabSpec("tag1");
        ts.setContent(R.imovel.tabCadastroResidencia);
        ts.setIndicator("Residencia",getResources().getDrawable(R.drawable.casa));
        setOpcoesSpinnersTab1("","","","");
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
        setOpcoesSpinnerTab3("","","","");
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
						setOpcoesSpinnersTab1(c.getString(c.getColumnIndex("UF")).toString(),
											  c.getString(c.getColumnIndex("MUNICIPIO")).toString(), 
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
										     c.getString(c.getColumnIndex("MEIO_TRANSPORTE")).toString());
					}
					
				}
			}catch(Exception e){
				Log.i("Erro no m�todo PreencheCampos", e.getMessage());
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
		
		if (EdtNumero.getText().toString().length() == 0){
			EdtNumero.setError("Informe o N�mero da Casa!");
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
			EdtArea.setError("Informe o C�digo da �rea!");
			retorno = false;
		}else{
			retorno = true;
		}
		if (EdtMicArea.getText().toString().length() == 0){
			EdtMicArea.setError("Informe o C�digo da �rea!");
			retorno = false;
		}else{
			retorno = true;
		}
		return retorno;
	}
	
	private void setOpcoesSpinnersTab1(String pUF,String pCidade, String pEndereco,String pBairro){
		setOpcoesUF(pUF);
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
	
	private void setOpcoesSpinnerTab3(String pCasoDoente, String pMeiosComunic, String pGrupoComunit,String pTransporteUtilizado){
		setOpcoesCasoDoente(pCasoDoente);
		setOpcoesMeiosComunicacao(pMeiosComunic);
		setOpcoesGruposComunitarios(pGrupoComunit);
		setOpcoesTransporteUtilizado(pTransporteUtilizado);
	}
	
	private void setOpcoesUF(String pUF){
		UF.clear();
		if (pUF.length()>0){
			UF.add(pUF);
		}
		UF.add("CE");		
		PreencheSpinner(SpUF, UF);
	}
	
	private void setOpcoesEnderecos(String pEnd,String pBairro){
		Enderecos.clear();
		Bairros.clear();
		if ((pEnd.length()>0)&&(pBairro.length()>0)){
			Enderecos.add(pEnd);
			Bairros.add(pBairro);
		}
		Banco bd = null;
		Cursor csr = null;
		String codUser = Sessao.SESSAO.getMatriculaUsuario(this);
		try{
			try{
				bd = new Banco(this);
				bd.open();
				csr = bd.consulta("ruas", new String[]{"*"}, "USU_VINCULADO = ? ", new String[] {codUser}, null, null, null, null);
				csr.moveToFirst();
				for (int i = 0;i < csr.getCount(); i++){
					Enderecos.add(csr.getString(csr.getColumnIndex("DESCRICAO")).toString());
					Bairros.add(csr.getString(csr.getColumnIndex("BAIRRO")).toString());
					csr.moveToNext();
				}
				
				PreencheSpinner(SpEndereco, Enderecos);
			}catch(Exception e){
				Log.i("M�todo SetOpcoesEndereco", e.getMessage());
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
		GruposComunitarios.add("Sim");
		GruposComunitarios.add("Nao");		
		PreencheSpinner(SpGruposComunitarios, GruposComunitarios);
	}
	
	private void setOpcoesTransporteUtilizado(String pTranspUtilizado){
		TransporteUtilizado.clear();
		if (pTranspUtilizado.length()>0){
			TransporteUtilizado.add(pTranspUtilizado);
		}
		TransporteUtilizado.add("Selecione");
		TransporteUtilizado.add("Carro Proprio");
		TransporteUtilizado.add("Taxi");
		TransporteUtilizado.add("Onibus");
		TransporteUtilizado.add("Metro");
		TransporteUtilizado.add("Moto");
		TransporteUtilizado.add("Bicicleta");
		TransporteUtilizado.add("Nenhum");		
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
		TipoCasa.add("Taipa N�o Revestida");
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
		r.UF			   = SpUF.getItemAtPosition(SpUF.getSelectedItemPosition()).toString();
		r.ENDERECO		   = SpEndereco.getItemAtPosition(SpEndereco.getSelectedItemPosition()).toString();
		r.NUMERO		   = EdtNumero.getText().toString();
		r.BAIRRO		   = Edtbairro.getText().toString();
		r.CEP			   = EdtCep.getText().toString();
		r.MUNICIPIO		   = SpMunicipio.getItemAtPosition(SpMunicipio.getSelectedItemPosition()).toString();
		r.SEG_TERRIT	   = EdtSegTerritorial.getText().toString();
		r.AREA			   = EdtArea.getText().toString();
		r.MICROAREA		   = EdtMicArea.getText().toString();
		r.COD_FAMILIA	   = "";
		r.DATA_CADASTRO	   = "";
		r.TIPO_CASA		   = SpTipoCasa.getItemAtPosition(SpTipoCasa.getSelectedItemPosition()).toString();
		r.DEST_LIXO		   = SpDestinoLixo.getItemAtPosition(SpDestinoLixo.getSelectedItemPosition()).toString();
		r.TRAT_AGUA 	   = SpTratamentoAgua.getItemAtPosition(SpTratamentoAgua.getSelectedItemPosition()).toString();
		r.ABAST_AGUA 	   = SpAbastecimentoAgua.getItemAtPosition(SpAbastecimentoAgua.getSelectedItemPosition()).toString();
		r.DEST_FEZES	   = SpDestFezesUrina.getItemAtPosition(SpDestFezesUrina.getSelectedItemPosition()).toString();
		r.CASO_DOENCA	   = SpCasoDoente.getItemAtPosition(SpCasoDoente.getSelectedItemPosition()).toString();
		r.MEIO_COMUNICACAO = SpMeiosComunicacao.getItemAtPosition(SpMeiosComunicacao.getSelectedItemPosition()).toString();
		r.PART_GRUPOS	   = SpGruposComunitarios.getItemAtPosition(SpGruposComunitarios.getSelectedItemPosition()).toString();
		r.MEIO_TRANSPORTE  = SpTransporteUtilizado.getItemAtPosition(SpTransporteUtilizado.getSelectedItemPosition()).toString();
		
		if (this.ID == 0){		
			if (r.Inserir(TelaResidencia.this)==true){
				Toast.makeText(this, "Sucesso ao Gravar!", Toast.LENGTH_LONG).show();
				finish();
			}else{
				Toast.makeText(this, "Erro ao Gravar!", Toast.LENGTH_LONG).show();
			}
		}else{
			if (r.Atualizar(TelaResidencia.this, this.ID)==true){
				Toast.makeText(this, "Sucesso ao Atulizar!", Toast.LENGTH_LONG).show();
				ClearID();
				finish();
			}else{
				Toast.makeText(this, "Erro ao Atulizar!", Toast.LENGTH_LONG).show();
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
				//pega nome pela posi��o
				//String nome = parent.getItemAtPosition(posicao).toString();
				//imprime um Toast na tela com o nome que foi selecionado
				//Toast.makeText(this, "Nome Selecionado: " + nome, Toast.LENGTH_LONG).show();
				
				if (s == SpEndereco){
					Edtbairro.setText(Bairros.get(posicao));
				}
				
			}

			public void onNothingSelected(AdapterView<?> parent) {
				
			}
			
		});
	}
	
	
	
}