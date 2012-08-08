package br.com.view;

import br.com.scs.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TelaLogin extends Activity implements OnClickListener {
	
	private Button btnLogin;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.telalogin);
	        
	        btnLogin = (Button) findViewById(R.telalogin.btnLogin);
	        btnLogin.setOnClickListener(this);       
	        
	  }

	public void onClick(View v) {
		if (v == btnLogin){
			Toast.makeText(this, "EFETUAR LOGIN", Toast.LENGTH_SHORT).show();
		}
		
	}

	

}
