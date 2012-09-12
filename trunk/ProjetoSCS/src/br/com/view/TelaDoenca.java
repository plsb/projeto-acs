package br.com.view;

import br.com.scs.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TelaDoenca extends Activity{
	
	TextView datavisita, medicacaodiaria, ultimadose, cuidados, comunicantes, bcg; //Hanseniase
	DatePicker dataprofissional, ultimadata; //hanseniase
	RadioGroup rgmedicacaodiaria, autocuidados; //hanseniase
	 
	

	TextView dDataVisita, dFazDieta, dFazExercicio, dUsaInsulina, dTomaHipo, dUltimaVisita; //Diabetes
	DatePicker dDtDataVisita, dDtUltimaConsulta; //Diabetes
	RadioGroup dRgFazExercicio, dRgFazDieta, dRgInsulina, dRgHipoglicemiante;//Diabetes
	

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
		
	}
	
	public void ComponentesGestante(){
		
	}
	
	public void ComponentesHiperTensao(){
		
	}
}
