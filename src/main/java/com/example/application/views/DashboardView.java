package com.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("")
@PageTitle("Dashboard | Acme")
@PermitAll
public class DashboardView extends VerticalLayout {

    public DashboardView() {
        addClassName("dashboard-page");
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication == null ? "there" : authentication.getName();

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("dashboard-header");
        header.setWidthFull();
        header.setAlignItems(Alignment.CENTER);

        Span brand = new Span("A");
        brand.addClassName("brand-mark");

        Span brandName = new Span("Acme");
        brandName.addClassName("brand-name");

        Div brandGroup = new Div(brand, brandName);
        brandGroup.addClassName("brand-group");

        Button signOut = new Button("Sign out", event -> {
            VaadinSession.getCurrent().getSession().invalidate();
            SecurityContextHolder.clearContext();
            UI.getCurrent().getPage().setLocation("/login");
        });
        signOut.addClassName("sign-out-button");

        header.add(brandGroup, signOut);
        header.expand(brandGroup);

        Div content = new Div();
        content.addClassName("dashboard-content");

        Span eyebrow = new Span("YOUR WORKSPACE");
        eyebrow.addClassName("eyebrow");

        H1 welcome = new H1("You’re signed in, " + username + ".");
        welcome.addClassName("dashboard-title");

        Paragraph intro = new Paragraph("Your Spring Boot and Vaadin login is working.");
        intro.addClassName("dashboard-intro");

        Div panel = new Div();
        panel.addClassName("welcome-panel");
        Span status = new Span("●");
        status.addClassName("status-dot");
        H2 panelTitle = new H2("Everything is ready");
        Paragraph panelText = new Paragraph(
                "This protected page is only available after you sign in. Use the button in the top-right to end your session."
        );
        panel.add(status, panelTitle, panelText);

        content.add(eyebrow, welcome, intro, panel);
        add(header, content);
    }
}
