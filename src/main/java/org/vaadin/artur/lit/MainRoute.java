package org.vaadin.artur.lit;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.router.Route;

@Route("")
@NpmPackage(value = "github-corner", version = "2.0.3")
public class MainRoute extends Div {

    public MainRoute() {
        add(new MainView());
    }
}
