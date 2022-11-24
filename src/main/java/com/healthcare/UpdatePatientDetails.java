package com.healthcare;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.healthcare.httpclient.ApiHttpClient;
import com.healthcare.httpclient.ResponseMessageBox;
import com.healthcare.model.Address;
import com.healthcare.model.GovernmentIdentity;
import com.healthcare.model.Patient;
import com.healthcare.model.Phone;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.events.*;
import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/*
 * This class will generate the SWT UI to provide support for the below CURD operations:
 * <ul>
 * <li> Patient data saving
 * <li> Patient data updating
 * <li> Finding Patient by ID
 * </ul>
 *
 * @author      Lovelesh
 * @version     1.0
 * @since       1.0
 */

public class UpdatePatientDetails {
	
	Display display;
	Shell shell;
	Shell dialog;

	Text idText;
	Text nameText;
	Text telNumberText;
	Text dobText;

	//Current Address details
	Text curAddressIdText;
	Text streetText;
	Text cityText;
	Text stateText;
	Text pinCodeText;

	//Permanent Address Details
	Text perAddressIdText;
	Text perStreetText;
	Text perCityText;
	Text perStateText;
	Text perPinCodeText;
	
	//Government Identity
	Text identityTypeText;
	Text identityNoText;
	
	public UpdatePatientDetails(Display display, boolean viewPatient, boolean updatePatient) 
	{
		this.display = display;

		shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX | SWT.ON_TOP);
		shell.setText("Create Patient");
		shell.setLayout(new GridLayout(2, true));

		shell.setMinimumSize(800, 0);

		if(viewPatient)
			shell.setText("View Patient");
		else if (updatePatient)
			shell.setText("Update Patient");

		// id
		if(viewPatient || updatePatient) {
			Label idLabel = new Label(shell, SWT.NONE);
			idLabel.setText("ID:");

			idText = new Text(shell, SWT.BORDER);
			idText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			idText.setEditable(false);
		}

		//  name
		Label nameLabel = new Label(shell, SWT.NONE);
		nameLabel.setText("Name:");

		nameText = new Text(shell, SWT.BORDER);
		nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		nameText.setEditable(!viewPatient);

		

		

		//telephone number
		Label telNumberLabel = new Label(shell, SWT.NONE);
		telNumberLabel.setText("Telephone No:");

		telNumberText = new Text(shell, SWT.BORDER);
		telNumberText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		telNumberText.setEditable(!viewPatient);
		telNumberText.setTextLimit(10);

		//dob
		Label dobLabel = new Label(shell, SWT.NONE);
		dobLabel.setText("Date of Birth:");

		dobText = new Text(shell, SWT.BORDER);
		dobText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		dobText.setText("YYYY-MM-DD");
		dobText.setEditable(!viewPatient);

		//current address
		Group addressGroup = new Group(shell, SWT.NONE);
		addressGroup.setText("Current Address");
		addressGroup.setLayout(new GridLayout(2, true));
		addressGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		//current address id
		if(viewPatient || updatePatient) {
			Label curAddressId = new Label(addressGroup, SWT.NONE);
			curAddressId.setText("Id:");
			curAddressId.setVisible(false);

			curAddressIdText = new Text(addressGroup, SWT.BORDER);
			curAddressIdText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			curAddressIdText.setVisible(false);	
		}

		
		// street				
		Label streetLabel = new Label(addressGroup, SWT.NONE);
		streetLabel.setText("Street:");

		streetText = new Text(addressGroup, SWT.BORDER);
		streetText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		streetText.setEditable(!viewPatient);

		// city
		Label cityLabel = new Label(addressGroup, SWT.NONE);
		cityLabel.setText("City:");

		cityText = new Text(addressGroup, SWT.BORDER);		
		cityText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		cityText.setEditable(!viewPatient);

		// state
		Label stateLabel = new Label(addressGroup, SWT.NONE);
		stateLabel.setText("State:");

		stateText = new Text(addressGroup, SWT.BORDER);		
		stateText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		stateText.setEditable(!viewPatient);

		//pin code
		Label pinCodeLabel = new Label(addressGroup, SWT.NONE);
		pinCodeLabel.setText("PinCode:");

		pinCodeText = new Text(addressGroup, SWT.BORDER);
		pinCodeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		pinCodeText.setEditable(!viewPatient);
		pinCodeText.setTextLimit(6);


