import { LitElement, html } from "lit-element";
import { repeat } from "lit-html/directives/repeat";
import "github-corner";
import '@vaadin/vaadin-button'

class MainView extends LitElement {
  static get properties() {
    return {
      text: String,
      strings: Array,
      persons: Array
    };
  }
  render() {
    return html`
      <github-corner>
        <a href="https://github.com/Artur-/flow-lit">GitHub</a>
      </github-corner>
      <vaadin-button id="hello">Hello</vaadin-button>
      <div>${this.text}</div>
      <div>Strings (updated by replacing model list):</div>
      <ul>
        ${this.strings.map(
          item =>
            html`
              <li>
                ${item}<vaadin-button @click="${e => this.$server.deleteString(item)}">
                  Remove
                </vaadin-button>
              </li>
            `
        )}
        <li><vaadin-button id="addString">Add</vaadin-button></li>
      </ul>

      <div>Persons (updated by modifying model list):</div>
      <ul>
        ${repeat(
          this.persons,
          person => person.id,
          (person, index) => html`
            <li>
              ${index}: ${person.lastName}, ${person.firstName}
              <vaadin-button @click="${e => this.$server.deletePerson(person.id)}">
                Remove
              </vaadin-button>
            </li>
          `
        )}
        <li><vaadin-button id="addPerson">Add</vaadin-button></li>
      </ul>
    `;
  }
}
customElements.define("main-view", MainView);
