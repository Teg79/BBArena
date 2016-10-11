package net.sf.bbarena.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.bbarena.model.dice.JavaRandomizer;
import net.sf.bbarena.model.event.Event;
import net.sf.bbarena.model.event.game.KickOffBallEvent;
import net.sf.bbarena.model.event.game.ScatterBallEvent;
import net.sf.bbarena.model.replay.ReplayChoice;
import net.sf.bbarena.model.replay.Replayer;
import net.sf.bbarena.model.team.Team;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMatchReplay {
	
	private class RandomCoach extends Coach<ReplayChoice> {

		private JavaRandomizer die = new JavaRandomizer();

		public RandomCoach(Team... teams) {
			super(teams);
		}
		
		@Override
		public ReplayChoice ask(String question, Set<ReplayChoice> choices) {
			ReplayChoice res;
			if (!choices.contains(ReplayChoice.NEXT)) {
				res = ReplayChoice.EXIT;
			} else {
				res = ReplayChoice.NEXT;
			}
			if (choices.contains(ReplayChoice.PREV)) {
				int roll = die.getRollFace(100, "");
				if (roll < 30) {
					res = ReplayChoice.PREV;
				}
			}
			return res;
		}

		@Override
		protected void say(String message, ReplayChoice... choices) {
			// TODO Auto-generated method stub
			
		}
	}
	
//	private class DummyCoach extends Coach<ReplayChoice> {
//
//		public DummyCoach(Team... teams) {
//			super(teams);
//		}
//		
//		@Override
//		public ReplayChoice ask(String question, ReplayChoice... choices) {
//			ReplayChoice res = ReplayChoice.EXIT;
//			for (ReplayChoice c: choices) {
//				if (c == ReplayChoice.NEXT) {
//					res = ReplayChoice.NEXT;
//					break;
//				}
//			}
//			return res;
//		}
//	}
	
	private List<Event> _events = new ArrayList<Event>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		_events.add(new KickOffBallEvent(0, 4, 4));
		_events.add(new ScatterBallEvent(0, Direction.SE.ordinal()));
		_events.add(new ScatterBallEvent(0, Direction.SE.ordinal()));
		_events.add(new ScatterBallEvent(0, Direction.SE.ordinal()));
	}
	
	@Test
	public void testReplay() {
		Team t1 = new Team(0, null);
		t1.setName("T1");
		Team t2 = new Team(1, null);
		t2.setName("T2");
		RandomCoach coach = new RandomCoach(t1, t2);
		Replayer replayer = new Replayer(_events);
		Match<Replayer, ReplayChoice> match = new Match<Replayer, ReplayChoice>(new RandomCoach[]{coach});
		match.start(replayer);
	}

}
