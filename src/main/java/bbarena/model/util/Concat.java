package bbarena.model.util;

public class Concat {
	
	public static String buildLog(Class<?> type, Pair... pairs) {
		StringBuilder res = new StringBuilder();
		res.append(type.getSimpleName())
		.append("[");
		if (pairs != null) {
			for (int i = 0; i < pairs.length; i++) {
				if (pairs[i] != null) {
					res.append(pairs[i].toString());
					if (i < pairs.length - 1) {
						res.append(",");
					}
				}
			}
		}
		res.append("]");
		return res.toString();
	}
	
}
