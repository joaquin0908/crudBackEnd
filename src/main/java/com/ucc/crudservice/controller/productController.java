package com.ucc.crudservice.controller;

import com.ucc.crudservice.model.Product;
import com.ucc.crudservice.service.productService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/products")
@RequiredArgsConstructor

public class productController {

    private final productService productService;

    //metodos get
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProducts(){
        return this.productService.getProducts();
    }
    //metodo post
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<String>> newProduct(@Valid @RequestBody  Product product, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errors =bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return  new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);         }
        this.productService.addProduct(product);
        return null;
    }
    //metodo delete
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long productId) {
        System.out.println(productId);
        this.productService.deleteProduct(productId);
    }
    //metodo update
    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
        this.productService.updateProduct(productId, updatedProduct);
    }
}
