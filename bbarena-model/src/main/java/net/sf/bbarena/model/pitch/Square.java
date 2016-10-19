package net.sf.bbarena.model.pitch;

import net.sf.bbarena.model.Choice;
import net.sf.bbarena.model.Coordinate;
import net.sf.bbarena.model.exception.SquareAlreadyOccupiedException;
import net.sf.bbarena.model.team.Player;
import net.sf.bbarena.model.team.Team;

/**
 * Square represents a single square on a Pitch
 *
 * @author f.bellentani
 */
public class Square implements Choice {

    public enum SquareType {
        NORMAL, RIGHT_WIDE_ZONE, LEFT_WIDE_ZONE, LOS, END_ZONE, OUT;

        public String toString() {
            String res = null;
            switch (this) {
                case NORMAL:
                    res = "N";
                    break;
                case RIGHT_WIDE_ZONE:
                case LEFT_WIDE_ZONE:
                    res = "W";
                    break;
                case LOS:
                    res = "L";
                    break;
                case END_ZONE:
                    res = "E";
                    break;
                case OUT:
                    res = "O";
                    break;
                default:
                    res = super.toString();
                    break;
            }
            return res;
        }
    }

    public static final double SQUARE_WIDTH = 2.90D;

    private Coordinate coords = new Coordinate();

    private Pitch pitch = null;

    private Ball ball = null;

    private Player player = null;

    private SquareType type = null;

    private Team teamOwner = null;

    private String special = null;

    public Square(Pitch pitch, Coordinate xy) {
        this(pitch, xy, SquareType.NORMAL, null);
    }

    public Square(Pitch pitch, Coordinate xy, SquareType type) {
        this(pitch, xy, type, null);
    }

    public Square(Pitch pitch, Coordinate xy, SquareType type, Team owner) {
        this.pitch = pitch;
        this.type = type;
        this.teamOwner = owner;
        if (xy != null) {
            this.coords = xy;
        }
    }

    public Pitch getPitch() {
        return this.pitch;
    }

    public boolean hasBall() {
        return this.ball != null;
    }

    public Ball getBall() {
        return this.ball;
    }

    public boolean hasPlayer() {
        return this.player != null;
    }

    public Coordinate getCoords() {
        return coords;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Set the player in the Square if the Square is empty
     *
     * @param player Player to place in the Square, if null the current Player in
     *               the Square (if exists) will be removed
     * @throws SquareAlreadyOccupiedException if the Square already has a Player
     */
    protected void setPlayer(Player player) {
        if (player == null) {
            removePlayer();
        } else {
            if (this.player != null) {
                throw new SquareAlreadyOccupiedException("The square "
                        + this.getCoords().toString()
                        + " is already occupied by another player!");
            } else {
                this.player = player;
            }
        }
    }

    protected void removePlayer() {
        this.player = null;
    }

    /**
     * Set the ball in the Square if the Square has no ball
     *
     * @param ball Ball to place in the Square, if null the ball in
     *             the Square (if exists) will be removed
     * @throws SquareAlreadyOccupiedException if the Square already has a Ball
     */
    protected void setBall(Ball ball) {
        if (ball == null) {
            removeBall();
        } else {
            if (this.ball != null) {
                throw new SquareAlreadyOccupiedException("The square "
                        + this.getCoords().toString()
                        + " is already occupied by another ball!");
            } else {
                this.ball = ball;
            }
        }
    }

    protected void removeBall() {
        this.ball = null;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public boolean isSpecial() {
        return (special == null ? false : true);
    }

    public SquareType getType() {
        return type;
    }

    public void setType(SquareType type) {
        this.type = type;
    }

    public Team getTeamOwner() {
        return teamOwner;
    }

    public void setTeamOwner(Team teamOwner) {
        this.teamOwner = teamOwner;
    }

    public String toString() {
        return hasPlayer() ? "X" : getType().toString();
    }

}
