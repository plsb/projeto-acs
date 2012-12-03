package br.com.view;

import java.util.ArrayList;
import java.util.HashMap;
import br.com.control.Banco;
import br.com.control.Sessao;
import br.com.scs.R;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast; 

public class TelaVacinas extends ListActivity implements OnClickListener {
	Banco _bd = new Banco(this);
	
	private Button   btnFiltrar,btnVoltar;
	private EditText edtFiltro;
	
	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

	private SimpleAdapter sa;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listax);
        
        edtFiltro  = (EditText) findViewById(R.id.edtFiltro);
        btnFiltrar = (Button) findViewById(R.id.btnFiltrar);
        btnFiltrar.setOnClickListener(this);  
        btnVoltar  = (Button) findViewById(R.id.btnVoltarLista);
        btnVoltar.setOnClickListener(this);
        
        ListarResidencias(false);
        
    }//Fim do método Main
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listaresidencia, menu);        
        return true;
    }
    
    @Override
	public void onListItemClick(ListView l,View v,int position,long id){
    	
    	super.onListItemClick(l, v, position, id);
    	
    	Object o = this.getListAdapter().getItem(position);
    	
    	//Log.i("Retorno", o.toString());
    	    	
    	String endereco = o.toString();
    	
    	endereco = endereco.substring(endereco.indexOf("{line1=")+7, endereco.indexOf(", Nº"));
    	
    	String numero = o.toString();
    	
    	numero = numero.substring(numero.indexOf(", Nº")+4, numero.lastIndexOf(", l"));
    	
    	String complemento = o.toString();
    	
    	complemento = complemento.substring(complemento.indexOf(":")+2, complemento.lastIndexOf(","));
    	    
    	Intent i = new Intent(this, Lista_Familiar_Vacinas.class);    
    	Lista_Familiar_Vacinas.END = endereco;
    	Lista_Familiar_Vacinas.NUM = numero.trim();
    	Lista_Familiar_Vacinas.COMPLEMENTO = complemento;
    	startActivity(i);
    	
    }
    
    public void ListarResidencias(boolean usaFiltro){
    	String Usuario = Sessao.SESSAO.getMatriculaUsuario(this);
    	HashMap<String,String> item;
        _bd.open();
        try{
        	list.clear();
        	Cursor _cursor = null;
        	if (!usaFiltro){
        		_cursor = _bd.consulta("residencia", new String[] { "*" },"cod_endereco in (select cod_ret from ruas where usu_vinculado = "+Usuario+")",null,null,null,null,null);
        	}else{
        		_cursor = _bd.consulta("residencia", new String[] { "*" },"endereco like '%"+edtFiltro.getText().toString()+"%' and cod_endereco in (select cod_ret from ruas where usu_vinculado = "+Usuario+")",null,null,null,null,null);
        	}
        	item = new HashMap<String,String>();
        	_cursor.moveToFirst(); 
        	if (_cursor.getCount() > 0){
	        	do{	
	        	  item = new HashMap<String,String>();
	        	  item.put( "line1", _cursor.getString(_cursor.getColumnIndex("ENDERECO")).toString()+", Nº "+
						             _cursor.getString(_cursor.getColumnIndex("NUMERO")).toString());
	        	  item.put( "line2", "Complemento: "+_cursor.getString(_cursor.getColumnIndex("COMPLEMENTO")).toString()+", BAIRRO: "+
	        			  			 _cursor.getString(_cursor.getColumnIndex("BAIRRO")).toString());
		          list.add( item );
		        }while (_cursor.moveToNext());	
        	}
        	_cursor.close();
        	_bd.fechaBanco();
        }catch(Exception e){
        	Toast.makeText(this, "Exceção:" +e.getMessage(), Toast.LENGTH_LONG).show();
        }
        
	    sa = new SimpleAdapter(this, list,R.layout.lista_residencias, new String[] { "line1","line2" }, new int[] {R.id.line_a, R.id.line_b});
	    if (!sa.isEmpty()){    
	        setListAdapter(sa);
        }else{
        	setListAdapter(sa);
        }
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.listaresidencia.menu_filtrar:
			ListarResidencias(true);
			break;
		}
		return true;
	}

	public void onClick(View v) {
		
		if (v == btnFiltrar){
			ListarResidencias(true);
		}
		if (v == btnVoltar){
			finish();
		}		
	}
    
}