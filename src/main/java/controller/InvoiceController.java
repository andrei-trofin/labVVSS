package controller;

import model.Invoice;
import repository.ClientRepository;
import repository.InvoiceRepository;

import java.util.List;
import java.util.stream.Collectors;

public class InvoiceController {
    private InvoiceRepository invoiceRepo;
    private ClientRepository clientRepo;

    public InvoiceController(final InvoiceRepository invoiceRepo, final ClientRepository clientRepo) {
        this.invoiceRepo = invoiceRepo;
        this.clientRepo = clientRepo;
    }

    public void setInvoiceRepo(InvoiceRepository invoiceRepo) {
        this.invoiceRepo = invoiceRepo;
    }

    public void setClientRepo(ClientRepository clientRepo) {
        this.clientRepo = clientRepo;
    }

    public List<Invoice> getInvoices() {
        return invoiceRepo.getInvoices();
    }

    public String addInvoice(final int clientId, final int year, final int month, final float toPay){
        String valid;
        if (!(valid = validateInvoice(clientId, year, month, toPay)).equals("OK")) {
            return valid;
        } else {
            Invoice invoice = new Invoice(clientId, year, month, toPay, 0);
            if (!clientRepo.isClientInDatabase(clientId)) {
                return "No client with this id in database";
            }
            if (!invoiceRepo.addInvoice(invoice)) {
                return "Same invoice cannot be added twice";
            } else {
                return "OK";
            }
        }
    }

    public String getInvoicesAsString(final int clientId) {
        List<Invoice> invoiceFromClient =
                invoiceRepo.getInvoices()
                        .stream()
                        .filter(i -> i.getClientId() == clientId).collect(Collectors.toList());
        String invoicesAsString = "";
        for (Invoice i: invoiceFromClient) {
            invoicesAsString += i.toString() + "\n";
        }
        return invoicesAsString;
    }

    private String validateInvoice(final Integer clientId, final Integer year, final Integer month, final Float toPay){
        if (clientId < 0 || month < 0 || month > 12 || year < 0 || toPay < 0) {
            return "Data inserted is invalid";
        } else {
            return "OK";
        }
    }
}
