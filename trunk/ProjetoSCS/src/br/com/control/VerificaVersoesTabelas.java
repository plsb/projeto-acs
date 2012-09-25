package br.com.control;

import android.content.Context;
import br.com.control.Banco;
import br.com.control.Banco.BancoDados;

public class VerificaVersoesTabelas {
	
	private static final String _nomeTabelaUsuario 	   = "usuarios";
	private static final String _nomeTabelaUsuarioAux  = "usuariosAux";
	private static final String _nomeTabelaResidencia  = "residencia";
	private static final String _nomeTabelaRuas        = "ruas";
	private static final String _nomeTabelaSessao      = "sessao";
	private static final String _nomeTabelaResidente   = "residente";
	private static final String _nomeTabelaBairros     = "bairros";
	private static final String _nomeTabelaGestacao    = "gestacao";
	private static final String _nomeTabelaHanseniase  = "hanseniase";
	private static final String _nomeTabelaDiabete     = "diabete";
	private static final String _nomeTabelaTuberculose = "tuberculose";
	private static final String _nomeTabelaHipertensao = "hipertensao";
	
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
		BancoDados.getDAO(contexto, Banco._sqlTabelaGestacao, VerificaVersoesTabelas._nomeTabelaGestacao);
		BancoDados.getDAO(contexto, Banco._sqlTabelaHanseniase, VerificaVersoesTabelas._nomeTabelaHanseniase);
		BancoDados.getDAO(contexto, Banco._sqlTabelaDiabate, VerificaVersoesTabelas._nomeTabelaDiabete);
		BancoDados.getDAO(contexto, Banco._sqlTabelaTuberlose, VerificaVersoesTabelas._nomeTabelaTuberculose);
		BancoDados.getDAO(contexto, Banco._sqlTabelaHipertensao, VerificaVersoesTabelas._nomeTabelaHipertensao);
	}

}