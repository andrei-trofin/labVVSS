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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TopDownIntegrationTesting {
    private InvoiceRepository invoiceRepo;
    private ClientRepository clientRepo;
    private InvoiceController invoiceController;
    private ClientController clientController;

    @Before()
    public void setUp() {
        invoiceRepo = mock(InvoiceRepository.class);
        clientRepo = mock(ClientRepository.class);
        when(clientRepo.addClient(any())).thenReturn(true);
    }

    //a)
    @Test
    public void addClient_sut_clientIsAddedIntoRepo() {
        // Given
        prepareEnvironmentUnitTest();
        Client client = new Client(7, "Ali", "Somewhere");

        // When
        String result = clientController.addClient(client.getId(), client.getName(), client.getAddress());

        // Then
        assertThat("result as expected", result, equalTo("OK"));
    }

    @Test
    public void addInvoiceToExistingClient_sut_invoiceIsAddedToClient() {
        // Given
        Client client = new Client(0, RandomString.make(5), RandomString.make(5));
        prepareEnvironmentFirstIntegrationTest(client);
        Invoice invoice = new Invoice(0, 1976, 8, 676.7f, 56.4f);

        // When
        String result = invoiceController.addInvoice(client.getId(), invoice.getYear(), invoice.getMonth(), invoice.getToPay());

        // Then
        assertThat("result as expected", result, equalTo("OK"));
    }

    @Test
    public void listAllInvoicesForClient_sut_listTheCorrectInvoices() {
        // Given
        Client client = new Client(0, RandomString.make(5), RandomString.make(5));
        List<Invoice> invoices = prepareRepoWithClientWithInvoices(client.getId());

        // When
        List<Invoice> storedInvoices = invoiceController.getInvoices();

        // Then
        assertThat("result as expected", invoices, equalTo(storedInvoices));
    }

    private void prepareEnvironmentUnitTest() {
        this.clientController = new ClientController(clientRepo);
    }

    private void prepareEnvironmentFirstIntegrationTest(Client client) {
        when(clientRepo.isClientInDatabase(anyInt())).thenReturn(true);
        when(clientRepo.getClients()).thenReturn(Collections.singletonList(client));
        when(invoiceRepo.addInvoice(any())).thenReturn(true);
        this.clientController = new ClientController(clientRepo);
        this.invoiceController = new InvoiceController(invoiceRepo, clientRepo);
    }

    private List<Invoice> prepareRepoWithClientWithInvoices(final Integer clientId) {
        when(clientRepo.isClientInDatabase(clientId)).thenReturn(true);
        when(clientRepo.getClients())
                .thenReturn(Collections
                        .singletonList(new Client(clientId,
                                RandomString.make(5),
                                RandomString.make(5))));
        List<Invoice> invoices = generateInvoices(3, clientId);
        when(invoiceRepo.getInvoices()).thenReturn(invoices);
        this.clientController = new ClientController(clientRepo);
        this.invoiceController = new InvoiceController(invoiceRepo, clientRepo);
        return invoices;
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
