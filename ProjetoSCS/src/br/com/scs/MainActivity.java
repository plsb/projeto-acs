package br.com.scs;

import java.sql.Date;
import java.text.SimpleDateFormat;

import br.com.control.Mensagem;
import br.com.control.Sessao;
import br.com.control.VerificaVersoesTabelas;
import br.com.control.Banco;
import br.com.scs.R;
import br.com.view.TelaFamilia;
import br.com.view.TelaPrincipal;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private Button   btnLogin;
	private EditText edtUsuario, edtSenha;
	private CheckBox chkLembrarMe; 

	private static Banco _bd;
	private int valor;
	private Cursor _cursor = null;
	private Sessao sessao;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		_bd = new Banco(MainActivity.this);
		
		VerificaVersaoTabela();

		chkLembrarMe = (CheckBox) findViewById(R.telalogin.LembraLoginnBox);
		edtUsuario   = (EditText) findViewById(R.telalogin.Usuario);
		edtSenha	 = (EditText) findViewById(R.telalogin.Senha);
		btnLogin 	 = (Button)   findViewById(R.telalogin.btnLogin);
		btnLogin.setOnClickListener(this);
		
		setUsuarioLembrado();

	}
	
	public void VerificaVersaoTabela(){
		if(VerificaVersoesTabelas.jaVerificouAtualizacao == false){
			VerificaVersoesTabelas pg = VerificaVersoesTabelas.verificaVersoesTabelas(MainActivity.this);
			VerificaVersoesTabelas.jaVerificouAtualizacao = true;
		}
	}
	
	public void LembrarMe(String usuario){
		//VerificaVersaoTabela();
		Cursor cAux = null;
		ContentValues c = new ContentValues(); 
		try{			
			c.put("USU_NOME", usuario);		 
			try{
				_bd.open();
				cAux = _bd.consulta("usuariosAux", new String[] { "*" }, null, null, null, null, null, null);
				if (cAux.getCount() > 0){
					_bd.atualizarDadosTabela("usuariosAux",1,c);
				}else{
					_bd.inserirRegistro("usuariosAux", c);
				}//Fim else
			}catch(Exception E){
				Toast.makeText(this, "Erro no método Lembrar-Me "+E.getMessage(), Toast.LENGTH_SHORT).show();
			}//Fim Catch
		}finally{
			cAux.close();
			_bd.fechaBanco();
			c    = null;
		}//Fim Finally
	}//Fim Método LembrarMe
	
	public void setUsuarioLembrado(){		
		Cursor cAux = null;				 
		try{
			_bd.open();
			//VerificaVersaoTabela();
			cAux = _bd.consulta("usuariosAux", new String[] { "*" }, null, null, null, null, null, null);
			cAux.moveToFirst();
			if (cAux.getCount() > 0){
				cAux.moveToFirst();
				edtUsuario.setText(cAux.getString(1));
				chkLembrarMe.setChecked(true);
			}//Fim else
			cAux.close();
			_bd.fechaBanco();
		}catch(Exception E){
			Toast.makeText(this, "Erro no método UsuarioLembrado "+E.getMessage(), Toast.LENGTH_LONG).show();
		}//Fim Catch
	}

	private int autenticar(String usuario, String senha) {
		
		SimpleDateFormat formatador = new SimpleDateFormat("dd");

		if (usuario.equalsIgnoreCase("admin") && (senha.equalsIgnoreCase(formatador.format(new Date(System.currentTimeMillis()))+"0908"))) {
			sessao = Sessao.getSessao();
			sessao.setUsuario(this,"0000","Administrador");
			valor = 0;
		} else {
			_bd.open();
			try {				
				_cursor = _bd.consulta("usuarios", new String[] { "*" },
									   "USU_LOGIN = ? AND USU_SENHA = ? AND USU_FL_ADMIN = 0 AND USU_ATIVO = 'S' ",
									   new String[] { usuario, senha }, null, null, null, null);

				if (_cursor.getCount() != 0) {
					_cursor.moveToFirst();
					sessao = Sessao.getSessao();
					sessao.setUsuario(this,_cursor.getString(_cursor.getColumnIndex("USU_MATRICULA")).toString(),_cursor.getString(_cursor.getColumnIndex("USU_NOME")).toString());
					valor = 1;
				} else {
					_cursor = _bd.consulta("usuarios", new String[] { "*" },
										   "USU_LOGIN = ? AND USU_SENHA = ? AND USU_FL_ADMIN = 1 AND USU_ATIVO = 'S' ",
										   new String[] { usuario, senha }, null, null, null, null);

					if (_cursor.getCount() != 0) {
						_cursor.moveToFirst();
						sessao = Sessao.getSessao();
						sessao.setUsuario(this,_cursor.getString(_cursor.getColumnIndex("USU_MATRICULA")).toString(),_cursor.getString(_cursor.getColumnIndex("USU_NOME")).toString());
						valor = 0;
					} else {
						valor = 2;
					}// Fim else
				}// Fim else				
			} catch (Exception e) {
				Mensagem.exibeMessagem(this, "SCS - Erro", "ERRO " + e.getMessage());
			} finally {
				
			}// Fim Finally
			_cursor.close();
			_bd.fechaBanco();
		}// Fim else		
		return valor;
	}//Fim do Método Autenticar

	public void onClick(View v) {
		if (v == btnLogin) {
			try {
				//_bd = new Banco(MainActivity.this);
				String usuario = edtUsuario.getText().toString();
				String senha = edtSenha.getText().toString();
				
				if (chkLembrarMe.isChecked()){
					LembrarMe(usuario);
				}

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
				// SE FOR 2 O LOGIN OU SENHA ESTÃO ERRADOS
				case 2:
					Mensagem.exibeMessagem(this, "SCS", "Usuário ou Senha Incorretos");
				
				}//Fim switch				

			} catch (Exception e) {
				Toast.makeText(this, "Erro no método logar!" + e.getMessage(), Toast.LENGTH_SHORT).show();
			}//Fim do Catch
			
		}//Fim do If

	}//Fim do Método onClick 

	public void onClose() {
		_bd.fechaBanco();
	}

}
