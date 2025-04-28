import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SafeHouse extends NormalLocation {
    private Stage stage;

    public SafeHouse(Player player, Stage stage) {
        super(player, "Safe House", stage);
        this.stage = stage;
    }

    @Override
    public boolean onLocation() {
        Platform.runLater(this::showSafeHouseScreen);
        return true;
    }

    private void showSafeHouseScreen() {
        VBox layout = new VBox(20);
        layout.setStyle("-fx-padding: 20;");

        Label labelStatus = new Label("You are in a safe area. Your health has been restored.");
        labelStatus.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(e -> backToMainScreen());

        layout.getChildren().addAll(labelStatus, continueButton);

        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);

        // Restore player health
        this.getPlayer().sethealth(this.getPlayer().getDefaultHealth());
    }

    private void backToMainScreen() {
        VBox menu = new VBox(20);
        menu.setStyle("-fx-padding: 20;");
        Label label = new Label("Where do you want to go next?");
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button safeHouseButton = new Button("Safe House");
        Button shopButton = new Button("Shop");
        Button forestButton = new Button("Forest");
        Button caveButton = new Button("Cave");
        Button castleButton = new Button("Castle");

        safeHouseButton.setOnAction(e -> {
            SafeHouse safeHouse = new SafeHouse(getPlayer(), stage);
            safeHouse.onLocation();
        });

        shopButton.setOnAction(e -> {
            Shop shop = new Shop(getPlayer(), stage);
            shop.onLocation();
        });

        forestButton.setOnAction(e -> {
            BattleLocation forest = new BattleLocation(getPlayer(), "Cave", new Zombie(), "Money", 3, stage);
            forest.onLocation();
        });

        caveButton.setOnAction(e -> {
            BattleLocation cave = new BattleLocation(getPlayer(), "Cave", new Zombie(), "Money", 3, stage);
            cave.onLocation();
        });

        castleButton.setOnAction(e -> {
            Castle castle = new Castle(getPlayer(), stage);
            castle.onLocation();
        });

        menu.getChildren().addAll(label, safeHouseButton, shopButton, caveButton, castleButton);

        Scene scene = new Scene(menu, 600, 400);
        stage.setScene(scene);
    }
}
