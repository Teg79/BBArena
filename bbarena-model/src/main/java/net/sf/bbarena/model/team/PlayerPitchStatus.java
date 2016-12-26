package net.sf.bbarena.model.team;

/**
 * Created by Teg on 26/12/2016.
 */
public enum PlayerPitchStatus {
    STANDING, PRONE, STUNNED;

    public static PlayerPitchStatus getPitchStatus(int pitchStatus) {
        PlayerPitchStatus res = null;
        if (pitchStatus >= 1 && pitchStatus <= 8) {
            res = PlayerPitchStatus.values()[pitchStatus];
        }
        return res;
    }
}
