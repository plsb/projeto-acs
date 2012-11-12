package br.com.view;

import br.com.scs.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Acomp_Hipertensao extends Activity implements OnClickListener {
	
	private static int IdTransacao = 0;
	
	public static String DtAcompanhamento = null;
	public static String Hash = null; 
	
	private int DATE_DIALOG_ID = -1;
	
	static RadioGroup RgHtFd, RgHtTm, RgHtFe;
	static RadioButton RbHiFazDieta_S, RbHiFazDieta_N, RbHiTomaMedic_S, RbHiTomaMedic_N, RbHiExcercFisico_S, RbHiExcercFisico_N; 
	static EditText EdtHtPe, EdtHtObs, EdtDtUltimaVisita;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.acomp_hipertensao);
		
		InicializaObjetos();
		
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

}
