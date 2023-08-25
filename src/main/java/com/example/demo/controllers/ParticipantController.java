package com.example.demo.controllers;

import com.example.demo.models.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.services.ParticipantService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/participants")
public class ParticipantController {
    @Autowired
    private ParticipantService participantService;

    // Api tambah peserta
    @PostMapping("")
    public ResponseEntity saveParticipant(@RequestBody Participant participant) {
        boolean added = participantService.addParticipant(participant);

        // Kondisi response
        if (added) {
            return ResponseEntity.ok("Participant saved successfully");
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", true);
            response.put("message", participantService.getMessage()); // Menampung pesan eror
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    // Api menampilkan semua peserta
    @GetMapping("")
    public ResponseEntity viewAllParticipants() {
        List<Participant> participants = participantService.viewAll();
        if (participants.isEmpty()){
            Map<String, Object> response = new HashMap<>();
            response.put("error", true);
            response.put("message", "Data is Empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Menampung pesan eror
        }
        return ResponseEntity.ok(participants);
    }

    // Api update peserta
    @PutMapping("/{name}")
    public ResponseEntity update(@RequestBody Participant updatedParticipant, @PathVariable("name") String name) {
        boolean updated = participantService.updateParticipantByName(name, updatedParticipant);
        if (updated) {
            return ResponseEntity.ok("Participant update successfully");
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", true);
            response.put("message", participantService.getMessage()); // Menampung pesan eror
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Api soft delete peserta
    @DeleteMapping("/{name}")
    public ResponseEntity softDelete(@PathVariable("name") String name) {
        boolean deleted = participantService.softDeleteParticipantByName(name);
        if (deleted){
            return ResponseEntity.ok("Participant soft deleted successfully");
        }else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", true);
            response.put("message", participantService.getMessage()); // Menampung Pesan Eror
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}

