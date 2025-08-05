package com.salesSystem.application.services;

import com.salesSystem.domain.dtos.product.ProductListDto;
import com.salesSystem.domain.dtos.product.ProductRegisterDto;
import com.salesSystem.domain.dtos.product.UpdateProductBalanceDto;
import com.salesSystem.domain.models.Product;
import com.salesSystem.infra.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository repository;

    ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Product createProduct(ProductRegisterDto newProduct) {
        Product product = new Product(newProduct);

        repository.save(product);

        return product;
    }

    public Page<ProductListDto> findAll(Pageable pagination) {

        return repository.findAll(pagination).map(ProductListDto::new);
    }

    public Product findById(Long id) {
        return repository.getReferenceById(id);
    }

    @Transactional
    public Product updateProductBalance(Long id, UpdateProductBalanceDto updateProduct) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        if (updateProduct.balance() == null) {
            throw new IllegalArgumentException("O saldo a atualizar não pode ser nulo");
        }

        product.setBalance(product.getBalance() + updateProduct.balance());

        return product;
    }

    @Transactional
    public void updateProduct(Product product) {

        repository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {

        Product product = repository.getReferenceById(id);

        repository.delete(product);

    }
}
