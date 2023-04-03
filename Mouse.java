import java.util.Random;

public class Mouse extends Creature {
    

    private int dir;
    protected int x;
    protected int y;
    private int round = 0;
    

    public Mouse(int x, int y, City cty, Random rnd) {
        super(x, y, cty, rnd);
        this.lab = LAB_BLUE;
        this.round=0;
        this.stepLen=1;
    }

    public void step()
    {
        int dc = stepLen;

       //This method changes the direction 20% of the time by having dir = a random value of DIRS
        if(rand.nextInt(100) <= 20){
            dir = DIRS[rand.nextInt(DIRS.length)];
        randomTurn();
        }

        switch(dir){
            case EAST: //East
            this.point.x += dc;
            break;
            case SOUTH: //South
            this.point.y += dc;
            break;
            case WEST: //West
            this.point.x -= dc;
            break;
            case NORTH: //North
            this.point.y -= dc;
            break;
            default:
            break;
        }

    }

    //public void randomChangeDirection(){
     //   if(rand.nextInt(100) <= 20);
       // randomTurn();
    //}
        
    public void takeAction(){

        round++;

        if(round == 20){
            //adds a mouse in the same gridpoints
            city.creaturesToAdd.add(new Mouse(getX(), getY(), city, rand));
            //new Mouse(getX(), getY(), city, rand);

        }
        else if (round==30){
            //dies after 30 rounds
            dead =true;

        }
        else if(round % 100 == 0){
            //adds a new mouse every 100 rounds at a random point
            city.addMouse();
            //int RandPointX = rand.nextInt(rand.nextInt(80));
            //city.creaturesToAdd.add(new addMouse(x, y, city, rand));
        }
        //round++;
    }

}
