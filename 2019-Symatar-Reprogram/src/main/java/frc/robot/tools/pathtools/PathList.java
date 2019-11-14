package frc.robot.tools.pathtools;

import java.io.File;
import java.io.IOException;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class PathList {
  private File testFile1 = new File("/home/lvuser/deploy/test1.pf1.csv");
  private File testFile2 = new File("/home/lvuser/deploy/test2.pf1.csv");

  public PathSetup test2Path;
  public PathSetup test1Path;


   //remember that for all paths if the first point is at (0,0,0) for some reason the end y value is revesred in the coordinate plane
  //for example for a path from (x,y,h) to (0,0,0) a path that goes from (0,0,0) to (x,y,h) would look the same but for one you would 
  // be decreasing y units on the coordinate plane, while in the other you would be increasing y units
  public PathList() {
    test1Path = new PathSetup(testFile1, false);
    test2Path = new PathSetup(testFile2, true);

	}
    
  
  public void resetAllPaths(){
  }
}