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
		const containerStyle = notification._container.style;

		document.body.addEventListener("load-persons", e => {
			notification.opened = true;
		})
		document.body.addEventListener("persons-available", e => {
			notification.opened = false;
		})
	}
}
customElements.define("lazy-loading-indicator", LazyLoadingIndicator)