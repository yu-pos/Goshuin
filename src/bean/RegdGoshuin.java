package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RegdGoshuin implements Serializable {

	private int id;
	private ShrineAndTemple shrineAndTemple;
	private String saleStartDate;
	private String saleEndDate;
	private String imagePath;
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;
}
