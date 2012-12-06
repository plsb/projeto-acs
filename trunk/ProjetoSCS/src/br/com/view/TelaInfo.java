/************************************************/
/** Projeto SCS - Sistema de Controle de Sa�de **/
/************ Desenvolvido por: *****************/
/********** Lucas de Souza Sales ****************/
/******* Karlos Kelvin Alves dos Santos *********/
/************* Pedro Luis Saraiva ***************/
/************************************************/
package br.com.view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import br.com.control.Sessao;
import br.com.scs.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;

public class TelaInfo extends Activity implements OnClickListener {
	
	private static TextView tvIMEI, tvResponsavel, tvUsuario, tvDataAtual;
	private static Button   btnVoltar, btnCasasFechadas, btnRelatorios;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telainfo);
		
		tvDataAtual      = (TextView) findViewById(R.TelaInfo.dataInfo);
		tvIMEI           = (TextView) findViewById(R.TelaInfo.imeiCelular);
		tvResponsavel    = (TextView) findViewById(R.TelaInfo.respCelular);
		tvUsuario        = (TextView) findViewById(R.TelaInfo.numeUsuInfo);
		btnVoltar        = (Button) findViewById(R.TelaInfo.btnVoltarInfo);
		btnCasasFechadas = (Button) findViewById(R.TelaInfo.btnCasaFechada);
		btnRelatorios    = (Button) findViewById(R.TelaInfo.btnRelatorios);
		btnRelatorios.setOnClickListener(this);
		btnVoltar.setOnClickListener(this);
		btnCasasFechadas.setOnClickListener(this);
		
		InfoResponsavelDispo();
	}	

	public void onClick(View v) {
		
		if (v == btnVoltar){
			finish();
		}
		if (v == btnCasasFechadas) {
			Intent i = new Intent(this, Lista_Casas_Fechadas.class);
			startActivity(i);
		}
		if (v == btnRelatorios){
			Intent i = new Intent(this, Rel_Parametros.class);
			startActivity(i);
		}
		
	}
	
	private void InfoResponsavelDispo(){
		
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		tvIMEI.setText(telephonyManager.getDeviceId());
		
		
		
		tvResponsavel.setText(Sessao.SESSAO.getNomeUsuario(this));
		tvUsuario.setText(Sessao.SESSAO.getMatriculaUsuario(this)+" - "+Sessao.SESSAO.getNomeUsuario(this));
		
		String dataAtual;
		SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
    	dataAtual = data.format(new Date(System.currentTimeMillis()));;
    	tvDataAtual.setText(dataAtual);		
		
	}

}
