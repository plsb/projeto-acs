/************************************************/
/** Projeto SCS - Sistema de Controle de Saúde **/
/************ Desenvolvido por: *****************/
/********** Lucas de Souza Sales ****************/
/******* Karlos Kelvin Alves dos Santos *********/
/************* Pedro Luis Saraiva ***************/
/************************************************/
package br.com.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import br.com.control.Banco;
import br.com.control.Mensagem;
import br.com.scs.R;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Lista_Agendamentos_Marcados extends ListActivity implements OnClickListener {
	
	private String _Hash = "";
	
	public static String ID_Familiar = "";
	
	private Banco _bd = new Banco(this);
	private SimpleAdapter sa;
	
	private Button   btnFiltrar,btnVoltar;
	private EditText edtFiltro;
	
	static final int DATE_DIALOG_ID = 0;
	private java.util.Date data;
	
	private ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_agendamentos_marcados);
		
		edtFiltro  = (EditText) findViewById(R.agend_marcados.edtFiltro);
        btnFiltrar = (Button) findViewById(R.agend_marcados.btnFiltrar);        
        btnVoltar  = (Button) findViewById(R.agend_marcados.btnVoltarLista);
        btnVoltar.setOnClickListener(this);
        btnFiltrar.setOnClickListener(this);  
        edtFiltro.setOnClickListener(this);
        
        _Hash = getHashFalimiar(ID_Familiar);
        
        ListaAgendamentos(false);
                
	}
	
	public void ListaAgendamentos(boolean usaFiltro){
    	HashMap<String,String> item;
        _bd.open();
        try{
        	list.clear();
        	Cursor _cursor = null;
        	if (!usaFiltro){
        		_cursor = _bd.consulta("agendamento a join profissional p on p.cod_ret = a.profissional", new String[] { "*" },"a.hash = '"+_Hash+"' ",null,null,null,null,null);
        	}else{
        		_cursor = _bd.consulta("agendamento a join profissional p on p.cod_ret = a.profissional", new String[] { "*" },"a.DT_AGENDAMENTO = '"+edtFiltro.getText().toString()+"' and a.hash = '"+_Hash+"' ",null,null,null,null,null);
        	}
        	item = new HashMap<String,String>();
        	_cursor.moveToFirst(); 
        	if (_cursor.getCount() > 0){
	        	do{	
	        	  item = new HashMap<String,String>();
	        	  item.put( "line1", "Data: "+_cursor.getString(_cursor.getColumnIndex("a.DT_AGENDAMENTO")).toString()+", Hora:"+ 
	        			  			 _cursor.getString(_cursor.getColumnIndex("a.HR_AGENDAMENTO")).toString());
	        	  item.put( "line2", "Médico: "+_cursor.getString(_cursor.getColumnIndex("p.NOME")).toString()+", Espec.: "+	        			  			 
	        			  			 _cursor.getString(_cursor.getColumnIndex("p.ESPECIALIDADE")).toString());
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
	

	public void onClick(View v) {
		if (v == btnVoltar) {
			finish();
		}
		if (v == btnFiltrar) {
			ListaAgendamentos(true);
		}
		if (v == edtFiltro) {
			showDialog(DATE_DIALOG_ID);
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

}
