package mycafe.MyCafeWeb.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VisitRecord implements Serializable {
	
	private LocalDate visitDate ;
	private List<MenuItem> orderedItems;
	private String comment;
	
	public VisitRecord(LocalDate visitDate) {
		this.visitDate = visitDate;
		this.orderedItems = new ArrayList<>();
		this.comment = "";
	}
	
	public LocalDate getVisitDate() {
		return visitDate;
	}
	
	public List<MenuItem> getOrderedItems() {
		return orderedItems;
	}
	
	public void addMenuItem(MenuItem item) {
		this.orderedItems.add(item);
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
