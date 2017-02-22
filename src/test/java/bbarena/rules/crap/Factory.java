package bbarena.rules.crap;

import bbarena.model.team.*;

import java.util.Collections;

public class Factory {

    private static Integer PLAYER_COUNTER = 0;

    public static final Roster ORC_ROSTER = new Roster("Orc", "", 0, 7, 70000);

    static {
        PlayerTemplate lineorc = new PlayerTemplate(0, 12, "Lineorc", 50000, 5, 3, 3, 9, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        ORC_ROSTER.addPlayer(lineorc);
    }

    private static int _teamCounter = 0;

    public static Team buildTeam() {
        Team team = new Team(_teamCounter++, ORC_ROSTER);
        for (int i = 0; i < 12; i++) {
            PlayerTemplate playerTemplate = ORC_ROSTER.getPlayers().get(0);
            Player player = new Player(PLAYER_COUNTER++, i + 1, "Player " + (i + 1), playerTemplate, new Experience(), playerTemplate.getSkills(), Collections.emptyList());
            team.addPlayer(player);
        }
        team.setReRolls(2);
        return team;
    }

}
