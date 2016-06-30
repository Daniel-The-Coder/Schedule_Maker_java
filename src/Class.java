/**
 * Created by Lord Daniel on 6/20/2016.
 */
public class Class {
    private String name;
    private String day;
    private int startTime;
    private int endTime;
    private int section;

    public Class(String name, int section, String day, int startTime, int endTime){
        this.day=day;
        this.startTime=startTime;
        this.endTime=endTime;
        this.name=name;
        this.section=section;
    }

    public String getName(){
        return this.name;
    }

    public String getDay(){
        return this.day;
    }

    public int getStartTime(){
        return this.startTime;
    }

    public int getEndTime(){
        return this.endTime;
    }

    public int getSection(){
        return this.section;
    }

    @Override
    public String toString(){
        String st = day+", "+Integer.toString(startTime/100)+":";
        if (startTime%100 <10) {
            st += "0" + Integer.toString(startTime % 100) + " - ";
        }
        else {
            st += Integer.toString(startTime % 100) + " - ";
        }
        st += Integer.toString(endTime/100)+":";
        if (endTime%100 <10) {
            st += "0" + Integer.toString(endTime % 100) + " - ";
        }
        else {
            st += Integer.toString(endTime % 100);
        }
        return st;
    }
}
