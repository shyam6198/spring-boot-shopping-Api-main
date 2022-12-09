package com.miniproject.service;

import com.miniproject.model.Product;
import com.miniproject.model.ProductInventory;
import com.miniproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product productSave(Product product){
        return productRepository.save(product);
    }

    public List<Product> allProductList(){
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(Long id){
       return productRepository.findById(id);
    }

    public String deleteProductById(Long id){
        Optional<Product> product=productRepository.findById(id);
        if (product!= null){
            productRepository.deleteById(id);
            return "valid";
        }
        else {
            return null;
        }
    }

    public Product stockUpdate(Long id,ProductInventory productInventory){
        Product product=productRepository.findById(id).get();
        ProductInventory productInventory1=new ProductInventory();
        productInventory1.setAvailableQuantity(productInventory.getAvailableQuantity());
        productInventory1.setTotalQuantity(productInventory.getTotalQuantity());
        product.setProductInventory(productInventory1);
        return productRepository.save(product);
    }
}
