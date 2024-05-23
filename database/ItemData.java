package application.database;

public class ItemData {
	private Integer id;
	private String name;
	private String price;
	private String category;
	private String image;
	
	public ItemData(Integer id,String name, String price, String category, String image) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
		this.image = image;
	}

	public String getName() {return name;}

	public String getPrice() {return price;}

	public String getCategory() {return category;}

	public Integer getId() {return id;}

	public String getImage() {return image;}
}
