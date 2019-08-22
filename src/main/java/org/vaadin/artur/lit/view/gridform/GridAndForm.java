package org.vaadin.artur.lit.view.gridform;

import java.util.List;
import java.util.stream.Stream;

import org.vaadin.artur.lit.MainLayout;
import org.vaadin.artur.lit.data.Person;
import org.vaadin.artur.lit.data.PersonService;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasEnabled;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;

@Route(value = "grid-and-form", layout = MainLayout.class)
@JsModule("./grid-form.js")
@Tag("grid-form")
public class GridAndForm extends LitTemplate<TemplateModel> implements HasComponents {

	public Grid<Person> grid = new Grid<>(Person.class);

	@Id
	private TextField firstName;
	@Id
	private TextField lastName;
	@Id
	private DatePicker birthDate;

	@Id
	private Button save;
	@Id
	private Button cancel;

	private Binder<Person> binder;

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
		add(grid);

		binder = new Binder<Person>(Person.class);
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
