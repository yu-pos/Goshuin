package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RegdGoshuin implements Serializable {

	private int id; //御朱印ID
	private ShrineAndTemple shrineAndTemple; //神社仏閣情報(外部)
	private String description; //説明
	private String saleStartDate; //販売開始日 mm-ddでデータを入れる
	private String saleEndDate; //販売終了日 mm-ddでデータを入れる
	private String imagePath; //画像パス
	private LocalDateTime updatedAt; //更新日時
	private LocalDateTime createdAt; //作成日時

	private boolean isOwned = false; //対象の利用者がこの御朱印を持っているかどうか 購入御朱印選択画面で指定・使用する
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ShrineAndTemple getShrineAndTemple() {
		return shrineAndTemple;
	}
	public void setShrineAndTemple(ShrineAndTemple shrineAndTemple) {
		this.shrineAndTemple = shrineAndTemple;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSaleStartDate() {
		return saleStartDate;
	}
	public void setSaleStartDate(String saleStartDate) {
		this.saleStartDate = saleStartDate;
	}
	public String getSaleEndDate() {
		return saleEndDate;
	}
	public void setSaleEndDate(String saleEndDate) {
		this.saleEndDate = saleEndDate;
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
