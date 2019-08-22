package org.vaadin.artur.lit.view.listmap;

import java.util.List;
import java.util.Optional;

import org.vaadin.artur.lit.ai.PersonMover;
import org.vaadin.artur.lit.data.Person;
import org.vaadin.artur.lit.data.PersonService;
import org.vaadin.artur.lit.view.listmap.LazyList.Model;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.UIDetachedException;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.templatemodel.Exclude;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("lazy-list")
@JsModule("./lazy-list.js")
public class LazyList extends LitTemplate<Model> {

	public interface Model extends TemplateModel {
		public void setPersons(List<Person> persons);

		@Exclude({ "birthDate" })
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

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);

		PersonMover.get().addListener(e -> {
			// This code is executed WITHOUT session lock
			Optional<UI> ui = getUI();
			if (!ui.isPresent()) {
				e.unregisterListener();
				return;
			}
			try {
				ui.get().access(() -> {
					List<Person> persons = getModel().getPersons();
					for (int i = 0; i < persons.size(); i++) {
						Person p = persons.get(i);
						if (p.getId() == e.getPersonId()) {
							Person newPerson = PersonService.get().getById(e.getPersonId()).get();
							Person modelPerson = getModel().getPersons().get(i);
							modelPerson.setLatitude(newPerson.getLatitude());
							modelPerson.setLongitude(newPerson.getLongitude());
							sendUpdateEvent(i);
							break;
						}
					}

				});
			} catch (UIDetachedException ee) {
				e.unregisterListener();
			}
		});

	}

	private void sendUpdateEvent(int i) {
		getElement().callJsFunction("personUpdated", i);

	}

	private void sendEvent() {
		getElement().executeJs(
				"this.dispatchEvent(new CustomEvent('persons-available', {bubbles: true, detail: {persons: this.persons}}))");
	}

}
