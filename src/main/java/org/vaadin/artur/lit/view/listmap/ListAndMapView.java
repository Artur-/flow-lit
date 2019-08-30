package org.vaadin.artur.lit.view.listmap;

import org.vaadin.artur.lit.MainLayout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@Tag("listmap-view")
@JsModule("./listmap-view.js")
public class ListAndMapView extends Component implements HasComponents {

	public ListAndMapView() {
		super();
		addToSlot("first", new LazyList());
		addToSlot("second", new MarkerMap());
		add(new LazyLoadingIndicator());
	}

	private void addToSlot(String name, Component child) {
		child.getElement().setAttribute("slot", name);
		add(child);
	}

}
