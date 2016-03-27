import javax.swing.*;
import java.awt.*;

public class ActionField extends JPanel {

    final boolean COLORDED_MODE = false;

    private BattleField battleField;
    private TankOOP tankOOP;
    private Bullet bullet;

    void runTheGame() throws Exception {
//        tankOOP.turn(1);
//        tankOOP.move();
//        tankOOP.fire();
//        tankOOP.turn(2);
//        tankOOP.fire();
//        tankOOP.turn(3);
//        tankOOP.fire();
//        tankOOP.turn(4);
//        tankOOP.fire();
//        tankOOP.move();
        tankOOP.moveRandom();
    }

    boolean processInterception() {

        String coordinates = getQuadrant(bullet.getX(), bullet.getY());
        int y = Integer.parseInt(coordinates.split("_")[0]);
        int x = Integer.parseInt(coordinates.split("_")[1]);

        if ((x >= 0 && x < 9) && (y >= 0 && y < 9)) {
            if (battleField.ScanQadrant(x, y) != " ") {
                battleField.UpdateQadrant(y, x, " ");
                return true;
            }
        }
        return false;
    }

    public String getQuadrant(int x, int y) {
        return y / 64 + "_" + x / 64;
    }

    public String getQuadrantXY(int v, int h) {
        return (v - 1) * 64 + "_" + (h - 1) * 64;
    }

    void ProcessTurn(TankOOP tankOOP) throws Exception {
        repaint();
    }

    void ProcessMove(TankOOP tankOOP) throws Exception {
        this.tankOOP = tankOOP;
        int step = 1;
        int covered = 0;

        // check limits x: 0, 513; y: 0, 513
        if ((tankOOP.getDirection() == 1 && tankOOP.getY() == 0) || (tankOOP.getDirection() == 2 && tankOOP.getY() >= 512)
                || (tankOOP.getDirection() == 3 && tankOOP.getX() == 0)
                || (tankOOP.getDirection() == 4 && tankOOP.getX() >= 512)) {
            System.out.println("[illegal move] direction: " + tankOOP.getDirection()
                    + " tankX: " + tankOOP.getX() + ", tankY: " + tankOOP.getY());
            return;
        }

        tankOOP.turn(tankOOP.getDirection());

        while (covered < 64) {
            if (tankOOP.getDirection() == 1) {
                tankOOP.updateY(-step);
                System.out.println("[move up] direction: " + tankOOP.getDirection()
                        + " tankX: " + tankOOP.getX() + ", tankY: " + tankOOP.getY());
            } else if (tankOOP.getDirection() == 2) {
                tankOOP.updateY(+step);
                System.out.println("[move down] direction: " + tankOOP.getDirection()
                        + " tankX: " + tankOOP.getX() + ", tankY: " + tankOOP.getY());
            } else if (tankOOP.getDirection() == 3) {
                tankOOP.updateX(-step);
                System.out.println("[move left] direction: " + tankOOP.getDirection()
                        + " tankX: " + tankOOP.getX() + ", tankY: " + tankOOP.getY());
            } else {
                tankOOP.updateX(+step);
                System.out.println("[move right] direction: " + tankOOP.getDirection()
                        + " tankX: " + tankOOP.getX() + ", tankY: " + tankOOP.getY());
            }
            covered += step;

            repaint();
            Thread.sleep(tankOOP.getSpeed());
        }
    }


    void ProcessFire(Bullet bullet) throws Exception {
        this.bullet = bullet;
        int step = 1;
        while ((bullet.getX() > -14 && bullet.getX() < 590)
                && (bullet.getY() > -14 && bullet.getY() < 590)) {
            if (tankOOP.getDirection() == 1) {
                bullet.updateY(-step);
            } else if (tankOOP.getDirection() == 2) {
                bullet.updateY(+step);
            } else if (tankOOP.getDirection() == 3) {
                bullet.updateX(-step);
            } else {
                bullet.updateX(+step);
            }
            if (processInterception() == true) {
                bullet.destroy();
                break;
            }
            repaint();
            Thread.sleep(bullet.getSpeed());
        }

    }

    public ActionField() throws Exception {

        battleField = new BattleField();
        tankOOP = new TankOOP(this, battleField);
        bullet = new Bullet(-100, -100, -1);

        JFrame frame = new JFrame("BATTLE FIELD OOP");
        frame.setLocation(500, 70);
        frame.setMinimumSize(new Dimension(battleField.getBF_WIDTH() + 18, battleField.getBF_WIDTH() + 45));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int i = 0;
        Color cc;
        for (int v = 0; v < 9; v++) {
            for (int h = 0; h < 9; h++) {
                if (COLORDED_MODE) {
                    if (i % 2 == 0) {
                        cc = new Color(252, 241, 177);
                    } else {
                        cc = new Color(233, 243, 255);
                    }
                } else {
                    cc = new Color(180, 180, 180);
                }
                i++;
                g.setColor(cc);
                g.fillRect(h * 64, v * 64, 64, 64);
            }
        }

        for (int j = 0; j < battleField.getDimentionY(); j++) {
            for (int k = 0; k < battleField.getDimentionX(); k++) {
                if (battleField.ScanQadrant(j, k).equals("B")) {
                    String coordinates = getQuadrantXY(j + 1, k + 1);
                    int separator = coordinates.indexOf("_");
                    int y = Integer.parseInt(coordinates
                            .substring(0, separator));
                    int x = Integer.parseInt(coordinates
                            .substring(separator + 1));
                    g.setColor(new Color(0, 0, 99));
                    g.fillRect(x, y, 64, 64);
                }
            }
        }

        g.setColor(new Color(255, 0, 0));
        g.fillRect(tankOOP.getX(), tankOOP.getY(), 64, 64);

        g.setColor(new Color(0, 255, 0));
        if (tankOOP.getDirection() == 1) {
            g.fillRect(tankOOP.getX() + 20, tankOOP.getY(), 24, 34);
        } else if (tankOOP.getDirection() == 2) {
            g.fillRect(tankOOP.getX() + 20, tankOOP.getY() + 30, 24, 34);
        } else if (tankOOP.getDirection() == 3) {
            g.fillRect(tankOOP.getX(), tankOOP.getY() + 20, 34, 24);
        } else {
            g.fillRect(tankOOP.getX() + 30, tankOOP.getY() + 20, 34, 24);
        }

        g.setColor(new Color(255, 255, 0));
        g.fillRect(bullet.getX(), bullet.getY(), 14, 14);

    }
}
