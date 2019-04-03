import { html, LitElement } from "lit-element";
import "github-corner";
class MainView extends LitElement {
  render() {
    return html`
      <github-corner>
        <a href="https://github.com/Artur-/flow-lit">GitHub</a>
      </github-corner>

      <div><b>Hello from LitElement</b></div>
      <div>Server Time: ${this.serverTime}</div>
      <div>MainView instances created on server: ${this.instancesCreated}</div>
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
