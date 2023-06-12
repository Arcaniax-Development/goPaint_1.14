package dev.themeinerlp.bettergopaint.command;

import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import dev.themeinerlp.bettergopaint.BetterGoPaint;
import org.bukkit.entity.Player;

public final class ReloadCommand {

    private final BetterGoPaint betterGoPaint;

    public ReloadCommand(final BetterGoPaint betterGoPaint) {
        this.betterGoPaint = betterGoPaint;
    }

    @CommandMethod("bgp|gp reload")
    @CommandPermission("bettergopaint.command.admin.reload")
    public void onReload(Player player) {
        betterGoPaint.reload();
    }

}
