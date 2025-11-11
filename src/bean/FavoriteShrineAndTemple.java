package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FavoriteShrineAndTemple implements Serializable {


private ShrineAndTemple shrineAndTempleId;
private int userId;
private LocalDateTime updatedAt;
private LocalDateTime createdAt;
public ShrineAndTemple getShrineAndTempleId() {
	return shrineAndTempleId;
}
public void setShrineAndTempleId(ShrineAndTemple shrineAndTempleId) {
	this.shrineAndTempleId = shrineAndTempleId;
}
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
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
