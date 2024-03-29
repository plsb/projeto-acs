/************************************************/
/** Projeto SCS - Sistema de Controle de Sa�de **/
/************ Desenvolvido por: *****************/
/********** Lucas de Souza Sales ****************/
/******* Karlos Kelvin Alves dos Santos *********/
/************* Pedro Luis Saraiva ***************/
/************************************************/
package br.com.view;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.control.Banco;
import br.com.control.Mensagem;
import br.com.control.ResidenteAux;
import br.com.control.Sessao;
import br.com.scs.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


public class TelaCadastroFamilia extends Activity implements OnClickListener{	
	
	public static int hanseniase, hipertensao, diabetes, tuberculose, gestante;
	
	public static int ID = 0;	
	static final int DATE_DIALOG_ID = 0;
	private java.util.Date data;
	private String HASH_FAMILIAR = "";
	Banco bd = null;
    Cursor c = null;
    

	Spinner     SpAlfabetizado, SpFreqEscola, SpRua, SpNumero, SpComplemento, SpMotivoObito, SpInfObito;
	CheckBox    Hanseniase, Hipertensao, Diabetes, Tuberculose, Gestante, Alcolismo, Chagas, Deficiencia, Malaria, Epilepsia;
	//CheckBox    ChMenor28Dias, Ch28Dias11Meses, ChMenor1Ano, ChMulher10_49, Ch10_14anos, Ch15_49anos, ChAdolescPorViolencia, ChOutrosObitos;
	EditText    EdtNome, EdtOcupacao, DtNascimento, EdtNomePai, EdtNomeMae; 
	RadioGroup  RdSexo;
	RadioButton RdbMasculino, RdbFeminino, RbMudouSIM, RbMudouNAO, RbObitoSim, RbObitoNAO;
	Button      btnVoltar,btnSalvar;
	
