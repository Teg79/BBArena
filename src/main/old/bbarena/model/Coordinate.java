/**
 * 
 */
package bbarena.model;

import java.io.Serializable;

/**
 * @author f.bellentani
 *
 */
public class Coordinate implements Serializable {

	private static final long serialVersionUID = 834310084349631868L;
	
	private int x = -1;
	private int y = -1;
	
	public Coordinate () {
		
	}
	
	public Coordinate (int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Coordinate getNext(Direction direction) {
		int x = this.getX();
		int y = this.getY();

		if(direction == Direction.NW) {
			y--;
			x--;
		} else if(direction == Direction.N) {
			y--;
		} else if(direction == Direction.NE) {
			y--;
			x++;
		} else if(direction == Direction.W) {
			x--;
		} else if(direction == Direction.E) {
			x++;
		} else if(direction == Direction.SW) {
			y++;
			x--;
		} else if(direction == Direction.S) {
			y++;
		} else if(direction == Direction.SE) {
			y++;
			x++;
		}
		
		return new Coordinate(x,y);
	}

    public int sqDistanceFrom(Coordinate c) {
        int dx = (x - c.x);
        int dy = (y - c.y);
        return  dx * dx + dy * dy; 
    }

    public float distanceFrom(Coordinate c) {
        return (float) Math.sqrt(sqDistanceFrom(c)); 
    }
    
    public int roundedDistanceFrom(Coordinate c) {
        return (int) distanceFrom(c); 
    }
    
    @Override
	protected Object clone() throws CloneNotSupportedException {
		return new Coordinate(x, y);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Coordinate that = (Coordinate) o;

		if (x != that.x) return false;
		return y == that.y;

	}

	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		return result;
	}

	@Override
	public String toString() {
		return "["+x+","+y+"]";
	}
	
	
}
