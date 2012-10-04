package br.com.view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.com.control.Banco;
import br.com.control.Mensagem;
import br.com.scs.R;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class InsereAgendamento extends Activity implements OnClickListener {
	
	public static int _ID = 0;
	private String Hash = "";
	
	private EditText EdtNome,EdtDescricao;
	private Spinner  SpTipoAgendamento;
	private CheckBox ChkUrgente;
	private Button   BtnVoltar, BtnSalvar;
	
	private Banco _bd;
	
	ArrayList<String> TpAgendamento = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.telaagendamento);
		
		_bd = new Banco(this);		
		InicializaObjetos();		
		BuscaHash();
		setOpcoesTpAgendamento();
	}
	
	public void BuscaHash(){
		try{
			_bd.open();
			Cursor c = _bd.consulta("residente", new String[]{"_ID,HASH,NOME"}, "_ID = "+String.valueOf(_ID), null, null, null, null, null);
			c.moveToFirst();
			if (c.getCount() > 0){
				Hash = c.getString(c.getColumnIndex("HASH")).toString().trim();
				EdtNome.setText(c.getString(c.getColumnIndex("NOME")).toString().trim());		
			}
			c.close();
			_bd.fechaBanco();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public void setOpcoesTpAgendamento(){
		TpAgendamento.clear();
		TpAgendamento.add("CONSULTA");
		TpAgendamento.add("EXAME");
		PreencheSpinner(SpTipoAgendamento, TpAgendamento);
	}
	
	public void onClick(View v) {
		if (v == BtnVoltar){
			finish();
		}else if (v == BtnSalvar){
			if (InsereBD()==true){
				Mensagem.exibeMessagem(this, "SCS", "Sucesso ao Gravar!", 2000);
				new Handler().postDelayed(new Runnable() {		
					public void run() {
						finish();
					}
				}, 2000);
			}else{
				Mensagem.exibeMessagem(this, "SCS", "Erro ao Gravar!");
			}
		}
		
	}
	
	public void InicializaObjetos(){
		
		EdtNome			  = (EditText) findViewById(R.telaAgendamento.EdtPessoa);
		EdtDescricao 	  = (EditText) findViewById(R.telaAgendamento.EdtDescricao);
		SpTipoAgendamento = (Spinner)  findViewById(R.telaAgendamento.SpTipoAgendamento);
		ChkUrgente 		  = (CheckBox) findViewById(R.telaAgendamento.ChkFLUrgente);
		BtnVoltar         = (Button)   findViewById(R.telaAgendamento.btnVoltarTelaAgend);
		BtnSalvar         = (Button)   findViewById(R.telaAgendamento.btnSalvarTelaAgend);
		BtnVoltar.setOnClickListener(this);
		BtnSalvar.setOnClickListener(this);
		
	}
	
	public boolean InsereBD(){
		try{
			ContentValues c = new ContentValues();
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
			c.clear();
			c.put("HASH", Hash); 		
			c.put("DT_AGENDAMENTO",formatador.format(new Date(System.currentTimeMillis())));
			c.put("FL_URGENTE",(ChkUrgente.isChecked()?"S":"N"));			
			c.put("TIPO_AGENDAMENTO",SpTipoAgendamento.getItemAtPosition(SpTipoAgendamento.getSelectedItemPosition()).toString());
			c.put("DESCRICAO",EdtDescricao.getText().toString());						
			_bd.open();
			_bd.inserirRegistro("agendamento", c);
			_bd.fechaBanco();			
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	@Override
	protected void onDestroy() {
		Hash = "";
		_ID = 0;
		super.onDestroy();
	}
	
	public void PreencheSpinner(final Spinner s,ArrayList<String> a){
	
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, a);		
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;		
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		s.setAdapter(spinnerArrayAdapter);		
		
		s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
								
				
			}

			public void onNothingSelected(AdapterView<?> parent) {
				
			}
			
		});
	}

}
