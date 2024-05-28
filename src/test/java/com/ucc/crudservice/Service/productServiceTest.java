package com.ucc.crudservice.Service;

import com.ucc.crudservice.model.Product;
import com.ucc.crudservice.repositories.productRepository;
import com.ucc.crudservice.service.productService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
public class  productServiceTest {

    @MockBean
    private productRepository productRepository;

    @Autowired
    private productService ProductService;

    @BeforeEach
    void setUp (){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createProducts(){
        Product product = new Product(1L, "SKU001", "Product1", "Description1", 100.0, true);
        when(productRepository.save(product)).thenReturn(product);
        ResponseEntity<Object> response = ProductService.addProduct(product);

        // Verificar el Comportamiento
        verify(productRepository, times(1)).save(product);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());


        // Verificar el contenido de la respuesta
        HashMap<String, Object> responseBody = (HashMap<String, Object>) response.getBody();
        assertEquals("Successfully saved", responseBody.get("message"));
        assertEquals(product, responseBody.get("data"));

    }

    @Test
    public void getProducts(){
        Product product = new Product(1L, "SKU001", "Product1", "Description1", 100.0, true);
        List<Product> products = Collections.singletonList(product);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = ProductService.getProducts();
        assertEquals(1, result.size());
        assertEquals("SKU001", result.get(0).getSku());

    }

    @Test
    public void testUpdateProduct() {
        Long productId = 1L;
        Product product = new Product(productId, "SKU001", "Product1", "Description1", 100.0, true);
        Product updatedProduct = new Product(productId, "SKU002", "Product2", "Description2", 200.0, false);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Ejecutar el Método a Probar
        ResponseEntity<Object> response = ProductService.updateProduct(productId, updatedProduct);

        // Verificar el Comportamiento
        verify(productRepository, times(1)).save(product);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product updated successfully", response.getBody());

        // Verificar que los cambios se hayan aplicado
        assertEquals("SKU002", product.getSku());
        assertEquals("Product2", product.getName());
        assertEquals("Description2", product.getDescription());
        assertEquals(200.0, product.getPrice());
        assertEquals(false, product.getStatus());
    }


    @Test
    public void testDeleteProduct() {
        Long productId = 1L;

        // Ejecutar el Método a Probar
        ResponseEntity<Object> response = ProductService.deleteProduct(productId);

        // Verificar el Comportamiento
        verify(productRepository, times(1)).deleteById(productId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product delete successfully", response.getBody());
    }

}