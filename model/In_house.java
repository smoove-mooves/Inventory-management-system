package model;

/** This class extends the Part class. It also declares the in-house constructor. */

public class In_house extends Part {
    private int machineId;

    public In_house(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id , name , price , stock , min , max);
        this.machineId = machineId;
    }

/** This method sets the machineID.
   @param machineId this is the machineID for in-house pats*/

    void setMachineId(int machineId) {this.machineId = machineId;}

    /** This method gets the machineID. */

    public int getMachineId() {return machineId;}
}
