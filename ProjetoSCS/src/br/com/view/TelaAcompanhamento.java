package br.com.view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import br.com.control.Banco;
import br.com.control.Sessao;
import br.com.scs.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
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

@SuppressLint({ "ParserError", "SimpleDateFormat" })
public class TelaAcompanhamento extends ListActivity implements OnClickListener{
	
	Banco _bd = new Banco(this);
	
	private Button   btnFiltrar,btnVoltar;
	private EditText edtFiltro;
	
	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

	private SimpleAdapter sa;

	
	private String endereco, numero, complemento;
	
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
				
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
        
    }//Fim do mï¿½todo Main
    
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
    	    	
    	this.endereco = o.toString();
    	
    	this.endereco = this.endereco.substring(this.endereco.indexOf("{line1=")+7, this.endereco.indexOf(", Nº"));
    	
    	this.numero = o.toString();
    	
    	this.numero = this.numero.substring(this.numero.indexOf(", Nº")+4, this.numero.lastIndexOf(", l"));
    	
    	this.complemento = o.toString();
    	
    	this.complemento = this.complemento.substring(this.complemento.indexOf(":")+2, this.complemento.lastIndexOf(","));    	    
    	
    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    	dialog.setMessage("Escolha uma Opção:");
    	dialog.setIcon(R.drawable.iconscs);
    	dialog.setTitle("Acompanhamento");
    	dialog.setPositiveButton("Iniciar", new
				DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent i = new Intent(TelaAcompanhamento.this, Lista_Familiar_Acompanhamento.class);    
				    	Lista_Familiar_Acompanhamento.END = getEndereco();
				    	Lista_Familiar_Acompanhamento.NUM = getNumero(); 
				    	Lista_Familiar_Acompanhamento.COMPLEMENTO = getComplemento();
				    	
				    	InsereVisita(getEndereco(), getNumero(), getComplemento(), "N", "S");
				    	
				    	startActivity(i);
				    	
					}
    	});
    	
    	dialog.setNeutralButton("Casa Fechada", new
				DialogInterface.OnClickListener() {
				 
				public void onClick(DialogInterface di, int arg) {
					InsereVisita(getEndereco(), getNumero(), getComplemento(), "S", "N");
				}
		});
		dialog.show();
    	
    }
        
	@SuppressLint("SimpleDateFormat")
	private void InsereVisita(String _Endereco,String _Numero, String _Comp,
    						  String _FlCasaFechada, String _VisitaConf) {
    	
    	ContentValues c = new ContentValues();
    	SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
    	SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
    	try {
    		
    		c.clear();
        	c.put("DATA", formatador.format(new Date(System.currentTimeMillis())));
        	c.put("HORA", formataHora.format(new Date(System.currentTimeMillis())));
        	c.put("AGENTE", Sessao.SESSAO.getMatriculaUsuario(this));
        	c.put("ENDERECO", _Endereco);
        	c.put("NUMERO", _Numero);
        	c.put("COMPLEMENTO", _Comp);
        	c.put("FL_CASA_FECHADA", _FlCasaFechada);
        	c.put("VISITA_CONFIRMADA", _VisitaConf);

        	if ((_Endereco.trim().length() > 0)&&(_Numero.trim().length() > 0)) {
        		Banco banco = new Banco(TelaAcompanhamento.this);
        		banco.open();
        		banco.inserirRegistro("visita", c);
        		banco.fechaBanco();
        	}
        	
    	} catch(Exception e) {
    		System.out.println("Problema ao tentar inserir visita no banco!"+e.getMessage());
    	} finally {
    		formatador = null;
    		formataHora = null;
    		
    	}
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
