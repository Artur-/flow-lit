import { LitElement, html, css } from "lit-element";
import "@vaadin/vaadin-notification";

class LazyLoadingIndicator extends LitElement {
	render() {
		return html`
		<vaadin-notification duration=-1 position='middle' theme="primary large">
			<template>
				Loading server data slowly just so you can see this message and be annoyed...
			</template>
		</vaadin-notification>
		`;
	}
	firstUpdated(changedProperties) {
		super.firstUpdated(changedProperties);
		const notification = this.shadowRoot.querySelector("vaadin-notification");
		this.onLoad = e => {
			notification.opened = true;
		};
		this.onPersons = e => {
			notification.opened = false;
		};
		const containerStyle = notification._container.style;

		document.body.addEventListener("load-persons", this.onLoad);
		document.body.addEventListener("persons-available", this.onPersons);
	}
	disconnectedCallback() {
		super.disconnectedCallback();
		document.body.removeEventListener("load-persons", this.onLoad);
		document.body.removeEventListener("persons-available", this.onPersons);
	}
}
customElements.define("lazy-loading-indicator", LazyLoadingIndicator)