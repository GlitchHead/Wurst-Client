/*
 * Copyright � 2014 - 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.commands;

import java.util.Iterator;

import tk.wurst_client.Client;
import tk.wurst_client.commands.Cmd.Info;
import tk.wurst_client.utils.MiscUtils;

@Info(help = "Shows the command list or the help for a command.",
	name = "help",
	syntax = {"[<page>]", "[<command>]"})
public class HelpCmd extends Cmd
{
	@Override
	public void execute(String[] args) throws Error
	{
		if(args.length == 0)
		{
			execute(new String[]{"1"});
			return;
		}
		int pages =
			(int)Math.ceil(Client.wurst.cmdManager.countCommands() / 8D);
		if(MiscUtils.isInteger(args[0]))
		{
			int page = Integer.valueOf(args[0]);
			if(page > pages || page < 1)
				syntaxError("Invalid page: " + page);
			Client.wurst.chat.message("Available commands: "
				+ Client.wurst.cmdManager.countCommands());
			Client.wurst.chat.message("Command list (page " + page + "/"
				+ pages + "):");
			Iterator<Cmd> itr =
				Client.wurst.cmdManager.getAllCommands().iterator();
			for(int i = 0; itr.hasNext(); i++)
			{
				Cmd cmd = itr.next();
				if(i >= (page - 1) * 8 && i < (page - 1) * 8 + 8)
					Client.wurst.chat.message(cmd.getName());
			}
		}else
		{
			Cmd cmd = Client.wurst.cmdManager.getCommandByName(args[0]);
			if(cmd != null)
			{
				Client.wurst.chat.message("Available help for ." + args[0]
					+ ":");
				cmd.printHelp();
				cmd.printSyntax();
			}else
				error("Command \"" + args[0] + "\" could not be found.");
		}
	}
}
