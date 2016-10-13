package net.sf.bbarena.model.event;

import java.io.Serializable;

public enum Die implements Serializable {

	D2(2),
	D3(3),
	D4(4),
	D6(6),
	D8(8),
	D10(10),
	D11(11),
	D12(12),
	D16(16),
	D100(100),
	DB(6), // Block Die
	DS(8), // Scatter Die
	D68(6, 8); // Special CAS Die
	
	private static final long serialVersionUID = -1269858379098579914L;

	private Integer[] _faces;

	Die(Integer... faces) {
		_faces = faces;
	}

	public Integer[] getFaces() {
		return _faces;
	}

}
