/*
 This file is part of the OdinMS Maple Story Server
 Copyright (C) 2008 ~ 2010 Patrick Huy <patrick.huy@frz.cc> 
 Matthias Butz <matze@odinms.de>
 Jan Christian Meyer <vimes@odinms.de>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License version 3
 as published by the Free Software Foundation. You may not use, modify
 or distribute this program under any other version of the
 GNU Affero General Public License.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package scripting;

import java.io.File;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import client.MapleClient;
import config.MapleServerConfig;
import tools.FileoutputUtil;
import tools.StringUtil;

/**
 *
 * @author Matze
 */
public abstract class AbstractScriptManager {

	private static final ScriptEngineManager sem = new ScriptEngineManager();

	protected Invocable getInvocable(String path, MapleClient c) {
		return getInvocable(path, c, false);
	}

	protected Invocable getInvocable(String path, MapleClient c, boolean npc) {
		try {
			path = MapleServerConfig.scriptFolder +"/scripts/" + path;
			ScriptEngine engine = null;
			
			if(MapleServerConfig.isDebugMode){
				System.out.println("Loading script script " + path);
			}
			if (c != null) {
				engine = c.getScriptEngine(path);
			}
			if (engine == null) {
				File scriptFile = new File(path);
				if (!scriptFile.exists()) {
					return null;
				}
				engine = sem.getEngineByName("nashorn");
				if (c != null) {
					c.setScriptEngine(path, engine);
				}
				StringBuilder builder = new StringBuilder();
				builder.append("load('nashorn:mozilla_compat.js');" + System.lineSeparator());
				builder.append(StringUtil.readFileAsString(path));
				engine.eval(builder.toString());
			} 
			return (Invocable) engine;
		} catch (ScriptException e) {
			System.err.println("Error executing script. Path: " + path + "\nException " + e);
			FileoutputUtil.log(FileoutputUtil.ScriptEx_Log,
					"Error executing script. Path: " + path + "\nException " + e);
			return null;
		} 
	}
}
