package br.com.view;

import java.util.ArrayList;

import br.com.control.ResidenciaAux;
import br.com.scs.R;
import br.com.scs.R.menu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class TelaResidencia extends Activity {
	
	Spinner  SpUF, SpMunicipio; //TAB1
	EditText EdtEndereco, Edtbairro, EdtCep, EdtNumero, EdtSegTerritorial, EdtArea, EdtMicArea; //TAB1
	Spinner  SpTipoCasa, SpDestinoLixo, SpTratamentoAgua, SpDestFezesUrina, SpAbastecimentoAgua; //TAB2	
    Spinner  SpCasoDoente, SpMeiosComunicacao, SpGruposComunitarios, SpTransporteUtilizado; //TAB3
	
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
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.telaresidencia);
		
		//Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		//int largura = display.getWidth();  
        //int altura  = display.getHeight(); 
		
		SpUF                  = (Spinner)  findViewById(R.imovel.SpUF);
		SpMunicipio           = (Spinner)  findViewById(R.imovel.SpMunicipio);
		EdtEndereco           = (EditText) findViewById(R.imovel.edtEndereco);
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
        setOpcoesSpinnersTab1();
        th.addTab(ts);
        
        //Segunda Aba
        ts = th.newTabSpec("tag2");
        ts.setContent(R.imovel.tabMoradiaSaneamento);        
        ts.setIndicator("Moradia/Saneamento",getResources().getDrawable(R.drawable.saneamento));
		setOpcoesSpinnerTab2();
        th.addTab(ts);
        
        //Terceira Aba
        ts = th.newTabSpec("tag3");
        ts.setContent(R.imovel.tabOutras);        
        ts.setIndicator("Outros",getResources().getDrawable(R.drawable.outros));
        setOpcoesSpinnerTab3();
        th.addTab(ts);		
		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.telaresidencia, menu);        
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.menu_gravar:
			PreparaInsercao();
			break;
		}
		return true;
	}
	
	private void setOpcoesSpinnersTab1(){
		setOpcoesUF();
		setOpcoesMunicipios();
	}

	public void setOpcoesSpinnerTab2(){
		setOpcoesTipoCasa();
		setOpcoesDestLixo();	
		setOpcoesTratAgua();
		setOpcoesDestFezes();
		setOpcoesAbastAgua();
	}
	
	private void setOpcoesSpinnerTab3(){
		setOpcoesCasoDoente();
		setOpcoesMeiosComunicacao();
		setOpcoesGruposComunitarios();
		setOpcoesTransporteUtilizado();
	}
	
	private void setOpcoesUF(){
		UF.clear();
		UF.add("CE");
		PreencheSpinner(SpUF, UF);
	}
	
	private void setOpcoesCasoDoente(){
		CasoDoente.clear();
		CasoDoente.add("Selecione");
		CasoDoente.add("Hospital");
		CasoDoente.add("Posto de Saude");
		CasoDoente.add("Auto Medicar");
		CasoDoente.add("Outro");
		PreencheSpinner(SpCasoDoente, CasoDoente);
	}
	
	private void setOpcoesMeiosComunicacao(){
		MeiosComunicacao.clear();
		MeiosComunicacao.add("Selecione");
		MeiosComunicacao.add("Telefone Fixo");
		MeiosComunicacao.add("Celular");
		MeiosComunicacao.add("Internet");
		MeiosComunicacao.add("Outro");
		PreencheSpinner(SpMeiosComunicacao, MeiosComunicacao);
	}
	
	private void setOpcoesGruposComunitarios(){
		GruposComunitarios.clear();
		GruposComunitarios.add("Selecione");
		GruposComunitarios.add("Sim");
		GruposComunitarios.add("Nao");
		PreencheSpinner(SpGruposComunitarios, GruposComunitarios);
	}
	
	private void setOpcoesTransporteUtilizado(){
		TransporteUtilizado.clear();
		TransporteUtilizado.add("Selecione");
		TransporteUtilizado.add("Carro Proprio");
		TransporteUtilizado.add("Taxi");
		TransporteUtilizado.add("Ônibus");
		TransporteUtilizado.add("Metrô");
		TransporteUtilizado.add("Moto");
		TransporteUtilizado.add("Bicicleta");
		TransporteUtilizado.add("Nenhum");
		PreencheSpinner(SpTransporteUtilizado, TransporteUtilizado);
		
	}
	
	private void setOpcoesMunicipios(){
		Municipio.clear();
		Municipio.add("Selecione");
		Municipio.add("2304202-CRATO");
		Municipio.add("2307304-JUAZEIRO DO NORTE");
		PreencheSpinner(SpMunicipio, Municipio);
	}
	
	private void setOpcoesTipoCasa(){
		TipoCasa.clear();
		TipoCasa.add("Selecione");
		TipoCasa.add("Tijolo/Adobe");
		TipoCasa.add("Taipa Revestida");
		TipoCasa.add("Taipa Não Revestida");
		TipoCasa.add("Madeira");
		TipoCasa.add("Material Aproveitado");
		TipoCasa.add("Outro");
		PreencheSpinner(SpTipoCasa, TipoCasa);
	}
	
	private void setOpcoesDestLixo(){
		DestinoLixo.clear();
		DestinoLixo.add("Selecione");
		DestinoLixo.add("Coletado");
		DestinoLixo.add("Queimado/Enterrado");
		DestinoLixo.add("Ceu Aberto");
		PreencheSpinner(SpDestinoLixo, DestinoLixo);
	}
	
	private void setOpcoesTratAgua(){
		TratamentoAgua.clear();
		TratamentoAgua.add("Selecione");
		TratamentoAgua.add("Filtracao");
		TratamentoAgua.add("Fervura");
		TratamentoAgua.add("Cloracao");
		TratamentoAgua.add("Sem Tratamento");
		PreencheSpinner(SpTratamentoAgua, TratamentoAgua);
	}
	
	private void setOpcoesDestFezes(){
		DestFezesUrina.clear();
		DestFezesUrina.add("Selecione");
		DestFezesUrina.add("Sistema de Esgoto");
		DestFezesUrina.add("Fossa");
		DestFezesUrina.add("Ceu Aberto");
		PreencheSpinner(SpDestFezesUrina, DestFezesUrina);
	}
	
	private void setOpcoesAbastAgua(){
		AbastAgua.clear();
		AbastAgua.add("Selecione");
		AbastAgua.add("Rede Publica");
		AbastAgua.add("Poco ou Nascente");
		AbastAgua.add("Outro");
		PreencheSpinner(SpAbastecimentoAgua, AbastAgua);
	}
	
	public void PreparaInsercao(){
		ResidenciaAux r = new ResidenciaAux();
		r.UF			   = SpUF.getItemAtPosition(SpUF.getSelectedItemPosition()).toString();
		r.ENDERECO		   = EdtEndereco.getText().toString();
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
		
		if (r.Inserir(TelaResidencia.this)==true){
			Toast.makeText(this, "Sucesso ao Gravar!", Toast.LENGTH_LONG).show();
			finish();
		}else{
			Toast.makeText(this, "Erro ao Gravar!", Toast.LENGTH_LONG).show();
		}
	}
	
	public void PreencheSpinner(Spinner s,ArrayList<String> a){
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, a);		
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;		
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		s.setAdapter(spinnerArrayAdapter);		
		
		s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
				//pega nome pela posição
				//String nome = parent.getItemAtPosition(posicao).toString();
				//imprime um Toast na tela com o nome que foi selecionado
				//Toast.makeText(this, "Nome Selecionado: " + nome, Toast.LENGTH_LONG).show();
				
			}

			public void onNothingSelected(AdapterView<?> parent) {
				
			}
			
		});
	}
	
	
	
}
