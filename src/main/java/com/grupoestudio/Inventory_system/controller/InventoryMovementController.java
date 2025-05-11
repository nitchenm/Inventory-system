package com.grupoestudio.Inventory_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupoestudio.Inventory_system.model.InventoryMovement;
import com.grupoestudio.Inventory_system.service.InventoryMovementService;



@RestController
@RequestMapping("/api/v1/inventory-movements")
public class InventoryMovementController {

    @Autowired
    private InventoryMovementService invMovService;


    @GetMapping
    public ResponseEntity<List<InventoryMovement>> getAllInvMovement() {
        List<InventoryMovement> invMovList = invMovService.findAll(); 
        return invMovList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(invMovList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryMovement> getById(@PathVariable Long id) {
        try {
            InventoryMovement invMov = invMovService.findById(id);
            return ResponseEntity.ok(invMov);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInv(@PathVariable Long id){
       try {
            invMovService.deleteById(id);
            return ResponseEntity.noContent().build();
       } catch (Exception e) {
            return ResponseEntity.notFound().build();
       }
    }

    @PostMapping
    public ResponseEntity<InventoryMovement> saveInvMov(@PathVariable InventoryMovement invMov) {
        try {
            InventoryMovement invToSave = invMovService.save(invMov);
            return ResponseEntity.status(HttpStatus.CREATED).body(invToSave);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{sourceid}/transfer-stock/{targetid}")
    public ResponseEntity<Void> transferStock(@PathVariable Long sourceid, @PathVariable Long targetid, int quantity) {
        try {
            invMovService.transferStock(sourceid, targetid, quantity);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    
}
