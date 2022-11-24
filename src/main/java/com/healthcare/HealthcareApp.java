package com.healthcare;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import com.healthcare.httpclient.ApiHttpClient;
import com.healthcare.httpclient.ResponseMessageBox;
import com.healthcare.model.Address;
import com.healthcare.model.Patient;
import com.healthcare.model.Phone;
public class HealthcareApp {

	
	public static Display display = null;
	public static Shell shell = null;
	public static Tree tree = null;
	public static String patientId = null;
	public static boolean patientSelected = false;
	static Button viewPatient = null;
	static Button updatePatient = null;
	static Button deletePatient = null;
	
	/**
	 * Launch the application.
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		
		display = new Display();
		shell = new Shell(display);
		shell.setText("Patients List");
		shell.setLayout(new GridLayout(4, true));
		
		Text searchByNameText = new Text(shell, SWT.BORDER);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 1;
		searchByNameText.setLayoutData(gridData);
		
		Button searchByNameButton = new Button (shell, SWT.PUSH);
		GridData gridDataButton = new GridData();
		gridDataButton.horizontalAlignment = GridData.BEGINNING;
		gridDataButton.horizontalSpan = 1;
		searchByNameButton.setLayoutData(gridDataButton);
		searchByNameButton.setText ("Search Patient By Name");
		
		
		
		searchByNameButton.addSelectionListener(widgetSelectedAdapter(e -> {
			try {
				getPatientsList(searchByNameText.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}));
		
		
		
		Button searchByGovtIdButton = new Button (shell, SWT.PUSH);
		GridData gridDataButtonByGovtIdButton = new GridData();
		gridDataButtonByGovtIdButton.horizontalAlignment = GridData.END;
		gridDataButtonByGovtIdButton.horizontalSpan = 1;
		searchByGovtIdButton.setText ("Search Patient By Govt Id");
		
		searchByGovtIdButton.addSelectionListener(widgetSelectedAdapter(e -> {
			try {
				getPatientByGovtId(searchByNameText.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}));
		
		
		GridData gridDataForTree = new GridData();
		gridDataForTree.horizontalAlignment = GridData.FILL;
		gridDataForTree.horizontalSpan = 2;

		tree = new Tree(shell, SWT.V_SCROLL|SWT.H_SCROLL | SWT.FULL_SELECTION);
		tree.setHeaderVisible(true);
		tree.setLayoutData(gridDataForTree);
		final Menu headerMenu = new Menu(shell, SWT.POP_UP);
		
		final TreeColumn columnId = new TreeColumn(tree, SWT.NONE);
		columnId.setText("ID");
		columnId.setWidth(150);
		createMenuItem(headerMenu, columnId);

		final TreeColumn columnName = new TreeColumn(tree, SWT.NONE);
		columnName.setText("Name");
		columnName.setWidth(150);
		createMenuItem(headerMenu, columnName);
		
		
		  final TreeColumn columndob = new TreeColumn(tree, SWT.NONE);
		  columndob.setText("Date of Birth"); columndob.setWidth(150);
		  createMenuItem(headerMenu, columndob);
		  
		  final TreeColumn columnTelNumber = new TreeColumn(tree, SWT.NONE);
		  columnTelNumber.setText("Phone No"); columnTelNumber.setWidth(150);
		  createMenuItem(headerMenu, columnTelNumber);
		 
		
			
			  final TreeColumn columnStreetName = new TreeColumn(tree, SWT.NONE);
			  columnStreetName.setText("Street"); columnStreetName.setWidth(150);
			  createMenuItem(headerMenu, columnStreetName);
			  
			  final TreeColumn columnCityName = new TreeColumn(tree, SWT.NONE);
			  columnCityName.setText("City"); columnCityName.setWidth(150);
			  createMenuItem(headerMenu, columnCityName);
			  
			  final TreeColumn columnStateName = new TreeColumn(tree, SWT.NONE);
			  columnStateName.setText("State"); columnStateName.setWidth(150);
			  createMenuItem(headerMenu, columnStateName);
			  
			  final TreeColumn columnPinCode = new TreeColumn(tree, SWT.NONE);
			  columnPinCode.setText("Pin Code"); 
			  columnPinCode.setWidth(150);
			  createMenuItem(headerMenu, columnPinCode);
			  
			  final TreeColumn columnidentityType = new TreeColumn(tree, SWT.NONE);
			  columnidentityType.setText("identityType");
			  columnidentityType.setWidth(150);
			  createMenuItem(headerMenu, columnidentityType);
			  
			  final TreeColumn identityNo = new TreeColumn(tree, SWT.NONE);
			  identityNo.setText("identityNo");
			  identityNo.setWidth(150);
			  createMenuItem(headerMenu, identityNo);
			  
			 
		
		getPatientsList("");
		
		Menu menu = new Menu (shell, SWT.POP_UP);
		tree.setMenu (menu);

		MenuItem view = new MenuItem (menu, SWT.PUSH);
		view.setText ("View");
		view.addListener (SWT.Selection, event -> {
			viewActionListener();
		});
		//On click on patient record View Update and Delete should get enabled for Selected Patient
		tree.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				TreeItem[] selected = tree.getSelection();
				if (selected.length > 0) {
					patientId = selected[0].getText(0);
					patientSelected = true;
					changeButtonsStatus(patientSelected);
				}
			}
		});
		
		GridData gridDataForButtons = new GridData();
		gridDataForTree.horizontalAlignment = GridData.FILL;
		gridDataForTree.horizontalSpan = 4;
		
		Button createPatient = new Button (shell, SWT.PUSH);
		createPatient.setText ("Create Patient");
		//createPatient.setLayoutData(gridDataForButtons);
		createPatient.addSelectionListener(widgetSelectedAdapter(e -> {
			//Create new patient
			System.out.println("Create Patient method");
			new UpdatePatientDetails(display, false, false);
		}));
		
		viewPatient = new Button (shell, SWT.PUSH);
		viewPatient.setText ("View Patient");
		viewPatient.setEnabled(patientSelected);
		//viewPatient.setLayoutData(gridDataForButtons);
		viewPatient.addSelectionListener(widgetSelectedAdapter(e -> {
			viewActionListener();
		}));
		
		updatePatient = new Button (shell, SWT.PUSH);
		updatePatient.setText ("Update Patient");
		updatePatient.setEnabled(patientSelected);
		//updatePatient.setLayoutData(gridDataForButtons);
		updatePatient.addSelectionListener(widgetSelectedAdapter(e -> {
			updateActionListener();
		}));
		
		
		deletePatient = new Button (shell, SWT.PUSH);
		deletePatient.setText ("Delete Patient");
		deletePatient.setEnabled(patientSelected);
		//deletePatient.setLayoutData(gridDataForButtons);
		deletePatient.addSelectionListener(widgetSelectedAdapter(e -> {
			deleteActionListener();
		}));
		
		
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
		
	}

	static void getPatientByGovtId(String govtId) throws IOException, InterruptedException {

		List<Patient> patientsList = null;
		if(govtId != "" && !govtId.isEmpty() && !govtId.equals("")) {
			patientsList = ApiHttpClient.fetchByGovtId(govtId);
			System.out.println(">>>>>>>>>>getPatientByGovtId "+govtId+" >>>>>>>>> "+patientsList);
		}
		tree.removeAll();
		tree.update();
		if (patientsList!=null)
		for (int i=0; i<patientsList.size(); i++) {
			Patient patientListItem = patientsList.get(i);

			System.out.println(" patientListItem " +patientListItem);
			TreeItem item = new TreeItem(tree, SWT.NONE);

			item.setText (0, String.valueOf(patientListItem.getPatientId()));
			item.setText (1, patientListItem.getName());
			if (patientListItem.getDob()!=null) {
			 item.setText (2,  patientListItem.getDob());
			}
			
				
			
			patientListItem.getPhone().stream().forEach(ph-> System.out.println(ph.getPhoenumber()));
			List<Phone> phonNumbers = patientListItem.getPhone();
			for ( Phone ph :phonNumbers )
			{
				item.setText (3,  String.valueOf( ph.getPhoenumber()));
			}
			
			List<Address> addressList = patientListItem.getAddresses();
			if (addressList.size()>0) {
			Address address1 = addressList.get(0);
			 item.setText (4, address1.getStreet()); 
			 item.setText (5, address1.getCity()); 
			 item.setText (6, address1.getState());
			 item.setText (7, address1.getPin().toString());
			}
			  
			for (int j=1; j<addressList.size(); j++) {
				Address address = addressList.get(j);
				
				  TreeItem subItem = new TreeItem(item, SWT.NONE);
				 
				 
				  
				  subItem.setText (4, address.getStreet()); 
				  subItem.setText (5, address.getCity()); 
				  subItem.setText (6, address.getState());
				  subItem.setText (7, address.getPin().toString());
				 
			}
			if (patientListItem.getGovtIds().size()>0) {
			item.setText(8, patientListItem.getGovtIds().get(0).getIdentityType());
			item.setText(9, patientListItem.getGovtIds().get(0).getIdentityNo());
			}
		}
	
	}

	/**
	 * Open the window.
	 */
	public void open() throws IOException, InterruptedException {}
	
	
	static void getPatientsList(String byName) throws IOException, InterruptedException {
		List<Patient> patientsList = null;
		if(byName != "" && !byName.isEmpty() && !byName.equals("")) {
			patientsList = ApiHttpClient.fetchByName(byName);
			System.out.println(">>>>>>>>>>>>>>>>>>> "+patientsList);
		} else {
			patientsList = ApiHttpClient.fetchAllPatients();
		}

		tree.removeAll();
		tree.update();

		for (int i=0; i<patientsList.size(); i++) {
			Patient patientListItem = patientsList.get(i);

			System.out.println(" patientListItem " +patientListItem);
			TreeItem item = new TreeItem(tree, SWT.NONE);

			item.setText (0, String.valueOf(patientListItem.getPatientId()));
			item.setText (1,  patientListItem.getName()== null ? "" : patientListItem.getName()  );
			if (patientListItem.getDob()!=null) {
			 item.setText (2,  patientListItem.getDob());
			}
			
				
			
			patientListItem.getPhone().stream().forEach(ph-> System.out.println(ph.getPhoenumber()));
			List<Phone> phonNumbers = patientListItem.getPhone();
			for ( Phone ph :phonNumbers )
			{
				item.setText (3,  String.valueOf( ph.getPhoenumber()));
			}
			
			List<Address> addressList = patientListItem.getAddresses();
			if (addressList.size()>0) {
			Address address1 = addressList.get(0);
			 item.setText (4, address1.getStreet()); 
			 item.setText (5, address1.getCity()); 
			 item.setText (6, address1.getState());
			 item.setText (7, address1.getPin().toString());
			}
			  
			for (int j=1; j<addressList.size(); j++) {
				Address address = addressList.get(j);
				
				  TreeItem subItem = new TreeItem(item, SWT.NONE);
				 
				 
				  
				  subItem.setText (4, address.getStreet()); 
				  subItem.setText (5, address.getCity()); 
				  subItem.setText (6, address.getState());
				  subItem.setText (7, address.getPin().toString());
				 
			}
			if (patientListItem.getGovtIds().size()>0) {
			item.setText(8, patientListItem.getGovtIds().get(0).getIdentityType());
			item.setText(9, patientListItem.getGovtIds().get(0).getIdentityNo());
			}
		}
	}
	
