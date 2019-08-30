package org.vaadin.artur.lit.view.listmap;

import java.util.List;
import java.util.Optional;

import org.vaadin.artur.lit.ai.PersonMover;
import org.vaadin.artur.lit.data.Person;
import org.vaadin.artur.lit.data.PersonService;

import elemental.json.Json;
import elemental.json.JsonObject;
import elemental.json.JsonValue;
import elemental.json.impl.JreJsonFactory;

import com.google.gson.Gson;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.UIDetachedException;
import com.vaadin.flow.component.dependency.JsModule;

@Tag("lazy-list")
@JsModule("./lazy-list.js")
public class LazyList extends Component {

	private int loadedInBrowser = 0;

	public LazyList() {
		List<Person> persons = PersonService.get().get(0, 10);
		loadedInBrowser = 10;
		getElement().setPropertyJson("persons", toJson(persons));
		getElement().addEventListener("load-persons", e -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
			}
			loadedInBrowser += 10;
			sendEvent(PersonService.get().get(loadedInBrowser, 10));
		});

	}

	private JsonValue toJson(List<Person> persons) {
		String personsJson = new Gson().toJson(persons);
		return new JreJsonFactory().parse(personsJson);
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
					List<Person> loadedPersons = PersonService.get().get(0, loadedInBrowser);
					boolean personLoaded = loadedPersons.stream().anyMatch(person -> person.getId() == e.getPersonId());
					if (personLoaded) {
						JsonObject person = Json.createObject();
						person.put("id", e.getPersonId());
						person.put("longitude", e.getLongitude());
						person.put("latitude", e.getLatitude());
						getElement().callJsFunction("personUpdated", person);
					}
				});
			} catch (UIDetachedException ee) {
				e.unregisterListener();
			}
		});

	}

	private void sendEvent(List<Person> list) {
		getElement().callJsFunction("personsAdded", toJson(list));
	}

}
