package org.vaadin.artur.lit;

import java.util.List;

import org.vaadin.artur.lit.LazyList.Model;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("lazy-list")
@JsModule("./lazy-list.js")
public class LazyList extends LitTemplate<Model> {

	public interface Model extends TemplateModel {
		public void setPersons(List<Person> persons);

		public List<Person> getPersons();
	}

	public LazyList() {
		getModel().setPersons(PersonService.get().get(0, 10));
		getElement().addEventListener("load-persons", e -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
			}
			List<Person> persons = getModel().getPersons();
			persons.addAll(PersonService.get().get(persons.size(), 10));
			sendEvent();
		});
	}

	private void sendEvent() {
		getElement().executeJs("this.dispatchEvent(new CustomEvent('persons-available', {bubbles: true, detail: {persons: this.persons}}))");
	}

}
