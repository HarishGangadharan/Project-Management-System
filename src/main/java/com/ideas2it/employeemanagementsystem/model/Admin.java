package com.ideas2it.employeemanagementsystem.model;

/**
 * Assign the user defined project input details such as name, description,
 * members involved in the project and start date of the project to the 
 * corresponding fields using setters.
 * <p>
 * All the details will be stored in the form of object in which a single
 * object consist of single project information.
 * </p>
 *
 * @author   Harish
 */

public class Admin {

    private Integer id;
    private String name;
    private String pin;
    private String mail;
    private String password;
    private long mobileNumber;

    // Getters and setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public  long getMobileNumber() {
        return mobileNumber;
    }

    public  String getName() {
        return name;
    }

    public  String getPin() {
        return pin;
    }

    public  String getMail() {
        return mail;
    }

 
    public  String getPassword() {
        return password;
    }

}
