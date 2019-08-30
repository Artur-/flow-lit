package org.vaadin.artur.lit.view.gridform;

import java.util.stream.Stream;

import org.vaadin.artur.lit.MainLayout;
import org.vaadin.artur.lit.data.Person;
import org.vaadin.artur.lit.data.PersonService;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasEnabled;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.Route;

@Route(value = "grid-and-form", layout = MainLayout.class)
@JsModule("./grid-form.js")
@Tag("grid-form")
public class GridAndForm extends Component implements HasComponents {

	public Grid<Person> grid = new Grid<>(Person.class);

	private VerticalLayout form = new VerticalLayout();

	private TextField firstName = new TextField("First name");
	private TextField lastName = new TextField("Last name");
	private DatePicker birthDate = new DatePicker("Date of Birth");

	private Button save = new Button("Save");
	private Button cancel = new Button("Cancel");

	private Binder<Person> binder = new Binder<>(Person.class);

	private int editId;

	public GridAndForm() {
		grid.setDataProvider(new AbstractBackEndDataProvider<Person, String>() {
			@Override
			protected Stream<Person> fetchFromBackEnd(Query<Person, String> query) {
				return PersonService.get().get(query.getOffset(), query.getLimit()).stream();
			}

			@Override
			protected int sizeInBackEnd(Query<Person, String> query) {
				return PersonService.get().getCount();
			}
		});
		grid.setColumns("firstName", "lastName", "birthDate");
		grid.addSelectionListener(e -> {
			e.getFirstSelectedItem().ifPresent(person -> {
				this.editId = person.getId();
				binder.readBean(person);
				setFieldsEnabled(true);
			});
		});
		addToSlot("grid", grid);

		binder.bindInstanceFields(this);
		clear();

		save.addClickListener(e -> {
			try {
				Person person = PersonService.get().getById(editId).get();

				binder.writeBean(person);
				clear();
			} catch (ValidationException e1) {
				e1.printStackTrace();
			}
		});
		cancel.addClickListener(e -> {
			clear();
		});
		form.add(firstName, lastName, birthDate, new HorizontalLayout(save, cancel));
		addToSlot("form", form);
	}

	private void addToSlot(String slot, Component... components) {
		for (Component c : components) {
			c.getElement().setAttribute("slot", slot);
			add(c);
		}
	}

	private void clear() {
		binder.readBean(null);
		setFieldsEnabled(false);
		grid.deselectAll();
	}

	private void setFieldsEnabled(boolean enabled) {
		binder.getFields().forEach(field -> ((HasEnabled) field).setEnabled(enabled));
		save.setEnabled(enabled);
		cancel.setEnabled(enabled);

	}

}
