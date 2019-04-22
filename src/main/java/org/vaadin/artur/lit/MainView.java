package org.vaadin.artur.lit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.templatemodel.TemplateModel;

import org.vaadin.artur.lit.MainView.MyModel;

@Tag("main-view")
@JsModule("main-view.js")
@NpmPackage("lit-element")
@NpmPackage("github-corner")
public class MainView extends LitTemplate<MyModel> {

    private static final String[] firstNames = new String[] { "Nash", "Cade", "Willa", "Joel", "Mechelle" };
    private static final String[] lastNames = new String[] { "Davenport", "Suarez", "Morrison" };

    @Id
    public Button hello;
    @Id
    public Button addString;
    @Id
    public Button addPerson;
    private int personIndex = 1;

    public interface MyModel extends TemplateModel {
        public void setText(String text);

        public List<String> getStrings();

        public void setStrings(List<String> strings);

        public List<Person> getPersons();

        public void setPersons(List<Person> persons);
    }

    public MainView() {
        super();
        getModel().setText("Hello");
        List<String> strings = new ArrayList<>();
        Collections.addAll(strings, new String[] { "String 1", "String 2", "String 3" });
        getModel().setStrings(strings);
        getModel().setPersons(new ArrayList<>());
        getModel().getPersons().add(new Person(personIndex++, "Wyoming", "Wiggins"));
        getModel().getPersons().add(new Person(personIndex++, "Ori", "Griffith"));
        getModel().getPersons().add(new Person(personIndex++, "Maisie", "Hurst"));
        hello.addClickListener(e -> {
            getModel().setText("Hello from the server at " + System.currentTimeMillis());
        });
        addString.addClickListener(e -> {
            List<String> newStrings = new ArrayList<>(getModel().getStrings());
            newStrings.add("String created at " + System.currentTimeMillis());
            getModel().setStrings(newStrings);
        });
        addPerson.addClickListener(e -> {
            getModel().getPersons().add(new Person(personIndex++, firstNames[personIndex % firstNames.length],
                    lastNames[personIndex % lastNames.length]));
        });
    }

    @ClientCallable
    public void deleteString(String string) {
        List<String> newList = getModel().getStrings().stream().filter(str -> !str.equals(string))
                .collect(Collectors.toList());
        getModel().setStrings(newList);
    }

    @ClientCallable
    public void deletePerson(int personId) {
        getModel().getPersons().removeIf(p -> p.getId() == personId);
    }

}
