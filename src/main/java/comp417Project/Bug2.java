package comp417Project;

import java.util.LinkedList;

import edu.brown.cs.h2r.burlapcraft.dungeongenerator.Dungeon;
import edu.brown.cs.h2r.burlapcraft.helper.HelperActions;
import edu.brown.cs.h2r.burlapcraft.helper.HelperGeometry.Pose;
import edu.brown.cs.h2r.burlapcraft.helper.HelperPos;

/***
 * Extends the WallRunner class to take advantage of the convenience methods.
 * Implements the bug2 algorithm which follows a wall and then follows the S-Line.
 * 
 * The S-Line being the line from the start point to the goal.
 * @author Nicholas Nathan Colotouros
 *
 */
public class Bug2 extends WallRunner{
	HelperPos startingPos;
	Pose goal;
	LinkedList<Pose> sline;
    
	public Bug2(Pose goalPose) {
		super();
		startingPos = HelperActions.getPlayerPosition();
		goal = goalPose;
	}
	
	/***
	 * Initializes all states from the start state (exclusively) to the goal state (inclusively).
	 * sline is empty if the bot is already at the goal.
	 */
	private void initializeSLine() {
		sline = new LinkedList<Pose>();
		
		// check for equal points and infinite slopes
		if(goal.getZ() == startingPos.z){
			if(goal.getX() == startingPos.x){
				return;
			}
			
			else{
				// TODO 
			}
		}
		// the line is of the form z = ax+b
		double a = (startingPos.x - goal.getX()) / (startingPos.z - goal.getZ());
		double b = startingPos.z - a*startingPos.x;
		
		// TODO
	}

	public void runBug(){
		initializeSLine();

		// TODO		
	}
}