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
import android.widget.SimpleAdapter;
import android.widget.Toast; 

public class Lista_Residencias extends ListActivity {
	Banco _bd = new Banco(this);
	
	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

	private SimpleAdapter sa;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listax);
        
        HashMap<String,String> item;
        _bd.open();
        try{
        	Cursor _cursor = _bd.consulta("residencia", new String[] { "*" },null,null,null,null,null,null);
        	item = new HashMap<String,String>();
        	_cursor.moveToFirst();
        	item.put( "line1", _cursor.getString(_cursor.getColumnIndex("ENDERECO")).toString()+", "+
        					   _cursor.getString(_cursor.getColumnIndex("NUMERO")).toString());
        	item.put( "line2", _cursor.getString(_cursor.getColumnIndex("BAIRRO")).toString()+" - "+
		  			 		   _cursor.getString(_cursor.getColumnIndex("MUNICIPIO")).toString());
	        list.add( item );
        	while (_cursor.moveToNext()){	
        	  item = new HashMap<String,String>();
        	  item.put( "line1", _cursor.getString(_cursor.getColumnIndex("ENDERECO")).toString()+", "+
					             _cursor.getString(_cursor.getColumnIndex("NUMERO")).toString());
        	  item.put( "line2", _cursor.getString(_cursor.getColumnIndex("BAIRRO")).toString()+" - "+
        			  			 _cursor.getString(_cursor.getColumnIndex("MUNICIPIO")).toString());
	          list.add( item );
	          _cursor.moveToNext();
	        }
        	_cursor.close();
        	_bd.fechaBanco();
        }catch(Exception e){
        	Toast.makeText(this, "Exceção:" +e.getMessage(), Toast.LENGTH_LONG).show();
        }
        
        sa = new SimpleAdapter(this, list,R.layout.lista_residencias
        	    ,
        	    new String[] { "line1","line2" },
        	    new int[] {R.id.line_a, R.id.line_b});
        
        setListAdapter(sa);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listaresidencia, menu);        
        return true;
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
    
}