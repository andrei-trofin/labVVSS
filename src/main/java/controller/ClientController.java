package controller;

import repository.ClientRepository;
import model.Client;

public class ClientController {
	private ClientRepository clientRepo;
    
    public ClientController(final ClientRepository clientRepo){
        this.clientRepo = clientRepo;
    }
    
    public String addClient(int id, String name, String address){
        //validation
        String valid;
        if(!(valid = validateClient(id, name, address)).equals("OK")){
            return valid;
        }

        Client c = new Client(id, name, address);
        if (!clientRepo.addClient(c)) {
            return "Client already in database.";
        }
        else {
            return "OK";
        }
    }

    public int getClientsSize() {
        return this.clientRepo.getClients().size();
    }

    private String validateClient(final Integer id, final String name, final String address){
        if (!(id instanceof Integer) || id < 0) {
            return "Invalid client id";
        }
        if (!name.equals("") && !address.equals("") && !name.equals(" ")) {
            for (int i=0;i<name.length();i++) {
                if (!Character.isAlphabetic(name.charAt(i)) && !Character.isSpaceChar(name.charAt(i))) {
                    return "Invalid character: " + name.charAt(i);
                }
            }
            for (int i=0;i<address.length();i++) {
                if (!Character.isLetterOrDigit(address.charAt(i)) && !Character.isSpaceChar(address.charAt(i))) {
                    return "Invalid character: " + address.charAt(i);
                }
            }
            return "OK";
        }else{
            return "Name or address cannot be empty!";
        }
    }
}
