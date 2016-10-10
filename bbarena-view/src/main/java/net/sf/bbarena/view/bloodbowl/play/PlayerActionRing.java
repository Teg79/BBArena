package net.sf.bbarena.view.bloodbowl.play;

import java.awt.Component;
import java.util.List;

import net.sf.bbarena.view.*;
import net.sf.bbarena.view.util.*;

public final class PlayerActionRing extends ActionRingUi {

    public PlayerActionRing(Component owner) {
	super(owner);
    }
    
    @Override
    protected void createButtons(List<ActionButton> buttons) {
	buttons.add(new ActionButton( BitmapManager.loadImage("data/gfx/action/move.png", true)));
	buttons.add(new ActionButton( BitmapManager.loadImage("data/gfx/action/blitz.png", true)));
	buttons.add(new ActionButton( BitmapManager.loadImage("data/gfx/action/block.png", true)));
	buttons.add(new ActionButton( BitmapManager.loadImage("data/gfx/action/foul.png", true)));
	buttons.add(new ActionButton( BitmapManager.loadImage("data/gfx/action/pass.png", true)));
	buttons.add(new ActionButton( BitmapManager.loadImage("data/gfx/action/handoff.png", true)));
	buttons.add(new ActionButton( BitmapManager.loadImage("data/gfx/action/throw_team_mate.png", true)));
    }
}
