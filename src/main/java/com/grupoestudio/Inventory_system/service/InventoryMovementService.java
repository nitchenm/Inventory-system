package com.grupoestudio.Inventory_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoestudio.Inventory_system.model.InventoryMovement;
import com.grupoestudio.Inventory_system.model.Product;
import com.grupoestudio.Inventory_system.repository.InventoryMovementRepository;
import com.grupoestudio.Inventory_system.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InventoryMovementService {
    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<InventoryMovement> findAll(){
        return inventoryMovementRepository.findAll();
    }

    public InventoryMovement findById(Long id){
        return inventoryMovementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory Movement not found"));
    }

    public void deleteById(Long id){
        inventoryMovementRepository.deleteById(id);
    }


    public InventoryMovement save(InventoryMovement invMov){
        Product product = productRepository.findById(invMov.getProduct().getId()).get();
       
        int currentStock = product.getStock();
        if ( invMov.getType().equals("ENTRADA")){
            product.setStock(currentStock + invMov.getQuantity());
        } else if (invMov.getType().equals("SALIDA")){
            if (currentStock < invMov.getQuantity()){
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
            product.setStock(currentStock - invMov.getQuantity());
        }
        productRepository.save(product);

        return inventoryMovementRepository.save(invMov);
      
    }

    
    
    //Id del producto fuente, Id del producto destino, cantidad
    //Metodo de transferencia (id producto fuente, id producto destino, cantidad)
    public void transferStock (Long idSourceProduct, Long idTargetProduct, int quantity){
        Product sourceProduct = productRepository.findById(idSourceProduct).orElseThrow(()-> new RuntimeException("Source product not found"));
        Product targetProduct = productRepository.findById(idTargetProduct).orElseThrow(()-> new RuntimeException("Target product not found"));
       
        if ( sourceProduct.getStock() < quantity){
            throw new RuntimeException("Not enough stock in the source product.");
        }

        //Crear movimiento salida para el producto fuente
        InventoryMovement exitMovement = new InventoryMovement();
        exitMovement.createInvMov(targetProduct, quantity, "SALIDA");
        sourceProduct.setStock(sourceProduct.getStock() - quantity);
        
        //Crear movimiento entrada para el producto destino
        InventoryMovement entryMovement = new InventoryMovement();
        entryMovement.createInvMov(targetProduct, quantity, "ENTRADA");
        targetProduct.setStock(targetProduct.getStock() + quantity);

        //Guardar productos y movimientos 
        productRepository.save(sourceProduct);
        productRepository.save(targetProduct);
        inventoryMovementRepository.save(entryMovement);
        inventoryMovementRepository.save(exitMovement);
    }
}
