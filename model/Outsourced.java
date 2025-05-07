package model;

/** This class extends the Part class. It also declares the outsourced constructor. */

public class Outsourced extends Part{
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max , String companyName){
        super(id , name , price , stock , min , max);
        this.companyName = companyName;
    }

    /** This method sets the machineID.
     @param companyName this is the company name for outsourced pats*/

    void setCompanyName(String companyName) {this.companyName = companyName;}

    /** This method returns an outsourced part's company name. */

    public String getCompanyName() {return companyName;}
}
