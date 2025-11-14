package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FavoriteShrineAndTemple implements Serializable {


private int shrineAndTempleId;
private int userId;
private LocalDateTime updatedAt;
private LocalDateTime createdAt;
public int getShrineAndTempleId() {
	return shrineAndTempleId;
}
public void setShrineAndTempleId(int shrineAndTempleId) {
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
