package org.vaadin.artur.lit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.router.Route;

@Tag("main-view")
@JsModule("main-view.js")
@Route("")
@NpmPackage("lit-element")
@NpmPackage("github-corner")
public class MainView extends Component {

    private static int instancesCreated = 0;

    public MainView() {
        instancesCreated++;
        refresh();
    }

    @ClientCallable
    public void refresh() {
        getElement().setProperty("serverTime", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        getElement().setProperty("instancesCreated", instancesCreated);
    }

}
