package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RegdGoshuinBookDesignGroup implements Serializable {

	//デザイングループID
	private int id;

	//デザイングループ名
	private String name;

	//サムネイル画像
	private String imagePath;

	//更新日時
	private LocalDateTime updatedAt;

	//登録日時
	private LocalDateTime createdAt;

	//対象の利用者が所持しているかどうか
	private boolean isOwned;



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public boolean isOwned() {
		return isOwned;
	}

	public void setOwned(boolean isOwned) {
		this.isOwned = isOwned;
	}


}
