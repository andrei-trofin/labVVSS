

import controller.InvoiceController;
import repository.ClientRepository;
import controller.ClientController;
import repository.InvoiceRepository;
import ui.ElectricaUI;

public class App {
	public static void main(String[] args) {
		ClientRepository clientRepo = new ClientRepository();
		InvoiceRepository invoiceRepo = new InvoiceRepository();
		
		ClientController clientCtrl = new ClientController(clientRepo);
		InvoiceController invoiceCtrl = new InvoiceController(invoiceRepo, clientRepo);
		
		ElectricaUI console = new ElectricaUI(clientCtrl, invoiceCtrl);
		console.Run();
	}
}
