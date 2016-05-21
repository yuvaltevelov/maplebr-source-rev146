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
import java.util.HashMap;
import java.util.Map;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import client.MapleClient;
import config.MapleServerConfig;
import server.MaplePortal;
import tools.FileoutputUtil;
import tools.StringUtil;
public class PortalScriptManager {

	private static final PortalScriptManager instance = new PortalScriptManager();
	private final Map<String, PortalScript> scripts = new HashMap<>();
	private final static ScriptEngineFactory sef = new ScriptEngineManager().getEngineByName("nashorn").getFactory();

	public static PortalScriptManager getInstance() {
		return instance;
	}

	private PortalScript getPortalScript(final String scriptName) {
		
		final String filename = MapleServerConfig.wzFolder + "scripts/portal/" + scriptName + ".js";
		if(MapleServerConfig.isDebugMode){
			System.out.println("Loading portal script " + filename);
		}else{
			if (scripts.containsKey(scriptName)) {
				return scripts.get(scriptName);
			}
		}
		final File scriptFile = new File(filename);
		if (!scriptFile.exists()) {
			return null;
		}
		final ScriptEngine portal = sef.getScriptEngine();
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("load('nashorn:mozilla_compat.js');" + System.lineSeparator());
			builder.append(StringUtil.readFileAsString(filename));
			
			CompiledScript compiled = ((Compilable) portal).compile(builder.toString());
			compiled.eval();
		} catch (ScriptException e) {
			System.err.println("Error executing Portalscript: " + scriptName + ":" + e);
			FileoutputUtil.log(FileoutputUtil.ScriptEx_Log, "Error executing Portal script. (" + scriptName + ") " + e);
		} 
		final PortalScript script = ((Invocable) portal).getInterface(PortalScript.class);
		scripts.put(scriptName, script);
		return script;
	}

	public final void executePortalScript(final MaplePortal portal, final MapleClient c) {
		final PortalScript script = getPortalScript(portal.getScriptName());

		if (script != null) {
			try {
				script.enter(new PortalPlayerInteraction(c, portal));
			} catch (Exception e) {
				System.err.println("Error entering Portalscript: " + portal.getScriptName() + " : " + e);
			}
		} else {
			System.out.println(
					"Unhandled portal script " + portal.getScriptName() + " on map " + c.getPlayer().getMapId());
			FileoutputUtil.log(FileoutputUtil.ScriptEx_Log,
					"Unhandled portal script " + portal.getScriptName() + " on map " + c.getPlayer().getMapId());
		}
	}

	public final void clearScripts() {
		scripts.clear();
	}
}
