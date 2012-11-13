package br.com.view;

import br.com.control.Banco;
import br.com.scs.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;

public class Acomp_Crianca extends Activity implements OnClickListener {
	
	Banco _bd = new Banco(this);
	Cursor _cCriancas = null;
	
	private static int IdTransacao = 0;
	public  static String DtAcompanhamento = null;
	public  static String Hash = null;
	
	static EditText EdtAltura, EdtPeso, EdtPerCefalico, EdtObs, EdtApgar5;
	static RadioButton RbNutrido, RbDesnutrido, RbCesario, RbNormal, RbForceps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.acomp_crianca);
		
		InicializaObjetos();
		
		if (DtAcompanhamento != null) {
			PreencheCampos();
		}
	}
	
	public void onClick(View v) {		
		
	}
	
	public static int getIdTransacao() {
		return IdTransacao;
	}

	public static void setIdTransacao(int idTransacao) {
		IdTransacao = idTransacao;
	}

	@Override
	protected void onDestroy() {
		DtAcompanhamento = null;
		Hash = null;
		setIdTransacao(0);
		super.onDestroy();
	}
	
	public void PreencheCampos() {
		try{
			_bd.open();
			_cCriancas = _bd.consulta("crianca", new String[]{"*"}, "hash = '"+Hash+"'", null, null, null, null, "1");
			_cCriancas.moveToFirst();
			if (_cCriancas.getCount() > 0){
				setIdTransacao(Integer.valueOf(_cCriancas.getString(_cCriancas.getColumnIndex("_ID")).toString()));
				EdtAltura.setText(_cCriancas.getString(_cCriancas.getColumnIndex("ALTURA")).toString());
				EdtPeso.setText(_cCriancas.getString(_cCriancas.getColumnIndex("PESO")).toString());
				EdtApgar5.setText(_cCriancas.getString(_cCriancas.getColumnIndex("APGAR5")).toString());
				EdtPerCefalico.setText(_cCriancas.getString(_cCriancas.getColumnIndex("PER_CEFALICO")).toString());
				EdtObs.setText(_cCriancas.getString(_cCriancas.getColumnIndex("OBSERVACAO")).toString());
				
				//Faz Dieta
	    		if (_cCriancas.getString(_cCriancas.getColumnIndex("TP_PARTO")).toString().trim().equals("C"))
	    			RbCesario.setChecked(true);
	    		else if (_cCriancas.getString(_cCriancas.getColumnIndex("TP_PARTO")).toString().trim().equals("N"))
	    			RbNormal.setChecked(true);
	    		else
	    			RbForceps.setChecked(true);
	    		
	    		//Faz Exercicios
	    		if (_cCriancas.getString(_cCriancas.getColumnIndex("SITUACAO")).toString().trim().equals("N"))
	    			RbNutrido.setChecked(true);
	    		else
	    			RbDesnutrido.setChecked(true);	    
			}
		  }catch(Exception e) {
				System.out.println("Exceção Acomp_Crianças: "+e.getMessage());
		  } finally {
			  _cCriancas.close();
	      }
	}
	
	public void InicializaObjetos() {
		
		EdtAltura      = (EditText)    findViewById(R.acompcrianca.EdtAltura);
		EdtPeso        = (EditText)    findViewById(R.acompcrianca.EdtPeso);
		EdtPerCefalico = (EditText)    findViewById(R.acompcrianca.EdtPerimetroCefalico);		
		EdtObs         = (EditText)    findViewById(R.acompcrianca.EdtObs);
		EdtApgar5      = (EditText)    findViewById(R.acompcrianca.EdtApgar5);
		RbCesario      = (RadioButton) findViewById(R.acompcrianca.RbCesario);
		RbNormal       = (RadioButton) findViewById(R.acompcrianca.RbNormal);
		RbForceps      = (RadioButton) findViewById(R.acompcrianca.RbForceps);
		RbNutrido      = (RadioButton) findViewById(R.acompcrianca.RbNutrida);
		RbDesnutrido   = (RadioButton) findViewById(R.acompcrianca.RbDesnutrida);
	}
	
}
