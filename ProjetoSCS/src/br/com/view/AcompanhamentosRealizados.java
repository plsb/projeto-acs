package br.com.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.control.Banco;
import br.com.control.Mensagem;
import br.com.scs.R;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SimpleExpandableListAdapter;

public class AcompanhamentosRealizados extends ExpandableListActivity implements OnChildClickListener {
    
	/*/ D E C L A R A Ç Õ E S /*/
	
	private static final String TITULO = "NAME";
    private static final String SUBTITULO = "IS_EVEN";

    private ExpandableListAdapter mAdapter;
    
    List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
    List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
    
    String Hash = "";
    
    Banco _bd;	
    
	ArrayList<HashMap<String,String>> list;	
	
	public static int _ID = 0;
	
	/*/ F I M  D E C L A R A Ç Õ E S /*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        _bd = new Banco(this);
             
    }
    
    @Override
    protected void onResume() {
    	groupData.clear();
    	childData.clear();
    	ListarAcompanhamentos();
    	super.onResume();
    }
    
    public void ListarAcompanhamentos(){
    	
        _bd.open();
        
        try{        	
        	Cursor _cursor,_cHan,_cHip,_Ges,_Tb,_Dia = null;
        	
        	
        	_cursor = _bd.consulta("residente", new String[] { "*" },"_ID = "+String.valueOf(_ID).trim(),null,null,null,"_ID",null);          	        	
        	_cursor.moveToFirst();        
        	
        	//System.out.println("ID DO FAMILIAR SELECIONADO: "+String.valueOf(_ID).trim());
        	
        	if ((_cursor.getCount() > 0)){
        		//Pega o Hash da sessoa que está sendo entrevistada pelo agente de saúde 
        		Hash = _cursor.getString(_cursor.getColumnIndex("HASH")).toString().trim();
        		
        		if (_cursor.getString(_cursor.getColumnIndex("FL_HANSENIASE")).toString().trim().equals("S")){
        			
        			Map<String, String> curGroupMap = new HashMap<String, String>();
                    curGroupMap.put(TITULO, "HANSENÍASE");
                    curGroupMap.put(SUBTITULO, "HAN-Acompanhamento" /*(i % 2 == 0) ? "This group is even" : */);
                    groupData.add(curGroupMap);
                    List<Map<String, String>> children = new ArrayList<Map<String, String>>();
                    
                    /*******************************************************************************/
                    _cHan = _bd.consulta("hanseniase", new String[] { "*" },"HASH = '"+Hash+"'",null,null,null,null,null);
                    _cHan.moveToFirst();
                    if (_cHan.getCount()>0){
	                    do{
	                    	Map<String, String> curChildMap = new HashMap<String, String>();
	                        children.add(curChildMap);
	                        curChildMap.put(TITULO,"Data: "+ _cHan.getString(_cHan.getColumnIndex("DT_VISITA")).toString());
	                        curChildMap.put(SUBTITULO, "Obs: "+_cHan.getString(_cHan.getColumnIndex("OBSERVACAO")).toString());
	                    }while(_cHan.moveToNext());
	                    childData.add(children);
                    }else{
                    	Map<String, String> curChildMap = new HashMap<String, String>();
	                    children.add(curChildMap);
	                    curChildMap.put(TITULO,"NENHUM ACOMP. REGISTRADO");
	                    curChildMap.put(SUBTITULO, "One Team Tecnologia");
	                    childData.add(children); 
                    }
                    _cHan.close();                    
                    /*******************************************************************************/
                    
        		}
        		
        		if (_cursor.getString(_cursor.getColumnIndex("FL_HIPERTENSAO")).toString().trim().equals("S")){
        			
        			Map<String, String> curGroupMap = new HashMap<String, String>();
                    curGroupMap.put(TITULO, "HIPERTENSÃO");
                    curGroupMap.put(SUBTITULO, "HA-Acompanhamento" /*(i % 2 == 0) ? "This group is even" : */);
                    groupData.add(curGroupMap);
                    List<Map<String, String>> children = new ArrayList<Map<String, String>>();
                    
                    /*******************************************************************************/
                    _cHip = _bd.consulta("hipertensao", new String[] { "*" },"HASH = '"+Hash+"'",null,null,null,null,null);
                    _cHip.moveToFirst();
                    if (_cHip.getCount() > 0){                    	
	                    do{
	                    	Map<String, String> curChildMap = new HashMap<String, String>();
	                        children.add(curChildMap);
	                        curChildMap.put(TITULO,"Data: "+ _cHip.getString(_cHip.getColumnIndex("DT_VISITA")).toString());
	                        curChildMap.put(SUBTITULO, "Obs: "+_cHip.getString(_cHip.getColumnIndex("OBSERVACAO")).toString());
	                    }while(_cHip.moveToNext());
	                    childData.add(children);
                    }else{
                    	Map<String, String> curChildMap = new HashMap<String, String>();
	                    children.add(curChildMap);
	                    curChildMap.put(TITULO,"NENHUM ACOMP. REGISTRADO");
	                    curChildMap.put(SUBTITULO, "One Team Tecnologia");
	                    childData.add(children); 
                    }
                    _cHip.close();                    
                    /*******************************************************************************/
        		}
        		
        		if (_cursor.getString(_cursor.getColumnIndex("FL_GESTANTE")).toString().trim().equals("S")){
        			
        			Map<String, String> curGroupMap = new HashMap<String, String>();
                    curGroupMap.put(TITULO, "GESTAÇÃO");
                    curGroupMap.put(SUBTITULO, "GES-Acompanhamento");
                    groupData.add(curGroupMap);
                    List<Map<String, String>> children = new ArrayList<Map<String, String>>();
                    
                    /*******************************************************************************/
                    _Ges = _bd.consulta("gestacao", new String[] { "*" },"HASH = '"+Hash+"'",null,null,null,null,null);
                    _Ges.moveToFirst();
                    if (_Ges.getCount() > 0){
	                    do{
	                    	Map<String, String> curChildMap = new HashMap<String, String>();
	                        children.add(curChildMap);
	                        curChildMap.put(TITULO,"Data: "+ _Ges.getString(_Ges.getColumnIndex("DT_VISITA")).toString());
	                        curChildMap.put(SUBTITULO, "Obs: "+_Ges.getString(_Ges.getColumnIndex("OBSERVACAO")).toString());
	                    }while(_Ges.moveToNext());
	                    childData.add(children);
                    }else{
                    	Map<String, String> curChildMap = new HashMap<String, String>();
	                    children.add(curChildMap);
	                    curChildMap.put(TITULO,"NENHUM ACOMP. REGISTRADO");
	                    curChildMap.put(SUBTITULO, "One Team Tecnologia");
	                    childData.add(children); 
                    }
                    _Ges.close();                    
                    /*******************************************************************************/
        		}
        		
        		if (_cursor.getString(_cursor.getColumnIndex("FL_TUBERCULOSE")).toString().trim().equals("S")){
      			    
        			Map<String, String> curGroupMap = new HashMap<String, String>();
                    curGroupMap.put(TITULO, "TUBERCULOSE");
                    curGroupMap.put(SUBTITULO, "TB-Acompanhamento");
                    groupData.add(curGroupMap);
                    List<Map<String, String>> children = new ArrayList<Map<String, String>>();
                    
                    /*******************************************************************************/
                    _Tb = _bd.consulta("tuberculose", new String[] { "*" },"HASH = '"+Hash+"'",null,null,null,null,null);
                    _Tb.moveToFirst();
                    if (_Tb.getCount() > 0){                    
		                do{
		                   	Map<String, String> curChildMap = new HashMap<String, String>();
		                    children.add(curChildMap);
		                    curChildMap.put(TITULO,"Data: "+ _Tb.getString(_Tb.getColumnIndex("DT_VISITA")).toString());
		                    curChildMap.put(SUBTITULO, "Obs: "+_Tb.getString(_Tb.getColumnIndex("OBSERVACAO")).toString());
		                }while(_Tb.moveToNext());
		                childData.add(children);                    	
                    }else{
                    	Map<String, String> curChildMap = new HashMap<String, String>();
	                    children.add(curChildMap);
	                    curChildMap.put(TITULO,"NENHUM ACOMP. REGISTRADO");
	                    curChildMap.put(SUBTITULO, "One Team Tecnologia");
	                    childData.add(children); 
                    }
                    _Tb.close();                    
                    /*******************************************************************************/
        		}
        		
        		if (_cursor.getString(_cursor.getColumnIndex("FL_DIABETE")).toString().trim().equals("S")){
      			    
        			Map<String, String> curGroupMap = new HashMap<String, String>();
                    curGroupMap.put(TITULO, "DIABETE");
                    curGroupMap.put(SUBTITULO, "DIA-Acompanhamento");
                    groupData.add(curGroupMap);
                    List<Map<String, String>> children = new ArrayList<Map<String, String>>();
                    
                    /*******************************************************************************/
                    _Dia = _bd.consulta("diabete", new String[] { "*" },"HASH = '"+Hash+"'",null,null,null,null,null);
                    _Dia.moveToFirst();
                    if (_Dia.getCount() > 0){
	                    do{
	                    	Map<String, String> curChildMap = new HashMap<String, String>();
	                        children.add(curChildMap);
	                        curChildMap.put(TITULO,"Data: "+ _Dia.getString(_Dia.getColumnIndex("DT_VISITA")).toString());
	                        curChildMap.put(SUBTITULO, "Obs: "+_Dia.getString(_Dia.getColumnIndex("OBSERVACAO")).toString());
	                    }while(_Dia.moveToNext());
	                    childData.add(children);
                    }else{
                    	Map<String, String> curChildMap = new HashMap<String, String>();
	                    children.add(curChildMap);
	                    curChildMap.put(TITULO,"NENHUM ACOMP. REGISTRADO");
	                    curChildMap.put(SUBTITULO, "One Team Tecnologia");
	                    childData.add(children); 
                    }
                    _Dia.close();                    
                    /*******************************************************************************/		          
        		}
        		
        		
	        	mAdapter = new SimpleExpandableListAdapter(
	                    this,
	                    
	                    groupData,
	                    R.layout.listapai,
	                    new String[] { TITULO, SUBTITULO },
	                    new int[] {R.id.line_a, R.id.line_b},
	                    
	                    childData,
	                    R.layout.listafilho,
	                    new String[] { TITULO, SUBTITULO },
	                    new int[] {R.id.line_a, R.id.line_b}
	                    );	            
	        
	            setListAdapter(mAdapter);
            
        	}else{
        		Mensagem.exibeMessagem(this, "SCS", "Nenhum Acompanhamento Encotrado!",2000);
				new Handler().postDelayed(new Runnable() {		
					public void run() {
						finish();
					}
				}, 2000);
        	}
        	_cursor.close();
        	_bd.fechaBanco();
        }catch(Exception e){
        	Mensagem.exibeMessagem(this, "SCS", "Nenhum Acompanhamento Encotrado!",2000);
        	new Handler().postDelayed(new Runnable() {		
				public void run() {
					finish();
				}
			}, 2000);
        	System.out.println(e.getMessage());
        }       
    }//Fim do Método ListarDoencas
    
    
    public boolean onChildClick(ExpandableListView parent,View v,int groupPosition,int childPosition,long id){
         
    	 String Data = "";    	 
    	 String TipoDoenca = "";
    	 
    	 TipoDoenca = mAdapter.getGroup(groupPosition).toString();       	 
    	 TipoDoenca = TipoDoenca.substring(TipoDoenca.indexOf("=")+1,TipoDoenca.indexOf("-"));
    	 
    	 Data = mAdapter.getChild(groupPosition, childPosition).toString();    	     	 
    	 Data = Data.substring(Data.lastIndexOf(":")+2);    	 
    	 Data = Data.substring(0, Data.length()-1);    	 
    	 
    	 Intent td = new Intent(this, TelaDoenca.class);
    	 TelaDoenca._editando = true;
    	 TelaDoenca._HASH = Hash;
    	 TelaDoenca._dataVisita = Data;
    	 
    	 if (TipoDoenca.trim().equals("HAN")){
    		TelaDoenca._tabelaDoenca = "hanseniase"; 
    		TelaDoenca._Hanseniase = true;
    	 }else if (TipoDoenca.trim().equals("HA")){
    		TelaDoenca._tabelaDoenca = "hipertensao";
    		TelaDoenca._Hipertensao = true;
    	 }else if (TipoDoenca.trim().equals("GES")){
    		TelaDoenca._tabelaDoenca = "gestacao";
    		TelaDoenca._Gestante = true;
    	 }else if (TipoDoenca.trim().equals("TB")){
    		TelaDoenca._tabelaDoenca = "tuberculose";
    		TelaDoenca._Tuberculose = true;
    	 }else if (TipoDoenca.trim().equals("DIA")){
    		TelaDoenca._tabelaDoenca = "diabete";
    		TelaDoenca._Diabetes = true;
    	 }
    	 
    	 startActivity(td);

         return true;
    }
    

}