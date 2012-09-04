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
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.teladoenca);
		
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
		
		
		
		TabHost th = (TabHost) findViewById(R.teladoenca.tabhost);
        th.setup();
        TabSpec ts;
        
        if (TelaCadastroFamilia.hanseniase != 0){
        	ts = th.newTabSpec("tag1");
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
            ts.setContent(R.teladoenca.tabHanseniase);
            ts.setIndicator("Hanseniase",getResources().getDrawable(R.drawable.hanseniase));
            th.addTab(ts);
        }
        if (TelaCadastroFamilia.diabetes != 0){
        	ts = th.newTabSpec("tag2");
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

}
