package bean;

import java.io.Serializable;
import java.time.LocalDateTime;


public class OwnedVoucher implements Serializable {

	 // 所持情報ID（主キー）
    private int id;

    private int voucherId;

    // 商品券内容
    private String description;

    // 画像パス
    private String imagePath;

    // 利用者ID（外部キー）
    private int userId;


    // 使用日時（nullは未使用を表す）
    private LocalDateTime usedAt;

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

	public int getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(int voucherId) {
		this.voucherId = voucherId;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public LocalDateTime getUsedAt() {
		return usedAt;
	}

	public void setUsedAt(LocalDateTime usedAt) {
		this.usedAt = usedAt;
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
