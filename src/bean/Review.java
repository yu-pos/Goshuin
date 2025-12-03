package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Review implements Serializable {

	private int id;

	private int shrineAndTempleId;

	private int userId;

	private String userName;

	private String userImagePath;

	private String text;

	private String imagePath;

	private int likeCount;

	private LocalDateTime  updatedAt;

	private  LocalDateTime createdAt;

	private boolean isLiked; //ログイン中のユーザーがいいねを押してるかどうか

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserImagePath() {
		return userImagePath;
	}

	public void setUserImagePath(String userImagePath) {
		this.userImagePath = userImagePath;
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




	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
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

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}






}
