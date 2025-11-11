package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Operator implements Serializable {

	private int id;
	private  String password;
	private  boolean isAdmin;
	private boolean isFirstLoginCompleted;
	private boolean isEnable;
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public boolean isFirstLoginCompleted() {
		return isFirstLoginCompleted;
	}
	public void setFirstLoginCompleted(boolean isFirstLoginCompleted) {
		this.isFirstLoginCompleted = isFirstLoginCompleted;
	}
	public boolean isEnable() {
		return isEnable;
	}
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
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