import java.util.ArrayList;

public class Moi {

	private Position pos;
	ArrayList<Ennemi> ennemis;
	Choix[] chois = { Choix.DOWN, Choix.UP, Choix.RIGHT, Choix.LEFT };
	boolean[][] tab;
	private Position depart;
	private final static int nbRecursivite = 1;
	private int nbTour = 0;

	public Moi(boolean[][] tab) {
		ennemis = new ArrayList<>();
		this.tab = tab;
	}

	public ArrayList<Ennemi> getEnnemis() {
		return ennemis;
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;

	}

	public void setPosDepart(Position depart) {
		this.depart = new Position(depart.getPosX(), depart.getPosY());
		setPos(depart);

	}

	public ArrayList<Choix> addChoix() {
		ArrayList<Choix> choix = new ArrayList<>();
		for (Choix choi : chois) {
			choix.add(choi);
		}
		return choix;
	}

	public boolean ennemiIci(Position pos) {
		for (Ennemi ennemi : this.ennemis) {
			if (ennemi.getPos().equals(pos)) {
				return true;
			}
		}
		return false;
	}

	public boolean quelquUnPlusPlus1Autour(Position pos) {
		return ennemiIci(new Position(pos.getPosX() - 1, pos.getPosY()))
				|| ennemiIci(new Position(pos.getPosX() + 1, pos.getPosY()))
				|| ennemiIci(new Position(pos.getPosX(), pos.getPosY() - 1))
				|| ennemiIci(new Position(pos.getPosX(), pos.getPosY() + 1));
	}

	public void retireChoixLogique(ArrayList<Choix> choix) {
		retireChoixLogique(choix, pos, tab);
	}

	public void retireChoixLogique(ArrayList<Choix> choix, Position pos, boolean[][] tab) {
		if (pos.getPosX() < 0 || pos.getPosY() < 0 || pos.getPosY() > tab.length - 1
				|| pos.getPosX() > tab[0].length - 1) {
			choix.clear();
		}
		if (pos.getPosX() <= 0) {
			choix.remove(Choix.LEFT);
		} else if (pos.getPosX() >= tab[0].length - 1) {
			choix.remove(Choix.RIGHT);
		}
		if (pos.getPosY() <= 0) {
			choix.remove(Choix.UP);
		} else if (pos.getPosY() >= tab.length - 1) {
			choix.remove(Choix.DOWN);
		}

		if (choix.contains(Choix.RIGHT) && tab[pos.getPosY()][pos.getPosX() + 1]) {
			choix.remove(Choix.RIGHT);
		}
		if (choix.contains(Choix.LEFT) && tab[pos.getPosY()][pos.getPosX() - 1]) {
			choix.remove(Choix.LEFT);
		}
		if (choix.contains(Choix.UP) && tab[pos.getPosY() - 1][pos.getPosX()]) {
			choix.remove(Choix.UP);
		}
		if (choix.contains(Choix.DOWN) && tab[pos.getPosY() + 1][pos.getPosX()]) {
			choix.remove(Choix.DOWN);
		}
	}

	public ArrayList<Choix> retireChoixClassique(ArrayList<Choix> choix) {
		retireChoixLogique(choix);

		ArrayList<Choix> choixSecurise = new ArrayList<>();
		choixSecurise.addAll(choix);
		choixSecurise = rajouteSecuriteFaible(choixSecurise);
		if (choixSecurise.size() != 0) {
			System.out.println(choixSecurise);
			choix = choixSecurise;
		} else {
			System.out.println(choix);
			System.out.println("plus d'emplacement faible securise");
		}

		choixSecurise = new ArrayList<>();
		choixSecurise.addAll(choix);
		choixSecurise = rajouteSecurite(choixSecurise);
		if (choixSecurise.size() != 0) {
			choix = choixSecurise;
		}
		// System.out.println(choix);
		return choix;
	}

	public boolean quelquUnPlusPlus1(Choix choix) {
		switch (choix) {
		case LEFT:
			return ennemiIci(new Position(pos.getPosX() - 2, pos.getPosY()));
		case RIGHT:
			return ennemiIci(new Position(pos.getPosX() + 2, pos.getPosY()));
		case UP:
			return ennemiIci(new Position(pos.getPosX(), pos.getPosY() - 2));
		case DOWN:
			return ennemiIci(new Position(pos.getPosX(), pos.getPosY() + 2));
		default:
			break;
		}
		return false;
	}

