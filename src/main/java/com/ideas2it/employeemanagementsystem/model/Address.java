package com.ideas2it.employeemanagementsystem.model;

import com.ideas2it.employeemanagementsystem.commons.constants.AddressConstants;
import com.ideas2it.employeemanagementsystem.commons.constants.Constants;
/**
 * <p>
 * All the address information will be stored as a single object and consist of
 * getters and setters for the address purpose.
 * </p>
 * @author   Harish
 */

public class Address {

    private  Integer id;
    private String firstAddressline;
    private String city;
    private String state;
    private String type;
    private Integer pincode;
    private Integer employeeId;
    private Integer clientId;

    // Getters and setters
    public void setFirstAddressline(String firstAddressline) {
        this.firstAddressline = firstAddressline;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public String getType() {
        return type;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public String getFirstAddressline() {
        return firstAddressline;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public Integer getPincode() {
        return pincode;
    }


    public Integer getId() {
        return id;
    }

    public int getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getClientId() {
        return this.clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }


    /**
     * <p>
     * Used to display  Address information.
     * </p>
     *
     * @return    displayMessage    Address information 
     */
    public String toString() {
        StringBuilder displayMessage = new StringBuilder();
        displayMessage.append(AddressConstants.ADDRESSLINE1_VALUE).
            append(getFirstAddressline()).
            append(AddressConstants.CITY_VALUE).
            append(getCity()).
            append(AddressConstants.STATE_VALUE).
            append(getState()).append(getType()).
            append(AddressConstants.PINCODE_VALUE).
            append(getPincode()).append(Constants.NEW_LINE);
        return displayMessage.toString();
    }
}
