package bean;

import java.time.LocalDateTime;

public class GoshuinBookStickerAttachment {

	private int goshuinBookId;

	private RegdGoshuinBookSticker goshuinBookSticker;

	private double xPos;

	private double yPos;

	private double rotation;

	private LocalDateTime updatedAt;

	private LocalDateTime createdAt;

	public int getGoshuinBookId() {
		return goshuinBookId;
	}

	public void setGoshuinBookId(int goshuinBookId) {
		this.goshuinBookId = goshuinBookId;
	}

	public RegdGoshuinBookSticker getGoshuinBookSticker() {
		return goshuinBookSticker;
	}

	public void setGoshuinBookSticker(RegdGoshuinBookSticker goshuinBookSticker) {
		this.goshuinBookSticker = goshuinBookSticker;
	}

	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
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
