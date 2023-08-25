package com.example.demo.services;

import com.example.demo.models.Participant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParticipantService {

    private List<Participant> participantsDB;
    private StringBuilder message;

    // Konstruktor daftar peserta saat objek ParticipantService dibuat
    public ParticipantService(){
        participantsDB = new ArrayList<>();
        message = new StringBuilder();
    }

    // Metode untuk menambahkan data
    public boolean addParticipant(Participant participant) {
        message.setLength(0);
        String name = participant.getName().trim();
        String address = participant.getAddress().trim();
        String phoneNumber = participant.getPhoneNumber().trim();

        Participant existingParticipant = getParticipantByName(name);
        Participant existingPhoneNumber = getParticipantByPhoneNumber(phoneNumber);

        // Kondisi jika nomor telepon sudah ada dalam List
        if (existingPhoneNumber != null){
            appendErrorMessage("Phone Number is already exists");
        }
        // Kondisi jika nama sudah ada dalam List
        if (existingParticipant != null) {
            appendErrorMessage("Name is already exists");
        }

        // Meminta validasi data
        validateData(name, address, phoneNumber);

        // Kondisi jika terdapat pesan error
        if (message.length() > 0) {
            return false;
        }
        // Membuat objek baru dan menambahkan kedalam list peserta
        Participant dataParticipant = new Participant(name, address, phoneNumber, "Active");
        participantsDB.add(dataParticipant);
        return true;
    }

    // Metode mengubah data peserta berdasarkan nama
    public boolean updateParticipantByName(String name, Participant updatedParticipant) {
        message.setLength(0);
        // Validasi jika data peserta tidak ditemukan
        Participant existingParticipant = getParticipantByName(name);
        if (existingParticipant == null) {
            appendErrorMessage("Participant not found");
            return false;
        }

        String phoneNumber = updatedParticipant.getPhoneNumber().trim();
        String address = updatedParticipant.getAddress().trim();

        // Kondisi jika value data dan data yang dimasukan sama
        if (!name.equals(existingParticipant.getName()) || !address.equals(existingParticipant.getAddress()) || !phoneNumber.equals(existingParticipant.getPhoneNumber())) {

            validateData(name, address, phoneNumber);

            Participant existingPhoneNumber = getParticipantByPhoneNumber(phoneNumber);
            if (existingPhoneNumber != null && !existingPhoneNumber.getName().equals(name)) {
                appendErrorMessage("Phone Number is already exists");
            }

            if (message.length() > 0) {
                return false;
            }

            existingParticipant.setName(updatedParticipant.getName().trim());
            existingParticipant.setAddress(address);
            existingParticipant.setPhoneNumber(phoneNumber);
        } else {
            appendErrorMessage("No changes detected");
            return false;
        }

        return true;
    }

    // Metode manghapus secara halus data peserta berdasarkan nama
    public boolean softDeleteParticipantByName(String name) {
        message.setLength(0);
        Participant existingParticipant = getParticipantByName(name);
        if (existingParticipant != null) {
            existingParticipant.setStatus("Delete");
        } else {
            appendErrorMessage("Participant Not Found");
            return false;
        }
        return true;
    }

    // Metode untuk menampilkan semua data peserta
    public List<Participant> viewAll() {
        List<Participant> activeParticipantsWithoutStatus = new ArrayList<>();
        for (Participant participant : participantsDB) {
            if (participant.getStatus().equalsIgnoreCase("Active")) {
                activeParticipantsWithoutStatus.add(participant);
            }
        }

        return activeParticipantsWithoutStatus;
    }

    // Metode untuk mendapatkan data partisipasi berdasarkan nama
    public Participant getParticipantByName(String name) {
        for (Participant participants : participantsDB) {
            if (participants.getName().equalsIgnoreCase(name) && participants.getStatus().equalsIgnoreCase("Active")) {
                return participants;
            }
        }
        return null;
    }

    // Metode untuk mendapatkan data partisipasi berdasarkan nomor telepon
    public Participant getParticipantByPhoneNumber(String phoneNumber) {
        for (Participant participants : participantsDB) {
            if (participants.getPhoneNumber().equalsIgnoreCase(phoneNumber) && participants.getStatus().equalsIgnoreCase("Active")) {
                return participants;
            }
        }
        return null;
    }

    // Metode validasi data nomor telepon
    public boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{8,13}");
    }

    // Metode untuk menampung semua pesan eror
    private void appendErrorMessage(String errorMessage) {
        if (message.length() > 0) {
            message.append(", ");
        }
        message.append(errorMessage);
    }

    // Metode getter untuk mendapatkan pesan error
    public String getMessage() {
        return message.toString();
    }

    // Metode validasi data
    public void validateData(String name, String address, String phoneNumber ){
        // Validasi nama harus berupa alfabet
        if (!name.matches("^[a-zA-Z -]*$")){
            appendErrorMessage("Can only input the alphabet");
        }
        // Validasi nomor telepon harus berupa angka minimal 8 digit dan maksimal 13 digit
        if (!isValidPhoneNumber(phoneNumber)) {
            appendErrorMessage("the telephone number must be a number and cannot be more than 13 digits or less than 8 digits");
        }
        // Validasi semua inputan tidak boleh kosong
        if (name.isEmpty() || address.isEmpty() || phoneNumber.isEmpty()) {
            appendErrorMessage("All data must be filled in");
        }

    }
}


