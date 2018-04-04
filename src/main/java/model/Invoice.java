package model;

public class Invoice {
	private int clientId;
    private int year;
    private int month;
    private float toPay;
    private float paid;
    
    public Invoice(final int clientId, final  int year, final int month, final float toPay, final float paid){
        this.clientId = clientId;
        this.year = year;
        this.month = month;
        this.toPay = toPay;
        this.paid = paid;
    }

    public int getClientId() {
        return clientId;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public float getToPay() {
        return toPay;
    }

    public float getPaid() {
        return paid;
    }

    @Override
    public String toString(){
        String r = String.format("%d,%d,%d,%f,%f",
                this.clientId, this.year, this.month, this.toPay, this.paid);
        return r;
    }
    
    @Override 
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof Invoice)) return false;
        Invoice i = (Invoice)object;
        if(i.getClientId() == -1) return false;
        if((i.month == this.month) && (i.year == this.year) && (i.getClientId() == (this.getClientId()))) return true;
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.clientId;
        hash = 71 * hash + this.year;
        hash = 71 * hash + this.month;
        hash = 71 * hash + Float.floatToIntBits(this.toPay);
        hash = 71 * hash + Float.floatToIntBits(this.paid);
        return hash;
    }
}
