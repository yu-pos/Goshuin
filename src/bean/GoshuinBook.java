package bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class GoshuinBook implements Serializable {
	private int id; //御朱印帳ID
	private int userId; //利用者ID(外部)
	private RegdGoshuinBookDesign goshuinBookDesign; //設定された表紙デザイン
	private List<GoshuinBookStickerAttachment> attachedStickerList; //張り付けられたステッカーリスト(外部)
	private List<RegdGoshuin> goshuinList; //御朱印帳に登録されている御朱印リスト(外部)
	private LocalDateTime updatedAt; //データ更新日時
	private LocalDateTime createdAt; //データ登録日時


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public RegdGoshuinBookDesign getGoshuinBookDesign() {
		return goshuinBookDesign;
	}
	public void setGoshuinBookDesign(RegdGoshuinBookDesign goshuinBookDesign) {
		this.goshuinBookDesign = goshuinBookDesign;
	}
	public List<GoshuinBookStickerAttachment> getAttachedStickerList() {
		return attachedStickerList;
	}
	public void setAttachedStickerList(List<GoshuinBookStickerAttachment> attachedStickerList) {
		this.attachedStickerList = attachedStickerList;
	}

	//貼付ステッカーリストに情報を追加
	public void addAttachedStickerList(GoshuinBookStickerAttachment attachedSticker) {
		List<GoshuinBookStickerAttachment> list = this.attachedStickerList;
		list.add(attachedSticker);
		this.attachedStickerList = list;
	}

	public List<RegdGoshuin> getGoshuinList() {
		return goshuinList;
	}
	public void setGoshuinList(List<RegdGoshuin> goshuinList) {
		this.goshuinList = goshuinList;
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
