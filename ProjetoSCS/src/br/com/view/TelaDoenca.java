package br.com.view;

import br.com.control.Banco;
import br.com.control.Diabete;
import br.com.control.Gestante;
import br.com.control.Hanseniase;
import br.com.control.Hipertensao;
import br.com.control.Mensagem;
import br.com.control.Tuberculose;
import br.com.scs.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TelaDoenca extends Activity{
	
	public static int COD_FAMILAR = 0;	
	
	public static boolean _editando = false;
	public static String _dataVisita,_tabelaDoenca = "";
	private int _IdTransacao = 0;
	
	public static boolean _Hanseniase  = false;
	public static boolean _Diabetes    = false;
	public static boolean _Hipertensao = false;
	public static boolean _Gestante	   = false;
	public static boolean _Tuberculose = false;
	
	public static String _HASH = "";
	
	 /*HANSENIASE*/
	TextView datavisita, medicacaodiaria, ultimadose, cuidados, comunicantes, bcg, TxtHNComunicantes, TxtHDtUltimaConsulta; //Hanseniase
	DatePicker DtHVisita, DtHUltimadose, DtHDtUltimaConsulta; //hanseniase
	RadioGroup rgmedicacaodiaria, autocuidados; //hanseniase
	RadioButton RbHMdSim, RbHMdNao, RbHAcSim, RbHAcNao;
	EditText EdtHCe, EdtHM5Bcg, EdtHNObservacao;
	 /*HANSENIASE*/

	 /*DIABETES*/
	TextView dDataVisita, dFazDieta, dFazExercicio, dUsaInsulina, dTomaHipo, dUltimaVisita, TxtDObs; //Diabetes
	DatePicker dDtDataVisita, dDtUltimaConsulta; //Diabetes
	RadioGroup dRgFazExercicio, dRgFazDieta, dRgInsulina, dRgHipoglicemiante;//Diabetes
	RadioButton dFazDieta_S,dFazDieta_N,dFazExerc_S,dFazExerc_N,dUsaInsulina_S,dUsaInsulina_N,dHipog_S,dHipog_N;//Diabetes
	EditText EdtDObs;
	 /*DIABETES*/
	
    /*TUBERCULOSE*/
	TextView TxtTDataVisita, TxtTMd, TxtTRi, TxtTEe, TxtTCe, TxtTM5Bcg, TxtTObs;
	DatePicker DtTDataVisita;
	RadioGroup RgTMd, RgTRi, RgTEe;
	RadioButton RbTMedicDiaria_S,RbTMedicDiaria_N,RbTReacoesIndesejaveis_S,RbTReacoesIndesejaveis_N,RbTExameEscarro_S,RbTExameEscarro_N;
	EditText EdtTCe, EdtTM5Bcg, EdtTObs;
	/*TUBERCULOSE*/
	
	/*HIPERTENSAO*/
	TextView TxtHtDataVisita, TxtHtFd, TxtHtTm, TxtHtFe, TxtHtPa, TxtHtDtUV, TxtHtObs;
	DatePicker DtHtDataVisita, DtHtUltimaVisita;
	RadioGroup RgHtFd, RgHtTm, RgHtFe;
	RadioButton RbHiFazDieta_S, RbHiFazDieta_N, RbHiTomaMedic_S, RbHiTomaMedic_N, RbHiExcercFisico_S, RbHiExcercFisico_N; 
	EditText EdtHtPe, EdtHtObs;
	/*HIPERTENSAO*/
	
	/*HIPERTENSAO*/
	TextView TxtGDtUltimaRegra, TxtGDtParto, TxtGDtVacina, TxtGEn, TxtGDtPN, TxtGFr, TxtGRG, TxtGDtPuerbio, TxtGEnMes, TxtGObs;
	DatePicker GDtUltimaRegra, GDtParto, GDtVacina, GDtPuerbio, DtGPreNatal;
	CheckBox ChGGestacoes, ChGAborto, ChGIdade36, ChGIdade20, ChGSangue, ChGEdema, ChGDiabetes, ChGPressao, ChGUm, ChG2, ChGR;
	EditText EdtGEn,EdtMesGestacao, EdtGObs;
	/*HIPERTENSAO*/
	

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.teladoenca);
		
		//Componentes Hanseniase
		//datavisita           = (TextView)    findViewById(R.teladoenca.TxtHDataVisita);
		medicacaodiaria      = (TextView)    findViewById(R.teladoenca.TxtHMedicacaoDiaria);
		ultimadose           = (TextView)    findViewById(R.teladoenca.TxtHDataUltimaDose);
		cuidados             = (TextView)    findViewById(R.teladoenca.TxtHFazCuidados);
		comunicantes         = (TextView)    findViewById(R.teladoenca.TxtHComunicantes);
		bcg                  = (TextView)    findViewById(R.teladoenca.TxtHBCG);
		TxtHNComunicantes    = (TextView)    findViewById(R.teladoenca.TxtHNComunicantes);
		TxtHDtUltimaConsulta = (TextView)    findViewById(R.teladoenca.TxtHDtUltimaConsulta);
		DtHDtUltimaConsulta  = (DatePicker)  findViewById(R.teladoenca.DtHDtUltimaConsulta); 
		//DtHVisita            = (DatePicker)  findViewById(R.teladoenca.DtHDataProfissional);
		DtHUltimadose        = (DatePicker)  findViewById(R.teladoenca.DtHUltimaDose);
		rgmedicacaodiaria    = (RadioGroup)  findViewById(R.teladoenca.RgHMedicacaoDiaria);
		autocuidados         = (RadioGroup)  findViewById(R.teladoenca.RgHAutoCuidados);
		EdtHCe               = (EditText)    findViewById(R.teladoenca.EdtHCe);
		EdtHM5Bcg            = (EditText)    findViewById(R.teladoenca.EdtHMenor5Bcg);
		EdtHNObservacao      = (EditText)    findViewById(R.teladoenca.EdtHNObservacao);
		RbHMdSim             = (RadioButton) findViewById(R.teladoenca.RbMSim);
		RbHMdNao             = (RadioButton) findViewById(R.teladoenca.RbMNao);
		RbHAcSim             = (RadioButton) findViewById(R.teladoenca.RbASim);
		RbHAcNao             = (RadioButton) findViewById(R.teladoenca.RbANao);
		//Componentes Hanseniase
		
		//Componentes Diabetes
		dUltimaVisita      = (TextView)    findViewById(R.teladoenca.TxtDUltimaVisita);
		//dDataVisita        = (TextView)    findViewById(R.teladoenca.TxtDDataVisita);
		dFazDieta          = (TextView)    findViewById(R.teladoenca.TxtDFazDieta);
		dFazExercicio      = (TextView)    findViewById(R.teladoenca.TxtDFazExercicio);
		dUsaInsulina       = (TextView)    findViewById(R.teladoenca.TxtDInsulina);
		dTomaHipo          = (TextView)    findViewById(R.teladoenca.TxtDHipoglicemiante);
		TxtDObs            = (TextView)    findViewById(R.teladoenca.TxtDObs);
		//dDtDataVisita      = (DatePicker)  findViewById(R.teladoenca.DtDDataProfissional);
		dDtUltimaConsulta  = (DatePicker)  findViewById(R.teladoenca.DtDUltimaConsulta);
		dRgFazExercicio    = (RadioGroup)  findViewById(R.teladoenca.RgDFazExercicio);
		dRgFazDieta        = (RadioGroup)  findViewById(R.teladoenca.RgDDieta);
		dRgInsulina        = (RadioGroup)  findViewById(R.teladoenca.RgDInsulina);
		dRgHipoglicemiante = (RadioGroup)  findViewById(R.teladoenca.RgDHipoglicemiante);
		EdtDObs            = (EditText)    findViewById(R.teladoenca.EdtDObs);
		dFazDieta_S        = (RadioButton) findViewById(R.teladoenca.RbDietaSim);
		dFazDieta_N        = (RadioButton) findViewById(R.teladoenca.RbDietaNao);
		dFazExerc_S        = (RadioButton) findViewById(R.teladoenca.RbFazExercicioSim);
		dFazExerc_N        = (RadioButton) findViewById(R.teladoenca.RbFazExercicioNao);
		dUsaInsulina_S     = (RadioButton) findViewById(R.teladoenca.RbInsulinaSim);
		dUsaInsulina_N     = (RadioButton) findViewById(R.teladoenca.RbInsulinaNao);
		dHipog_S           = (RadioButton) findViewById(R.teladoenca.RbHipoglicemianteSim);
		dHipog_N           = (RadioButton) findViewById(R.teladoenca.RbHipoglicemianteNao);
		
		//Componentes tuberculose
		//TxtTDataVisita  		 = (TextView) 	 findViewById(R.teladoenca.TxtTDataVisita);
		TxtTMd          		 = (TextView) 	 findViewById(R.teladoenca.TxtTMedicacaoDiaria);
		TxtTRi          		 = (TextView) 	 findViewById(R.teladoenca.TxtTReacoesIndesejadas);
		TxtTEe          		 = (TextView) 	 findViewById(R.teladoenca.TxtTExameEscarro);
		TxtTCe          		 = (TextView) 	 findViewById(R.teladoenca.TxtTComunicantesExaminados);
		TxtTM5Bcg       		 = (TextView) 	 findViewById(R.teladoenca.TxtTMenor5Bcg);
		TxtTObs         		 = (TextView) 	 findViewById(R.teladoenca.TxtTObs);
		//DtTDataVisita   		 = (DatePicker)  findViewById(R.teladoenca.DtTDataVisita);
		RgTMd           		 = (RadioGroup)  findViewById(R.teladoenca.RgTMedicacaoDiaria);
		RgTEe           		 = (RadioGroup)  findViewById(R.teladoenca.RgTExameEscarro);
		RgTRi           		 = (RadioGroup)  findViewById(R.teladoenca.RgTReacoesIndesejadas);
		RbTMedicDiaria_S		 = (RadioButton) findViewById(R.teladoenca.RbMedicacaoDiariaSim);
		RbTMedicDiaria_N		 = (RadioButton) findViewById(R.teladoenca.RbMedicacaoDiariaNao);
		RbTReacoesIndesejaveis_S = (RadioButton) findViewById(R.teladoenca.RbTReacoesIndesejadasSim);
		RbTReacoesIndesejaveis_N = (RadioButton) findViewById(R.teladoenca.RbTReacoesIndesejadasNao);
		RbTExameEscarro_S        = (RadioButton) findViewById(R.teladoenca.RbTExameEscarroSim);
		RbTExameEscarro_N        = (RadioButton) findViewById(R.teladoenca.RbTExameEscarroNao);
		EdtTCe					 = (EditText) 	 findViewById(R.teladoenca.EdtTCe);
		EdtTM5Bcg				 = (EditText) 	 findViewById(R.teladoenca.EdtTMenor5Bcg);
		EdtTObs					 = (EditText) 	 findViewById(R.teladoenca.EdtTObs);
		
		//Componentes Hipertensao
		//TxtHtDataVisita 		 = (TextView) 	 findViewById(R.teladoenca.TxtHtDataVisita);
		TxtHtFd 				 = (TextView) 	 findViewById(R.teladoenca.TxtHtFazDieta); 
		TxtHtTm					 = (TextView) 	 findViewById(R.teladoenca.TxtHtTomaMedicacao);
		TxtHtFe					 = (TextView) 	 findViewById(R.teladoenca.TxtHtFazExercicio); 
		TxtHtPa					 = (TextView) 	 findViewById(R.teladoenca.TxtHtPressaoArterial); 
		TxtHtDtUV				 = (TextView) 	 findViewById(R.teladoenca.TxtHtDtUltimaVisita);
		TxtHtObs				 = (TextView) 	 findViewById(R.teladoenca.TxtHtObs);
		//DtHtDataVisita			 = (DatePicker)  findViewById(R.teladoenca.DtHtDataVisita);
		DtHtUltimaVisita		 = (DatePicker)  findViewById(R.teladoenca.DtHtUltimaVisita);
		RgHtFd					 = (RadioGroup)  findViewById(R.teladoenca.RgHtDieta); 
		RgHtTm					 = (RadioGroup)  findViewById(R.teladoenca.RgHtMedicacao); 
		RgHtFe					 = (RadioGroup)  findViewById(R.teladoenca.RgHtFazExercicio);
		RbHiFazDieta_S			 = (RadioButton) findViewById(R.teladoenca.RbHtDietaSim);
		RbHiFazDieta_N			 = (RadioButton) findViewById(R.teladoenca.RbHtDietaNao);
		RbHiTomaMedic_S			 = (RadioButton) findViewById(R.teladoenca.RbHtMedicacaoSim);
		RbHiTomaMedic_N			 = (RadioButton) findViewById(R.teladoenca.RbHtMedicacaoNao);
		RbHiExcercFisico_S		 = (RadioButton) findViewById(R.teladoenca.RbHtFazExercicioSim);
		RbHiExcercFisico_N		 = (RadioButton) findViewById(R.teladoenca.RbHtFazExercicioNao);
		EdtHtPe					 = (EditText) 	 findViewById(R.teladoenca.EdtHtPressaoArterial);
		EdtHtObs				 = (EditText)    findViewById(R.teladoenca.EdtHtObs);
		
		//Componentes Gestante
		TxtGDtUltimaRegra = (TextView)   findViewById(R.teladoenca.TxtGDtUltimaRegra);
		TxtGDtParto       = (TextView)   findViewById(R.teladoenca.TxtGParto);
		TxtGDtVacina      = (TextView)   findViewById(R.teladoenca.TxtGVacina);
		TxtGEn         	  = (TextView)   findViewById(R.teladoenca.TxtGEn);
		TxtGDtPN      	  = (TextView)   findViewById(R.teladoenca.TxtGPreNatal);
		TxtGFr        	  = (TextView)   findViewById(R.teladoenca.TxtGFr);
		TxtGRG        	  = (TextView)   findViewById(R.teladoenca.TxtGRga);
		TxtGDtPuerbio 	  = (TextView)   findViewById(R.teladoenca.TxtGDtPuerbio);
		TxtGEnMes     	  = (TextView)   findViewById(R.teladoenca.TxtGEnMes);
		TxtGObs       	  = (TextView)   findViewById(R.teladoenca.TxtGObs);
		GDtUltimaRegra	  = (DatePicker) findViewById(R.teladoenca.DtGDataRegra);
		GDtParto	  	  = (DatePicker) findViewById(R.teladoenca.DtGParto);
		GDtVacina 	  	  = (DatePicker) findViewById(R.teladoenca.DtGVacina);
		GDtPuerbio 	  	  = (DatePicker) findViewById(R.teladoenca.DtGDtPuerbio);
		GDtUltimaRegra	  = (DatePicker) findViewById(R.teladoenca.DtGDataRegra); 
		GDtParto 	  	  = (DatePicker) findViewById(R.teladoenca.DtGParto);  
		GDtVacina 		  = (DatePicker) findViewById(R.teladoenca.DtGVacina);  
		GDtPuerbio        = (DatePicker) findViewById(R.teladoenca.DtGDtPuerbio);
		DtGPreNatal       = (DatePicker) findViewById(R.teladoenca.DtGPreNatal);
		EdtGEn            = (EditText)   findViewById(R.teladoenca.EdtGEn);
		EdtGObs           = (EditText)   findViewById(R.teladoenca.EdtGObs);
		EdtMesGestacao    = (EditText)   findViewById(R.teladoenca.EdtMesGestacao);
		ChGGestacoes      = (CheckBox)   findViewById(R.teladoenca.ChGGestacoes); 
		ChGAborto         = (CheckBox)   findViewById(R.teladoenca.ChGAborto);  
		ChGIdade36        = (CheckBox)   findViewById(R.teladoenca.ChGIdade36);  
		ChGIdade20        = (CheckBox)   findViewById(R.teladoenca.ChGIdade20); 
		ChGSangue         = (CheckBox)   findViewById(R.teladoenca.ChGSangue); 
		ChGEdema          = (CheckBox)   findViewById(R.teladoenca.ChGEdema); 
		ChGDiabetes       = (CheckBox)   findViewById(R.teladoenca.ChGDiabetes); 
		ChGPressao        = (CheckBox)   findViewById(R.teladoenca.ChGPressao);
		ChGUm             = (CheckBox)   findViewById(R.teladoenca.ChGUm); 
		ChG2              = (CheckBox)   findViewById(R.teladoenca.ChG2);
		ChGR              = (CheckBox)   findViewById(R.teladoenca.ChGR);	
		
		if (_editando == true)
			EditaDados();
		else
			InicializaTelas();
		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.telaresidencia, menu);        
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.menu_gravar:
			Inserir();
			break;
		}
		return true;
	}
	
	public void LimparVariaveis(){		
		_tabelaDoenca = "";
		_dataVisita   = "";
		_HASH 		  = "";
		_IdTransacao  = 0;
		_Diabetes     = false;
		_Gestante     = false;
		_Hanseniase   = false;
		_Hipertensao  = false;
		_Tuberculose  = false;		
		_editando     = false;
	}
	
	@Override
	protected void onDestroy() {
		LimparVariaveis();
		super.onDestroy();
	}
	
	public void EditaDados(){
		Banco bd = null;
		Cursor c = null;
			
		try{
			bd = new Banco(this);
			bd.open();	
			System.out.println("Tabela: "+_tabelaDoenca);
			System.out.println("Dt_Visita: "+_dataVisita);
			System.out.println("HASH: "+_HASH);
			c = bd.consulta(_tabelaDoenca, new String[]{"*"}, "DT_VISITA = '"+_dataVisita+"' and HASH = '"+_HASH+"'", null, null, null, null, null);					
			
			c.moveToFirst();
			
			if (c.getCount()>0){
				
				_IdTransacao = Integer.valueOf(c.getString(c.getColumnIndex("_ID")).toString().trim());
				
				System.out.println("idTransacao:"+c.getString(c.getColumnIndex("_ID")).toString().trim());
				
				TabHost th = (TabHost) findViewById(R.teladoenca.tabhost);
		        th.setup();
		        TabSpec ts;
		        
		        if (_Hanseniase == true){			        	
		        	ts = th.newTabSpec("tag1");
		        	ComponentesHanseniase();
		            ts.setContent(R.teladoenca.tabHanseniase);
		            ts.setIndicator("Hanseniase",getResources().getDrawable(R.drawable.hanseniase));
		            th.addTab(ts);
		            
		            //Toma Medicação
		            if (c.getString(c.getColumnIndex("TOMA_MEDICACAO")).toString().trim().equals("S"))
		            	RbHMdSim.setChecked(true);		        
		            else
		            	RbHMdNao.setChecked(true);		            
		            //Auto Cuidado
		            if (c.getString(c.getColumnIndex("AUTO_CUIDADOS")).toString().trim().equals("S"))
		            	RbHAcSim.setChecked(true);		        
		            else
		            	RbHAcNao.setChecked(true);		            		            
		            //Data da Ultima Dose
		            DtHUltimadose.updateDate(Integer.valueOf(c.getString(c.getColumnIndex("DT_ULTIMA_DOSE")).toString().substring(c.getString(c.getColumnIndex("DT_ULTIMA_DOSE")).toString().lastIndexOf("/")+1)), 
											 Integer.valueOf(c.getString(c.getColumnIndex("DT_ULTIMA_DOSE")).toString().substring(c.getString(c.getColumnIndex("DT_ULTIMA_DOSE")).toString().indexOf("/")+1,c.getString(c.getColumnIndex("DT_ULTIMA_DOSE")).toString().lastIndexOf("/")))-1, 
											 Integer.valueOf(c.getString(c.getColumnIndex("DT_ULTIMA_DOSE")).toString().substring(0, c.getString(c.getColumnIndex("DT_ULTIMA_DOSE")).toString().indexOf("/"))));		            
		            //Data Ultima Consulta
		    		DtHDtUltimaConsulta.updateDate(Integer.valueOf(c.getString(c.getColumnIndex("DT_ULTIMA_CONSULTA")).toString().substring(c.getString(c.getColumnIndex("DT_ULTIMA_CONSULTA")).toString().lastIndexOf("/")+1)), 
							 					   Integer.valueOf(c.getString(c.getColumnIndex("DT_ULTIMA_CONSULTA")).toString().substring(c.getString(c.getColumnIndex("DT_ULTIMA_CONSULTA")).toString().indexOf("/")+1,c.getString(c.getColumnIndex("DT_ULTIMA_CONSULTA")).toString().lastIndexOf("/")))-1, 
							 					   Integer.valueOf(c.getString(c.getColumnIndex("DT_ULTIMA_CONSULTA")).toString().substring(0, c.getString(c.getColumnIndex("DT_ULTIMA_CONSULTA")).toString().indexOf("/"))));		    				    				    		
		    		EdtHCe.setText(c.getString(c.getColumnIndex("COMUNICANTES_EXAMINADOS")).toString());
		    		EdtHM5Bcg.setText(c.getString(c.getColumnIndex("COMUNICANTES_BCG")).toString());           
		    		EdtHNObservacao.setText(c.getString(c.getColumnIndex("OBSERVACAO")).toString()); 
		        }
		        if (_Diabetes == true){			        	
		        	ts = th.newTabSpec("tag2");
		        	ComponentesDiabetes();
		            ts.setContent(R.teladoenca.tabDiabetes);
		            ts.setIndicator("Diabetes",getResources().getDrawable(R.drawable.diabetes));
		            th.addTab(ts);		           		          
		    				            
		            dDtUltimaConsulta.updateDate(Integer.valueOf(c.getString(c.getColumnIndex("DT_ULTIMA_VISITA")).toString().substring(c.getString(c.getColumnIndex("DT_ULTIMA_VISITA")).toString().lastIndexOf("/")+1)), 
		 					   				     Integer.valueOf(c.getString(c.getColumnIndex("DT_ULTIMA_VISITA")).toString().substring(c.getString(c.getColumnIndex("DT_ULTIMA_VISITA")).toString().indexOf("/")+1,c.getString(c.getColumnIndex("DT_ULTIMA_VISITA")).toString().lastIndexOf("/")))-1, 
		 					   				     Integer.valueOf(c.getString(c.getColumnIndex("DT_ULTIMA_VISITA")).toString().substring(0, c.getString(c.getColumnIndex("DT_ULTIMA_VISITA")).toString().indexOf("/"))));
		            
		    		EdtDObs.setText(c.getString(c.getColumnIndex("OBSERVACAO")).toString());
		    		
		    		if (c.getString(c.getColumnIndex("FL_FAZ_DIETA")).toString().trim().equals("S"))
		    			dFazDieta_S.setChecked(true);
		    		else
		    			dFazDieta_N.setChecked(true);
		    		
		    		if (c.getString(c.getColumnIndex("FL_FAZ_EXCERCICIOS")).toString().trim().equals("S"))
		    			dFazExerc_S.setChecked(true);
		    		else
		    			dFazExerc_N.setChecked(true);
		    		
		    		if (c.getString(c.getColumnIndex("FL_USA_INSULINA")).toString().trim().equals("S"))
		    			dUsaInsulina_S.setChecked(true);
		    		else
		    			dUsaInsulina_N.setChecked(true);
		    		
		    		if (c.getString(c.getColumnIndex("FL_USA_HIPOGLICEMIANTE")).toString().trim().equals("S"))
		    			dHipog_S.setChecked(true);
		    		else
		    			dHipog_N.setChecked(true);
		        }
		       if (_Hipertensao == true){			    	   
		        	ts = th.newTabSpec("tag3");
		        	ComponentesHiperTensao();
		            ts.setContent(R.teladoenca.tabHipertensao);
		            ts.setIndicator("Hipertensao",getResources().getDrawable(R.drawable.hipertensao));
		            th.addTab(ts);
		            //Data Ultima Visita
		            DtHtUltimaVisita.updateDate(Integer.valueOf(c.getString(c.getColumnIndex("DT_ULTIMA_VISITA")).toString().substring(c.getString(c.getColumnIndex("DT_ULTIMA_VISITA")).toString().lastIndexOf("/")+1)), 
		 					   				    Integer.valueOf(c.getString(c.getColumnIndex("DT_ULTIMA_VISITA")).toString().substring(c.getString(c.getColumnIndex("DT_ULTIMA_VISITA")).toString().indexOf("/")+1,c.getString(c.getColumnIndex("DT_ULTIMA_VISITA")).toString().lastIndexOf("/")))-1, 
		 					   				    Integer.valueOf(c.getString(c.getColumnIndex("DT_ULTIMA_VISITA")).toString().substring(0, c.getString(c.getColumnIndex("DT_ULTIMA_VISITA")).toString().indexOf("/"))));
		    		
		            if (c.getString(c.getColumnIndex("FL_FAZ_DIETA")).toString().trim().equals("S")){
		            	RbHiFazDieta_S.setChecked(true);
		    		}else{
		    			RbHiFazDieta_N.setChecked(true);
		    		}
		            	
		            if (c.getString(c.getColumnIndex("FL_TOMA_MEDICACAO")).toString().trim().equals("S")){
		            	RbHiTomaMedic_S.setChecked(true);
		    		}else{
		    			RbHiTomaMedic_N.setChecked(true);
		    		}
		    		
		            if (c.getString(c.getColumnIndex("FL_FAZ_EXERCICIOS")).toString().trim().equals("S")){
		            	RbHiExcercFisico_S.setChecked(true);
		    		}else{
		    			RbHiExcercFisico_N.setChecked(true);
		    		}

		            
		    		EdtHtPe.setText(c.getString(c.getColumnIndex("PRESSAO_ARTERIAL")).toString());
		    		EdtHtObs.setText(c.getString(c.getColumnIndex("OBSERVACAO")).toString());
		        }
		        if (_Gestante == true){			        	
		        	ts = th.newTabSpec("tag4");
		        	ComponentesGestante();
		            ts.setContent(R.teladoenca.tabGestante);
		            ts.setIndicator("Gestante",getResources().getDrawable(R.drawable.gestante));
		            th.addTab(ts);
		            
		            //Data Última Regra		            
		    		GDtUltimaRegra.updateDate(Integer.valueOf(c.getString(c.getColumnIndex("DT_ULTIMA_REGRA")).toString().substring(c.getString(c.getColumnIndex("DT_ULTIMA_REGRA")).toString().lastIndexOf("/")+1)), 
											  Integer.valueOf(c.getString(c.getColumnIndex("DT_ULTIMA_REGRA")).toString().substring(c.getString(c.getColumnIndex("DT_ULTIMA_REGRA")).toString().indexOf("/")+1,c.getString(c.getColumnIndex("DT_ULTIMA_REGRA")).toString().lastIndexOf("/")))-1, 
											  Integer.valueOf(c.getString(c.getColumnIndex("DT_ULTIMA_REGRA")).toString().substring(0, c.getString(c.getColumnIndex("DT_ULTIMA_REGRA")).toString().indexOf("/"))));
		    		//Data Provável do Parto
		    		GDtParto.updateDate(Integer.valueOf(c.getString(c.getColumnIndex("DT_PROVAVEL_PARTO")).toString().substring(c.getString(c.getColumnIndex("DT_PROVAVEL_PARTO")).toString().lastIndexOf("/")+1)), 
		    							Integer.valueOf(c.getString(c.getColumnIndex("DT_PROVAVEL_PARTO")).toString().substring(c.getString(c.getColumnIndex("DT_PROVAVEL_PARTO")).toString().indexOf("/")+1,c.getString(c.getColumnIndex("DT_PROVAVEL_PARTO")).toString().lastIndexOf("/")))-1, 
		    							Integer.valueOf(c.getString(c.getColumnIndex("DT_PROVAVEL_PARTO")).toString().substring(0, c.getString(c.getColumnIndex("DT_PROVAVEL_PARTO")).toString().indexOf("/"))));
		    		//Data de Vacina
		    		//GDtVacina.updateDate(Integer.valueOf(c.getString(c.getColumnIndex("DT_VACINA")).toString().substring(c.getString(c.getColumnIndex("DT_VACINA")).toString().lastIndexOf("/")+1)), 
					//					 Integer.valueOf(c.getString(c.getColumnIndex("DT_VACINA")).toString().substring(c.getString(c.getColumnIndex("DT_VACINA")).toString().indexOf("/")+1,c.getString(c.getColumnIndex("DT_VACINA")).toString().lastIndexOf("/")))-1, 
					//					 Integer.valueOf(c.getString(c.getColumnIndex("DT_VACINA")).toString().substring(0, c.getString(c.getColumnIndex("DT_VACINA")).toString().indexOf("/"))));
		    		//Data de Consulta Puerbio
		    		GDtPuerbio.updateDate(Integer.valueOf(c.getString(c.getColumnIndex("DT_CONSULTA_PUERBIO")).toString().substring(c.getString(c.getColumnIndex("DT_CONSULTA_PUERBIO")).toString().lastIndexOf("/")+1)), 
										  Integer.valueOf(c.getString(c.getColumnIndex("DT_CONSULTA_PUERBIO")).toString().substring(c.getString(c.getColumnIndex("DT_CONSULTA_PUERBIO")).toString().indexOf("/")+1,c.getString(c.getColumnIndex("DT_CONSULTA_PUERBIO")).toString().lastIndexOf("/")))-1, 
										  Integer.valueOf(c.getString(c.getColumnIndex("DT_CONSULTA_PUERBIO")).toString().substring(0, c.getString(c.getColumnIndex("DT_CONSULTA_PUERBIO")).toString().indexOf("/"))));
		    		//Data Pré-Natal		    		
		    		DtGPreNatal.updateDate(Integer.valueOf(c.getString(c.getColumnIndex("DT_PRE_NATAL")).toString().substring(c.getString(c.getColumnIndex("DT_PRE_NATAL")).toString().lastIndexOf("/")+1)), 
							        	   Integer.valueOf(c.getString(c.getColumnIndex("DT_PRE_NATAL")).toString().substring(c.getString(c.getColumnIndex("DT_PRE_NATAL")).toString().indexOf("/")+1,c.getString(c.getColumnIndex("DT_PRE_NATAL")).toString().lastIndexOf("/")))-1, 
							        	   Integer.valueOf(c.getString(c.getColumnIndex("DT_PRE_NATAL")).toString().substring(0, c.getString(c.getColumnIndex("DT_PRE_NATAL")).toString().indexOf("/"))));
		    		
		    		EdtGEn.setText(c.getString(c.getColumnIndex("EST_NUTRICIONAL")).toString());
		    		EdtGObs.setText(c.getString(c.getColumnIndex("OBSERVACAO")).toString());
		    		EdtMesGestacao.setText(c.getString(c.getColumnIndex("MES_GESTACAO")).toString());
		    		
		    		/*if (c.getString(c.getColumnIndex("TIPO_VACINA")).toString().trim().equals("1"))
		    			ChGUm.setChecked(true);
		    		else if (c.getString(c.getColumnIndex("TIPO_VACINA")).toString().trim().equals("2"))
		    			ChG2.setChecked(true);
		    		else if (c.getString(c.getColumnIndex("TIPO_VACINA")).toString().trim().equals("R"))
		    			ChGR.setChecked(true);*/
		    		
		    		
		    		if (c.getString(c.getColumnIndex("FATORES_RISCO")).substring(0, 1).toString().trim().equals("S"))
		    			ChGGestacoes.setChecked(true);
		    		else
		    			ChGGestacoes.setChecked(false);
		    		
		    		if (c.getString(c.getColumnIndex("FATORES_RISCO")).substring(1, 2).toString().trim().equals("S"))		    		
		    			ChGIdade36.setChecked(true);
		    		else
		    			ChGIdade36.setChecked(false);
		    		
		    		if (c.getString(c.getColumnIndex("FATORES_RISCO")).substring(2, 3).toString().trim().equals("S"))
		    			ChGSangue.setChecked(true);
		    		else
		    			ChGSangue.setChecked(false);
		    		
		    		if (c.getString(c.getColumnIndex("FATORES_RISCO")).substring(3, 4).toString().trim().equals("S"))
		    			ChGDiabetes.setChecked(true);
		    		else
		    			ChGDiabetes.setChecked(false);
		    		
		    		if (c.getString(c.getColumnIndex("FATORES_RISCO")).substring(4, 5).toString().trim().equals("S"))
		    			ChGAborto.setChecked(true);
		    		else
		    			ChGAborto.setChecked(false);
		    		
		    		if (c.getString(c.getColumnIndex("FATORES_RISCO")).substring(5, 6).toString().trim().equals("S"))
		    			ChGIdade20.setChecked(true);
		    		else
		    			ChGIdade20.setChecked(false);
		    		
		    		if (c.getString(c.getColumnIndex("FATORES_RISCO")).substring(6, 7).toString().trim().equals("S"))
		    			ChGEdema.setChecked(true); 		    		 
		    		else
		    			ChGEdema.setChecked(false);
		    		
		    		if (c.getString(c.getColumnIndex("FATORES_RISCO")).substring(7, 8).toString().trim().equals("S"))
		    			ChGPressao.setChecked(true);
		    		else
		    			ChGPressao.setChecked(false);
		       }
		        if (_Tuberculose == true){			
		        	ts = th.newTabSpec("tag5");
		        	ComponentesTuberculose();
		            ts.setContent(R.teladoenca.tabTuberculose);
		            ts.setIndicator("Tuberculose",getResources().getDrawable(R.drawable.tuberculose));
		            th.addTab(ts);
		           
		            if (c.getString(c.getColumnIndex("FL_MEDIC_DIARIA")).toString().trim().equals("S"))
		            	RbTMedicDiaria_S.setChecked(true);
		            else
		            	RbTMedicDiaria_N.setChecked(true);		            
		            	
		    		if (c.getString(c.getColumnIndex("FL_REACOES_IND")).toString().trim().equals("S"))
		    			RbTReacoesIndesejaveis_S.setChecked(true);
		    		else
		    			RbTReacoesIndesejaveis_N.setChecked(true);
		    		
		    		if (c.getString(c.getColumnIndex("FL_EXAME_ESCARRO")).toString().trim().equals("S"))
		    			RbTExameEscarro_S.setChecked(true);
		    		else
		    			RbTExameEscarro_N.setChecked(true);
		    					    
		    		EdtTCe.setText(c.getString(c.getColumnIndex("COMUNIC_EXAMINADOS")).toString());
		    		EdtTM5Bcg.setText(c.getString(c.getColumnIndex("MENOR_BCG")).toString());				 
		    		EdtTObs.setText(c.getString(c.getColumnIndex("OBSERVACAO")).toString());
		       }
			}else{
				Mensagem.exibeMessagem(this, "SCS", "Nenhum Acompanhamento Encontrado!");
			}
			
		}catch(Exception e){
			Mensagem.exibeMessagem(this, "SCS-ERRO", e.getMessage());
		}finally{
			if (c != null){
				c.close();
			}
			if (bd != null){
				bd.fechaBanco();
			}
		}//Fim do Finally
	}
	
	public void InicializaTelas(){
		Banco bd = null;
		Cursor c = null;
			try{
				bd = new Banco(this);
				bd.open();				
				c = bd.consulta("residente", new String[]{"*"}, "_ID = "+String.valueOf(COD_FAMILAR), null, null, null, null, null);					
				
				c.moveToFirst();
				
				if (c.getCount()>0){
					
					_HASH = c.getString(c.getColumnIndex("HASH")).toString().trim();
					
					TabHost th = (TabHost) findViewById(R.teladoenca.tabhost);
			        th.setup();
			        TabSpec ts;
			        
			        if (c.getString(c.getColumnIndex("FL_HANSENIASE")).toString().trim().equals("S")){
			        	_Hanseniase = true;
			        	ts = th.newTabSpec("tag1");
			        	ComponentesHanseniase();
			            ts.setContent(R.teladoenca.tabHanseniase);
			            ts.setIndicator("Hanseniase",getResources().getDrawable(R.drawable.hanseniase));
			            th.addTab(ts);
			        }
			        if (c.getString(c.getColumnIndex("FL_DIABETE")).toString().trim().equals("S")){
			        	_Diabetes = true;
			        	ts = th.newTabSpec("tag2");
			        	ComponentesDiabetes();
			            ts.setContent(R.teladoenca.tabDiabetes);
			            ts.setIndicator("Diabetes",getResources().getDrawable(R.drawable.diabetes));
			            th.addTab(ts);
			        }
			       if (c.getString(c.getColumnIndex("FL_HIPERTENSAO")).toString().trim().equals("S")){
			    	   _Hipertensao = true;
			        	ts = th.newTabSpec("tag3");
			        	ComponentesHiperTensao();
			            ts.setContent(R.teladoenca.tabHipertensao);
			            ts.setIndicator("Hipertensao",getResources().getDrawable(R.drawable.hipertensao));
			            th.addTab(ts);
			        }
			        if (c.getString(c.getColumnIndex("FL_GESTANTE")).toString().trim().equals("S")){
			        	_Gestante = true;
			        	ts = th.newTabSpec("tag4");
			        	ComponentesGestante();
			            ts.setContent(R.teladoenca.tabGestante);
			            ts.setIndicator("Gestante",getResources().getDrawable(R.drawable.gestante));
			            th.addTab(ts);
			       }
			        if (c.getString(c.getColumnIndex("FL_TUBERCULOSE")).toString().trim().equals("S")){
			        	_Tuberculose = true;
			        	ts = th.newTabSpec("tag5");
			        	ComponentesTuberculose();
			            ts.setContent(R.teladoenca.tabTuberculose);
			            ts.setIndicator("Tuberculose",getResources().getDrawable(R.drawable.tuberculose));
			            th.addTab(ts);
			       }
				}else{
					Mensagem.exibeMessagem(this, "SCS", "Nenhum familiar cadastrado com esse código!");
				}
				
			}catch(Exception e){
				Mensagem.exibeMessagem(this, "SCS-ERRO", e.getMessage());
			}finally{
				if (c != null){
					c.close();
				}
				if (bd != null){
					bd.fechaBanco();
				}
			}
	}
	
	public void ComponentesHanseniase(){
      	//datavisita.setVisibility(0);
    	medicacaodiaria.setVisibility(0);
    	ultimadose.setVisibility(0);
    	cuidados.setVisibility(0);
    	comunicantes.setVisibility(0);
    	bcg.setVisibility(0);
    	//DtHVisita.setVisibility(0);
    	DtHUltimadose.setVisibility(0);
    	rgmedicacaodiaria.setVisibility(0);
    	autocuidados.setVisibility(0);
		EdtHCe.setVisibility(0);
		EdtHM5Bcg.setVisibility(0);
		TxtHNComunicantes.setVisibility(0);
		EdtHNObservacao.setVisibility(0);
		TxtHDtUltimaConsulta.setVisibility(0);
		DtHDtUltimaConsulta.setVisibility(0);
	}
	
	public void ComponentesDiabetes(){
		dUltimaVisita.setVisibility(0);
		//dDataVisita.setVisibility(0); 
		dFazDieta.setVisibility(0);
		dFazExercicio.setVisibility(0); 
		dUsaInsulina.setVisibility(0);
		dTomaHipo.setVisibility(0);  
		//dDtDataVisita.setVisibility(0); 
		dDtUltimaConsulta.setVisibility(0); 
		dRgFazExercicio.setVisibility(0);
		dRgFazDieta.setVisibility(0);
		dRgInsulina.setVisibility(0);
		dRgHipoglicemiante.setVisibility(0);
		TxtDObs.setVisibility(0);
		EdtDObs.setVisibility(0);
	}
	
	public void ComponentesTuberculose(){
		TxtTCe.setVisibility(0);
		//TxtTDataVisita.setVisibility(0);
		TxtTEe.setVisibility(0);
		TxtTM5Bcg.setVisibility(0);
		TxtTMd.setVisibility(0);
		TxtTRi.setVisibility(0);
		//DtTDataVisita.setVisibility(0);
		RgTEe.setVisibility(0);
		RgTMd.setVisibility(0);
		RgTRi.setVisibility(0);
		EdtTCe.setVisibility(0);
		EdtTM5Bcg.setVisibility(0);
		TxtTObs.setVisibility(0);
		EdtTObs.setVisibility(0);

	}
	
	public void ComponentesGestante(){
		TxtGDtUltimaRegra.setVisibility(0); 
		TxtGDtParto.setVisibility(0) ;
		TxtGDtVacina.setVisibility(0); 
		TxtGEn.setVisibility(0);
		TxtGDtPN.setVisibility(0); 
		TxtGFr.setVisibility(0); 
		TxtGRG.setVisibility(0); 
		TxtGDtPuerbio.setVisibility(0);
		TxtGEnMes.setVisibility(0);
		GDtUltimaRegra.setVisibility(0); 
		GDtParto.setVisibility(0);  
		GDtVacina.setVisibility(0);  
		GDtPuerbio.setVisibility(0);
		DtGPreNatal.setVisibility(0);
		EdtGEn.setVisibility(0);
		EdtMesGestacao.setVisibility(0);
		ChGGestacoes.setVisibility(0); 
		ChGAborto.setVisibility(0); 
		ChGIdade36.setVisibility(0); 
		ChGIdade20.setVisibility(0); 
		ChGSangue.setVisibility(0);
		ChGEdema.setVisibility(0);
		ChGDiabetes.setVisibility(0);
		ChGPressao.setVisibility(0);
		ChGUm.setVisibility(0); 
		ChG2.setVisibility(0);
		ChGR.setVisibility(0);
		TxtGObs.setVisibility(0);
		EdtGObs.setVisibility(0);
	}
	
	public void ComponentesHiperTensao(){
		//TxtHtDataVisita.setVisibility(0);
		TxtHtFd.setVisibility(0); 
		TxtHtTm.setVisibility(0); 
		TxtHtFe.setVisibility(0); 
		TxtHtPa.setVisibility(0); 
		TxtHtDtUV.setVisibility(0);
		//DtHtDataVisita.setVisibility(0);
		DtHtUltimaVisita.setVisibility(0);
		RgHtFd.setVisibility(0); 
		RgHtTm.setVisibility(0); 
		RgHtFe.setVisibility(0);
		EdtHtPe.setVisibility(0);
		TxtHtObs.setVisibility(0);
		EdtHtObs.setVisibility(0);
	}
	
	public void Inserir(){
		
		String msgInsercao = "";
		
		/*GESTANTE*/
		if (_Gestante==true){
			
			Gestante g = null;
			
			try{
				String fatores_risco = "";
				g = new Gestante();
				g.DT_ULTIMA_REGRA     = String.valueOf(GDtUltimaRegra.getDayOfMonth())+"/"+String.valueOf(GDtUltimaRegra.getMonth()+1)+"/"+String.valueOf(GDtUltimaRegra.getYear());
				g.DT_PROVAVEL_PARTO   = String.valueOf(GDtParto.getDayOfMonth())+"/"+String.valueOf(GDtParto.getMonth()+1)+"/"+String.valueOf(GDtParto.getYear());
				g.DT_VACINA           = String.valueOf(GDtVacina.getDayOfMonth())+"/"+String.valueOf(GDtVacina.getMonth()+1)+"/"+String.valueOf(GDtVacina.getYear());
				g.DT_PRE_NATAL        = String.valueOf(DtGPreNatal.getDayOfMonth())+"/"+String.valueOf(DtGPreNatal.getMonth()+1)+"/"+String.valueOf(DtGPreNatal.getYear());
				g.DT_CONSULTA_PUERBIO = String.valueOf(GDtPuerbio.getDayOfMonth())+"/"+String.valueOf(GDtPuerbio.getMonth()+1)+"/"+String.valueOf(GDtPuerbio.getYear());
				g.EST_NUTRICIONAL     = EdtGEn.getText().toString();
				g.MES_GESTACAO        = EdtMesGestacao.getText().toString();
				g.OBSERVACAO          = EdtGObs.getText().toString();
				g.HASH                = _HASH;
				
				
				//Tipo de Vacina
				if (ChGUm.isChecked())
				  g.TIPO_VACINA = "1";
				else if (ChG2.isChecked())
					g.TIPO_VACINA = "2";
				else if (ChGR.isChecked())
					g.TIPO_VACINA = "R";
				
				//Fatores de Risco
				if (ChGGestacoes.isChecked())
					fatores_risco += "S";
				else
					fatores_risco += "N";
				if (ChGIdade36.isChecked())
					fatores_risco += "S";
				else
					fatores_risco += "N";
				if (ChGSangue.isChecked())
					fatores_risco += "S";
				else
					fatores_risco += "N";
				if (ChGDiabetes.isChecked())
					fatores_risco += "S";
				else
					fatores_risco += "N";
				if (ChGAborto.isChecked())
					fatores_risco += "S";
				else
					fatores_risco += "N";
				if (ChGIdade20.isChecked())
					fatores_risco += "S";
				else
					fatores_risco += "N";
				if (ChGEdema.isChecked())
					fatores_risco += "S";
				else
					fatores_risco += "N";
				if (ChGPressao.isChecked())
					fatores_risco += "S";
				else
					fatores_risco += "N";
				
				g.FATORES_RISCO = fatores_risco;
				
				if (_IdTransacao == 0){
					if (g.Inserir(this) == true){
						msgInsercao += "Gestante - Gravado\n";
					}else{
						msgInsercao += "Gestante - Erro\n";
					}
				}else{
					if (g.Atualizar(this,_IdTransacao) == true){
						msgInsercao += "Gestante - Atualizado\n";
					}else{
						msgInsercao += "Gestante - Erro\n";
					}
				}
			}finally{
				g = null;			
			}
				
		}/*FIM GESTANTE*/
		
		/*HANSENIASE*/
		if (_Hanseniase == true){
			
			Hanseniase h = null;
			
			try{
				
				h = new Hanseniase();
				//h.DT_VISITA				  = String.valueOf(DtHVisita.getDayOfMonth())+"/"+String.valueOf(DtHVisita.getMonth()+1)+"/"+String.valueOf(DtHVisita.getYear());
				h.DT_ULTIMA_CONSULTA	  = String.valueOf(DtHDtUltimaConsulta.getDayOfMonth())+"/"+String.valueOf(DtHDtUltimaConsulta.getMonth()+1)+"/"+String.valueOf(DtHDtUltimaConsulta.getYear());
				h.DT_ULTIMA_DOSE		  = String.valueOf(DtHUltimadose.getDayOfMonth())+"/"+String.valueOf(DtHUltimadose.getMonth()+1)+"/"+String.valueOf(DtHUltimadose.getYear());
				h.COMUNICANTES_EXAMINADOS = Integer.valueOf(EdtHCe.getText().toString());
				h.COMUNICANTES_BCG		  = Integer.valueOf(EdtHM5Bcg.getText().toString());
				h.OBS 				      = EdtHNObservacao.getText().toString();
				h.HASH                    = _HASH;
				
				//Toma Medicacao
				if (RbHMdSim.isChecked()){
					h.TOMA_MEDICACAO = "S";
				}
				if (RbHMdNao.isChecked()){
					h.TOMA_MEDICACAO = "N";
				}
				//Auto Cuidados
				if (RbHAcSim.isChecked()){
					h.AUTO_CUIDADOS = "S";
				}
				if (RbHAcNao.isChecked()){
					h.AUTO_CUIDADOS = "N";
				}
				
				if (_IdTransacao == 0){
					if (h.Inserir(this) == true){
						msgInsercao += "Hanseníase - Gravado\n";
					}else{
						msgInsercao += "Hanseníase - Erro\n";
					}
				}else{
					if (h.Atualizar(this,_IdTransacao) == true){
						msgInsercao += "Hanseníase - Atualizado\n";
					}else{
						msgInsercao += "Hanseníase - Erro\n";
					}
				}
			
			}finally{
				h = null;
			}
				
		}/*FIM HANSENIASE*/
		
		/*DIABETE*/
		if (_Diabetes == true){
			
			Diabete d = null;
			
			try{
				d = new Diabete();
				d.HASH	     = _HASH;
				//d.DT_VISITA  = String.valueOf(dDtDataVisita.getDayOfMonth())+"/"+String.valueOf(dDtDataVisita.getMonth()+1)+"/"+String.valueOf(dDtDataVisita.getYear());
				d.OBSERVACAO = EdtDObs.getText().toString();
				d.DT_ULTIMA_VISITA = String.valueOf(dDtUltimaConsulta.getDayOfMonth())+"/"+String.valueOf(dDtUltimaConsulta.getMonth()+1)+"/"+String.valueOf(dDtUltimaConsulta.getYear());
				
				//Faz Dieta
				if (dFazDieta_S.isChecked())
					d.FL_FAZ_DIETA = "S";
				else if (dFazDieta_N.isChecked())
					d.FL_FAZ_DIETA = "N";
				
				//Faz Exercícios Físicos
				if (dFazExerc_S.isChecked())
					d.FL_FAZ_EXCERCICIOS = "S";
				else if (dFazExerc_N.isChecked())
					d.FL_FAZ_EXCERCICIOS = "N";
				
				//Usa Insulina
				if (dUsaInsulina_S.isChecked())
					d.FL_USA_INSULINA = "S";
				else if (dUsaInsulina_N.isChecked())
					d.FL_USA_INSULINA = "N";
				
				//Usa Hipoglicemiante Oral
				if (dHipog_S.isChecked())
					d.FL_USA_HIPOGLICEMIANTE = "S";
				else if (dHipog_N.isChecked())
					d.FL_USA_HIPOGLICEMIANTE = "N";
				
				if (_IdTransacao == 0){
					if (d.Inserir(this) == true){
						msgInsercao += "Diabete - Gravado\n";
					}else{
						msgInsercao += "Diabete - Erro\n";
					}
				}else{
					if (d.Atualizar(this,_IdTransacao) == true){
						msgInsercao += "Diabete - Atualizado\n";
					}else{
						msgInsercao += "Diabete - Erro\n";
					}
				}
				
			}finally{
				d = null;
			}
			
		}/*FIM DIABETE*/
		
		/*TUBERCULOSE*/
		if (_Tuberculose == true){
			
			Tuberculose t = null;
			
			try{
				t = new Tuberculose();
				t.HASH               = _HASH; 
				//t.DT_VISITA 		 = String.valueOf(DtTDataVisita.getDayOfMonth())+"/"+String.valueOf(DtTDataVisita.getMonth()+1)+"/"+String.valueOf(DtTDataVisita.getYear());
				t.COMUNIC_EXAMINADOS = EdtTCe.getText().toString();
				t.MENOR_BCG 		 = EdtTM5Bcg.getText().toString();
				t.OBSERVACAO 	     = EdtTObs.getText().toString();
				
				//Toma Medicação Diária
				if (RbTMedicDiaria_S.isChecked())
					t.FL_MEDIC_DIARIA = "S";
				else if (RbTMedicDiaria_N.isChecked())
					t.FL_MEDIC_DIARIA = "N";
				
				//Reações Indesejáveis
				if (RbTReacoesIndesejaveis_S.isChecked())
					t.FL_REACOES_IND = "S";
				else if (RbTReacoesIndesejaveis_N.isChecked())
					t.FL_REACOES_IND = "N";
				
				//Exame de Escarro
				if (RbTExameEscarro_S.isChecked())
					t.FL_EXAME_ESCARRO = "S";
				else if (RbTExameEscarro_N.isChecked())
					t.FL_EXAME_ESCARRO = "N";
				
				if (_IdTransacao == 0){
					if (t.Inserir(this) == true){
						msgInsercao += "Tuberculose - Gravado\n";
					}else{
						msgInsercao += "Tuberculose - Erro\n";
					}
				}else{
					if (t.Atualizar(this,_IdTransacao) == true){
						msgInsercao += "Tuberculose - Atualizado\n";
					}else{
						msgInsercao += "Tuberculose - Erro\n";
					}
				}
				
			}finally{
				t = null;
			}
			
		}/*FIM TUBERCULOSE*/
		
		/*HIPERTENSÃO*/
		if (_Hipertensao == true){
			
			Hipertensao hi = null;
			
			try{
				hi = new Hipertensao();
				hi.HASH = _HASH;
				//hi.DT_VISITA = String.valueOf(DtHtDataVisita.getDayOfMonth())+"/"+String.valueOf(DtHtDataVisita.getMonth()+1)+"/"+String.valueOf(DtHtDataVisita.getYear());
				hi.DT_ULTIMA_VISITA = String.valueOf(DtHtUltimaVisita.getDayOfMonth())+"/"+String.valueOf(DtHtUltimaVisita.getMonth()+1)+"/"+String.valueOf(DtHtUltimaVisita.getYear());
				hi.PRESSAO_ARTERIAL = EdtHtPe.getText().toString();
				hi.OBSERVACAO = EdtHtObs.getText().toString();
				
				//Faz Dieta
				if (RbHiFazDieta_S.isChecked())
					hi.FL_FAZ_DIETA = "S";
				else if (RbHiFazDieta_N.isChecked())	
					hi.FL_FAZ_DIETA = "N";
				
				//Toma Medicação
				if (RbHiTomaMedic_S.isChecked())
					hi.FL_TOMA_MEDICACAO = "S";
				else if (RbHiTomaMedic_N.isChecked())	
					hi.FL_TOMA_MEDICACAO = "N";
				
				//Faz Exercícios Físicos
				if (RbHiExcercFisico_S.isChecked())
					hi.FL_FAZ_EXERCICIOS = "S";
				else if (RbHiExcercFisico_N.isChecked())	
					hi.FL_FAZ_EXERCICIOS = "N";
				
				if (_IdTransacao == 0){
					if (hi.Inserir(this) == true){
						msgInsercao += "Hipertensão - Gravado\n";
					}else{
						msgInsercao += "Hipertensão - Erro\n";
					}
				}else{
					if (hi.Atualizar(this,_IdTransacao) == true){
						msgInsercao += "Hipertensão - Atualizado\n";
					}else{
						msgInsercao += "Hipertensão - Erro\n";
					}
				}
				
			}finally{
				hi = null;
			}
			
		}/*FIM HIPERTENSÃO*/
		
		Mensagem.exibeMessagem(TelaDoenca.this, "SCS - Acompanhamento", msgInsercao,2000);
		new Handler().postDelayed(new Runnable() {		
			public void run() {
				finish();
			}
		}, 2000);
	}
}
