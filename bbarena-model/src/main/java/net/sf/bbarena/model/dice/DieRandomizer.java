package net.sf.bbarena.model.dice;

/**
 * 
 * @author cammerucci
 *
 */
public interface DieRandomizer {

	/**
	 * Return the face number, between 1 and 'facesNumber'
	 */
	public int getRollFace(int faces, String rollId);
}
