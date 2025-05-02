package com.grupoestudio.Inventory_system.model;

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
@Table(name ="product")
@Data
@NoArgsConstructor

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private double price;

    @Column(nullable=false)
    private int stock;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int i) {
        this.stock = i;
    }

    public String getName() {
        return name;
    }
}
