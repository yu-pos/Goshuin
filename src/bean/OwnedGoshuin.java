package bean;

import java.io.Serializable;

public class OwnedGoshuin implements Serializable {

	private RegdGoshuin goshuin;
	private int userId;
	private int goshuinBookId;

	public RegdGoshuin getGoshuin() {
		return goshuin;
	}
	public void setGoshuin(RegdGoshuin goshuin) {
		this.goshuin = goshuin;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getGoshuinBookId() {
		return goshuinBookId;
	}
	public void setGoshuinBookId(int goshuinBookId) {
		this.goshuinBookId = goshuinBookId;
	}

}
