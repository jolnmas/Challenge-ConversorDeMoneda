package modelo;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;

import javax.swing.JOptionPane;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import org.json.*;
public class Conversor {
	
	public String[] arrayFiat= {"USD","GBP","JPY","EUR","KRW","ARS"};
	private HttpClient cliente= HttpClient.newBuilder().version(Version.HTTP_2).build();
    private String secret_api_key="3SzTW_hLfXGTHgkdR3xh";
	public String getConvertForex(String coin1, String coin2) {
		String totalValue="";
		final HttpRequest req = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create("https://marketdata.tradermade.com/api/v1/convert?api_key="+secret_api_key+"&from="+coin1+"&to="+coin2+"&amount=1"))
				.build();
		try {
			final HttpResponse<String> response= cliente.send(req, HttpResponse.BodyHandlers.ofString());
		    JSONObject myJson = new JSONObject(response.body());
			totalValue= myJson.get("total").toString();	
		}
		
		catch(IOException | InterruptedException ex) {
			JOptionPane.showMessageDialog(null, "Coneccion fallida: "+ex,"Error", JOptionPane.ERROR_MESSAGE); 
		}
		
	return totalValue;	  
	}
	
	
	public String[] mostrarFiat() {
	return arrayFiat;  
	}
	
	
	public ArrayList<String> obetenerArraylistUpdate(String coin) {
	ArrayList<String> arrayAux= new ArrayList<>();
	for(int i=0;i<arrayFiat.length;i++) {
			 if(arrayFiat[i]!=(coin)){
				 arrayAux.add(arrayFiat[i]);
			 }
		 }
	return arrayAux;
	}
	
}
