package com.example.kickselfmod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.client.gui.screens.TitleScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientChatEvent;

/**
 * 房主运行的是集成服务器，服务端断开连接不会让本地客户端离开房间。
 * 因此在客户端拦截本地房主输入的 /kick，并走正常的保存退出流程。
 */
@EventBusSubscriber(modid = "kickselfmod", value = Dist.CLIENT)
public final class KickSelfClient {
    private static final String COMMAND = "/kick";

    private KickSelfClient() {
    }

    @SubscribeEvent
    public static void onClientChat(ClientChatEvent event) {
        if (!COMMAND.equalsIgnoreCase(event.getMessage().trim())) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        IntegratedServer server = minecraft.getSingleplayerServer();
        if (server == null || minecraft.player == null || !minecraft.player.hasPermissions(2)) {
            return;
        }

        event.setCanceled(true);
        minecraft.execute(() -> {
            IntegratedServer currentServer = minecraft.getSingleplayerServer();
            if (currentServer != null && currentServer.isRunning()) {
                currentServer.halt(false);
            }
            minecraft.disconnect(new TitleScreen(), false);
        });
    }
}
