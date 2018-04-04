
import controller.ClientController;
import model.Client;
import org.junit.Before;
import org.junit.Test;
import repository.ClientRepository;


public class Test2 {
    private ClientController sut;

    @Before
    public void setUp() {
        ClientRepository clientRepository = new ClientRepository();
        sut = new ClientController(clientRepository);
    }

    @Test
    public void createClientWithWrongId_sut_returnReasonAndDoNotAddClient() {
        // Given
        Client client = new Client(-4, "Gheorghe", "Aici");
        int size = sut.getClientsSize();

        // When & Then
        assert(!sut.addClient(client.getId(), client.getName(), client.getAddress()).equals("OK"));
        assert(size == sut.getClientsSize());
    }

    @Test
    public void createClientWithCorrectData_sut_returnNullAndAddClient() {
        // Given
        Client client = new Client(4, "Gheorghe", "Aici");
        int size = sut.getClientsSize();

        // When & Then
        assert(sut.addClient(client.getId(), client.getName(), client.getAddress()).equals("OK"));
        assert(size + 1 == sut.getClientsSize());
    }
}
