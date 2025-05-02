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
        if (product != null){
            //Product productAsociado = invMov.getProduct();
            //Long idProductoAsociado = productAsociado.getId();
            //Product productoAsociadoFinal = productRepository.findById(idProductoAsociado).get();
            //Product product1 = productRepository.findById(idProducto).get();
            //Queremos saber el stock del producto que queremos mover 
            //Hacemos una variable local
            //¿Como llego al producto asociado a mi columna de movimientoInventario?
            //Hago mi variable product
            //invMov.getProduct() == product
            //product.getId() = 5
            //productRepository.findById()
            //invMov tenemos un producto asociado cual es su id? 
            //invMov.getProduct().getId()
            //productRepository.findById()
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
        throw new RuntimeException("No se encontro el id del producto");
    }

    //save ---> Cambiar el stock del producto dependiendo del tipo de movimiento ("ENTRADA" O "SALIDA"),
    // guardar mi producto (con su nuevo stock), guardar mi movimientoInventario.
}
