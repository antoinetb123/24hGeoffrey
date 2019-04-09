
public class Ennemi {
	private Position pos;

	public Ennemi(Position pos) {
		super();
		this.pos = pos;
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	@Override
	public String toString() {
		return "Ennemi [pos=" + pos + "]";
	}

}
