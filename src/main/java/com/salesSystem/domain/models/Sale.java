package com.salesSystem.domain.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "sales")
@Table(name = "sales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(optional = false)
    @JoinColumn(name = "buyer_id")
    private Client buyer;

    private LocalDateTime timestamp = LocalDateTime.now();

    @ElementCollection
    @CollectionTable(name = "client_cart_items", joinColumns = @JoinColumn(name = "client_id"))
    private List<CartItem> cartItems = new ArrayList<>();

    public void addItemToCart(Product product, int quantity) {
        CartItem item = new CartItem(product.getId(), product.getProductName(), product.getPrice(), quantity);
        cartItems.add(item);
    }

    public BigDecimal getCartTotal() {
        return cartItems.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Sale(Client client, Seller seller, List<CartItem> cart) {
        this.seller = seller;
        this.buyer = client;
        this.cartItems = cart;
    }
}
