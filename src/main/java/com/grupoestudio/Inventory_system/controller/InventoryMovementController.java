package com.grupoestudio.Inventory_system.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupoestudio.Inventory_system.model.InventoryMovement;
import com.grupoestudio.Inventory_system.repository.InventoryMovementRepository;
import com.grupoestudio.Inventory_system.service.InventoryMovementService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/inventory-movement")
public class InventoryMovementController {

    @Autowired
    private InventoryMovementService invMovService;


    @GetMapping
    public ResponseEntity<List<InventoryMovement>> getAllInvMovement() {
        List<InventoryMovement> invMovList = invMovService.findAll(); 
        return invMovList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(invMovList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryMovement> getById(@RequestParam Long id) {
        try {
            InventoryMovement invMov = invMovService.findById(id);
            return ResponseEntity.ok(invMov);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInv(@RequestParam Long id){
       try {
            invMovService.deleteById(id);
            return ResponseEntity.noContent().build();
       } catch (Exception e) {
            return ResponseEntity.notFound().build();
       }
    }

    @PostMapping
    public ResponseEntity<InventoryMovement> saveInvMov(@RequestBody InventoryMovement invMov) {
        try {
            InventoryMovement invToSave = invMovService.save(invMov);
            return ResponseEntity.status(HttpStatus.CREATED).body(invToSave);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        
        
    }
    
    
    

}
