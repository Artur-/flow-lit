package org.vaadin.artur.lit.ai;

import java.util.Random;

import org.vaadin.artur.lit.data.Person;
import org.vaadin.artur.lit.data.PersonService;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventBus;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;

public class PersonMover implements Runnable {

	private static PersonMover INSTANCE;

	private ComponentEventBus eventBus = new ComponentEventBus(new Div());

	private Random r = new Random();

	public static synchronized PersonMover get() {
		if (INSTANCE == null) {
			INSTANCE = new PersonMover();
			new Thread(INSTANCE).start();
		}

		return INSTANCE;
	}

	public static class PersonChangeEvent extends ComponentEvent<Div> {

		private int personId;

		public PersonChangeEvent(int personId) {
			super(new Div(), false);
			this.personId = personId;
		}

		public int getPersonId() {
			return personId;
		}
	}

	@Override
	public void run() {
		PersonService service = PersonService.get();
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int persons = service.getCount();
			for (int i = 0; i < persons/3; i++) {
				int index = random(0, persons);
				Person randomPerson = service.get(index, 1).get(0);
				service.move(randomPerson.getId(), random(-2.0, 2.0), random(-2.0, 2.0));
				try {
					eventBus.fireEvent(new PersonChangeEvent(randomPerson.getId()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	private double random(double from, double to) {
		return r.nextDouble() * (to - from) + from;
	}

	private int random(int from, int to) {
		return r.nextInt(to - from) + from;
	}

	public Registration addListener(ComponentEventListener<PersonChangeEvent> listener) {
		return eventBus.addListener(PersonChangeEvent.class, listener);
	}

}
