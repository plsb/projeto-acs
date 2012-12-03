package br.com.view;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.scs.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter;

public class Lista_Relatorio extends ListActivity implements OnClickListener{
	
	Button BtnVoltar;
	
	private SimpleAdapter sa;
	
	public static ArrayList<HashMap<String, String>> relatorio = new ArrayList<HashMap<String,String>>(); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_relatorio);
		
		InicializaObjetos();
		
		sa = new SimpleAdapter(this, relatorio, R.layout.lista_residencias, new String[] { "line1, line2" }, new int[] {R.id.line_a, R.id.line_b});
		setListAdapter(sa);
	}
	
	public void onClick(View v) {
		if ( v == BtnVoltar){
			relatorio = null;
			finish();
		}
		
	}
	
	public void InicializaObjetos(){
		BtnVoltar = (Button) findViewById(R.listarelatorio.btnVoltarLista);
		BtnVoltar.setOnClickListener(this);
	}

}
