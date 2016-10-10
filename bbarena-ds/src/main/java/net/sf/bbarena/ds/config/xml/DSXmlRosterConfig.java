package net.sf.bbarena.ds.config.xml;

public class DSXmlRosterConfig{
	
	private static String xmlRostersBaseDir = "././resources/xml/rosters/";
	
	//TODO: add Url for server xml files path
	
	public static String getXmlRostersDir(String rules, boolean isLocal){
		return (isLocal == true) ? xmlRostersBaseDir + rules + "/local/" :
			xmlRostersBaseDir + rules + "/server/";
	}
}
