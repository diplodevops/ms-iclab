package com.devopsusach2020.rest;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.devopsusach2020.model.Pais;
import com.devopsusach2020.model.Mundial;
import com.google.gson.Gson;

@RestController
@RequestMapping(path = "/rest/mscovid")
public class RestData {
	
	private final static Logger LOGGER = Logger.getLogger("devops.subnivel.Control");

	
	@GetMapping(path = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Pais getData(@RequestParam(name = "msg") String message){
		
		LOGGER.log(Level.INFO, "Proceso exitoso de prueba");
		
		Pais response = new Pais();
		response.setMensaje("Mensaje Recibido: " + message);
		return response;
	}

	private ResponseEntity<String> ValidaApiCovid(String message) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> call = null;
		LOGGER.log(Level.INFO, "valor de message ===> " + message);
		if (message.isEmpty()) {
			try {
				call = restTemplate.getForEntity("https://api.covid19api.com/world/total", String.class);
			} catch(Exception e) {
				LOGGER.log(Level.SEVERE, e.toString());
			}
		} else {
			try{
				call = restTemplate.getForEntity("https://api.covid19api.com/live/country/" + message, String.class);
			} catch(Exception e) {
				LOGGER.log(Level.SEVERE, e.toString());
			}
		}

		return call;
	}

	@GetMapping(path = "/estadoPais", produces = MediaType.APPLICATION_JSON_VALUE)	
	public @ResponseBody Pais getTotalPais(@RequestParam(name = "pais") String message){
		LOGGER.log(Level.INFO, "Consulta por pais");
		Pais response = new Pais();
		ResponseEntity<String> call = this.ValidaApiCovid(message);
		LOGGER.log(Level.INFO, call.getBody());

		int confirmed = 0;
		int death = 0;
		int recovered = 0;
		Gson gson = new Gson();
		Pais[] estados = gson.fromJson(call.getBody().toLowerCase(), Pais[].class);

		if (estados.length > 0) {
			for (Pais estado : estados) {
				response.setDate(estado.getDate());
				response.setActive(estado.getActive());
				confirmed += estado.getConfirmed();
				death += estado.getDeaths();
				recovered += estado.getRecovered();
			}
		} else {
			return (Pais) ResponseEntity.status(409);
		}

		response.setConfirmed(confirmed);
		response.setDeaths(death);
		response.setRecovered(recovered);
		response.setCountry(message);
		response.setMensaje("ok");
		return response;
	}

	@GetMapping(path = "/estadoMundial", produces = MediaType.APPLICATION_JSON_VALUE)	
	public @ResponseBody Mundial getTotalMundial(){
		LOGGER.log(Level.INFO, "Consulta mundial");

		ResponseEntity<String> call = this.ValidaApiCovid("");
		Mundial response = new Mundial();
		Gson gson = new Gson();        
		Mundial estado = gson.fromJson(call.getBody().toLowerCase(), Mundial.class);
		response.setTotalConfirmed(estado.getTotalConfirmed());        
		response.setTotalDeaths(estado.getTotalDeaths());        
		response.setTotalRecovered(estado.getTotalRecovered());

		return response;
	}
}


