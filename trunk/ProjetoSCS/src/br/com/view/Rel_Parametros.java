package br.com.view;


import br.com.scs.R;
import android.app.Activity;
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
			
		}
		
	}

}
