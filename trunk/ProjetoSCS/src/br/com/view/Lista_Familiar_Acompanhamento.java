package br.com.view;

import java.util.ArrayList;
import java.util.HashMap;
import br.com.control.Banco;
import br.com.scs.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
public class Lista_Familiar_Acompanhamento extends ListActivity implements OnClickListener {
	
	Banco _bd = new Banco(this);
	
	private Button   btnFiltrar,btnVoltar;
	private EditText edtFiltro;
	
	public static String END,NUM = "";	
	
	String _ID;
	
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
        
        ListarResidentes(true);
        
    }//Fim do método Main
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listaresidencia, menu);        
        return true;
    }
    
    @Override
	public void onListItemClick(ListView l,View v,int position,long id){
    	
    	super.onListItemClick(l, v, position, id);
    	    	
    	_ID = this.getListAdapter().getItem(position).toString();
    	
    	_ID = _ID.substring(_ID.indexOf("=")+1,_ID.indexOf("-"));
    	
    	System.out.println(_ID);
    	
    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    	dialog.setMessage("Escolha uma Opção:");
    	dialog.setIcon(R.drawable.scs_icone);
    	dialog.setPositiveButton("Iniciar", new
				DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						IniciaAcompanhamento();
					}
    	});
    	
    	dialog.setNeutralButton("Visualizar", new
				DialogInterface.OnClickListener() {
				 
				public void onClick(DialogInterface di, int arg) {
					VisualizarAcompanhamentos();
				}
		});
		dialog.setTitle("Acompanhamento");
		dialog.show();
    	
    }
    
    public void IniciaAcompanhamento(){
    	Intent i = new Intent(this, TelaDoenca.class); 
	    TelaDoenca.COD_FAMILAR = Integer.valueOf(_ID.trim());    	
	    startActivity(i);
    }
    
    public void VisualizarAcompanhamentos(){
    	Intent i = new Intent(this, AcompanhamentosRealizados.class);
    	AcompanhamentosRealizados._ID = Integer.valueOf(_ID.trim());
    	startActivity(i);
    }
    
    public void ListarResidentes(boolean usaFiltro){
    	HashMap<String,String> item;
        _bd.open();
        try{
        	list.clear();
        	Cursor _cursor = null;
        	if (!usaFiltro){
        		_cursor = _bd.consulta("residente", new String[] { "*" },null,null,null,null,"_ID",null);  
        	}else{
        		_cursor = _bd.consulta("residente", new String[] { "*" },"endereco = '"+END+"' AND numero = '"+NUM.trim()+"' and nome like'%"+edtFiltro.getText().toString()+"%'",null,null,null,null,null);
        	}
        	item = new HashMap<String,String>();
        	_cursor.moveToFirst(); 
        	if (_cursor.getCount() > 0){
	        	do{	
	        	  item = new HashMap<String,String>();
	        	  item.put( "line1", _cursor.getString(_cursor.getColumnIndex("_ID")).toString()+"-"+
	        			  			 _cursor.getString(_cursor.getColumnIndex("NOME")).toString());
	        	  item.put( "line2", "Sexo: "+_cursor.getString(_cursor.getColumnIndex("SEXO")).toString()+" - Dt. Nascimento: "+
	        			  			 _cursor.getString(_cursor.getColumnIndex("DTNASCIMENTO")).toString());
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
			ListarResidentes(true);
			break;
		}
		return true;
	}

	public void onClick(View v) {
		
		if (v == btnFiltrar){
			ListarResidentes(true);
		}
		if (v == btnVoltar){
			finish();
		}		
	}
}
