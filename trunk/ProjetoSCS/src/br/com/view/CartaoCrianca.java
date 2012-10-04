package br.com.view;

import br.com.control.Banco;
import br.com.scs.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.CheckBox;

public class CartaoCrianca extends Activity {
	
	public static String Hash = "";
	
	private CheckBox chkDoseUnicaBCG, chkDose1HPTB, chkDose2HPTB, chkDose3HPTB, chkDoseRHPTB,
					 chkDose1TVLT, chkDose2TVLT, chkDose3TVLT, chkDoseRTVLT, chkDose1VOPV, chkDose2VOPV, 
					 chkDose3VOPV, chkDoseRVOPV,chkDose1VORHGV, chkDose2VORHGV, chkDose1PNMCA, chkDose2PNMCA, 
					 chkDose3PNMCA, chkDoseRPNMCA, chkDose1MNGCA, chkDose2MNGCA,chkDoseRMNGCA, chkDose1FBA, 
					 chkDoseRFBA, chkDose1TV, chkDose2TV, chkDose1TB, chkDoseRTB;
	
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
		chkDose2HPTB   = (CheckBox) findViewById(R.VacinaAplicada.chkDose2HPTB);
		chkDose3HPTB   = (CheckBox) findViewById(R.VacinaAplicada.chkDose3HPTB);
		chkDoseRHPTB   = (CheckBox) findViewById(R.VacinaAplicada.chkDoseRHPTB);
		chkDose1TVLT   = (CheckBox) findViewById(R.VacinaAplicada.chkDose1TVLT);
		chkDose2TVLT   = (CheckBox) findViewById(R.VacinaAplicada.chkDose2TVLT);
		chkDose3TVLT   = (CheckBox) findViewById(R.VacinaAplicada.chkDose3TVLT);
		chkDoseRTVLT   = (CheckBox) findViewById(R.VacinaAplicada.chkDoseRTVLT);
		chkDose1VOPV   = (CheckBox) findViewById(R.VacinaAplicada.chkDose1VOPV);
		chkDose2VOPV   = (CheckBox) findViewById(R.VacinaAplicada.chkDose2VOPV);
		chkDose3VOPV   = (CheckBox) findViewById(R.VacinaAplicada.chkDose3VOPV);
		chkDoseRVOPV   = (CheckBox) findViewById(R.VacinaAplicada.chkDoseRVOPV);
		chkDose1VORHGV = (CheckBox) findViewById(R.VacinaAplicada.chkDose1VORHGV);
		chkDose2VORHGV = (CheckBox) findViewById(R.VacinaAplicada.chkDose2VORHGV);
		chkDose1PNMCA  = (CheckBox) findViewById(R.VacinaAplicada.chkDose1PNMCA);
		chkDose2PNMCA  = (CheckBox) findViewById(R.VacinaAplicada.chkDose2PNMCA);
		chkDose3PNMCA  = (CheckBox) findViewById(R.VacinaAplicada.chkDose3PNMCA);
		chkDoseRPNMCA  = (CheckBox) findViewById(R.VacinaAplicada.chkDoseRPNMCA);
	    chkDose1MNGCA  = (CheckBox) findViewById(R.VacinaAplicada.chkDose1MNGCA);
		chkDose2MNGCA  = (CheckBox) findViewById(R.VacinaAplicada.chkDose2MNGCA);
		chkDoseRMNGCA  = (CheckBox) findViewById(R.VacinaAplicada.chkDoseRMNGCA);
		chkDose1FBA    = (CheckBox) findViewById(R.VacinaAplicada.chkDose1FBA);
		chkDoseRFBA    = (CheckBox) findViewById(R.VacinaAplicada.chkDoseRFBA);
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
				&&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose1HPTB.setChecked(true);
			 }
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("HEPATITE B"))
				 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
				 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("2"))
				 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				chkDose2HPTB.setChecked(true);
			}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("HEPATITE B"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("3"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
					chkDose3HPTB.setChecked(true);
			}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("HEPATITE B"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("R"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
					chkDoseRHPTB.setChecked(true);
			 }
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("TETRAVALENTE"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("1"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose1TVLT.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("TETRAVALENTE"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("2"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose2TVLT.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("TETRAVALENTE"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("3"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose3TVLT.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("TETRAVALENTE"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("R"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDoseRTVLT.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("VOP"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("1"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose1VOPV.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("VOP"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("2"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose2VOPV.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("VOP"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("3"))){
				 chkDose3VOPV.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("VOP"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("R"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDoseRVOPV.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("VORH"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("1"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose1VORHGV.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("VORH"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("2"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose2VORHGV.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("PNEUMOCOCICA"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("1"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose1PNMCA.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("PNEUMOCOCICA"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("2"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose2PNMCA.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("PNEUMOCOCICA"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("3"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose3PNMCA.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("PNEUMOCOCICA"))
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
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("FEBRE AMARELA"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("1"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDose1FBA.setChecked(true);
				}
			 if ((c.getString(c.getColumnIndex("TIPO_VACINA")).toString().equals("FEBRE AMARELA"))
					 &&(c.getString(c.getColumnIndex("FL_APLICADA")).toString().trim().equals("S"))
					 &&(c.getString(c.getColumnIndex("DOSE_APLICADA")).toString().trim().equals("R"))
					 &&(c.getString(c.getColumnIndex("TIPO")).toString().trim().equals("C"))){
				 chkDoseRFBA.setChecked(true);
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
