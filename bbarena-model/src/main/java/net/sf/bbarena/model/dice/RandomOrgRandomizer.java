package net.sf.bbarena.model.dice;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;

/**
 * 
 * @author cammerucci
 * 
 */
public class RandomOrgRandomizer extends SecureRandom implements DieRandomizer {

	private static final long serialVersionUID = 921076210048471019L;

	private static RandomOrgRandomizer randomizer;
	private int cacheSize = 0;
	private byte[] cache = null;
	private int iterator = 0;

	private RandomOrgRandomizer() {
		super();
		setCache(256);
	}

	public static synchronized RandomOrgRandomizer getInstance() {
		if (randomizer == null) {
			randomizer = new RandomOrgRandomizer();
		}

		return randomizer;
	}

	// -------- Get dice face from random.org without an ID -----------

	public synchronized void setCache(int cacheSize) {
		if (cacheSize < 1 || cacheSize > 16384)
			throw new IllegalArgumentException(
					"cache size must be between 1 and 16384");

		this.cacheSize = cacheSize;
		this.cache = new byte[cacheSize];
		this.iterator = cacheSize;
	}

	public synchronized void nextBytes(byte[] bytes) {
		try {
			for (int i = 0; i < bytes.length; ++i) {
				if (iterator == cacheSize) {
					connectRandomOrgAndFillCache();
					iterator = 0;
				}
				bytes[i] = cache[iterator++];
			}
		} catch (IOException e) {
			System.err.println("Exception while receiving true random bytes: "
					+ e + ". Use super class java.security.SecureRandom.");
			super.nextBytes(bytes);
		}
	}

	private void connectRandomOrgAndFillCache() throws IOException {

		System.out.println("Connecting www.random.org ...");
		URL randomOrg = new URL("http://www.random.org/cgi-bin/randbyte?"
				+ "nbytes=" + cacheSize + "&format=dec");
		HttpURLConnection con = (HttpURLConnection) randomOrg.openConnection();

		con.setDoInput(true);
		con.setDoOutput(false);
		con.setRequestMethod("GET");

		// TODO: change mail address!
		con.setRequestProperty("User-Agent", "your-email@your-domain.com");

		StreamTokenizer st = new StreamTokenizer(new InputStreamReader(con
				.getInputStream()));

		int i = 0;
		while (st.nextToken() != StreamTokenizer.TT_EOF) {
			if (st.ttype != StreamTokenizer.TT_NUMBER)
				throw new IOException("received data contains non-numbers");
			cache[i++] = (byte) st.nval;
		}

		con.disconnect();
	}

	public int getRandomDiceNumber(int facesNumber) {
		int face = nextInt(facesNumber);

		return face;
	}

	// -------- Get dice face from random.org/integers with an ID -----------

	public int getRandomDiceNumberWithID(int facesNumber, String rollId) {
		int face = 1;
		try {
			face = getFaceFromRandomOrgWithID(facesNumber, rollId);
		} catch (IOException e) {
			face = ((int) (Math.random() * facesNumber)) + 1;
		}

		return face;
	}

	private int getFaceFromRandomOrgWithID(int facesNumber, String rollId)
			throws IOException {
		int face = 0;

		System.out.println("Connecting www.random.org/integers ...");
		URL randomOrg = new URL(
				"http://preview.random.org/integers/?num=1&min=1&max="
						+ facesNumber + "&unique=off&format=plain&rnd=id."
						+ rollId);
		HttpURLConnection con = (HttpURLConnection) randomOrg.openConnection();

		con.setDoInput(true);
		con.setDoOutput(false);
		con.setRequestMethod("GET");

		StreamTokenizer st = new StreamTokenizer(new InputStreamReader(con
				.getInputStream()));

		if (st.nextToken() != StreamTokenizer.TT_EOF) {
			if (st.ttype != StreamTokenizer.TT_NUMBER)
				throw new IOException("received data contains non-numbers");
			else {
				face = (int) st.nval;
			}
		}

		con.disconnect();

		return face;
	}

	@Override
	public int getRollFace(int faces, String rollId) {
		return getRandomDiceNumberWithID(faces, rollId);
//		return nextInt(faces) + 1;
	}

}
