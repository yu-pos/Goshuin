package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OwnedGoshuinBookSticker implements Serializable {

	private RegdGoshuinBookSticker goshuinBookSticker;

	private int userId;

	private LocalDateTime updatedAt;

	private LocalDateTime createdAt;

	public RegdGoshuinBookSticker getGoshuinBookSticker() {
		return goshuinBookSticker;
	}

	public void setGoshuinBookSticker(RegdGoshuinBookSticker goshuinBookSticker) {
		this.goshuinBookSticker = goshuinBookSticker;
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
