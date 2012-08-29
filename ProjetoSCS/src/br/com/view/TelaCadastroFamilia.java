package br.com.view;


import java.util.ArrayList;
import java.util.Collection;

import br.com.scs.R;
import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TelaCadastroFamilia extends Activity{
	

	Spinner SpAlfabetizado, SpFreqEscola, SpIdade;
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

