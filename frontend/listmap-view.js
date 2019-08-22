import { LitElement, html, css } from "lit-element";

class ListMapView extends LitElement {
  static get styles() {
    return css`
    .container {
      display: flex;
      flex-direction: row;
      height: calc(100vh - 32px);
    }

    ::slotted(lazy-list) {
      width: 300px;
    }

    @media(max-width: 400px) {

    .container {
      flex-direction: column;
    }
    ::slotted(lazy-list) {
      height: 50%;
      width: 100%;
      margin: 0;
    }
   }
   `;
  }
  render() {
    return html`
      <div class="container">
          <slot name="first"></slot>
          <slot name="second"></slot>
      </div>
      <slot></slot>
    </div>
    `;
  }
}
customElements.define("listmap-view", ListMapView);
