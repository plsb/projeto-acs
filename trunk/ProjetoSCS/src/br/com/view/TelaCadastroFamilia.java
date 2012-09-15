package br.com.view;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.com.control.Banco;
import br.com.control.ResidenteAux;
import br.com.control.Sessao;
import br.com.scs.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


public class TelaCadastroFamilia extends Activity{
	public static int hanseniase, hipertensao, diabetes, tuberculose, gestante;
	 

	Spinner     SpAlfabetizado, SpFreqEscola, SpRua, SpNumero;
	DatePicker  DtNascimento;
	CheckBox    Hanseniase, Hipertensao, Diabetes, Tuberculose, Gestante, Alcolismo, Chagas, Deficiencia, Malaria, Epilepsia;
	EditText    EdtNome, EdtOcupacao; 
	RadioGroup  RdSexo;
	RadioButton RdbMasculino, RdbFeminino;
	
	ArrayList<String> Opcao = new ArrayList<String>();
	ArrayList<String> Idade = new ArrayList<String>();
	ArrayList<String> Ruas  = new ArrayList<String>();
	ArrayList<String> Num   = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telacadastrofamilia);		
	
		SpAlfabetizado = (Spinner)    findViewById(R.cadastrofamilia.SpAlfabetizado);
		SpFreqEscola   = (Spinner)    findViewById(R.cadastrofamilia.SpFrequenEsc);
		SpRua          = (Spinner)    findViewById(R.cadastrofamilia.SpRua);
		SpNumero 	   = (Spinner)    findViewById(R.cadastrofamilia.SpNumero);
		DtNascimento   = (DatePicker) findViewById(R.cadastrofamilia.DpDataNascimento);
		Hanseniase     = (CheckBox)   findViewById(R.cadastrofamilia.ChHanseniase);
		Hipertensao    = (CheckBox)   findViewById(R.cadastrofamilia.ChHipertensao);
		Diabetes       = (CheckBox)   findViewById(R.cadastrofamilia.ChDiabetes);
		Tuberculose    = (CheckBox)   findViewById(R.cadastrofamilia.ChTuberculose);
		Gestante       = (CheckBox)   findViewById(R.cadastrofamilia.ChGestante);
		Alcolismo 	   = (CheckBox)   findViewById(R.cadastrofamilia.ChAlcolismo);
		Chagas		   = (CheckBox)   findViewById(R.cadastrofamilia.ChChagas);
		Deficiencia    = (CheckBox)   findViewById(R.cadastrofamilia.ChDeficiencia);
		Malaria        = (CheckBox)   findViewById(R.cadastrofamilia.ChMalaria);
		Epilepsia      = (CheckBox)   findViewById(R.cadastrofamilia.ChEpilepsia);
		EdtNome        = (EditText)   findViewById(R.cadastrofamilia.EdtNome);
		EdtOcupacao    = (EditText)   findViewById(R.cadastrofamilia.EdtOcupacao);
		RdSexo         = (RadioGroup) findViewById(R.cadastrofamilia.RgSexo);
		RdbMasculino   = (RadioButton)findViewById(R.cadastrofamilia.RbMasculino);
		RdbFeminino    = (RadioButton)findViewById(R.cadastrofamilia.RbFeminino);
		
		OpcaoSpinner();
		
		setOpcoesEnderecos("");		

}	

@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastrofamilia, menu);        
        return true;
    }
	
	@SuppressLint({ "ParserError", "ParserError" })
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.MenuTelaFamilia.menu_continuar: 
			
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
			
			Intent teladoenca = new Intent(this, TelaDoenca.class);
			startActivity(teladoenca);
			
			break;
			
		case R.MenuTelaFamilia.menu_gravar :{
			
			InsereBD();
				//Toast.makeText(this, "Ação Salvar", Toast.LENGTH_LONG).show();
			}
		}
		return true;
	}
	
public void OpcaoSpinner(){
		Opcao.clear();
		Opcao.add("Sim");
		Opcao.add("Nao");
		PreencheSpinner(SpAlfabetizado, Opcao);
		PreencheSpinner(SpFreqEscola, Opcao);	
	}

