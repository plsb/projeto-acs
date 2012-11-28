package br.com.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import br.com.control.Banco;
import br.com.scs.R;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Lista_Casas_Fechadas extends ListActivity implements OnClickListener {

	Banco _bd = new Banco(this);
	
	private Button  btnVoltar, btnFiltrar;
	private EditText edtFiltro;
	static final int DATE_DIALOG_ID = 0;
	private java.util.Date data;
	
	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

	private SimpleAdapter sa;
	
	private String endereco, numero, complemento;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_casas_fechadas);
        
        edtFiltro  = (EditText) findViewById(R.listacasasfechadas.edtFiltro);
        btnFiltrar = (Button)   findViewById(R.listacasasfechadas.btnFiltrar);
        btnVoltar  = (Button)   findViewById(R.listacasasfechadas.btnVoltarLista);
        btnVoltar.setOnClickListener(this);
        btnFiltrar.setOnClickListener(this);
        edtFiltro.setOnClickListener(this);
         
        ListarResidencias(false);
        
    }//Fim do mï¿½todo Main
	
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
    	    	
    	
    }
	
	@Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendario = Calendar.getInstance();
         
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
         
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this, mDateSetListener, ano, mes,
                    dia);
        }
        return null;
    }
 
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {
           String data1 = String.valueOf(dayOfMonth) + "/"
                    + String.valueOf(monthOfYear+1) + "/" + String.valueOf(year);
            SimpleDateFormat formatador =  new SimpleDateFormat("dd/MM/yyyy");
            try {
            	 data = formatador.parse(data1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());;
			}
            edtFiltro.setText(formatador.format(data));
        }
    };
 
	
	public void ListarResidencias(boolean usaFiltro){
	    	HashMap<String,String> item;
	        _bd.open();
	        try{
	        	list.clear();
	        	Cursor _cursor = null;
	        	if (!usaFiltro){
	        		_cursor = _bd.consulta("visita", new String[] { "*" },"fl_casa_fechada = 'S'",null,null,null,null,null);
	        	}else{
	        		_cursor = _bd.consulta("visita", new String[] { "*" },"DATA = '"+edtFiltro.getText().toString()+"' and fl_casa_fechada = 'S' ",null,null,null,null,null);
	        	}
	        	item = new HashMap<String,String>();
	        	_cursor.moveToFirst(); 
	        	if (_cursor.getCount() > 0){
		        	do{	
		        	  item = new HashMap<String,String>();
		        	  item.put( "line1", _cursor.getString(_cursor.getColumnIndex("ENDERECO")).toString()+", Nº "+
	 			  			 _cursor.getString(_cursor.getColumnIndex("NUMERO")).toString());
		        	  item.put( "line2", "Complemento: "+_cursor.getString(_cursor.getColumnIndex("COMPLEMENTO")).toString()+", DATA: "+
	 			  			 _cursor.getString(_cursor.getColumnIndex("DATA")).toString());	        	  
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
	
	public void onClick(View v) {
		if (v == btnVoltar) {
			finish();
		}
		if (v == edtFiltro) {			
			showDialog(DATE_DIALOG_ID);
		}
		if(v == btnFiltrar) {
			ListarResidencias(true);
		}
	}

}
