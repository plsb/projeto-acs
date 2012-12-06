/************************************************/
/** Projeto SCS - Sistema de Controle de Saúde **/
/************ Desenvolvido por: *****************/
/********** Lucas de Souza Sales ****************/
/******* Karlos Kelvin Alves dos Santos *********/
/************* Pedro Luis Saraiva ***************/
/************************************************/
package br.com.view;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.CheckBox;
import br.com.control.Banco;
import br.com.scs.R;

public class CartaoAdulto extends Activity {
	
	public static String Hash = "";
	
	CheckBox chkDose1HPTB, chkDose2HPTB, chkDose3HPTB, chkDoseZDA, chkDoseUnicaTRV, 
			 chkDoseZFBA, chkDoseAINSA, chkDose1MNCA, chkDose2MNCA, chkDoseRMNCA,
			 chkDoseUnicaPNCA23;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cartaoadulto);
		
		InicializaComponentes();  
		ConsultaVacinas();
	}
	
	public void InicializaComponentes(){
		chkDose1HPTB       = (CheckBox) findViewById(R.cartaoAdulto.chkDose1HPTB); 
		chkDose2HPTB       = (CheckBox) findViewById(R.cartaoAdulto.chkDose2HPTB);
		chkDose3HPTB       = (CheckBox) findViewById(R.cartaoAdulto.chkDose3HPTB); 
		chkDoseZDA         = (CheckBox) findViewById(R.cartaoAdulto.chkDoseZDA); 
		chkDoseUnicaTRV    = (CheckBox) findViewById(R.cartaoAdulto.chkDoseUnicaTRV); 
		chkDoseZFBA        = (CheckBox) findViewById(R.cartaoAdulto.chkDoseZFBA); 
		chkDoseAINSA       = (CheckBox) findViewById(R.cartaoAdulto.chkDoseAINSA); 
		chkDose1MNCA       = (CheckBox) findViewById(R.cartaoAdulto.chkDose1MNCA);
		chkDose2MNCA       = (CheckBox) findViewById(R.cartaoAdulto.chkDose2MNCA);
		chkDoseRMNCA       = (CheckBox) findViewById(R.cartaoAdulto.chkDoseRMNCA);
		chkDoseUnicaPNCA23 = (CheckBox) findViewById(R.cartaoAdulto.chkDoseUnicaPNCA23);
	}
	
	public void ConsultaVacinas(){
		Banco bd = new Banco(this);
		bd.open();
		
		Cursor c = bd.consulta("vacinas", new String[]{"*"}, "HASH = '"+Hash+"'", null, null, null, null, null);
		c.moveToFirst();
		
		if (c.getCount() > 0){
			
			do{
				if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("HEPATITE B"))					 
					    &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					    &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("1"))
					    &&((c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("D"))
					    ||(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("A"))
					    ||(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C")))){
					chkDose1HPTB.setChecked(true);
			    }
				if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("HEPATITE B"))					 
					    &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					    &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("2"))
					    &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("D"))){
					chkDose2HPTB.setChecked(true);
			    }
				if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("HEPATITE B"))					 
					    &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					    &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("3"))
					    &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("D"))){
					chkDose3HPTB.setChecked(true);
			    }
				if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("DUPLA ADULTO"))					 
					    &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					    &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("D"))){
					chkDoseZDA.setChecked(true);
			    }
				if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("TRIPLICE VIRAL"))					 
					    &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					    &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("D"))){
					chkDoseUnicaTRV.setChecked(true);
			    }
				if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("FEBRE AMARELA"))					 
					    &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					    &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("D"))){
					chkDoseZFBA.setChecked(true);
			    }
				if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("INFLUENZA SAZONAL"))					 
					    &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					    &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("D"))){
					chkDoseAINSA.setChecked(true);
			    }
				if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("MENINGOCOCICA"))					 
					    &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					    &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("1"))
					    &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("D"))){
					chkDose1MNCA.setChecked(true);
			    }
				if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("MENINGOCOCICA"))					 
					    &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					    &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("2"))
					    &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("D"))){
					chkDose2MNCA.setChecked(true);
			    }
				if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("MENINGOCOCICA"))					 
					    &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					    &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("R"))
					    &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("D"))){
					chkDoseRMNCA.setChecked(true);
			    }
				if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("PNEUMOCOCICA 23"))					 
					    &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					    &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("D"))){
					chkDoseUnicaPNCA23.setChecked(true);
			    }
				
				
				
			}while(c.moveToNext());//FIM DO WHILE
		
		}//FIM DO SE
		c.close();
		bd.fechaBanco();
	}
}
