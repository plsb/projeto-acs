package br.com.view;

import java.util.ArrayList;
import java.util.HashMap;
import br.com.control.Banco;
import br.com.scs.R;
import android.annotation.SuppressLint;
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

@SuppressLint("ParserError")
public class Lista_Residencias extends ListActivity implements OnClickListener {
	Banco _bd = new Banco(this);
	
	private Button   btnFiltrar;
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
        
        ListarResidencias(false);
        
    }//Fim do m�todo Main
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listaresidencia, menu);        
        return true;
    }
    
    @Override
	public void onListItemClick(ListView l,View v,int position,long id){
    	
    	super.onListItemClick(l, v, position, id);
    	
    	Object o = this.getListAdapter().getItem(position);
    	
    	Log.i("Retorno", o.toString());
    	    	
    	String _ID = o.toString();
    	
    	_ID = _ID.substring(_ID.indexOf("{line1=")+7, _ID.indexOf("-"));
    	
    	String numero = o.toString();
    	
    	numero = numero.substring(numero.indexOf(", N�")+4, numero.lastIndexOf(","));
    	
    	Intent i = new Intent(this, TelaResidencia.class);
    	
    	TelaResidencia.ID = Integer.valueOf(_ID.trim());
    	
    	startActivity(i);
    	
    	/*Banco bd = null;
    	Cursor c = null;
    	try{
	    	try{
		    	bd = new Banco(this);
		    	bd.open();
		    	c = bd.consulta("residencia", new String[]{"*"}, "endereco = ? and numero = ? ", new String[]{endereco,numero}, null, null, null, null);
		    	c.moveToFirst();
		    	if (c.getCount() > 0){
		    		Toast.makeText(this, "C�digo no Banco: "+c.getString(c.getColumnIndex("_ID")).toString(),Toast.LENGTH_SHORT).show();
		    	}
		    	
	    	}catch(Exception e){
	    		Log.i(WINDOW_SERVICE, e.getMessage());
	    	}
    	}finally{
    		if (c != null){
    			c.close();
    		}
    		if (bd != null){
    			bd.fechaBanco();
    		}
    	}
    	//startActivity(i);*/
    	//Toast.makeText(this, "Voc� Selecionou: "+ _ID+" numero:"+numero,Toast.LENGTH_SHORT).show();
    	
    }
    
    public void ListarResidencias(boolean usaFiltro){
    	HashMap<String,String> item;
        _bd.open();
        try{
        	list.clear();
        	Cursor _cursor = null;
        	if (!usaFiltro){
        		_cursor = _bd.consulta("residencia", new String[] { "*" },null,null,null,null,null,null);
        	}else{
        		_cursor = _bd.consulta("residencia", new String[] { "*" },"endereco like '%"+edtFiltro.getText().toString()+"%' ",null,null,null,null,null);
        	}
        	item = new HashMap<String,String>();
        	_cursor.moveToFirst(); 
        	if (_cursor.getCount() > 0){
	        	do{	
	        	  item = new HashMap<String,String>();
	        	  item.put( "line1", _cursor.getString(_cursor.getColumnIndex("_ID")).toString()+"-"+
	        			  			 _cursor.getString(_cursor.getColumnIndex("ENDERECO")).toString()+", N� "+
						             _cursor.getString(_cursor.getColumnIndex("NUMERO")).toString());
	        	  item.put( "line2", _cursor.getString(_cursor.getColumnIndex("BAIRRO")).toString()+" - "+
	        			  			 _cursor.getString(_cursor.getColumnIndex("MUNICIPIO")).toString());
		          list.add( item );
		        }while (_cursor.moveToNext());	
        	}
        	_cursor.close();
        	_bd.fechaBanco();
        }catch(Exception e){
        	Toast.makeText(this, "Exce��o:" +e.getMessage(), Toast.LENGTH_LONG).show();
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
		
	}
    
}