	public boolean bloquant(Choix choix) {
		switch (choix) {
		case LEFT:
			return ((pos.getPosX() - 1 == 0 || tab[pos.getPosY()][pos.getPosX() - 2])
					&& (pos.getPosY() + 1 >= tab.length - 1 || tab[pos.getPosY() + 1][pos.getPosX() - 1])
					&& (pos.getPosY() - 1 <= 0 || tab[pos.getPosY() - 1][pos.getPosX() - 1]));
		case RIGHT:
			return ((pos.getPosX() + 1 == tab[0].length - 1 || tab[pos.getPosY()][pos.getPosX() + 2])
					&& (pos.getPosY() + 1 >= tab.length - 1 || tab[pos.getPosY() + 1][pos.getPosX() + 1])
					&& (pos.getPosY() - 1 <= 0 || tab[pos.getPosY() - 1][pos.getPosX() + 1]));
		case UP:
			return ((pos.getPosY() - 1 == 0 || tab[pos.getPosY() - 2][pos.getPosX()])
					&& (pos.getPosX() - 1 <= 0 || tab[pos.getPosY() - 1][pos.getPosX() - 1])
					&& (pos.getPosX() + 1 >= tab[0].length - 1 || tab[pos.getPosY() - 1][pos.getPosX() + 1]));
		case DOWN:
			return ((pos.getPosY() + 1 == tab.length - 1 || tab[pos.getPosY() + 2][pos.getPosX()])
					&& (pos.getPosX() - 1 <= 0 || tab[pos.getPosY() + 1][pos.getPosX() - 1])
					&& (pos.getPosX() + 1 >= tab[0].length - 1 || tab[pos.getPosY() + 1][pos.getPosX() + 1]));
		default:
			break;
		}
		return false;
	}

	public ArrayList<Choix> rajouteSecuriteFaible(ArrayList<Choix> choix) {
		if (ennemis.size() != 0)
			System.out.println(choix + "" + ennemis.get(0) + ennemis.get(1) + ennemis.get(2) + pos);
		if (choix.contains(Choix.DOWN) && (quelquUnPlusPlus1(Choix.DOWN) || bloquant(Choix.DOWN))) {
			choix.remove(Choix.DOWN);
		}
		if (choix.contains(Choix.UP) && (quelquUnPlusPlus1(Choix.UP) || bloquant(Choix.UP))) {
			choix.remove(Choix.UP);
		}
		if (choix.contains(Choix.RIGHT) && (quelquUnPlusPlus1(Choix.RIGHT) || bloquant(Choix.RIGHT))) {
			choix.remove(Choix.RIGHT);
		}
		if (choix.contains(Choix.LEFT) && (quelquUnPlusPlus1(Choix.LEFT) || bloquant(Choix.LEFT))) {
			choix.remove(Choix.LEFT);
		}
		return choix;

	}

	public ArrayList<Choix> rajouteSecurite(ArrayList<Choix> choix) {
		if (choix.contains(Choix.DOWN) && quelquUnPlusPlus1Autour(new Position(pos.getPosX(), pos.getPosY() + 1))) {
			choix.remove(Choix.DOWN);
		}
		if (choix.contains(Choix.UP) && quelquUnPlusPlus1Autour(new Position(pos.getPosX(), pos.getPosY() - 1))) {
			choix.remove(Choix.UP);
		}
		if (choix.contains(Choix.RIGHT) && quelquUnPlusPlus1Autour(new Position(pos.getPosX() + 1, pos.getPosY()))) {
			choix.remove(Choix.RIGHT);
		}
		if (choix.contains(Choix.LEFT) && quelquUnPlusPlus1Autour(new Position(pos.getPosX() - 1, pos.getPosY()))) {
			choix.remove(Choix.LEFT);
		}
		return choix;
	}

