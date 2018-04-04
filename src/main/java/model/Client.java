package model;

import java.util.Objects;

public class Client {
	private String name;
    private String address;
    private int id;

    public Client(final int id, final String name, final String address){
        this.name = name;
        this.address = address;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString(){
        String r = String.format("%d,%s,%s", this.id, this.name, this.address);
        return r;
    }
    
    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof Client)) return false;
        Client c = (Client)object;        
        
        if(c.getId() == this.id) return true;
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.address);
        return hash;
    }
}
