package br.com.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

public class Acomp_Hanseniase extends Activity implements OnClickListener  {
	
	Banco _bd = new Banco(this);
	Cursor _cHans = null;
	
	private static int IdTransacao = 0;
	
	public static RadioButton RbMdSim, RbMdNao, RbAcSim, RbAcNao;
	public static EditText EdtCe, EdtM5Bcg, EdtObservacao, EdtDtUltimadose, EdtDtUltimaConsulta;
	
	public static String DtAcompanhamento = null;
	public static String Hash = null;
	
	private java.util.Date data;
	
	private int DATE_DIALOG_ID = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.acomp_hanseniase);
		
		InicializaObjetos();
		
		if (DtAcompanhamento != null) {
			PreencheCampos();
		}
}

	private void PreencheCampos() {
	  try{
		_bd.open();
		_cHans = _bd.consulta("hanseniase", new String[]{"*"}, "hash = '"+Hash+"'", null, null, null, null, "1");
		_cHans.moveToFirst();
		if (_cHans.getCount() > 0){
			setIdTransacao(Integer.valueOf(_cHans.getString(_cHans.getColumnIndex("_ID")).toString()));
			EdtDtUltimadose.setText(_cHans.getString(_cHans.getColumnIndex("DT_ULTIMA_DOSE")).toString());
			EdtDtUltimaConsulta.setText(_cHans.getString(_cHans.getColumnIndex("DT_ULTIMA_CONSULTA")).toString());
			EdtCe.setText(_cHans.getString(_cHans.getColumnIndex("COMUNICANTES_EXAMINADOS")).toString());
			EdtM5Bcg.setText(_cHans.getString(_cHans.getColumnIndex("COMUNICANTES_BCG")).toString());
			EdtObservacao.setText(_cHans.getString(_cHans.getColumnIndex("OBSERVACAO")).toString());
			
			//Medicacao Diaria
    		if (_cHans.getString(_cHans.getColumnIndex("TOMA_MEDICACAO")).toString().trim().equals("N"))
    			RbMdNao.setChecked(true);
    		else
    			RbMdSim.setChecked(true);
    		
    		//Autos Cuidados
    		if (_cHans.getString(_cHans.getColumnIndex("AUTO_CUIDADOS")).toString().trim().equals("N"))
    			RbAcNao.setChecked(true);
    		else
    			RbAcSim.setChecked(true);
		}
	  }catch(Exception e) {
			System.out.println("Exceção Acomp_Hanseniase: "+e.getMessage());
	  } finally {
			_cHans.close();
      }
    }

	public static int getIdTransacao() {
		return IdTransacao;
	}

	public static void setIdTransacao(int idTransacao) {
		IdTransacao = idTransacao;
	}

	public void onClick(View v) {
		if (v == EdtDtUltimadose) {
			DATE_DIALOG_ID = 0;
			showDialog(DATE_DIALOG_ID);
		} 
		
		if (v == EdtDtUltimaConsulta) {
			DATE_DIALOG_ID = 1;
			showDialog(DATE_DIALOG_ID);
		} 
		
	}
	
	public void InicializaObjetos(){
		EdtCe               = (EditText)    findViewById(R.acomphanseniase.EdtCe);
		EdtM5Bcg            = (EditText)    findViewById(R.acomphanseniase.EdtMenor5Bcg);
		EdtObservacao       = (EditText)    findViewById(R.acomphanseniase.EdtObs);
		EdtDtUltimaConsulta = (EditText)    findViewById(R.acomphanseniase.EdtDtUltimaConsulta);
		EdtDtUltimadose     = (EditText)    findViewById(R.acomphanseniase.EdtDtUltimaDose);
		RbMdSim             = (RadioButton) findViewById(R.acomphanseniase.RbMSim);
		RbMdNao             = (RadioButton) findViewById(R.acomphanseniase.RbMNao);
		RbAcSim             = (RadioButton) findViewById(R.acomphanseniase.RbASim);
		RbAcNao             = (RadioButton) findViewById(R.acomphanseniase.RbANao);
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
				System.out.println(e.getMessage());
			}if (DATE_DIALOG_ID == 0) {
            	EdtDtUltimadose.setText(formatador.format(data));
			}if (DATE_DIALOG_ID == 1) {
            	EdtDtUltimaConsulta.setText(formatador.format(data));
			}
        }
    };
    
    @Override
	protected void onDestroy() {
		DtAcompanhamento = null;
		Hash = null;
		DATE_DIALOG_ID = -1;
		super.onDestroy();
	}

}
