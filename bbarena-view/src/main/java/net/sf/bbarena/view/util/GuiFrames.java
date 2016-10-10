package net.sf.bbarena.view.util;

public class GuiFrames {
    
    private GuiIcon[] frames;
    
    private int max;
    
    private int currentFrame;
       
    public GuiFrames(GuiIcon[] frames) {
	this.frames = frames;
	reset();
    }
    
    public GuiIcon getCurrentFrame() {
	return frames[currentFrame];
    }
 
    public GuiIcon getFrame(int frame) {
	return frames[frame];
    }

    public int getFrameCount() {
	return frames.length;
    }
 
    public void reset() {
	currentFrame = 0;
	max = 1;
    }
   
    public void setMaximum(int max) {
	reset();
	this.max = max;
    }

    public int getMaximum() {
	return max;
    }
    
    public boolean setCurrent(int current) {
	current = Math.min(max, current);
	currentFrame = current * frames.length / max;
	currentFrame = Math.max(0, Math.min(currentFrame, frames.length - 1));
	return current >= max;
    }
    
}
