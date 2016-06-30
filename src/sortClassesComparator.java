import java.util.Comparator;

/**
 * Created by Lord Daniel on 6/22/2016.
 */
public class sortClassesComparator implements Comparator<Class> {
    public int compare(Class c1, Class c2){
        return Integer.compare(c1.getStartTime(),c2.getStartTime());
    }
}
