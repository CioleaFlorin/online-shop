package org.fasttrackit.onlineshop.transfer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class SaveCustomerRequest {

    @NotBlank
    private String firstName;
    @NotNull
    private String lastName;





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

    @Override
    public String toString() {
        return "SaveCustomerRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
