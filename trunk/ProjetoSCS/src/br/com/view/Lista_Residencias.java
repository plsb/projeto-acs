package br.com.view;

import java.util.ArrayList;
import java.util.HashMap;
import br.com.control.Banco;
import br.com.scs.R;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast; 

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
	        	  item.put( "line1", _cursor.getString(_cursor.getColumnIndex("ENDERECO")).toString()+", "+
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
        
	    sa = new SimpleAdapter(this, list,R.layout.lista_residencias,
	        	    		   new String[] { "line1","line2" },
	        	    		   new int[] {R.id.line_a, R.id.line_b});
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
			//PreparaInsercao();
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