package oy6icjJSON;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONWriteOY6ICJ {
	public static void main(String[] args) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		
		try (FileReader reader = new FileReader("res/orarendOY6ICJ.JSON")){
			
			JSONObject jsonObject = (JSONObject)parser.parse(reader);
			JSONObject orarendObj = (JSONObject) jsonObject.get("oy6icj_orarend");
			
			
			JSONArray oraArray = (JSONArray) orarendObj.get("ora");
			
			JSONObject newJson = new JSONObject();
			JSONObject newOrarend = new JSONObject();
			
			for(Object o : oraArray) {
				JSONObject ora = (JSONObject) o;
				JSONObject idopont = (JSONObject)ora.get("idopont");
				
				String targy = (String) ora.get("targy");
				String nap = (String) idopont.get("nap");
				String tol = (String) idopont.get("tol");
				String ig = (String) idopont.get("ig");
				String helyszin = (String) ora.get("helyszin");
				String oktato = (String) ora.get("oktato");
				String szak = (String) ora.get("szak");
				String id = (String) ora.get("_id");
				String tipus = (String) ora.get("_tipus");
				
				
				System.out.println("\nAktuális elem: óra");
				System.out.printf("Tárgy: %s\n", targy);
				System.out.printf("Időpont: %s %s-%s\n", nap, tol, ig);
				System.out.printf("Helyszín: %s\n", helyszin);
				System.out.printf("Oktató: %s\n", oktato);
				System.out.printf("Szak: %s\n", szak);
				System.out.printf("ID: %s\n", id);
				System.out.printf("Típus: %s\n", tipus);
				
				
				try(FileWriter writer = new FileWriter("res/orarendOY6ICJ1.json")){
					writer.write(newJson.toJSONString());
				}
			}
		}
	}
}
