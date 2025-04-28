import javafx.stage.Stage;

public class NormalLocation extends Location {
    protected Stage stage;

    public NormalLocation(Player player, String name, Stage stage) {
        super(player, name);
        this.stage = stage;
    }
}
