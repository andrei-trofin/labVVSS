package controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import repository.ClientRepository;
import repository.InvoiceRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceControllerTest {
    private InvoiceController sut;
    private ClientRepository clientRepo;
    private InvoiceRepository invoiceRepo;

    @Before
    public void setUp() {
        clientRepo = mock(ClientRepository.class);
        invoiceRepo = mock(InvoiceRepository.class);
        sut = new InvoiceController(invoiceRepo, clientRepo);
    }

    @Test
    public void addInvoice_validInvoice_returnOKString() {
        // Given
        prepareClientRepoWithClient();
        when(invoiceRepo.isInvoiceInDatabase(any())).thenReturn(false);
        when(invoiceRepo.addInvoice(any())).thenReturn(true);

        // When
        String result = sut.addInvoice(19, 1922, 7, 6.55f);

        // Then
        assertThat("Result does not match expected", result, is("OK"));
    }

    @Test
    public void addInvoice_invoiceWithSameIdAlreadyInRepo_returnErrorString() {
        // Given
        prepareClientRepoWithClient();
        when(invoiceRepo.isInvoiceInDatabase(any())).thenReturn(true);

        // When
        String result = sut.addInvoice(19, 1922, 7, 6.55f);

        // Then
        assertThat("Result does not match expected", result, is("Same invoice cannot be added twice"));
    }

    @Test
    public void addInvoice_noClientCorrespondsToInvoice_returnErrorString() {
        // Given
        prepareClientRepoWithoutClient();

        // When
        String result = sut.addInvoice(19, 1922, 7, 6.55f);

        // Then
        assertThat("Result does not match expected", result, is("No client with this id in database"));
    }

    @Test
    public void addInvoice_invalidMonthInvoice_returnErrorValidate() {
        // Given
        prepareClientRepoWithClient();

        // When
        String result = sut.addInvoice(19, -1922, 7, 6.55f);

        // Then
        assertThat("Result does not match expected", result, is("Data inserted is invalid"));

    }

    private void prepareClientRepoWithClient() {
        when(clientRepo.isClientInDatabase(anyInt())).thenReturn(true);
    }

    private void prepareClientRepoWithoutClient() {
        when(clientRepo.isClientInDatabase(anyInt())).thenReturn(false);
    }
}