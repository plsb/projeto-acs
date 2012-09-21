package br.com.control;

import java.util.Timer;
import java.util.TimerTask;
import br.com.scs.R;
import android.app.Activity;
import android.app.AlertDialog;

public class Mensagem {
	
	private Activity _act;
	
	private static Mensagem msg;
	
	
	private void setAcvitivy(Activity act){
		this._act = act;
	}
		
	public static void exibeMessagem(Activity act, String Titulo,String Texto)
	{		
		verificaInstacia();
		msg.setAcvitivy(act);
		AlertDialog.Builder mensagem = new AlertDialog.Builder(act);
		mensagem.setTitle(Titulo);
		mensagem.setMessage(Texto);
		mensagem.setIcon(R.drawable.scs_icone);
		mensagem.setNeutralButton("OK",null);
		mensagem.show();
	}	
	
	public static void exibeMessagem(Activity act,String Titulo,String Texto,int tempo)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(act);
		builder.setTitle(Titulo);
		builder.setMessage(Texto);
		builder.setIcon(R.drawable.scs_icone);
		builder.setCancelable(true);
		
		final AlertDialog dlg = builder.create();		
		dlg.show();		
		
		final Timer t = new Timer();		
		                
		t.schedule(new TimerTask() {			
			@Override
			public void run() {
				dlg.dismiss();
				t.cancel();				
			}
		}, tempo);
	}	
	
	
	
	private static void verificaInstacia(){
		if(msg==null){
			msg = new Mensagem();
		}
	}

}