package com.salesSystem.application.services;

import com.salesSystem.domain.dtos.cart.CartRegisterDto;
import com.salesSystem.domain.dtos.product.UpdateProductBalanceDto;
import com.salesSystem.domain.dtos.sale.ListSalesDto;
import com.salesSystem.domain.dtos.sale.SaleRegisterDto;
import com.salesSystem.domain.models.*;
import com.salesSystem.infra.repositories.SalesRepository;
import com.salesSystem.infra.validations.Validation;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {

    private final SalesRepository repository;

    private final UserService userService;

    private final ClientService clientService;

    private final ProductService productService;

    private final List<Validation> validations;

    public SaleService(SalesRepository repository, UserService userService, ClientService clientService, ProductService productService, List<Validation> validation) {
        this.repository = repository;
        this.clientService = clientService;
        this.userService = userService;
        this.productService = productService;
        this.validations = validation;
    }

    @Transactional
    public Sale createSale(SaleRegisterDto newSale) {

        validations.forEach(v -> v.validate(newSale));

        Client client = clientService.findClientById(newSale.buyerId());

        Seller seller = userService.findSellerById(newSale.sellerId());

        List<CartItem> cart = new ArrayList<>();

        for (CartRegisterDto item : newSale.cart()) {

            Product product = productService.findById(item.productId());

            CartItem cartItem = new CartItem(product, item.quantity());

            product.setBalance(product.getBalance() - item.quantity());

            productService.updateProduct(product);

            cart.add(cartItem);

        }

        Sale sale = new Sale(client, seller, cart);

        repository.save(sale);

        return sale;
    }

    public Page<ListSalesDto> findAllSales(Pageable pagination) {
        return repository.findAll(pagination).map(ListSalesDto::new);
    }

    public Sale findSaleById(Long id) {
        Sale sale = repository.getReferenceById(id);

        return sale;
    }

    @Transactional
    public void DeleteSale(Long id) {
        Sale sale = repository.getReferenceById(id);

        var cart = sale.getCartItems();

        for (CartItem item : cart) {
            productService.updateProductBalance(item.getProductId(), new UpdateProductBalanceDto(item.getQuantity()));
        }

        repository.delete(sale);

    }


}
