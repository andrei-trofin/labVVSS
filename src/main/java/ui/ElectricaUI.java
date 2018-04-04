package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.InvoiceController;
import model.*;
import controller.ClientController;;

public class ElectricaUI {
	public ClientController clientCtrl;
	public InvoiceController invoiceCtrl;
	Scanner in;
	
	public ElectricaUI(ClientController clientCtrl, InvoiceController invoiceCtrl) {
		this.clientCtrl = clientCtrl;
		this.invoiceCtrl = invoiceCtrl;
		this.in=new Scanner(System.in);
	}
	
	public void printMenu() {
		String menu;
		menu="Electrica Admin Menu: \n";
		menu +="\t 1 - to add a new client; \n";
		menu +="\t 2 - to add a new invoice; \n";
		menu +="\t 3 - to list the current invoices (and possible penalties or pending payment) of the subscribers; \n";
		menu +="\t 0 - exit \n";
		
		System.out.println(menu);
	}
	
	
	public void Run() {
		printMenu();
	
		
		int cmd = in.nextInt();
		in.nextLine();
		
		while (cmd !=0 ) {
			if (cmd == 1) {
				System.out.println("Enter name:");
				String name = in.nextLine();
				System.out.println("Enter address:");
				String address = in.nextLine();
				System.out.println("Enter id:");
				int id = Integer.valueOf(in.nextLine());

				String result = clientCtrl.addClient(id, name, address);
				if(!result.equals("OK")) {
					System.out.println("Error:\n" + result);
				} else {
					System.out.println("Success");
				}
				
			} else if (cmd == 2) {
				System.out.println("Enter client id:");
				int clientId = Integer.valueOf(in.nextLine());

				System.out.println("Enter the YEAR:");
				String yearS = in.nextLine();
				int year = Integer.parseInt(yearS);
				System.out.println("Enter the MONTH:");
				String monthS = in.nextLine();
				int month = Integer.parseInt(monthS);
				
				System.out.println("Enter toPay:");
				String sumToPayS = in.nextLine();
				float sumToPay = Float.parseFloat(sumToPayS);

				String result = invoiceCtrl.addInvoice(clientId, year, month, sumToPay);
				if (!result.equals("OK")) {
					System.out.println("Error:\n" + result);
				} else {
					System.out.println("Success");
				}
			} else if (cmd == 3) {
				System.out.println("Enter client id:");
				int clientId = Integer.valueOf(in.nextLine());

				String invoicesFromClient = invoiceCtrl.getInvoicesAsString(clientId);
				if (invoicesFromClient.equals("")) {
					System.out.println("No invoices were found");
				} else {
					System.out.println("List of invoices found:\n" + invoicesFromClient);
				}
			}

			printMenu();
			cmd = in.nextInt();
			in.nextLine();
		}
	}
}

