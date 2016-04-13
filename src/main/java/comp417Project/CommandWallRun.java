package comp417Project;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import edu.brown.cs.h2r.burlapcraft.BurlapCraft;
import edu.brown.cs.h2r.burlapcraft.dungeongenerator.Dungeon;
import edu.brown.cs.h2r.burlapcraft.solver.MinecraftSolver;
import edu.brown.cs.h2r.burlapcraft.stategenerator.StateGenerator;

/***
 * Chat command for bug0
 * 
 * @author Nicholas Nathan Colotouros
 *
 */
public class CommandWallRun implements ICommand {

	private final List aliases;
	
	public CommandWallRun() {
		aliases = new ArrayList();
		aliases.add("wallrun");
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "wallrun";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "wallrun -- no arguments";
	}

	@Override
	public List getCommandAliases() {
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		World world = sender.getEntityWorld();
		if (!world.isRemote) {
			if (args.length > 0) {
				sender.addChatMessage(new ChatComponentText("This command does not take any optional arguments."));
				return;
			}
			
			Dungeon dungeon = BurlapCraft.currentDungeon;
			
			if (dungeon == null) {
				sender.addChatMessage(new ChatComponentText("You are not inside a dungeon"));
				return;
			}

			Thread bthread = new Thread(new Runnable() {
				@Override
				public void run() {
					MinecraftSolver.plan(2, false, false);
				}
			});

			bthread.start();


		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_,
			String[] p_71516_2_) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		return false;
	}
	
}
