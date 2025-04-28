import javafx.stage.Stage;

public class Forest extends BattleLocation{
    public Forest(Player player, Stage stage) {
        super(player, "Forest", new Zombie(), "Wood", 3, stage);
    }
}
