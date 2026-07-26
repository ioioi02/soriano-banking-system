package org.banking.model;

public class Employee {

    private Integer id;
    private String employeeId;
    private String lastname;
    private String firstname;
    private String middlename;
    private String suffix;
    private Boolean isActive;

    public Employee(Integer id, String employeeId, String lastname, String firstname, String middlename, String suffix, Boolean isActive) {
        this.id = id;
        this.employeeId = employeeId;
        this.lastname = lastname;
        this.firstname = firstname;
        this.middlename = middlename;
        this.suffix = suffix;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getfullName() {
        StringBuilder fullname = new StringBuilder();

        if (firstname != null && !firstname.isBlank()) {
            fullname.append(firstname).append(" ");
        }

        if (middlename != null && !middlename.isBlank()) {
            fullname.append(middlename.charAt(0)).append(". ");
        }

        if (lastname != null && !lastname.isBlank()) {
            fullname.append(lastname).append(" ");
        }

        if (suffix != null && !suffix.isBlank()) {
            fullname.append(suffix);
        }

        return fullname.toString().trim();
    }
}