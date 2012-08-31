package br.com.view;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import br.com.scs.R;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

public class TelaCadastroFamilia extends Activity{
	

	Spinner SpAlfabetizado, SpFreqEscola, SpIdade;
	DatePicker DtNascimento;
	ArrayList<String> Opcao = new ArrayList<String>();
	ArrayList<String> Idade = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telacadastrofamilia);
		
		SpIdade = (Spinner) findViewById(R.cadastrofamilia.SpIdade);
		SpAlfabetizado = (Spinner) findViewById(R.cadastrofamilia.SpAlfabetizado);
		SpFreqEscola = (Spinner) findViewById(R.cadastrofamilia.SpFrequenEsc);
		DtNascimento = (DatePicker) findViewById(R.cadastrofamilia.DpDataNascimento);
		
		OpcaoSpinner();
		SpinnerIdade();
			

}	
	
public void OpcaoSpinner(){
		Opcao.clear();
		Opcao.add("Sim");
		Opcao.add("Não");
		PreencheSpinner(SpAlfabetizado, Opcao);
		PreencheSpinner(SpFreqEscola, Opcao);	
	}

public void SpinnerIdade(){
	  Idade.clear();
	  for (int i = 1; i <=100; i++){
		  Idade.add(String.valueOf(i));
	  }
	  PreencheSpinner(SpIdade, Idade);
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
	if ((_dia >= dia)&&(_mes >= mes)){
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

	


}

