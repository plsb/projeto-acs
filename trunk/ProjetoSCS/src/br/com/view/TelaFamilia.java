package br.com.view;

import br.com.scs.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TelaFamilia extends Activity implements OnClickListener{
	
	private Button btnIncluir, btnEditar;
	
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.telafamilia);
	        
	        btnIncluir = (Button) findViewById(R.telaFamilia.btnIncluir);
	        btnIncluir.setOnClickListener(this);
	        
	  }
	  
	  public void onClick(View v) {
		  Intent i = new Intent(this,TelaLogin.class);
		  startActivity(i);
		  //Toast.makeText(this, "Incluir", Toast.LENGTH_SHORT).show();
	  }

}
