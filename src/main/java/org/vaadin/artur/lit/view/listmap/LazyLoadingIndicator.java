package org.vaadin.artur.lit.view.listmap;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;

@Tag("lazy-loading-indicator")
@JsModule("./lazy-loading-indicator.js")
@CssImport(value = "./lazy-loading-indicator.css", themeFor = "vaadin-notification-card")
public class LazyLoadingIndicator extends Component {

}
