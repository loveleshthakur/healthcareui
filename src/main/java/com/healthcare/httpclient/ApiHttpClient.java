package com.healthcare.httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.model.Patient;

public class ApiHttpClient {

	
	public static List<Patient> fetchByName(String patientName) throws IOException, InterruptedException {
		String findByNameEndpoint = "http://localhost:8080/patient/name/" + patientName;

		var request = HttpRequest.newBuilder().uri(URI.create(findByNameEndpoint))
				.header("Content-Type", "application/json").GET().build();

		var client = HttpClient.newHttpClient();

		var response = client.send(request, HttpResponse.BodyHandlers.ofString());

		System.out.println(response.statusCode());
		System.out.println(response.body());

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(response.body(), new TypeReference<List<Patient>>() {
		});
	}
	public static List<Patient> fetchByGovtId(String govtId) throws IOException, InterruptedException {
		String findGovtIdEndpoint = "http://localhost:8080/patient/govtid/" + govtId;

		var request = HttpRequest.newBuilder().uri(URI.create(findGovtIdEndpoint))
				.header("Content-Type", "application/json").GET().build();

		var client = HttpClient.newHttpClient();

		var response = client.send(request, HttpResponse.BodyHandlers.ofString());

		System.out.println(response.statusCode());
		System.out.println(response.body());

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(response.body(), new TypeReference<List<Patient>>() {
		});
	}
	public static List<Patient> fetchAllPatients() throws IOException, InterruptedException {
		String findAllEndpoint = "http://localhost:8080/patient";

		var request = HttpRequest.newBuilder().uri(URI.create(findAllEndpoint))
				.header("Content-Type", "application/json").GET().build();

		var client = HttpClient.newHttpClient();

		var response = client.send(request, HttpResponse.BodyHandlers.ofString());

		System.out.println(response.statusCode());
		System.out.println(response.body());

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(response.body(), new TypeReference<List<Patient>>() {
		});
	}
	public static HttpResponse<String> updatePatient(Patient patient) throws IOException, InterruptedException {		
		var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(patient);
        
        String updateEndpoint = "http://localhost:8080/patient";
        
        System.out.println(">>>>>>>>xyzzzzzz: "+ requestBody);
        var request = HttpRequest.newBuilder()
                .uri(URI.create(updateEndpoint))
				.header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        var client = HttpClient.newHttpClient();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        System.out.println(response.statusCode());
		System.out.println(response.body());
		
		return response;

//        ObjectMapper mapper = new ObjectMapper();
//		return mapper.readValue(response.body(), new TypeReference<Patient>() {});
	}
	
	public static HttpResponse<String> savePatient(Patient patient) throws IOException, InterruptedException {		
		var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(patient); 
        
        String saveEndpoint = "http://localhost:8080/patient";
        
        var request = HttpRequest.newBuilder()
                .uri(URI.create(saveEndpoint))
				.header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        var client = HttpClient.newHttpClient();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        System.out.println(response.statusCode());
		System.out.println(response.body());

		return response;
		
	}
	public static Patient getPatient(String patientId) throws IOException, InterruptedException {
		String findByIdEndpoint = "http://localhost:8080/patient/"+patientId;

		var request = HttpRequest.newBuilder()
				.uri(URI.create(findByIdEndpoint))
				.header("Content-Type", "application/json")
				.GET()
				.build();

		var client = HttpClient.newHttpClient();

		var response = client.send(request, HttpResponse.BodyHandlers.ofString());

		System.out.println(response.statusCode());
		System.out.println(response.body());

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(response.body(), new TypeReference<Patient>() {});
	}
	
	public static HttpResponse<String> deletePatient(String patientId) throws IOException, InterruptedException {
		String deleteEndpoint = "http://localhost:8080/patient/"+patientId;

		var request = HttpRequest.newBuilder()
				.uri(URI.create(deleteEndpoint))
				.header("Content-Type", "application/json")
				.DELETE()
				.build();

		var client = HttpClient.newHttpClient();

		var response = client.send(request, HttpResponse.BodyHandlers.ofString());

		System.out.println(response.statusCode());
		System.out.println(response.body());
		
		return response;
	}
}
