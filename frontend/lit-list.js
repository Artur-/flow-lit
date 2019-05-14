import { LitElement, html, css } from "lit-element";
import { repeat } from "lit-html/directives/repeat";
import "@vaadin/vaadin-button";
import "j-elements";
import { VisibilityTrigger } from "./visibility-trigger";

class LitList extends LitElement {
  static get properties() {
    return {
      persons: Array
    };
  }
  static get styles() {
    return css`
      j-card {
        margin: 1em;
        width: 400px;
      }
    `;
  }
  render() {
    return html`
      ${repeat(
        this.persons,
        person => person.id,
        (person, index) => html`
            <j-card>
            <h3 slot="title">${person.firstName} ${person.lastName}</h3>
            <div>${person.company}</div>
            <div>${person.address}</div>
            <span>${person.zip} ${person.city}</div>
            </j-card>          `
      )}
      <vaadin-button id="loadMore" @click="${e => this.$server.loadMore(this.persons.length)}"
        >Load more</vaadin-button
      >
    `;
  }
  addItems(items) {
    this.persons = [...this.persons, ...items];
  }
  firstUpdated(changedProperties) {
    super.firstUpdated(changedProperties);
    const trigger = new VisibilityTrigger();
    const loadMore = this.shadowRoot.querySelector("#loadMore");
    trigger.connect(loadMore, async () => {
      this.$server.loadMore(this.persons.length);
    });

  }
}
customElements.define("lit-list", LitList);
