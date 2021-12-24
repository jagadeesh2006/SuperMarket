package Controller;

public enum ErrorCodes {
	NO_PRODUCTS_SELECTED(404, "No products selected!!!!"),
	NO_CUSTOMER_EXISTS(404, "CUSTOMER DOES NOT EXISTS!!!!"),
	NO_REPRESENTATIVE_EXISTS(404,"REPRESENTATIVE DOES NOT EXISTS!!!!"),
	DATABASE_ERROR(500, "A database error has occurred!!!!!"),
	MAXIMUM_LIMIT_EXCEEDED(608,"MAXIMUM STOCK QUANTY EXCEEDED!!!!"),
	NO_DATA_FOUND(404,"NO RECORDS FOUND!!!!");
	
	  private final int code;
	  private final String description;

	private ErrorCodes(int code, String description) {
	    this.code = code;
	    this.description = description;
	  }

	  public String getDescription() {
	     return description;
	  }

	  public int getCode() {
	     return code;
	  }

}
