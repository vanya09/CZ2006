package com.kevincherian.cz2006;

/**
 * Represents a user object, can be either administrator or surveyee
 *
 * @author Lee Shei Pin
 */
public class User {
    /**
     * ID of the user
     */
    private String id;

    /**
     * Username of the user (used to login)
     */
    private String username;

    /**
     * First name of the user
     */
    private String firstName;

    /**
     * Last name of the user
     */
    private String lastName;

    /**
     * Age group of the user
     */
    private String age;
    
    private String phone; 

    /**
     * E-mail of the user
     */
    private String email;

    /**
     * Company ID of the user
     * Null if user is surveyee
     * Otherwise indicates the ID of the company of the administrator
     */


    public User(String id, String username, String firstName, String lastName, String age, String phone, String email) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phone=phone;
        this.email = email;
       
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        
        this.phone = phone;
    }
    

//    public String getCompanyId() {
//        return companyId;
//    }
//
//    public void setCompanyId(String companyId) {
//        this.companyId = companyId;
//    }
//
//    public String getCompanyName() {
//        return companyName;
//    }
//
//    public void setCompanyName(String companyName) {
//        this.companyName = companyName;
//    }
}
