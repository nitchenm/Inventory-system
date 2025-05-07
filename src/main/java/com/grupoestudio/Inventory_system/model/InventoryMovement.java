package com.grupoestudio.Inventory_system.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="inventory_movement")
@Data
@NoArgsConstructor
public class InventoryMovement {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name= "product_id", nullable=false)
    private Product product;

    @Column(nullable= false)
    private int quantity;

    @Column(nullable=false)
    private String type; // "ENTRADA", "SALIDA", "AJUSTE"

    @Column(nullable=false)
    private Date date;

    public String getType() {
        return type;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProduct(Product product){
        this.product = product;
    }

    public void createInvMov(Product product, int quantity, String type){
        this.product = product;
        this.quantity = quantity;
        this.type = type;
        this.date = new Date();
    }



}
