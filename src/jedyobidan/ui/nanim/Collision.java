package jedyobidan.ui.nanim;

public class Collision {
	private Actor a, b;
	public Collision(Actor a, Actor b){
		this.a = a;
		this.b = b;
	}
	public Actor getOtherActor(Actor me){
		return a==me? b: a;
	}
	public Actor[] getActors(){
		return new Actor[]{a,b};
	}
	public boolean equals(Object o){
		try{
			Collision other = (Collision) o;
			return a == other.a && b == other.b || a == other.b && b == other.a;
		} catch (Exception e){
			return false;
		}
	}
	@Override
	public int hashCode() {
		return a.hashCode() + b.hashCode();
	}

}
