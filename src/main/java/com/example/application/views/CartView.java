package com.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.List;

@Route("cart")
@PageTitle("Shopping cart | Aadish")
@PermitAll
public class CartView extends VerticalLayout {

    private final List<CartItem> items = List.of(
            new CartItem("Classic Leather Backpack", "Navy", 1, 129.00,
                    "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?auto=format&fit=crop&w=240&h=240&q=80"),
            new CartItem("Wireless Headphones", "Silver", 1, 199.00,
                    "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&w=240&h=240&q=80"),
            new CartItem("Smart Water Bottle", "Black", 2, 39.00,
                    "https://images.unsplash.com/photo-1602143407151-7111542de6e8?auto=format&fit=crop&w=240&h=240&q=80")
    );

    public CartView() {
        addClassName("cart-page");
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        HorizontalLayout topBar = new HorizontalLayout();
        topBar.setWidthFull();
        topBar.setAlignItems(Alignment.CENTER);
        topBar.setJustifyContentMode(JustifyContentMode.BETWEEN);

        Span brand = new Span("Aadish");
        brand.addClassName("brand-name");

        Button continueShopping = new Button("Continue shopping", event -> UI.getCurrent().navigate(DashboardView.class));
        Button checkout = new Button("Checkout");
        checkout.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        checkout.addClickListener(event -> Notification.show("Checkout is not connected to a backend yet."));

        topBar.add(brand, new HorizontalLayout(continueShopping, checkout));

        H1 title = new H1("Your shopping cart");
        title.addClassName("cart-title");

        H2 itemCount = new H2(items.size() + " item" + (items.size() == 1 ? "" : "s") + " ready for checkout");
        itemCount.addClassName("cart-subtitle");

        VerticalLayout cartList = new VerticalLayout();
        cartList.setPadding(false);
        cartList.setSpacing(true);
        cartList.setWidthFull();

        for (CartItem item : items) {
            cartList.add(createCartRow(item));
        }

        Div summary = createSummary();

        HorizontalLayout content = new HorizontalLayout(cartList, summary);
        content.setWidthFull();
        content.setSpacing(true);
        content.setAlignItems(Alignment.START);
        content.setFlexGrow(1.0, cartList);

        add(topBar, title, itemCount, content);
    }

    private Div createCartRow(CartItem item) {
        Div row = new Div();
        row.addClassName("cart-row");

        Div product = new Div();
        product.addClassName("product-details");

        Image image = new Image(item.imageUrl(), item.name());
        image.setWidth("88px");
        image.setHeight("88px");
        image.getStyle().set("object-fit", "cover");
        image.getStyle().set("border-radius", "8px");

        Span name = new Span(item.name());
        name.addClassName("product-name");

        Span meta = new Span(item.color() + " • Qty " + item.quantity());
        meta.addClassName("product-meta");

        product.add(name, meta);

        Span unitPrice = new Span(String.format("$%.2f each", item.price()));
        unitPrice.addClassName("product-unit-price");

        Span totalPrice = new Span(String.format("$%.2f", item.price() * item.quantity()));
        totalPrice.addClassName("product-total-price");

        Button remove = new Button("Remove");
        remove.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        remove.addClickListener(event -> Notification.show(item.name() + " removed from cart."));

        HorizontalLayout layout = new HorizontalLayout(image, product, unitPrice, totalPrice, remove);
        layout.setWidthFull();
        layout.setAlignItems(Alignment.CENTER);
        layout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        layout.setFlexGrow(1, product);
        row.add(layout);

        return row;
    }

    private Div createSummary() {
        Div summary = new Div();
        summary.addClassName("summary-panel");

        H2 heading = new H2("Order summary");
        heading.addClassName("summary-heading");

        Div subtotalRow = createSummaryRow("Subtotal", calculateSubtotal());
        Div shippingRow = createSummaryRow("Shipping", 18.00);
        Div totalRow = createSummaryRow("Total", calculateSubtotal() + 18.00, true);

        Button checkout = new Button("Proceed to checkout");
        checkout.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        checkout.addClickListener(event -> Notification.show("Proceeding to checkout..."));

        summary.add(heading, subtotalRow, shippingRow, totalRow, checkout);
        return summary;
    }

    private Div createSummaryRow(String label, double amount) {
        return createSummaryRow(label, amount, false);
    }

    private Div createSummaryRow(String label, double amount, boolean emphasize) {
        Div row = new Div();
        row.addClassName("summary-row");
        if (emphasize) {
            row.addClassName("summary-total");
        }

        Span labelText = new Span(label);
        labelText.addClassName("summary-label");

        Span valueText = new Span(String.format("$%.2f", amount));
        valueText.addClassName("summary-value");

        row.add(labelText, valueText);
        return row;
    }

    private double calculateSubtotal() {
        return items.stream().mapToDouble(item -> item.price() * item.quantity()).sum();
    }

    private record CartItem(String name, String color, int quantity, double price, String imageUrl) {
    }
}
