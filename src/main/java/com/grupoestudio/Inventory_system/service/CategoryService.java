package com.grupoestudio.Inventory_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoestudio.Inventory_system.model.Category;
import com.grupoestudio.Inventory_system.model.InventoryMovement;
import com.grupoestudio.Inventory_system.model.Product;
import com.grupoestudio.Inventory_system.repository.CategoryRepository;
import com.grupoestudio.Inventory_system.repository.InventoryMovementRepository;
import com.grupoestudio.Inventory_system.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

    public List<Category> findall(){
        return categoryRepository.findAll();
    }

    public Category findById(Long id){
        return categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Category not found"));
    }

    public Category save(Category category){
        return categoryRepository.save(category);
    }

    public void deleteById(Long id){
        categoryRepository.deleteById(id);
    }

    // metodo booleano que me dice si cierta categoria tiene un producto con stock bajo (pasar como parametro)

    public boolean hasLowStock(Long idCategory, int minStock){
        Category category = categoryRepository.findById(idCategory).orElseThrow(() -> new RuntimeException("Category not found."));

        List<Product> products = category.getProducts(); 
        
        for (Product product : products){
            if (product.getStock() < minStock){
                return true;
            }
        }
        return false;
    }

    //Un metodo que actualice el precio de todos los productos de una categoria 
    //aplicando un porcentaje de aumento (por ejemplo 10%)

    public void setPriceByPercentage(Long id, double percentage){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        List<Product> productList = category.getProducts();

        for (Product product : productList){
            double add = (product.getPrice() * percentage)/100;
            double finalPrice =product.getPrice()+add; 
            product.setPrice(finalPrice);
            productRepository.save(product);
        }

    }


    //Crear un metodo que desactive todos los productos de una categoria cuyo stock sea menos a un valor dado 
    //Encontrar categoria por id
    public void deactivateProductByStock(Long categoryId, int minStock){
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("Category not found."));

        List<Product> productList = category.getProducts();

        for(Product product : productList){
            if(product.getStock() < minStock && product.isActive()){
                product.setActive(false);
                productRepository.save(product);
            }
        }
    }
    
    
    //Crear un metodo que reasigne todos los productos de una categoria origen a una categoria destino.

    public void reassignProductsToCategory(Long sourceCategory, Long targetCategory){

        Category sourceCatFound = categoryRepository.findById(sourceCategory).orElseThrow(()-> new RuntimeException("Source Category not found"));
        Category targetCatFound = categoryRepository.findById(targetCategory).orElseThrow(()-> new RuntimeException("Target Category not found"));

        List<Product> sourceProducts = sourceCatFound.getProducts();

        for(Product product : sourceProducts){
            product.setCategory(targetCatFound);
            productRepository.save(product);
        }

    }

    //Crear un metodo que ajuste el stock de todos los productos de una categoria para que no superen 
    //El limite maximo que pasamos como parametro al metodo

    public void adjustStockByLimit(Long categoryId, int maxStock){
        Category category = categoryRepository.findById(categoryId).get();

        List<Product> listaProducto = category.getProducts();

        for (Product product : listaProducto){
            if (product.getStock() > maxStock){
            int diferencia = product.getStock() - maxStock;
            int ajuste = product.getStock() - diferencia;
            product.setStock(maxStock);
            productRepository.save(product);

            InventoryMovement adjuInventoryMovement = new InventoryMovement();

            adjuInventoryMovement.createInvMov(product, ajuste, "SALIDA");

            inventoryMovementRepository.save(adjuInventoryMovement);
           

            }
        }
    }
}
