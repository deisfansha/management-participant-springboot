package com.example.demo.models;

public class Participant {
    private String name;
    private String address;
    private String phoneNumber;
    private String status;

    // Konstruktor untuk menginisialisasi objek Participant
    public Participant(String name, String address, String phoneNumber, String status) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    // Metode getter untuk nama
    public String getName() {
        return name;
    }
    // Metode Setter untuk nama
    public void setName(String name) {
        this.name = name;
    }
    // Metode getter untuk alamat
    public String getAddress() {
        return address;
    }

    // Metode Setter untuk alamat
    public void setAddress(String address) {
        this.address = address;
    }

    // Metode getter untuk Nomor Telepon
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Metode Setter untuk Nomor Telepon
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Metode Setter untuk status
    public void setStatus(String status) {
        this.status = status;
    }

    // Metode getter untuk Status
    public String getStatus() {
        return status;
    }
}
