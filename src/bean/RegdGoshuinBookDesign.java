package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RegdGoshuinBookDesign implements Serializable {

	//デザインID
	private int id;

	//デザイングループID
	private int goshuinBookDesignGroupId;

	//デザイン名(色の名前を想定している)
	private String name;

	//デザイン画像パス
	private String imagePath;

	//更新日時
	private LocalDateTime updatedAt;

	//登録日時
	private LocalDateTime createdAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGoshuinBookDesignGroupId() {
		return goshuinBookDesignGroupId;
	}

	public void setGoshuinBookDesignGroupId(int goshuinBookDesignGroupId) {
		this.goshuinBookDesignGroupId = goshuinBookDesignGroupId;
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


}
