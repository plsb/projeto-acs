package br.com.view;

import br.com.control.Banco;
import br.com.control.Mensagem;
import br.com.scs.R;
import android.app.ActivityGroup;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;

public class ControleDoencas  extends ActivityGroup implements OnClickListener {
	
	static TabHost th;
	static int tab = -1;
	static String _Hash = "";
	static boolean editando  = false;
	static String  dataAcomp = null;
	static boolean gestante,hipertensao, hanseniase,tuberculose, diabetes = false;
	
	private Button btnVoltar, btnSalvar;
	
	Banco _bd = new Banco(this);
	Cursor _doencas = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.controle_doencas);
		
		btnVoltar = (Button) findViewById(R.controledoencas.btnVoltar);
		btnSalvar = (Button) findViewById(R.controledoencas.btnSalvar);
		btnVoltar.setOnClickListener(this);
		btnSalvar.setOnClickListener(this);
		
		if (!editando){
			try {			
				dataAcomp = null;
				_bd.open();
				_doencas = _bd.consulta("residente", new String[]{"*"}, "hash = '"+_Hash+"'", null, null, null, null, "1");
				_doencas.moveToFirst();
				if (_doencas.getCount() > 0){
					gestante = (_doencas.getString(_doencas.getColumnIndex("FL_GESTANTE")).toString().trim().equals("S"));
				}
			} catch(Exception e) {
				System.out.println("Exceção: "+e.getMessage());
			} finally {
				_doencas.close();
			}
		} 
		
		th = (TabHost) findViewById(R.controledoencas.tabhost);
		th.setup(this.getLocalActivityManager());
		TabHost.TabSpec spec;
		Intent intent;  
		
		if (gestante == true){
			intent = new Intent().setClass(this, Acomp_Gestante.class);
			Acomp_Gestante.Hash = _Hash;
			Acomp_Gestante.DtAcompanhamento = dataAcomp;
			spec = th.newTabSpec("0").setIndicator("Gestante", getResources().getDrawable(R.drawable.gravida)).setContent(intent);        
	        th.addTab(spec);
		}
		
	}	

	public void onClick(View v) {	
		if (v == btnVoltar) {
			finish();
		}
		
		if (v == btnSalvar) {
			Mensagem.exibeMessagem(this, "", Acomp_Gestante.EdtDtPreNatal.getText().toString());
		}
	}

	@Override
	protected void onDestroy() {
		_Hash = "";
		dataAcomp = null;
		editando  = false;
		super.onDestroy();
	}
}
