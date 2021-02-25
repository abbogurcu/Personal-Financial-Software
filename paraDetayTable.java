package application;

public class paraDetayTable {
	String currencyName;
	Double currencyAmount,toBuyingTL,toSellingTL,crossrate;
	
	
	public paraDetayTable(String currencyName, Double currencyAmount, Double toBuyingTL, Double toSellingTL,
			Double crossrate) {
		super();
		this.currencyName = currencyName;
		this.currencyAmount = currencyAmount;
		this.toBuyingTL = toBuyingTL;
		this.toSellingTL = toSellingTL;
		this.crossrate = crossrate;
	}


	public String getCurrencyName() {
		return currencyName;
	}


	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}


	public Double getCurrencyAmount() {
		return currencyAmount;
	}


	public void setCurrencyAmount(Double currencyAmount) {
		this.currencyAmount = currencyAmount;
	}


	public Double getToBuyingTL() {
		return toBuyingTL;
	}


	public void setToBuyingTL(Double toBuyingTL) {
		this.toBuyingTL = toBuyingTL;
	}


	public Double getToSellingTL() {
		return toSellingTL;
	}


	public void setToSellingTL(Double toSellingTL) {
		this.toSellingTL = toSellingTL;
	}


	public Double getCrossrate() {
		return crossrate;
	}


	public void setCrossrate(Double crossrate) {
		this.crossrate = crossrate;
	}
	
	
}
