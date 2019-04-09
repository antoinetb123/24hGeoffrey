import java.util.ArrayList;

public class Partie {
	Moi moi;
	boolean[][] tab;
	Position tailleMax;

	public Partie() {
	}

	public void recupere1emessage(String premierMessage) {
		tailleMax = lisTaille(premierMessage);
		tab = new boolean[tailleMax.getPosY()][tailleMax.getPosX()];
		for (int y = 0; y < tab.length; y++) {
			tab[y] = new boolean[tailleMax.getPosX()];
			for (int x = 0; x < tab[y].length; x++) {
				tab[y][x] = false;
			}

		}
		placePositionDepart();
		moi = new Moi(tab);
		Position depart = getPositionDepart(premierMessage);
		moi.setPosDepart(depart);
		tab[depart.getPosY()][depart.getPosX()] = true;
	}

	public void placePositionDepart() {
		tab[tab.length / 2][0] = true;
		tab[0][tab[0].length / 2] = true;
		tab[tab.length - 1][tab[0].length / 2] = true;
		tab[tab.length / 2][tab[0].length - 1] = true;
	}

	public Position lisTaille(String premierMessage) {
		String[] tabTaille = premierMessage.split(";");
		String taille[] = tabTaille[0].split(":");
		return new Position(taille[0], taille[1]);
	}

	public Position getPositionDepart(String premierMessage) {
		String[] tabTaille = premierMessage.split(";");
		String taille[] = tabTaille[1].split(":");
		return new Position(taille[0], taille[1]);
	}

	public void traiteMessage(String message) {
		ArrayList<Position> poss = lisMessage(message);
		int compteur = 0;
		if (moi.getEnnemis().size() == 0) {
			for (Position pos : poss) {
				if (!pos.equals(moi.getPos())) {
					moi.getEnnemis().add(new Ennemi(pos));
				}
			}
		}
		for (Position pos : poss) {
			if (!pos.equals(moi.getPos())) {
				moi.getEnnemis().get(compteur).setPos(pos);
				compteur++;
			}

		}
		for (Position position : poss) {
			if (position.getPosY() < tailleMax.getPosY() && position.getPosX() < tailleMax.getPosX()
					&& position.getPosY() >= 0 && position.getPosX() >= 0)
				tab[position.getPosY()][position.getPosX()] = true;
		}
		// afficheTab(tab);
	}

	public void afficheTab(boolean[][] tab) {
		for (int y = 0; y < tab.length; y++) {
			for (int x = 0; x < tab[y].length; x++) {
				// System.out.print(tab[y][x] ? "x" : "0");
			}
			// System.out.println();
		}
	}

	public ArrayList<Position> lisMessage(String message) {
		ArrayList<Position> poss = new ArrayList<>();
		for (String pos : message.split(";")) {
			poss.add(new Position(pos.split(":")[0], pos.split(":")[1]));
		}
		return poss;
	}

	public String choix() {
		Choix choix = moi.choix();
		// System.out.println(choix);
		return choix.toString();
	}

	/**
	 * Clones the provided array
	 * 
	 * @param src
	 * @return a new clone of the provided array
	 */
	public static boolean[][] cloneArray(boolean[][] src) {
		int length = src.length;
		boolean[][] target = new boolean[length][src[0].length];
		for (int i = 0; i < length; i++) {
			System.arraycopy(src[i], 0, target[i], 0, src[i].length);
		}
		return target;
	}

}
