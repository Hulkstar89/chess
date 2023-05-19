package chess;



import java.util.ArrayList;


public class pieces {
    public String location;
    public String name;
    public boolean moreThenOnce = false;
    public ArrayList<String> validMoves = new ArrayList<String>();

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void move() {

    }

    

   
   
    
}

class pawn extends pieces {

    pawn(String location, String name) {
        this.location = location;
        this.name = name;
        char[] locationArr = this.location.toCharArray();
        String l = Character.toString(locationArr[0]);
        int f;
        String subName = this.name.substring(0, 1);

        if (subName.equals("w")) {
            f = Integer.parseInt(Character.toString(locationArr[1])) + 2;
        } else {
            f = Integer.parseInt(Character.toString(locationArr[1])) - 2;
        }

        validMoves.add(l + f);

        // System.out.println("name: " + name);
        // System.out.println("location:" + location);
    }

}

class rook extends pieces {

    rook(String location, String name) {
        this.location = location;
        this.name = name;
        // System.out.println("name: " + name);
        // System.out.println("location: " + location);
    }

}

class horse extends pieces {

    horse(String location, String name) {
        this.location = location;
        this.name = name;
        // System.out.println("name: " + name);
        // System.out.println("location: " + location);
    }
}

class bishop extends pieces {

    bishop(String location, String name) {
        this.location = location;
        this.name = name;
        // System.out.println("name: " + name);
        // System.out.println("location: " + location);
    }
}

class queen extends pieces {

    queen(String location, String name) {
        this.location = location;
        this.name = name;
        // System.out.println("name: " + name);
        // System.out.println("location: " + location);
    }
}

class king extends pieces {

    king(String location, String name) {
        this.location = location;
        this.name = name;
        // System.out.println("name: " + name);
        // System.out.println("location: " + location);
    }
}
