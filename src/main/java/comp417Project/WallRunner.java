package comp417Project;

import java.util.Random;

import burlap.oomdp.core.Domain;
import burlap.oomdp.core.states.State;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import edu.brown.cs.h2r.burlapcraft.BurlapCraft;
import edu.brown.cs.h2r.burlapcraft.dungeongenerator.Dungeon;
import edu.brown.cs.h2r.burlapcraft.helper.HelperActions;
import edu.brown.cs.h2r.burlapcraft.helper.HelperGeometry.Pose;
import edu.brown.cs.h2r.burlapcraft.helper.HelperPos;
import edu.brown.cs.h2r.burlapcraft.stategenerator.StateGenerator;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraft.server.*;
import java.util.Random;

/***
 * Moves forward until it hits a wall. Then it follows that wall.
 * @author Nicholas Nathan Colotouros
 */
public class WallRunner {
	
	public enum Direction { South, West, North, East }
	
	int actionWaitTime = 1500;
    Direction currentDirection;
    boolean followingWall;
    final int numIterations = 100; // Used so it the command eventually ends 
       
    public WallRunner(){
        followingWall = false;
    }
    
    public void wallRun(){
    	currentDirection = getCurrentDirection();

    	for(int i = 0; i < numIterations; i++){
	    	try{
	    		
	    		// If we're following a wall, we need to constantly turn left
	    		// to see if the wall went off to the side
	    		if(followingWall){ 
	    			turnLeft();
	    		
	    			// Wall went off to the left
	    			if(getBlockAhead() == 0){
	    				moveForward();
	    			}
	    			else {
	    				turnRight();
	    				
	    				if(getBlockAhead() != 0){ // Wall to the left and in front
	    					turnRight();
	    					
	    					if(getBlockAhead() != 0){ // Just entered an alcove
	    						turnRight();
	    					}
	    				}
	    				moveForward();
	    			}
	    		}
	    		else{
	    			if(getBlockAhead() == 0){
	    				moveForward();
	    			}
	    			else{
	    				followingWall = true;
	    				turnRight();
	    			}
	    		}
	    	}catch(InterruptedException e){e.printStackTrace();}
    	}
    }
    
    Direction getCurrentDirection() {
    	int yaw = Math.abs(HelperActions.getYawDirection());
    	return Direction.values()[yaw];
	}

	void printPos(){
		HelperPos curPos = HelperActions.getPlayerPosition();
        System.out.println("You are at "+ curPos.x + "," + curPos.y + "," + curPos.z);
    }
    
    int getBlockOnFloor(){
    	HelperPos curPos = HelperActions.getPlayerPosition();
        return HelperActions.getBlockId(curPos.x, curPos.y-1, curPos.z);
    }
    
    int getBlockAhead(){
    	HelperPos curPos = HelperActions.getPlayerPosition();
        
    	switch(currentDirection){
    	case North:
    		return HelperActions.getBlockId(curPos.x, curPos.y, curPos.z-1);
    		
    	case East:
    		return HelperActions.getBlockId(curPos.x+1, curPos.y, curPos.z);
    		
    	case South:
    		return HelperActions.getBlockId(curPos.x, curPos.y, curPos.z+1);
    		
    	case West:
    		return HelperActions.getBlockId(curPos.x-1, curPos.y, curPos.z);
    	}
    	return -1;
    }
    
    void turnRight() throws InterruptedException{
    	switch(currentDirection){
    	case North:
    		HelperActions.faceEast();
    		currentDirection = Direction.East;
    		break;
    		
    	case East:
    		HelperActions.faceSouth();
    		currentDirection = Direction.South;
    		break;
    		
    	case South:
    		HelperActions.faceWest();
    		currentDirection = Direction.West;
    		break;
    		
    	case West:
    		HelperActions.faceNorth();
    		currentDirection = Direction.North;
    	}
    	Thread.sleep(actionWaitTime);
    }

    void turnLeft() throws InterruptedException{
    	switch(currentDirection){
    	case North:
    		HelperActions.faceWest();
    		currentDirection = Direction.West;
    		break;
    		
    	case East:
    		HelperActions.faceNorth();
    		currentDirection = Direction.North;
    		break;
    		
    	case South:
    		HelperActions.faceEast();
    		currentDirection = Direction.East;
    		break;
    		
    	case West:
    		HelperActions.faceSouth();
    		currentDirection = Direction.South;
    	}
    	Thread.sleep(actionWaitTime);
    }
    
    void moveForward() throws InterruptedException{
    	HelperActions.moveForward(false);
    	Thread.sleep(actionWaitTime);
    }
}
