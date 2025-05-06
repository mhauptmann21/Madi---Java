import javafx.stage.Stage;

public class Forest extends BattleLocation{
    public Forest(Player player, Stage stage) {
        super(player, "Forest", new Zombie(), "Money", 3, stage);
    }
}
