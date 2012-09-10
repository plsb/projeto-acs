package br.com.control;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.*;
import org.ksoap2.transport.*;
import org.xmlpull.v1.XmlPullParserException;

public class WSCliente {
	
	private SoapPrimitive retorno;

	private static String SOAP_ACTION = "";
	private static String METHOD_NAME = "";
	private static String NAMESPACE   = "";
	private static final String URL   = "http://192.168.0.101:8080/SCS/webservice/scsWS?wsdl";
	
	public WSCliente(String _NOME_METODO,String _NAMESPACE){
		SOAP_ACTION = _NAMESPACE + _NOME_METODO;
		METHOD_NAME = _NOME_METODO;
		NAMESPACE   = _NAMESPACE;
	}
	
	public boolean Execute() throws IOException, XmlPullParserException{	
		
		try{		
			SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
			
			SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			
			soapEnvelope.setOutputSoapObject(Request);
			
			HttpTransportSE aht  = new HttpTransportSE(URL);
			
			aht.call(SOAP_ACTION, soapEnvelope);
			
			retorno = (SoapPrimitive)soapEnvelope.getResponse();
			
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public SoapPrimitive getRetorno(){
		return retorno;
	}
}
