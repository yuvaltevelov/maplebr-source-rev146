function enter(pi) {
    if (pi.isQuestActive(21201)) { //aran first job
	pi.forceCompleteQuest(21201);
	pi.warp(914021000,0);
	pi.playerMessage(5, "You recovered your memories!");
    } else if (pi.isQuestActive(21302)) { //aran first job
	//pi.forceCompleteQuest(21302);
	pi.warp(914022100,0);
	pi.playerMessage(5, "You recovered your memories!");
    }
	pi.dropMessage(6, "You cannot enter the Mirror");
	
   // pi.warp(914021000,0);
 //what does this even do
}