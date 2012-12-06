/************************************************/
/** Projeto SCS - Sistema de Controle de Saúde **/
/************ Desenvolvido por: *****************/
/********** Lucas de Souza Sales ****************/
/******* Karlos Kelvin Alves dos Santos *********/
/************* Pedro Luis Saraiva ***************/
/************************************************/
package br.com.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.control.Banco;
import br.com.scs.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

public class Acomp_Diabetes extends Activity implements OnClickListener  {
	
	Banco _bd = new Banco(this);
	Cursor _cDiabetes = null;
	
	private static int IdTransacao = 0;

	public static RadioButton RbFazDieta_S, RbFazDieta_N, RbFazExerc_S, RbFazExerc_N, RbUsaInsulina_S, 
				  RbUsaInsulina_N, RbHipog_S, RbHipog_N;//Diabetes
	public static EditText EdtObs , EdtDtUltimaConsulta;

	public static String DtAcompanhamento = null;
	public static String Hash = null;
	
	private java.util.Date data;
	
	private int DATE_DIALOG_ID = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.acomp_diabetes);
		
		InicializaObjetos();
		
		if (DtAcompanhamento != null) {
			PreencheCampos();
		}
	}

	public void onClick(View v) {
		if ( v == EdtDtUltimaConsulta){
			showDialog(DATE_DIALOG_ID);
		}
		
	}
	
	public void InicializaObjetos(){
		EdtDtUltimaConsulta = (EditText)    findViewById(R.acompdiabetes.EdtDtUltimaConsulta);
		EdtObs              = (EditText)    findViewById(R.acompdiabetes.EdtObs);
		RbFazDieta_S        = (RadioButton) findViewById(R.acompdiabetes.RbDietaSim);
		RbFazDieta_N        = (RadioButton) findViewById(R.acompdiabetes.RbDietaNao);
		RbFazExerc_S        = (RadioButton) findViewById(R.acompdiabetes.RbFazExercicioSim);
		RbFazExerc_N        = (RadioButton) findViewById(R.acompdiabetes.RbFazExercicioNao);
		RbUsaInsulina_S     = (RadioButton) findViewById(R.acompdiabetes.RbInsulinaSim);
		RbUsaInsulina_N     = (RadioButton) findViewById(R.acompdiabetes.RbInsulinaNao);
		RbHipog_S           = (RadioButton) findViewById(R.acompdiabetes.RbHipoglicemianteSim);
		RbHipog_N           = (RadioButton) findViewById(R.acompdiabetes.RbHipoglicemianteNao);
		
		EdtDtUltimaConsulta.setOnClickListener(this);
	}
	

	private void PreencheCampos() {
		try{
			_bd.open();
			_cDiabetes = _bd.consulta("diabete", new String[]{"*"}, "hash = '"+Hash+"' and dt_visita = '"+DtAcompanhamento+"' ", null, null, null, null, "1");
			_cDiabetes.moveToFirst();
			if (_cDiabetes.getCount() > 0){
				setIdTransacao(Integer.valueOf(_cDiabetes.getString(_cDiabetes.getColumnIndex("_ID")).toString()));
				EdtDtUltimaConsulta.setText(_cDiabetes.getString(_cDiabetes.getColumnIndex("DT_ULTIMA_VISITA")).toString());
				EdtObs.setText(_cDiabetes.getString(_cDiabetes.getColumnIndex("OBSERVACAO")).toString());
				
				//Faz Dieta
	    		if (_cDiabetes.getString(_cDiabetes.getColumnIndex("FL_FAZ_DIETA")).toString().trim().equals("N"))
	    			RbFazDieta_N.setChecked(true);
	    		else
	    			RbFazDieta_S.setChecked(true);
	    		
	    		//Faz Exercicios
	    		if (_cDiabetes.getString(_cDiabetes.getColumnIndex("FL_FAZ_EXCERCICIOS")).toString().trim().equals("N"))
	    			RbFazExerc_N.setChecked(true);
	    		else
	    			RbFazExerc_S.setChecked(true);
	    		
	    		//Usa Insulina
	    		if (_cDiabetes.getString(_cDiabetes.getColumnIndex("FL_USA_INSULINA")).toString().trim().equals("N"))
	    			RbUsaInsulina_N.setChecked(true);
	    		else
	    			RbUsaInsulina_S.setChecked(true);
	    		
	    		//Faz Exercicios
	    		if (_cDiabetes.getString(_cDiabetes.getColumnIndex("FL_USA_HIPOGLICEMIANTE")).toString().trim().equals("N"))
	    			RbHipog_N.setChecked(true);
	    		else
	    			RbFazExerc_S.setChecked(true);
			}
		  }catch(Exception e) {
				System.out.println("Exceção Acomp_Diabetes: "+e.getMessage());
		  } finally {
				_cDiabetes.close();
	      }
		
	}
	
	public static int getIdTransacao() {
		return IdTransacao;
	}

	public static void setIdTransacao(int idTransacao) {
		IdTransacao = idTransacao;
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendario = Calendar.getInstance();
         
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
         
        switch (id) {
        case 0:
            return new DatePickerDialog(this, mDateSetListener, ano, mes,
                    dia);
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
				System.out.println(e.getMessage());
			}
            EdtDtUltimaConsulta.setText(formatador.format(data));
        }
    };
    
    @Override
	protected void onDestroy() {
		DtAcompanhamento = null;
		Hash = null;
		super.onDestroy();
	}
}
