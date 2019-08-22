package org.vaadin.artur.lit;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.artur.github_corner.GitHubCorner;
import org.vaadin.artur.lit.view.gridform.GridAndForm;
import org.vaadin.artur.lit.view.listmap.ListAndMapView;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLink;

@BodySize(width = "100%")
@Push
@CssImport(value = "./githubcorner.css", themeFor = "vaadin-app-layout")
public class MainLayout extends AppLayout implements BeforeEnterObserver {

	private Tabs tabs = new Tabs();
	private Map<Class<? extends Component>, Tab> targets = new HashMap<>();

	public MainLayout() {
		GitHubCorner gitHubCorner = new GitHubCorner("https://github.com/Artur-/flow-lit");
		addToNavbar(gitHubCorner);

		addMenuItem("List & Map", ListAndMapView.class);
		addMenuItem("Grid & Form", GridAndForm.class);

		addToNavbar(tabs);
	}

	private void addMenuItem(String label, Class<? extends Component> target) {
		Tab tab = new Tab(new RouterLink(label, target));
		targets.put(target, tab);
		tabs.add(tab);

	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		tabs.setSelectedTab(targets.get(event.getNavigationTarget()));

	}
}
