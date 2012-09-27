package br.com.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.control.Banco;
import br.com.control.Mensagem;
import br.com.scs.R;

import android.app.ExpandableListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

public class AcompanhamentosRealizados extends ExpandableListActivity {
    private static final String NAME = "NAME";
    private static final String IS_EVEN = "IS_EVEN";

    private ExpandableListAdapter mAdapter;
    
    Banco _bd;	
	ArrayList<HashMap<String,String>> list;	
	
	List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
    List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
	
	public static int _ID = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        _bd = new Banco(this);
        
        ListarResidentes();        
    }
    
    public void ListarResidentes(){
    	
        _bd.open();
        
        try{        	
        	Cursor _cursor,_cHan,_cHip,_Ges,_Tb,_Dia = null;
        	String Hash = "";
        	
        	_cursor = _bd.consulta("residente", new String[] { "*" },"_ID = "+String.valueOf(_ID),null,null,null,"_ID",null);          	        	
        	_cursor.moveToFirst(); 
        	
        	if (_cursor.getCount() > 0){
        		//Pega o Hash da sessoa que está sendo entrevistada pelo agente de saúde 
        		Hash = _cursor.getString(_cursor.getColumnIndex("HASH")).toString().trim();
        		
        		if (_cursor.getString(_cursor.getColumnIndex("FL_HANSENIASE")).toString().trim().equals("S")){
        			
        			Map<String, String> curGroupMap = new HashMap<String, String>();
                    curGroupMap.put(NAME, "HANSENÍASE");
                    curGroupMap.put(IS_EVEN, "HAN-Acompanhamento" /*(i % 2 == 0) ? "This group is even" : */);
                    groupData.add(curGroupMap);
                    List<Map<String, String>> children = new ArrayList<Map<String, String>>();
                    
                    ///////////////////////////////////////////////////////////////////////////////
                    _cHan = _bd.consulta("hanseniase", new String[] { "*" },"HASH = '"+Hash+"'",null,null,null,null,null);
                    _cHan.moveToFirst();
                    do{
                    	Map<String, String> curChildMap = new HashMap<String, String>();
                        children.add(curChildMap);
                        curChildMap.put(NAME,"Data: "+ _cHan.getString(_cHan.getColumnIndex("DT_VISITA")).toString());
                        curChildMap.put(IS_EVEN, "Obs: "+_cHan.getString(_cHan.getColumnIndex("OBSERVACAO")).toString());
                    }while(_cHan.moveToNext());
                    childData.add(children);
                    _cHan.close();
                    
                    //////////////////////////////////////////////////////////////////////////////
                    
        		}
        		
        		if (_cursor.getString(_cursor.getColumnIndex("FL_HIPERTENSAO")).toString().trim().equals("S")){
        			
        			Map<String, String> curGroupMap = new HashMap<String, String>();
                    curGroupMap.put(NAME, "HIPERTENSÃO");
                    curGroupMap.put(IS_EVEN, "HA-Acompanhamento" /*(i % 2 == 0) ? "This group is even" : */);
                    groupData.add(curGroupMap);
                    List<Map<String, String>> children = new ArrayList<Map<String, String>>();
                    
                    ///////////////////////////////////////////////////////////////////////////////
                    _cHip = _bd.consulta("hipertensao", new String[] { "*" },"HASH = '"+Hash+"'",null,null,null,null,null);
                    _cHip.moveToFirst();
                    do{
                    	Map<String, String> curChildMap = new HashMap<String, String>();
                        children.add(curChildMap);
                        curChildMap.put(NAME,"Data: "+ _cHip.getString(_cHip.getColumnIndex("DT_VISITA")).toString());
                        curChildMap.put(IS_EVEN, "Obs: "+_cHip.getString(_cHip.getColumnIndex("OBSERVACAO")).toString());
                    }while(_cHip.moveToNext());
                    childData.add(children);
                    _cHip.close();
                    
                    //////////////////////////////////////////////////////////////////////////////
        		}
        		
        		if (_cursor.getString(_cursor.getColumnIndex("FL_GESTANTE")).toString().trim().equals("S")){
        			
        			Map<String, String> curGroupMap = new HashMap<String, String>();
                    curGroupMap.put(NAME, "GESTAÇÃO");
                    curGroupMap.put(IS_EVEN, "GES-Acompanhamento");
                    groupData.add(curGroupMap);
                    List<Map<String, String>> children = new ArrayList<Map<String, String>>();
                    
                    ///////////////////////////////////////////////////////////////////////////////
                    _Ges = _bd.consulta("gestacao", new String[] { "*" },"HASH = '"+Hash+"'",null,null,null,null,null);
                    _Ges.moveToFirst();
                    do{
                    	Map<String, String> curChildMap = new HashMap<String, String>();
                        children.add(curChildMap);
                        curChildMap.put(NAME,"Data: "+ _Ges.getString(_Ges.getColumnIndex("DT_VISITA")).toString());
                        curChildMap.put(IS_EVEN, "Obs: "+_Ges.getString(_Ges.getColumnIndex("OBSERVACAO")).toString());
                    }while(_Ges.moveToNext());
                    childData.add(children);
                    _Ges.close();
                    
                    //////////////////////////////////////////////////////////////////////////////
        		}
        		
        		if (_cursor.getString(_cursor.getColumnIndex("FL_TUBERCULOSE")).toString().trim().equals("S")){
      			    
        			Map<String, String> curGroupMap = new HashMap<String, String>();
                    curGroupMap.put(NAME, "TUBERCULOSE");
                    curGroupMap.put(IS_EVEN, "TB-Acompanhamento");
                    groupData.add(curGroupMap);
                    List<Map<String, String>> children = new ArrayList<Map<String, String>>();
                    
                    ///////////////////////////////////////////////////////////////////////////////
                    _Tb = _bd.consulta("tuberculose", new String[] { "*" },"HASH = '"+Hash+"'",null,null,null,null,null);
                    _Tb.moveToFirst();
                    do{
                    	Map<String, String> curChildMap = new HashMap<String, String>();
                        children.add(curChildMap);
                        curChildMap.put(NAME,"Data: "+ _Tb.getString(_Tb.getColumnIndex("DT_VISITA")).toString());
                        curChildMap.put(IS_EVEN, "Obs: "+_Tb.getString(_Tb.getColumnIndex("OBSERVACAO")).toString());
                    }while(_Tb.moveToNext());
                    childData.add(children);
                    _Tb.close();
                    
                    //////////////////////////////////////////////////////////////////////////////
        		}
        		
        		if (_cursor.getString(_cursor.getColumnIndex("FL_DIABETE")).toString().trim().equals("S")){
      			    
        			Map<String, String> curGroupMap = new HashMap<String, String>();
                    curGroupMap.put(NAME, "DIABETE");
                    curGroupMap.put(IS_EVEN, "DIA-Acompanhamento");
                    groupData.add(curGroupMap);
                    List<Map<String, String>> children = new ArrayList<Map<String, String>>();
                    
                    ///////////////////////////////////////////////////////////////////////////////
                    _Dia = _bd.consulta("diabete", new String[] { "*" },"HASH = '"+Hash+"'",null,null,null,null,null);
                    _Dia.moveToFirst();
                    do{
                    	Map<String, String> curChildMap = new HashMap<String, String>();
                        children.add(curChildMap);
                        curChildMap.put(NAME,"Data: "+ _Dia.getString(_Dia.getColumnIndex("DT_VISITA")).toString());
                        curChildMap.put(IS_EVEN, "Obs: "+_Dia.getString(_Dia.getColumnIndex("OBSERVACAO")).toString());
                    }while(_Dia.moveToNext());
                    childData.add(children);
                    _Dia.close();
                    
                    ////////////////////////////////////////////////////////////////////////////// 		          
        		}
        		
        		
	        	mAdapter = new SimpleExpandableListAdapter(
	                    this,
	                    
	                    groupData,
	                    R.layout.listapai,
	                    new String[] { NAME, IS_EVEN },
	                    new int[] {R.id.line_a, R.id.line_b},
	                    
	                    childData,
	                    R.layout.listafilho,
	                    new String[] { NAME, IS_EVEN },
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
        	Toast.makeText(this, "Exceção:" +e.getMessage(), Toast.LENGTH_LONG).show();
        	System.out.println(e.getMessage());
        }       
    }//Fim do Método ListarDoencas

}