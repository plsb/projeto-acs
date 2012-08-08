package br.com.control;

/*
 *Classe de acesso ao banco criada por Lucas de Souza Sales. 
 */

import br.com.control.VerificaVersoesTabelas;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.*;
import android.database.Cursor;
import android.database.SQLException;


public class Banco{
	
		private static SQLiteDatabase bd = null;
		
		private static String _nomeBanco = "SCS";
		private static int    _versaoBanco = 1;
		
		private BancoDados bdados;
		
		private final Context ctx;
		
		public static final String[] _sqlTabelaUsuario =  new String[] {"CREATE TABLE if not exists usuarios (_ID integer PRIMARY KEY autoincrement NOT NULL,"+
																		"USU_MATRICULA text NOT NULL,USU_NOME text NOT NULL,USU_SENHA text NOT NULL,USU_FL_ADMIN integer NOT NULL);"};
		
		
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
						// lembrar de mensagem
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
					//cria uma linha na tabela versao caso n�o exista com o nome da tabela
					ContentValues _valores = new ContentValues();
					_valores.put(Banco.NOMETABELA, nomeTabela);
					_valores.put(Banco.VERSAOTABELA, 1);
					_instDAO.inserirRegistro("versao", _valores);
		
					//executa o script 0 da tabela
					String sql = scriptSql[0];
					bd.execSQL(sql);
				}
				return _instDAO;
			}
			
			private Cursor selecionar(String table, String[] columns, String where,
					String[] argumentosWhere, String groupby, String having,
					String orderby, String limit) {
				return bd.query(table, columns, where, argumentosWhere, groupby,
						having, orderby, limit);
			}
			
			//atualiza uma linha d euma tabela
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
		bdados.close();
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
	
}