private void setOpcoesEnderecos(String pEnd){
	Ruas.clear();
	if (pEnd.length()>0){
		Ruas.add(pEnd);
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
			if (csr.getCount()>0){
				for (int i = 0;i < csr.getCount(); i++){
					Ruas.add(csr.getString(csr.getColumnIndex("DESCRICAO")).toString());
					csr.moveToNext();
				}
			}			
			PreencheSpinner(SpRua, Ruas);
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

private void setOpcoesNumeros(String pNumero){
	Num.clear();
	if (pNumero.length()>0){
		Num.add(pNumero);
	}
	Banco bd = null;
	Cursor csr = null;	
	if (SpRua.getItemAtPosition(SpRua.getSelectedItemPosition()).toString().trim().length() > 0){
		try{
			try{
				bd = new Banco(this);
				bd.open();
				csr = bd.consulta("residencia", new String[]{"*"}, "ENDERECO = '"+SpRua.getItemAtPosition(SpRua.getSelectedItemPosition()).toString()+"' ", null, null, null, null, null);
				csr.moveToFirst();
				if (csr.getCount() > 0){
					for (int i = 0;i < csr.getCount(); i++){
						Num.add(csr.getString(csr.getColumnIndex("NUMERO")).toString());
						csr.moveToNext();
					}
				}
				PreencheSpinner(SpNumero, Num);
			}catch(Exception e){
				Log.i("Método SetOpcoesNumeros", e.getMessage());
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


public void PreencheSpinner(final Spinner s,ArrayList<String> a){
	
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
			if (s == SpRua){
				setOpcoesNumeros("");
			}
		} 

		public void onNothingSelected(AdapterView<?> parent) {
			
		}
		
	});
}


public void InsereBD(){
	try{
		ResidenteAux r = new ResidenteAux();
		r.Limpar();
		r.NOME         = EdtNome.getText().toString();
		r.ENDERECO     = SpRua.getItemAtPosition(SpRua.getSelectedItemPosition()).toString();
		r.NUMERO       = SpNumero.getItemAtPosition(SpNumero.getSelectedItemPosition()).toString();
		r.DTNASCIMENTO = String.valueOf(DtNascimento.getDayOfMonth())+"/"+String.valueOf(DtNascimento.getMonth()+1)+"/"+String.valueOf(DtNascimento.getYear());
		r.FREQ_ESCOLA  = SpFreqEscola.getItemAtPosition(SpFreqEscola.getSelectedItemPosition()).toString();
		r.ALFABETIZADO = SpAlfabetizado.getItemAtPosition(SpAlfabetizado.getSelectedItemPosition()).toString();
		r.OCUPACAO     = EdtOcupacao.getText().toString();
		if (Hanseniase.isChecked())
			r.FL_HANSENIASE = "Sim";
		else
			r.FL_HANSENIASE = "Nao";
		if (Hipertensao.isChecked())
			r.FL_HIPERTENSAO = "Sim";
		else
			r.FL_HIPERTENSAO = "Nao";
		if (Gestante.isChecked())
			r.FL_GESTANTE = "Sim";
		else
			r.FL_GESTANTE = "Nao";
		if (Tuberculose.isChecked())
			r.FL_TURBECULOSE = "Sim";
		else
			r.FL_TURBECULOSE = "Nao";
		if (Alcolismo.isChecked())
			r.FL_ALCOLISMO = "Sim";
		else
			r.FL_ALCOLISMO = "Nao";
		if (Chagas.isChecked())
			r.FL_CHAGAS = "Sim";
		else	
			r.FL_CHAGAS = "Nao";
		if (Deficiencia.isChecked())
			r.FL_DEFICIENTE = "Sim";
		else	
			r.FL_DEFICIENTE = "Nao";
		if (Malaria.isChecked())
			r.FL_MALARIA = "Sim";
		else	
			r.FL_MALARIA = "Nao";
		if (Diabetes.isChecked())
			r.FL_DIABETE = "Sim";
		else
			r.FL_DIABETE = "Nao";
		if (Epilepsia.isChecked())
			r.FL_EPILETICO = "Sim";
		else
			r.FL_EPILETICO = "Nao";				
		if (RdbMasculino.isChecked())
			r.SEXO = "M";
		else if (RdbFeminino.isChecked())
			r.SEXO = "F";	
		
		if (r.Inserir(TelaCadastroFamilia.this)==true){
			Toast.makeText(this, "Sucesso ao Gravar!", Toast.LENGTH_LONG).show();
			finish();
		}
	
	}catch(Exception e){
		Toast.makeText(this, "Erro no método: Prepara Inserção"+e.getMessage(), Toast.LENGTH_LONG).show();
		Log.i("Erro no método: Prepara Inserção", e.getMessage());
	}
}

	


}

