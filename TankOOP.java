import java.util.Random;

public class TankOOP {

    private int speed = 10;
    private int x;
    private int y;
    private int direction;

    private ActionField actionField;
    private BattleField battleField;

    public TankOOP(ActionField actionField, BattleField battleField) {
        this(actionField, battleField, 64, 512, 3);

    }

    public TankOOP(ActionField actionField, BattleField battleField, int x, int y, int direction) {
        this.actionField = actionField;
        this.battleField = battleField;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move() throws Exception {
        actionField.ProcessMove(this);
    }

    public void turn(int direction) throws Exception {
        this.direction = direction;
        actionField.ProcessTurn(this);
    }

    public void fire() throws Exception {
        Bullet bullet = new Bullet((x + 25), (y + 25), direction);
        actionField.ProcessFire(bullet);
    }


    public void moveToQadrant(int v, int h) throws Exception {
        String coordinates = actionField.getQuadrant(v, h);
        int y = Integer.parseInt(coordinates.split("_")[0]);
        int x = Integer.parseInt(coordinates.split("_")[1]);

        if (this.x < x) {
            while (this.x != x) {
                turn(1);
                fire();
                move();
            }
        } else {
            while (this.x != x) {
                turn(2);
                fire();
                move();
            }
        }

        if (this.y < y) {
            while (this.y != y) {
                turn(3);
                fire();
                move();
            }
        } else {
            while (this.y != y) {
                turn(4);
                fire();
                move();
            }
        }
    }

    public void moveRandom() throws Exception {
        Random random = new Random();
        while (true) {
            direction = random.nextInt(7);
            if (direction > 0) {
                fire();
                actionField.ProcessMove(this);
                fire();
            }
        }
    }


    public void clean() {

    }

    public void updateX(int x) {
        this.x += x;
    }

    public void updateY(int y) {
        this.y += y;
    }
}
