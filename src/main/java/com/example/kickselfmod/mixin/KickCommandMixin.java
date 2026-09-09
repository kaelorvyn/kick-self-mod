package com.example.kickselfmod.mixin;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.KickCommand;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

/** Replaces vanilla kick execution so the integrated-server owner is not protected. */
@Mixin(KickCommand.class)
public class KickCommandMixin {
    @Inject(method = "kickPlayers", at = @At("HEAD"), cancellable = true)
    private static void kickSelfMod$kickPlayers(
            CommandSourceStack source,
            Collection<ServerPlayer> players,
            Component reason,
            CallbackInfoReturnable<Integer> callback) {
        for (ServerPlayer player : players) {
            player.connection.disconnect(reason);
            source.sendSuccess(
                    () -> Component.translatable("commands.kick.success", player.getDisplayName(), reason),
                    true
            );
        }
        callback.setReturnValue(players.size());
    }
}
