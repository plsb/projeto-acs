package br.com.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.PublicKey;
import java.util.Iterator;

import org.jdom2.JDOMException;
import org.ksoap2.serialization.SoapPrimitive;
import org.xmlpull.v1.XmlPullParserException;

import br.com.control.Sessao;
import br.com.control.WSCliente;
import br.com.control.CarregarXML;
import br.com.scs.R;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent.OnFinished;
import android.content.Intent;
import org.jdom2.Element;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TelaPrincipal extends Activity implements OnClickListener{

	private Button btnAgendamento, btnFamilias, btnAcompanhamento,
				   btnUsuarios, btnSincronizar, btnSobre;
	TextView tv1;
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
        
        //VerificaWebService();   
        
    }
    
    public void VerificaWebService(){
    	
    	WSCliente ws = new WSCliente("estaConectado","http://webservice.scs/");
        
    	try {
    		boolean conectado;
    		
    		if (ws.Execute()==true){
    			SoapPrimitive retorno = ws.getRetorno();
    			conectado = Boolean.valueOf(retorno.toString());
    		}else{
    			conectado = false;
    		}   		
			
			if (conectado==true){									
				Log.i(WINDOW_SERVICE,"WebService Ativo");
			}else{
				btnSincronizar.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.semsincronia), null, null);
				Log.i(WINDOW_SERVICE,"WebService Inativo");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.menu_settings:
			finish();
			break;
		}
		return true;
	}

    @SuppressLint("ParserError")
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
			Intent i = new Intent(this, TelaInfo.class);
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