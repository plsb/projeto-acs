package br.com.control;

/*
 *Classe de acesso ao banco de dados criada por Lucas de Souza Sales. 
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.*;
import android.database.Cursor;
import android.database.SQLException;

public class Banco{
	
		private static SQLiteDatabase bd = null;
		
		private static String _nomeBanco = "SCS";
		
		private static int _versaoBanco = 1; 
		
		private BancoDados bdados;
		
		private final Context ctx;
		
		
		public static final String[] _sqlTabelaUsuario =  new String[] {"CREATE TABLE if not exists usuarios (_ID integer PRIMARY KEY autoincrement NOT NULL,"+
																		"USU_MATRICULA text NOT NULL,      "+
																		"USU_NOME text NOT NULL,           "+
																		"USU_LOGIN text NOT NULL,          "+
																		"USU_SENHA text NOT NULL,      	   "+
																		"USU_COORDENADOR text,			   "+
																		"USU_ATIVO text NOT NULL,          "+
																		"USU_FL_ADMIN integer NOT NULL);   "};
		
		public static final String[] _sqlTabelaAgendamento=  new String[] {"CREATE TABLE if not exists agendamento (_ID integer PRIMARY KEY autoincrement NOT NULL,"+
															  		       "HASH text not null, 	   "+
																	       "DT_AGENDAMENTO text,	   "+
																	       "FL_URGENTE text,		   "+
																		   "TIPO_AGENDAMENTO text,	   "+
																		   "DESCRICAO text);			   "};
		
		public static final String[] _sqlTabelaVacina =  new String[] {"CREATE TABLE if not exists vacinas (_ID integer PRIMARY KEY autoincrement NOT NULL,"+
																	   "HASH text not null,          "+
																	   "TIPO_VACINA text not null,   "+
																	   "DOSE_APLICADA text not null, "+
																	   "DT_APLICACAO text not null,  "+
																	   "DT_CADASTRO text not null,   "+
																	   "LOTE text,		 			 "+
																	   "FL_APLICADA text,		 	 "+
																	   "TIPO text not null);	  	 "}; 
		
		public static final String[] _sqlTabelaBairros =  new String[] {"CREATE TABLE if not exists bairros (_ID integer PRIMARY KEY autoincrement NOT NULL,"+
																	    "COD_RET integer NOT NULL, 		"+
																	    "CEP text, 						"+
																	    "DESCRICAO text NOT NULL    );	"};
		
		public static final String[] _sqlTabelaGestacao =  new String[] {"CREATE TABLE if not exists gestacao (_ID integer PRIMARY KEY autoincrement NOT NULL,"+
																	     "HASH text not null, 		"+
																	     "DT_VISITA text,			"+
																	     "DT_ATUALIZACAO text,		"+
																	     "DT_ULTIMA_REGRA text,		"+
																	     "DT_PROVAVEL_PARTO text,	"+
																	     "DT_CONSULTA_PUERBIO text,	"+
																	     "EST_NUTRICIONAL text,		"+
																	     "MES_GESTACAO integer,		"+
																	     "DT_PRE_NATAL text,		"+
																	     "FATORES_RISCO text,		"+
																	     "RESULTADO_GESTACAO text,	"+
																	     "OBSERVACAO text);			"};
		
		 public static final String[] _sqlTabelaHanseniase =  new String[] {"CREATE TABLE if not exists hanseniase (_ID integer PRIMARY KEY autoincrement NOT NULL,"+
			     															"HASH text not null, 		       "+
			     															"DT_VISITA text,		           "+
			     															"DT_ATUALIZACAO text,			   "+
			     															"DT_ULTIMA_CONSULTA text,	       "+
			     															"DT_ULTIMA_DOSE text,	           "+
			     															"TOMA_MEDICACAO text,		       "+
			     															"AUTO_CUIDADOS text,			   "+
			     															"COMUNICANTES_EXAMINADOS integer,  "+
			     															"COMUNICANTES_BCG integer,		   "+
			     															"OBSERVACAO text,				   "+
			     															"NUMERO_COMUNICANTES integer);	   "};
		 
		public static final String[] _sqlTabelaDiabate =  new String[] {"CREATE TABLE if not exists diabete (_ID integer PRIMARY KEY autoincrement NOT NULL,"+
																		"HASH text not null, 		       "+
																		"DT_VISITA text,		           "+
																		"DT_ATUALIZACAO text,			   "+
																		"FL_FAZ_DIETA text,			   	   "+
																		"FL_FAZ_EXCERCICIOS text,	       "+
																		"FL_USA_INSULINA text,		       "+
																	 	"FL_USA_HIPOGLICEMIANTE text,	   "+
																		"DT_ULTIMA_VISITA text,		   "+																		 
																		"OBSERVACAO text);				   "};
		
		public static final String[] _sqlTabelaHipertensao =  new String[] {"CREATE TABLE if not exists hipertensao (_ID integer PRIMARY KEY autoincrement NOT NULL,"+
																			"HASH text not null, 		       "+
																			"DT_VISITA text,		           "+
																			"DT_ATUALIZACAO text,			   "+
																			"FL_FAZ_DIETA text,			  	   "+
																			"FL_TOMA_MEDICACAO text,	       "+
																			"FL_FAZ_EXERCICIOS text,		   "+
																		 	"PRESSAO_ARTERIAL text,	           "+
																			"DT_ULTIMA_VISITA text,		  	   "+																		 
																			"OBSERVACAO text);				   "};
		 
		public static final String[] _sqlTabelaTuberlose =  new String[] {"CREATE TABLE if not exists tuberculose (_ID integer PRIMARY KEY autoincrement NOT NULL,"+
		  															      "HASH text not null, 		       "+
		  															      "DT_VISITA text,		           "+
		  															      "DT_ATUALIZACAO text,		       "+
																		  "FL_MEDIC_DIARIA text,		   "+
																		  "FL_REACOES_IND text,			   "+
																		  "FL_EXAME_ESCARRO text,	       "+
																		  "COMUNIC_EXAMINADOS text,		   "+
																	 	  "MENOR_BCG text,	   			   "+																	 
																		  "OBSERVACAO text);			   "};
			     															
		
		public static final String[] _sqlTabelaRuas =  new String[] {"CREATE TABLE if not exists ruas (_ID integer PRIMARY KEY autoincrement NOT NULL,"+
																	 "COD_RET integer NOT NULL, 		"+
																	 "DESCRICAO text NOT NULL,      	"+
																	 "COD_MICROAREA INTEGER NOT NULL,   "+
																	 "COD_AREA INTEGER NOT NULL,	    "+
																	 "COD_SEGMENTO INTEGER NOT NULL,    "+
																	 "COD_BAIRRO INTEGER NOT NULL,      "+
																	 "USU_VINCULADO text NOT NULL );	"};
		
		public static final String[] _sqlTabelaSessao =  new String[] {"CREATE TABLE if not exists sessao (_ID integer PRIMARY KEY autoincrement NOT NULL,"+
																	   "USU_MATRICULA text,  	   "+
																	   "USU_NOME text,             "+
																	   "ULTIMO_USU_LOGADO text,    "+
																	   "DATA text );			   "};
		
		public static final String[] _sqlTabelaUsuarioAux =  new String[] {"CREATE TABLE if not exists usuariosAux (_ID integer PRIMARY KEY autoincrement NOT NULL,"+
																		   "USU_NOME text NOT NULL);"};
		
		public static final String[] _sqlTabelaResidentes =  new String[] {"CREATE TABLE if not exists residente (_ID integer PRIMARY KEY autoincrement NOT NULL,"+
																		   "NOME TEXT NOT NULL,      	  "+
																		   "ENDERECO TEXT NOT NULL,       "+
																		   "COD_ENDERECO INTEGER,   	  "+
																		   "NUMERO TEXT NOT NULL,	      "+
																		   "DTNASCIMENTO TEXT,  		  "+
																		   "SEXO TEXT,			  		  "+
																		   "FREQ_ESCOLA TEXT,	  		  "+
																		   "ALFABETIZADO TEXT,	  		  "+
																		   "OCUPACAO TEXT,		  		  "+
																		   "FL_HANSENIASE TEXT,  		  "+
																		   "FL_HIPERTENSAO TEXT, 		  "+
																		   "FL_GESTANTE TEXT,	  		  "+
																		   "FL_TUBERCULOSE TEXT, 		  "+
																		   "FL_ALCOLISMO TEXT,   		  "+
																		   "FL_CHAGAS TEXT,      		  "+
																		   "FL_DEFICIENTE TEXT,  		  "+
																		   "FL_MALARIA TEXT,     		  "+
																		   "FL_DIABETE TEXT,     		  "+
																		   "FL_EPILETICO TEXT,   		  "+
																		   "FL_ATIVO TEXT,		   		  "+
																		   "FL_VIVO TEXT,		   		  "+
																		   "HASH TEXT,			   		  "+
																		   "NOME_PAI TEXT,		   		  "+
																		   "NOME_MAE TEXT,		   		  "+
																		   "DATA_ATUALIZACAO TEXT);		  "};
		
		public static final String[] _sqlTabelaResidencia =  new String[] {"CREATE TABLE if not exists residencia (_ID integer PRIMARY KEY autoincrement NOT NULL, "+																		   
																		   "ENDERECO TEXT NOT NULL, 	 "+
																		   "NUMERO TEXT NOT NULL, 		 "+
																		   "BAIRRO TEXT NOT NULL, 		 "+
																		   "COD_BAIRRO TEXT,    		 "+
																		   "COD_ENDERECO TEXT,   		 "+
																		   "CEP TEXT, 					 "+
																		   "MUNICIPIO TEXT NOT NULL, 	 "+
																		   "SEG_TERRIT TEXT, 			 "+
																		   "AREA TEXT, 					 "+
																		   "MICROAREA TEXT, 			 "+																		   
																		   "COD_FAMILIA TEXT,   		 "+
																		   "DATA_CADASTRO TEXT NOT NULL, "+
																		   "TIPO_CASA TEXT, 			 "+
																		   "TIPO_CASA_OUTROS TEXT, 		 "+
																		   "DEST_LIXO TEXT, 			 "+
																		   "TRAT_AGUA TEXT,				 "+
																		   "ABAST_AGUA TEXT,			 "+
																		   "DEST_FEZES TEXT,			 "+
																		   "CASO_DOENCA TEXT,			 "+
																		   "CASO_DOENCA_OUTROS TEXT,	 "+
																		   "MEIO_COMUNICACAO TEXT,		 "+
																		   "MEIO_COMUNICACAO_OUTRO TEXT, "+
																		   "PART_GRUPOS TEXT,		   	 "+
																		   "PART_GRUPOS_OUTRO TEXT,      "+
																		   "POSSUI_PLANO TEXT,		   	 "+
																		   "NUM_PESSOAS_COM_PLANO TEXT,  "+
																		   "NOME_PLANO_SAUDE TEXT,       "+
																		   "MEIO_TRANSPORTE TEXT,        "+																		 
																		   "MEIO_TRANSPORTE_OUTRO TEXT,	 "+
																		   "NUM_COMODOS TEXT, 			 "+ 
																		   "FL_ENERGIA TEXT);            "};

		
		
		private static final String _sqlTabelaVersao = "create table versao (_id integer primary key autoincrement, "+
				 									   "nometabela text not null, versao integer not null);";
		
		private static final String _nomeTabelaVersao= "versao";
		
		/**campos para a tabela versao**/
		public static final String CHAVE = "_id";
		private static final String NOMETABELA = "nometabela";
		private static final String VERSAOTABELA = "versao";
		private static final String[] _colunas = { Banco.CHAVE, Banco.NOMETABELA, Banco.VERSAOTABELA };
	
		
	public static class BancoDados extends SQLiteOpenHelper{	
		
			//instancia unica do dao
			private static BancoDados _instDAO;

			private BancoDados(Context context) {
				super(context, _nomeBanco, null, _versaoBanco);
			}	
			
			public static BancoDados getDAO(Context ctx, String[] scriptSql,
					String nomeTabela) {
				if  (_instDAO == null) {
					_instDAO = new BancoDados(ctx);
					try {
						bd = _instDAO.getWritableDatabase();
					} catch (SQLiteException e) {
						//lembrar de mensagem
					}
		
				}
				// verifica a quantidade de script para executar no banco de dados da tabela
				Cursor _cursor = null;
				_cursor = _instDAO.selecionar(_nomeTabelaVersao, _colunas, Banco.NOMETABELA
						+ "= ?", new String[] { nomeTabela }, null, null, null, null);
		
				if (_cursor.getCount() > 0) {
					_cursor.moveToFirst();
					int _versaoTabela = _cursor.getInt(2);
					int qtdeScript = scriptSql.length;
					for (int i = _versaoTabela; i < qtdeScript; i++) {
						String sql = scriptSql[i];
						bd.execSQL(sql);
					}
					//atualiza a versao da tabela
					ContentValues _valores = new ContentValues();
					_valores.put(Banco.VERSAOTABELA, qtdeScript);
					_instDAO.atualizarDadosTabela(_nomeTabelaVersao, _cursor.getInt(0), _valores);
		
				} else {
					//cria uma linha na tabela versao caso nao exista com o nome da tabela
					ContentValues _valores = new ContentValues();
					_valores.put(Banco.NOMETABELA, nomeTabela);
					_valores.put(Banco.VERSAOTABELA, 1);
					_instDAO.inserirRegistro("versao", _valores);
		
					//executa o script 0 da tabela
					String sql = scriptSql[0];
					bd.execSQL(sql);
				}
				_cursor.close();
				return _instDAO;
			}
			
			private Cursor selecionar(String table, String[] columns, String where,
					String[] argumentosWhere, String groupby, String having,
					String orderby, String limit) {
				return bd.query(table, columns, where, argumentosWhere, groupby,
						having, orderby, limit);
			}
			
			//atualiza uma linha de uma tabela
			public int atualizarDadosTabela(String tabela, long id,
					ContentValues valores) {
				return bd.update(tabela, valores, CHAVE + "=" + id, null);
			}	
			
			public long inserirRegistro(String tabela, ContentValues valores) {
				return bd.insert(tabela, null, valores);
				
			}
		
			@Override
			public void onCreate(SQLiteDatabase bd) {
				
				try{
					bd.execSQL(_sqlTabelaVersao);
				}catch(SQLException e){
					//
				}
			}
		
			@Override
			public void onUpgrade(SQLiteDatabase bd, int versaoAntiga, int versaoNova) {
				bd.execSQL("DROP TABLE IF EXISTS usuarios");
				bd.execSQL("DROP TABLE IF EXISTS usuariosAux");	
				bd.execSQL("DROP TABLE IF EXISTS versao");
				onCreate(bd);				
			}
			
	}
	
	public Banco(Context c){
		this.ctx = c;
	}
	
	public Banco open(){
		bdados = new BancoDados(ctx);		
		bd = bdados.getWritableDatabase();
		return this;
	}
	
	
	public void fechaBanco(){
		if (bd.isOpen()){
			bdados.close();
		}
	}
	
	public long inserirRegistro(String tabela, ContentValues valores) {
		return bd.insert(tabela, null, valores);
		
	}
	
	public boolean ExecutaSql(String Sql){
		try{
			open();
			bd.execSQL(Sql);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public Cursor consulta(String table, String[] columns, String where, String[] argumentosWhere, String groupby, String having, String orderby, String limit) {
		return bd.query(table, columns, where, argumentosWhere, groupby, having, orderby, limit);
	}

	public int atualizarDadosTabela(String tabela, long id,	ContentValues valores) {
		return bd.update(tabela, valores, CHAVE + "=" + id, null);
	}
	
	
}
