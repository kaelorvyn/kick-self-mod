package com.example.kickselfmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

/**
 * /kick 指令：让执行者（通常是房主/OP）把自己踢下线。
 * 仅 OP（权限等级 2）可用。
 * 用法：/kick
 */
public class KickSelfCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("kick")
                        .requires(src -> src.hasPermission(2)) // 仅 OP
                        .executes(ctx -> kickSelf(ctx.getSource()))
        );
    }

    private static int kickSelf(CommandSourceStack source) {
        ServerPlayer player = source.getPlayer();
        if (player == null) {
            return 0;
        }
        // 断开连接并显示提示
        player.connection.disconnect(Component.literal("你已将自己踢出服务器。"));
        return 1;
    }
}
