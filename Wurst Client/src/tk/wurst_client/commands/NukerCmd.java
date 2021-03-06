/*
 * Copyright � 2014 - 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.commands;

import net.minecraft.block.Block;
import tk.wurst_client.Client;
import tk.wurst_client.commands.Cmd.Info;
import tk.wurst_client.mods.NukerMod;
import tk.wurst_client.utils.MiscUtils;

@Info(help = "Changes the settings of Nuker.", name = "nuker", syntax = {
	"mode (normal|id|flat|smash)", "id <block_id>", "name <block_name>"})
public class NukerCmd extends Cmd
{
	@Override
	public void execute(String[] args) throws Error
	{
		if(args.length != 2)
			syntaxError();
		else if(args[0].toLowerCase().equals("mode"))
		{// 0=normal, 1=id, 2=flat, 3=smash
			if(args[1].toLowerCase().equals("normal"))
			{
				Client.wurst.options.nukerMode = 0;
				NukerMod.id = 0;
			}else if(args[1].toLowerCase().equals("id"))
			{
				Client.wurst.options.nukerMode = 1;
				NukerMod.id = 0;
			}else if(args[1].toLowerCase().equals("flat"))
			{
				Client.wurst.options.nukerMode = 2;
				NukerMod.id = 0;
			}else if(args[1].toLowerCase().equals("smash"))
			{
				Client.wurst.options.nukerMode = 3;
				NukerMod.id = 0;
			}else
				syntaxError();
			Client.wurst.fileManager.saveOptions();
			Client.wurst.chat.message("Nuker mode set to \"" + args[1] + "\".");
		}else if(args[0].equalsIgnoreCase("id") && MiscUtils.isInteger(args[1]))
		{
			if(Client.wurst.options.nukerMode != 1)
			{
				Client.wurst.options.nukerMode = 1;
				Client.wurst.chat.message("Nuker mode set to \"" + args[0]
					+ "\".");
			}
			NukerMod.id = Integer.valueOf(args[1]);
			Client.wurst.fileManager.saveOptions();
			Client.wurst.chat.message("Nuker ID set to " + args[1] + ".");
		}else if(args[0].equalsIgnoreCase("name"))
		{
			if(Client.wurst.options.nukerMode != 1)
			{
				Client.wurst.options.nukerMode = 1;
				Client.wurst.chat.message("Nuker mode set to \"" + args[0]
					+ "\".");
			}
			int newID = Block.getIdFromBlock(Block.getBlockFromName(args[1]));
			if(newID == -1)
			{
				Client.wurst.chat.message("The block \"" + args[1]
					+ "\" could not be found.");
				return;
			}
			NukerMod.id = Integer.valueOf(newID);
			Client.wurst.fileManager.saveOptions();
			Client.wurst.chat.message("Nuker ID set to " + newID + " ("
				+ args[1] + ").");
		}else
			syntaxError();
	}
}
