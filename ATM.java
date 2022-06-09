package ATM;


import java.io.IOException;
import java.util.List;

import DatabaseConnection.DBConnection;

public class ATM {

	public static void main(String[] args) throws IOException {
		
		List<AccountDetails> ad = DBConnection.ConnectWithDB();
		OptionMenu optionMenu = new OptionMenu();
		introduction();
		optionMenu.setAccountDetails(ad);
		optionMenu.mainMenu();
	}

	public static void introduction() {
		System.out.println("Welcome to the ATM Project!");
	}
}