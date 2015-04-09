/*
 * Copyright � 2014 - 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.mods;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import tk.wurst_client.events.EventManager;
import tk.wurst_client.events.listeners.UpdateListener;
import tk.wurst_client.mods.Mod.Category;
import tk.wurst_client.mods.Mod.Info;

@Info(category = Category.COMBAT,
	description = "Automatically leaves the server when your health is low.\n"
		+ "Can bypass CombatLogger.",
	name = "AutoLeave")
public class AutoLeaveMod extends Mod implements UpdateListener
{
	@Override
	public void onEnable()
	{
		EventManager.update.addListener(this);
	}
	
	@Override
	public void onUpdate()
	{
		if(Minecraft.getMinecraft().thePlayer.getHealth() <= 8.0
			&& !Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode
			&& (!Minecraft.getMinecraft().isIntegratedServerRunning() || Minecraft
				.getMinecraft().thePlayer.sendQueue.getPlayerInfo().size() > 1))
		{
			Minecraft.getMinecraft().thePlayer.sendQueue
				.addToSendQueue(new C01PacketChatMessage("�"));
			setEnabled(false);
		}
	}
	
	@Override
	public void onDisable()
	{
		EventManager.update.removeListener(this);
	}
}
