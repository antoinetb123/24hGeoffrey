
public class Position {
	public int posX;
	public int posY;

	public Position(String posX, String posY) {
		super();
		this.posX = Integer.parseInt(posX);
		this.posY = Integer.parseInt(posY);
	}
	public Position(Position pos) {
		this.posX = pos.getPosX();
		this.posY =pos.getPosY();
	}
	public Position(int posX2, int posY2) {
		this.posX = posX2;
		this.posY =posY2;
	}

	public int getPosX() {
		return posX;
	}

	@Override
	public String toString() {
		return "posX=" + posX + ", posY=" + posY;
	}

	public void setPosX(String posX) {
		this.posX = Integer.parseInt(posX);
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(String posY) {
		this.posY = Integer.parseInt(posY);
	}

	public void changePos(Choix direction) {
		switch (direction) {
		case DOWN:
			this.posY++;
			break;
		case UP:
			this.posY--;
			break;
		case LEFT:
			this.posX--;
			break;
		case RIGHT:
			this.posX++;
			break;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + posX;
		result = prime * result + posY;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (posX != other.posX)
			return false;
		if (posY != other.posY)
			return false;
		return true;
	}

	public void setPosY(int posY) {
		this.posY=posY;
		
	}
	public void setPosX(int posX) {
		this.posX=posX;
		
	}
}
