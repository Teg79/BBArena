package net.sf.bbarena.ds.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import net.sf.bbarena.ds.RosterDS;
import net.sf.bbarena.ds.config.xml.DSXmlRosterConfig;
import net.sf.bbarena.model.team.*;
import net.sf.bbarena.model.team.Attributes.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XmlRosterDS implements RosterDS {

    private static final Logger log = LoggerFactory.getLogger(XmlRosterDS.class);

    private boolean isLocalXml = true;

    private String xmlRostersDir = null;
    private String rules = null;

    public XmlRosterDS(String rules, boolean isLocal) {
        isLocalXml = isLocal;
        this.rules = rules;

        xmlRostersDir = DSXmlRosterConfig.getXmlRostersDir(this.rules, isLocalXml);
    }

    public Roster getRoster(String race) {
        Roster roster = null;
        XStream xstream = getXStream();

        String xmlRosterFileName = xmlRostersDir + race + ".xml";
        File rosterFile = new File(xmlRosterFileName);
        FileInputStream fin;
        try {
            fin = new FileInputStream(rosterFile);

            InputStreamReader isr = new InputStreamReader(fin);
            BufferedReader br = new BufferedReader(isr);

            String line = br.readLine();

            String xmlRoster = "";
            while (line != null) {
                xmlRoster += line + "\n";
                line = br.readLine();
            }

            roster = (Roster) xstream.fromXML(xmlRoster);
        } catch (IOException e) {
            log.error("Error in DSXmlRosterHelper - getRoster: " + e.getMessage(), e);
        }

        return roster;
    }

    public List<String> getRosterNames() {
        List<String> rosterNames = new ArrayList<String>();

        File rostersDir = new File(xmlRostersDir);
        String[] rosters = rostersDir.list(new XmlFileFilter());

        for (int i = 0; i < rosters.length; i++) {
            rosterNames.add(rosters[i].split(".xml")[0]);
        }

        return rosterNames;
    }

    public List<Roster> getRosters() {
        List<Roster> rosters = new ArrayList<Roster>();

        Iterator<String> rostersIt = getRosterNames().iterator();

        while (rostersIt.hasNext()) {
            Roster roster = getRoster(rostersIt.next());

            if (roster != null) {
                rosters.add(roster);
            }
        }

        return rosters;
    }

    //TODO: to be removed
    public void saveRoster(Roster roster) throws FileNotFoundException {
        XStream xstream = getXStream();

        String xmlRoster = xstream.toXML(roster);

        PrintWriter out;

        String xmlRosterFileName = xmlRostersDir + roster.getRace() + ".xml";

        out = new PrintWriter(
                new BufferedOutputStream(new FileOutputStream(xmlRosterFileName)));

        out.print(xmlRoster);
        out.close();
    }

    private XStream getXStream() {
        XStream xstream = new XStream(new DomDriver());

        xstream.alias("roster", Roster.class);
        xstream.alias("playertemplate", PlayerTemplate.class);
        xstream.alias("qty", Qty.class);

        xstream.alias("attributes", Attributes.class);
        xstream.alias("skill", Skill.class);
        xstream.alias("skillcategory", SkillCategory.class);
        xstream.alias("attribute", Attribute.class);

        return xstream;
    }
}
