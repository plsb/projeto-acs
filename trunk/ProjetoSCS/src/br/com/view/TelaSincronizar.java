package br.com.view;

import br.com.control.ExportarXML;
import br.com.control.ImportarXML;
import br.com.control.Mensagem;
import br.com.scs.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TelaSincronizar extends Activity implements OnClickListener{
	
	  private Button  btnImportarXmls,btnVisulizarUsuarios,btnVoltar,btnExportarVisitas,btnConfigWS;
	
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        setContentView(R.layout.telasincronizar);        	        
	        
	        InicializaObjetos();	
	  }

	public void onClick(View v) {
		
		if (v==btnImportarXmls){				
			Intent i = new Intent(this, ImportarXML.class);
			startActivity(i);			
		}
				
		
		if (v==btnVisulizarUsuarios){
			Intent i = new Intent(this, Lista_Usuarios.class);
			startActivity(i);
		}
		
		if (v == btnVoltar){
			finish();
		}
		
		if (v == btnExportarVisitas){
			Intent i = new Intent(this, ExportarXML.class);
			ExportarXML.Residencias = true;
			startActivity(i);			
		}
		
		if (v == btnConfigWS){
			Mensagem.exibeMessagem(this, "Web Service", "Função em desenvolvimento");
		}
	}//Fim do Onclick  
	
	public void InicializaObjetos(){
		btnImportarXmls      = (Button) findViewById(R.telaSincrozinar.btnImportarXmls);
		btnVisulizarUsuarios = (Button) findViewById(R.telaSincrozinar.btnVisualizarUsuarios);
		btnVoltar            = (Button) findViewById(R.telaSincrozinar.btnVoltar);
		btnExportarVisitas   = (Button) findViewById(R.telaSincrozinar.btnExportarXML);
		btnConfigWS			 = (Button) findViewById(R.telaSincrozinar.btnConfigWS);
		btnImportarXmls.setOnClickListener(this);	
		btnVisulizarUsuarios.setOnClickListener(this);		
		btnVoltar.setOnClickListener(this);		
		btnExportarVisitas.setOnClickListener(this);
		btnConfigWS.setOnClickListener(this);
	}


}