	ArrayList<String> Alfabetizado = new ArrayList<String>();
	ArrayList<String> FrqEscola    = new ArrayList<String>();
	ArrayList<String> Idade 	   = new ArrayList<String>();
	ArrayList<String> Ruas   	   = new ArrayList<String>();
	ArrayList<String> Num   	   = new ArrayList<String>();
	ArrayList<String> Comp  	   = new ArrayList<String>();
	ArrayList<String> MotivoObito  = new ArrayList<String>();
	ArrayList<String> InfObito     = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telacadastrofamilia);	
		
		bd = new Banco(this);
	
		SpAlfabetizado = (Spinner)    findViewById(R.cadastrofamilia.SpAlfabetizado);
		SpFreqEscola   = (Spinner)    findViewById(R.cadastrofamilia.SpFrequenEsc);
		SpRua          = (Spinner)    findViewById(R.cadastrofamilia.SpRua);
		SpNumero 	   = (Spinner)    findViewById(R.cadastrofamilia.SpNumero);
		SpComplemento  = (Spinner)    findViewById(R.cadastrofamilia.SpComplemento);
		SpMotivoObito  = (Spinner)    findViewById(R.cadastrofamilia.SpMotivoObito);
		SpInfObito     = (Spinner)    findViewById(R.cadastrofamilia.SpInformacoesObito);
		DtNascimento   = (EditText)   findViewById(R.cadastrofamilia.EdtDataNascimento);
		Hanseniase     = (CheckBox)   findViewById(R.cadastrofamilia.ChHanseniase);
		Hipertensao    = (CheckBox)   findViewById(R.cadastrofamilia.ChHipertensao);
		Diabetes       = (CheckBox)   findViewById(R.cadastrofamilia.ChDiabetes);
		Tuberculose    = (CheckBox)   findViewById(R.cadastrofamilia.ChTuberculose);
		Gestante       = (CheckBox)   findViewById(R.cadastrofamilia.ChGestante);
		Alcolismo 	   = (CheckBox)   findViewById(R.cadastrofamilia.ChAlcolismo);
		Chagas		   = (CheckBox)   findViewById(R.cadastrofamilia.ChChagas);
		Deficiencia    = (CheckBox)   findViewById(R.cadastrofamilia.ChDeficiencia);
		Malaria        = (CheckBox)   findViewById(R.cadastrofamilia.ChMalaria);
		Epilepsia      = (CheckBox)   findViewById(R.cadastrofamilia.ChEpilepsia);		
		EdtNome        = (EditText)    findViewById(R.cadastrofamilia.EdtNome);
		EdtOcupacao    = (EditText)    findViewById(R.cadastrofamilia.EdtOcupacao);
		EdtNomeMae     = (EditText)    findViewById(R.cadastrofamilia.EdtNomeMae);
		EdtNomePai     = (EditText)    findViewById(R.cadastrofamilia.EdtNomePai);
		RdSexo         = (RadioGroup)  findViewById(R.cadastrofamilia.RgSexo);
		RdbMasculino   = (RadioButton) findViewById(R.cadastrofamilia.RbMasculino);
		RdbFeminino    = (RadioButton) findViewById(R.cadastrofamilia.RbFeminino);
		RbObitoSim     = (RadioButton) findViewById(R.cadastrofamilia.RbObitoSIM);
		RbObitoNAO     = (RadioButton) findViewById(R.cadastrofamilia.RbObitoNAO);
		RbMudouSIM     = (RadioButton) findViewById(R.cadastrofamilia.RbMudouSIM);
		RbMudouNAO     = (RadioButton) findViewById(R.cadastrofamilia.RbMudouNAO);
		btnVoltar      = (Button)      findViewById(R.cadastrofamilia.btnVoltarFamiliar);
		btnSalvar      = (Button)      findViewById(R.cadastrofamilia.btnSalvarFamiliar);		
		btnVoltar.setOnClickListener(this);
		btnSalvar.setOnClickListener(this);
		DtNascimento.setOnClickListener(this);
		RdbMasculino.setOnClickListener(this);
		RdbFeminino.setOnClickListener(this);
		RbObitoSim.setOnClickListener(this);
		RbObitoNAO.setOnClickListener(this);
		
		OpcaoAlfabetizado("");
		OpcaoFreqEscola("");		
		setOpcoesEnderecos("");		
		
		PreencheCampos(String.valueOf(ID));

}	
	
	public void PreencheCampos(String pID){
		try{
			try{ 
				bd.open();
				c = bd.consulta("residente", new String[]{"*"},"_ID = "+pID, null, null, null, null, null);
				c.moveToFirst();
				if (c.getCount() > 0){
					HASH_FAMILIAR = c.getString(c.getColumnIndex("HASH")).toString();
					EdtNome.setText(c.getString(c.getColumnIndex("NOME")).toString());
					setOpcoesEnderecos(c.getString(c.getColumnIndex("COD_ENDERECO")).toString()+"-"+
									   c.getString(c.getColumnIndex("ENDERECO")).toString());
					setOpcoesNumeros(c.getString(c.getColumnIndex("NUMERO")).toString());					
					setOpcoesComplemento(c.getString(c.getColumnIndex("COMPLEMENTO")).toString());
					
					
					DtNascimento.setText(c.getString(c.getColumnIndex("DTNASCIMENTO")).toString());
					
					if (c.getString(c.getColumnIndex("SEXO")).toString().equals("M")){
						RdbMasculino.setChecked(true);
						RdbFeminino.setChecked(false);
					}else{
						RdbMasculino.setChecked(false);
						RdbFeminino.setChecked(true);
					}										
					
					if (c.getString(c.getColumnIndex("FL_FALECEU")).toString().equals("S")) {
						RbObitoSim.setChecked(true);
						setMotivosObito(c.getString(c.getColumnIndex("MOTIVO_OBITO")).toString());
						setInfObito(c.getString(c.getColumnIndex("INF_OBITO")).toString());
					} else {
						RbObitoNAO.setChecked(true);
					}
					
					if (c.getString(c.getColumnIndex("FL_MUDOU_SE")).toString().equals("S")) {
						RbMudouSIM.setChecked(true);
					} else {
						RbMudouNAO.setChecked(true);
					}
					
					if (c.getString(c.getColumnIndex("ALFABETIZADO")).toString().equals("S")){
						OpcaoAlfabetizado("Sim");
					}else{
						OpcaoAlfabetizado("Nao");
					}
					if (c.getString(c.getColumnIndex("FREQ_ESCOLA")).toString().equals("S")){
						OpcaoFreqEscola("Sim");
					}else{
						OpcaoFreqEscola("Nao");
					}
					
					EdtOcupacao.setText(c.getString(c.getColumnIndex("OCUPACAO")).toString());
					EdtNomeMae.setText(c.getString(c.getColumnIndex("NOME_MAE")).toString());
					EdtNomePai.setText(c.getString(c.getColumnIndex("NOME_PAI")).toString());
					
					if (c.getString(c.getColumnIndex("FL_HANSENIASE")).toString().equals("S")){
						Hanseniase.setChecked(true);
					}
					if (c.getString(c.getColumnIndex("FL_HIPERTENSAO")).toString().equals("S")){
						Hipertensao.setChecked(true);
					}
					if (c.getString(c.getColumnIndex("FL_GESTANTE")).toString().equals("S")){
						Gestante.setChecked(true);
					}
					if (c.getString(c.getColumnIndex("FL_TUBERCULOSE")).toString().equals("S")){
						Tuberculose.setChecked(true);
					}
					if (c.getString(c.getColumnIndex("FL_ALCOLISMO")).toString().equals("S")){
						Alcolismo.setChecked(true);
					}
					if (c.getString(c.getColumnIndex("FL_CHAGAS")).toString().equals("S")){
						Chagas.setChecked(true);
					}
					if (c.getString(c.getColumnIndex("FL_DEFICIENTE")).toString().equals("S")){
						Deficiencia.setChecked(true);
					}
					if (c.getString(c.getColumnIndex("FL_MALARIA")).toString().equals("S")){
						Malaria.setChecked(true);
					}
					if (c.getString(c.getColumnIndex("FL_DIABETE")).toString().equals("S")){
						Diabetes.setChecked(true);
					}
					if (c.getString(c.getColumnIndex("FL_EPILETICO")).toString().equals("S")){
						Epilepsia.setChecked(true);
					}
					
				}//Fim if
			}catch(Exception e){
				Log.i("Erro no m�todo PreencheCampos", e.getMessage());
			}
		}finally{			
			bd.fechaBanco();			
		}
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
			 if (EdtNome.getText().length() <= 0){
				 EdtNome.setError("Nome Precisa ser Preenchido!");
			 }else if(!(RdbMasculino.isChecked()) && !(RdbFeminino.isChecked())){
				 Mensagem.exibeMessagem(this, "Aten��o", "� necess�rio escolher uma op��o de sexo!");
			 }
			 else{
				InsereBD();
			 }
			}
		}
		return true;
	}
	
