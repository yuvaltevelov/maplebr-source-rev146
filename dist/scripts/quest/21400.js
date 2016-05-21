var status = -1;

function start(mode, type, selection) {
	    if (mode == 1) {
	status++;
	//qm.dispose();
    } else {
	if (status == 0) {
	    qm.sendOk("Please reconsider.. I need your help!");
	    qm.dispose();
	    return;
	}
	status--;
    }
	if (status == 0) {
	qm.sendAcceptDecline("How is the training going? I know you're busy, but please come to #bRien#k immediately. #bMaha#k has started to act weird again... But it's even weirder now. It's different from before. It's... darker than usual");
	} else if (status == 1) {
    qm.sendOk("I have a bad feeling about this. Please come back here. I've never seen or heard Maha like this, but I can sense the suffering it's going through. #bOnly you, the master of Maha, can do something about it!");
	} else if (status == 2) {
	qm.forceStartQuest();
	qm.warp(140000000);
		qm.dispose();
	}
}

function end(mode, type, selection) {
	qm.sendNext("Please talk to Harmonia of Leafre instead.");
	qm.dispose();
}