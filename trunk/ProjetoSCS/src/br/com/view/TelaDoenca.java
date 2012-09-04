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
        
        //if (TelaCadastroFamilia.hanseniase == 1){
        	ts = th.newTabSpec("tag1");
            ts.setContent(R.teladoenca.tabHanseniase);
            //ts.setIndicator("Residencia",getResources().getDrawable(R.drawable.casa));
            th.addTab(ts);
       // }
       //if (TelaCadastroFamilia.diabetes == 1){
        	ts = th.newTabSpec("tag2");
            ts.setContent(R.teladoenca.tabDiabetes);
            //ts.setIndicator("Residencia",getResources().getDrawable(R.drawable.casa));
            th.addTab(ts);
       // }
       // if (TelaCadastroFamilia.hipertensao == 1){
        	ts = th.newTabSpec("tag3");
            ts.setContent(R.teladoenca.tabHipertensao);
            //ts.setIndicator("Residencia",getResources().getDrawable(R.drawable.casa));
            th.addTab(ts);
      //  }
      //  if (TelaCadastroFamilia.gestante == 1){
        	ts = th.newTabSpec("tag4");
            ts.setContent(R.teladoenca.tabGestante);
            //ts.setIndicator("Residencia",getResources().getDrawable(R.drawable.casa));
            th.addTab(ts);
     //   }
      //  if (TelaCadastroFamilia.tuberculose == 1){
        	ts = th.newTabSpec("tag5");
            ts.setContent(R.teladoenca.tabTuberculose);
            //ts.setIndicator("Residencia",getResources().getDrawable(R.drawable.casa));
            th.addTab(ts);
      //  }
	}

}
