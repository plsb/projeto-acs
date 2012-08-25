package br.com.control;

import android.content.Context;
import br.com.control.Banco;
import br.com.control.Banco.BancoDados;

public class VerificaVersoesTabelas {
	
	private static final String _nomeTabelaUsuario = "usuarios";
	private static final String _nomeTabelaUsuarioAux = "usuariosAux";
	private static final String _nomeTabelaResidencia = "residencia";

	public static Boolean jaVerificouAtualizacao = false;
	
	private BancoDados _dao;
	private static VerificaVersoesTabelas _veri;
	
	public static VerificaVersoesTabelas verificaVersoesTabelas(Context ctx) {
		if (_veri==null){
			_veri = new VerificaVersoesTabelas();
		}
		verificaDAO(ctx);
		return _veri;		
	}

	private static void verificaDAO(Context contexto) {
		BancoDados.getDAO(contexto, Banco._sqlTabelaUsuario, VerificaVersoesTabelas._nomeTabelaUsuario);
		BancoDados.getDAO(contexto, Banco._sqlTabelaUsuarioAux, VerificaVersoesTabelas._nomeTabelaUsuarioAux);
		BancoDados.getDAO(contexto, Banco._sqlTabelaResidencia, VerificaVersoesTabelas._nomeTabelaResidencia);
	}

}