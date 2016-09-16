
import java.util.Comparator;

public interface Sorter<E> {
    
    void sort();
    
    void setComparator(Comparator<E> comparator);
    
    
}
