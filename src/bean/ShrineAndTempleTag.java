package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ShrineAndTempleTag implements Serializable {

private int id;

private int tagTypeId;

private String name;

private String tagTypeName;

private LocalDateTime  updatedAt;

private  LocalDateTime createdAt;

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public int getTagTypeId() {
	return tagTypeId;
}

public void setTagTypeId(int tagTypeId) {
	this.tagTypeId = tagTypeId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getTagTypeName() {
	return tagTypeName;
}

public void setTagTypeName(String tagTypeName) {
	this.tagTypeName = tagTypeName;
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
