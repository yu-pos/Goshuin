package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RegdGoshuin implements Serializable {

	private int id; //御朱印ID
	private ShrineAndTemple shrineAndTemple; //神社仏閣情報(外部)
	private String saleStartDate; //販売開始日 mm-ddでデータを入れる
	private String saleEndDate; //販売終了日 mm-ddでデータを入れる
	private String imagePath; //画像パス
	private LocalDateTime updatedAt; //更新日時
	private LocalDateTime createdAt; //作成日時
}