public void OpcaoAlfabetizado(String opcao){
		Alfabetizado.clear();
		if (opcao.length()>0){
			Alfabetizado.add(opcao);
		}
		Alfabetizado.add("Sim");
		Alfabetizado.add("Nao");
		PreencheSpinner(SpAlfabetizado, Alfabetizado);			
	}

public void OpcaoFreqEscola(String opcao){
	FrqEscola.clear();
	if (opcao.length()>0){
		FrqEscola.add(opcao);
	}
	FrqEscola.add("Sim");
	FrqEscola.add("Nao");
	PreencheSpinner(SpFreqEscola, FrqEscola);
}

public void setMotivosObito(String pMotivo) {
	MotivoObito.clear();
	if (pMotivo.length() > 0) {
		MotivoObito.add(pMotivo);
	}
	MotivoObito.add("Por Diarreia");
	MotivoObito.add("Por Infeccao Respiratoria");
	MotivoObito.add("Por Outras Causas");
	PreencheSpinner(SpMotivoObito, MotivoObito);
}

public void setInfObito(String pInfObito) {
	InfObito.clear();
	if (pInfObito.length() > 0) {
		InfObito.add(pInfObito);
	}
	InfObito.add("Menores de 28 dias");
	InfObito.add("De 28 dias a 11 meses e 29 dias");
	InfObito.add("Menores de 1 ano");
	InfObito.add("Mulheres de 10 a 49 anos");
	InfObito.add("De 10 a 14 anos");
	InfObito.add("De 15 a 49 anos");
	InfObito.add("Outros �bitos");
	InfObito.add("Adolescente (10-19 anos) por viol�ncia");
	PreencheSpinner(SpInfObito, InfObito);
}

