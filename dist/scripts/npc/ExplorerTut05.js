/* RED 1st impact
    Port
    Made by Daenerys
 */

var status = -1;

function action(mode, type, selection) {
	if (mode == 1) {
		status++;
	} else if (status == 0) {
		cm.sendNext("This is a great day!");
		cm.dispose();
		status--;
	}
	if (status == 0) {
		cm
				.sendYesNo("Are you ready to explore the world? Do you want to go now?");
	} else if (status == 1) {
		cm.warp(100000000, 0);
		cm.dispose();
	}
}