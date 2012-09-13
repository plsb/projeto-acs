package br.com.view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import br.com.control.Sessao;
import br.com.scs.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;

public class TelaInfo extends Activity implements OnClickListener {
	
	private static TextView tvIMEI, tvResponsavel, tvUsuario, tvDataAtual;
	private static Button   btnVoltar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telainfo);
		
		tvDataAtual   = (TextView) findViewById(R.TelaInfo.dataInfo);
		tvIMEI        = (TextView) findViewById(R.TelaInfo.imeiCelular);
		tvResponsavel = (TextView) findViewById(R.TelaInfo.respCelular);
		tvUsuario     = (TextView) findViewById(R.TelaInfo.numeUsuInfo);
		btnVoltar     = (Button) findViewById(R.TelaInfo.btnVoltarInfo);
		btnVoltar.setOnClickListener(this);
		
		InfoResponsavelDispo();
	}	

	public void onClick(View v) {
		
		if (v==btnVoltar){
			finish();
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
