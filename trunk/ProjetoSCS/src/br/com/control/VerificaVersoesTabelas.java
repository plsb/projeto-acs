package br.com.control;

import android.content.Context;
import br.com.control.Banco;
import br.com.control.Banco.BancoDados;

public class VerificaVersoesTabelas {
	
	private static final String _nomeTabelaUsuario 	  = "usuarios";
	private static final String _nomeTabelaUsuarioAux = "usuariosAux";
	private static final String _nomeTabelaResidencia = "residencia";
	private static final String _nomeTabelaRuas       = "ruas";
	private static final String _nomeTabelaSessao     = "sessao";
	private static final String _nomeTabelaResidente  = "residente";
	private static final String _nomeTabelaBairros    = "bairros";
	private static final String _nomeTabelaSegmentos  = "segmentos";
	private static final String _nomeTabelaAreas      = "area";
	private static final String _nomeTabelaMicroareas = "microarea";
	
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
		BancoDados.getDAO(contexto, Banco._sqlTabelaRuas, VerificaVersoesTabelas._nomeTabelaRuas);
		BancoDados.getDAO(contexto, Banco._sqlTabelaSessao, VerificaVersoesTabelas._nomeTabelaSessao);
		BancoDados.getDAO(contexto, Banco._sqlTabelaResidentes, VerificaVersoesTabelas._nomeTabelaResidente);
		BancoDados.getDAO(contexto, Banco._sqlTabelaBairros, VerificaVersoesTabelas._nomeTabelaBairros);
		//BancoDados.getDAO(contexto, Banco._sqlTabelaSegmentos, VerificaVersoesTabelas._nomeTabelaSegmentos);
		//BancoDados.getDAO(contexto, Banco._sqlTabelaAreas, VerificaVersoesTabelas._nomeTabelaAreas);
		//BancoDados.getDAO(contexto, Banco._sqlTabelaMicroAreas, VerificaVersoesTabelas._nomeTabelaMicroareas);
	}

}