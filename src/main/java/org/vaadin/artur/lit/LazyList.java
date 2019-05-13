package org.vaadin.artur.lit;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;

import elemental.json.Json;
import elemental.json.JsonValue;

@Tag("lit-list")
@JsModule("lit-list.js")
public class LazyList extends Component {

    public LazyList() {
        getElement().setPropertyJson("persons",
                toJson(PersonService.get().get(0, 10)));
    }

    private JsonValue toJson(List<Person> list) {
        try {
            return Json.instance()
                    .parse(new ObjectMapper().writeValueAsString(list));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Json.createArray();
        }
    }

    @ClientCallable
    public void loadMore(int offset) {
        getElement().callJsFunction("addItems",
                toJson(PersonService.get().get(offset, 10)));
    }

}
