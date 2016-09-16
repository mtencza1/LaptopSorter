

import java.util.Comparator;
import java.util.ArrayList;

public class Quicksorter<E>  implements Sorter{
   
    private Comparator<E> comp;
    private ArrayList<E> array;
    
    
    public Quicksorter(Comparator<E> comp, ArrayList<E> array){
        this.comp = comp;
        this.array = array;
    }
    
    @Override
    public void setComparator(Comparator comparator) {
        this.comp = comparator;
    }
    
    @Override
    public void sort() {
        sort(0,array.size()-1);
    }
 
    public void sort(int first, int n){
        if(first>=n){
            return;
        }
        int pivot = partition(first, n);
        sort(first,pivot-1);
        sort(pivot+1,n);
    }
    
    public int partition(int first,int n){
        E pivot = array.get(first);
        
        int i = first;
        int j = n;
        
        while(i<j){
            while(comp.compare(array.get(i),pivot) <= 0 && i<j ){i++;}
            while(comp.compare(array.get(j), pivot) == 1){j--;}
            if(i<j){
                swap(i,j);
            }
        }
        swap(first, j);
        return j;
    }

    public void swap(int x, int y){
        E temp = array.get(x);
        array.set(x, array.get(y));
        array.set(y, temp);
    }
    
}