	static void changeButtonsStatus(boolean patientSelected) {
		viewPatient.setEnabled(patientSelected);
		updatePatient.setEnabled(patientSelected);
		deletePatient.setEnabled(patientSelected);
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		

	}
	
	static void viewActionListener() {

		if(patientId == "" || patientId == null) {
			ResponseMessageBox responseBox = new ResponseMessageBox(shell);
			responseBox.waringResponse("Please select patient record");
			return;
		}
		
		UpdatePatientDetails patientModify = new UpdatePatientDetails(display, true, false);
		try {
			patientModify.getPatient(patientId);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} 
	}
	
	static void updateActionListener() {

		if(patientId == "" || patientId == null) {
			ResponseMessageBox responseBox = new ResponseMessageBox(shell);
			responseBox.waringResponse("Please select patient record");
			return;
		}
		
		UpdatePatientDetails patientModify = new UpdatePatientDetails(display, false, true);
		try {
			patientModify.getPatient(patientId);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} 
	}
	
	static void deleteActionListener() {
		try {

			if(patientId == "" || patientId == null) {
				ResponseMessageBox responseBox = new ResponseMessageBox(shell);
				responseBox.waringResponse("Please select patient record");
				return;
			}
			
			HttpResponse<String> response =  ApiHttpClient.deletePatient(patientId);

			ResponseMessageBox responseBox = new ResponseMessageBox(shell);
			if(response.statusCode() == 200) {
				responseBox.successResponse("Patient details removed successfully");
				getPatientsList("");
				patientSelected = false;
				patientId = null;
				changeButtonsStatus(patientSelected);
			} else {
				responseBox.failureResponse(response);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	static void createMenuItem(Menu parent, final TreeColumn column) {
		final MenuItem itemName = new MenuItem(parent, SWT.CHECK);
		itemName.setText(column.getText());
		itemName.setSelection(column.getResizable());
		itemName.addListener(SWT.Selection, event -> {
			if (itemName.getSelection()) {
				column.setWidth(150);
				column.setResizable(true);
			} else {
				column.setWidth(0);
				column.setResizable(false);
			}
		});
	}
	
}
