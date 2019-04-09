
public enum Choix {
	DOWN("DOWN"),UP("UP"),RIGHT("RIGHT"),LEFT("LEFT");
	private String name = "";
	   
	Choix(String name){
    this.name = name;
	}
   
  public String toString(){
    return name;
  }
}
