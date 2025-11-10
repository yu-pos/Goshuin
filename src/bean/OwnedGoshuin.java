package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OwnedGoshuin implements Serializable {

	private RegdGoshuin goshuin; //御朱印情報(外部)
	private int userId; //所持利用者
	private int goshuinBookId; //登録御朱印帳ID(外部キー)
	private LocalDateTime updatedAt; //更新日時
	private LocalDateTime createdAt; //作成日時

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
