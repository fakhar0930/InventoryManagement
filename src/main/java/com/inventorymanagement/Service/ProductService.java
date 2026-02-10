package com.inventorymanagement.Service;

import com.inventorymanagement.DTOs.ProductDTO;
import com.inventorymanagement.Entity.Order;
import com.inventorymanagement.Entity.ProductEntity;
import com.inventorymanagement.Repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    private final OrderService orderService;

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return auth.getName(); // admin1 / admin2
    }

    public ProductEntity addProduct(ProductEntity product){
        return productRepo.save(product);
    }

    public List<ProductEntity> addAllProduct(List<ProductEntity>  product){
        return productRepo.saveAll(product);
    }

    public ProductEntity getProductById(int id){
        return productRepo.
                findById(id).
                orElseThrow(()->new RuntimeException("Product WIth Id: " + id + " Not Found:( "));
    }

    public ProductEntity getProductByName(String name){
        Optional<ProductEntity> product= productRepo.findByProductName(name.toUpperCase());
        if (product.isPresent()){
            return product.get();
        } else throw new RuntimeException();
    }

    public List<ProductEntity> getAllProduct(){
        return productRepo.findAll();
    }

    public ProductDTO updateStock(ProductDTO productDTO){
        Optional<ProductEntity> product= productRepo.findByProductName(productDTO.productName().toUpperCase());

        if (product.isPresent()){
            ProductEntity newProdcut = product.get();
        int updatedQuantity = newProdcut.getProductStock()+ productDTO.productQuantity();
        newProdcut.setProductStock(updatedQuantity);

        ProductEntity updatedProduct= productRepo.save(newProdcut);
        return new ProductDTO(updatedProduct.getProductName(),updatedProduct.getProductStock());
        }

        throw new RuntimeException();
    }

    public String deleteProduct(int id){
        productRepo.deleteById(id);
        return "Product With Id: " + id + " Deleted Successfully";
    }

    public String buyProduct(String name,int quantity){
        Optional<ProductEntity> product= productRepo.findByProductName(name.toUpperCase());

        if (product.isPresent()) {
            ProductEntity newProdcut = product.get();
            if (newProdcut.getProductStock()<quantity){
                throw new RuntimeException("Product is not Available with Selected Quantity. Only " + newProdcut.getProductStock() + " Piece Available as of now");
            } else {
                Order order = new Order();
                order.setProductId(newProdcut.getProductId());
                order.setProductName(newProdcut.getProductName());
                order.setProductPrice(newProdcut.getProductPrice());
                order.setProductQuantity(quantity);
                order.setBuyerName(getCurrentUsername());
                order.setProductRevenue(quantity * newProdcut.getProductPrice());


                newProdcut.setProductStock(newProdcut.getProductStock()-quantity);
                productRepo.save(newProdcut);
                orderService.orderCreation(order);
            }
        }
        return "You Bought Product: "+ name + " Successfully :) Keep Shopping With US.";
    }

    public List<ProductEntity> getProductByCategory(String category){

        //toUpperCase for Better Search Results
        String categoryUpperCase= category.toUpperCase();

        //Get All Products
        List<ProductEntity> allProducts= productRepo.findAll();

        //Filter Products by Provided Category
        List<ProductEntity> filteredProductByCategory = allProducts
                .stream()
                .filter(x->x.getProductCategory().toUpperCase().equals(categoryUpperCase))
                .collect(Collectors.toList());

        return filteredProductByCategory;
    }

    public Map<String,List<ProductEntity>> groupByCategory(){

        //Finding All Products and then below Grouping It
        List<ProductEntity> product = productRepo.findAll();

        //Grouping Products by Categories like Electronics,Accessories,Office,Power ETC
        Map<String,List<ProductEntity>> groupBy= product
                .stream()
                .collect(Collectors.groupingBy(ProductEntity::getProductCategory));

        return groupBy;
    }

    public List<String> getCategories(){
       List<ProductEntity> allProduct= productRepo.findAll();
       List<String> categoryList= allProduct
               .stream()
               .map(ProductEntity::getProductCategory)
               .distinct()
               .collect(Collectors.toList());
       return categoryList;
    }

}

