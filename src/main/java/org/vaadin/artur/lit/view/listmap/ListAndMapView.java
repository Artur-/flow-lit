package org.vaadin.artur.lit.view.listmap;

import org.vaadin.artur.lit.MainLayout;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;

@Route(value = "", layout = MainLayout.class)
@Tag("listmap-view")
@JsModule("./listmap-view.js")
public class ListAndMapView extends LitTemplate<TemplateModel> implements HasComponents {

	public ListAndMapView() {
		super();
		HorizontalLayout horizontalLayout = new HorizontalLayout(new LazyList(), new MarkerMap());
		horizontalLayout.setHeight("calc( 100vh - 44px )");
		add(horizontalLayout);
		add(new LazyLoadingIndicator());
	}

}
