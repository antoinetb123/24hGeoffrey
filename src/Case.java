
public class Case {

	private boolean utilise;

	public Case() {
		utilise = false;
	}

	public boolean isUtilise() {
		return utilise;
	}

	public void setUtilise(boolean utilise) {
		this.utilise = utilise;
	}

	public String toString() {
		if (utilise)
			return "x";
		else
			return "0";
	}

}
