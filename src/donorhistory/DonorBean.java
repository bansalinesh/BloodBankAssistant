package donorhistory;

import java.time.LocalDate;

public class DonorBean 
{
	LocalDate date;
	String blood;
	public DonorBean() {}
	
	public DonorBean(LocalDate date, String blood) {
		super();
		this.date = date;
		this.blood = blood;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getBlood() {
		return blood;
	}

	public void setBlood(String blood) {
		this.blood = blood;
	}
}
