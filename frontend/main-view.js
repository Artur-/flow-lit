import { LitElement, html, css } from "lit-element";

class MainView extends LitElement {
  render() {
    return html`
    <div>
      <slot></slot>
    </div>
    `;
  }
}
customElements.define("main-view", MainView);
