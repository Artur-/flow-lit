import { LitElement, html, css, customElement, property, PropertyValues, unsafeCSS } from "lit-element";
import * as L from "leaflet/dist/leaflet-src.esm";
import icon from 'leaflet/dist/images/marker-icon.png';
import iconShadow from 'leaflet/dist/images/marker-shadow.png';
import leafletCss from '!!raw-loader!leaflet/dist/leaflet.css';
import ResizeObserver from 'resize-observer-polyfill';

/* Workaround for fixing icons with webpack */
const DefaultIcon = L.icon({
  iconUrl: icon,
  shadowUrl: iconShadow
});

L.Marker.prototype.options.icon = DefaultIcon;

export class MarkerMap extends LitElement {
  static get styles() {
    return [
      css`
        :host {
          display: block;
          overflow: hidden;
          height: 100%;
          flex: 1;
        }
        #map {
          height: 100%;
        }
      `,
      css`
        ${unsafeCSS(leafletCss)}
      `
    ];
  }

  render() {
    return html`
      <div id="map"></div>
    `;
  }
  constructor() {
    super();
    this.markers = [];
  }

  firstUpdated(_changedProperties) {
    super.firstUpdated(_changedProperties);
    const apiTokenFromMapbox =
      "pk.eyJ1IjoiYXJ0dXItIiwiYSI6ImNqczR3ZmZjdTA2bG0zeXFrZzUyZWZzOG4ifQ.imLHDDke9wglhq-afKaLAg";
    this.initMap(apiTokenFromMapbox);
    document.body.addEventListener("persons-available", e => {
      this.showMarkers(e.detail.persons);
    });
    document.body.addEventListener("person-updated", e => {
      const markerIndex = this.markers.findIndex(marker => marker.person.id == e.detail.person.id);
      if (markerIndex >= 0) {
        const marker = this.markers[markerIndex];
        marker.person = e.detail.person;
        marker.setLatLng({lat: marker.person.latitude, lng: marker.person.longitude});
      }
    });
    document.body.addEventListener("person-selected", e => {
      const person = e.detail.person;
      const marker = this.markers.find(marker => marker.person.id == person.id);
      if (marker) {
        // Reset all other
        this.markers.forEach(marker => {
          marker.setStyle({ radius: 10, color: "#3388ff", fillColor: "#3388ff" });
        });
        marker.setStyle({ radius: 20, color: "#daa", fillColor: "#daa" });
        this.map.panTo(marker.getLatLng());

        marker.openPopup();
      }
    });
  }

  initMap(apiToken) {
    this.map = L.map(this.shadowRoot.querySelector("#map"));
    this.map.setView([35, -20], 3);
    L.tileLayer(
      "https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}",
      {
        attribution:
          'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
        maxZoom: 18,
        id: "mapbox.streets",
        accessToken: apiToken
      }
    ).addTo(this.map);

    const observer = new ResizeObserver(() => {
      this.map.invalidateSize();
    });
    observer.observe(this.map.getContainer());

  }
  showMarkers(persons) {
    // We only add persons at the end so we only need to add the last N persons of the array
    const newPersons = persons.slice(this.markers.length);
    newPersons.forEach(item => {
      const marker = L.circleMarker([item.latitude, item.longitude]);
      marker.person = item;
      this.markers.push(marker);
      marker.addTo(this.map);
      let content = `
        Person: <b> ${item.firstName} ${item.lastName}</b><p>
        Latitude: <b>${item.latitude.toFixed(1)}</b>
        <br>
        Longitude: <b>${item.longitude.toFixed(1)}</b>
              </p>`;
      marker.bindPopup(content);
      marker.on('click', e => {
        this.dispatchEvent(new CustomEvent("person-selected", { bubbles: true, detail: { person: marker.person } }));
      });
    });
  }

}
customElements.define("marker-map", MarkerMap)