private void setOpcoesEnderecos(String pEnd) {
	Ruas.clear();
	if (pEnd.length()>0){
		Ruas.add(pEnd);
	}
	//Banco bd = null;
	Cursor csr = null;
	String codUser = Sessao.SESSAO.getMatriculaUsuario(this);
	try{
		try{
			//bd = new Banco(this);
			bd.open();
			if (codUser.equals("0000"))
				csr = bd.consulta("ruas", new String[]{"*"}, null, null, null, null, null, null);
			else
				csr = bd.consulta("ruas", new String[]{"*"}, "USU_VINCULADO = ? ", new String[] {codUser}, null, null, null, null);
			csr.moveToFirst();
			if (csr.getCount()>0){
				for (int i = 0;i < csr.getCount(); i++){
					Ruas.add(csr.getString(csr.getColumnIndex("COD_RET")).toString()+"-"+csr.getString(csr.getColumnIndex("DESCRICAO")).toString());
					csr.moveToNext();
				}
			}			
			PreencheSpinner(SpRua, Ruas);
			csr.close();
		}catch(Exception e){
			Log.i("M�todo SetOpcoesEndereco", e.getMessage());
		}
	}finally{		
		bd.fechaBanco();		
	}
}

private void setOpcoesNumeros(String pNumero){
	Num.clear();
	if (pNumero.length() > 0){
		Num.add(pNumero);
	}
	
	Cursor csr = null;
	
	if (SpRua.getItemAtPosition(SpRua.getSelectedItemPosition()).toString().trim().length() > 0){
		try{
			try{
				bd.open();
				csr = bd.consulta("residencia", new String[]{"distinct *"}, "ENDERECO = '"+SpRua.getItemAtPosition(SpRua.getSelectedItemPosition()).toString().substring(
																				  SpRua.getItemAtPosition(SpRua.getSelectedItemPosition()).toString().indexOf("-")+1)+"' ", 
								  null, null, null, null, null);
				csr.moveToFirst();
				if (csr.getCount() > 0){
					for (int i = 0;i < csr.getCount(); i++){
						Num.add(csr.getString(csr.getColumnIndex("NUMERO")).toString());
						csr.moveToNext();
					}
				}
				csr.close();
				PreencheSpinner(SpNumero, Num);
			}catch(Exception e){
				Log.i("M�todo SetOpcoesNumeros", e.getMessage());
			}
		}finally{
			bd.fechaBanco();
		}
	}
}

