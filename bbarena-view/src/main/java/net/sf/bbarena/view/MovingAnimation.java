package net.sf.bbarena.view;

import java.awt.*;

import net.sf.bbarena.model.*;
import net.sf.bbarena.view.exception.*;
import net.sf.bbarena.view.util.*;

public class MovingAnimation extends Animation {
    
    private GuiFrames frames;
    private float distance;
    private float x;
    private float dx;
    private float y;
    private float dy;
    private int current;
    private int speed;
         
    public MovingAnimation(Component owner, Coordinate start, Coordinate end, GuiFrames frames, int speed) {
	super(owner);
	this.frames = frames;
	if (frames == null) {
	    throw new AnimationException("frames is null!");
	}
	this.speed = speed;
	if (speed <= 0) {
	    throw new AnimationException("speed <= 0!");
	}
	updateStartEnd(start, end);
    }
    
    public void updateStartEnd(Coordinate start, Coordinate end) {
	distance = start.distanceFrom(end);
	x = start.getX();
	dx = speed * (end.getX() - start.getX()) / distance; 
	x += (current * dx) / speed;
	y = start.getY();
	dy = speed * (end.getY() - start.getY()) / distance;
	y += (current * dy) / speed;
	frames.setMaximum((int)distance);
	frames.setCurrent(current);
    }
    
    @Override
    protected void paintAnimation(Graphics g) {
	Graphics2D g2 = (Graphics2D)g.create();
	frames.getCurrentFrame().paint(g2, (int)x, (int)y);
	g2.dispose();
    }

    @Override
    protected void update() {
	x += dx;
	y += dy;
	current += speed;
	if (frames.setCurrent(current)) {
	    stop();
	}
    }

}
