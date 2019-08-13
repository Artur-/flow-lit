package org.vaadin.artur.lit;

import java.util.List;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;

import org.vaadin.artur.lit.LazyList.Model;

@Tag("lit-list")
@JsModule("lit-list.js")
public class LazyList extends LitTemplate<Model> {

    public interface Model extends TemplateModel {
        public void setPersons(List<Person> persons);

        public List<Person> getPersons();
    }

    public LazyList() {
        getModel().setPersons(PersonService.get().get(0, 10));
    }

    @ClientCallable
    public void loadMore() {
        List<Person> persons = getModel().getPersons();
        persons.addAll(PersonService.get().get(persons.size(), 10));
    }

}
