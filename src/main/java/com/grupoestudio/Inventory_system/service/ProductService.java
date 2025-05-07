package com.grupoestudio.Inventory_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoestudio.Inventory_system.model.Product;
import com.grupoestudio.Inventory_system.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(Long id){
        return productRepository.findById(id).get();
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    //VALOR FINAL A LA CANTIDAD POR EL PRECIO DEL PRODUCTO 

    //obtener el producto, su precio y cantidad
    //precio * cantidad = valor total = retornar el valor total 

    public double calculateTotalValue(Long id){
        Product product = productRepository.findById(id).get();
        return product.getPrice() * product.getStock();

    }


}
