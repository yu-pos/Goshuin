package bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class GoshuinBook implements Serializable {
	private int id; //御朱印帳ID
	private RegdGoshuinBookDesign goshuinBookDesign; //設定された表紙デザイン(外部)
	private List<GoshuinBookStickerAttachment> AttachedStickerList; //張り付けられたステッカーリスト(外部)
	private List<RegdGoshuin> goshuinList; //御朱印帳に登録されている御朱印リスト(外部)
	private LocalDateTime updatedAt; //データ更新日時
	private LocalDateTime createdAt; //データ登録日時

	public RegdGoshuinBookDesign getGoshuinBookDesign() {
		return goshuinBookDesign;
	}
	public void setGoshuinBookDesign(RegdGoshuinBookDesign goshuinBookDesign) {
		this.goshuinBookDesign = goshuinBookDesign;
	}
	public List<GoshuinBookStickerAttachment> getAttachedStickerList() {
		return AttachedStickerList;
	}
	public void setAttachedStickerList(List<GoshuinBookStickerAttachment> attachedStickerList) {
		AttachedStickerList = attachedStickerList;
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