		//permanent address
		Group perAddressGroup = new Group(shell, SWT.NONE);
		perAddressGroup.setText("Permanent Address");
		perAddressGroup.setLayout(new GridLayout(2, true));
		perAddressGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		//Permanent address id
		if(viewPatient || updatePatient) {
			Label perAddressId = new Label(perAddressGroup, SWT.NONE);
			perAddressId.setText("Id:");
			perAddressId.setVisible(false);

			perAddressIdText = new Text(perAddressGroup, SWT.BORDER);
			perAddressIdText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			perAddressIdText.setVisible(false);
		}

		// street
		Label perStreetLabel = new Label(perAddressGroup, SWT.NONE);
		perStreetLabel.setText("Street:");

		perStreetText = new Text(perAddressGroup, SWT.BORDER);
		perStreetText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		perStreetText.setEditable(!viewPatient);

		// city
		Label perCityLabel = new Label(perAddressGroup, SWT.NONE);
		perCityLabel.setText("City:");

		perCityText = new Text(perAddressGroup, SWT.BORDER);		
		perCityText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		perCityText.setEditable(!viewPatient);

		// state
		Label perStateLabel = new Label(perAddressGroup, SWT.NONE);
		perStateLabel.setText("State:");

		perStateText = new Text(perAddressGroup, SWT.BORDER);		
		perStateText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		perStateText.setEditable(!viewPatient);

		//pin code
		Label perPinCodeLabel = new Label(perAddressGroup, SWT.NONE);
		perPinCodeLabel.setText("PinCode:");

		perPinCodeText = new Text(perAddressGroup, SWT.BORDER);
		perPinCodeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		perPinCodeText.setEditable(!viewPatient);
		perPinCodeText.setTextLimit(6);
		
		//Government Identity
				Group governmentIdentityGroup = new Group(shell, SWT.NONE);
				governmentIdentityGroup.setText("Government Identity");
				governmentIdentityGroup.setLayout(new GridLayout(2, true));
				governmentIdentityGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
			
				//IdentityType
				Label IdentityTypeLabel = new Label(governmentIdentityGroup, SWT.NONE);
				IdentityTypeLabel.setText("Identity Type:");

				
				identityTypeText = new Text(governmentIdentityGroup, SWT.BORDER);
				identityTypeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				identityTypeText.setEditable(!viewPatient); 
				
				
				//IdentityNo
				Label IdentityNoLabel = new Label(governmentIdentityGroup, SWT.NONE);
				IdentityNoLabel.setText("Identity No:");

				
				identityNoText = new Text(governmentIdentityGroup, SWT.BORDER);
				identityNoText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				identityNoText.setEditable(!viewPatient); 
				 	

