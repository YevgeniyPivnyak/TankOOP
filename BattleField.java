
public class BattleField {

    public int BF_WIDTH = 576;
    public int BF_HEIGHT = 576;

    String[][] battleField = {
            {"B ", " ", "B", "B", "B", "B", "B", " ", " "},
            {"B", " ", " ", " ", " ", " ", " ", " ", "B"},
            {"B", "B", "B", " ", "B", " ", "B", "B", "B"},
            {"B", " ", "B", " ", "B", " ", "B", " ", "B"},
            {"B", "B", "B", " ", "B", " ", "B", "B", "B"},
            {" ", "B", " ", "B", "B", "B", " ", "B", " "},
            {" ", "B", " ", " ", " ", " ", " ", "B", " "},
            {"B", " ", " ", "B", "B", "B", " ", " ", "B"},
            {"B", " ", " ", "B", "B", "B", " ", " ", "B"}
    };

    BattleField() {
    }

    public int getBF_WIDTH() {
        return BF_WIDTH;
    }

    public int getBF_HEIGHT() {
        return BF_HEIGHT;
    }

    public String[][] getBattleField() {
        return battleField;
    }

    public String ScanQadrant(int x, int y) {
        if (x >= 0 && x < battleField[0].length && y >= 0 && y < battleField.length) {
            return battleField[x][y];
        }
        return null;
    }

    public void UpdateQadrant(int x, int y, String updateValue) {
       this.battleField[x][y] = updateValue;
//        if (x >= 0 && x < battleField[0].length && y >= 0 && y < battleField.length) {
//            battleField[x][y] = updateValue;
//        }
    }

    public int getDimentionX() {
        return battleField[0].length;
    }

    public int getDimentionY() {
        return battleField.length;
    }
}
