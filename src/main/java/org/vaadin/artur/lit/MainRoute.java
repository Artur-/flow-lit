package org.vaadin.artur.lit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.router.Route;

@Route("")
public class MainRoute extends Div {

    public MainRoute() {
        add(new MainView());
    }
}
