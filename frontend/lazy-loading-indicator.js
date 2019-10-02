import { LitElement, html, css } from "lit-element";
import "@vaadin/vaadin-notification";

class LazyLoadingIndicator extends LitElement {
  static get styles() {
    return css`
      :host([hidden]) {
        display: none;
      }
      :host {
        display: inline-block;
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        padding: 1.2em;
        background: blue;
        color: white;
        border: 1px solid black;
        z-index: 10000;
      }
    `;
  }
  render() {
    return html`
      <div>
        Loading server data slowly just so you can see this message and be
        annoyed...
      </div>
    `;
  }
}
customElements.define("lazy-loading-indicator", LazyLoadingIndicator);
