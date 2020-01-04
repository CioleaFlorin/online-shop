package org.fasttrackit.onlineshop.service;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshop.persistance.ProductRepository;
import org.fasttrackit.onlineshop.transfer.GetProductsRequest;
import org.fasttrackit.onlineshop.transfer.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    //IoC inversion of control
    private final ProductRepository productRepository;

    //Dependency injection
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(SaveProductRequest request) {
        LOGGER.info("Creating product{}", request);
        Product product = new Product();
        product.setDescription(request.getDescription());
        product.setName(request.getName());
        product.setImageUrl(request.getImageUrl());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        return productRepository.save(product);

    }

    public Product getProduct(long id) {
        LOGGER.info("Retriving product {}", id);
        //using Optional
        return productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product " + id + " does not exists."));

    }

    public Page<Product> getProduct(GetProductsRequest request, Pageable pageable) {
        LOGGER.info("Retriving products: {}", request );
        if (request != null && request.getPartialName() != null && request.getMinQuantity() != null) {
            return productRepository.findByNameContainingAndQuantityGreaterThanEqual(
                    request.getPartialName(), request.getMinQuantity(), pageable);
        } else if (request != null && request.getPartialName() != null){
            return productRepository.findByNameContaining(request.getPartialName(), pageable);
        }else {
            return productRepository.findAll(pageable);
        }
    }

    public Product updateProduct(long id, SaveProductRequest request) {
        LOGGER.info("Updating product {}:  {}", id,request);

        Product product = getProduct(id);
        BeanUtils.copyProperties(request,product);
        return productRepository.save(product);
    }

    public void deleteProduct(long id){
        LOGGER.info("Deleting product{}",id);
        productRepository.deleteById(id);
    }


}
