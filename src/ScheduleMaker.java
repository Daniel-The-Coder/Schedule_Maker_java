import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Lord Daniel on 6/20/2016.
 */
public class ScheduleMaker {
    public static void main(String[] args) throws FileNotFoundException {
        String folder = args[0];
        ArrayList<File> files = new ArrayList<>(Arrays.asList(new File(folder).listFiles()));
        ScheduleConfig init = new ScheduleConfig(files);
        BFSolver BFS = new BFSolver();
        double start = System.currentTimeMillis(); // start the clock
        ArrayList<ScheduleConfig> ar = new ArrayList<>();
        ar.add(init);
        ArrayList<ScheduleConfig> solution = BFS.solve(ar);
        if (solution == null){
            System.out.println("No solution found");
        }

        //to find average end time for each schedule
        for (ScheduleConfig s:solution){
            HashMap<String, Integer> map = new HashMap<>();
            for (Course c:s.getSchedule()){
                for (Class cl:c.getClasses()){
                    if (map.containsKey(cl.getDay())){
                        if (map.get(cl.getDay())<cl.getEndTime()){
                            map.put(cl.getDay(), cl.getEndTime());
                        }
                    }
                    else{
                        map.put(cl.getDay(), cl.getEndTime());
                    }
                }
            }
            //calculate average end time and update value in the schedule configuration
            float y = 0;
            for (String k:map.keySet()){
                y += (float)map.get(k);
            }
            float ave = (float)y/5;
            s.aveEndTime=ave;
        }

        Collections.sort(solution, new classEndEarlyComparator());
        //System.out.println(solution.get(0).getSchedule().size());
        // compute the elapsed time
        System.out.println("Elapsed time: " +
                (System.currentTimeMillis() - start)/1000.0 + " seconds");
        System.out.println("\nSOLUTIONS");
        int x=1;
        for (ScheduleConfig s:solution){
            System.out.println("\n"+x);
            System.out.println(s.stringFormat());
            x++;
        }
    }
}