private void setOpcoesComplemento(String pComplemento){
	
	Comp.clear();
	
	if (pComplemento.trim().length() > 0){
		Comp.add(pComplemento);
	}
	
	Cursor csr = null;
	//Comp.add("");
	
	if (SpRua.getItemAtPosition(SpRua.getSelectedItemPosition()).toString().trim().length() > 0){
		try{
			try{
				bd.open();
				csr = bd.consulta("residencia", new String[]{"*"}, "ENDERECO = '"+SpRua.getItemAtPosition(SpRua.getSelectedItemPosition()).toString().substring(
																				  SpRua.getItemAtPosition(SpRua.getSelectedItemPosition()).toString().indexOf("-")+1)+"' AND "+
																				  "NUMERO = '"+SpNumero.getItemAtPosition(SpNumero.getSelectedItemPosition()).toString()+"' ", 
								  null, null, null, null, null);
				csr.moveToFirst();
				if (csr.getCount() > 0){
					for (int i = 0;i < csr.getCount(); i++){						
						Comp.add(csr.getString(csr.getColumnIndex("COMPLEMENTO")).toString());
						csr.moveToNext();
					}
				}
				csr.close();
				PreencheSpinner(SpComplemento, Comp);
			}catch(Exception e){
				Log.i("M�todo SetOpcoesComplemento", e.getMessage());
			}
		}finally{
			bd.fechaBanco();
		}
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
	if ((_dia <= dia)&&(_mes <= mes)){
		return (ano - _ano);
	}else{
		return ((ano - _ano) -1);
	}
}


public void PreencheSpinner(final Spinner s,ArrayList<String> a){
	
	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, a);		
	ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;		
	spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
	s.setAdapter(spinnerArrayAdapter);		
	
	s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		
		public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
			if ((s == SpRua) && (ID == 0)) {
				setOpcoesNumeros("");
			}
			if ((s == SpNumero) && (ID == 0)) {
				setOpcoesComplemento("");
			}
			if (s == SpInfObito) {
				
				if ((SpInfObito.getItemAtPosition(posicao).toString().equals("Menores de 28 dias"))||
					(SpInfObito.getItemAtPosition(posicao).toString().equals("De 28 dias a 11 meses e 29 dias"))||
					(SpInfObito.getItemAtPosition(posicao).toString().equals("Menores de 1 ano"))) {						
					SpMotivoObito.setClickable(true);
					setMotivosObito("");					
				} else {
					SpMotivoObito.setClickable(false);
					setMotivosObito(" ");				
				}
				
			}
		} 

		public void onNothingSelected(AdapterView<?> parent) {
			
		}
		
	});
}


