/**
 * 
 */
package net.sf.bbarena.model.team;

import java.io.Serializable;

import net.sf.bbarena.model.team.Attributes.Attribute;

/**
 * @author f.bellentani
 *
 */
public class AttributeModifier implements Serializable {
    
	private static final long serialVersionUID = 2874947044590462308L;
	
	private int mod = 0;
    private Attribute type;
    
    /** Creates a new instance of Attribute */
    public AttributeModifier() {
    }
    
    public AttributeModifier(String s){
    	s = s.trim();
    	if(s.startsWith("+")){
            s = s.substring(1);
        }
        
        int endModId = 1;
        while(s.charAt(endModId) >= '0' && s.charAt(endModId) <= '9'){
            endModId++;
        }
        setType(s.substring(endModId).trim());
        setMod(Integer.parseInt(s.substring(0, endModId).trim()));
    }
    
    public AttributeModifier(String type, int mod){
        this();
        setType(type);
        setMod(mod);
    }
    
    public AttributeModifier(Attribute attribute, int mod) {
    	this.type = attribute;
    	this.mod = mod;
    }
    
    public String toString(){
        return getMod()+" "+getType();
    }
    
    /** Getter for property mod.
     * @return Value of property mod.
     *
     */
    public int getMod() {
        return mod;
    }
    
    /** Setter for property mod.
     * @param mod New value of property mod.
     *
     */
    public void setMod(int mod) {
        this.mod = mod;
    }
    
    /** Getter for property type.
     * @return Value of property type.
     *
     */
    public Attribute getType() {
        return type;
    }
    
    /** Setter for property type.
     * @param type New value of property type.
     *
     */
    public void setType(String type) {
        this.type = Attribute.parseAttribute(type);
    }
    
    public void setType(Attribute type) {
    	this.type = type;
    }
    
    public static void main(String[] args){
        AttributeModifier b = new AttributeModifier("-10 St");
        AttributeModifier a = new AttributeModifier("+13Ag");
        System.out.println(a.toString()+"\n"+b.toString());        
    }
}
