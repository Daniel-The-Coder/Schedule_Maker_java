import java.util.ArrayList;

/**
 * Created by Lord Daniel on 6/20/2016.
 */
public class Course {
    private int number;
    private String codeName;
    private String name;
    private int section;
    private ArrayList<Class> classes;
    private String instructor;
    private String location;

    public Course(int number, String codeName, String name, int section,
                  ArrayList<Class> classes, String instructor, String location){
        this.number=number;
        this.codeName=codeName;
        this.name=name;
        this.section=section;
        this.classes=classes;
        this.instructor=instructor;
        this.location=location;
    }

    public ArrayList<Class> getClasses(){
        return  this.classes;
    }

    @Override
    public String toString(){
        String st = "#  | "+codeName+"-"+name+", section "+Integer.toString(section)+"\n";
        st += "#  | Instructor: "+instructor+"\n";
        for (Class c:classes){
            st += "#  | "+c.toString()+"\n";
        }
        st+="#   ___________________";
        return st;
    }

}
