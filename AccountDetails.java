package ATM;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import DatabaseConnection.DBConnection;

public  class AccountDetails {
	
	Scanner input = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'Rs.'###,##0.00");
	

	private int CustomerNumber;
	private String LastName;
	private String FirstName;
	private String AccountType;
	private String BankName;
	private double Balance;
	private int PIN;
	private double savingBalance;
	public int getCustomerNumber() {
		return CustomerNumber;
	}
	public void setCustomerNumber(int customerNumber) {
		CustomerNumber = customerNumber;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getAccountType() {
		return AccountType;
	}
	public void setAccountType(String accountType) {
		AccountType = accountType;
	}
	public String getBankName() {
		return BankName;
	}
	public void setBankName(String bankName) {
		BankName = bankName;
	}
	public double getBalance() {
		return Balance;
	}
	public void setBalance(double balance) {
		Balance = balance;
	}
	public int getPIN() {
		return PIN;
	}
	public void setPIN(int pIN) {
		PIN = pIN;
	}
	
	public double calcSavingWithdraw(double amount) {
		savingBalance = (savingBalance - amount);
		return savingBalance;
	}
	
	public double calcSavingDeposit(double amount) {
		savingBalance = (savingBalance + amount);
		return savingBalance;
	}
	
	public void getSavingWithdrawInput(AccountDetails acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(acc.getBalance()));
				savingBalance = acc.getBalance();
				System.out.print("\nAmount you want to withdraw from Savings Account: ");
				double amount = input.nextDouble();
				if ((acc.getBalance() - amount) >= 0 && amount >= 0) {
					calcSavingWithdraw(amount);
					System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingBalance));
					acc.setBalance(savingBalance);
					DBConnection.updateDB(acc);
					end = true;
				} else {
					System.out.println("\nBalance Cannot Be Negative.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				input.next();
			}
		} 
	} 
	
	public void getSavingDepositInput(AccountDetails acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(acc.getBalance()));
				savingBalance = acc.getBalance();
				System.out.print("\nAmount you want to deposit into your Savings Account: ");
				double amount = input.nextDouble();

				if ((savingBalance + amount) >= 0 && amount >= 0) {
					calcSavingDeposit(amount);
					System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingBalance));
					acc.setBalance(savingBalance);
					DBConnection.updateDB(acc);
					end = true;
				} else {
					System.out.println("\nBalance Cannot Be Negative.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				input.next();
			}
		}
	}
	
	public void getTransferInput(AccountDetails acc,List<AccountDetails> accDetails) {
		boolean end = false;
		boolean flag=false;
		AccountDetails transferacc = new AccountDetails();
		while (!end) {
			try {
				 if (acc.getAccountType().equalsIgnoreCase("Saving")) {
					System.out.println("\nEnter the Customer Number to whom you need to transfer the amount : ");
					int cust_no = input.nextInt();
					for(int i=0;i<accDetails.size();i++) {
						AccountDetails ad = accDetails.get(i);
						if (ad.getCustomerNumber()==cust_no) {
							flag = true;
							transferacc=ad;
							break;
						}
					}
					if (flag) {

						System.out.println("\nYour Current Savings Account Balance: " + moneyFormat.format(acc.getBalance()));
						savingBalance = acc.getBalance();
						System.out.print("\nEnter the Amount you want to transfer : ");
						double amount = input.nextDouble();
						if ((savingBalance - amount) >= 0 && amount >= 0) {
							calcSavingWithdraw(amount);
							System.out.println("\nYour Current Savings Account Balance: " + moneyFormat.format(savingBalance));
							acc.setBalance(savingBalance);
							DBConnection.updateDB(acc);
							amount = amount+ transferacc.getBalance();
							DBConnection.updateTransferDB(cust_no,amount);
							end = true;
						} else {
							System.out.println("\nBalance Cannot Be Negative.");
						}
				    }
						else
						{
							System.out.println("\nThe Customer Number you have entered is Invalid!");
						}
					
				}
				 else
				 {
					 System.out.println("\nCurrently Transfer feature is not enabled for Current Accounts");
				 }
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				input.next();
			}
		}
	}
	
	
}
	
	