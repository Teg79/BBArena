package net.sf.bbarena.view;

import java.awt.Dimension;
import java.awt.Graphics2D;

import net.sf.bbarena.model.pitch.Ball;
import net.sf.bbarena.view.util.GuiFrames;
import net.sf.bbarena.view.util.GuiIcon;

public class BallUi extends Draggable {

    private Ball ball;
    private GuiIcon draggedIcon;
    private GuiIcon onFieldIcon;
    private GuiIcon carriedIcon;
    private GuiFrames passFrames;
    private GuiFrames scatterFrames;
    private GuiFrames throwInFrames;
        
    public BallUi(Ball ball, GuiIcon draggedIcon, GuiIcon onFieldIcon, GuiIcon carriedIcon, GuiFrames passFrames, GuiFrames scatterFrames, GuiFrames throwInFrames) {	
	this.ball = ball;
	this.draggedIcon = draggedIcon;
	this.onFieldIcon = onFieldIcon;
	this.carriedIcon = carriedIcon;
	this.passFrames = passFrames;
	this.scatterFrames = scatterFrames;
	this.throwInFrames = throwInFrames;
	setIsVisible(false);
    }
    
    public Ball getBall() {
	return ball;
    }
    
    public GuiFrames getPassFrames() {
	return passFrames;
    }

    public GuiFrames getScatterFrames() {
	return scatterFrames;
    }
    
    public GuiFrames getThrowInFrames() {
	return throwInFrames;    
    }
    
    @Override
    public void paintDragged(Graphics2D g, int x, int y) {
	draggedIcon.paint(g, x, y);
    }

    @Override
    public Dimension getSize() {
	return carriedIcon.getSize();
    }

    @Override
    public void paint(Graphics2D g, int x, int y) {
	GuiIcon icon = onFieldIcon;
	if(ball.getOwner() != null) {
	    icon = carriedIcon;
	}
	icon.paint(g, x, y);
    }

}
