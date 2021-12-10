import java.util.*;

public class BOX {
    private String boxName;
    private ArrayList<Entry> entries;

    private int size;



    public BOX(String name) {
        this.setBoxName(name);
        this.setSize(0);
        this.entries = new ArrayList<Entry>();
    }
    

    public ArrayList<Entry> getLimitedEntries(int numOfEntries) {
        ArrayList<Entry> limitedEntries = new ArrayList<Entry>();
        for (int i = 0; i < numOfEntries; i ++){
            limitedEntries.add(this.entries.get(size-i));
        }
        return limitedEntries;
    }

    public void printEntries(int numOfEntries){
        ArrayList<Entry> limitedEntries = getLimitedEntries(numOfEntries);
        for (Entry entry : limitedEntries){
            System.out.print(entry.toString() + "; ");
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void addEntry(String inText){
        Entry newEntry = new Entry(inText);
        entries.add(newEntry);
        this.size += 1;
    }
    public ArrayList<Entry> getEntries(){
        return this.entries;
    }
    
    public String getName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }
    
    public String toString(){
        String outString = "";
        for (Entry entry : this.entries){
            outString += entry.toString() + "; ";
        } 
        return outString;
    }
}