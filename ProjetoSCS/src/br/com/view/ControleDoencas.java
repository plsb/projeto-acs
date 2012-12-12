/************************************************/
/** Projeto SCS - Sistema de Controle de Saúde **/
/************ Desenvolvido por: *****************/
/********** Lucas de Souza Sales ****************/
/******* Karlos Kelvin Alves dos Santos *********/
/************* Pedro Luis Saraiva ***************/
/************************************************/
package br.com.view;

import java.sql.Date;
import java.text.SimpleDateFormat;

import br.com.control.AcompanhamentoAux;
import br.com.control.Banco;
import br.com.control.CriancaAux;
import br.com.control.Diabete;
import br.com.control.Gestante;
import br.com.control.Hanseniase;
import br.com.control.Hipertensao;
import br.com.control.Mensagem;
import br.com.control.Tuberculose;
import br.com.scs.R;
import android.app.ActivityGroup;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;

public class ControleDoencas  extends ActivityGroup implements OnClickListener {
	
	static TabHost th;
	static int tab = -1;
	static String _Hash = "";
	static boolean editando  = false;
	static String  dataAcomp = null;
	static boolean gestante,hipertensao, hanseniase,
				   tuberculose, diabetes, crianca,acomp_padrao = false;
	
	private Button btnVoltar, btnSalvar;
	
