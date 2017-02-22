package bbarena.ds.util.xml;

import java.io.File;
import java.io.FilenameFilter;

class XmlFileFilter implements FilenameFilter {
	String select;
	
	XmlFileFilter() {
		select = ".xml";
	}

    public boolean accept(File dir, String name) {
    	String f = new File(name).getName();
        return f.indexOf(select) != -1;
    }
}
