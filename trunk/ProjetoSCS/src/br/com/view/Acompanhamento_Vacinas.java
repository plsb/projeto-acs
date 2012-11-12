package br.com.view;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.control.Banco;
import br.com.control.Mensagem;
import br.com.control.VacinaAux;
import br.com.scs.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


public class Acompanhamento_Vacinas extends Activity implements OnClickListener {
	
	private EditText EdtFamiliar,EdtLote;
	private Spinner  SpTipoVacina, SpDoseAplicada;
	private EditText DtDataAPlicacao;
	private CheckBox ChkAplicado;	
	private String 	 Hash = "";
	
	private java.util.Date data = null;
	static final int DATE_DIALOG_ID = 0;
	
	public static int _ID = 0;
	public static int IdadeFamiliar = 0;
	public static boolean FalimiarGestante = false;
		
	
	ArrayList<String> TipoVacina   = new ArrayList<String>();
	ArrayList<String> DoseAplicada = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telavacina);
		
		InicializaObjetos();
		
		InformacoesFamiliar();
		
		PreencheTipoVacina(IdadeFamiliar, FalimiarGestante);
		
	}//Fim onCreate
	
public void InformacoesFamiliar(){
	try{
		Banco bd = new Banco(this);
		bd.open();		
		Cursor cAux = bd.consulta("residente", new String[]{"*"}, "_ID = ?", new String[]{String.valueOf(_ID).trim()}, null, null, null, null);
		cAux.moveToFirst();
		if (cAux.getCount() > 0){
			EdtFamiliar.setText(cAux.getString(cAux.getColumnIndex("NOME")).toString());
			Hash = cAux.getString(cAux.getColumnIndex("HASH")).toString();	
		}//Fim if
		
		cAux.close();
		bd.fechaBanco();

	  }catch (Exception e) {  
		System.out.println(e.getMessage());
    }
	
}
	
	public void InicializaObjetos(){
		
		EdtFamiliar     = (EditText)   findViewById(R.telavacina.EdtPessoa);
		EdtLote         = (EditText)   findViewById(R.telavacina.EdtLoteVacina);
		SpTipoVacina    = (Spinner)    findViewById(R.telavacina.SpTipoVacina);		
		SpDoseAplicada  = (Spinner)    findViewById(R.telavacina.SpDose);
		DtDataAPlicacao = (EditText)   findViewById(R.telavacina.DtDataAplicacao);
		ChkAplicado     = (CheckBox)   findViewById(R.telavacina.ChkFLAplicada);
		DtDataAPlicacao.setOnClickListener(this);
		
	}//Fim InicializaObjetos
	
	public void PreencheTipoVacina(int _Idade,boolean _Gestante){
		if(_Gestante == true){
			TipoVacina.clear();
			TipoVacina.add("HEPATITE B");
			TipoVacina.add("DUPLA ADULTO");
			TipoVacina.add("INFLUENZA");
		}else if (_Idade <= 10){
			TipoVacina.clear();
			TipoVacina.add("BCG");
			TipoVacina.add("HEPATITE B");
			TipoVacina.add("PENTAVALENTE");
			TipoVacina.add("VOPI");
			TipoVacina.add("VORH");
			TipoVacina.add("PNEUMOCOCICA");
			TipoVacina.add("MENINGOCOCICA");
			TipoVacina.add("TRIPLICE VIRAL");
			TipoVacina.add("TRIPLICE BACTERIANA");			
		}else if ((_Idade >10)&&(_Idade < 20)){
			TipoVacina.clear();
			TipoVacina.add("HEPATITE B");
			TipoVacina.add("DUPLA ADULTO");
			TipoVacina.add("FEBRE AMARELA");
			TipoVacina.add("TRIPLICE VIRAL");			
		}else if (_Idade > 20){
			TipoVacina.clear();
			TipoVacina.add("HEPATITE B");
			TipoVacina.add("DUPLA ADULTO");
			TipoVacina.add("FEBRE AMARELA");
			TipoVacina.add("TRIPLICE VIRAL");
			TipoVacina.add("INFLUENZA SAZONAL");
			TipoVacina.add("MENINGOCOCICA");
			TipoVacina.add("PNEUMOCOCICA 23");
		}
		PreencheSpinner(SpTipoVacina, TipoVacina);
	}//Fim do PreecheTipoVacina
	
	public void PreecheDoseAplicada(String _TipoVacina,int _Idade,boolean _Gestante){
		if ((_Gestante == true)&&(_TipoVacina.equals("HEPATITE B"))){
			DoseAplicada.clear();
			DoseAplicada.add("1");
			DoseAplicada.add("2");
			DoseAplicada.add("3");
		}else if ((_Gestante == true)&&(_TipoVacina.equals("DUPLA ADULTO"))){
			DoseAplicada.clear();
			DoseAplicada.add("1");
			DoseAplicada.add("2");
			DoseAplicada.add("3");
			DoseAplicada.add("R-REFORÇO");
		}else if ((_Gestante == true)&&(_TipoVacina.equals("INFLUENZA"))){
			DoseAplicada.clear();
			DoseAplicada.add("A-DOSE ANUAL");
		}else if ((IdadeFamiliar <= 10 )&&(_TipoVacina.equals("BCG"))){
			DoseAplicada.clear();
			DoseAplicada.add("U-DOSE UNICA");
		}else if ((IdadeFamiliar <= 10 )&&(_TipoVacina.equals("HEPATITE B"))){
			DoseAplicada.clear();
			DoseAplicada.add("1");
		}else if ((IdadeFamiliar <= 10 )&&(_TipoVacina.equals("PENTAVALENTE"))){
			DoseAplicada.clear();
			DoseAplicada.add("1");
			DoseAplicada.add("2");
			DoseAplicada.add("3");
		}else if ((IdadeFamiliar <= 10 )&&(_TipoVacina.equals("VOPI")||(_TipoVacina.equals("PNEUMOCOCICA")))){
			DoseAplicada.clear();
			DoseAplicada.add("1");
			DoseAplicada.add("2");
			DoseAplicada.add("3");
			DoseAplicada.add("R-REFORÇO");
		}else if ((IdadeFamiliar <= 10 )&&(_TipoVacina.equals("VORH")
				   ||(_TipoVacina.equals("TRIPLICE VIRAL")))){
			DoseAplicada.clear();
			DoseAplicada.add("1");
			DoseAplicada.add("2");
		}else if ((IdadeFamiliar <= 10 )&&(_TipoVacina.equals("MENINGOCOCICA"))){
			DoseAplicada.clear();
			DoseAplicada.add("1");
			DoseAplicada.add("2");
			DoseAplicada.add("R-REFORÇO");
		}else if ((IdadeFamiliar <= 10 )&&(_TipoVacina.equals("TRIPLICE BACTERIANA"))){
			DoseAplicada.clear();
			DoseAplicada.add("1R-REFORÇO");
			DoseAplicada.add("2R-REFORÇO");
		/**************************** A D O L E S C E N T E ******************************/	
		}else if ((IdadeFamiliar >= 11)&&(IdadeFamiliar <= 19)&&(_TipoVacina.equals("HEPATITE B"))){
			DoseAplicada.clear();
			DoseAplicada.add("1");
			DoseAplicada.add("2");
			DoseAplicada.add("3");
		}else if ((IdadeFamiliar >= 11)&&(IdadeFamiliar <= 19)&&(_TipoVacina.equals("DUPLA ADULTO")
				  ||(_TipoVacina.equals("FEBRE AMARELA")))){
			DoseAplicada.clear();
			DoseAplicada.add("Z-DEZ ANOS");
		}else if ((IdadeFamiliar >= 11)&&(IdadeFamiliar <= 19)&&(_TipoVacina.equals("TRIPLICE VIRAL"))){
			DoseAplicada.clear();
			DoseAplicada.add("1");
			DoseAplicada.add("2");
		/************************** A D U L T O  /  I D O S O ****************************/	
		}else if ((IdadeFamiliar >=20)&&(_TipoVacina.equals("HEPATITE B"))){
			DoseAplicada.clear();
			DoseAplicada.add("1");
			DoseAplicada.add("2");
			DoseAplicada.add("3");
		}else if ((IdadeFamiliar >=20)&&(_TipoVacina.equals("DUPLA ADULTO")||(_TipoVacina.equals("FEBRE AMARELA")))){
			DoseAplicada.clear();
			DoseAplicada.add("Z-DEZ ANOS");
		}else if ((IdadeFamiliar >=20)&&(_TipoVacina.equals("TRIPLICE VIRAL")||(_TipoVacina.equals("PNEUMOCOCICA 23")))){
			DoseAplicada.clear();
			DoseAplicada.add("U-DOSE UNICA");
		}else if ((IdadeFamiliar >=20)&&(_TipoVacina.equals("INFLUENZA SAZONAL"))){
			DoseAplicada.clear();
			DoseAplicada.add("A-DOSE ANUAL");
		}else if ((IdadeFamiliar >=20)&&(_TipoVacina.equals("MENINGOCOCICA"))){
			DoseAplicada.clear();
			DoseAplicada.add("1");
			DoseAplicada.add("2");
			DoseAplicada.add("R");
		}
		
		PreencheSpinner(SpDoseAplicada, DoseAplicada);
	}

	public void onClick(View v) {			
		if (v == DtDataAPlicacao){
			showDialog(DATE_DIALOG_ID);
		}
	}//Fim onClick
	
	public boolean CamposValidos(){
		
		if (EdtLote.getText().toString().trim().length() <= 0){
			EdtLote.setError("O campo lote deve ser preenchido.");
			return false;
		}else{
			return true;
		}		
	}
	
	public void Inserir(){
		if (CamposValidos() == true){
			VacinaAux va = new VacinaAux();
			if (SpDoseAplicada.getItemAtPosition(SpDoseAplicada.getSelectedItemPosition()).toString().trim().length() > 1){
				va.DS_VACINA = SpDoseAplicada.getItemAtPosition(SpDoseAplicada.getSelectedItemPosition()).toString().substring(0,SpDoseAplicada.getItemAtPosition(SpDoseAplicada.getSelectedItemPosition()).toString().indexOf("-"));
			}else{
				va.DS_VACINA = SpDoseAplicada.getItemAtPosition(SpDoseAplicada.getSelectedItemPosition()).toString();
			}
				
			va.TP_VACINA   	= SpTipoVacina.getItemAtPosition(SpTipoVacina.getSelectedItemPosition()).toString();
			va.DT_APLICACAO = DtDataAPlicacao.getText().toString().trim();
			va.HASH         = Hash;
			va.LOTE         = EdtLote.getText().toString();
			
			if (FalimiarGestante == true){
				va.TIPO = "G";
			}else if (IdadeFamiliar <=10){
				va.TIPO = "C";
			}else if ((IdadeFamiliar >= 11)&&(IdadeFamiliar <= 19)){
				va.TIPO = "A";
			}else if (IdadeFamiliar >= 20){
				va.TIPO = "D";
			}
			
			if (ChkAplicado.isChecked())
				va.FL_APLICADA = "S";
			else
				va.FL_APLICADA = "N";
			
			if (va.Inserir(this) == true){
				Mensagem.exibeMessagem(this, "SCS", "Sucesso ao Gravar.", 2000);
				new Handler().postDelayed(new Runnable() {		
					public void run() {
						finish();
					}
				}, 2000);
			}
			
		}
	}
	
    public void PreencheSpinner(final Spinner s,ArrayList<String> a){
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, a);		
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;		
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		s.setAdapter(spinnerArrayAdapter);		
		
		s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
								
				if (s == SpTipoVacina){
					PreecheDoseAplicada(SpTipoVacina.getItemAtPosition(posicao).toString(), IdadeFamiliar, FalimiarGestante);
				}
			}

			public void onNothingSelected(AdapterView<?> parent) {
				
			}
			
		});
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
    	FalimiarGestante = false;
    	IdadeFamiliar = 0;
    	Hash = "";
    	super.onDestroy();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		
        getMenuInflater().inflate(R.menu.cadastrofamilia, menu);        
        return true;
    }	
	
	@SuppressLint({ "ParserError", "ParserError" })
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			
		case R.MenuTelaFamilia.menu_gravar :{			
				Inserir();
			}
		}
		return true;
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
            DtDataAPlicacao.setText(formatador.format(data));
        }
    };

}
