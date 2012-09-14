package br.com.view;

import br.com.scs.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TelaDoenca extends Activity{
	
	TextView datavisita, medicacaodiaria, ultimadose, cuidados, comunicantes, bcg; //Hanseniase
	DatePicker dataprofissional, ultimadata; //hanseniase
	RadioGroup rgmedicacaodiaria, autocuidados; //hanseniase
	EditText EdtHCe, EdtHM5Bcg;
	 
	

	TextView dDataVisita, dFazDieta, dFazExercicio, dUsaInsulina, dTomaHipo, dUltimaVisita; //Diabetes
	DatePicker dDtDataVisita, dDtUltimaConsulta; //Diabetes
	RadioGroup dRgFazExercicio, dRgFazDieta, dRgInsulina, dRgHipoglicemiante;//Diabetes
	
	TextView TxtTDataVisita, TxtTMd, TxtTRi, TxtTEe, TxtTCe, TxtTM5Bcg;
	DatePicker DtTDataVisita;
	RadioGroup RgTMd, RgTRi, RgTEe;
	EditText EdtTCe, EdtTM5Bcg;
	
	TextView TxtHtDataVisita, TxtHtFd, TxtHtTm, TxtHtFe, TxtHtPa, TxtHtDtUV;
	DatePicker DtHtDataVisita, DtHtUltimaVisita;
	RadioGroup RgHtFd, RgHtTm, RgHtFe;
	EditText EdtHtPe;
	

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.teladoenca);
		
		//Componentes Hanseniase
		datavisita = (TextView) findViewById(R.teladoenca.TxtHDataVisita);
		medicacaodiaria = (TextView) findViewById(R.teladoenca.TxtHMedicacaoDiaria);
		ultimadose = (TextView) findViewById(R.teladoenca.TxtHDataUltimaDose);
		cuidados = (TextView) findViewById(R.teladoenca.TxtHFazCuidados);
		comunicantes = (TextView) findViewById(R.teladoenca.TxtHComunicantes);
		bcg = (TextView) findViewById(R.teladoenca.TxtHBCG);
		dataprofissional = (DatePicker) findViewById(R.teladoenca.DtHDataProfissional);
		ultimadata = (DatePicker) findViewById(R.teladoenca.DtHUltimaData);
		rgmedicacaodiaria = (RadioGroup) findViewById(R.teladoenca.RgHMedicacaoDiaria);
		autocuidados = (RadioGroup) findViewById(R.teladoenca.RgHAutoCuidados);
		EdtHCe = (EditText) findViewById(R.teladoenca.EdtHCe);
		EdtHM5Bcg = (EditText) findViewById(R.teladoenca.EdtHMenor5Bcg);
		//Componentes Hanseniase
		
		//Componentes Diabetes
		dUltimaVisita = (TextView) findViewById(R.teladoenca.TxtDUltimaVisita);
		dDataVisita = (TextView) findViewById(R.teladoenca.TxtDDataVisita);
		dFazDieta = (TextView) findViewById(R.teladoenca.TxtDFazDieta);
		dFazExercicio = (TextView) findViewById(R.teladoenca.TxtDFazExercicio);
		dUsaInsulina = (TextView) findViewById(R.teladoenca.TxtDInsulina);
		dTomaHipo = (TextView) findViewById(R.teladoenca.TxtDHipoglicemiante);
		dDtDataVisita = (DatePicker) findViewById(R.teladoenca.DtDDataProfissional);
		dDtUltimaConsulta = (DatePicker) findViewById(R.teladoenca.DtDUltimaConsulta);
		dRgFazExercicio = (RadioGroup) findViewById(R.teladoenca.RgDFazExercicio);
		dRgFazDieta = (RadioGroup) findViewById(R.teladoenca.RgDDieta);
		dRgInsulina = (RadioGroup) findViewById(R.teladoenca.RgDInsulina);
		dRgHipoglicemiante = (RadioGroup) findViewById(R.teladoenca.RgDHipoglicemiante);
		
		
		//Componentes tuberculose
		TxtTDataVisita = (TextView) findViewById(R.teladoenca.TxtTDataVisita);
		TxtTMd = (TextView) findViewById(R.teladoenca.TxtTMedicacaoDiaria);
		TxtTRi = (TextView) findViewById(R.teladoenca.TxtTReacoesIndesejadas);
		TxtTEe = (TextView) findViewById(R.teladoenca.TxtTExameEscarro);
		TxtTCe = (TextView) findViewById(R.teladoenca.TxtTComunicantesExaminados);
		TxtTM5Bcg = (TextView) findViewById(R.teladoenca.TxtTMenor5Bcg);
		DtTDataVisita = (DatePicker) findViewById(R.teladoenca.DtTDataVisita);
		RgTMd = (RadioGroup) findViewById(R.teladoenca.RgTMedicacaoDiaria);
		RgTEe = (RadioGroup) findViewById(R.teladoenca.RgTExameEscarro);
		RgTRi = (RadioGroup) findViewById(R.teladoenca.RgTReacoesIndesejadas);
		EdtTCe = (EditText) findViewById(R.teladoenca.EdtTCe);
		EdtTM5Bcg = (EditText) findViewById(R.teladoenca.EdtTMenor5Bcg);
		
		//Componentes Hipertensao
		TxtHtDataVisita = (TextView) findViewById(R.teladoenca.TxtHtDataVisita);
		TxtHtFd = (TextView) findViewById(R.teladoenca.TxtHtFazDieta); 
		TxtHtTm = (TextView) findViewById(R.teladoenca.TxtHtTomaMedicacao);
		TxtHtFe = (TextView) findViewById(R.teladoenca.TxtHtFazExercicio); 
		TxtHtPa = (TextView) findViewById(R.teladoenca.TxtHtPressaoArterial); 
		TxtHtDtUV = (TextView) findViewById(R.teladoenca.TxtHtDtUltimaVisita);
		DtHtDataVisita = (DatePicker) findViewById(R.teladoenca.DtHtDataVisita);
		DtHtUltimaVisita = (DatePicker) findViewById(R.teladoenca.DtHtUltimaVisita);
		RgHtFd = (RadioGroup) findViewById(R.teladoenca.RgHtDieta); 
		RgHtTm = (RadioGroup) findViewById(R.teladoenca.RgHtMedicacao); 
		RgHtFe = (RadioGroup) findViewById(R.teladoenca.RgHtFazExercicio);
		EdtHtPe = (EditText) findViewById(R.teladoenca.EdtHtPressaoArterial);
		
		
		TabHost th = (TabHost) findViewById(R.teladoenca.tabhost);
        th.setup();
        TabSpec ts;
        
        if (TelaCadastroFamilia.hanseniase != 0){
        	ts = th.newTabSpec("tag1");
        	ComponentesHanseniase();
            ts.setContent(R.teladoenca.tabHanseniase);
            ts.setIndicator("Hanseniase",getResources().getDrawable(R.drawable.hanseniase));
            th.addTab(ts);
        }
        if (TelaCadastroFamilia.diabetes != 0){
        	ts = th.newTabSpec("tag2");
        	ComponentesDiabetes();
            ts.setContent(R.teladoenca.tabDiabetes);
            ts.setIndicator("Diabetes",getResources().getDrawable(R.drawable.diabetes));
            th.addTab(ts);
        }
       if (TelaCadastroFamilia.hipertensao != 0){
        	ts = th.newTabSpec("tag3");
        	ComponentesHiperTensao();
            ts.setContent(R.teladoenca.tabHipertensao);
            ts.setIndicator("Hipertensao",getResources().getDrawable(R.drawable.hipertensao));
            th.addTab(ts);
        }
        if (TelaCadastroFamilia.gestante != 0){
        	ts = th.newTabSpec("tag4");
            ts.setContent(R.teladoenca.tabGestante);
            ts.setIndicator("Gestante",getResources().getDrawable(R.drawable.gestante));
            th.addTab(ts);
       }
        if (TelaCadastroFamilia.tuberculose != 0){
        	ts = th.newTabSpec("tag5");
        	ComponentesTuberculose();
            ts.setContent(R.teladoenca.tabTuberculose);
            ts.setIndicator("Tuberculose",getResources().getDrawable(R.drawable.tuberculose));
            th.addTab(ts);
       }
	}
	
	public void ComponentesHanseniase(){
      	datavisita.setVisibility(0);
    	medicacaodiaria.setVisibility(0);
    	ultimadose.setVisibility(0);
    	cuidados.setVisibility(0);
    	comunicantes.setVisibility(0);
    	bcg.setVisibility(0);
    	dataprofissional.setVisibility(0);
    	ultimadata.setVisibility(0);
    	rgmedicacaodiaria.setVisibility(0);
    	autocuidados.setVisibility(0);
		EdtHCe.setVisibility(0);
		EdtHM5Bcg.setVisibility(0);
	}
	
	public void ComponentesDiabetes(){
		dUltimaVisita.setVisibility(0);
		dDataVisita.setVisibility(0); 
		dFazDieta.setVisibility(0);
		dFazExercicio.setVisibility(0); 
		dUsaInsulina.setVisibility(0);
		dTomaHipo.setVisibility(0);  
		dDtDataVisita.setVisibility(0); 
		dDtUltimaConsulta.setVisibility(0); 
		dRgFazExercicio.setVisibility(0);
		dRgFazDieta.setVisibility(0);
		dRgInsulina.setVisibility(0);
		dRgHipoglicemiante.setVisibility(0);
	}
	
	public void ComponentesTuberculose(){
		TxtTCe.setVisibility(0);
		TxtTDataVisita.setVisibility(0);
		TxtTEe.setVisibility(0);
		TxtTM5Bcg.setVisibility(0);
		TxtTMd.setVisibility(0);
		TxtTRi.setVisibility(0);
		DtTDataVisita.setVisibility(0);
		RgTEe.setVisibility(0);
		RgTMd.setVisibility(0);
		RgTRi.setVisibility(0);
		EdtTCe.setVisibility(0);
		EdtTM5Bcg.setVisibility(0);
		
	}
	
	public void ComponentesGestante(){
		
	}
	
	public void ComponentesHiperTensao(){
		TxtHtDataVisita.setVisibility(0);
		TxtHtFd.setVisibility(0); 
		TxtHtTm.setVisibility(0); 
		TxtHtFe.setVisibility(0); 
		TxtHtPa.setVisibility(0); 
		TxtHtDtUV.setVisibility(0);
		DtHtDataVisita.setVisibility(0);
		DtHtUltimaVisita.setVisibility(0);
		RgHtFd.setVisibility(0); 
		RgHtTm.setVisibility(0); 
		RgHtFe.setVisibility(0);
		EdtHtPe.setVisibility(0);
	}
}
