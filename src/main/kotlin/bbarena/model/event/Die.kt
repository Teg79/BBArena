package bbarena.model.event

import java.io.Serializable

enum class Die(val faces: Array<Int>) : Serializable {

    D2(arrayOf(2)),
    D3(arrayOf(3)),
    D4(arrayOf(4)),
    D6(arrayOf(6)),
    D8(arrayOf(8)),
    D10(arrayOf(10)),
    D11(arrayOf(11)),
    D12(arrayOf(12)),
    D16(arrayOf(16)),
    D100(arrayOf(100)),
    DB(arrayOf(6)), // Block Die
    DS(arrayOf(8)), // Scatter Die
    D68(arrayOf(6, 8));

}
