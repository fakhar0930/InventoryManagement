package com.inventorymanagement.Controller;

import com.inventorymanagement.DTOs.ProductDTO;
import com.inventorymanagement.Entity.ProductEntity;
import com.inventorymanagement.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //http:localhost:8080/products/add   Done
    @PostMapping("/add")
    public ProductEntity addProduct(@RequestBody @Validated ProductEntity product) {
        return productService.addProduct(product);
    }

    //http:localhost:8080/products/addall  Done
    @PostMapping("/addall")
    public List<ProductEntity> addProduct(@RequestBody @Validated List<ProductEntity> product) {
        return productService.addAllProduct(product);
    }

    //http://localhost:8080/products/id/5  Done
    @GetMapping("/id/{id}")
    public ProductEntity getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    //http://localhost:8080/products/name/Cooker    Done
    @GetMapping("/name/{name}")
    public ProductEntity getProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    //http://localhost:8080/products/all     DONE
    @GetMapping("/all")
    public List<ProductEntity> getAllProducts() {
        return productService.getAllProduct();
    }

    //http://localhost:8080/products/category/Electronics  DOne

    @GetMapping("/category/{category}")
    public List<ProductEntity> getProductByCategory(@PathVariable String category){
        return productService.getProductByCategory(category);
    }

    //http://localhost:8080/products/update-stock
    @PatchMapping("/update-stock")
    public ProductDTO updateStock(@RequestBody @Validated ProductDTO productDTO) {
        return productService.updateStock(productDTO);
    }

    //http://localhost:8080/products/delete/2   DONE
    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        return productService.deleteProduct(id);
    }

    //http://localhost:8080/products/buy?name=TabLET&&quantity=20  DONE
    @PatchMapping("/buy")
    public String buyProduct(@RequestParam String name, @RequestParam int quantity) {
        return productService.buyProduct(name, quantity);
    }
}