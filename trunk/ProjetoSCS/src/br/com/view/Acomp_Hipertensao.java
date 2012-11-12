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
import android.widget.RadioGroup;

public class Acomp_Hipertensao extends Activity implements OnClickListener {
	
	private static int IdTransacao = 0;
	
	Banco _bd = new Banco(this);
	Cursor _cHipertensao = null;
	
	public static String DtAcompanhamento = null;
	public static String Hash = null; 
	
	private int DATE_DIALOG_ID = -1;
	
	static RadioGroup RgHtFd, RgHtTm, RgHtFe;
	static RadioButton RbHiFazDieta_S, RbHiFazDieta_N, RbHiTomaMedic_S, RbHiTomaMedic_N, RbHiExcercFisico_S, RbHiExcercFisico_N; 
	static EditText EdtHtPe, EdtHtObs, EdtDtUltimaVisita;
	
	private java.util.Date data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.acomp_hipertensao);
		
		InicializaObjetos();
		
		if (DtAcompanhamento != null) {
			PreencheCampos();
		}
	}
	
	public static int getIdTransacao() {
		return IdTransacao;
	}

	public void setIdTransacao(int idTransacao) {
		IdTransacao = idTransacao;
	}

	public void onClick(View v) {
		if (v == EdtDtUltimaVisita) {
			DATE_DIALOG_ID = 0;
			showDialog(DATE_DIALOG_ID);
		}
	}
	
	public void InicializaObjetos() {
		RgHtFd					 = (RadioGroup)  findViewById(R.acompanhamentohipertensao.RgHtDieta); 
		RgHtTm					 = (RadioGroup)  findViewById(R.acompanhamentohipertensao.RgHtMedicacao); 
		RgHtFe					 = (RadioGroup)  findViewById(R.acompanhamentohipertensao.RgHtFazExercicio);
		RbHiFazDieta_S			 = (RadioButton) findViewById(R.acompanhamentohipertensao.RbHtDietaSim);
		RbHiFazDieta_N			 = (RadioButton) findViewById(R.acompanhamentohipertensao.RbHtDietaNao);
		RbHiTomaMedic_S			 = (RadioButton) findViewById(R.acompanhamentohipertensao.RbHtMedicacaoSim);
		RbHiTomaMedic_N			 = (RadioButton) findViewById(R.acompanhamentohipertensao.RbHtMedicacaoNao);
		RbHiExcercFisico_S		 = (RadioButton) findViewById(R.acompanhamentohipertensao.RbHtFazExercicioSim);
		RbHiExcercFisico_N		 = (RadioButton) findViewById(R.acompanhamentohipertensao.RbHtFazExercicioNao);
		EdtHtPe					 = (EditText) 	 findViewById(R.acompanhamentohipertensao.EdtHtPressaoArterial);
		EdtHtObs				 = (EditText)    findViewById(R.acompanhamentohipertensao.EdtHtObs);
		EdtDtUltimaVisita        = (EditText)    findViewById(R.acompanhamentohipertensao.DtUltimaVisita);
		EdtDtUltimaVisita.setOnClickListener(this);
	}
	
	@Override
	protected void onDestroy() {
		DtAcompanhamento = null;
		Hash = null;
		DATE_DIALOG_ID = -1;
		setIdTransacao(0);
		super.onDestroy();
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
				System.out.println(e.getMessage());
			}
            if (DATE_DIALOG_ID == 0) {
            	EdtDtUltimaVisita.setText(formatador.format(data));
            }
        }
    };
    
    public void PreencheCampos() {
    	try {			
			_bd.open();
			_cHipertensao = _bd.consulta("hipertensao", new String[]{"*"}, "hash = '"+Hash+"' and dt_visita = '"+DtAcompanhamento+"' ", null, null, null, null, "1");
			_cHipertensao.moveToFirst();
			if (_cHipertensao.getCount() > 0){
				
				setIdTransacao(Integer.valueOf(_cHipertensao.getString(_cHipertensao.getColumnIndex("_ID")).toString().trim()));
								
				if (_cHipertensao.getString(_cHipertensao.getColumnIndex("FL_FAZ_DIETA")).toString().trim().equals("S")){
	            	RbHiFazDieta_S.setChecked(true);
	    		}else{
	    			RbHiFazDieta_N.setChecked(true);
	    		}
	            	
	            if (_cHipertensao.getString(_cHipertensao.getColumnIndex("FL_TOMA_MEDICACAO")).toString().trim().equals("S")){
	            	RbHiTomaMedic_S.setChecked(true);
	    		}else{
	    			RbHiTomaMedic_N.setChecked(true);
	    		}
	    		
	            if (_cHipertensao.getString(_cHipertensao.getColumnIndex("FL_FAZ_EXERCICIOS")).toString().trim().equals("S")){
	            	RbHiExcercFisico_S.setChecked(true);
	    		}else{
	    			RbHiExcercFisico_N.setChecked(true);
	    		}

	            EdtDtUltimaVisita.setText(_cHipertensao.getString(_cHipertensao.getColumnIndex("DT_ULTIMA_VISITA")).toString());
	    		EdtHtPe.setText(_cHipertensao.getString(_cHipertensao.getColumnIndex("PRESSAO_ARTERIAL")).toString());
	    		EdtHtObs.setText(_cHipertensao.getString(_cHipertensao.getColumnIndex("OBSERVACAO")).toString());
			}
    	} catch(Exception e) {
    		System.out.println("Exceção na tela de Acompanhamento de Hipertensão: "+e.getMessage());
    	} finally {
    		_cHipertensao.close();
    	}
    }

}
