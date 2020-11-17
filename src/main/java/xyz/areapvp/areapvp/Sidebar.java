package xyz.areapvp.areapvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import xyz.areapvp.areapvp.level.Exp;
import xyz.areapvp.areapvp.level.PlayerInfo;
import xyz.areapvp.areapvp.level.PlayerModify;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sidebar
{
    public static Scoreboard getBoard(Player player)
    {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");

        final PlayerInfo info = PlayerModify.getInfo(player);
        if (info == null)
            return null;

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        final Scoreboard board = manager.getNewScoreboard();
        final Objective objective = board.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.YELLOW + ChatColor.BOLD.toString() + "THE PIT");

        objective.getScore(ChatColor.GRAY + format.format(new Date())).setScore(10);
        objective.getScore(ChatColor.WHITE.toString()).setScore(9);
        objective.getScore(ChatColor.WHITE + "Level: " + PlayerInfo.getPrefix(info.level, info.prestige)).setScore(8);
        objective.getScore(ChatColor.WHITE + "Needed XP: " +
                ChatColor.AQUA + (Exp.getExp(info.level + 1, info.prestige) - info.exp)).setScore(7);
        objective.getScore(ChatColor.ITALIC.toString()).setScore(6);
        objective.getScore(ChatColor.WHITE + "Gold: " + ChatColor.GOLD + AreaPvP.economy.getBalance(player)).setScore(5);
        objective.getScore(ChatColor.YELLOW.toString()).setScore(4);
        if (!player.hasMetadata("x-hitted"))
            objective.getScore(ChatColor.WHITE + "Status: " + ChatColor.GREEN + "Idling").setScore(3);
        else
        {
            Integer hitted = null;
            for (MetadataValue hitter: player.getMetadata("x-hitted"))
                if (hitter.getOwningPlugin().getName().equals(AreaPvP.getPlugin().getName()))
                    hitted = hitter.asInt();
            if (hitted == null)
                objective.getScore(ChatColor.WHITE + "Status: " + ChatColor.GREEN + "Idling").setScore(3);
            else
            {
                if (hitted > 5)
                    objective.getScore(ChatColor.WHITE + "Status: " + ChatColor.RED + "Fighting").setScore(3);
                else
                    objective.getScore(ChatColor.WHITE + "Status: " + ChatColor.RED + "Fighting" + ChatColor.GRAY +
                            "(" + hitted + ChatColor.GRAY + ")").setScore(3);
            }
        }

        objective.getScore(ChatColor.BLUE.toString()).setScore(2);
        objective.getScore(ChatColor. BLACK.toString()).setScore(1);
        player.setPlayerListName(PlayerInfo.getPrefix(info.level, info.prestige) + ChatColor.GRAY + " " + player.getName());
        return board;
    }

}
