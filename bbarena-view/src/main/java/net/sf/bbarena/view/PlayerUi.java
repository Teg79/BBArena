package net.sf.bbarena.view;

import java.awt.*;

import net.sf.bbarena.model.team.*;
import net.sf.bbarena.view.util.*;

public class PlayerUi extends Draggable {

    private Player player;
    
    private GuiIcon icon;
    
    public PlayerUi( Player player, GuiIcon icon ) {
	this.player = player;
	this.icon = icon;
    }
    
    public Player getPlayer() {
	return player;
    }
    
    @Override
    public Dimension getSize() {
	return icon.getSize();
    }
    
    @Override
    public void paint( Graphics2D g, int x, int y ) {
	if ( getIsDragged() ) {
            Composite c = setAlpha( g, 0.5f );
            icon.paint( g, x, y );
            g.setComposite( c );
	} else {
	    icon.paint( g, x, y );
	}
    }

    @Override
    public void paintDragged( Graphics2D g, int x, int y ) {
	icon.paint( g, x, y );
    }
}
