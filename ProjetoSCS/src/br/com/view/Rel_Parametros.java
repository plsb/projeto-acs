package br.com.view;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import br.com.control.Banco;
import br.com.control.Sessao;
import br.com.scs.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class Rel_Parametros extends Activity implements OnClickListener{

	EditText EdtIdadeInicial, EdtIdadeFinal;
	RadioButton RbSexoM, RbSexoF;
	Button BtnVoltar, BtnFiltrar;
	CheckBox ChHanseniase, ChDiabetes, ChHipertensao, ChAlcolismo, ChGestante, ChTuberculose,
			 ChChagas, ChDeficiente, ChMalaria, ChEplepsia;
	
	private boolean FiltroIdade, FiltroSexo = false;
	private String  Sexo = "";
	
	private ArrayList<HashMap<String,String>> lista = new ArrayList<HashMap<String,String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rel_parametros);
		
		InicializaObjetos();
		
	}

	public void InicializaObjetos(){           
		EdtIdadeFinal   = (EditText) findViewById(R.relparametros.EdtIdadeFinal);
		EdtIdadeInicial = (EditText) findViewById(R.relparametros.EdtIdadeInicial);
		RbSexoM         = (RadioButton) findViewById(R.relparametros.RbSexoM);
		RbSexoF         = (RadioButton) findViewById(R.relparametros.RbSexoF);
		ChAlcolismo     = (CheckBox) findViewById(R.relparametros.ChAlcolismo);
		ChChagas        = (CheckBox) findViewById(R.relparametros.ChChagas);
		ChDeficiente    = (CheckBox) findViewById(R.relparametros.ChDeficiencia);
		ChDiabetes      = (CheckBox) findViewById(R.relparametros.ChDiabetes);
		ChGestante      = (CheckBox) findViewById(R.relparametros.ChGestante);
		ChHanseniase    = (CheckBox) findViewById(R.relparametros.ChAlcolismo);
		ChEplepsia      = (CheckBox) findViewById(R.relparametros.ChEpilepsia);
		ChHipertensao   = (CheckBox) findViewById(R.relparametros.ChHipertensao);
		ChMalaria       = (CheckBox) findViewById(R.relparametros.ChMalaria);
		ChTuberculose   = (CheckBox) findViewById(R.relparametros.ChTuberculose);
		BtnFiltrar      = (Button) findViewById(R.relparametros.btnFiltrar);
		BtnVoltar       = (Button) findViewById(R.relparametros.btnVoltar);
		BtnFiltrar.setOnClickListener(this);
		BtnVoltar.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (v == BtnVoltar){
			finish();
		}
		
		if (v == BtnFiltrar){
			VerificaFiltros();
			GeraRelatorio();
		}
		
	}
	
	private void VerificaFiltros() {
		if ((EdtIdadeInicial.getText().toString().trim().length() > 0)&&
			(EdtIdadeFinal.getText().toString().trim().length() > 0)) {
			FiltroIdade = true;
		}
		if ((RbSexoM.isChecked())||(RbSexoF.isChecked())) {
			FiltroSexo = true;
			if (RbSexoM.isChecked()) {
				Sexo = "M";
			} else {
				Sexo = "F";
			}	
		}
	}
	
	private void GeraRelatorio() {
		
		lista.clear();
		
		String Usuario = Sessao.SESSAO.getMatriculaUsuario(this);
		
		Banco banco = new Banco(this);
		banco.open();
		Cursor _c = null;
		HashMap<String,String> item; 
		try {
			
			_c = banco.consulta("residente r join ruas ru on ru.cod_ret = r.cod_endereco", new String[]{"*"},
							  "ru.usu_vinculado = "+Usuario+(FiltroSexo ? " and sexo ='"+Sexo+"'" : "")+
							  (ChHanseniase.isChecked()  ? " and fl_hanseniase = 'S' " : "")+
							  (ChHipertensao.isChecked() ? " and fl_hipertensao = 'S' " : "")+
							  (ChGestante.isChecked()    ? " and fl_gestante = 'S' " : "")+
							  (ChTuberculose.isChecked() ? " and fl_tuberculose = 'S' " : "")+
							  (ChAlcolismo.isChecked()   ? " and fl_alcolismo = 'S' " : "")+
							  (ChChagas.isChecked()      ? " and fl_chagas = 'S' " : "")+
							  (ChDeficiente.isChecked()  ? " and fl_deficiente = 'S' " : "")+
							  (ChMalaria.isChecked()     ? " and fl_malaria = 'S' " : "")+
							  (ChDiabetes.isChecked()    ? " and fl_diabete = 'S' " : "")+
							  (ChEplepsia.isChecked()    ? " and fl_epiletico = 'S' " : ""),							  
							  null, null, null, null, null);
			_c.moveToFirst();
			
			if (_c.getCount() > 0) {
				
				if (FiltroIdade == true) {
					
					do {
						int idade =	CalculaIdade(Integer.valueOf(_c.getString(_c.getColumnIndex("DTNASCIMENTO")).toString().substring(0, _c.getString(_c.getColumnIndex("DTNASCIMENTO")).toString().indexOf("/"))),
								 Integer.valueOf(_c.getString(_c.getColumnIndex("DTNASCIMENTO")).toString().substring(_c.getString(_c.getColumnIndex("DTNASCIMENTO")).toString().indexOf("/")+1,_c.getString(_c.getColumnIndex("DTNASCIMENTO")).toString().lastIndexOf("/")))-1,
								 Integer.valueOf(_c.getString(_c.getColumnIndex("DTNASCIMENTO")).toString().substring(_c.getString(_c.getColumnIndex("DTNASCIMENTO")).toString().lastIndexOf("/")+1)));
						if ((idade >= Integer.valueOf(EdtIdadeInicial.getText().toString()))&&(idade <= Integer.valueOf(EdtIdadeFinal.getText().toString()))) {
							
							item = new HashMap<String,String>();
				        	item.put( "line1", _c.getString(_c.getColumnIndex("_ID")).toString()+"-"+
				        					   _c.getString(_c.getColumnIndex("NOME")).toString());
				        	item.put( "line2", "Sexo: "+_c.getString(_c.getColumnIndex("SEXO")).toString()+" - Dt. Nascimento: "+
				        								_c.getString(_c.getColumnIndex("DTNASCIMENTO")).toString());
					        lista.add( item );
						}		        	
				        
				    } while (_c.moveToNext());
										
				} 
				
			}//Fim if
			
			System.out.println(lista.toString());
			System.out.println(String.valueOf(lista.size()));
			_c.close();
		} catch(Exception e) {
			System.out.println("Erro no método GeraRelatório: "+e.getMessage());
		}
	}
	
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

}
