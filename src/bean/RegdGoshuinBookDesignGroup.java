package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RegdGoshuinBookDesignGroup implements Serializable {

	//デザイングループID
	private int id;

	//デザイングループ名
	private String name;

	//更新日時
	private LocalDateTime updateAt;

	//登録日時
	private LocalDateTime createdAt;

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

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
