package mycafe.MyCafeWeb.model;

import java.io.Serializable;

public class Drink extends MenuItem implements Serializable {
	private boolean isHot;
	
	public Drink(String name, int price, boolean isHot, String text) {
		super(name, price, text);
		this.isHot = isHot;	
	}

	@Override
	public String getDetails() {
		return getName() + "(" + (isHot ? "Hot" : "ice") + ") - " + getPrice() + "円";
		}
	
}
