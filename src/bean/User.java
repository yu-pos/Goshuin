package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {

	private int id;

	private String userName;

	private String realName;

	private LocalDateTime birthDate;

	private String  address;

	private String telNumber;

	private String password;

	private GoshuinBook activeGoshuinBook ;

	private int point;

	private int rank;

	private int goshuinCount;

	private String profileImagePath;

	private GoshuinBook myGoshuinBook;

	private boolean isMyGoshuinBookPublic;

	private LocalDateTime lastLoginAt;

	private LocalDateTime updatedAt;

	private LocalDateTime createdAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public LocalDateTime getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDateTime birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public GoshuinBook getActiveGoshuinBook() {
		return activeGoshuinBook;
	}

	public void setActiveGoshuinBook(GoshuinBook activeGoshuinBook) {
		this.activeGoshuinBook = activeGoshuinBook;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getGoshuinCount() {
		return goshuinCount;
	}

	public void setGoshuinCount(int goshuinCount) {
		this.goshuinCount = goshuinCount;
	}

	public String getProfileImagePath() {
		return profileImagePath;
	}

	public void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}

	public GoshuinBook getMyGoshuinBook() {
		return myGoshuinBook;
	}

	public void setMyGoshuinBook(GoshuinBook myGoshuinBook) {
		this.myGoshuinBook = myGoshuinBook;
	}

	public boolean isMyGoshuinBookPublic() {
		return isMyGoshuinBookPublic;
	}

	public void setMyGoshuinBookPublic(boolean isMyGoshuinBookPublic) {
		this.isMyGoshuinBookPublic = isMyGoshuinBookPublic;
	}

	public LocalDateTime getLastLoginAt() {
		return lastLoginAt;
	}

	public void setLastLoginAt(LocalDateTime lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
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
