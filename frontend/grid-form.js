import { LitElement, html, css } from "lit-element";
import "@vaadin/vaadin-grid";
import "@vaadin/vaadin-text-field";
import "@vaadin/vaadin-form-layout";

class GridForm extends LitElement {
  static get styles() {
    return css`
      #firstName,
      #lastName {
        width: 15em;
      }
    `;
  }
  render() {
    return html`
      <slot name="grid"></slot>
      <div style="display: block">
        <p>
          <slot name="form"></slot>
        </p>
      </div>
    `;
  }
}
customElements.define("grid-form", GridForm);
