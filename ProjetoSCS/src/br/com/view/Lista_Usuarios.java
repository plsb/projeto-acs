/************************************************/
/** Projeto SCS - Sistema de Controle de Saúde **/
/************ Desenvolvido por: *****************/
/********** Lucas de Souza Sales ****************/
/******* Karlos Kelvin Alves dos Santos *********/
/************* Pedro Luis Saraiva ***************/
/************************************************/
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
public class Lista_Usuarios extends ListActivity implements OnClickListener {
	
	Banco _bd = new Banco(this);
	
	private Button   btnFiltrar,btnVoltar;
	private EditText edtFiltro;
	
	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

	private SimpleAdapter sa;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listaresidente);
        
        edtFiltro  = (EditText) findViewById(R.listaResidente.edtFiltro);
        btnFiltrar = (Button) findViewById(R.listaResidente.btnFiltrar);
        btnFiltrar.setOnClickListener(this);  
        btnVoltar  = (Button) findViewById(R.listaResidente.btnVoltarLista);
        btnVoltar.setOnClickListener(this);
        
        ListarUsuarios(false);
        
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
    	
    	Log.i("Retorno", o.toString());
    	    	
    	/**String _ID = o.toString();
    	
    	_ID = _ID.substring(_ID.indexOf("{line1=")+7, _ID.indexOf("-"));
    	
    	String numero = o.toString();
    	
    	numero = numero.substring(numero.indexOf(", Nº")+4, numero.lastIndexOf(","));
    	
    	Intent i = new Intent(this, TelaResidencia.class); 
    	TelaResidencia.ID = Integer.valueOf(_ID.trim());    	
    	startActivity(i);**/
    	
    }
    
    public void ListarUsuarios(boolean usaFiltro){
    	HashMap<String,String> item;
        _bd.open();
        try{
        	list.clear();
        	Cursor _cursor = null;
        	if (!usaFiltro){
        		_cursor = _bd.consulta("usuarios", new String[] { "*" },null,null,null,null,null,null);
        	}else{
        		_cursor = _bd.consulta("usuarios", new String[] { "*" },"usu_nome like '%"+edtFiltro.getText().toString()+"%' ",null,null,null,null,null);
        	}
        	item = new HashMap<String,String>();
        	_cursor.moveToFirst(); 
        	if (_cursor.getCount() > 0){
	        	do{	
	        	  item = new HashMap<String,String>();
	        	  item.put( "line1", _cursor.getString(_cursor.getColumnIndex("_ID")).toString()+"-"+
	        			  			 _cursor.getString(_cursor.getColumnIndex("USU_NOME")).toString());
	        	  if (_cursor.getString(_cursor.getColumnIndex("USU_ATIVO")).toString().equals("S")){
	        		  item.put( "line2", "Status: Usuário liberado.");
	        	  }else{
	        		  item.put( "line2", "Status: Usuário bloqueado");
	        	  }	        	  
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
			ListarUsuarios(true);
			break;
		}
		return true;
	}

	public void onClick(View v) {
		
		if (v == btnFiltrar){
			ListarUsuarios(true);
		}
		if (v == btnVoltar){
			finish();
		}		
	}
    
}