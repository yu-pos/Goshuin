package bean;

import java.io.Serializable;
import java.time.LocalDateTime;


public class RegdVoucher implements Serializable {

	 // 商品券ID（主キー）
    private int id;

    // 商品券内容
    private String description;

    // 画像パス
    private String imagePath;

    // 更新日時
    private LocalDateTime updatedAt;

    // 登録日時
    private LocalDateTime createdAt;

    // ゲッターとセッター
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
