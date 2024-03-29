/************************************************/
/** Projeto SCS - Sistema de Controle de Sa�de **/
/************ Desenvolvido por: *****************/
/********** Lucas de Souza Sales ****************/
/******* Karlos Kelvin Alves dos Santos *********/
/************* Pedro Luis Saraiva ***************/
/************************************************/
package br.com.view;

import java.sql.Date;
import java.text.SimpleDateFormat;
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
public class Lista_Familiar_Vacinas extends ListActivity implements OnClickListener {
	
	Banco _bd = new Banco(this);
	
	private Button   btnFiltrar,btnVoltar;
	private EditText edtFiltro;
	
	public static String END,NUM,COMPLEMENTO = "";	
	
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
        
    }//Fim do m�todo Main
    
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
    	
    	OpcoesVacina._ID = _ID;
    	Intent i = new Intent(this, OpcoesVacina.class);
    	startActivity(i);
    	
    	/*AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    	dialog.setMessage("Escolha uma Op��o:");
    	dialog.setIcon(R.drawable.iconscs);
    	dialog.setPositiveButton("Agendar", new
				DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						AtualizaVacina();
					}
    	});
    	
    	dialog.setNeutralButton("Visualizar", new
				DialogInterface.OnClickListener() {
				 
				public void onClick(DialogInterface di, int arg) {
					VisualizarCartaoVacina();
				}
		});
		dialog.setTitle("Vacina��o");
		dialog.show();
    	*/
    }
    /*
    public void AtualizaVacina(){
    	Intent i = new Intent(this, OpcoesCartaoVacina.class); 
    	OpcoesCartaoVacina._ID = Integer.valueOf(_ID.trim());    
	    startActivity(i);
    }
    
    public void VisualizarCartaoVacina(){
    	String Hash = "";
    	Cursor c = null;
    	try{
    		_bd.open();
    		c = _bd.consulta("residente", new String[]{"*"}, "_ID = "+String.valueOf(_ID), null, null, null, null, null);
    		c.moveToFirst();    		
    		if (c.getCount() > 0){
    			Hash = c.getString(c.getColumnIndex("HASH")).toString().trim();    	
    		}    		    
    		
    	}catch (Exception e) {
			System.out.println(e.getMessage());
		}    	
    	Intent i = new Intent(this,CartoesVacinacao.class);
    	CartoesVacinacao._Hash = Hash;
    	CartoesVacinacao._CartaoGestante = ( c.getString(c.getColumnIndex("SEXO")).toString().trim().equals("F") ? true : false);
    	startActivity(i);    	
    }
    */
    
    public void ListarResidentes(boolean usaFiltro){
    	HashMap<String,String> item;
        _bd.open();
        try{
        	list.clear();
        	Cursor _cursor = null;
        	if (!usaFiltro){
        		_cursor = _bd.consulta("residente", new String[] { "*" },null,null,null,null,"_ID",null);  
        	}else{
        		_cursor = _bd.consulta("residente", new String[] { "*" },"endereco = '"+END+"' AND numero = '"+NUM.trim()+"' and complemento = '"+COMPLEMENTO+"' and nome like'%"+edtFiltro.getText().toString()+"%' and fl_faleceu = 'N'",null,null,null,null,null);
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
	
    @SuppressLint("ParserError")
    public int CalculaIdade(int _dia, int _mes, int _ano){
    	SimpleDateFormat f;	
    	f = new SimpleDateFormat("dd");
    	int dia = Integer.valueOf(f.format(new Date(System.currentTimeMillis())));
    	f = new SimpleDateFormat("MM");
    	int mes = Integer.valueOf(f.format(new Date(System.currentTimeMillis())));
    	f = new SimpleDateFormat("yyyy");
    	int ano = Integer.valueOf(f.format(new Date(System.currentTimeMillis())));	
    	if ((mes >= _mes)){
    		return (ano - _ano);
    	}else if ((mes == _mes)&&(dia >= dia)){	
    		return (ano - _ano);
    	}else{
    		return ((ano - _ano) -1);
    	}
    }
    
    @Override
    protected void onDestroy() {
    	END = "";
    	NUM = "";
    	COMPLEMENTO = "";
    	super.onDestroy();
    }
}

