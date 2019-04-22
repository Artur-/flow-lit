import { LitElement, html } from "lit-element";
import { repeat } from "lit-html/directives/repeat";
import "github-corner";

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
      <button id="hello">Hello</button>
      <div>${this.text}</div>
      <div>Strings (updated by replacing model list):</div>
      <ul>
        ${this.strings.map(
          item =>
            html`
              <li>
                ${item}<button @click="${e => this.$server.deleteString(item)}">
                  Remove
                </button>
              </li>
            `
        )}
        <li><button id="addString">Add</button></li>
      </ul>

      <div>Persons (updated by modifying model list):</div>
      <ul>
        ${repeat(
          this.persons,
          person => person.id,
          (person, index) => html`
            <li>
              ${index}: ${person.lastName}, ${person.firstName}
              <button @click="${e => this.$server.deletePerson(person.id)}">
                Remove
              </button>
            </li>
          `
        )}
        <li><button id="addPerson">Add</button></li>
      </ul>
    `;
  }
}
customElements.define("main-view", MainView);
