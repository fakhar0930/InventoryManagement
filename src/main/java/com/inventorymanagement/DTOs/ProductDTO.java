package com.inventorymanagement.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ProductDTO(@NotBlank String productName,@Positive int productQuantity) {
}