		if(!viewPatient) {
			Button ok = new Button (shell, SWT.PUSH);
			if(updatePatient) {
				ok.setText ("Update Patient");
			} else {
				ok.setText ("Create Patient");
			}
			ok.addSelectionListener(widgetSelectedAdapter(e -> {
				try {
					HttpResponse<String> response;
					if(updatePatient) {
						response  = ApiHttpClient.updatePatient(preparePatient());
					} else {
						response  = ApiHttpClient.savePatient(preparePatient());
					}
					ResponseMessageBox responseBox = new ResponseMessageBox(shell);
					if(response.statusCode() == 200) {
						responseBox.successResponse("Patient details updated successfully");
						shell.close();
						HealthcareApp.getPatientsList("");
						HealthcareApp.changeButtonsStatus(false);
					} else {
						responseBox.failureResponse(response);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}));
		}

		Button cancel = new Button (shell, SWT.PUSH);
		cancel.setText ("Close");
		cancel.addSelectionListener(widgetSelectedAdapter(e -> shell.close()));

		dobText.setEditable(false);
		dobText.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if(!viewPatient && !updatePatient) {
					dialog = new Shell (shell, SWT.DIALOG_TRIM);
					dialog.setLayout (new GridLayout (1, true));
					final DateTime calendar = new DateTime (dialog, SWT.CALENDAR | SWT.BORDER);
					Button ok = new Button (dialog, SWT.PUSH);
					ok.setText ("OK");
					ok.setLayoutData(new GridData (SWT.FILL, SWT.CENTER, false, false));
					ok.addSelectionListener (new SelectionAdapter () {
						public void widgetSelected (SelectionEvent e) {
							String month = String.valueOf(calendar.getMonth () + 1); 
							month = month.length() == 2 ? month : "0"+month;						
							String day = String.valueOf(calendar.getDay()); 
							day = day.length() == 2 ? day : "0"+day;						
							dobText.setText(calendar.getYear () + "-" + month + "-" + day);
							dialog.close ();
						}
					});
					dialog.setDefaultButton (ok);
					dialog.pack ();
					dialog.open ();
				}
			}
		});

		shell.pack();
		shell.open();
	
		
	}
	public Patient getPatient(String patientId) throws IOException, InterruptedException {		
		Patient patient = ApiHttpClient.getPatient(patientId); 
		if(idText != null)
			idText.setText(String.valueOf(patient.getPatientId()) );

		nameText.setText(patient.getName());
		if (patient.getPhone().size()>0) {
		telNumberText.setText(String.valueOf( patient.getPhone().get(0).getPhoenumber()) );
		}
		dobText.setText(patient.getDob());

		List<Address> addressList = patient.getAddresses();
		for (int j=0; j<addressList.size(); j++) {
			Address address = addressList.get(j);			
			if(j==0)
			{
				curAddressIdText.setText(address.getId().toString());
				streetText.setText(address.getStreet());
				cityText.setText(address.getCity());
				stateText.setText(address.getState());
				pinCodeText.setText(address.getPin().toString());
			}
			else
			{
			
			  perAddressIdText.setText(address.getId().toString());
			  perStreetText.setText(address.getStreet());
			  perCityText.setText(address.getCity());
			  perStateText.setText(address.getState());
			  perPinCodeText.setText(address.getPin().toString());
			  
			}
		}
		
		List<GovernmentIdentity> getGovtIds = patient.getGovtIds();
		for(GovernmentIdentity govtId : getGovtIds) {
			
			identityTypeText.setText(govtId.getIdentityType());
			identityNoText.setText(govtId.getIdentityNo());
		}
		
		return patient;
	}

	public Patient preparePatient() {
		Patient patient = new Patient();
		try {
			List<Address> addressList = new ArrayList<Address>();
			Address currentAddress = new Address();
			if(curAddressIdText != null) {
				currentAddress.setId(Long.parseLong(curAddressIdText.getText()));
			}
			currentAddress.setStreet(streetText.getText());
			currentAddress.setCity(cityText.getText());
			currentAddress.setState(stateText.getText());
			currentAddress.setPin(Long.parseLong(pinCodeText.getText()));
			//currentAddress.setAddressType("CURRENT");

			addressList.add(currentAddress);

			Address permanentAddress = new Address();
			if(perAddressIdText != null) {
				permanentAddress.setId(Long.parseLong(perAddressIdText.getText()));
			}
			permanentAddress.setStreet(perStreetText.getText());
			permanentAddress.setCity(perCityText.getText());
			permanentAddress.setState(perStateText.getText());
			System.out.println(" >>>>>>>>>>>>>>>perPinCodeText.getText()"+perPinCodeText.getText()+">>>");
			if (perPinCodeText.getText()!=null ||(! perPinCodeText.getText().equals("")))
			permanentAddress.setPin(Long.parseLong(perPinCodeText.getText()));
			//permanentAddress.setAddressType("PERMANENT");

			addressList.add(permanentAddress);

			if(idText != null)
				patient.setPatientId(Integer.parseInt(idText.getText()));
			patient.setName(nameText.getText());
			patient.setDob(dobText.getText());

			patient.setAddresses(addressList);
			
			Long phoneNum=Long.parseLong(telNumberText.getText());
			
			List<Phone> phoneList = new ArrayList<>();
			Phone phone = new Phone();
			phone.setPhoenumber(phoneNum);
			phoneList.add(phone );
			patient.setPhone(phoneList );
			
			List<GovernmentIdentity> govtIds = new ArrayList<>();
			GovernmentIdentity govtId = new GovernmentIdentity();
			govtId.setIdentityType(identityTypeText.getText());
			govtId.setIdentityNo(identityNoText.getText());
			govtIds.add(govtId );
			patient.setGovtIds(govtIds );
		} catch (Exception e1) {
			e1.printStackTrace();
			ResponseMessageBox responseBox = new ResponseMessageBox(shell);
			responseBox.exceptionHanlder(e1);
			shell.close();
		} 
		return patient;
	}
}
