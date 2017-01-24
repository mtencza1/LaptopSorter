



import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;


public class Laptop {
    String brand;
    double procSpeed;
    int memory;
    int hdd;
    int id;
    static SQLiteDB warehouse;

    private static ArrayList<Laptop> laptops = new ArrayList<>();
    
    public Laptop(String brand, double procSpeed, int memory, int hdd,int id){
        this.brand = brand;
        this.procSpeed = procSpeed;
        this.memory = memory;
        this.hdd = hdd;
        this.id = id;

    }

    public String getBrand() {
        return brand;
    }
    public int getID(){return id;}

    public double getProcSpeed() {
        return procSpeed;
    }

    public int getMemory() {
        return memory;
    }

    public int getHdd() {
        return hdd;
    }


    static Comparator<Laptop> brandComparator = (o1,o2) -> {
        int a,b;
        a = Character.getNumericValue(o1.brand.charAt(0));
        b = Character.getNumericValue(o2.brand.charAt(0));
        if(a<b){
            return -1;
        }
        else if(a>b){
            return 1;
        }
        else{
            return 0;
        }
    };
    

    static Comparator<Laptop> processorComparator = new Comparator<Laptop>() {
        @Override
        public int compare(Laptop o1, Laptop o2) {
            if(o1.procSpeed < o2.procSpeed){
                return -1;
            }
            else if(o1.procSpeed > o2.procSpeed){
                return 1;
            }
            else{
                return 0;
            }
        }
    };
    
    static Comparator<Laptop> memoryComparator = new Comparator<Laptop>() {
        @Override
        public int compare(Laptop o1, Laptop o2) {
            if(o1.memory<o2.memory){
                return -1;
            }
            else if(o1.memory>o2.memory){
                return 1;
            }
            else{
                return 0;
            }
        }
    };
    
    static Comparator<Laptop> hddComparator = new Comparator<Laptop>() {
        @Override
        public int compare(Laptop o1, Laptop o2) {
            if(o1.hdd<o2.hdd){
                return -1;
            }
            else if(o1.hdd>o2.hdd){
                return 1;
            }
            else{
                return 0;
            }
        }
    };

    public String toString(){
        String s = "";
        s+= " Brand: " + brand + "  " + procSpeed + " processor ,  "
            + memory + "GB RAM   " + hdd + "GB HDD";
        return s; 
    }
    
    public static ArrayList<Laptop> readLaptopInputs(Scanner scanner){
        System.out.println("Input each laptop specs: brand,procSpeed,memory, and hdd on a single"
                + "line seperated by spaces.");
        System.out.println("Press enter to type another or Type 'done' when finished inputing laptops");
        Connection c = warehouse.getConection();

        String inputData = "";
        int counter = 0;
        try {
            laptops.add(new Laptop("Lenovo", 50, 20, 300, warehouse.getMaxID(c) +1));
            laptops.add(new Laptop("Dell", 70, 30, 400, warehouse.getMaxID(c) +2));
            laptops.add(new Laptop("Asus", 80, 40, 500, warehouse.getMaxID(c) +3));

            while (!inputData.equals("done")) {
                if (counter > 0) {
                    System.out.println("Input  laptop or type done");
                }
                inputData = scanner.nextLine();
                String[] s = inputData.split(" ");
                if (inputData.equals("done")) {
                    break;
                }
                //change to +1 when you get rid of other laptop add
                Laptop l = new Laptop(s[0], Double.parseDouble(s[1]), Integer.parseInt(s[2])
                        , Integer.parseInt(s[3]), warehouse.getMaxID(c) +2);
                laptops.add(l);

                counter++;

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return laptops;
    }
    
    public static ArrayList<Integer> readIntegerInputs(Scanner scanner){
        System.out.println("Input ints seperated by spaces on a single line");
        String inputData = scanner.nextLine();
       
        ArrayList<Integer> integers = new ArrayList();
       
        String[] s = inputData.split("[^\\d]+");

        for(int i = 0; i < s.length; i++){
            integers.add(Integer.parseInt(s[i]));
        }
        return integers; 
    }
    
    public static String getStringJoinedBy(ArrayList a,String s){
        String list = "";
        for(int i =0;i<a.size();i++){
            list+= "{";
            list+=a.get(i);
            list+= "}";
            list+=s;
        }
        return list;
    }
    

    public static void main(String[] args) {
        warehouse = new SQLiteDB("Laptop Warehouse");
        warehouse.createTable();
        warehouse.selectAll();

        Scanner scanner = new Scanner(System.in);


        ArrayList<Laptop> laptops = readLaptopInputs(scanner);
        for(Laptop laptop:laptops){
            System.out.println(laptop.getID());
            warehouse.insert(laptop);
        }
        warehouse.selectAll();

        Quicksorter<Laptop> laptopSorter = new Quicksorter<>(brandComparator, laptops);
        warehouse.sort("RAM");

        laptopSorter.sort();
        System.out.print("Sorted by brand name:\n\t");
        System.out.println(getStringJoinedBy(laptops, "\n\t"));
        System.out.println();
        laptopSorter.setComparator(processorComparator);
        laptopSorter.sort();
        System.out.print("Sorted by processor speed:\n\t");
        System.out.println(getStringJoinedBy(laptops, "\n\t"));
        System.out.println();

        laptopSorter.setComparator(memoryComparator);
        laptopSorter.sort();
        System.out.print("Sorted by RAM:\n\t");
        System.out.println(getStringJoinedBy(laptops, "\n\t"));
        System.out.println();
                
        laptopSorter.setComparator(hddComparator);
        laptopSorter.sort();
        System.out.print("Sorted by hard disk capacity:\n\t");
        System.out.println(getStringJoinedBy(laptops, "\n\t"));

        }

    
}


