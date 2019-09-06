import { LitElement, html, css } from "lit-element";
import { repeat } from "lit-html/directives/repeat";
import "@vaadin/vaadin-button";
import "j-elements";
import { VisibilityTrigger } from "./visibility-trigger";
import { classMap } from "lit-html/directives/class-map.js";
import "a-avataaar";
class LazyList extends LitElement {
  static get properties() {
    return {
      persons: Array
    };
  }
  static get styles() {
    return css`
      :host {
        overflow-y: auto;
        -webkit-overflow-scrolling: touch;
        margin: 1em;
      }
      j-card {
        width: 100%;
      }
      j-card.selected {
        background: #daa;
      }
      j-card [slot="title"] {
        align-items: center;
        display: flex;
      }
    `;
  }
  render() {
    return html`
      ${repeat(
        this.persons,
        person => person.id,
        (person, index) => html`
      <j-card class=${classMap(
        person.classes ? person.classes : {}
      )} @click="${e => this.personSelected(person)}">
        <h3 slot="title"><a-avataaar identifier="${person.firstName} ${
          person.lastName
        }"></a-avataaar>${person.firstName} ${person.lastName}</h3>
        <div>${person.company}</div>
        <div>${person.address}</div>
        <span>${person.zip} ${person.city}</div>
      </j-card> `
    )}
      <vaadin-button id="loadMore" @click="${e => this.loadMore()}">Load more</vaadin-button>
    `;
  }
  firstUpdated(changedProperties) {
    super.firstUpdated(changedProperties);

    document.body.addEventListener("person-selected", async e => {
      const person = this.persons.find(p => p.id == e.detail.person.id);
      this.persons.forEach(person => {
        person.classes = {};
      });
      person.classes = { selected: true };
      this.requestUpdate("persons");
      await this.updateComplete;
      const selectedElement = this.shadowRoot.querySelector(".selected");
      selectedElement.scrollIntoView({ block: "nearest" });
    });

    const trigger = new VisibilityTrigger();
    const loadMore = this.shadowRoot.querySelector("#loadMore");
    trigger.connect(loadMore, () => {
      this.loadMore();
    });
    setTimeout(() => {
      this.dispatchEvent(new CustomEvent('persons-available', { bubbles: true, detail: { persons: this.persons } }));
    }, 100);
  }
  loadMore() {
    this.dispatchEvent(new CustomEvent("load-persons", { bubbles: true }));
  }
  personSelected(person) {
    this.dispatchEvent(new CustomEvent("person-selected", { bubbles: true, detail: { person: person } }));
  }
  personUpdated(personData) {
    const person = this.persons.find(p => p.id == personData.id);
    if (!person) {
      return;
    }
    person.longitude = personData.longitude;
    person.latitude = personData.latitude;

    this.dispatchEvent(new CustomEvent("person-updated", { bubbles: true, detail: { person: person } }));
  }
  personsAdded(persons) {
    this.persons.push(...persons);
    this.requestUpdate("persons");
		this.dispatchEvent(new CustomEvent('persons-available', {bubbles: true, detail: { persons: this.persons }}));
  }
}
customElements.define("lazy-list", LazyList);
