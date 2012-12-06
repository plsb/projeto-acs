/************************************************/
/** Projeto SCS - Sistema de Controle de Saúde **/
/************ Desenvolvido por: *****************/
/********** Lucas de Souza Sales ****************/
/******* Karlos Kelvin Alves dos Santos *********/
/************* Pedro Luis Saraiva ***************/
/************************************************/
package br.com.view;


import br.com.control.Banco;
import br.com.scs.R;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OpcoesVacina extends Activity implements OnClickListener{
	
	public static String _ID;
	
	Banco _bd = new Banco(this);
	
	private Button BtnAgendamento, BtnCartaoVacina, BtnEditarAgendamento, BtnVoltar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.opcoesvacina);
		
		InicializaObjetos();
	}
	
	public void InicializaObjetos(){
		BtnAgendamento = (Button) findViewById(R.opcoesvacina.btnAgendamento);
		BtnCartaoVacina = (Button) findViewById(R.opcoesvacina.btnCartaoVacina);
		BtnEditarAgendamento = (Button) findViewById(R.opcoesvacina.btnEditarAgendamento);
		BtnVoltar = (Button) findViewById(R.opcoesvacina.btnVoltar);
		BtnAgendamento.setOnClickListener(this);
		BtnCartaoVacina.setOnClickListener(this);
		BtnEditarAgendamento.setOnClickListener(this);
		BtnVoltar.setOnClickListener(this);		
	}

	public void onClick(View v) {		
		
		if (v == BtnVoltar){
			finish();
		}
		if (v == BtnAgendamento){
			AtualizaVacina();
		}
		if (v == BtnCartaoVacina){
			VisualizarCartaoVacina();
			
		}
		if (v == BtnEditarAgendamento){
			visualizaVacinasAgendadas();
		}
	
		
	}
	
	public void VisualizarCartaoVacina(){
    	String Hash = "";
    	Cursor c = null;
    	try{
    		_bd.open();
    		c = _bd.consulta("residente", new String[]{"*"}, "_ID = "+String.valueOf(_ID), null, null, null, null, null);
    		c.moveToFirst();    		
    		if (c.getCount() > 0){
    			Hash = c.getString(c.getColumnIndex("HASH")).toString().trim();    	
    		}    		    
    		
    	}catch (Exception e) {
			System.out.println(e.getMessage());
		}    	
    	Intent i = new Intent(this,CartoesVacinacao.class);
    	CartoesVacinacao._Hash = Hash;
    	CartoesVacinacao._CartaoGestante = ( c.getString(c.getColumnIndex("SEXO")).toString().trim().equals("F") ? true : false);
    	startActivity(i);    	
    }
	
	 public void AtualizaVacina(){
	    	Intent i = new Intent(this, OpcoesCartaoVacina.class); 
	    	OpcoesCartaoVacina._ID = Integer.valueOf(_ID.trim());    
		    startActivity(i);
	    }
	
	private void visualizaVacinasAgendadas() {
		Intent i = new Intent(this, Lista_Agendamento_Vacina.class); 
		Lista_Agendamento_Vacina._ID = Integer.valueOf(_ID.trim());    
	    startActivity(i);
	}
	 
	 @Override
	protected void onDestroy() {
	    _ID = null;
		super.onDestroy();
	}

}
