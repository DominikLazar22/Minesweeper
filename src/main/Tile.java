package main;

public class Tile {

	private boolean mine;
	private int mineNeighours = 0;
	private boolean hidden = true;
	private boolean marked = false;
	
	public Tile(boolean mine) {
		this.mine = mine;
	}
	
	public boolean isMine() {
		return mine;
	}
	
	public boolean clicked() {
		hidden = false;
		if(mine) {
			return false;
		}else {			
			return true;
		}
	}

	public void setNeighbourCount(int sum) {
		mineNeighours = sum;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
	public int getNeighbours() {
		return mineNeighours;
	}
	
	public boolean isMarked() {
		return marked;
	}
	
	public void marked() {
		marked = !marked;
	}
	
}
