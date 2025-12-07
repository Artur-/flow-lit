package org.vaadin.artur.lit.view.listmap;

import java.util.List;
import java.util.Optional;

import org.vaadin.artur.lit.ai.PersonMover;
import org.vaadin.artur.lit.data.Person;
import org.vaadin.artur.lit.data.PersonService;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.UIDetachedException;
import com.vaadin.flow.component.dependency.JsModule;

@Tag("lazy-list")
@JsModule("./lazy-list.js")
public class LazyList extends Component {
    private int loadedInBrowser;

    public LazyList() {
        List<Person> persons = PersonService.get().get(0, 10);
        loadedInBrowser = 10;
        getElement().setPropertyList("persons", persons);
    }

    @ClientCallable
    public List<Person> loadPersons(int offset) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
        }
        loadedInBrowser = Math.max(loadedInBrowser, offset + 10);
        return PersonService.get().get(offset, 10);
    }

    public record PersonUpdate(int personId, double longitude, double latitude) {
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
                        getElement().callJsFunction("personUpdated",
                                new PersonUpdate(e.getPersonId(), e.getLongitude(), e.getLatitude()));
                    }
                });
            } catch (UIDetachedException ee) {
                e.unregisterListener();
            }
        });

    }

}
