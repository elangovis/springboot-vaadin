package com.example.application.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Sign in | Acme")
public class LoginView extends VerticalLayout {

    public LoginView() {
        addClassName("login-page");
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        Div card = new Div();
        card.addClassName("login-card");

        Div brand = new Div();
        brand.addClassName("brand-mark");
        brand.setText("A");

        H1 title = new H1("Welcome back");
        title.addClassName("login-title");

        Paragraph subtitle = new Paragraph("Sign in to continue to your workspace.");
        subtitle.addClassName("login-subtitle");

        LoginForm loginForm = new LoginForm();
        loginForm.setAction("login");
        loginForm.addClassName("login-form");

        Paragraph credentials = new Paragraph("Demo account: demo / vaadin");
        credentials.addClassName("login-hint");

        card.add(brand, title, subtitle, loginForm, credentials);
        add(card);
    }
}
