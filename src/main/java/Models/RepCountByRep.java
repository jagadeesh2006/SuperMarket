package Models;

import java.time.LocalDate;

public class RepCountByRep {
	private LocalDate date;
	private int repId;
	private int Count;
	public RepCountByRep(LocalDate date, int repId, int count) {
		super();
		this.date = date;
		this.repId = repId;
		Count = count;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getRepId() {
		return repId;
	}
	public void setRepId(int repId) {
		this.repId = repId;
	}
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
}
