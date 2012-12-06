/************************************************/
/** Projeto SCS - Sistema de Controle de Saúde **/
/************ Desenvolvido por: *****************/
/********** Lucas de Souza Sales ****************/
/******* Karlos Kelvin Alves dos Santos *********/
/************* Pedro Luis Saraiva ***************/
/************************************************/
package br.com.view;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.control.Banco;
import br.com.control.Mensagem;
import br.com.scs.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

public class InsereAgendamento extends Activity implements OnClickListener {
	
	public static int _ID = 0;
	private String Hash = "";
	
	private EditText EdtNome,EdtDescricao, EdtData, EdtHora;
	private Spinner  SpTipoAgendamento, SpProfissional;
	private CheckBox ChkUrgente;
	private Button   BtnVoltar, BtnSalvar;
	
	private Banco _bd;
	
	private java.util.Date data;
	
	private int DATE_DIALOG_ID = 0;
	private int TIME_DIALOG_ID = 1;
	
	ArrayList<String> TpAgendamento = new ArrayList<String>();
	ArrayList<String> Profissional =  new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.telaagendamento);
		
		_bd = new Banco(this);		
		InicializaObjetos();		
		BuscaHash();
		setOpcoesTpAgendamento();
		setOpcoesProfissional();
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
	
	public void setOpcoesProfissional(){
		Cursor c = null;
		try{
		 _bd.open();
		 c = _bd.consulta("profissional", new String[]{"*"}, null , null, null, null, null, null);
		 c.moveToFirst();
		 
		 if (c.getCount() > 0){
			 
			 do {
				 Profissional.add(c.getString(c.getColumnIndex("COD_RET")).toString()+"-"+c.getString(c.getColumnIndex("NOME")).toString());
			 } while (c.moveToNext());
		 }
			
		}catch(Exception e){
			System.out.println("Exceção Agendamento: "+e.getMessage());
		}finally{
			c.close();
		}
		
		PreencheSpinner(SpProfissional, Profissional);
		
		
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
		
		if (v == EdtData){
			showDialog(DATE_DIALOG_ID);
		}
		if (v == EdtHora){
			showDialog(TIME_DIALOG_ID);
		}
		
	}
	
	public void InicializaObjetos(){
		
		EdtNome			  = (EditText) findViewById(R.telaAgendamento.EdtPessoa);
		EdtDescricao 	  = (EditText) findViewById(R.telaAgendamento.EdtDescricao);
		EdtData           = (EditText) findViewById(R.telaAgendamento.EdtData);
		EdtHora           = (EditText) findViewById(R.telaAgendamento.EdtHora);
		SpTipoAgendamento = (Spinner)  findViewById(R.telaAgendamento.SpTipoAgendamento);
		SpProfissional    = (Spinner)  findViewById(R.telaAgendamento.SpProfissional);
		ChkUrgente 		  = (CheckBox) findViewById(R.telaAgendamento.ChkFLUrgente);
		BtnVoltar         = (Button)   findViewById(R.telaAgendamento.btnVoltarTelaAgend);
		BtnSalvar         = (Button)   findViewById(R.telaAgendamento.btnSalvarTelaAgend);
		EdtData.setOnClickListener(this);
		EdtHora.setOnClickListener(this);
		BtnVoltar.setOnClickListener(this);
		BtnSalvar.setOnClickListener(this);
		
	}
	
	public boolean InsereBD(){
		try{
			ContentValues c = new ContentValues();
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
			c.clear();
			c.put("HASH", Hash); 		
			c.put("DT_REGISTRO",formatador.format(new Date(System.currentTimeMillis())));
			c.put("FL_URGENTE",(ChkUrgente.isChecked()?"S":"N"));			
			c.put("TIPO_AGENDAMENTO",SpTipoAgendamento.getItemAtPosition(SpTipoAgendamento.getSelectedItemPosition()).toString());
			c.put("DESCRICAO",EdtDescricao.getText().toString());
			c.put("PROFISSIONAL", SpProfissional.getItemAtPosition(SpProfissional.getSelectedItemPosition()).toString().substring(0,SpProfissional.getItemAtPosition(SpProfissional.getSelectedItemPosition()).toString().indexOf("-")));
			c.put("DT_AGENDAMENTO", EdtData.getText().toString().trim());
			c.put("HR_AGENDAMENTO", EdtHora.getText().toString().trim());
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
	
	@Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendario = Calendar.getInstance();
         
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        
        int hourOfDay = calendario.get(Calendar.HOUR_OF_DAY);
        int minute = calendario.get(Calendar.MINUTE);
        boolean is24HourView = true;
         
        switch (id) {
        case 0:
            return new DatePickerDialog(this, mDateSetListener, ano, mes,
                    dia);
        case 1:
        	return new TimePickerDialog(this, mTimeSetListener, hourOfDay, 
        			minute, is24HourView);
        }
        return null;
    }
	
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        
		public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {
            String data1 = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear+1) + "/" + String.valueOf(year);
            SimpleDateFormat formatador =  new SimpleDateFormat("dd/MM/yyyy");
            try {
            	 data = formatador.parse(data1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());;
			}
            
            EdtData.setText(formatador.format(data));
        }
    };
    
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			String minuto;
			String hora1;
			
			if (hourOfDay <10){
				hora1 = "0" + hourOfDay;
			}else{
				hora1 = String.valueOf(hourOfDay);
			}
			
			if (minute <10){
			    minuto = "0" + minute;
			}else{
				minuto = String.valueOf(minute);
			}

			String hora = hora1 +":"+ minuto;
			EdtHora.setText(hora);
		}
		
		
    };
    
    
    

}
