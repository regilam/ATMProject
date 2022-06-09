package ATM;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import DatabaseConnection.DBConnection;

public class OptionMenu {
	Scanner menuInput = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'Rs.'###,##0.00");
	AccountDetails accountDetails = new AccountDetails();
	List<AccountDetails> accDetails = new ArrayList<AccountDetails>();
	public void setAccountDetails(List<AccountDetails> ad)
	{
		accDetails = ad;
	}

	public void getLogin() throws IOException {
		boolean end = false;
		int customerNumber = 0;
		int pwd = -1;
		while (!end) {
			try {
				System.out.print("\nEnter your customer number: ");
				customerNumber = menuInput.nextInt();
				System.out.print("\nEnter your PIN number: ");
				pwd = menuInput.nextInt();
				
				for(int i=0;i<accDetails.size();i++) {
					AccountDetails ad = accDetails.get(i);
					if (ad.getCustomerNumber()==customerNumber && ad.getPIN()==pwd) {
						getAccountType(ad);
						end = true;
						accountDetails = ad;
						break;
					}
				}
				if (!end) {
					System.out.println("\nWrong Customer Number or Pin Number");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Character(s). Only Numbers.");
			}
		}
	}

	public void getAccountType(AccountDetails acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nSelect the Option you want to access: ");
				System.out.println(" Type 1 - Savings Account");
				System.out.println(" Type 2 - Exit");
				System.out.print("\nChoice: ");

				int selection = menuInput.nextInt();

				switch (selection) {
				case 3:
					//getChecking(acc);
					break;
				case 1:
					getSaving(acc);
					break;
				case 2:
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
	}



	public void getSaving(AccountDetails acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nSavings Account: ");
				System.out.println(" Type 1 - View Balance");
				System.out.println(" Type 2 - Withdraw Funds");
				System.out.println(" Type 3 - Deposit Funds");
				System.out.println(" Type 4 - Transfer Funds");
				System.out.println(" Type 5 - Exit");
				System.out.print("Choice: ");
				int selection = menuInput.nextInt();
				switch (selection) {
				case 1:
					System.out.println("\nSavings Account Balance: " + moneyFormat.format(acc.getBalance()));
					break;
				case 2:
					acc.getSavingWithdrawInput(acc);
					break;
				case 3:
					acc.getSavingDepositInput(acc);
					break;
				case 4:
					acc.getTransferInput(acc,accDetails);
					break ;
				case 5:
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
	}

	public void createAccount() throws IOException {
		int cst_no = 0;
		boolean end = false;
		AccountDetails adc = new AccountDetails();
		while (!end) {
			try {
				System.out.println("\nEnter your Customer Number ");
				cst_no = menuInput.nextInt();
				for(int i=0;i<accDetails.size();i++) {
					AccountDetails ad = accDetails.get(i);
					if (ad.getCustomerNumber()!=cst_no) {
						end = true;
						break;
					}
				}
				if (!end) {
					System.out.println("\nThis customer number is already registered");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		adc.setCustomerNumber(cst_no);
		System.out.println("\nEnter PIN to be registered");
		adc.setPIN(menuInput.nextInt());
		System.out.println("\nEnter your First Name");
		adc.setFirstName(menuInput.next());
		System.out.println("\nEnter your Last Name");
		adc.setLastName(menuInput.next());
		System.out.println("\nEnter your Bank Name");
		adc.setBankName(menuInput.next());
		System.out.println("\nEnter your Account Type");
		String accType = menuInput.next();
		adc.setAccountType(accType);
		double bal=0.00;
		if(accType.equalsIgnoreCase("current"))
		{
			bal=3000.00;
		}
		
		adc.setBalance(bal);
		
		DBConnection.insertDB(adc);
		//data.put(cst_no, new Account(cst_no, pin));
		System.out.println("\nYour new account has been successfuly registered!");
		System.out.println("\nRedirecting to login.............");
		getLogin();
	}

	public void mainMenu() throws IOException {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\n Type 1 - Login");
				System.out.println(" Type 2 - Create Account");
				System.out.print("\nChoice: ");
				int choice = menuInput.nextInt();
				switch (choice) {
				case 1:
					getLogin();
					end = true;
					break;
				case 2:
					createAccount();
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		System.out.println("\nThank You for using this ATM.\n");
		menuInput.close();
		System.exit(0);
	}

}