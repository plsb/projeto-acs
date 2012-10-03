package br.com.view;

import br.com.control.Banco;
import br.com.scs.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.CheckBox;

public class CartaoAdolescente extends Activity {
	
	private CheckBox chkDose1HPTB, chkDose2HPTB, chkDose3HPTB, chkDoseZDPADULTO, chkDoseZFBA, chkDose1TPV, chkDose2TPV;
	public static String Hash = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.cartaoadolescente);
		
	}//Fim do OnCreate
	
	public void InicializaObjetos(){
		
		chkDose1HPTB	 = (CheckBox) findViewById(R.CartaoAdolescente.chkDose1HPTB); 
		chkDose2HPTB	 = (CheckBox) findViewById(R.CartaoAdolescente.chkDose2HPTB); 
		chkDose3HPTB	 = (CheckBox) findViewById(R.CartaoAdolescente.chkDose3HPTB);
		chkDoseZDPADULTO = (CheckBox) findViewById(R.CartaoAdolescente.chkDoseZDPADULTO);
		chkDoseZFBA 	 = (CheckBox) findViewById(R.CartaoAdolescente.chkDoseZFBA); 
		chkDose1TPV		 = (CheckBox) findViewById(R.CartaoAdolescente.chkDose1TPV); 
		chkDose2TPV		 = (CheckBox) findViewById(R.CartaoAdolescente.chkDose1TPV);
		
	}//Fim do Método InicializaObjetos
	
	public void ConsultaVacinas(){
		
		try{
			Banco bd = new Banco(this);  			
			bd.open();			
			Cursor c = bd.consulta("vacinas", new String[]{"*"}, "HASH = '"+Hash+"'", null, null, null, null, null);
			c.moveToFirst();
			if (c.getCount() > 0){
				do{
					/************************** H E P A T I T E   B **********************************/
					if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("HEPATITE B"))					 
						&&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
						&&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("1"))
						&&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("A"))){
						chkDose1HPTB.setChecked(true);
					}
					if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("HEPATITE B"))					 
						&&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
						&&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("2"))
						&&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("A"))){
						chkDose2HPTB.setChecked(true);
					}
					if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("HEPATITE B"))					 
						&&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
						&&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("3"))
						&&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("A"))){
						chkDose3HPTB.setChecked(true);
					}
					
					/************************** D U P L A   A D U L T O **********************************/
					if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("DUPLA ADULTO"))					 
						&&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
						&&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("Z"))
						&&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("A"))){
						chkDoseZDPADULTO.setChecked(true);
					}
					
					/************************** F E B R E   A M A R E L A **********************************/
					if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("FEBRE AMARELA"))					 
						&&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
						&&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("Z"))
						&&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("A"))){
						chkDoseZFBA.setChecked(true);
					}
					
					/************************** T R Í P L I C E   V I R A L *******************************/
					if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("TRIPLICE VIRAL"))					 
						&&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
						&&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("1"))
						&&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("A"))){
						chkDose1TPV.setChecked(true);
					}
					if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("TRIPLICE VIRAL"))					 
						&&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
						&&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("2"))
						&&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("A"))){
						chkDose2TPV.setChecked(true);
					}
				}while(c.moveToNext());
			}//Fim do If
		
			c.close();
			bd.fechaBanco();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}//Fim do Método ConsultaVacinas
	
	@Override
	protected void onDestroy() {
		Hash = "";
		super.onDestroy();
	}

}
