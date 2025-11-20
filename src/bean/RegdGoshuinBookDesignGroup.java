package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RegdGoshuinBookDesignGroup implements Serializable {

	//デザイングループID
	private int id;

	//デザイングループ名
	private String name;

	//更新日時
	private LocalDateTime updatedAt;

	//登録日時
	private LocalDateTime createdAt;

	//対象の利用者が所持しているかどうか
	private boolean isOwned;

	//regdGoshuinDesign型のリストを追加するかも

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
