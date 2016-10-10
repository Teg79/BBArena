package net.sf.bbarena.view.reporter;

import net.sf.bbarena.view.reporter.ReporterImpl;
import net.sf.bbarena.view.reporter.systray.SysTrayReporter;


public class TestReporter {	
	public static void main(String[] args) {
		ReporterImpl stReporter = new SysTrayReporter();
		
		stReporter.eventCasualty();
		stReporter.eventPassComplete();
		stReporter.eventTouchdown();
		
		stReporter = null;
	}

}
