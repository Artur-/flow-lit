export class VisibilityTrigger {
  async connect(triggerElement, onVisible) {
    if (!("IntersectionObserver" in window)) {
      window.alert(
        "VisibilityTrigger requires a browser supporting IntersectionObserver (not IE11)"
      );
      throw "Browser not supported";
    }
    if (this.observer) {
      throw "Can only be connected once";
    }

    this.observer = new IntersectionObserver(entries => {
      const entry = entries[0];
      if (entry.isIntersecting) {
        onVisible(triggerElement);
      }
    });
    this.observer.observe(triggerElement);
  }
  disconnect() {
    if (this.observer) {
      this.observer.disconnect();
      delete this.observer;
    }
  }
}
