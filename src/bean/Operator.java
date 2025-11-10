package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Operator implements Serializable {

	private int id;
	private  String password;
	private  boolean isAdmin;
	private boolean firstLoginCompleted;
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
		return firstLoginCompleted;
	}
	public void setFirstLoginCompleted(boolean firstLoginCompleted) {
		this.firstLoginCompleted = firstLoginCompleted;
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
