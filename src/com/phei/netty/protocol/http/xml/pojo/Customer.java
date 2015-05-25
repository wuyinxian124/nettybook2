package com.phei.netty.protocol.http.xml.pojo;

import java.util.List;

/**
 * Customer information.
 */
public class Customer {
    private long customerNumber;

    /** Personal name. */
    private String firstName;

    /** Family name. */
    private String lastName;

    /** Middle name(s), if any. */
    private List<String> middleNames;

    public long getCustomerNumber() {
	return customerNumber;
    }

    public void setCustomerNumber(long customerId) {
	this.customerNumber = customerId;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public List<String> getMiddleNames() {
	return middleNames;
    }

    public void setMiddleNames(List<String> middleNames) {
	this.middleNames = middleNames;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Customer [customerNumber=" + customerNumber + ", firstName="
		+ firstName + ", lastName=" + lastName + ", middleNames="
		+ middleNames + "]";
    }

}