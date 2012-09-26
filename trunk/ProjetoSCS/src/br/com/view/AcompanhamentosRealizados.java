package br.com.view;

import br.com.scs.R;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AcompanhamentosRealizados extends ListActivity implements OnClickListener {
	
	private Button   btnFiltrar,btnVoltar;
	private EditText edtFiltro;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acompanhamentosrealizados);
		
		edtFiltro  = (EditText) findViewById(R.id.edtFiltroAcompRealizados);
        btnFiltrar = (Button) findViewById(R.id.btnFiltrarAcompRealizados);
        btnVoltar  = (Button) findViewById(R.id.btnVoltarAcompRealizados);
        btnFiltrar.setOnClickListener(this);          
        btnVoltar.setOnClickListener(this);
	}

	public void onClick(View v) {
		
		if (v == btnVoltar){
			finish();
		}else
		
		if (v == btnFiltrar){
			//
		}
		
		
	}//Fim do Método Onclick
}
