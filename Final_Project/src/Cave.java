import javafx.stage.Stage;

public class Cave extends BattleLocation {
    public Cave(Player player, Stage stage) {
        super(player, "Cave", new Skeleton(), "Food", 3, stage);
    }
}