	public Choix pseudoChoix() {
		ArrayList<Choix> choix = addChoix();
		choix = retireChoixClassique(choix);
		// System.out.println(choix);
		if (depart.getPosX() == 0) {
			if (choix.contains(Choix.RIGHT)) {
				return Choix.RIGHT;
			} else if (choix.contains(Choix.DOWN)) {
				return Choix.DOWN;
			} else if (choix.contains(Choix.LEFT)) {
				return Choix.LEFT;
			} else
				return Choix.UP;

		} else if (depart.getPosX() == tab[0].length - 1) {
			if (choix.contains(Choix.LEFT)) {
				return Choix.LEFT;
			} else if (choix.contains(Choix.UP)) {
				return Choix.UP;
			} else if (choix.contains(Choix.RIGHT)) {
				return Choix.RIGHT;
			} else
				return Choix.DOWN;
		} else if (depart.getPosY() == 0) {
			if (choix.contains(Choix.DOWN)) {
				return Choix.DOWN;
			} else if (choix.contains(Choix.LEFT)) {
				return Choix.LEFT;
			} else if (choix.contains(Choix.UP)) {
				return Choix.UP;
			} else
				return Choix.RIGHT;
		} else if (depart.getPosY() == tab.length - 1) {
			if (choix.contains(Choix.UP)) {
				return Choix.UP;

			} else if (choix.contains(Choix.RIGHT)) {
				return Choix.RIGHT;
			} else if (choix.contains(Choix.DOWN)) {
				return Choix.DOWN;
			} else {
				return Choix.LEFT;
			}
		}
		return null;
	}

	public void bfs(boolean[][] tab, int x, int y, int[][] nvTabDistance, int compteur) {
		if (x < 0 || y < 0 || y > tab.length - 1 || x > tab[0].length - 1 || (tab[y][x] && compteur != 0)) {
			return;
		}
		compteur++;
		nvTabDistance[y][x] = compteur;
		tab[y][x] = true;
		bfs(tab, x + 1, y, nvTabDistance, compteur);
		bfs(tab, x - 1, y, nvTabDistance, compteur);
		bfs(tab, x, y + 1, nvTabDistance, compteur);
		bfs(tab, x, y - 1, nvTabDistance, compteur);

	}

	public int minDistance(int x, int y, ArrayList<int[][]> tabDistances) {
		int min = Integer.MAX_VALUE;
		for (int[][] tab : tabDistances) {
			if (tab[y][x] != 0 && tab[y][x] < min) {
				min = tab[y][x];
			}
		}
		return min;
	}

	public int calculPoid(boolean[][] tab2, Position maPos, ArrayList<Ennemi> ennemisClone) {
		ArrayList<int[][]> tabDistances = new ArrayList<int[][]>();
		boolean[][] tab;
		int[][] tabDistance;
		int point = 0;
		for (Ennemi ennemi : ennemisClone) {
			tab = Partie.cloneArray(tab2);
			tabDistance = new int[tab.length][tab[0].length];
			bfs(tab, ennemi.getPos().getPosX(), ennemi.getPos().getPosY(), tabDistance, 0);
			tabDistances.add(tabDistance);
		}
		tab = Partie.cloneArray(tab2);
		tabDistance = new int[tab2.length][tab2[0].length];
		bfs(tab, maPos.getPosX(), maPos.getPosY(), tabDistance, 0);
		for (int y = 0; y < tab.length; y++) {
			for (int x = 0; x < tab[y].length; x++) {
				if (tabDistance[y][x] != 0 && tabDistance[y][x] < minDistance(x, y, tabDistances)) {
					point++;
				}
			}
		}

		return point;

	}

	public Choix mesChoix() {
		boolean[][] tab;
		ArrayList<Ennemi> ennemis = new ArrayList<>();
		ennemis.addAll(this.ennemis);
		ArrayList<Choix> choix = addChoix();
		retireChoixLogique(choix);
		Position pos;
		int poidDuChoix = Integer.MIN_VALUE;
		int meilleur_poid = Integer.MIN_VALUE;
		Choix meilleur_Choix = null;
		for (Choix choi : choix) {
			pos = new Position(this.pos);
			tab = Partie.cloneArray(this.tab);
			deplace(pos, choi, tab);
			poidDuChoix = cherche(ennemis, tab, pos, new ArrayList<>(), 0);
			// System.out.println("poid des choix" + poidDuChoix + " choix : " + choi);
			if (meilleur_poid <= poidDuChoix) {
				meilleur_poid = poidDuChoix;
				meilleur_Choix = choi;
			}
		}
		// System.out.println("poid trouve par minimax" + meilleur_poid);
		return meilleur_Choix;
	}

	public Choix minimax() {
		ArrayList<Ennemi> ennemis = new ArrayList<>();
		ennemis.addAll(this.ennemis);

		return mesChoix();
	}

