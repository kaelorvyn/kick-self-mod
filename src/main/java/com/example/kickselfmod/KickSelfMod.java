package com.example.kickselfmod;

import com.example.kickselfmod.commands.KickSelfCommand;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

/**
 * 主入口类。
 */
@Mod(KickSelfMod.MOD_ID)
public class KickSelfMod {
    public static final String MOD_ID = "kickselfmod";

    public KickSelfMod() {
        NeoForge.EVENT_BUS.addListener(KickSelfMod::onRegisterCommands);
    }

    public static void onRegisterCommands(RegisterCommandsEvent event) {
        KickSelfCommand.register(event.getDispatcher());
    }
}
