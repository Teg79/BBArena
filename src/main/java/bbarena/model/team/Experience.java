package bbarena.model.team;

import java.io.Serializable;

import bbarena.model.util.Concat;
import bbarena.model.util.Pair;

/**
 *
 * @author f.bellentani
 */
public class Experience implements Serializable {

	private static final long serialVersionUID = -4647512533501512777L;

	private int comp = 0;
	private int td = 0;
	private int inter = 0;
	private int cas = 0;
	private int mvp = 0;
	private int old = 0;
	private int scars = 0;
	private int fouls = 0;
	private int rush = 0;

	public Experience() {

	}

	public Experience(int comp, int td, int inter, int cas, int mvp, int old, int scars, int fouls, int rush) {
		this();
		this.comp = comp;
		this.td = td;
		this.inter = inter;
		this.cas = cas;
		this.mvp = mvp;
		this.old = old;
		this.scars = scars;
		this.fouls = fouls;
		this.rush = rush;
	}

	public int getCas() {
		return cas;
	}

	public void setCas(int cas) {
		this.cas = cas;
	}

	public int getComp() {
		return comp;
	}

	public void setComp(int comp) {
		this.comp = comp;
	}

	public int getInter() {
		return inter;
	}

	public void setInter(int inter) {
		this.inter = inter;
	}

	public int getMvp() {
		return mvp;
	}

	public void setMvp(int mvp) {
		this.mvp = mvp;
	}

	public int getOld() {
		return old;
	}

	public void setOld(int old) {
		this.old = old;
	}

	public int getScars() {
		return scars;
	}

	public void setScars(int scars) {
		this.scars = scars;
	}

	public int getTd() {
		return td;
	}

	public void setTd(int td) {
		this.td = td;
	}

	public int getSpp() {
		return td * 3 + comp + inter * 2 + cas * 2 + mvp * 5;
	}

	public int getFouls() {
		return fouls;
	}

	public void setFouls(int fouls) {
		this.fouls = fouls;
	}

	public int getRush() {
		return rush;
	}

	public void setRush(int rush) {
		this.rush = rush;
	}

	public void add(Experience exp) {
		cas += exp.cas;
		comp += exp.comp;
		fouls += exp.fouls;
		inter += exp.inter;
		mvp += exp.mvp;
		old += exp.old;
		rush += exp.rush;
		scars += exp.scars;
		td += exp.td;
	}

	public void sub(Experience exp) {
		cas -= exp.cas;
		comp -= exp.comp;
		fouls -= exp.fouls;
		inter -= exp.inter;
		mvp -= exp.mvp;
		old -= exp.old;
		rush -= exp.rush;
		scars -= exp.scars;
		td -= exp.td;
	}
	
	public String toString() {
		return Concat.buildLog(getClass(),
				new Pair("cas", cas),
				new Pair("comp", comp),
				new Pair("fouls", fouls),
				new Pair("inter", inter),
				new Pair("mvp", mvp),
				new Pair("old", old),
				new Pair("rush", rush),
				new Pair("scars", scars),
				new Pair("td", td));
	}

}
