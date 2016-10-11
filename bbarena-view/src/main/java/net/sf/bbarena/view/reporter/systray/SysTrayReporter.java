package net.sf.bbarena.view.reporter.systray;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;

import net.sf.bbarena.view.reporter.ReporterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SysTrayReporter extends ReporterImpl {

	private static final Logger log = LoggerFactory.getLogger(SysTrayReporter.class);

	private TrayIcon trayIcon;
	private Image image;
	
	public SysTrayReporter() {
		if(!SystemTray.isSupported()) {
			log.warn("Sorry, the system tray is not supported on your system.");
		} else {
			trayIcon = this.getTrayIcon();
			
			SystemTray.getSystemTray().remove(trayIcon);
			try {
				SystemTray.getSystemTray().add(trayIcon);
			} catch (AWTException e) {
				log.warn("Error adding tray icon: " + e.getMessage(), e);
			}
		}
		
		
		
	}
	private TrayIcon getTrayIcon() {
		if (trayIcon == null) {
			image = Toolkit.getDefaultToolkit().getImage("res/tray.gif");
			trayIcon = new TrayIcon(image);
		}
		return trayIcon;
	}
	@Override
	public void eventCasualty() {
		this.getTrayIcon().displayMessage("Orcland Raiders vs Darkside Cowbowys - Casualty","Outch....another player from the Darkside Cowboys suffered a casualty", TrayIcon.MessageType.INFO);

	}

	@Override
	public void eventMVP() {
		this.getTrayIcon().displayMessage("Orcland Raiders vs. Darkside Cowboys - MVP Awards","Player XY was awarded the most valuable player", TrayIcon.MessageType.INFO);

	}

	@Override
	public void eventPassComplete() {
		this.getTrayIcon().displayMessage("Orcland Raiders vs Darkside Cowbowys - Completion","The Darkside cowboys just completed a pass.", TrayIcon.MessageType.INFO);

	}

	@Override
	public void eventPassIncomplete() {
		this.getTrayIcon().displayMessage("Orcland Raiders vs Darkside Cowbowys - Incomplete Pass!","The Orcland Raiders just can't complete a pass.", TrayIcon.MessageType.INFO);

	}

	@Override
	public void eventPassIntercepted() {
		this.getTrayIcon().displayMessage("Orcland Raiders vs Darkside Cowbowys - Interception!","The Darkside Cowboys just picked off a pass thrown by the Orcland Raiders", TrayIcon.MessageType.INFO);

	}

	@Override
	public void eventTouchdown() {
		this.getTrayIcon().displayMessage("Orcland Raiders vs Darkside Cowbowys - TOUCHDOWN!","The Orcland Raiders scored the 1-0.", TrayIcon.MessageType.INFO);

	}

}
