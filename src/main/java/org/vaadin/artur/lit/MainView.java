package org.vaadin.artur.lit;

import org.vaadin.artur.github_corner.GitHubCorner;
import org.vaadin.artur.lit.ai.PersonMover;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;

@Route("")
@BodySize(width = "100%")
@Tag("main-view")
@JsModule("./main-view.js")
@Push
public class MainView extends LitTemplate<TemplateModel> implements HasComponents {

	public MainView() {
		super();
		GitHubCorner gitHubCorner = new GitHubCorner("https://github.com/Artur-/flow-lit");
		gitHubCorner.getElement().setAttribute("position", "left");
		add(gitHubCorner);
		HorizontalLayout horizontalLayout = new HorizontalLayout(new LazyList(), new MarkerMap());
		horizontalLayout.setHeight("100vh");
		add(horizontalLayout);
		add(new LazyLoadingIndicator());

	}

}
