package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Rank implements Serializable {
	private int id; //ランクID
	private String name; //ランク名
	private int rankUpQuantity; //ランクアップ（商品券付与）に必要な御朱印数
	private LocalDateTime updatedAt; //データ更新日時
	private LocalDateTime createdAt; //データ登録日時

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
	public int getRankUpQuantity() {
		return rankUpQuantity;
	}
	public void setRankUpQuantity(int rankUpQuantity) {
		this.rankUpQuantity = rankUpQuantity;
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
