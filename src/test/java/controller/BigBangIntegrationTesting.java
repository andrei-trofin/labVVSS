package controller;

import model.Client;
import model.Invoice;
import net.bytebuddy.utility.RandomString;
import org.junit.Before;
import org.junit.Test;
import repository.ClientRepository;
import repository.InvoiceRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BigBangIntegrationTesting {
    private InvoiceRepository invoiceRepo;
    private ClientRepository clientRepo;
    private InvoiceController invoiceController;
    private ClientController clientController;

    @Before()
    public void setUp() {
        invoiceRepo = mock(InvoiceRepository.class);
        clientRepo = mock(ClientRepository.class);
    }

    // c)
    @Test
    public void listCurrentInvoices_clientNotInDatabase_returnEmptyString() {
        // Given & When
        prepareRepoWithClientWithInvoices(3);
        invoiceController = new InvoiceController(invoiceRepo, clientRepo);
        clientController = new ClientController(clientRepo);
        String result = invoiceController.getInvoicesAsString(2);

        // Then
        assertThat("result is not as expected", result.equals(""));
    }

    // the whole system
    @Test
    public void bigBangIntegration_allGood() {
        // Given
        int clientId = 7;
        Client client = new Client(clientId, "Ala", "Bala");
        Invoice invoice = new Invoice(7, 1876, 12, 43.5f, 0);
        prepareRepositories(clientId, invoice);
        invoiceController = new InvoiceController(invoiceRepo, clientRepo);
        clientController = new ClientController(clientRepo);

        // When
        String res1 = clientController.addClient(clientId, client.getName(), client.getAddress());
        String res2 = invoiceController.addInvoice(clientId, invoice.getYear(), invoice.getMonth(), invoice.getToPay());
        String res3 = invoiceController.getInvoicesAsString(clientId);

        // Then
        assertThat("client was not added properly", res1.equals("OK"));
        assertThat("invoice was not added properly", res2.equals("OK"));
        assertThat("invoice list not retrieved properly", res3.equals(invoice.toString() + "\n"));
    }

    private void prepareRepositories(final int clientId, final Invoice invoiceFromClient) {
        when(clientRepo.addClient(any())).thenReturn(true);
        when(clientRepo.isClientInDatabase(clientId)).thenReturn(true);
        when(clientRepo.getClients())
                .thenReturn(Collections
                        .singletonList(new Client(clientId,
                                RandomString.make(5),
                                RandomString.make(5))));
        when(invoiceRepo.getInvoices()).thenReturn(Collections
                .singletonList(invoiceFromClient));
        when(invoiceRepo.addInvoice(any())).thenReturn(true);

    }

    private void prepareRepoForNoClientDetection() {
        when(clientRepo.isClientInDatabase(anyInt())).thenReturn(false);
        invoiceController.setClientRepo(clientRepo);
    }

    private void prepareRepoWithClientWithInvoices(final Integer clientId) {
        when(clientRepo.isClientInDatabase(clientId)).thenReturn(true);
        when(clientRepo.getClients())
                .thenReturn(Collections
                        .singletonList(new Client(clientId,
                        RandomString.make(5),
                        RandomString.make(5))));
        List<Invoice> invoices = generateInvoices(3, clientId);
        when(invoiceRepo.getInvoices()).thenReturn(invoices);
    }

    private List<Invoice> generateInvoices(final int nrOfInvoices, final int clientId) {
        Random random = new Random();
        List<Invoice> invoiceList = new ArrayList<>();
        for (int i = 0; i < nrOfInvoices; i++) {
            int randomInt = random.nextInt();
            invoiceList.add(new Invoice(clientId, randomInt, randomInt, randomInt, randomInt));
        }
        return invoiceList;
    }
}
