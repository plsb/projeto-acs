package br.com.view;

import br.com.control.Banco;
import br.com.scs.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;

public class CartaoCrianca extends Activity {
	
	public static String Hash = "";
	
	private CheckBox chkDoseUnicaBCG, chkDose1HPTB, chkDose2HPTB, chkDose3HPTB, chkDoseRHPTB,
					 chkDose1PV, chkDose2PV, chkDose3PV, chkDose1VOPI, chkDose2VOPI, 
					 chkDose3VOPI, chkDoseRVOPI,chkDose1VORH, chkDose2VORH, chkDose1PNMCA, chkDose2PNMCA, 
					 chkDose3PNMCA, chkDoseRPNMCA, chkDose1MNGCA, chkDose2MNGCA,chkDoseRMNGCA, 
					 chkDose1TV, chkDose2TV, chkDose1TB, chkDoseRTB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cartaocrianca);
		
		InicializaObjetos();
		
		ConsultaVacinas();
		
	}
	
	public void InicializaObjetos(){
		
		chkDoseUnicaBCG= (CheckBox) findViewById(R.VacinaAplicada.chkDoseUnicaBCG);
		chkDose1HPTB   = (CheckBox) findViewById(R.VacinaAplicada.chkDose1HPTB);
		chkDose1PV   = (CheckBox) findViewById(R.VacinaAplicada.chkDose1PV);
		chkDose2PV   = (CheckBox) findViewById(R.VacinaAplicada.chkDose2PV);
		chkDose3PV   = (CheckBox) findViewById(R.VacinaAplicada.chkDose3PV);
		chkDose1VOPI   = (CheckBox) findViewById(R.VacinaAplicada.chkDose1VOPI);
		chkDose2VOPI   = (CheckBox) findViewById(R.VacinaAplicada.chkDose2VOPI);
		chkDose3VOPI   = (CheckBox) findViewById(R.VacinaAplicada.chkDose3VOPI);
		chkDoseRVOPI   = (CheckBox) findViewById(R.VacinaAplicada.chkDoseRVOPI);
		chkDose1VORH = (CheckBox) findViewById(R.VacinaAplicada.chkDose1VORH);
		chkDose2VORH = (CheckBox) findViewById(R.VacinaAplicada.chkDose2VORH);
		chkDose1PNMCA  = (CheckBox) findViewById(R.VacinaAplicada.chkDose1PNMCA);
		chkDose2PNMCA  = (CheckBox) findViewById(R.VacinaAplicada.chkDose2PNMCA);
		chkDose3PNMCA  = (CheckBox) findViewById(R.VacinaAplicada.chkDose3PNMCA);
		chkDoseRPNMCA  = (CheckBox) findViewById(R.VacinaAplicada.chkDoseRPNMCA);
	    chkDose1MNGCA  = (CheckBox) findViewById(R.VacinaAplicada.chkDose1MNGCA);
		chkDose2MNGCA  = (CheckBox) findViewById(R.VacinaAplicada.chkDose2MNGCA);
		chkDoseRMNGCA  = (CheckBox) findViewById(R.VacinaAplicada.chkDoseRMNGCA);
		chkDose1TV     = (CheckBox) findViewById(R.VacinaAplicada.chkDose1TV);
		chkDose2TV     = (CheckBox) findViewById(R.VacinaAplicada.chkDose2TV);
		chkDose1TB     = (CheckBox) findViewById(R.VacinaAplicada.chkDose1TB);
		chkDoseRTB     = (CheckBox) findViewById(R.VacinaAplicada.chkDoseRTB);
		
	}  
	
	public void ConsultaVacinas(){
		Banco bd = new Banco(this);  
		
		bd.open();
		
		Cursor c = bd.consulta("vacinas", new String[]{"*"}, "HASH = '"+Hash+"'", null, null, null, null, null);
		c.moveToFirst();
		
		if (c.getCount() > 0){
		  do{ 
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("BCG"))					 
			    &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
			    &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDoseUnicaBCG.setChecked(true);
			 }
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("HEPATITE B"))
				&&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
				&&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("1"))
				&&((c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))
				||(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("A"))
				||(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("D")))){				 
				 chkDose1HPTB.setChecked(true);			
			 }			 
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("PENTAVALENTE"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("1"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose1PV.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("PENTAVALENTE"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("2"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose2PV.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("PENTAVALENTE"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("3"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose3PV.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("VOPI"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("1"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose1VOPI.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("VOPI"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("2"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose2VOPI.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("VOPI"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("3"))){
				 chkDose3VOPI.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("VOPI"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("R"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDoseRVOPI.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("VORH"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("1"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose1VORH.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("VORH"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("2"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose2VORH.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("PNEUMOCOCICA 10"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("1"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose1PNMCA.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("PNEUMOCOCICA 10"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("2"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose2PNMCA.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("PNEUMOCOCICA 10"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("3"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose3PNMCA.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("PNEUMOCOCICA 10"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("R")) 
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDoseRPNMCA.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("MENINGOCOCICA"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("1"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose1MNGCA.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("MENINGOCOCICA"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("2"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose2MNGCA.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("MENINGOCOCICA"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("R"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDoseRMNGCA.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("TRIPLICE VIRAL"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("1"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose1TV.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("TRIPLICE VIRAL"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("2"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose2TV.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("TRIPLICE BACTERIANA"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("1R"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose1TB.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("TRIPLICE BACTERIANA"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("2R"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDoseRTB.setChecked(true);
				}
		  }while(c.moveToNext());//FIM DO WHILE
			
		 
		}//FIM IF
		 c.close();
		 bd.fechaBanco();
	}

}
