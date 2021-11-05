package com.example.bookinglessons.Model;

public class BookedLesson {
    private String idUser;
    private String idTeacher;
    private String slot;
    private String subject;
    private String day;
    private String status;

    public BookedLesson(String idUser, String idTeacher, String slot, String subject, String day, String status) {
        this.idUser = idUser;
        this.idTeacher = idTeacher;
        this.slot = slot;
        this.subject = subject;
        this.day = day;
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookedLesson{" +
                "idUser='" + idUser + '\'' +
                ", idTeacher='" + idTeacher + '\'' +
                ", slot='" + slot + '\'' +
                ", subject='" + subject + '\'' +
                ", day='" + day + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(String idTeacher) {
        this.idTeacher = idTeacher;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
