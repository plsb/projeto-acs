package br.com.view;

import br.com.control.Banco;
import br.com.control.Gestante;
import br.com.control.Mensagem;
import br.com.scs.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TelaDoenca extends Activity{
	
	public static int COD_FAMILAR = 0;
	
	public static boolean _Hanseniase  = false;
	public static boolean _Diabetes    = false;
	public static boolean _Hipertensao = false;
	public static boolean _Gestante	  = false;
	public static boolean _Tuberculose = false;
	
	TextView datavisita, medicacaodiaria, ultimadose, cuidados, comunicantes, bcg; //Hanseniase
	DatePicker dataprofissional, ultimadata; //hanseniase
	RadioGroup rgmedicacaodiaria, autocuidados; //hanseniase
	EditText EdtHCe, EdtHM5Bcg;
	 
	

	TextView dDataVisita, dFazDieta, dFazExercicio, dUsaInsulina, dTomaHipo, dUltimaVisita; //Diabetes
	DatePicker dDtDataVisita, dDtUltimaConsulta; //Diabetes
	RadioGroup dRgFazExercicio, dRgFazDieta, dRgInsulina, dRgHipoglicemiante;//Diabetes
	
	TextView TxtTDataVisita, TxtTMd, TxtTRi, TxtTEe, TxtTCe, TxtTM5Bcg;
	DatePicker DtTDataVisita;
	RadioGroup RgTMd, RgTRi, RgTEe;
	EditText EdtTCe, EdtTM5Bcg;
	
	TextView TxtHtDataVisita, TxtHtFd, TxtHtTm, TxtHtFe, TxtHtPa, TxtHtDtUV;
	DatePicker DtHtDataVisita, DtHtUltimaVisita;
	RadioGroup RgHtFd, RgHtTm, RgHtFe;
	EditText EdtHtPe;
	
	TextView TxtGDtUltimaRegra, TxtGDtParto, TxtGDtVacina, TxtGEn, TxtGDtPN, TxtGFr, TxtGRG, TxtGDtPuerbio, TxtGEnMes;
	DatePicker GDtUltimaRegra, GDtParto, GDtVacina, GDtPuerbio, DtGPreNatal;
	CheckBox ChGGestacoes, ChGAborto, ChGIdade36, ChGIdade20, ChGSangue, ChGEdema, ChGDiabetes, ChGPressao, ChGUm, ChG2, ChGR;
	EditText EdtGEn,EdtMesGestacao;
	

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.teladoenca);
		
		//Componentes Hanseniase
		datavisita = (TextView) findViewById(R.teladoenca.TxtHDataVisita);
		medicacaodiaria = (TextView) findViewById(R.teladoenca.TxtHMedicacaoDiaria);
		ultimadose = (TextView) findViewById(R.teladoenca.TxtHDataUltimaDose);
		cuidados = (TextView) findViewById(R.teladoenca.TxtHFazCuidados);
		comunicantes = (TextView) findViewById(R.teladoenca.TxtHComunicantes);
		bcg = (TextView) findViewById(R.teladoenca.TxtHBCG);
		dataprofissional = (DatePicker) findViewById(R.teladoenca.DtHDataProfissional);
		ultimadata = (DatePicker) findViewById(R.teladoenca.DtHUltimaData);
		rgmedicacaodiaria = (RadioGroup) findViewById(R.teladoenca.RgHMedicacaoDiaria);
		autocuidados = (RadioGroup) findViewById(R.teladoenca.RgHAutoCuidados);
		EdtHCe = (EditText) findViewById(R.teladoenca.EdtHCe);
		EdtHM5Bcg = (EditText) findViewById(R.teladoenca.EdtHMenor5Bcg);
		//Componentes Hanseniase
		
		//Componentes Diabetes
		dUltimaVisita = (TextView) findViewById(R.teladoenca.TxtDUltimaVisita);
		dDataVisita = (TextView) findViewById(R.teladoenca.TxtDDataVisita);
		dFazDieta = (TextView) findViewById(R.teladoenca.TxtDFazDieta);
		dFazExercicio = (TextView) findViewById(R.teladoenca.TxtDFazExercicio);
		dUsaInsulina = (TextView) findViewById(R.teladoenca.TxtDInsulina);
		dTomaHipo = (TextView) findViewById(R.teladoenca.TxtDHipoglicemiante);
		dDtDataVisita = (DatePicker) findViewById(R.teladoenca.DtDDataProfissional);
		dDtUltimaConsulta = (DatePicker) findViewById(R.teladoenca.DtDUltimaConsulta);
		dRgFazExercicio = (RadioGroup) findViewById(R.teladoenca.RgDFazExercicio);
		dRgFazDieta = (RadioGroup) findViewById(R.teladoenca.RgDDieta);
		dRgInsulina = (RadioGroup) findViewById(R.teladoenca.RgDInsulina);
		dRgHipoglicemiante = (RadioGroup) findViewById(R.teladoenca.RgDHipoglicemiante);
		
		
		//Componentes tuberculose
		TxtTDataVisita = (TextView) findViewById(R.teladoenca.TxtTDataVisita);
		TxtTMd = (TextView) findViewById(R.teladoenca.TxtTMedicacaoDiaria);
		TxtTRi = (TextView) findViewById(R.teladoenca.TxtTReacoesIndesejadas);
		TxtTEe = (TextView) findViewById(R.teladoenca.TxtTExameEscarro);
		TxtTCe = (TextView) findViewById(R.teladoenca.TxtTComunicantesExaminados);
		TxtTM5Bcg = (TextView) findViewById(R.teladoenca.TxtTMenor5Bcg);
		DtTDataVisita = (DatePicker) findViewById(R.teladoenca.DtTDataVisita);
		RgTMd = (RadioGroup) findViewById(R.teladoenca.RgTMedicacaoDiaria);
		RgTEe = (RadioGroup) findViewById(R.teladoenca.RgTExameEscarro);
		RgTRi = (RadioGroup) findViewById(R.teladoenca.RgTReacoesIndesejadas);
		EdtTCe = (EditText) findViewById(R.teladoenca.EdtTCe);
		EdtTM5Bcg = (EditText) findViewById(R.teladoenca.EdtTMenor5Bcg);
		
		//Componentes Hipertensao
		TxtHtDataVisita = (TextView) findViewById(R.teladoenca.TxtHtDataVisita);
		TxtHtFd = (TextView) findViewById(R.teladoenca.TxtHtFazDieta); 
		TxtHtTm = (TextView) findViewById(R.teladoenca.TxtHtTomaMedicacao);
		TxtHtFe = (TextView) findViewById(R.teladoenca.TxtHtFazExercicio); 
		TxtHtPa = (TextView) findViewById(R.teladoenca.TxtHtPressaoArterial); 
		TxtHtDtUV = (TextView) findViewById(R.teladoenca.TxtHtDtUltimaVisita);
		DtHtDataVisita = (DatePicker) findViewById(R.teladoenca.DtHtDataVisita);
		DtHtUltimaVisita = (DatePicker) findViewById(R.teladoenca.DtHtUltimaVisita);
		RgHtFd = (RadioGroup) findViewById(R.teladoenca.RgHtDieta); 
		RgHtTm = (RadioGroup) findViewById(R.teladoenca.RgHtMedicacao); 
		RgHtFe = (RadioGroup) findViewById(R.teladoenca.RgHtFazExercicio);
		EdtHtPe = (EditText) findViewById(R.teladoenca.EdtHtPressaoArterial);
		
		//Componentes Gestante
		TxtGDtUltimaRegra = (TextView) findViewById(R.teladoenca.TxtGDtUltimaRegra);
		TxtGDtParto = (TextView) findViewById(R.teladoenca.TxtGParto);
		TxtGDtVacina = (TextView) findViewById(R.teladoenca.TxtGVacina);
		TxtGEn = (TextView) findViewById(R.teladoenca.TxtGEn);
		TxtGDtPN = (TextView) findViewById(R.teladoenca.TxtGPreNatal);
		TxtGFr = (TextView) findViewById(R.teladoenca.TxtGFr);
		TxtGRG = (TextView) findViewById(R.teladoenca.TxtGRga);
		TxtGDtPuerbio = (TextView) findViewById(R.teladoenca.TxtGDtPuerbio);
		TxtGEnMes = (TextView) findViewById(R.teladoenca.TxtGEnMes);
		GDtUltimaRegra = (DatePicker) findViewById(R.teladoenca.DtGDataRegra);
		GDtParto = (DatePicker) findViewById(R.teladoenca.DtGParto);
		GDtVacina = (DatePicker) findViewById(R.teladoenca.DtGVacina);
		GDtPuerbio = (DatePicker) findViewById(R.teladoenca.DtGDtPuerbio);
		GDtUltimaRegra = (DatePicker) findViewById(R.teladoenca.DtGDataRegra); 
		GDtParto = (DatePicker) findViewById(R.teladoenca.DtGParto);  
		GDtVacina = (DatePicker) findViewById(R.teladoenca.DtGVacina);  
		GDtPuerbio = (DatePicker) findViewById(R.teladoenca.DtGDtPuerbio);
		DtGPreNatal = (DatePicker) findViewById(R.teladoenca.DtGPreNatal);
		EdtGEn = (EditText) findViewById(R.teladoenca.EdtGEn);
		EdtMesGestacao = (EditText) findViewById(R.teladoenca.EdtMesGestacao);
		ChGGestacoes = (CheckBox) findViewById(R.teladoenca.ChGGestacoes); 
		ChGAborto = (CheckBox) findViewById(R.teladoenca.ChGAborto);  
		ChGIdade36 = (CheckBox) findViewById(R.teladoenca.ChGIdade36);  
		ChGIdade20 = (CheckBox) findViewById(R.teladoenca.ChGIdade20); 
		ChGSangue = (CheckBox) findViewById(R.teladoenca.ChGSangue); 
		ChGEdema = (CheckBox) findViewById(R.teladoenca.ChGEdema); 
		ChGDiabetes = (CheckBox) findViewById(R.teladoenca.ChGDiabetes); 
		ChGPressao = (CheckBox) findViewById(R.teladoenca.ChGPressao);
		ChGUm = (CheckBox) findViewById(R.teladoenca.ChGUm); 
		ChG2 = (CheckBox) findViewById(R.teladoenca.ChG2);
		ChGR = (CheckBox) findViewById(R.teladoenca.ChGR);	
		
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
			//if (validaCampos()==true){
			//	PreparaInsercao();
			//}
			break;
		}
		return true;
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
      	datavisita.setVisibility(0);
    	medicacaodiaria.setVisibility(0);
    	ultimadose.setVisibility(0);
    	cuidados.setVisibility(0);
    	comunicantes.setVisibility(0);
    	bcg.setVisibility(0);
    	dataprofissional.setVisibility(0);
    	ultimadata.setVisibility(0);
    	rgmedicacaodiaria.setVisibility(0);
    	autocuidados.setVisibility(0);
		EdtHCe.setVisibility(0);
		EdtHM5Bcg.setVisibility(0);
	}
	
	public void ComponentesDiabetes(){
		dUltimaVisita.setVisibility(0);
		dDataVisita.setVisibility(0); 
		dFazDieta.setVisibility(0);
		dFazExercicio.setVisibility(0); 
		dUsaInsulina.setVisibility(0);
		dTomaHipo.setVisibility(0);  
		dDtDataVisita.setVisibility(0); 
		dDtUltimaConsulta.setVisibility(0); 
		dRgFazExercicio.setVisibility(0);
		dRgFazDieta.setVisibility(0);
		dRgInsulina.setVisibility(0);
		dRgHipoglicemiante.setVisibility(0);
	}
	
	public void ComponentesTuberculose(){
		TxtTCe.setVisibility(0);
		TxtTDataVisita.setVisibility(0);
		TxtTEe.setVisibility(0);
		TxtTM5Bcg.setVisibility(0);
		TxtTMd.setVisibility(0);
		TxtTRi.setVisibility(0);
		DtTDataVisita.setVisibility(0);
		RgTEe.setVisibility(0);
		RgTMd.setVisibility(0);
		RgTRi.setVisibility(0);
		EdtTCe.setVisibility(0);
		EdtTM5Bcg.setVisibility(0);

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
	}
	
	public void ComponentesHiperTensao(){
		TxtHtDataVisita.setVisibility(0);
		TxtHtFd.setVisibility(0); 
		TxtHtTm.setVisibility(0); 
		TxtHtFe.setVisibility(0); 
		TxtHtPa.setVisibility(0); 
		TxtHtDtUV.setVisibility(0);
		DtHtDataVisita.setVisibility(0);
		DtHtUltimaVisita.setVisibility(0);
		RgHtFd.setVisibility(0); 
		RgHtTm.setVisibility(0); 
		RgHtFe.setVisibility(0);
		EdtHtPe.setVisibility(0);
	}
	
	public void Inserir(){
		
		if (_Gestante==true){
			
			String fatores_risco = "";
			Gestante g = new Gestante();
			g.DT_ULTIMA_REGRA     = String.valueOf(GDtUltimaRegra.getDayOfMonth())+"/"+String.valueOf(GDtUltimaRegra.getMonth()+1)+"/"+String.valueOf(GDtUltimaRegra.getYear());
			g.DT_PROVAVEL_PARTO   = String.valueOf(GDtParto.getDayOfMonth())+"/"+String.valueOf(GDtParto.getMonth()+1)+"/"+String.valueOf(GDtParto.getYear());
			g.DT_VACINA           = String.valueOf(GDtVacina.getDayOfMonth())+"/"+String.valueOf(GDtVacina.getMonth()+1)+"/"+String.valueOf(GDtVacina.getYear());
			g.DT_PRE_NATAL        = String.valueOf(DtGPreNatal.getDayOfMonth())+"/"+String.valueOf(DtGPreNatal.getMonth()+1)+"/"+String.valueOf(DtGPreNatal.getYear());
			g.DT_CONSULTA_PUERBIO = String.valueOf(GDtPuerbio.getDayOfMonth())+"/"+String.valueOf(GDtPuerbio.getMonth()+1)+"/"+String.valueOf(GDtPuerbio.getYear());
			g.EST_NUTRICIONAL     = EdtGEn.getText().toString();
			g.MES_GESTACAO        = EdtMesGestacao.getText().toString();
			
			
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
				
		}
	}
}
