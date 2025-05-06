import javafx.stage.Stage;

public class Cave extends BattleLocation {
    public Cave(Player player, Stage stage) {
        super(player, "Cave", new Skeleton(), "Money", 3, stage);
    }
}
