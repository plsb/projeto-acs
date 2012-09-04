package br.com.view;

import br.com.scs.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TelaDoenca extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.teladoenca);
		
		TabHost th = (TabHost) findViewById(R.teladoenca.tabhost);
        th.setup();
        TabSpec ts;
        
        if (TelaCadastroFamilia.hanseniase != 0){
        	ts = th.newTabSpec("tag1");
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
