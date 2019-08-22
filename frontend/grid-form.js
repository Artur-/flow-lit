import { LitElement, html, css } from "lit-element";
import "@vaadin/vaadin-grid";
import "@vaadin/vaadin-text-field";
import "@vaadin/vaadin-form-layout";

class GridForm extends LitElement {
  static get styles() {
    return css`
      #firstName,#lastName {
        width: 15em;
      }
    `;
  }
  render() {
    return html`
        <slot></slot>
        <div style="display: block">
        <p>
          <vaadin-text-field label="First Name" id="firstName"></vaadin-text-field>
          <vaadin-text-field label="Last Name" id="lastName"></vaadin-text-field>
          </p>
          <p>
          <vaadin-date-picker label="Date of Birth" id="birthDate"></vaadin-date-picker>
          </p>
          <vaadin-button id="cancel">Cancel</vaadin-button>
          <vaadin-button id="save">Save</vaadin-button>
        </div>
        `
  }
}
customElements.define("grid-form", GridForm)