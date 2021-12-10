import java.util.*;

public class UserList {
    private ArrayList<User> users;

    private int size;



    public UserList() {
        this.size = 0;
        this.users = new ArrayList<User>();
    }
    

    public boolean isEmpty(){
        boolean truth = false;
        if (this.users.isEmpty()){
            truth = true;
        }
        return truth;
    }

    public boolean contains(User inUser){
        boolean truth = false;
        if (this.users.contains(inUser)){
            truth = true;
        }
        return truth;
    }
    public void printUsers(){
        
        for (User user : this.users){
            System.out.println(user.toString());
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void makeUser(String inText){
        User newUser = new User(inText);
        this.users.add(newUser);
        this.size += 1;
    }
    public void addUser(User inUser){
        this.users.add(inUser);
        this.size += 1;
    }
    public ArrayList<User> getUsers(){
        if (this.size <= 0){
            System.out.println("There are no users!");
            
        }
        return this.users;
    }
   
    
    public String toString(){
        String outString = "";
        for (User user : this.users){
            outString += user.toString() + "\n";
        } 
        return outString;
    }
}