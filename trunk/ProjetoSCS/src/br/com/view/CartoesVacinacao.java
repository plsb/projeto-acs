package br.com.view;

import br.com.scs.R;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;

public class CartoesVacinacao extends ActivityGroup implements OnClickListener {
	
	public static String _Hash = "";
	public static boolean _CartaoGestante = false;
	
	static TabHost th;
	static int tab = -1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.cartoesvacinacao);
		
		th = (TabHost) findViewById(R.id.tabhost);
		th.setup(this.getLocalActivityManager());
		TabHost.TabSpec spec;
		Intent intent;        
        
        intent = new Intent().setClass(this, CartaoCrianca.class);
        CartaoCrianca.Hash = _Hash;        
        spec = th.newTabSpec("0").setIndicator("Criança", getResources().getDrawable(R.drawable.child)).setContent(intent);        
        th.addTab(spec);
        
        intent = new Intent().setClass(this, CartaoAdolescente.class);
        CartaoAdolescente.Hash = _Hash;        
        spec = th.newTabSpec("1").setIndicator("Adolescente", getResources().getDrawable(R.drawable.adolescente)).setContent(intent);        
        th.addTab(spec);
        
        intent = new Intent().setClass(this, CartaoAdulto.class); 
        CartaoAdulto.Hash = _Hash;        
        spec = th.newTabSpec("2").setIndicator("Adulto", getResources().getDrawable(R.drawable.adulto)).setContent(intent);        
        th.addTab(spec);
        
        if (_CartaoGestante == true) {
	        intent = new Intent().setClass(this, CartaoGestante.class);
	        CartaoGestante.Hash = _Hash;        
	        spec = th.newTabSpec("3").setIndicator("Gestante", getResources().getDrawable(R.drawable.gravida)).setContent(intent);        
	        th.addTab(spec);
        }
        
	}
	
	public void onClick(View v) {
				
	}
	
	@Override
	protected void onDestroy() {
		_CartaoGestante = false;
		_Hash = "";
		super.onDestroy();
	}

}
