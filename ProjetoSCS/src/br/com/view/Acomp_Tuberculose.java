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
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Acomp_Tuberculose extends Activity implements OnClickListener {
	
	private static int IdTransacao = 0;
	
	Banco _bd = new Banco(this);
	Cursor _cTuberculose = null;
	
	public static String DtAcompanhamento = null;
	public static String Hash = null; 
	
	static RadioGroup RgTMd, RgTRi, RgTEe;
	static RadioButton RbTMedicDiaria_S,RbTMedicDiaria_N,RbTReacoesIndesejaveis_S,RbTReacoesIndesejaveis_N,RbTExameEscarro_S,RbTExameEscarro_N;
	static EditText EdtTCe, EdtTM5Bcg, EdtTObs;
	
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
	    		EdtTM5Bcg.setText(_cTuberculose.getString(_cTuberculose.getColumnIndex("MENOR_BCG")).toString());				 
	    		EdtTObs.setText(_cTuberculose.getString(_cTuberculose.getColumnIndex("OBSERVACAO")).toString());				
			}
    	} catch(Exception e) {
    		System.out.println("Exceção na tela de Acompanhamento de Tuberculose: "+e.getMessage());
    	} finally {
    		_cTuberculose.close();
    	}
    }

}
