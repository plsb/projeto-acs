package br.com.view;

import java.security.PublicKey;

import br.com.scs.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent.OnFinished;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TelaPrincipal extends Activity implements OnClickListener{

	private Button btnAgendamento, btnFamilias, btnAcompanhamento,
				   btnUsuarios, btnSincronizar, btnSobre;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.telaprinc);
        
        
        btnAgendamento = (Button) findViewById(R.id.BtnAgendamento); 
        btnAgendamento.setOnClickListener(this);
        btnFamilias = (Button) findViewById(R.id.BtnFamilias);
        btnFamilias.setOnClickListener(this);
        btnAcompanhamento = (Button) findViewById(R.id.BtnAcompanhamento);
        btnAcompanhamento.setOnClickListener(this);
        btnUsuarios = (Button) findViewById(R.id.BtnUsuarios);
        btnUsuarios.setOnClickListener(this);
        btnSincronizar = (Button) findViewById(R.id.BtnSincronizar);
        btnSincronizar.setOnClickListener(this);
        btnSobre = (Button) findViewById(R.id.BtnSobre);
        btnSobre.setOnClickListener(this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void onClick(View v) {
		
		if (v == btnAgendamento){
			Intent i = new Intent(this, TelaAgendamento.class);
			startActivity(i);
		}
		else if (v == btnFamilias){
			Intent i = new Intent(this, TelaFamilia.class);
			startActivity(i);
		}
		else if (v == btnAcompanhamento){
			Intent i = new Intent(this, TelaAcompanhamento.class);
			startActivity(i);
		}
		else if (v == btnUsuarios){
			Intent i = new Intent(this, TelaUsuario.class);
			startActivity(i);
		}
		else if (v == btnSincronizar){
			Intent i = new Intent(this, TelaSincronizar.class);
			startActivity(i);
		}
		else if (v == btnSobre){
			Intent i = new Intent(this, TelaSobre.class);
			startActivity(i);
		}
		
		
	}

    
}
