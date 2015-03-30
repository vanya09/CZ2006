package com.kevincherian.cz2006;

import java.util.List;
import java.util.Date;

/**
 * Interface for the Appointment Manager
 * Provides methods to create, obtain and update products
 *
 * @author Vanya Gupta
 */
public interface AppointmentManagerInterface {

    /**
     * Asynchronous method to create new appointment
     *
     * @param userId   ID of the user making the appointment
     * @param serviceType Type of Service that is provided to the patient.                 
     * @param description Description of the appointment
     * @param doctorName Doctor assigned to the appointment
     * @param date  Time of the appointment
     * @param callback    Callback to receive the result
     */
    public void createAppointment(String userId, String serviceType, String description, String doctorName, Date date, final ResultCallback<Boolean> callback);

    /**
     * Asynchronous method to retrieve a list of appointments
     * Returns a list of product
     *
     * @param offset    Offset of the first appointment to fetch
     * @param limit     Maximum number of Appointments to fetch
     * @param appointmentId  ID of the appointment
     * @param callback  Callback to receive the result
     */
    
    
    
    public void getAppointments(int offset, int limit, String appointmentId, final ResultCallback<List> callback);

    /**
     * Asynchronous method to retrieve an appointment by ID
     * Returns the product if found, null if otherwise
     *
     * @param appointmentId ID of the product
     * @param callback  Callback to receive the result
     */
    public void getAppointmentById(String appointmentId, final ResultCallback<Appointment> callback);

    /**
     * Asynchronous method to update a product
     * Returns the product if successful, null if otherwise
     *
     * @param id          ID of the appointment
     * @param serviceType Type of service that is provided to the patient.                   
     * @param description Description of the appointment
     * @param doctorName Doctor assigned to the appointment
     * @param date   Time of the appointment*
     * @param callback    Callback to receive the result
     */
    public void updateAppointment(String id, String serviceType, String description, String doctorName, Date date, final ResultCallback<Appointment> callback);
}
