package repository;

import model.Client;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClientRepository {
    private final static String fileClient = "client.txt";
    private List<Client> clients;

    public ClientRepository() {
        clients = new ArrayList<>();

        loadClients();
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    private void loadClients() {
        File clientFile = new File(fileClient);
        try {
            Scanner sc = new Scanner(clientFile);

            while(sc.hasNextLine()) {
                List<String> line = Arrays.asList(sc.nextLine().replaceAll("[\n\r]", "").split(","));
                int id = Integer.valueOf(line.get(0));
                String name = line.get(1);
                String address = line.get(2);
                clients.add(new Client(id, name, address));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean addClient(final Client client) {
        if (!isClientInDatabase(client.getId())) {
            clients.add(client);
            persistData();
            return true;
        } else {
            return false;
        }
    }

    private void persistData() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileClient));
            for (int i = 0; i < clients.size() - 1; i++) {
                writer.write(clients.get(i).toString());
                writer.newLine();
            }
            writer.write(clients.get(clients.size()-1).toString());

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isClientInDatabase(final int id) {
        for (Client c: clients) {
            if (id == c.getId()) {
                return true;
            }
        }
        return false;
    }
}
