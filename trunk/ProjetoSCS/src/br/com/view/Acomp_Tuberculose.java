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
import android.widget.RadioGroup;

public class Acomp_Tuberculose extends Activity implements OnClickListener {
	
	private static int IdTransacao = 0;
	
	Banco _bd = new Banco(this);
	Cursor _cTuberculose = null;
	
	private java.util.Date data;
	private int DATE_DIALOG_ID = 0;
	
	public static String DtAcompanhamento = null;
	public static String Hash = null; 
	
	static RadioGroup RgTMd, RgTRi, RgTEe;
	static RadioButton RbTMedicDiaria_S,RbTMedicDiaria_N,RbTReacoesIndesejaveis_S,RbTReacoesIndesejaveis_N,RbTExameEscarro_S,RbTExameEscarro_N;
	static EditText EdtTCe, EdtTM5Bcg, EdtTObs, EdtTUltimaConsulta;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.acomp_tuberculose);
		
		InicializaObjetos();
		
		if (DtAcompanhamento != null) {
			PreencheCampos();
		} else {
			setIdTransacao(0);
		}
	}
	
	public static int getIdTransacao() {
		return IdTransacao;
	}

	public void setIdTransacao(int idTransacao) {
		IdTransacao = idTransacao;
	}

	public void onClick(View v) {
		if (v == EdtTUltimaConsulta){
			showDialog(DATE_DIALOG_ID);
		}
	}
	
	public void InicializaObjetos() {
		RgTMd           		 = (RadioGroup)  findViewById(R.acompanhamentotuberculose.RgTMedicacaoDiaria);
		RgTEe           		 = (RadioGroup)  findViewById(R.acompanhamentotuberculose.RgTExameEscarro);
		RgTRi           		 = (RadioGroup)  findViewById(R.acompanhamentotuberculose.RgTReacoesIndesejadas);
		RbTMedicDiaria_S		 = (RadioButton) findViewById(R.acompanhamentotuberculose.RbMedicacaoDiariaSim);
		RbTMedicDiaria_N		 = (RadioButton) findViewById(R.acompanhamentotuberculose.RbMedicacaoDiariaNao);
		RbTReacoesIndesejaveis_S = (RadioButton) findViewById(R.acompanhamentotuberculose.RbTReacoesIndesejadasSim);
		RbTReacoesIndesejaveis_N = (RadioButton) findViewById(R.acompanhamentotuberculose.RbTReacoesIndesejadasNao);
		RbTExameEscarro_S        = (RadioButton) findViewById(R.acompanhamentotuberculose.RbTExameEscarroSim);
		RbTExameEscarro_N        = (RadioButton) findViewById(R.acompanhamentotuberculose.RbTExameEscarroNao);
		EdtTCe					 = (EditText) 	 findViewById(R.acompanhamentotuberculose.EdtTCe);
		EdtTM5Bcg				 = (EditText) 	 findViewById(R.acompanhamentotuberculose.EdtTMenor5Bcg);
		EdtTObs					 = (EditText) 	 findViewById(R.acompanhamentotuberculose.EdtTObs);
		EdtTUltimaConsulta       = (EditText)    findViewById(R.acompanhamentotuberculose.EdtTUltimaConsulta);
		
		EdtTUltimaConsulta.setOnClickListener(this);
	}
	
	@Override
	protected void onDestroy() {
		DtAcompanhamento = null;
		Hash = null;
		setIdTransacao(0);
		super.onDestroy();
	}		
    
    public void PreencheCampos() {
    	try {			
			_bd.open();
			_cTuberculose = _bd.consulta("tuberculose", new String[]{"*"}, "hash = '"+Hash+"' and dt_visita = '"+DtAcompanhamento+"' ", null, null, null, null, "1");
			_cTuberculose.moveToFirst();
			if (_cTuberculose.getCount() > 0){
				
				setIdTransacao(Integer.valueOf(_cTuberculose.getString(_cTuberculose.getColumnIndex("_ID")).toString().trim()));
				
				if (_cTuberculose.getString(_cTuberculose.getColumnIndex("FL_MEDIC_DIARIA")).toString().trim().equals("S"))
	            	RbTMedicDiaria_S.setChecked(true);
	            else
	            	RbTMedicDiaria_N.setChecked(true);		            
	            	
	    		if (_cTuberculose.getString(_cTuberculose.getColumnIndex("FL_REACOES_IND")).toString().trim().equals("S"))
	    			RbTReacoesIndesejaveis_S.setChecked(true);
	    		else
	    			RbTReacoesIndesejaveis_N.setChecked(true);
	    		
	    		if (_cTuberculose.getString(_cTuberculose.getColumnIndex("FL_EXAME_ESCARRO")).toString().trim().equals("S"))
	    			RbTExameEscarro_S.setChecked(true);
	    		else
	    			RbTExameEscarro_N.setChecked(true);
	    					    
	    		EdtTCe.setText(_cTuberculose.getString(_cTuberculose.getColumnIndex("COMUNIC_EXAMINADOS")).toString());
	    		EdtTUltimaConsulta.setText(_cTuberculose.getString(_cTuberculose.getColumnIndex("DT_ULTIMA_CONSULTA")).toString());
	    		EdtTM5Bcg.setText(_cTuberculose.getString(_cTuberculose.getColumnIndex("MENOR_BCG")).toString());				 
	    		EdtTObs.setText(_cTuberculose.getString(_cTuberculose.getColumnIndex("OBSERVACAO")).toString());				
			}
    	} catch(Exception e) {
    		System.out.println("Exceção na tela de Acompanhamento de Tuberculose: "+e.getMessage());
    	} finally {
    		_cTuberculose.close();
    	}
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
				System.out.println(e.getMessage());;
			}
            	EdtTUltimaConsulta.setText(formatador.format(data));
            
        }
    };

}
