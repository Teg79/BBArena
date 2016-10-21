package net.sf.bbarena.ds;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.sf.bbarena.ds.util.xml.XmlRosterDS;
import net.sf.bbarena.model.team.PlayerTemplate;
import net.sf.bbarena.model.team.Roster;
import net.sf.bbarena.model.team.Skill;
import net.sf.bbarena.model.team.SkillCategory;
import net.sf.bbarena.model.team.Attributes.Attribute;

public class XmlRosterDSTest extends TestCase {
	
	XmlRosterDS locxmlh = null;
	XmlRosterDS srvxmlh = null;
	
	public void setUp(){
		locxmlh = new XmlRosterDS("LRB5.0", true);
		srvxmlh = new XmlRosterDS("LRB5.0", false);
	}
	
	
	public void testCaosRosterCreateAndSave(){
		Roster caosRoster = new Roster("Caos", "Chaos teams are not noted for the subtlety or originality of " +
				"their game play. A simple drive up the	centre of the pitch, maiming and " +
				"injuring as many opposing players as possible, is about the limit of their game plan. " +
				"They rarely, if ever, worry about such minor considerations like picking up the ball and	" +
				"scoring touchdowns  not while there are any players left alive on the opposing team, anyway.", 0, 8, 60000);
		
		SkillCategory general = new SkillCategory('G',"General", "General skills");
		SkillCategory strenght = new SkillCategory('S',"Strenght", "Strenght skills");
		SkillCategory mutation = new SkillCategory('M',"Mutation", "Mutation skills");
		SkillCategory agility = new SkillCategory('A',"Agility", "Agility skills");
		SkillCategory passing = new SkillCategory('P',"Passing", "Passing skills");
		
		Skill horns = Skill.getSkill("Horns");
		Skill loner = Skill.getSkill("Loner");
		Skill frenzy = Skill.getSkill("Frenzy");
		Skill mblow = Skill.getSkill("Mighty blow");
		Skill tSkull = Skill.getSkill("Thick skull");
		Skill wAnimal = Skill.getSkill("Wild animal");
		
		//------------- Beastmen --------------------------
		
		List <Skill> bmSkills = new ArrayList<Skill>();
		List <SkillCategory > bmNormals = new ArrayList<SkillCategory >();
		List <SkillCategory > bmDoubles = new ArrayList<SkillCategory >();
		
		bmSkills.add(horns);
		
		bmNormals.add(general);
		bmNormals.add(strenght);
		bmNormals.add(mutation);
		
		bmDoubles.add(agility);
		bmDoubles.add(passing);
		
		PlayerTemplate beastmen = new PlayerTemplate(0,16,"Beastmen",60000, 6,3,3,8,bmSkills,bmNormals,bmDoubles);
		
		//-------------- Beastmen ------------------------
		
		
		//-------------- Chaos warriors -------------------//
		
		List <Skill> cwSkills = new ArrayList<Skill>();
		List <SkillCategory > cwNormals = new ArrayList<SkillCategory >();
		List <SkillCategory > cwDoubles = new ArrayList<SkillCategory >();
		
		cwNormals.add(general);
		cwNormals.add(strenght);
		cwNormals.add(mutation);
		
		cwDoubles.add(agility);
		cwDoubles.add(passing);
		
		PlayerTemplate caosWarriors = new PlayerTemplate(0,4,"Caos Warriors",100000, 5,4,3,9,cwSkills,cwNormals,cwDoubles);
		
		//-------------- Chaos warriors -------------------//
		
		// ----------------- Minotaur ------------------------
		
		List <Skill> mSkills = new ArrayList<Skill>();
		List <SkillCategory > mNormals = new ArrayList<SkillCategory >();
		List <SkillCategory > mDoubles = new ArrayList<SkillCategory >();
		
		mSkills.add(loner);
		mSkills.add(frenzy);
		mSkills.add(horns);
		mSkills.add(mblow);
		mSkills.add(tSkull);
		mSkills.add(wAnimal);
		
		mNormals.add(strenght);
		mNormals.add(mutation);
		
		mDoubles.add(general);
		mDoubles.add(agility);
		mDoubles.add(passing);
		
		PlayerTemplate minotaur = new PlayerTemplate(0,1,"Minotaur",150000, 5,5,2,9,mSkills,mNormals,mDoubles);
		
		// ------------------ Minotaur ------------------------
		
		caosRoster.addPlayer(beastmen);
		caosRoster.addPlayer(caosWarriors);
		caosRoster.addPlayer(minotaur);
		
		String starId1 = "Brick Farth & Grotty";
		String starId2 = "Grashnak Blackhoof";
		String starId3 = "Lord Borak the Despoiler";
		String starId4 = "Max Spleenripper";
		String starId5 = "Morg n Thorg";
		String starId6 = "Ripper";
		
		caosRoster.addStarId(starId1);
		caosRoster.addStarId(starId2);
		caosRoster.addStarId(starId3);
		caosRoster.addStarId(starId4);
		caosRoster.addStarId(starId5);
		caosRoster.addStarId(starId6);
		
		try {
			locxmlh.saveRoster(caosRoster);
			srvxmlh.saveRoster(caosRoster);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void testOrcRosterCreateAndSave(){
		Roster orcRoster = new Roster("Orcs", "Orcs have been playing Blood Bowl since the game was invented, and Orc teams such as the Gouged " +
				"Eye and Severed Heads are amongst the best in the league. Orc teams are tough and hard-hitting, " +
				"grinding down the oppositions line to create gaps for their excellent Orc Blitzers to exploit.", 0, 8, 60000);
		
		SkillCategory general = new SkillCategory('G',"General", "General skills");
		SkillCategory strenght = new SkillCategory('S',"Strenght", "Strenght skills");
		SkillCategory agility = new SkillCategory('A',"Agility", "Agility skills");
		SkillCategory passing = new SkillCategory('P',"Passing", "Passing skills");
		
		Skill rStuff = Skill.getSkill("Right stuff");
		Skill dodge = Skill.getSkill("Dodge");
		Skill stunty = Skill.getSkill("Stunty");
		Skill sHands = Skill.getSkill("Sure hands");
		Skill pass = Skill.getSkill("Pass");
		Skill block = Skill.getSkill("Block");
		Skill loner = Skill.getSkill("Loner");
		Skill aHungry = Skill.getSkill("Always Hungry");
		Skill mblow = Skill.getSkill("Mighty blow");
		Skill rStupid = Skill.getSkill("Really stupid");
		Skill regeneration = Skill.getSkill("Regeneration");
		Skill ttMate = Skill.getSkill("Throw team-mate");
		
		//------------- Linemen --------------------------
		
		List <Skill> lSkills = new ArrayList<Skill>();
		List <SkillCategory > lNormals = new ArrayList<SkillCategory >();
		List <SkillCategory > lDoubles = new ArrayList<SkillCategory >();
		
		lNormals.add(general);
		
		lDoubles.add(agility);
		lDoubles.add(strenght);
		lDoubles.add(passing);
		
		PlayerTemplate linemen = new PlayerTemplate(0,16,"Linemen",50000, 5,3,3,9,lSkills,lNormals,lDoubles);
		
		//-------------- Linemen ------------------------
		
		
		//-------------- Goblins -------------------//
		
		List <Skill> gSkills = new ArrayList<Skill>();
		List <SkillCategory > gNormals = new ArrayList<SkillCategory >();
		List <SkillCategory > gDoubles = new ArrayList<SkillCategory >();
		
		gSkills.add(rStuff);
		gSkills.add(dodge);
		gSkills.add(stunty);
		
		gNormals.add(agility);
		
		gDoubles.add(general);
		gDoubles.add(strenght);
		gDoubles.add(passing);
		
		PlayerTemplate goblins = new PlayerTemplate(0,4,"Goblins",40000, 6,2,3,7,gSkills,gNormals,gDoubles);
		
		//-------------- Goblins -------------------//
		
		//-------------- Throwers -------------------//
		
		List <Skill> thSkills = new ArrayList<Skill>();
		List <SkillCategory > thNormals = new ArrayList<SkillCategory >();
		List <SkillCategory > thDoubles = new ArrayList<SkillCategory >();
		
		thSkills.add(sHands);
		thSkills.add(pass);
		
		thNormals.add(general);
		thNormals.add(passing);
		
		thDoubles.add(agility);
		thDoubles.add(strenght);
		
		PlayerTemplate throwers = new PlayerTemplate(0,2,"Throwers",70000, 5,3,3,8,thSkills,thNormals,thDoubles);
		
		//-------------- Throwers -------------------//
		
		//-------------- Black ORcs -------------------//
		
		List <Skill> boSkills = new ArrayList<Skill>();
		List <SkillCategory > boNormals = new ArrayList<SkillCategory >();
		List <SkillCategory > boDoubles = new ArrayList<SkillCategory >();
		
		boNormals.add(general);
		boNormals.add(strenght);
		
		boDoubles.add(agility);
		boDoubles.add(passing);
		
		PlayerTemplate blackorcs = new PlayerTemplate(0,4,"Black Orc Blockers",80000, 4,4,2,9,boSkills,boNormals,boDoubles);
		
		//-------------- Black ORcs -------------------//
		
		//-------------- Blitzers -------------------//
		
		List <Skill> bzSkills = new ArrayList<Skill>();
		List <SkillCategory > bzNormals = new ArrayList<SkillCategory >();
		List <SkillCategory > bzDoubles = new ArrayList<SkillCategory >();
		
		bzSkills.add(block);
		
		bzNormals.add(general);
		bzNormals.add(strenght);
		
		bzDoubles.add(agility);
		bzDoubles.add(passing);
		
		PlayerTemplate blitzers = new PlayerTemplate(0,4,"Blitzers",80000, 6,3,3,9,bzSkills,bzNormals,bzDoubles);
		
		//-------------- Blitzers -------------------//
		
		//-------------- Troll -------------------//
		
		List <Skill> trSkills = new ArrayList<Skill>();
		List <SkillCategory > trNormals = new ArrayList<SkillCategory >();
		List <SkillCategory > trDoubles = new ArrayList<SkillCategory >();
		
		trSkills.add(loner);
		trSkills.add(aHungry);
		trSkills.add(mblow);
		trSkills.add(rStupid);
		trSkills.add(regeneration);
		trSkills.add(ttMate);
		
		trNormals.add(strenght);
		
		trDoubles.add(general);
		trDoubles.add(agility);
		trDoubles.add(passing);
		
		PlayerTemplate troll = new PlayerTemplate(0,1,"Troll",110000, 4,5,1,9,trSkills,trNormals,trDoubles);
		
		//-------------- Troll -------------------//
		
		orcRoster.addPlayer(linemen);
		orcRoster.addPlayer(goblins);
		orcRoster.addPlayer(throwers);
		orcRoster.addPlayer(blackorcs);
		orcRoster.addPlayer(blitzers);
		orcRoster.addPlayer(troll);
		
		String starId1 = "Bomber Dribblesnot";
		String starId2 = "Ripper";
		String starId3 = "Grashnak Blackhoof";
		String starId4 = "Scrappa Sorehead";
		String starId5 = "Ugroth Bolgrot";
		String starId6 = "Varag Ghoul-Chewer";
		
		
		orcRoster.addStarId(starId1);
		orcRoster.addStarId(starId2);
		orcRoster.addStarId(starId3);
		orcRoster.addStarId(starId4);
		orcRoster.addStarId(starId5);
		orcRoster.addStarId(starId6);
		
		try {
			locxmlh.saveRoster(orcRoster);
			srvxmlh.saveRoster(orcRoster);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//--------------------------------------------------------------------------------
	
	public void testSrvGetRoster() throws IOException{
		assertTrue(srvxmlh.getRoster("Orcs").getRace().equals("Orcs"));
		assertTrue(srvxmlh.getRoster("Caos").getRace().equals("Caos"));
	}

//	public void testLocalGetRosters() throws IOException{
//		List<Roster> rosters = locxmlh.getRosters();
//		
//		assertTrue(rosters != null);
//		assertTrue(rosters.size() == 2);
//		assertTrue(rosters.get(0).getRace().equals("Caos"));
//	}
	
	public void testLocalGetOrcRoster() throws IOException{
		Roster roster = locxmlh.getRoster("Orcs");
		
		assertTrue(roster instanceof Roster);
		assertTrue(roster.getRace().equals("Orcs"));
		assertTrue(roster.getPlayers().size() == 6);
		assertTrue(roster.getPlayers().get(0).getPosition().equals("Linemen"));
		assertTrue(roster.getPlayers().get(1).getPosition().equals("Goblins"));
		assertTrue(roster.getPlayers().get(0).getRoster()!=null);
		assertTrue(roster.getPlayers().get(0).getRoster()== roster);
	}
	
	public void testLocalOrcsAttributes() throws IOException{
		Roster roster = locxmlh.getRoster("Orcs");
		
		// Black orc
		assertTrue(roster.getPlayers().get(3).getAttributes().getAttribute(Attribute.MA) == 4);
		assertTrue(roster.getPlayers().get(3).getAttributes().getAttribute(Attribute.ST) == 4);
		assertTrue(roster.getPlayers().get(3).getAttributes().getAttribute(Attribute.AG) == 2);
		assertTrue(roster.getPlayers().get(3).getAttributes().getAttribute(Attribute.AV) ==9);
	}
	
	public void testLocalCaosSkills() {
		Roster roster = locxmlh.getRoster("Caos");
		
		//Beastmen
		assertTrue(roster.getPlayers().get(0).getSkills().size() == 1);
		assertTrue(roster.getPlayers().get(0).getSkills().get(0).getName().equals("Horns"));
		
		//Chaos warriors
		assertTrue(roster.getPlayers().get(1).getSkills().size() == 0);
	}
	
	public void testLocalCaosSkillCategory () {
		Roster roster = locxmlh.getRoster("Caos");
		assertNotNull(roster);
	}
}
