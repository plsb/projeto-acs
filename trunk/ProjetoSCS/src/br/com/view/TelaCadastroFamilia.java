package br.com.view;


import br.com.scs.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TelaCadastroFamilia extends Activity{
	
	EditText dataNascimento;
	private boolean isUpdating = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.telacadastrofamilia);
		
		dataNascimento = (EditText) findViewById(R.cadastrofamilia.EdtDataNascimento);
	}

/*
	
	public class DateMask implements OnKeyListener{
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			Log.i("TIME", "keyCode:" + keyCode);

			EditText ed = (EditText) v;
			if (event.getAction() == KeyEvent.ACTION_UP && keyCode != KeyEvent.KEYCODE_DEL) {
				int length = ed.getText().toString().length();
				switch (length) {
				case 2: {
					ed.setTextKeepState(ed.getText() + "/");
					break;
				}
				case 5: {
					ed.setTextKeepState(ed.getText() + "/");
					break;
				}
				default:
				break;
			}

		}
			//Posiciona o cursor no fim
			Selection.setSelection(ed.getText(), ed.getText().toString().length());
			return false;
		}
		
	}
	
	*/


}

