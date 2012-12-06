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
import br.com.control.Mensagem;
import br.com.scs.R;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Lista_Agendamento_Vacina extends ListActivity implements OnClickListener {
	
	public static int _ID = 0;
	private String Hash = "";
	
	private Banco _bd = new Banco(this);
	private SimpleAdapter sa;
	
	private Button   btnFiltrar,btnVoltar;
	private EditText edtFiltro;
	
	private ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listavacinasmarcadas);
		
		edtFiltro  = (EditText) findViewById(R.listavacinas.edtFiltro);
        btnFiltrar = (Button) findViewById(R.listavacinas.btnFiltrar);        
        btnVoltar  = (Button) findViewById(R.listavacinas.btnVoltarLista);
        btnVoltar.setOnClickListener(this);
        btnFiltrar.setOnClickListener(this);  
        
        Hash = getHashFalimiar(String.valueOf(_ID));
                
	}
	
	@Override
	protected void onResume() {
		ListarVacinas(false);
		super.onResume();
	}
	
	public void onClick(View v) {
		if (v == btnFiltrar) {
			if (edtFiltro.getText().toString().trim().length() > 0) {
				ListarVacinas(true);
			}
		}
		if (v == btnVoltar) {
			finish();
		}
	}
	
	public String getHashFalimiar(String pID) {
		 String retorno = "";
		 _bd.open();
	     try{
	    	 Cursor _cursor = null;
	    	 _cursor = _bd.consulta("residente", new String[] {"*"}, "_ID = "+pID, null, null, null, null, null);
	    	 _cursor.moveToFirst();
	    	 if (_cursor.getCount() > 0) {
	    		 retorno = _cursor.getString(_cursor.getColumnIndex("HASH")).toString();
	    	 } else {
	    		 retorno = "";
	    	 }
	    	 _cursor.close();
	     } catch(Exception e) {
	    	 Mensagem.exibeMessagem(this, "SCS-Erro", "Problema no método getHashFamiliar");
	     } 
	     
		return retorno;
	}
	
	public void ListarVacinas(boolean usaFiltro){
    	HashMap<String,String> item;
        _bd.open();
        try{
        	list.clear();
        	Cursor _cursor = null;
        	if (!usaFiltro){
        		_cursor = _bd.consulta("vacinas", new String[] { "*" },"hash = '"+Hash+"' and fl_aplicada = 'N' ",null,null,null,null,null);
        	}else{
        		_cursor = _bd.consulta("vacinas", new String[] { "*" },"hash = '"+Hash+"' and fl_aplicada = 'N' and tipo_vacina like '%"+edtFiltro.getText().toString()+"%' ",null,null,null,null,null);
        	}
        	item = new HashMap<String,String>();
        	_cursor.moveToFirst(); 
        	if (_cursor.getCount() > 0){
	        	do{	
	        	  item = new HashMap<String,String>();
	        	  item.put( "line1", _cursor.getString(_cursor.getColumnIndex("_ID")).toString()+"-"+ 
	        			  			 _cursor.getString(_cursor.getColumnIndex("TIPO_VACINA")).toString());
	        	  item.put( "line2", "Data p/ Aplicação: "+_cursor.getString(_cursor.getColumnIndex("DT_APLICACAO")).toString()+", Dose: "+	        			  			 
	        			  			 _cursor.getString(_cursor.getColumnIndex("DOSE_APLICADA")).toString());
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
	public void onListItemClick(ListView l,View v,int position,long id){
    	
    	super.onListItemClick(l, v, position, id);
    	
    	Object o = this.getListAdapter().getItem(position);
    	    	
    	String codigo = o.toString();
    	
    	codigo = codigo.substring(codigo.indexOf("{line1=")+7, codigo.indexOf("-"));
    	
    	Intent i = new Intent(this, Acompanhamento_Vacinas.class);    
    	Acompanhamento_Vacinas._ID = this._ID;
    	Acompanhamento_Vacinas._CODVACINA = Integer.valueOf(codigo);
    	startActivity(i);
    	
    }
	
	@Override
	protected void onDestroy() {
		_ID = 0;
		Hash = "";
		super.onDestroy();
	}
	
}
