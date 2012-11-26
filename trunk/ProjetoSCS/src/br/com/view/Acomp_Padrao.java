package br.com.view;

import java.util.ArrayList;

import br.com.control.Banco;
import br.com.scs.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

public class Acomp_Padrao extends Activity implements OnClickListener{
	
	private static int IdTransacao = 0;
	
	Banco _bd = new Banco(this);
	Cursor _cHipertensao = null;
	
	public static String DtAcompanhamento = null;
	public static String Hash = null;
	
	RadioButton rbHospitSIM, rbHospitNAO, rbDoenteSIM, rbDoenteNAO;
	Spinner     SpMotivoHospit, SpDoenca;
	
	ArrayList<String> MotivoHospitalizacao = new ArrayList<String>();
	ArrayList<String> Doencas = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.acomp_padrao);
		
		InicializaObjetos();		
		
	}
	
	public void InicializaObjetos(){
		rbHospitSIM = (RadioButton) findViewById(R.acomppadrao.RbHSim);
		rbHospitNAO = (RadioButton) findViewById(R.acomppadrao.RbHNao);
		rbDoenteSIM = (RadioButton) findViewById(R.acomppadrao.RbDSim);
		rbDoenteNAO = (RadioButton) findViewById(R.acomppadrao.RbDNao);
		SpMotivoHospit = (Spinner) findViewById(R.acomppadrao.SpHospitalizacao);
		SpDoenca       = (Spinner) findViewById(R.acomppadrao.SpDoente);
		rbHospitSIM.setOnClickListener(this);
		rbHospitNAO.setOnClickListener(this);
		rbDoenteSIM.setOnClickListener(this);
		rbDoenteNAO.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		if (v == rbHospitSIM) {
			SpMotivoHospit.setClickable(true);	
			setMotivosHospitalizacao("");
		}
		if (v == rbHospitNAO) {
			SpMotivoHospit.setClickable(false);
			MotivoHospitalizacao.clear();
			MotivoHospitalizacao.add("");
			PreencheSpinner(SpMotivoHospit, MotivoHospitalizacao);
		}
		if (v == rbDoenteSIM) {
			SpDoenca.setClickable(true);
			setDoencas("");
		}
		if (v == rbDoenteNAO) {
			SpDoenca.setClickable(false);
			Doencas.clear();
			Doencas.add("");
			PreencheSpinner(SpDoenca, Doencas);
		}
	}
	
	private void setMotivosHospitalizacao(String pMotivoHospitalizacao){
		MotivoHospitalizacao.clear();
		if (pMotivoHospitalizacao.length()>0) {
			MotivoHospitalizacao.add(pMotivoHospitalizacao);
		}
		MotivoHospitalizacao.add("");
		MotivoHospitalizacao.add("Pneumonia");
		MotivoHospitalizacao.add("Desidratacao");
		MotivoHospitalizacao.add("Excesso de Alcool");
		MotivoHospitalizacao.add("Diabetes");
		MotivoHospitalizacao.add("H. Psiquiatrico");
		MotivoHospitalizacao.add("Dengue");
		MotivoHospitalizacao.add("Febre Alta");
		MotivoHospitalizacao.add("Infeccao");
		MotivoHospitalizacao.add("Outros");
		
		PreencheSpinner(SpMotivoHospit, MotivoHospitalizacao);
	}
	
	private void setDoencas(String pDoenca) {
		Doencas.clear();
		if (pDoenca.length() > 0) {
			Doencas.add(pDoenca);
		}
		Doencas.add("");
		Doencas.add("Dengue");
		Doencas.add("Pneumonia");
		Doencas.add("Febre Alta");
		Doencas.add("Infeccao");
		Doencas.add("Excesso Alcool");
		Doencas.add("Vomito");
		Doencas.add("Outro");
		
		PreencheSpinner(SpDoenca, Doencas);
	}
	
	public void PreencheSpinner(final Spinner s,ArrayList<String> a){
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, a);		
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;		
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		s.setAdapter(spinnerArrayAdapter);		
		
		s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
								
				
			}

			public void onNothingSelected(AdapterView<?> parent) {
				
			}
			
		});
	}
	

}
