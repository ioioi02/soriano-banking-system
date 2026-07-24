package org.banking.model;

public class Employee {

    private int id;
    private String employeeId;
    private String lastname;
    private String firstname;
    private String middlename;
    private String suffix;
    private byte isActive;

    public Employee(int id, String employeeId, String lastname, String firstname, String middlename, String suffix, byte isActive) {
        this.id = id;
        this.employeeId = employeeId;
        this.lastname = lastname;
        this.firstname = firstname;
        this.middlename = middlename;
        this.suffix = suffix;
        this.isActive = isActive;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getEmployeeId() { return employeeId; }

    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getLastname() { return lastname; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getFirstname() { return firstname; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getMiddlename() { return middlename; }

    public void setMiddlename(String middlename) { this.middlename = middlename; }

    public String getSuffix() { return suffix; }

    public void setSuffix(String suffix) { this.suffix = suffix; }

    public byte getIsActive() { return isActive; }

    public void setIsActive(byte isActive) { this.isActive = isActive; }
}