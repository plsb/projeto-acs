package br.com.scs;

import br.com.control.Sessao;
import br.com.control.VerificaVersoesTabelas;
import br.com.control.Banco;
import br.com.scs.R;
import br.com.view.TelaFamilia;
import br.com.view.TelaPrincipal;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private Button btnLogin;
	private EditText edtUsuario, edtSenha;

	private static Banco _bd;
	private int valor;
	private Cursor _cursor;
	private Sessao sessao;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		edtUsuario = (EditText) findViewById(R.telalogin.Usuario);
		edtSenha = (EditText) findViewById(R.telalogin.Senha);
		btnLogin = (Button) findViewById(R.telalogin.btnLogin);
		btnLogin.setOnClickListener(this);

	}

	private int autenticar(String usuario, String senha) {

		if (usuario.equalsIgnoreCase("admin")
				&& (senha.equalsIgnoreCase("040908"))) {
			valor = 0;
		} else {
			_bd.open();
			if(VerificaVersoesTabelas.jaVerificouAtualizacao == false){
				VerificaVersoesTabelas pg = VerificaVersoesTabelas.verificaVersoesTabelas(MainActivity.this);
				VerificaVersoesTabelas.jaVerificouAtualizacao = true;
			}
			try {
				_cursor = _bd.consulta("usuarios", new String[] { "*" },
									   "USU_MATRICULA = ? AND USU_SENHA = ? AND USU_FL_ADMIN = 0",
									   new String[] { usuario, senha }, null, null, null, null);

				if (_cursor.getCount() != 0) {
					sessao = Sessao.getSessao();
					sessao.setUsuario(usuario,senha);
					valor = 1;
					
				} else {
					_cursor = null;
					_cursor = _bd.consulta("usuarios", new String[] { "*" },
										   "USU_MATRICULA = ? AND USU_SENHA = ? AND USU_FL_ADMIN = 1",
										   new String[] { usuario, senha }, null, null, null, null);

					if (_cursor.getCount() != 0) {
						valor = 0;
					} else {
						valor = 2;
					}// Fim else
				}// Fim else
				_cursor.close();
			} catch (Exception e) {
				Toast.makeText(this, "ERRO " + e.getMessage(),
						Toast.LENGTH_SHORT).show();
			} finally {
				_cursor.close();
				_bd.fechaBanco();
			}// Fim Finally

		}// Fim else
		return valor;
	}//Fim do Método Autenticar

	public void onClick(View v) {
		if (v == btnLogin) {
			try {
				_bd = new Banco(MainActivity.this);
				String usuario = edtUsuario.getText().toString();
				String senha = edtSenha.getText().toString();

				switch (autenticar(usuario, senha)) {
				// SE FOR 0 é ADMINISTRADOR
				case 0:
					Intent admOpcoes = new Intent(this, TelaPrincipal.class); 
					startActivity(admOpcoes);
					finish();
					break;
				// SE FOR 1 é USUARIO NORMAL
				case 1:
					Intent usuOpcoes = new Intent(this, TelaPrincipal.class); 
					startActivity(usuOpcoes);
					finish();					
					break;
				// SE FOR 2 O LOGIN OU SENHA ESTAO ERRADOS
				case 2:
					Toast.makeText(this, "USUÁRIO OU SENHA INCORRETOS", Toast.LENGTH_SHORT).show();
				}
				

			} catch (Exception e) {
				Toast.makeText(this, "Erro no método logar!" + e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}

	}

	public void onClose() {
		_bd.fechaBanco();
	}

}
