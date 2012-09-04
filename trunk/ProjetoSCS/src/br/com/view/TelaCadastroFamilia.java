package br.com.view;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import br.com.scs.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;


public class TelaCadastroFamilia extends Activity{
	public static int hanseniase, hipertensao, diabetes, tuberculose, gestante;
	 

	Spinner SpAlfabetizado, SpFreqEscola, SpIdade;
	DatePicker DtNascimento;
	CheckBox Hanseniase, Hipertensao, Diabetes, Tuberculose, Gestante;
	ArrayList<String> Opcao = new ArrayList<String>();
	ArrayList<String> Idade = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telacadastrofamilia);		
	
		SpAlfabetizado = (Spinner) findViewById(R.cadastrofamilia.SpAlfabetizado);
		SpFreqEscola = (Spinner) findViewById(R.cadastrofamilia.SpFrequenEsc);
		DtNascimento = (DatePicker) findViewById(R.cadastrofamilia.DpDataNascimento);
		Hanseniase = (CheckBox) findViewById(R.cadastrofamilia.ChHanseniase);
		Hipertensao = (CheckBox) findViewById(R.cadastrofamilia.ChHipertensao);
		Diabetes = (CheckBox) findViewById(R.cadastrofamilia.ChDiabetes);
		Tuberculose = (CheckBox) findViewById(R.cadastrofamilia.ChTuberculose);
		Gestante = (CheckBox) findViewById(R.cadastrofamilia.ChGestante);
		
		OpcaoSpinner();
		

}	

@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastrofamilia, menu);        
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.menu_continuar:
			
			Intent teladoenca = new Intent(this, br.com.scs.R.teladoenca.class);
			
			if(Hanseniase.isChecked()){
				hanseniase = 1;
			}else{
				hanseniase = 0;
			}	
			if (Hipertensao.isChecked()){
				hipertensao = 1;
			}else{
				hipertensao = 0;
			}
			if (Tuberculose.isChecked()){
				tuberculose = 1;
			}else
				tuberculose = 0;
			if (Gestante.isChecked()){
				gestante = 1;
			}else{
				gestante = 0;
			}
			if (Diabetes.isChecked()){
				diabetes = 1;
			}else{
				diabetes = 0;
			}
			
			startActivity(teladoenca);
			
			break;
		}
		return true;
	}
	
public void OpcaoSpinner(){
		Opcao.clear();
		Opcao.add("Sim");
		Opcao.add("Não");
		PreencheSpinner(SpAlfabetizado, Opcao);
		PreencheSpinner(SpFreqEscola, Opcao);	
	}




@SuppressLint("ParserError")
public int CalculaIdade(int _dia, int _mes, int _ano){
	SimpleDateFormat f;	
	f = new SimpleDateFormat("dd");
	int dia = Integer.valueOf(f.format(new Date(System.currentTimeMillis())));
	f = new SimpleDateFormat("MM");
	int mes = Integer.valueOf(f.format(new Date(System.currentTimeMillis())));
	f = new SimpleDateFormat("yyyy");
	int ano = Integer.valueOf(f.format(new Date(System.currentTimeMillis())));	
	if ((_dia <= dia)&&(_mes <= mes)){
		return (ano - _ano);
	}else{
		return ((ano - _ano) -1);
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

public void PreparaInsercao(){
	
}

	


}

