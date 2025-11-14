package mycafe.MyCafeWeb.model;

import java.io.Serializable;

public class Food extends MenuItem implements Serializable {
	
	public Food (String name, int price, String text) {
		super(name,price,text);
	}

	@Override
	public String getDetails() {
		return getName() +" "+ getPrice() + "円" +" "+ getText();
	}

}
