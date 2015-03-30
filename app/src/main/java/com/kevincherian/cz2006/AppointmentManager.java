//package com.kevincherian.cz2006;
//
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.parse.ParseException;
//import com.parse.ParseFile;
//import com.parse.ParseObject;
//import com.parse.ParsePush;
//import com.parse.ParseQuery;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Date;
//
///**
// * Implementation of Appointment manager using Parse
// * Allows for creating, fetching and updating appointments
// *
// * @author Kevin Cherian
// */
//
//
//public class AppointmentManager implements AppointmentManagerInterface {
//
//    public void createAppointment(String userId, String title, String description, String doctorName, String date, final ResultCallback<Boolean> callback) {
//        CreateAppointmentTask task = new CreateAppointmentTask() {
//            @Override
//            protected void onPostExecute(Boolean result) {
//                super.onPostExecute(result);
//                if (callback != null) {
//                    callback.onResult(result);
//                }
//            }
//        };
//        String date1= date.toString();
//
//        task.execute(new String[]{
//                userId, title, description, doctorName,date1
//        });
//    }
//
//    public void getAppointments(int offset, int limit, String userId, final ResultCallback<List> callback) {
//        FetchAppointmentsTask task = new FetchAppointmentsTask(userId) {
//            @Override
//            protected void onPostExecute(List result) {
//                super.onPostExecute(result);
//                if (callback != null)
//                    callback.onResult(result);
//            }
//        };
//
//        task.execute(new Integer[]{
//                offset, limit
//        });
//    }
//
//    public void getAppointmentById(String appointmentId, final ResultCallback<Appointment> callback) {
//        FetchAppointmentByIdTask task = new FetchAppointmentByIdTask(appointmentId){
//            @Override
//            protected void onPostExecute(Appointment result) {
//                super.onPostExecute(result);
//                if (callback != null)
//                    callback.onResult(result);
//            }
//        };
//
//        task.execute();
//    }
//
//    public void updateAppointment(String id, String title, String description, String doctorName, Date date, final ResultCallback<Appointment> callback) {
//        UpdateAppointmentTask task = new UpdateAppointmentTask() {
//            @Override
//            protected void onPostExecute(Appointment result) {
//                super.onPostExecute(result);
//                if (callback != null)
//                    callback.onResult(result);
//            }
//        };
//
//        String date1= date.toString();
//        task.execute(new String[]{
//                id, title, description, doctorName,date1
//        });
//
//    }
//
//    /**
//     * AsyncTask for fetching products
//     */
//    private class FetchAppointmentsTask extends AsyncTask<Integer, Void, List> {
//        String userId;
//
//        public FetchAppointmentsTask(String userId) {
//            this.userId = userId;
//        }
//
//        @Override
//        protected List doInBackground(Integer... integers) {
//            int offset = integers[0];
//            int limit = integers[1];
//
//            try {
//                ParseQuery<ParseObject> appointmentQuery = ParseQuery.getQuery(DbConstants.TABLE_APPOINTMENT);
//
//                // Get the most recent ones
//                appointmentQuery.orderByDescending(DbConstants.CREATED_AT);
//
//                appointmentQuery.include(DbConstants.USER_ID);
//
//                if (userId != null)
//                    appointmentQuery.whereEqualTo(DbConstants.USER_ID, ParseObject.createWithoutData(DbConstants.TABLE_USER, userId));
//
//               appointmentQuery.setSkip(offset);
//
//               appointmentQuery.setLimit(limit);
//
//                List<Appointment> appointmentList = new ArrayList<Appointment>();
//                List<ParseObject> result = appointmentQuery.find();
//
//                for (ParseObject appointmentObject : result) {
//                    Appointment appointment = new Appointment();
//                    appointment.setId(appointmentObject.getObjectId());
//                    appointment.setTitle(appointmentObject.getString(DbConstants.APPOINTMENT_TITLE));
//                    appointment.setDescription(appointmentObject.getString(DbConstants.APPOINTMENT_DESCRIPTION));
////                    appointment.setTime(appointmentObject.getString(DbConstants.APPOINTMENT_DATE));
//                    appointment.setDoctorName(appointmentObject.getParseObject(DbConstants.APPOINTMENT_DOCTOR_NAME).getString(DbConstants.USER_ID));
//                    appointment.setDate(appointmentObject.getParseObject(DbConstants.APPOINTMENT_DATE).getDate(DbConstants.USER_ID));
//
//
//                    Log.d(AppointmentManager.class.getSimpleName(), appointment.getTitle());
//
//                    appointmentList.add(appointment);
//                }
//
//                return appointmentList;
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//    }
//
//    /**
//     * AsyncTask for fetching appointment by ID
//     */
//    private class FetchAppointmentByIdTask extends AsyncTask<Void, Void, Appointment> {
//        final String appointmentId;
//
//        public FetchAppointmentByIdTask(String appointmentId) {
//            this.appointmentId = appointmentId;
//        }
//
//        @Override
//        protected Appointment doInBackground(Void... voids) {
//            try {
//                ParseQuery<ParseObject> appointmentQuery = ParseQuery.getQuery(DbConstants.TABLE_APPOINTMENT);
//
//                appointmentQuery.include(DbConstants.USER_ID);
//                ParseObject appointmentObject = appointmentQuery.get(appointmentId);
//                Appointment appointment = new Appointment();
//                appointment.setId(appointmentObject.getObjectId());
//                appointment.setTitle(appointmentObject.getString(DbConstants.APPOINTMENT_TITLE));
//                appointment.setDescription(appointmentObject.getString(DbConstants.APPOINTMENT_DESCRIPTION));
//                appointment.setDate(appointmentObject.getParseObject(DbConstants.APPOINTMENT_DATE).getDate(DbConstants.USER_ID));
//                appointment.setDoctorName(appointmentObject.getParseObject(DbConstants.APPOINTMENT_DOCTOR_NAME).getString(DbConstants.USER_ID));
//
//
//                return appointment;
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//    }
//
//    /**
//     * AsyncTask for updating products
//     */
//    private class UpdateAppointmentTask extends AsyncTask<String, Void, Appointment> {
//        @Override
//        protected Appointment doInBackground(String... params) {
//            try {
//
//                String id = params[0];
//                String title = params[1];
//                String description = params[2];
//                String doctorName = params[3];
//                String date = params[4];
//
//
//
//                ParseQuery<ParseObject> query = ParseQuery.getQuery(DbConstants.TABLE_APPOINTMENT);
//                query.include(DbConstants.USER_ID);
//                ParseObject appointmentObject = query.get(id);
//                appointmentObject.put(DbConstants.APPOINTMENT_TITLE, title);
//                appointmentObject.put(DbConstants.APPOINTMENT_DESCRIPTION, description);
//                appointmentObject.put(DbConstants.APPOINTMENT_DOCTOR_NAME, doctorName);
//                appointmentObject.put(DbConstants.APPOINTMENT_DATE, date);
//                appointmentObject.put(DbConstants.APPOINTMENT_USER_ID,id);
//
//                appointmentObject.save();
//
//                Appointment appointment = new Appointment();
//                appointment.setId(appointmentObject.getObjectId());
//
//                appointment.setDescription(description);
////                appointment.setDate(date);
//                appointment.setDoctorName(appointmentObject.getParseObject(DbConstants.APPOINTMENT_DOCTOR_NAME).getString(DbConstants.USER_ID));
//
//                return appointment;
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }
//
//    /**
//     * AsyncTask for creating products
//     */
//    private class CreateAppointmentTask extends AsyncTask<String, Void, Boolean> {
//        @Override
//        protected Boolean doInBackground(String... params) {
//            String userId = params[0];
//            String title = params[1];
//            String description = params[2];
//            String doctorName= params[3];
//            String date = params[4];
//
//            try {
//                ParseObject appointmentObject = new ParseObject(DbConstants.TABLE_APPOINTMENT);
//                appointmentObject.put(DbConstants.USER_ID, ParseObject.createWithoutData(DbConstants.TABLE_USER, userId));
//                appointmentObject.put(DbConstants.APPOINTMENT_TITLE, title);
//                appointmentObject.put(DbConstants.APPOINTMENT_DESCRIPTION, description);
//                appointmentObject.put(DbConstants.APPOINTMENT_DOCTOR_NAME, doctorName);
//                appointmentObject.put(DbConstants.APPOINTMENT_DATE, date);
//                appointmentObject.save();
//
//                String appointmentId = appointmentObject.getObjectId();
//
//
//                appointmentObject.save();
//
//                ParsePush push = new ParsePush();
//                push.setMessage("A new appointment has been set for you");
//                push.sendInBackground();
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//                return false;
//            }
//
//            return true;
//        }
//    }
//
//}
