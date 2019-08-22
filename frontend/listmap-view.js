import { LitElement, html, css } from "lit-element";

class ListMapView extends LitElement {
  render() {
    return html`
    <div>
      <slot></slot>
    </div>
    `;
  }
}
customElements.define("listmap-view", ListMapView);
