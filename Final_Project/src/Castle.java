import javafx.stage.Stage;

public class Castle extends BattleLocation {
    public Castle(Player player, Stage stage) {
        super(player, "Castle", new Vampire(), "Money", 2, stage);
    }

}
