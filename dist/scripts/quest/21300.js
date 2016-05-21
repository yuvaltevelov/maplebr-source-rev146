var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
	//qm.dispose();
    } else {
	if (status == 1) {
	    qm.sendNext("Please? He's being very weird...");
	    qm.dispose();
	    return;
	}
	status--;
    }
	if (status == 0) {
	qm.sendNext("How is taining going? Hm, Lv. 60? You still have a long way to go, but it's definitely praiseworthy compared to the first time I met you. Continue to train diligently, and I'm sure you'll regain your strength soon!");
	} else if (status == 1) {
	qm.sendAcceptDecline("But first, you must head to #bRien#k You #bGiant Polearm#k is acting weird again. I think it has something to tell you. It might be able to restore your abilities, so please hurry.")	
	} else if (status == 2) {
	qm.sendOk("Anyway, I thought it was really something that a weapon had it's own identity, but this weapon gets extremely annoying, it cries, saying that I'm not paying attention to it's needs and now... Oh please keep this a secret from the Polearm I don't think it's a good idea to upset the weapon any more than I already have.")	
	} else if (status == 3) {
		qm.warp(140000000);
		qm.forceStartQuest();
		qm.dispose();
	}
	//qm.dispose();
}

function end(mode, type, selection) {
	qm.sendNext("Please talk to Tylus of El Nath instead.");
	qm.dispose();
}