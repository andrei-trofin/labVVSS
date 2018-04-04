package repository;

import model.Invoice;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InvoiceRepository {
    private final static String fileInvoice = "invoice.txt";
    private ArrayList<Invoice> invoices;

    public InvoiceRepository() {
        invoices = new ArrayList<>();

        loadInvoices();
    }

    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }

    private void loadInvoices() {
        File clientFile = new File(fileInvoice);
        Scanner sc;
        try {
            sc = new Scanner(clientFile);
            while(sc.hasNextLine()) {
                List<String> line = Arrays.asList(sc.nextLine().replaceAll("[\n\r]", "").split(","));
                int clientId = Integer.valueOf(line.get(0));
                int year = Integer.valueOf(line.get(1));
                int month = Integer.valueOf(line.get(2));
                float toPay = Float.valueOf(line.get(3));
                float paid = Float.valueOf(line.get(4));
                invoices.add(new Invoice(clientId, year, month, toPay, paid));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public boolean addInvoice(final Invoice invoice) {
        if (!isInvoiceInDatabase(invoice)) {
            invoices.add(invoice);
            persistData();
            return true;
        }
        return false;
    }

    private void persistData() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileInvoice));
            for (int i = 0; i < invoices.size() - 1; i++) {
                writer.write(invoices.get(i).toString());
                writer.newLine();
            }
            writer.write(invoices.get(invoices.size()-1).toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isInvoiceInDatabase(final Invoice invoice) {
        for (Invoice i: invoices) {
            if (i.equals(invoice)) {
                return true;
            }
        }
        return false;
    }
}
