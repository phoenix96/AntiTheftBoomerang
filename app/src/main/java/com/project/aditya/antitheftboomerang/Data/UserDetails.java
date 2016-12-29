package com.project.aditya.antitheftboomerang.Data;

/**
 * Created by Aditya on 27-09-2016.
 */
public class UserDetails {
    private String name;
    private String Contact;
    private String EContact;
    private String email;

    public UserDetails(){

    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return Contact;
    }

    public String getName() {
        return name;
    }

    public String getEContact() {
        return EContact;
    }

    public void setContact(String contact) {
        this.Contact = contact;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEContact(String EContact) {
        this.EContact = EContact;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
