package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Review implements Serializable {

	private int id;
	
	private int shrineAndTempleId;
	
	private int userId;
	
	private String text;
	
	private String imagePath;
	
	private LocalDateTime  updatedAt;

	private  LocalDateTime createdAt;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getShrineAndTempleId() {
		return shrineAndTempleId;
	}

	public void setShrineAndTempleId(int shrineAndTempleId) {
		this.shrineAndTempleId = shrineAndTempleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
