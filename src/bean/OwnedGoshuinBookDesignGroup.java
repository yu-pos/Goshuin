package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OwnedGoshuinBookDesignGroup implements Serializable {

	private RegdGoshuinBookDesignGroup goshuinBookDesignGroup;
	private  int userId;
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;
	public RegdGoshuinBookDesignGroup getGoshuinBookDesignGroup() {
		return goshuinBookDesignGroup;
	}
	public void setGoshuinBookDesignGroup(RegdGoshuinBookDesignGroup goshuinBookDesignGroup) {
		this.goshuinBookDesignGroup = goshuinBookDesignGroup;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
