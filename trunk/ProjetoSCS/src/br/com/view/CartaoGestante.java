package br.com.view;

import br.com.control.Banco;
import br.com.scs.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.CheckBox;

public class CartaoGestante extends Activity {
	
	private CheckBox chkDose1HPTB, chkDose2HPTB, chkDose3HPTB, chkDose1DPADULTO, 
					 chkDose2DPADULTO, chkDose3DPADULTO, chkDoseRDPADULTO,chkDoseAInf;
	
	public static String Hash = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cartaogestante);
		
		InicializaObjetos();
		
		ConsultaVacinas();
		
	}
	
	public void InicializaObjetos(){
		chkDose1HPTB 	 = (CheckBox) findViewById(R.CartaoGestante.chkDose1HPTB);
		chkDose2HPTB 	 = (CheckBox) findViewById(R.CartaoGestante.chkDose2HPTB);
		chkDose3HPTB 	 = (CheckBox) findViewById(R.CartaoGestante.chkDose3HPTB);
		chkDose1DPADULTO = (CheckBox) findViewById(R.CartaoGestante.chkDose1DPADULTO);
		chkDose2DPADULTO = (CheckBox) findViewById(R.CartaoGestante.chkDose2DPADULTO);
		chkDose3DPADULTO = (CheckBox) findViewById(R.CartaoGestante.chkDose3DPADULTO);
		chkDoseRDPADULTO = (CheckBox) findViewById(R.CartaoGestante.chkDoseRDPADULTO);
		chkDoseAInf      = (CheckBox) findViewById(R.CartaoGestante.chkDoseAInf);
	}
	
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
						&&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("G"))){
						chkDose1HPTB.setChecked(true);
					}
					if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("HEPATITE B"))					 
						&&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
						&&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("2"))
						&&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("G"))){
						chkDose2HPTB.setChecked(true);
					}
					if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("HEPATITE B"))					 
						&&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
						&&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("3"))
						&&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("G"))){
						chkDose3HPTB.setChecked(true);
					}
					
					/************************** D U P L A   A D U L T O *******************************/
					if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("DUPLA ADULTO"))					 
						&&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
						&&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("1"))
						&&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("G"))){
						chkDose1DPADULTO.setChecked(true);
					}
					if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("DUPLA ADULTO"))					 
						&&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
						&&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("2"))
						&&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("G"))){
						chkDose2DPADULTO.setChecked(true);
					}
					if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("DUPLA ADULTO"))					 
						&&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
						&&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("3"))
						&&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("G"))){
						chkDose3DPADULTO.setChecked(true);
					}
					if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("DUPLA ADULTO"))					 
						&&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
						&&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("R"))
						&&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("G"))){
						chkDoseRDPADULTO.setChecked(true);
					}
					
					/************************** I N F L U E N Z A *******************************/
					if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("INFLUENZA"))					 
						&&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
						&&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("A"))
						&&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("G"))){
						chkDoseAInf.setChecked(true);
					}
				}while(c.moveToNext());
			}//Fim if
			
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
