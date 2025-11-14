package bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ShrineAndTemple implements Serializable {

	private int id;

	private String name;

	private String address;

	private String description;

	private String areaInfo;

	private String mapLink;

	private String imagePath;

	private List<ShrineAndTempleTag> tagList;

	private LocalDateTime  updatedAt;

	private  LocalDateTime createdAt;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAreaInfo() {
		return areaInfo;
	}

	public void setAreaInfo(String areaInfo) {
		this.areaInfo = areaInfo;
	}

	public String getMapLink() {
		return mapLink;
	}

	public void setMapLink(String mapLink) {
		this.mapLink = mapLink;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	

	public List<ShrineAndTempleTag> getTagList() {
		return tagList;
	}

	public void setTagList(List<ShrineAndTempleTag> tagList) {
		this.tagList = tagList;
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
