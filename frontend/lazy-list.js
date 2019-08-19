import { LitElement, html, css } from "lit-element";
import { repeat } from "lit-html/directives/repeat";
import "@vaadin/vaadin-button";
import "j-elements";
import { VisibilityTrigger } from "./visibility-trigger";
import { classMap } from 'lit-html/directives/class-map.js';

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
      }
      j-card {
        margin: 1em;
        width: 400px;
      }
      j-card.selected {
        background: #daa;
      }
    `;
  }
  render() {
    return html`
      ${repeat(
      this.persons,
      person => person.id,
      (person, index) => html`
      <j-card class=${classMap(person.classes)} @click="${e => this.personSelected(person)}">
        <h3 slot="title">${person.firstName} ${person.lastName}</h3>
        <div>${person.company}</div>
        <div>${person.address}</div>
        <span>${person.zip} ${person.city}</div>
      </j-card> `
      )}
      <vaadin-button id="loadMore" @click="${e => this.$server.loadMore()}">Load more</vaadin-button>
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
    trigger.connect(loadMore, async () => {
      this.$server.loadMore();
    });
    setTimeout(() => this.newItemsAvailable(), 100);
  }
  newItemsAvailable() {
    this.dispatchEvent(new CustomEvent("persons-available", { bubbles: true, detail: { persons: this.persons } }));
  }
  personSelected(person) {
    this.dispatchEvent(new CustomEvent("person-selected", { bubbles: true, detail: { person: person } }));
  }
}
customElements.define("lazy-list", LazyList);
