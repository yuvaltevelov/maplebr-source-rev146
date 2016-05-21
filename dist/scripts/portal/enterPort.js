function enter(pi) {
	if (pi.getQuestStatus(21301) >= 1) {
    pi.warp(914022000, 1);
	pi.spawnMonster(9001013, 2715, 3);
	} else {
		 pi.warp(140020300, 1);
	}
}