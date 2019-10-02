import { LitElement, html, css } from "lit-element";
import "./marker-map";
import "./lazy-loading-indicator";
import "github-corner";

class ListMapView extends LitElement {
  static get styles() {
    return css`
      .container {
        display: flex;
        flex-direction: row;
        height: 100vh;
      }

      ::slotted(lazy-list) {
        width: 300px;
      }

      @media (max-width: 400px) {
        .container {
          flex-direction: column;
        }
        ::slotted(lazy-list) {
          height: 50%;
          width: 100%;
          margin: 0;
        }
      }
      github-corner svg {
        z-index: 1000;
      }
    `;
  }
  render() {
    return html`
    <github-corner><a href="https://github.com/Artur-/flow-lit"></a></github-corner>
      <div class="container">
          <slot></slot>
          <marker-map></marker-map>
      </div>
    </div>
    `;
  }
}
customElements.define("listmap-view", ListMapView);
