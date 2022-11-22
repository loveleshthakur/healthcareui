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

	/*
	 * public static void main(String[] args) throws IOException,
	 * InterruptedException { fetchAllPatients(); }
	 */
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

}
