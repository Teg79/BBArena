package net.sf.bbarena.view.reporter;

public abstract class ReporterImpl implements IReporter {
	public abstract void eventCasualty();
	public abstract void eventMVP();
	public abstract void eventPassComplete();
	public abstract void eventPassIncomplete();
	public abstract void eventPassIntercepted();
	public abstract void eventTouchdown();
}
