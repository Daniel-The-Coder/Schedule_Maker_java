/**
 * Created by Lord Daniel on 6/20/2016.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class ScheduleConfig{

    public ArrayList<ArrayList<Course>> allCourses;
    private ArrayList<Course> schedule;
    private int curIndex;
    public float aveEndTime;

    /**
     * To create a list of all courses from files
     *
     *
     * @param files;
     */
    public ScheduleConfig(ArrayList<File> files) throws FileNotFoundException {
        allCourses = new ArrayList<>();
        for (File file:files){
            ArrayList<Course> thisCourse = new ArrayList<>();
            Scanner in = new Scanner(file);
            while (in.hasNextLine()){
                in.nextLine();
                String[] names = in.nextLine().split("-");
                String codeName = names[0];
                String name = names[1];
                String sectionAndNumber = in.nextLine();
                int section = Integer.parseInt(sectionAndNumber.substring(0,2));
                int s = sectionAndNumber.length();
                int number = Integer.parseInt(sectionAndNumber.substring(s-5,s));
                String instructor = in.nextLine();
                String[] dayTime = in.nextLine().split(" ");
                int startTime = Integer.parseInt(dayTime[1].split("-")[0]);
                int endTime = Integer.parseInt(dayTime[1].split("-")[1]);
                String days = dayTime[0];
                ArrayList<Class> classes = new ArrayList<>();
                for (int i=0;i<days.length();i+=2){
                    String day = days.substring(i,i+2);
                    //System.out.println(day);
                    Class klass = new Class(codeName, section, day, startTime, endTime);
                    classes.add(klass);
                }
                //System.out.println(codeName+": "+startTime+" - "+endTime);
                String location=in.nextLine();
                Course course = new Course(number,codeName,name,section,classes,instructor,location);
                thisCourse.add(course);
            }
            allCourses.add(thisCourse);
        }
        curIndex=0;
        schedule=new ArrayList<>();
    }

    /**
     * copy constructor
     */
    public ScheduleConfig(ScheduleConfig other){
        this.allCourses = new ArrayList<>(other.allCourses);
        this.schedule = new ArrayList<>(other.schedule);
        this.curIndex = new Integer(other.curIndex);
        this.nextCourse();
    }

    /**
     * no overlap
     */
    public boolean isValid(Course course){
        boolean valid = true;
        for (Course crs:this.schedule){
            for (Class cls1:crs.getClasses()){
                for (Class cls2:course.getClasses()){
                    //System.out.println("class1: "+cls1.getStartTime()+"-"+cls1.getEndTime()+",  class2: "+cls2.getStartTime()+"-"+cls2.getEndTime());
                    if(cls1.getDay().equals(cls2.getDay())) {
                        if (  cls1.getStartTime() > cls2.getStartTime() && cls1.getStartTime() < cls2.getEndTime() ){
                            valid=false;
                            //System.out.println(1);
                        }
                        if (  cls1.getEndTime() > cls2.getStartTime() && cls1.getEndTime() < cls2.getEndTime() ){
                            valid=false;
                            //System.out.println(2);
                        }
                        if ( cls1.getStartTime() < cls2.getStartTime() && cls1.getEndTime() > cls2.getEndTime() ){
                            valid=false;
                            //System.out.println(3);
                        }
                        if ( cls1.getStartTime() > cls2.getStartTime() && cls1.getEndTime() < cls2.getEndTime() ){
                            valid=false;
                            //System.out.println(4);
                        }
                        if ( cls1.getStartTime()==cls2.getStartTime() ){
                            valid=false;
                            //System.out.println(5);
                        }
                        if ( cls1.getEndTime()==cls2.getEndTime() ){
                            valid=false;
                            //System.out.println(6);
                        }
                    }
                }
            }
        }
        return valid;
    }

    /**
     * Check if all classes added
     */
    public boolean isGoal(){
       return this.schedule.size() == this.allCourses.size();
    }


    public int getCurIndex(){
        return this.curIndex;
    }

    /**
     * increment index by 1 to go to next course
     */
    public void nextCourse(){
        curIndex++;
    }

    public void addCourse(Course course){
        this.schedule.add(course);
    }

    public ArrayList<Course> getSchedule(){
        return  this.schedule;
    }

    @Override
    public String toString(){
        String st = "####################################################\n#   SCHEDULE\n#";
        for (Course c:this.schedule){
            st+="\n"+c.toString()+"\n#";
        }
        st += "###################################################";
        return st;
    }

    public HashMap<String, ArrayList<Class>> scheduleFormat(){
        HashMap<String, ArrayList<Class>> map = new HashMap<>();
        for (Course c:this.schedule){
            for (Class cl:c.getClasses()){
                if(!map.containsKey(cl.getDay())){
                    ArrayList<Class> ar = new ArrayList<>();
                    ar.add(cl);
                    map.put(cl.getDay(),ar);
                }
                else{
                    ArrayList<Class> ar = map.get(cl.getDay());
                    ar.add(cl);
                    Collections.sort(ar,new sortClassesComparator());
                    map.put(cl.getDay(),ar);
                }
            }
        }
        return map;
    }

    public String formatTime(int time){
        String st = Integer.toString(time);
        if (st.length()==3){
            st = "0"+st;
        }
        st = st.substring(0,2)+":"+st.substring(2,4);
        return st;
    }

    public String dayString(String day, HashMap schedule){
        ArrayList<Class> ar= (ArrayList<Class>)schedule.get(day);
        if(ar==null){
            return "";
        }
        String st = "# "+day+"\n";
        for (Class c:ar){
            st+="# | "+formatTime(c.getStartTime())+" - "+formatTime(c.getEndTime())+" : "+c.getName()+"-"+c.getSection()+"\n";
        }
        st+="#  __________________________\n";
        return st;
    }

    public String stringFormat(){
        HashMap sche = this.scheduleFormat();
        String st = "##############################\n# SCHEDULE\n";
        st+=dayString("Mo",sche);
        st+=dayString("Tu",sche);
        st+=dayString("We",sche);
        st+=dayString("Th",sche);
        st+=dayString("Fr",sche);
        st += "##############################\n";
        return st;
    }


}
