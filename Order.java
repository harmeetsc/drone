import java.util.Date;

public class Order {
	public String getOrdId() {
		return ordId;
	}
	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Date getTimeOfOrder() {
		return timeOfOrder;
	}
	public void setTimeOfOrder(Date timeOfOrder) {
		this.timeOfOrder = timeOfOrder;
	}
	public Order(String ordId, Address address,  Date timeOfOrder) {
		this.ordId = ordId;
		this.address = address;
		this.timeOfOrder = timeOfOrder;
	}
	String ordId;
	Address address;
	Date timeOfOrder;

}