public void InsereBD(){
	try{
		ResidenteAux r = new ResidenteAux();
		r.Limpar();
		r.NOME         = EdtNome.getText().toString();
		r.ENDERECO     = SpRua.getItemAtPosition(SpRua.getSelectedItemPosition()).toString();
		r.NUMERO       = SpNumero.getItemAtPosition(SpNumero.getSelectedItemPosition()).toString(); 
		r.COMPLEMENTO  = (!SpComplemento.isSelected()?"":SpComplemento.getItemAtPosition(SpComplemento.getSelectedItemPosition()).toString());
		r.DTNASCIMENTO = DtNascimento.getText().toString().trim();//String.valueOf(DtNascimento.getDayOfMonth())+"/"+String.valueOf(DtNascimento.getMonth()+1)+"/"+String.valueOf(DtNascimento.getYear());
		r.FREQ_ESCOLA  = SpFreqEscola.getItemAtPosition(SpFreqEscola.getSelectedItemPosition()).toString().substring(0, 1);
		r.ALFABETIZADO = SpAlfabetizado.getItemAtPosition(SpAlfabetizado.getSelectedItemPosition()).toString().substring(0, 1);
		r.OCUPACAO     = EdtOcupacao.getText().toString();
		r.NOME_MAE     = EdtNomeMae.getText().toString();
		r.NOME_PAI     = EdtNomePai.getText().toString();
		
		
		if (RbObitoSim.isChecked()) {
			r.FL_OBITO = "S";
			r.MOTIVO_OBITO = SpMotivoObito.getItemAtPosition(SpMotivoObito.getSelectedItemPosition()).toString();
			r.INF_OBITO    = SpInfObito.getItemAtPosition(SpInfObito.getSelectedItemPosition()).toString();
		} else if (RbObitoNAO.isChecked()) {
			r.FL_OBITO = "N";
		} else {
			r.FL_OBITO = "N";
		}
		
		if (RbMudouSIM.isChecked()) {
			r.FL_MUDOU_SE = "S";
		} else if (RbMudouNAO.isChecked()) {
			r.FL_MUDOU_SE = "N";
		} else {
			r.FL_MUDOU_SE = "N";
		}
		
		if (Hanseniase.isChecked())
			r.FL_HANSENIASE = "S";
		else
			r.FL_HANSENIASE = "N";
		if (Hipertensao.isChecked())
			r.FL_HIPERTENSAO = "S";
		else
			r.FL_HIPERTENSAO = "N";
		if (Gestante.isChecked())
			r.FL_GESTANTE = "S";
		else
			r.FL_GESTANTE = "N";
		if (Tuberculose.isChecked())
			r.FL_TUBERCULOSE = "S";
		else
			r.FL_TUBERCULOSE = "N";
		if (Alcolismo.isChecked())
			r.FL_ALCOLISMO = "S";
		else
			r.FL_ALCOLISMO = "N";
		if (Chagas.isChecked())
			r.FL_CHAGAS = "S";
		else	
			r.FL_CHAGAS = "N";
		if (Deficiencia.isChecked())
			r.FL_DEFICIENTE = "S";
		else	
			r.FL_DEFICIENTE = "N";
		if (Malaria.isChecked())
			r.FL_MALARIA = "S";
		else	
			r.FL_MALARIA = "N";
		if (Diabetes.isChecked())
			r.FL_DIABETE = "S";
		else
			r.FL_DIABETE = "N";
		if (Epilepsia.isChecked())
			r.FL_EPILETICO = "S";
		else
			r.FL_EPILETICO = "N";				
		if (RdbMasculino.isChecked())
			r.SEXO = "M";
		else if (RdbFeminino.isChecked())
			r.SEXO = "F";	
		
		if (this.ID == 0){
			TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			SimpleDateFormat formatador = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss"); 
			r.HASH = Mensagem.md5(telephonyManager.getDeviceId()+r.NOME+formatador.format(new Date(System.currentTimeMillis())));
			if (r.Inserir(TelaCadastroFamilia.this)==true){
				Mensagem.exibeMessagem(this, "SCS", "Sucesso ao Gravar!",2000);
				new Handler().postDelayed(new Runnable() {		
					public void run() {
						finish();
					}
				}, 2000);
			}else{
				Mensagem.exibeMessagem(this, "SCS", "Erro ao Gravar!");
			}
		}else{
			r.HASH = HASH_FAMILIAR;
			if (r.Atualizar(TelaCadastroFamilia.this, this.ID) == true){
				Mensagem.exibeMessagem(this, "SCS", "Sucesso ao Atualizar!",2000);
				ClearID();
				new Handler().postDelayed(new Runnable() {		
					public void run() {
						finish();
					}
				}, 2000);
			}else{
				Mensagem.exibeMessagem(this, "SCS", "Erro ao Gravar!");
			}
		}
	
	}catch(Exception e){
		Toast.makeText(this, "Erro no m�todo: Prepara Inser��o"+e.getMessage(), Toast.LENGTH_LONG).show();
		Log.i("Erro no m�todo: Prepara Inser��o", e.getMessage());
	}
}

	public void ClearID(){
		this.ID = 0;
		this.HASH_FAMILIAR = "";
		bd.fechaBanco();
	}     

	@Override
	protected void onDestroy() {
		if (this.ID > 0){
			ClearID();
		}
		super.onDestroy();
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
	            DtNascimento.setText(formatador.format(data));
	        }
	    };
	 

	public void onClick(View v) {
		if (v == btnVoltar) {
			finish();
		}
		if (v == btnSalvar) {
			InsereBD();
		}
		if (v == DtNascimento) {			
			showDialog(DATE_DIALOG_ID);
		}
		if (v == RdbMasculino) {
			Gestante.setEnabled(false);
		}
		if (v == RdbFeminino) {
			Gestante.setEnabled(true);
		}
		if (v == RbObitoSim) {
			SpInfObito.setClickable(true);	
			setInfObito("");
		}
		if (v == RbObitoNAO) {
			SpInfObito.setClickable(false);
			SpMotivoObito.setClickable(false);
			setMotivosObito(" ");
			setInfObito(" ");			
		}
		
		
	}


}

