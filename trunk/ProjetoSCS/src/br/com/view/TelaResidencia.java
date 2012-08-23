package br.com.view;

import java.util.ArrayList;

import br.com.scs.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TelaResidencia extends Activity {
	
	Spinner SpTipoCasa,SpDestinoLixo,SpTratamentoAgua,SpDestFezesUrina,SpAbastecimentoAgua,SpUF,SpMunicipio;
    
	
	ArrayList<String> TipoCasa = new ArrayList<String>();
	ArrayList<String> DestinoLixo = new ArrayList<String>();
	ArrayList<String> TratamentoAgua = new ArrayList<String>();
	ArrayList<String> DestFezesUrina = new ArrayList<String>();
	ArrayList<String> AbastAgua = new ArrayList<String>();
	ArrayList<String> UF = new ArrayList<String>();
	ArrayList<String> Municipio = new ArrayList<String>();
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.telaresidencia);
		
		//Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		//int largura = display.getWidth();  
        //int altura  = display.getHeight(); 
		
		
		TabHost th = (TabHost) findViewById(R.imovel.tabhost);
        th.setup();
        TabSpec ts;
        
        //Primeria Aba
        ts = th.newTabSpec("tag1");
        ts.setContent(R.imovel.tabCadastroResidencia);
        ts.setIndicator("Residencia",getResources().getDrawable(R.drawable.casa));
        SpUF                = (Spinner) findViewById(R.imovel.SpUF);
		SpMunicipio         = (Spinner) findViewById(R.imovel.SpMunicipio);
        setOpcoesSpinnersTab1();
        th.addTab(ts);
        
        //Segunda Aba
        ts = th.newTabSpec("tag2");
        ts.setContent(R.imovel.tabMoradiaSaneamento);        
        ts.setIndicator("Moradia/Saneamento",getResources().getDrawable(R.drawable.saneamento));
        SpTipoCasa   	    = (Spinner) findViewById(R.imovel.SpTipoCasa);
		SpDestinoLixo    	= (Spinner) findViewById(R.imovel.SpDestinoLixo);
		SpTratamentoAgua 	= (Spinner) findViewById(R.imovel.SpTratamentoAgua);
		SpDestFezesUrina    = (Spinner) findViewById(R.imovel.SpDestFezesUrina);
		SpAbastecimentoAgua = (Spinner) findViewById(R.imovel.SpAbastecimentoAgua);	
		setOpcoesNosSpinners();	
        th.addTab(ts);
        
        //Terceira Aba
        ts = th.newTabSpec("tag3");
        ts.setContent(R.id.tab3);
        ts.setIndicator("Outros",getResources().getDrawable(R.drawable.outros));
        th.addTab(ts);		
		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.telaresidencia, menu);
        return true;
    }
	
	private void setOpcoesSpinnersTab1(){
		setOpcoesUF();
		setOpcoesMunicipios();
	}
	
	private void setOpcoesUF(){
		UF.clear();
		UF.add("CE");
		PreencheSpinner(SpUF, UF);
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
		TipoCasa.add("Taipa N�o Revestida");
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
	
	public void setOpcoesNosSpinners(){
		setOpcoesTipoCasa();
		setOpcoesDestLixo();	
		setOpcoesTratAgua();
		setOpcoesDestFezes();
		setOpcoesAbastAgua();
	}
	
	public void PreencheSpinner(Spinner s,ArrayList<String> a){
		
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
				
			}

			public void onNothingSelected(AdapterView<?> parent) {
				
			}
			
		});
	}
	
	
	
}
