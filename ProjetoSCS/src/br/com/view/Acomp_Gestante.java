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

import br.com.control.Banco;
import br.com.scs.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class Acomp_Gestante extends Activity implements OnClickListener {
	
	Banco _bd = new Banco(this);
	Cursor _cGestante = null;
	
	private static int IdTransacao = 0;

	public static CheckBox    ChGGestacoes, ChGAborto, ChGIdade36, ChGIdade20, ChGSangue, ChGEdema, ChGDiabetes, 
							  ChGPressao,ChkGNV,ChkGNM,ChkGAB, ChAcompFinal;
	public static EditText    EdtGEn, EdtGObs, EdtDtUltimaRegra, EdtDtProvavelParto, EdtDtPreNatal, EdtDtUltimaConsulta, EdtDtConsPuerbio;
	public static RadioGroup  RdgEstadoNutri;
	public static RadioButton RbNutrida,RbDesnutrida;
	public static Spinner     SpMesGestacao;
	public static TextView    TxtProvavelParto;
	
	public static String DtAcompanhamento = null;
	public static String Hash = null;  
	
	private ArrayList<String> mesGestacao = new ArrayList<String>();
	
	private java.util.Date data;
	
	private int DATE_DIALOG_ID = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.acomp_gestante);
		
		InicializaObjetos();
		
		if (DtAcompanhamento != null) {
			PreencheCampos();
		} else {
			setMesGestacao("");
			setIdTransacao(0);
		}
		
	}
	
	public static int getIdTransacao() {
		return IdTransacao;
	}

	public void setIdTransacao(int idTransacao) {
		IdTransacao = idTransacao;
	}
	
	public void PreencheCampos() {
		try {			
			_bd.open();
			_cGestante = _bd.consulta("gestacao", new String[]{"*"}, "hash = '"+Hash+"' and dt_visita = '"+DtAcompanhamento+"' ", null, null, null, null, "1");
			_cGestante.moveToFirst();
			if (_cGestante.getCount() > 0){
				setIdTransacao(Integer.valueOf(_cGestante.getString(_cGestante.getColumnIndex("_ID")).toString()));
				setMesGestacao(_cGestante.getString(_cGestante.getColumnIndex("MES_GESTACAO")).toString());
				EdtDtPreNatal.setText(_cGestante.getString(_cGestante.getColumnIndex("DT_PRE_NATAL")).toString());
				EdtDtProvavelParto.setText(_cGestante.getString(_cGestante.getColumnIndex("DT_PROVAVEL_PARTO")).toString());
				EdtDtUltimaRegra.setText(_cGestante.getString(_cGestante.getColumnIndex("DT_ULTIMA_REGRA")).toString());
				EdtDtUltimaConsulta.setText(_cGestante.getString(_cGestante.getColumnIndex("DT_ULTIMA_CONSULTA")).toString());
				EdtGObs.setText(_cGestante.getString(_cGestante.getColumnIndex("OBSERVACAO")).toString());
				
				//Estado Nutricional
	    		if (_cGestante.getString(_cGestante.getColumnIndex("EST_NUTRICIONAL")).toString().trim().equals("N"))
	    			RbNutrida.setChecked(true);
	    		else
	    			RbDesnutrida.setChecked(true);
	    		
	    		if (_cGestante.getString(_cGestante.getColumnIndex("FATORES_RISCO")).substring(0, 1).toString().trim().equals("S"))
	    			ChGGestacoes.setChecked(true);
	    		else
	    			ChGGestacoes.setChecked(false);
	    		
	    		if (_cGestante.getString(_cGestante.getColumnIndex("FATORES_RISCO")).substring(1, 2).toString().trim().equals("S"))		    		
	    			ChGIdade36.setChecked(true);
	    		else
	    			ChGIdade36.setChecked(false);
	    		
	    		if (_cGestante.getString(_cGestante.getColumnIndex("FATORES_RISCO")).substring(2, 3).toString().trim().equals("S"))
	    			ChGSangue.setChecked(true);
	    		else
	    			ChGSangue.setChecked(false);
	    		
	    		if (_cGestante.getString(_cGestante.getColumnIndex("FATORES_RISCO")).substring(3, 4).toString().trim().equals("S"))
	    			ChGDiabetes.setChecked(true);
	    		else
	    			ChGDiabetes.setChecked(false);
	    		
	    		if (_cGestante.getString(_cGestante.getColumnIndex("FATORES_RISCO")).substring(4, 5).toString().trim().equals("S"))
	    			ChGAborto.setChecked(true);
	    		else
	    			ChGAborto.setChecked(false);
	    		
	    		if (_cGestante.getString(_cGestante.getColumnIndex("FATORES_RISCO")).substring(5, 6).toString().trim().equals("S"))
	    			ChGIdade20.setChecked(true);
	    		else
	    			ChGIdade20.setChecked(false);
	    		
	    		if (_cGestante.getString(_cGestante.getColumnIndex("FATORES_RISCO")).substring(6, 7).toString().trim().equals("S"))
	    			ChGEdema.setChecked(true); 		    		 
	    		else
	    			ChGEdema.setChecked(false);
	    		
	    		if (_cGestante.getString(_cGestante.getColumnIndex("FATORES_RISCO")).substring(7, 8).toString().trim().equals("S"))
	    			ChGPressao.setChecked(true);
	    		else
	    			ChGPressao.setChecked(false);
	    		
	    		if (_cGestante.getString(_cGestante.getColumnIndex("RESULTADO_GESTACAO")).toString().trim().equals("AB")){
	    			ChkGAB.setChecked(true);
	    		}else if (_cGestante.getString(_cGestante.getColumnIndex("RESULTADO_GESTACAO")).toString().trim().equals("NV")){
	    			ChkGNV.setChecked(true);
	    		}else if (_cGestante.getString(_cGestante.getColumnIndex("RESULTADO_GESTACAO")).toString().trim().equals("NM")){
	    			ChkGNM.setChecked(true);
	    		}
			}
		} catch(Exception e) {
			System.out.println("Exceção Acomp_Gestante: "+e.getMessage());
		} finally {
			_cGestante.close();
		}
	}
	
	public void InicializaObjetos() {
		
		SpMesGestacao      = (Spinner)     findViewById(R.acompanhamentogestante.SpMesGestacao);
		EdtDtPreNatal      = (EditText)    findViewById(R.acompanhamentogestante.DtConsultaPreNatal);
		EdtDtProvavelParto = (EditText)    findViewById(R.acompanhamentogestante.DtProvavelParto);
		EdtDtUltimaRegra   = (EditText)    findViewById(R.acompanhamentogestante.DtUltimaRegra);
		EdtDtUltimaConsulta= (EditText)    findViewById(R.acompanhamentogestante.DtUltimaConsulta);
		EdtGObs            = (EditText)    findViewById(R.acompanhamentogestante.EdtGObs);
		EdtDtConsPuerbio   = (EditText)    findViewById(R.acompanhamentogestante.DtConsultaPuerbio);
		ChGGestacoes       = (CheckBox)    findViewById(R.acompanhamentogestante.ChGGestacoes); 
		ChGAborto          = (CheckBox)    findViewById(R.acompanhamentogestante.ChGAborto);  
		ChGIdade36         = (CheckBox)    findViewById(R.acompanhamentogestante.ChGIdade36);  
		ChGIdade20         = (CheckBox)    findViewById(R.acompanhamentogestante.ChGIdade20); 
		ChGSangue          = (CheckBox)    findViewById(R.acompanhamentogestante.ChGSangue); 
		ChGEdema           = (CheckBox)    findViewById(R.acompanhamentogestante.ChGEdema); 
		ChGDiabetes        = (CheckBox)    findViewById(R.acompanhamentogestante.ChGDiabetes); 
		ChGPressao         = (CheckBox)    findViewById(R.acompanhamentogestante.ChGPressao);
		ChkGNV			   = (CheckBox)    findViewById(R.acompanhamentogestante.ChkGNV);
		ChkGNM			   = (CheckBox)    findViewById(R.acompanhamentogestante.ChkGNM);
		ChkGAB			   = (CheckBox)    findViewById(R.acompanhamentogestante.ChkGAB);
		ChAcompFinal       = (CheckBox)    findViewById(R.acompanhamentogestante.ChAcompFinal);
		RdgEstadoNutri     = (RadioGroup)  findViewById(R.acompanhamentogestante.RgEstNut);
		RbNutrida          = (RadioButton) findViewById(R.acompanhamentogestante.RbNutrida);
		RbDesnutrida       = (RadioButton) findViewById(R.acompanhamentogestante.RbDesnutrida);
		TxtProvavelParto   = (TextView)    findViewById(R.acompanhamentogestante.TxtGParto);
		
		EdtDtPreNatal.setOnClickListener(this);
		EdtDtProvavelParto.setOnClickListener(this);
		EdtDtUltimaRegra.setOnClickListener(this);
		EdtDtUltimaConsulta.setOnClickListener(this);
		EdtDtConsPuerbio.setOnClickListener(this);
		ChAcompFinal.setOnClickListener(this);
		
	}
	

	public void onClick(View v) {
		if (v == EdtDtUltimaRegra) {
			DATE_DIALOG_ID = 0;
			showDialog(DATE_DIALOG_ID);
		} 
		
		if (v == EdtDtPreNatal) {
			DATE_DIALOG_ID = 1;
			showDialog(DATE_DIALOG_ID);
		} 
		
		if (v == EdtDtProvavelParto) {
			DATE_DIALOG_ID = 2;
			showDialog(DATE_DIALOG_ID);
		}
		if (v == EdtDtUltimaConsulta) {
			DATE_DIALOG_ID = 3;
			showDialog(DATE_DIALOG_ID);
		}
		if (v == EdtDtConsPuerbio) {
			DATE_DIALOG_ID = 4;
			showDialog(DATE_DIALOG_ID);
		}
		if (v == ChAcompFinal) {
			if (ChAcompFinal.isChecked()){
				ChkGAB.setEnabled(true);
				ChkGNM.setEnabled(true);
				ChkGNV.setEnabled(true);
				TxtProvavelParto.setText("Data do Parto:");
				EdtDtConsPuerbio.setEnabled(true);
				EdtDtUltimaRegra.setEnabled(false);
				EdtDtPreNatal.setEnabled(false);				
				ChGGestacoes.setEnabled(false);       
				ChGAborto.setEnabled(false);            
				ChGIdade36.setEnabled(false);           
				ChGIdade20.setEnabled(false);          
				ChGSangue.setEnabled(false);           
				ChGEdema.setEnabled(false);            
				ChGDiabetes.setEnabled(false);         
				ChGPressao.setEnabled(false);
				setMesGestacao("0");
				SpMesGestacao.setClickable(false);
			} else {
				ChkGAB.setEnabled(false);
				ChkGNM.setEnabled(false);
				ChkGNV.setEnabled(false);
				ChkGAB.setChecked(false);
				ChkGNM.setChecked(false);
				ChkGNV.setChecked(false);
				TxtProvavelParto.setText("Data Provável do Parto:");
				EdtDtConsPuerbio.setText("");
				EdtDtConsPuerbio.setEnabled(false);
				EdtDtUltimaRegra.setEnabled(true);
				EdtDtPreNatal.setEnabled(true);			
				ChGGestacoes.setEnabled(true);       
				ChGAborto.setEnabled(true);            
				ChGIdade36.setEnabled(true);           
				ChGIdade20.setEnabled(true);          
				ChGSangue.setEnabled(true);           
				ChGEdema.setEnabled(true);            
				ChGDiabetes.setEnabled(true);         
				ChGPressao.setEnabled(true);
				setMesGestacao("");
				SpMesGestacao.setClickable(true);
			}
		}
	}
	
	public void setMesGestacao(String pMesGestacao){
		mesGestacao.clear();
		if (pMesGestacao.length()>0){
			mesGestacao.add(pMesGestacao);
		}		
		for(int i = 1; i < 10; i++){
			mesGestacao.add(String.valueOf(i));
		}
		
		PreencheSpinner(SpMesGestacao, mesGestacao);
	}
	
	public void PreencheSpinner(final Spinner s,ArrayList<String> a){
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, a);		
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;		
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		s.setAdapter(spinnerArrayAdapter);		
		
		s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
				
					
			}

			public void onNothingSelected(AdapterView<?> parent) {
				
			}
			
		});
	}
	
	@Override
	protected void onDestroy() {
		DtAcompanhamento = null;
		Hash = null;
		DATE_DIALOG_ID = -1;
		setIdTransacao(0);
		super.onDestroy();
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendario = Calendar.getInstance();
         
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
         
        switch (id) {
        case 0:
            return new DatePickerDialog(this, mDateSetListener, ano, mes,
                    dia);
        case 1:
            return new DatePickerDialog(this, mDateSetListener, ano, mes,
                    dia);
        case 2:
            return new DatePickerDialog(this, mDateSetListener, ano, mes,
                    dia);    
        case 3:
            return new DatePickerDialog(this, mDateSetListener, ano, mes,
                    dia);    
        case 4:
            return new DatePickerDialog(this, mDateSetListener, ano, mes,
                    dia);     
        }
        return null;
    }
	
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        
		public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {
            String data1 = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear+1) + "/" + String.valueOf(year);
            SimpleDateFormat formatador =  new SimpleDateFormat("dd/MM/yyyy");
            try {
            	 data = formatador.parse(data1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());;
			}
            if (DATE_DIALOG_ID == 0) {
            	EdtDtUltimaRegra.setText(formatador.format(data));
            } else if (DATE_DIALOG_ID == 1) {
            	EdtDtPreNatal.setText(formatador.format(data));
            } else if (DATE_DIALOG_ID == 2) {
            	EdtDtProvavelParto.setText(formatador.format(data));
            }else if (DATE_DIALOG_ID == 3) {
            	EdtDtUltimaConsulta.setText(formatador.format(data));
            }else if (DATE_DIALOG_ID == 4) {
            	EdtDtConsPuerbio.setText(formatador.format(data));
            }
        }
    };

}
