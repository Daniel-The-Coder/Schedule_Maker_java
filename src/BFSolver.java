/**
 * Created by Lord Daniel on 6/20/2016.
 */


import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the classic recursive backtracking algorithm.
 * It has a solver that can take a valid BreadthFirst.ScheduleConfig and return a
 * solution, if one exists.
 *
 * This file comes from the backtracking lab. It should be useful
 * in this project. A second method has been added that you should
 * implement.
 *
 * @author Sean Strout @ RIT CS
 * @author James Heliotis @ RIT CS
 * @author Daniel Roy Barman
 */
public class BFSolver {



    /**
     * Initialize a new backtracker.
     *
     */
    public BFSolver() {

    }


    public ArrayList<ScheduleConfig> getSuccessors(ArrayList<ScheduleConfig> configs){
        ArrayList<ScheduleConfig> successors = new ArrayList<>();

        for (ScheduleConfig c: configs){
            ArrayList<Course>  curCourse = c.allCourses.get(c.getCurIndex());
            for (Course course: curCourse) {
                //check if valid, then add to arraylist successors
                if (c.isValid(course)){
                    ScheduleConfig newSchedule = new ScheduleConfig(c);
                    newSchedule.addCourse(course);
                    successors.add(newSchedule);
                }
            }
        }
        return successors;
    }


    /**
     * Try find a solution, if one exists, for a given BreadthFirst.ScheduleConfig.
     *
     * @param configs A valid BreadthFirst.ScheduleConfig
     * @return A solution config, or null if no solution
     */
    public ArrayList<ScheduleConfig> solve(ArrayList<ScheduleConfig> configs) {

        if (configs.size() == 0){
            return null;
        }
        int n=0;
        if ( configs.get(0).isGoal() ){
            System.out.println(configs.size()+" possible schedules found.\n");
            return configs;
        }
        //if no solution found here
        ArrayList<ScheduleConfig> successors = getSuccessors(configs);
        return solve(successors);

    }

}


