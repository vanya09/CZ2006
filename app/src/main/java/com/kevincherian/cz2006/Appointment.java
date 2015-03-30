package com.kevincherian.cz2006;

import java.util.Date;

/**
 * Represents an appointment 
 *
 * @author Kevin Cherian
 */
public class Appointment {
    /**
     * ID of the Appointment
     */
    private String id;


    /**
     * Description of the Appointment
     */
    private String description;

    /**
     * Doctor name of the Appointment
     */
    private String doctorName;

    /**
     * Time of the Appointment
     */
    private Date date;

  

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date)
    {
        this.date= date;
    }

}

    
