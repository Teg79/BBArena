package net.sf.bbarena.view;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import net.sf.bbarena.model.pitch.Square;

public interface GuiState {
    public void prepare();
    public void enter();
    public void exit();
    public void paint(Graphics g);
    public void pitchLayoutChanged();
    public void mouseExitedSquare(Square square);
    public void mouseEnterSquare(Square square);
    public void mouseClickedSquare(Square square, MouseEvent e);
    public void mousePressedSquare(Square square, MouseEvent e);
    public void mouseReleasedSquare(Square square, MouseEvent e);
    public void mouseMoved(MouseEvent e);
}
