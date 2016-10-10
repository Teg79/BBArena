package net.sf.bbarena.view.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import net.sf.bbarena.model.Coordinate;
import net.sf.bbarena.model.pitch.DefaultDogout;
import net.sf.bbarena.model.pitch.Pitch;
import net.sf.bbarena.model.team.Player;
import net.sf.bbarena.model.team.Team;

public final class PlayerSetupFile {
    public static void setupPlayers(Pitch pitch, Team team, InputStream is) {
        for(Player player : team.getPlayers()) {
            pitch.putPlayer(player, DefaultDogout.BloodBowlDugoutRoom.RESERVES);
        }
        Scanner in = new Scanner(is);
        while (in.hasNextInt()) {
        	int id = in.nextInt();
        	int x = in.nextInt();
        	int y = in.nextInt();
        	Player player = team.getPlayer(id);
        	if(player != null) {
        	    pitch.putPlayer(player, new Coordinate(x, y));
        	}
        }
        in.close();
    }

    public static void savePlayerSetup(Team team, String file) {
	try {
	    PrintWriter out = new PrintWriter(new FileOutputStream(file));
	    for(Player p : team.getPlayers()) {
		if( p.getSquare() != null) {
                    out.print( p.getNum() );
                    out.print( " " );	
                    Coordinate c = p.getSquare().getCoords(); 
                    out.print( c.getX() );
                    out.print( " " );	
                    out.println( c.getY() );
		}
	    }
	    out.flush();
	    out.close();	    
	} catch (IOException e) {
	    e.printStackTrace();
	    System.exit(1);
	}
    }    
}
