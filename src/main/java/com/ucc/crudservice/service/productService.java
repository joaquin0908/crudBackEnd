package com.ucc.crudservice.service;


import com.ucc.crudservice.model.Product;
import com.ucc.crudservice.repositories.productRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class productService {

    private final productRepository productRepository;

    //Metodo para obtener los product
    public List<Product> getProducts (){
        return productRepository.findAll();
    }

    //Metodo para crear un product

    public ResponseEntity<Object> addProduct (Product product){
        HashMap<String, Object> data = new HashMap<>();
        productRepository.save(product);
        data.put("data", product);
        data.put("message","Successfully saved");
        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }

    //Metodo para borrar product
    public ResponseEntity<Object> deleteProduct (Long id){
        productRepository.deleteById(id);
        return new ResponseEntity<>("Product delete successfully", HttpStatus.OK);
    }

    //Metodo para actualizar product
    public ResponseEntity<Object> updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProductOptional = productRepository.findById(id);

        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();

            existingProduct.setSku(updatedProduct.getSku());
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setStatus(updatedProduct.getStatus());


            productRepository.save(existingProduct);

            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }
}