package com.inventorymanagement.Entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order09feb")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private String productName;
    private int productId;
    private double productPrice;
    private int productQuantity;
    private double productRevenue;
    private String buyerName;



}
