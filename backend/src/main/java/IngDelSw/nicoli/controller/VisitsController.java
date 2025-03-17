package IngDelSw.nicoli.controller;

import IngDelSw.nicoli.dto.GetVisitResponse;
import IngDelSw.nicoli.dto.SaveVisitRequest;
import IngDelSw.nicoli.model.Visit;
import IngDelSw.nicoli.model.enums.VisitType;
import IngDelSw.nicoli.services.VisitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/visit")
public class VisitsController {

    private final VisitService visitService;

    public VisitsController(VisitService visitService){
        this.visitService = visitService;
    }

    @PostMapping("/store")
    public ResponseEntity<?> storeVisit(@RequestBody Visit visitRequest) {
        try {
            Visit visit = visitService.storeVisit(
                    visitRequest.getPatientId(),
                    visitRequest.getTreatmentId(),
                    visitRequest.getMedicId(),
                    visitRequest.getDate(),
                    visitRequest.getVisitType(),
                    visitRequest.getLocalizationId(),
                    visitRequest.getNotes()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("visit", visit);
            response.put("success", true);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVisitById(@PathVariable int id) {
        try {
            GetVisitResponse visit = visitService.getVisit(id);

            Map<String, Object> response = new HashMap<>();
            response.put("visit", visit);
            response.put("success", true);

            return ResponseEntity.ok(response);  //200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());  //404
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateVisit(@RequestBody Visit visitRequest) {
        try {
            Visit visit = visitService.updateVisit(
                    visitRequest.getId(),
                    visitRequest.getPatientId(),
                    visitRequest.getTreatmentId(),
                    visitRequest.getMedicId(),
                    visitRequest.getDate(),
                    visitRequest.getVisitType(),
                    visitRequest.getLocalizationId(),
                    visitRequest.getNotes()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("visit", visit);
            response.put("success", true);

            return ResponseEntity.ok(response);  //200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());  //404
        }
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteVisit(@PathVariable int id) {
        try {
            visitService.deleteVisit(id);

            return ResponseEntity.ok("patient deleted successfully");  //200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());  //404
        }
    }

    @GetMapping("visitTypes")
    public ResponseEntity<?> getVisitTypes() {
        try {
            List<VisitType> visitTypes = visitService.getVisitTypes();

            Map<String, Object> response = new HashMap<>();
            response.put("visitTypes", visitTypes);
            response.put("success", true);

            return ResponseEntity.ok(response);  //200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());  //404
        }
    }

    @GetMapping("getVisitIdsByPatientId/{patientId}")
    public ResponseEntity<?> getVisitIdsByPatientId(@PathVariable int patientId) {
        try {
            List<Integer> visitIds = visitService.getVisitIdsByPatientId(
                    patientId
            );
            
            Map<String, Object> response = new HashMap<>();
            response.put("visitIds", visitIds);
            response.put("success", true);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveVisit(@RequestBody SaveVisitRequest saveVisitRequest) {
        try {
            Visit visit = visitService.saveVisit(
                    saveVisitRequest.getTreatmentId(),
                    saveVisitRequest.getDate(),
                    saveVisitRequest.getVisitType(),
                    saveVisitRequest.getNotes(),
                    saveVisitRequest.getProductIds()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("visit", visit);
            response.put("success", true);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }




}