	Banco _bd = new Banco(this);
	Cursor _doencas = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.controle_doencas);
		
		btnVoltar = (Button) findViewById(R.controledoencas.btnVoltar);
		btnSalvar = (Button) findViewById(R.controledoencas.btnSalvar);
		btnVoltar.setOnClickListener(this);
		btnSalvar.setOnClickListener(this);
		
		if (!editando){
			try {			
				dataAcomp = null;
				_bd.open();
				_doencas = _bd.consulta("residente", new String[]{"*"}, "hash = '"+_Hash+"'", null, null, null, null, "1");
				_doencas.moveToFirst();
				if (_doencas.getCount() > 0){
					
					if (CalculaIdade(Integer.valueOf(_doencas.getString(_doencas.getColumnIndex("DTNASCIMENTO")).toString().substring(0, _doencas.getString(_doencas.getColumnIndex("DTNASCIMENTO")).toString().indexOf("/"))),
							Integer.valueOf(_doencas.getString(_doencas.getColumnIndex("DTNASCIMENTO")).toString().substring(_doencas.getString(_doencas.getColumnIndex("DTNASCIMENTO")).toString().indexOf("/")+1,_doencas.getString(_doencas.getColumnIndex("DTNASCIMENTO")).toString().lastIndexOf("/")))-1,
							Integer.valueOf(_doencas.getString(_doencas.getColumnIndex("DTNASCIMENTO")).toString().substring(_doencas.getString(_doencas.getColumnIndex("DTNASCIMENTO")).toString().lastIndexOf("/")+1))) < 2) {
						crianca = true;
					} else {							
						gestante    = (_doencas.getString(_doencas.getColumnIndex("FL_GESTANTE")).toString().trim().equals("S"));
						tuberculose = (_doencas.getString(_doencas.getColumnIndex("FL_TUBERCULOSE")).toString().trim().equals("S"));
						hipertensao = (_doencas.getString(_doencas.getColumnIndex("FL_HIPERTENSAO")).toString().trim().equals("S"));
						hanseniase  = (_doencas.getString(_doencas.getColumnIndex("FL_HANSENIASE")).toString().trim().equals("S"));
						diabetes    = (_doencas.getString(_doencas.getColumnIndex("FL_DIABETE")).toString().trim().equals("S"));					
						
					}
				}
			} catch(Exception e) {
				System.out.println("Exceção: "+e.getMessage());
			} finally {
				_doencas.close();
			}
		} 
		
		th = (TabHost) findViewById(R.controledoencas.tabhost);
		th.setup(this.getLocalActivityManager());
		TabHost.TabSpec spec;
		Intent intent;  
			
		if (gestante == true) {
			intent = new Intent().setClass(this, Acomp_Gestante.class);
			Acomp_Gestante.Hash = _Hash;
			Acomp_Gestante.DtAcompanhamento = dataAcomp;
			spec = th.newTabSpec("0").setIndicator("Gestante", getResources().getDrawable(R.drawable.gravida)).setContent(intent);        
	        th.addTab(spec);
		}
		if (hanseniase == true){
			intent = new Intent().setClass(this, Acomp_Hanseniase.class);
			Acomp_Hanseniase.Hash = _Hash;
			Acomp_Hanseniase.DtAcompanhamento = dataAcomp;
			spec = th.newTabSpec("1").setIndicator("Hanseníase", getResources().getDrawable(R.drawable.hanseniase)).setContent(intent);        
	        th.addTab(spec);
		}
		if (hipertensao == true) {
			intent = new Intent().setClass(this, Acomp_Hipertensao.class);
			Acomp_Hipertensao.Hash = _Hash;
			Acomp_Hipertensao.DtAcompanhamento = dataAcomp;
			spec = th.newTabSpec("2").setIndicator("Hipertensão", getResources().getDrawable(R.drawable.hipertensao)).setContent(intent);        
	        th.addTab(spec);
		}
		if (tuberculose == true) {
			intent = new Intent().setClass(this, Acomp_Tuberculose.class);
			Acomp_Tuberculose.Hash = _Hash;
			Acomp_Tuberculose.DtAcompanhamento = dataAcomp;
			spec = th.newTabSpec("3").setIndicator("Tuberculose", getResources().getDrawable(R.drawable.tuberculose)).setContent(intent);        
	        th.addTab(spec);
		}		
		if (diabetes == true) {
			intent = new Intent().setClass(this, Acomp_Diabetes.class);
			Acomp_Diabetes.Hash = _Hash;
			Acomp_Diabetes.DtAcompanhamento = dataAcomp;
			spec = th.newTabSpec("4").setIndicator("Diabetes", getResources().getDrawable(R.drawable.diabetes)).setContent(intent);        
	        th.addTab(spec);
		}
		if (crianca == true) {
			intent = new Intent().setClass(this, Acomp_Crianca.class);
			Acomp_Crianca.Hash = _Hash;
			Acomp_Crianca.DtAcompanhamento = dataAcomp;
			spec = th.newTabSpec("5").setIndicator("Criança", getResources().getDrawable(R.drawable.child)).setContent(intent);        
	        th.addTab(spec);
		}
		if ((gestante == false)&&(hanseniase == false)&&(hipertensao == false)&&(tuberculose == false)&&(diabetes == false)){
			intent = new Intent().setClass(this, Acomp_Padrao.class);
			Acomp_Padrao.Hash = _Hash;
			Acomp_Padrao.DtAcompanhamento = dataAcomp;
			spec = th.newTabSpec("0").setIndicator("Acompanhamento", getResources().getDrawable(R.drawable.acompanhamento)).setContent(intent);
			th.addTab(spec);
			acomp_padrao = true;
		}
	}	

	public void onClick(View v) {	
		if (v == btnVoltar) {
			finish();
		}
		
		if (v == btnSalvar) {
			Inserir();
		}
	}

	@Override
	protected void onDestroy() {
		_Hash        = "";
		dataAcomp    = null;
		editando     = false;
		gestante     = false;
		hipertensao  = false;
		hanseniase   = false;
		tuberculose  = false;
		diabetes     = false;
		crianca      = false;
		acomp_padrao = false;
		super.onDestroy();
	}
	
	public void Inserir() {
		
		String msgInsercao = "";
		
		if (acomp_padrao == true) {
			AcompanhamentoAux c;			
			int _IdTransacao = 0;
			
			try {
				c = new AcompanhamentoAux();
				c.HASH = Acomp_Padrao.Hash;
				c.OBS  = Acomp_Padrao.EdtObs.getText().toString();
				
				if (Acomp_Padrao.rbHospitSIM.isChecked()) {
					c.FL_HOSPITALIZADA = "S";
					c.MOTIVO_HOSPITALIZACAO = Acomp_Padrao.SpMotivoHospit.getItemAtPosition(Acomp_Padrao.SpMotivoHospit.getSelectedItemPosition()).toString();
				} else {
					c.FL_HOSPITALIZADA = "N";
					c.MOTIVO_HOSPITALIZACAO = "";
				}
				
				if (Acomp_Padrao.rbDoenteSIM.isChecked()) {
					c.FL_DOENTE = "S";
					c.DESC_DOENCA = Acomp_Padrao.SpDoenca.getItemAtPosition(Acomp_Padrao.SpDoenca.getSelectedItemPosition()).toString();
				} else {
					c.FL_DOENTE = "N";
					c.DESC_DOENCA = "";
				}
				
				
				_IdTransacao = Acomp_Padrao.getIdTransacao();
				
				if (_IdTransacao == 0){
					if (c.Inserir(this) == true){
						msgInsercao += "Acompanhamento - Gravado\n";
					}else{
						msgInsercao += "Acompanhamento - Erro\n";
					}
				}else{
					if (c.Atualizar(this,_IdTransacao) == true){
						msgInsercao += "Acompanhamento - Atualizado\n";
					}else{
						msgInsercao += "Acompanhamento - Erro\n";
					}
				}
				
			} catch (Exception e) {
				System.out.println("Exceção ao tentar salvar acompanhamento padrão. "+e.getMessage());
			} finally {
				c = null;
			}
		}
		
		if (crianca == true) {
			CriancaAux c;			
			int _IdTransacao = 0;
			
			try {
				c = new CriancaAux();
				c.HASH         = Acomp_Crianca.Hash;
				c.ALTURA       = Acomp_Crianca.EdtAltura.getText().toString();
				c.PESO         = Acomp_Crianca.EdtPeso.getText().toString();
				c.PER_CEFALICO = Acomp_Crianca.EdtPerCefalico.getText().toString();
				c.APGAR5       = Acomp_Crianca.EdtApgar5.getText().toString();
				c.OBS          = Acomp_Crianca.EdtObs.getText().toString();
				
				if (Acomp_Crianca.RbCesario.isChecked()) {
					c.TP_PARTO = "C";
				} else if (Acomp_Crianca.RbNormal.isChecked()) {
					c.TP_PARTO = "N";
				} else {
					c.TP_PARTO = "F";
				}
				
				if (Acomp_Crianca.RbNutrido.isChecked()) {
					c.SITUACAO = "N";
				} else {
					c.SITUACAO = "D";
				}
				
				_IdTransacao = Acomp_Crianca.getIdTransacao();
				
				if (_IdTransacao == 0){
					if (c.Inserir(this) == true){
						msgInsercao += "Criança - Gravado\n";
					}else{
						msgInsercao += "Criança - Erro\n";
					}
				}else{
					if (c.Atualizar(this,_IdTransacao) == true){
						msgInsercao += "Criança - Atualizado\n";
					}else{
						msgInsercao += "Criança - Erro\n";
					}
				}
				
			} catch (Exception e) {
				System.out.println("Exceção ao tentar salvar acompanhar de crinça. "+e.getMessage());
			} finally {
				c = null;
			}
		}
	
		if (gestante == true) {
			
			Gestante g = null;
			int _IdTransacao = 0;
			
			try{
				String fatores_risco = "";
				g = new Gestante();
				g.DT_ULTIMA_REGRA     = Acomp_Gestante.EdtDtUltimaRegra.getText().toString().trim();
				g.DT_PROVAVEL_PARTO   = Acomp_Gestante.EdtDtProvavelParto.getText().toString().trim();			
				g.DT_PRE_NATAL        = Acomp_Gestante.EdtDtPreNatal.getText().toString().trim();
				g.DT_CONSULTA_PUERBIO = "";		
				g.DT_ULTIMA_CONSULTA  = Acomp_Gestante.EdtDtUltimaConsulta.getText().toString().trim();
				g.MES_GESTACAO        = Acomp_Gestante.SpMesGestacao.getItemAtPosition(Acomp_Gestante.SpMesGestacao.getSelectedItemPosition()).toString().trim();
				g.OBSERVACAO          = Acomp_Gestante.EdtGObs.getText().toString();
				g.HASH                = _Hash;
				
				//Estado Nutricional
				if (Acomp_Gestante.RbNutrida.isChecked()){
					g.EST_NUTRICIONAL = "N";     
				}else{
					g.EST_NUTRICIONAL = "D";
				}
				
				//Fatores de Risco
				if (Acomp_Gestante.ChGGestacoes.isChecked())
					fatores_risco += "S";
				else
					fatores_risco += "N";
				if (Acomp_Gestante.ChGIdade36.isChecked())
					fatores_risco += "S";
				else
					fatores_risco += "N";
				if (Acomp_Gestante.ChGSangue.isChecked())
					fatores_risco += "S";
				else
					fatores_risco += "N";
				if (Acomp_Gestante.ChGDiabetes.isChecked())
					fatores_risco += "S";
				else
					fatores_risco += "N";
				if (Acomp_Gestante.ChGAborto.isChecked())
					fatores_risco += "S";
				else
					fatores_risco += "N";
				if (Acomp_Gestante.ChGIdade20.isChecked())
					fatores_risco += "S";
				else
					fatores_risco += "N";
				if (Acomp_Gestante.ChGEdema.isChecked())
					fatores_risco += "S";
				else
					fatores_risco += "N";
				if (Acomp_Gestante.ChGPressao.isChecked())
					fatores_risco += "S";
				else
					fatores_risco += "N";
				
				g.FATORES_RISCO = fatores_risco;
				
				//Resultado Gestação
				if (Acomp_Gestante.ChkGAB.isChecked())
					g.RESULTADO_GESTACAO = "AB";
				else if (Acomp_Gestante.ChkGNV.isChecked())
					g.RESULTADO_GESTACAO = "NV";
				else if (Acomp_Gestante.ChkGNM.isChecked())
					g.RESULTADO_GESTACAO = "NM";
				
				_IdTransacao = Acomp_Gestante.getIdTransacao();
				
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
		}
		
		if (hanseniase == true){
			
			Hanseniase h = null;
			int _IdTransacao = 0;
			
			try{
				h = new Hanseniase();
				h.HASH                      = Acomp_Hanseniase.Hash;
				h.DT_ULTIMA_CONSULTA        = Acomp_Hanseniase.EdtDtUltimaConsulta.getText().toString().trim();
				h.DT_ULTIMA_DOSE            = Acomp_Hanseniase.EdtDtUltimadose.getText().toString().trim();
				h.COMUNICANTES_BCG          = (Acomp_Hanseniase.EdtM5Bcg.getText().toString().trim().length() > 0 ? Integer.valueOf(Acomp_Hanseniase.EdtM5Bcg.getText().toString().trim()) : 0);
				h.OBS                       = Acomp_Hanseniase.EdtObservacao.getText().toString().trim();
				h.COMUNICANTES_EXAMINADOS   = (Acomp_Hanseniase.EdtCe.getText().toString().trim().length() > 0 ? Integer.valueOf(Acomp_Hanseniase.EdtCe.getText().toString().trim()) : 0);
				
				//Toma Medicacao Diaria
				if (Acomp_Hanseniase.RbMdNao.isChecked()){
					h.AUTO_CUIDADOS = "N";
				}else{
					h.AUTO_CUIDADOS = "S";
				}
				
				//Faz Autos Cuidados
				if (Acomp_Hanseniase.RbAcNao.isChecked()){
					h.TOMA_MEDICACAO = "N";
				}else{
					h.TOMA_MEDICACAO = "S";
				}
				
				_IdTransacao = Acomp_Hanseniase.getIdTransacao();
				
				if (_IdTransacao == 0){
					if (h.Inserir(this)== true){
						msgInsercao += "Hanseniase - Gravado\n";
					}else{
						msgInsercao += "Hanseniase - Erro\n";
					}
				}else{
					if (h.Atualizar(this, _IdTransacao) == true){
						msgInsercao += "Hanseniase - Atualizado\n";
					}else{
						msgInsercao += "Hanseniase - Erro\n";
					}
				}
			}catch (Exception e){
				
			}finally{
				h = null;
			}
		}/*FIM HANSENIASE*/
		
		if (hipertensao == true){
			
			Hipertensao hi = null;
			int _IdTransacao = 0;
			
			try{
				hi = new Hipertensao();
				hi.HASH = Acomp_Hipertensao.Hash;				
				hi.DT_ULTIMA_VISITA = Acomp_Hipertensao.EdtDtUltimaVisita.getText().toString().trim();
				hi.PRESSAO_ARTERIAL = Acomp_Hipertensao.EdtHtPe.getText().toString();
				hi.OBSERVACAO = Acomp_Hipertensao.EdtHtObs.getText().toString();
				
				
				//Faz Dieta
				if (Acomp_Hipertensao.RbHiFazDieta_S.isChecked())
					hi.FL_FAZ_DIETA = "S";
				else if (Acomp_Hipertensao.RbHiFazDieta_N.isChecked())	
					hi.FL_FAZ_DIETA = "N";
				
				//Toma Medicação
				if (Acomp_Hipertensao.RbHiTomaMedic_S.isChecked())
					hi.FL_TOMA_MEDICACAO = "S";
				else if (Acomp_Hipertensao.RbHiTomaMedic_N.isChecked())	
					hi.FL_TOMA_MEDICACAO = "N";
				
				//Faz Exercícios Físicos
				if (Acomp_Hipertensao.RbHiExcercFisico_S.isChecked())
					hi.FL_FAZ_EXERCICIOS = "S";
				else if (Acomp_Hipertensao.RbHiExcercFisico_N.isChecked())	
					hi.FL_FAZ_EXERCICIOS = "N";
				
				//Fumante
				if (Acomp_Hipertensao.RbHiFumante_S.isChecked())
					hi.FL_FUMANTE = "S";
				else if (Acomp_Hipertensao.RbHiFumante_N.isChecked())	
					hi.FL_FUMANTE = "N";
				
				_IdTransacao = Acomp_Hipertensao.getIdTransacao();
				
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
		
		if (tuberculose == true) {
			
			Tuberculose t = null;
			int _IdTransacao = 0;
			
			try{
				t = new Tuberculose();
				t.HASH               = Acomp_Tuberculose.Hash; 				
				t.COMUNIC_EXAMINADOS = Acomp_Tuberculose.EdtTCe.getText().toString();
				t.MENOR_BCG 		 = Acomp_Tuberculose.EdtTM5Bcg.getText().toString();
				t.OBSERVACAO 	     = Acomp_Tuberculose.EdtTObs.getText().toString();
				t.DT_ULTIMA_CONSULTA = Acomp_Tuberculose.EdtTUltimaConsulta.getText().toString();
				
				//Toma Medicação Diária
				if (Acomp_Tuberculose.RbTMedicDiaria_S.isChecked())
					t.FL_MEDIC_DIARIA = "S";
				else if (Acomp_Tuberculose.RbTMedicDiaria_N.isChecked())
					t.FL_MEDIC_DIARIA = "N";
				
				//Reações Indesejáveis
				if (Acomp_Tuberculose.RbTReacoesIndesejaveis_S.isChecked())
					t.FL_REACOES_IND = "S";
				else if (Acomp_Tuberculose.RbTReacoesIndesejaveis_N.isChecked())
					t.FL_REACOES_IND = "N";
				
				//Exame de Escarro
				if (Acomp_Tuberculose.RbTExameEscarro_S.isChecked())
					t.FL_EXAME_ESCARRO = "S";
				else if (Acomp_Tuberculose.RbTExameEscarro_N.isChecked())
					t.FL_EXAME_ESCARRO = "N";
				
				_IdTransacao = Acomp_Tuberculose.getIdTransacao();
				
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
		
		if (diabetes == true){
			
			Diabete d = null;
			int _IdTransacao = 0;
			
			try{
				d = new Diabete();
				d.HASH             = Acomp_Diabetes.Hash;
				d.DT_ULTIMA_VISITA = Acomp_Diabetes.EdtDtUltimaConsulta.getText().toString().trim();
				d.OBSERVACAO       = Acomp_Diabetes.EdtObs.getText().toString().trim();
				
				//Faz Dieta
				if (Acomp_Diabetes.RbFazDieta_N.isChecked()){
					d.FL_FAZ_DIETA = "N";
				}else{
					d.FL_FAZ_DIETA = "S";
				}
				
				//Faz Exercicio
				if (Acomp_Diabetes.RbFazExerc_N.isChecked()){
					d.FL_FAZ_EXCERCICIOS = "N";
				}else{
					d.FL_FAZ_EXCERCICIOS = "S";
				}
				
				//Hipoglicemiante
				if (Acomp_Diabetes.RbHipog_N.isChecked()){
					d.FL_USA_HIPOGLICEMIANTE = "N";
				}else{
					d.FL_USA_HIPOGLICEMIANTE = "S";
				}
				
				//Usa Insulina
				if (Acomp_Diabetes.RbUsaInsulina_N.isChecked()){
					d.FL_USA_INSULINA = "N";
				}else{
					d.FL_USA_INSULINA = "S";
				}
				
				_IdTransacao = Acomp_Diabetes.getIdTransacao();
				
				if (_IdTransacao == 0){
					if (d.Inserir(this)== true){
						msgInsercao += "Diabetes - Gravado\n";
					}else{
						msgInsercao += "Diabetes - Erro\n";
					}
				}else{
					if (d.Atualizar(this, _IdTransacao) == true){
						msgInsercao += "Diabetes - Atualizado\n";
					}else{
						msgInsercao += "Diabetes - Erro\n";
					}
				}
				
			}catch (Exception e){
				
			}finally{
				d = null;
			}
		}/*FIM DIABETES*/

		Mensagem.exibeMessagem(this, "SCS - Acompanhamento", msgInsercao,2000);
		new Handler().postDelayed(new Runnable() {		
			public void run() {
				finish();
			}
		}, 2000);
		
	}
	
	public int CalculaIdade(int _dia, int _mes, int _ano){
		SimpleDateFormat f;	
		f = new SimpleDateFormat("dd");
		int dia = Integer.valueOf(f.format(new Date(System.currentTimeMillis())));
		f = new SimpleDateFormat("MM");
		int mes = Integer.valueOf(f.format(new Date(System.currentTimeMillis())));
		f = new SimpleDateFormat("yyyy");
		int ano = Integer.valueOf(f.format(new Date(System.currentTimeMillis())));	
		if ((_dia <= dia)&&(_mes <= mes)){
			return (ano - _ano);
		}else{
			return ((ano - _ano) -1);
		}
	}
	
}
