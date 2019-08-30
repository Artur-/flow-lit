package org.vaadin.artur.lit.view.listmap;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;

@Route("")
@Tag("listmap-view")
@JsModule("./listmap-view.js")
@Push
public class ListAndMapView extends Component implements HasComponents {

	public ListAndMapView() {
		super();
		add(new LazyList());
	}

}
