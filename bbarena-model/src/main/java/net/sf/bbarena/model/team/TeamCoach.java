package net.sf.bbarena.model.team;

import java.io.Serializable;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author f.bellentani
 *
 */
public class TeamCoach implements Serializable{
        
	private static final long serialVersionUID = -3654048016005810514L;
	
	private String name = "";
    private String email = "";
    
    /** Creates a new instance of Coach */
    public TeamCoach(){
    }
    
    public TeamCoach(Element xmlCoach) {
        this();
        
        setName(xmlCoach.selectSingleNode("@name").getText());
        setEmail(xmlCoach.selectSingleNode("@email").getText());
    }
    
    public Element asElement(){
        Element res = DocumentHelper.createElement("coach");
        res.addAttribute("name", getName())
        .addAttribute("email", getEmail());
        
        return res;
    }
    
    /** Getter for property email.
     * @return Value of property email.
     *
     */
    public java.lang.String getEmail() {
        return email;
    }
    
    /** Setter for property email.
     * @param email New value of property email.
     *
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }
    
    /** Getter for property name.
     * @return Value of property name.
     *
     */
    public java.lang.String getName() {
        return name;
    }
    
    /** Setter for property name.
     * @param name New value of property name.
     *
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }
}