	public int mesChoix2(boolean[][] tab, Position maPos, ArrayList<Ennemi> ennemisClone, int recursiviteEnCours) {
		ArrayList<Choix> choix = addChoix();
		retireChoixLogique(choix, maPos, tab);
		Position pos;
		boolean[][] tabClone;
		int poidDuChoix = Integer.MIN_VALUE;
		int tampon = Integer.MAX_VALUE;

		for (Choix choi : choix) {
			pos = new Position(maPos);
			tabClone = Partie.cloneArray(tab);
			// System.out.println(pos + " , " + choix);
			deplace(pos, choi, tabClone);
			if (recursiviteEnCours == nbRecursivite) {
				nbTour++;
				tampon = calculPoid(tabClone, pos, ennemisClone);
				if (tampon > poidDuChoix) {
					poidDuChoix = tampon;
				}
			} else {
				tampon = cherche(ennemisClone, tab, pos, new ArrayList<>(), recursiviteEnCours + 1);
				if (poidDuChoix <= tampon) {
					poidDuChoix = tampon;
				}
			}
		}
		return poidDuChoix;
	}

	public int cherche(ArrayList<Ennemi> ennemis, boolean[][] tab, Position maPos, ArrayList<Ennemi> ennemisClone,
			int recursiviteEnCours) {
		if (ennemis.isEmpty()) {
			return mesChoix2(tab, maPos, ennemisClone, recursiviteEnCours);

		}
		int poidMin = Integer.MAX_VALUE;
		int poidTrouve;
		Ennemi ennemiClone = new Ennemi(new Position(ennemis.get(0).getPos()));
		Ennemi ennemiRemove = null;
		ArrayList<Choix> choix = addChoix();
		retireChoixLogique(choix, ennemiClone.getPos(), tab);
		if (choix.size() == 0) {
			ennemiRemove = ennemis.remove(0);
			tab = Partie.cloneArray(tab);
			ennemisClone.add(ennemiClone);
			poidTrouve = cherche(ennemis, tab, maPos, ennemisClone, recursiviteEnCours);
			if (poidTrouve < poidMin)
				poidMin = poidTrouve;
			ennemisClone.remove(ennemiClone);
			ennemis.add(0, ennemiRemove);
		}
		for (Choix choi : choix) {
			ennemiClone = new Ennemi(new Position(ennemis.get(0).getPos()));
			ennemiRemove = ennemis.remove(0);
			tab = Partie.cloneArray(tab);
			deplace(ennemiClone.getPos(), choi, tab);
			ennemisClone.add(ennemiClone);
			poidTrouve = cherche(ennemis, tab, maPos, ennemisClone, recursiviteEnCours);
			ennemisClone.remove(ennemiClone);
			if (poidTrouve < poidMin)
				poidMin = poidTrouve;
			ennemis.add(0, ennemiRemove);
		}
		return poidMin;

	}

	public void afficheTab(boolean[][] tab) {
		for (int y = 0; y < tab.length; y++) {
			for (int x = 0; x < tab[y].length; x++) {
				// System.out.print(tab[y][x] ? "0" : ".");
			}
			// System.out.println();
		}
	}

	public void deplace(Position position, Choix choix, boolean[][] tableau) {
		switch (choix) {
		case UP:
			position.setPosY(position.getPosY() - 1);
			break;
		case DOWN:
			position.setPosY(position.getPosY() + 1);
			break;
		case RIGHT:
			position.setPosX(position.getPosX() + 1);
			break;
		case LEFT:
			position.setPosX(position.getPosX() - 1);
			break;
		}
		tableau[position.getPosY()][position.getPosX()] = true;
	}

	public Choix choix() {

		ArrayList<Choix> choix = addChoix();
		retireChoixLogique(choix);
		Choix choixRandom;
		if (choix.size() == 0) {
			// System.out.println("choix naze" + this.pos);
			afficheTab(tab);
			this.pos.changePos(Choix.DOWN);

			return Choix.DOWN;
		} else {
			// choixRandom=choix.get((int)(Math.random() * choix.size()));
			// choixRandom = pseudoChoix();
			choixRandom = minimax();
			System.out.println(nbTour);
			nbTour = 0;
		}
		this.pos.changePos(choixRandom);

		return choixRandom;

	}

}
