# Vaadin 14 & Lit

The example consists of a lazy loading list of persons, which are also plotted on a map. Push is used to update person locations in real time.

# Quickstart
Run the project as
```
mvn
```

Open http://localhost:8080/

---

A live demo is running at https://labs.vaadin.com/flow-lit/

# List & Map view

![map-list](https://user-images.githubusercontent.com/260340/64474122-ae2d5d00-d178-11e9-87fc-9b20e22f4208.png)

[ListAndMapView.java](https://github.com/Artur-/flow-lit/blob/master/src/main/java/org/vaadin/artur/lit/view/listmap/ListAndMapView.java) is a simple template-based view; it just adds the `LazyList` component to the (unnamed) slot in the template, while letting the template handle everything else. Note the annotations specifying which template to use, as well as enabling server push.

```java
@Tag("listmap-view")
@JsModule("./listmap-view.js")
@Push
public class ListAndMapView extends Component implements HasComponents {
	public ListAndMapView() {
		add(new LazyList());
	}
}
```

The associated template ([listmap-view.js](https://github.com/Artur-/flow-lit/blob/master/frontend/listmap-view.js)), has a few elements around a single slot where the lazy list will end up. The elements are laid out using CSS in the template.

```js
  render() {
    return html`
    <github-corner><a href="https://github.com/Artur-/flow-lit"></a></github-corner>
      <div class="container">
          <slot></slot>
          <marker-map></marker-map>
      </div>
      <lazy-loading-indicator></lazy-loading-indicator>
    </div>
    `;
  }
```

## The List
LazyList shows a more complex use of Lit. The [server-side Java](https://github.com/Artur-/flow-lit/blob/master/src/main/java/org/vaadin/artur/lit/view/listmap/LazyList.java) class provides lazy-loading of the Person list (with a fake delay to make the laziness visible) and pushes movements of the currently visible persons. The Java class provides the data, while the Lit template does handles the rendering.

```java
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
```

Meanwhile, the Lit template in [lazy-list.js](https://github.com/Artur-/flow-lit/blob/master/frontend/lazy-list.js) defines the presentation of the data and handles all interactions, requesting more data when needed (using an IntersectionObserver):

```js
render() {
  return html`
    ${repeat(
      this.persons,
      person => person.id,
      (person, index) => html`
        <j-card class=${classMap(person.classes ? person.classes : {})} @click="${e => this.personSelected(person)}">
          <h3 slot="title"><a-avataaar identifier="${person.firstName} ${person.lastName}"></a-avataaar>${person.firstName} ${person.lastName}</h3>
          <div>${person.company}</div>
          <div>${person.address}</div>
          <span>${person.zip} ${person.city}</div>
        </j-card> `
    )}
```

This uses the Lit `repeat` directive to loop over all the available persons and render a `<j-card>` element (from [j-elements](https://github.com/jouni/j-elements)) for each person. The `repeat` directive uses three parts:

1. `this.persons`, which is the list of persons populated by the server
1. `persons => person.id` defines the identity of individual items so they can be updated later
1. ```(person, index) => html`...` ``` defines the markup to render for each person. Here the person and index variables are related to the active person being rendered.

A click listener is added to each card using ```@click="${e ⇒ this.personSelected(person)}``` which calls the `personSelected` method with the selected person as an argument. This is used to highlight the person in the list and also to emit a `person-selected` event when a person is selected so the map can highlight the selected person without going through the server.

If you are interested in more details about lazy loading, intersection observers etc, see the corresponding files in [frontend (client side)](https://github.com/Artur-/flow-lit/tree/master/frontend) and/or [src/main/java (server side)](https://github.com/Artur-/flow-lit/tree/master/src/main/java/org/vaadin/artur/lit).


