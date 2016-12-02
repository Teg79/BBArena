//package net.sf.bbarena.model.event.game;
//
//import net.sf.bbarena.model.Arena;
//import net.sf.bbarena.model.util.Concat;
//import net.sf.bbarena.model.util.Pair;
//
//public class ChangeRerollEvent extends GameEvent {
//
//    private Integer _team;
//    private Integer _toAdd;
//    private Integer _prev;
//
//    public ChangeRerollEvent(Integer team, Integer toAdd) {
//        _team = team;
//        _toAdd = toAdd;
//    }
//
//    @Override
//    protected void doEvent(Arena arena) {
//        _arena = arena;
//        _prev = _arena.getTurnMarkers().get(_team).getRerolls();
//        _arena.getTurnMarkers().get(_team).setRerolls(_prev + _toAdd);
//    }
//
//    @Override
//    protected void undoEvent() {
//        _arena.getTurnMarkers().get(_team).setRerolls(_prev);
//    }
//
//    @Override
//    public String getString() {
//        return Concat.buildLog(getClass(),
//                new Pair("team", _team),
//                new Pair("rr", _toAdd));
//    }
//}
