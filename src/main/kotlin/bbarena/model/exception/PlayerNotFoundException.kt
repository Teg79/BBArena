package bbarena.model.exception

class PlayerNotFoundException(val playerId: Long) : RuntimeException("Player $playerId not found!")