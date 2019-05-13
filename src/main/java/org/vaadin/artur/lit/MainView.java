package org.vaadin.artur.lit;

import org.vaadin.artur.github_corner.GitHubCorner;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.router.Route;

@Route("")
@BodySize(width = "100%")
public class MainView extends Div {

    public MainView() {
        super();
        add(new GitHubCorner("https://github.com/Artur-/flow-lit"));
        add(new LazyList());
    }

}
