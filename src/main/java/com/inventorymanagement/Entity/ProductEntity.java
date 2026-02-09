package com.inventorymanagement.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.data.repository.RepositoryDefinition;

@Entity
@Table(name = "product09feb")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @NotBlank
    private String productName;

    private String productCategory;

    @Positive
    private double productPrice;

    @PositiveOrZero
    private int productStock;

}
