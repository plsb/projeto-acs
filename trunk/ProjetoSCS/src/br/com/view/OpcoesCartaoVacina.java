package br.com.view;

import br.com.scs.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OpcoesCartaoVacina extends Activity implements OnClickListener {
	
	private Button btnCartaoCrianca, btnCartaoAdolescente, btnCartaoAdulto, btnCartaoGestante, btnVoltar; 
	
	public static int _ID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opcoescartaovacina);
		InicializaObjetos();
	}
	
	public void InicializaObjetos(){
		btnCartaoCrianca     = (Button) findViewById(R.opcaocartaovacina.btnCrianca);
		btnCartaoAdolescente = (Button) findViewById(R.opcaocartaovacina.btnAdolescente);		
		btnCartaoAdulto      = (Button) findViewById(R.opcaocartaovacina.btnAdulto);
		btnCartaoGestante    = (Button) findViewById(R.opcaocartaovacina.btnGestante);
		btnVoltar            = (Button) findViewById(R.opcaocartaovacina.btnVoltar);
		btnCartaoCrianca.setOnClickListener(this);
		btnCartaoAdolescente.setOnClickListener(this);
		btnCartaoAdulto.setOnClickListener(this);
		btnCartaoGestante.setOnClickListener(this);
		btnVoltar.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		Intent i = null;
		
		if (v == btnVoltar){
			finish();
		}else if (v == btnCartaoCrianca){
			i = new Intent(this,Acompanhamento_Vacinas.class);
			Acompanhamento_Vacinas._ID = _ID;
			Acompanhamento_Vacinas.IdadeFamiliar = 8;
		}else if (v == btnCartaoAdolescente){
			i = new Intent(this,Acompanhamento_Vacinas.class);
			Acompanhamento_Vacinas._ID = _ID;
			Acompanhamento_Vacinas.IdadeFamiliar = 16;
		}else if (v == btnCartaoAdulto){
			i = new Intent(this,Acompanhamento_Vacinas.class);
			Acompanhamento_Vacinas._ID = _ID;
			Acompanhamento_Vacinas.IdadeFamiliar = 25;
		}else if (v == btnCartaoGestante){
			i = new Intent(this,Acompanhamento_Vacinas.class);
			Acompanhamento_Vacinas._ID = _ID;
			Acompanhamento_Vacinas.FalimiarGestante = true;
		}
		if (i != null){
			startActivity(i);
		}
		
	}
	
	@Override
	protected void onDestroy() {
		_ID = 0;
		super.onDestroy();
	}

}
