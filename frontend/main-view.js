import { html, LitElement } from "lit-element";

class MainView extends LitElement {
  render() {
    return html`
      <div><b>Hello from LitElement</b></div>
      <div>Server Time: ${this.serverTime}</div>
      <p>
        <vaadin-button @click="${e => this.$server.refresh()}"
          >Refresh</vaadin-button
        >
      </p>
    `;
  }
  static get properties() {
    return {
      serverTime: {
        type: String
      }
    };
  }
}

customElements.define("main-view", MainView);
