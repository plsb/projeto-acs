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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class Acomp_Padrao extends Activity implements OnClickListener{
	
	private static int IdTransacao = 0;
	
	Banco _bd = new Banco(this);
	Cursor _cAcomp = null;
	
	public static String DtAcompanhamento = null;
	public static String Hash = null;
	
	public static RadioButton rbHospitSIM, rbHospitNAO, rbDoenteSIM, rbDoenteNAO;
	public static Spinner     SpMotivoHospit, SpDoenca;
	public static EditText    EdtObs;
	
	ArrayList<String> MotivoHospitalizacao = new ArrayList<String>();
	ArrayList<String> Doencas = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.acomp_padrao);
		
		InicializaObjetos();	
		
		if (DtAcompanhamento != null) {
			PreencheCampos();
		}
		
	}
	
	public void InicializaObjetos(){
		rbHospitSIM = (RadioButton) findViewById(R.acomppadrao.RbHSim);
		rbHospitNAO = (RadioButton) findViewById(R.acomppadrao.RbHNao);
		rbDoenteSIM = (RadioButton) findViewById(R.acomppadrao.RbDSim);
		rbDoenteNAO = (RadioButton) findViewById(R.acomppadrao.RbDNao);
		SpMotivoHospit = (Spinner)  findViewById(R.acomppadrao.SpHospitalizacao);
		SpDoenca       = (Spinner)  findViewById(R.acomppadrao.SpDoente);
		EdtObs         = (EditText) findViewById(R.acomppadrao.EdtObs); 
		rbHospitSIM.setOnClickListener(this);
		rbHospitNAO.setOnClickListener(this);
		rbDoenteSIM.setOnClickListener(this);
		rbDoenteNAO.setOnClickListener(this);
	}
	
	public void PreencheCampos() {
		try{
			_bd.open();
			_cAcomp = _bd.consulta("acompanhamento", new String[]{"*"}, "hash = '"+Hash+"' and dt_visita = '"+DtAcompanhamento+"' ", null, null, null, null, "1");
			_cAcomp.moveToFirst();
			if (_cAcomp.getCount() > 0){
				setIdTransacao(Integer.valueOf(_cAcomp.getString(_cAcomp.getColumnIndex("_ID")).toString()));				
				
				EdtObs.setText(_cAcomp.getString(_cAcomp.getColumnIndex("OBSERVACAO")).toString());
				
				//Foi Hospitalizado
	    		if (_cAcomp.getString(_cAcomp.getColumnIndex("FL_HOSPITALIZADA")).toString().trim().equals("S")) {
	    			rbHospitSIM.setChecked(true);
	    			MotivoHospitalizacao.clear();
	    			MotivoHospitalizacao.add(_cAcomp.getString(_cAcomp.getColumnIndex("MOTIVO_HOSPITALIZACAO")).toString());
	    			PreencheSpinner(SpMotivoHospit, MotivoHospitalizacao);
	    		} else {
	    			rbHospitNAO.setChecked(true);
	    			MotivoHospitalizacao.clear();
	    			MotivoHospitalizacao.add("");
	    			PreencheSpinner(SpMotivoHospit, MotivoHospitalizacao);
	    		}	
	    		
	    		//Está doente
	    		if (_cAcomp.getString(_cAcomp.getColumnIndex("FL_DOENTE")).toString().trim().equals("S")) {
	    			rbDoenteSIM.setChecked(true);
	    			Doencas.clear();
	    			Doencas.add(_cAcomp.getString(_cAcomp.getColumnIndex("DESC_DOENCA")).toString());
	    			PreencheSpinner(SpDoenca, Doencas);
	    		} else {
	    			rbDoenteNAO.setChecked(true);
	    			Doencas.clear();
	    			Doencas.add("");
	    			PreencheSpinner(SpDoenca, Doencas);
	    		}
	    			    		  
			}
			
			
		  }catch(Exception e) {
				System.out.println("Exceção Acomp_Padrão: "+e.getMessage());
		  } finally {
			  _cAcomp.close();
	      }
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
		Doencas.add("Outros");
		
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
	
	public static int getIdTransacao() {
		return IdTransacao;
	}

	public static void setIdTransacao(int idTransacao) {
		IdTransacao = idTransacao;
	}
	
	@Override
	protected void onDestroy() {
		setIdTransacao(0);
		_bd.fechaBanco();
		DtAcompanhamento = null;
		Hash             = null;
		super.onDestroy();
	}
	

}
