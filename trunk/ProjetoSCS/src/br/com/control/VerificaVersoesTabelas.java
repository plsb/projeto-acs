package br.com.control;

import android.content.Context;
import br.com.control.Banco;
import br.com.control.Banco.BancoDados;

public class VerificaVersoesTabelas {
	
	public static Boolean jaVerificouAtualizacao = false;
	
	private BancoDados _dao;
	private static VerificaVersoesTabelas _veri;
	
	public static VerificaVersoesTabelas verificaVersoesTabelas(Context ctx) {
		if (_veri == null) {
			_veri = new VerificaVersoesTabelas();
		}
		verificaDAO(ctx);
		return _veri;		
	}

	private static void verificaDAO(Context contexto) { 
		BancoDados.getDAO(contexto, Banco._sqlTabelaUsuario, "usuarios");
		BancoDados.getDAO(contexto, Banco._sqlTabelaUsuarioAux, "usuariosAux");
		BancoDados.getDAO(contexto, Banco._sqlTabelaResidencia, "residencia");
		BancoDados.getDAO(contexto, Banco._sqlTabelaRuas, "ruas");
		BancoDados.getDAO(contexto, Banco._sqlTabelaSessao, "sessao");
		BancoDados.getDAO(contexto, Banco._sqlTabelaResidentes, "residente");
		BancoDados.getDAO(contexto, Banco._sqlTabelaBairros, "bairros");
		BancoDados.getDAO(contexto, Banco._sqlTabelaGestacao, "gestacao");
		BancoDados.getDAO(contexto, Banco._sqlTabelaHanseniase, "hanseniase");
		BancoDados.getDAO(contexto, Banco._sqlTabelaDiabate, "diabete");
		BancoDados.getDAO(contexto, Banco._sqlTabelaTuberlose, "tuberculose");
		BancoDados.getDAO(contexto, Banco._sqlTabelaHipertensao, "hipertensao");
		BancoDados.getDAO(contexto, Banco._sqlTabelaVacina, "vacinas");
		BancoDados.getDAO(contexto, Banco._sqlTabelaAgendamento, "agendamento");
		BancoDados.getDAO(contexto, Banco._sqlTabelaCrianca, "crianca");
		BancoDados.getDAO(contexto, Banco._sqlTabelaVisita, "visita");
		BancoDados.getDAO(contexto, Banco._sqlTabelaAcompanhamento, "acompanhamento");
		BancoDados.getDAO(contexto, Banco._sqlTabelaProfissional, "profissional");
	}

}