package com.healthcare.httpclient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.model.Address;
import com.healthcare.model.GovernmentIdentity;
import com.healthcare.model.Patient;
import com.healthcare.model.Phone;

class ApiHttpClientTest {
	@InjectMocks
	private ApiHttpClient apiHttpClient;

	
	  @Mock private ObjectMapper objectMapper;
	  
	  
	private List<Patient> getpatients() {
		List<Patient> patients = new ArrayList<Patient>();

		Patient patient = new Patient();
		patient.setPatientId(1001);
		patient.setName("LoveleshSingh");
		patient.setDob("1989-06-06");
		List<Phone> phoneList = new ArrayList<>();
		Phone phone= new Phone();
		phone.setId(2001);
		phone.setPhoenumber(8123812381L);
		phoneList.add(phone);
		patient.setPhone(phoneList );

		List<Address> addressList = new ArrayList<>();
		
		Address currentAddress = new Address();		
		currentAddress.setId(3001L);
		currentAddress.setStreet("JP Nagar Road");
		currentAddress.setCity("Bangalore");
		currentAddress.setState("Karnataka");
		currentAddress.setPin(560078L);
		
		Address permanentAddress = new Address();		
		permanentAddress.setId(3002L);
		permanentAddress.setStreet("Jayanagar Road");
		permanentAddress.setCity("Bangalore");
		permanentAddress.setState("Karnataka");
		permanentAddress.setPin(560079L);
		
		addressList.add(currentAddress);
		addressList.add(permanentAddress);
		
		patient.setAddresses(addressList);
		
		List<GovernmentIdentity> govtIds = new ArrayList<>();
		GovernmentIdentity govtId= new GovernmentIdentity();
		govtId.setIdentityType("PAN");
		govtId.setIdentityNo("DPMPS4157A");
		govtIds.add(govtId);
		patient.setGovtIds(govtIds );
		
		patients.add(patient);

		return patients;
	}

	@Test
	void testFetchByName() throws IOException, InterruptedException {
		
		apiHttpClient.savePatient(getpatients().get(0));
		
		
		
		List<Patient> patientsList = apiHttpClient.fetchByName("LoveleshSingh");
		System.out.println("testFetchByName >>> "+patientsList);
		assertNotNull(patientsList);
		apiHttpClient.deletePatient(String.valueOf(patientsList.get(0).getPatientId()));
	
	}

	@Test
	void testFetchByGovtId() throws IOException, InterruptedException {

		apiHttpClient.savePatient(getpatients().get(0));
		List<Patient> patientsList = apiHttpClient.fetchByGovtId("DPMPS4157A");
		System.out.println("testFetchByGovtId >>> "+patientsList);
		assertNotNull(patientsList);
		apiHttpClient.deletePatient(String.valueOf(patientsList.get(0).getPatientId()));
	
	}

	@Test
	void testFetchAllPatients() throws IOException, InterruptedException {
		int expectedSize = apiHttpClient.fetchAllPatients().size();
		
		HttpResponse<String> response = apiHttpClient.savePatient(getpatients().get(0));
		ObjectMapper mapper = new ObjectMapper();
		Patient patient = mapper.readValue(response.body(), new TypeReference<Patient>() {});
		System.out.println(patient);
		
		List<Patient> patientsList = apiHttpClient.fetchAllPatients();
		assertEquals(expectedSize+1, patientsList.size());
		
		apiHttpClient.deletePatient(String.valueOf(patient.getPatientId()));
	}

	@Test
	void testUpdatePatient() throws IOException, InterruptedException {

		HttpResponse<String> response = apiHttpClient.savePatient(getpatients().get(0));
		 ObjectMapper mapper = new ObjectMapper();
		 Patient patientResult = mapper.readValue(response.body(), new TypeReference<Patient>() {});	
		 
		 Patient patient = ApiHttpClient.getPatient(String.valueOf( patientResult.getPatientId()));
		 
		 patient.setName("Rajkumar");	
		
		apiHttpClient.updatePatient(patient);
		
		assertEquals("Rajkumar", patient.getName());
		apiHttpClient.deletePatient(String.valueOf(patient.getPatientId()));
	
	}

	@Test
	void testSavePatient() throws IOException, InterruptedException {

		Patient patient = getpatients().get(0);
		HttpResponse<String> response = apiHttpClient.savePatient(patient);
		
		 ObjectMapper mapper = new ObjectMapper();
		 Patient patientResult = mapper.readValue(response.body(), new TypeReference<Patient>() {});
		 
		 assertEquals("LoveleshSingh", patientResult.getName());
		 apiHttpClient.deletePatient(String.valueOf( patientResult.getPatientId()));
	
	}

	@Test
	void testGetPatient() throws IOException, InterruptedException {

		HttpResponse<String> response = apiHttpClient.savePatient(getpatients().get(0));
		 ObjectMapper mapper = new ObjectMapper();
		 Patient patientResult = mapper.readValue(response.body(), new TypeReference<Patient>() {});	
		 
		 Patient patient = apiHttpClient.getPatient(String.valueOf( patientResult.getPatientId()));
		
		 System.out.println("testGetPatient >>> "+patient);
		 assertNotNull(patient);
		 apiHttpClient.deletePatient(String.valueOf(patient.getPatientId()));
	}



}
