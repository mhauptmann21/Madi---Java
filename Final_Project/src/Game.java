import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game {
    private Player player;
    private Label storyLabel;
    private Stage primaryStage;
    private int gameStage = 0; // 0: entering name, 1: choosing character, 2: selecting location

    public Game(Stage stage) {
        this.primaryStage = stage;
    }

    public void start() {
        storyLabel = new Label("They're awake! They're awake!\nDo you know your name?\nEnter now:");
        storyLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField inputField = new TextField();
        Button submitButton = new Button("Submit");

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: left;");
        layout.getChildren().addAll(storyLabel, inputField, submitButton);

        Scene scene = new Scene(layout, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Adventure Game");
        primaryStage.show();

        submitButton.setOnAction(e -> handleInput(inputField));
    }

    private void handleInput(TextField inputField) {
        String inputText = inputField.getText().trim();
        inputField.clear();

        if (gameStage == 0) {
            player = new Player(inputText);
            storyLabel.setText("It's nice to meet you, " + player.getName() + "!\n\nIt is now time to choose who you will become.\nThe valley has been overturned by monsters... We need your help!");
            showCharacterSelection();
            gameStage = 1;
        }
    }

    private void selectCharacter(Character character) {
        player.initPlayer(character);
        storyLabel.setText("You chose: " + player.getCharacterName() + "!\n\nNow, where would you like to travel?");
        gameStage = 2;
        showLocationOptions();
    }

    private void showCharacterSelection() {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: left;");

        Label title = new Label("Choose your character:");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Samurai samurai = new Samurai();
        Archer archer = new Archer();
        Wizard wizard = new Wizard();

        Button samuraiButton = new Button("Samurai");
        Button archerButton = new Button("Archer");
        Button wizardButton = new Button("Wizard");

        Label samuraiStats = new Label("Damage: " + samurai.getDamage() + ", Health: " + samurai.getHealth() + ", Money: " + samurai.getMoney());
        Label archerStats = new Label("Damage: " + archer.getDamage() + ", Health: " + archer.getHealth() + ", Money: " + archer.getMoney());
        Label wizardStats = new Label("Damage: " + wizard.getDamage() + ", Health: " + wizard.getHealth() + ", Money: " + wizard.getMoney());

        samuraiStats.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        archerStats.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        wizardStats.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        samuraiButton.setOnAction(e -> selectCharacter(samurai));
        archerButton.setOnAction(e -> selectCharacter(archer));
        wizardButton.setOnAction(e -> selectCharacter(wizard));

        layout.getChildren().addAll(title,
                samuraiButton, samuraiStats,
                archerButton, archerStats,
                wizardButton, wizardStats);

        Scene characterScene = new Scene(layout, 500, 400);
        primaryStage.setScene(characterScene);
    }

    private void showLocationOptions() {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: left;");

        Label title = new Label("Where would you like to go?");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label moneyLabel = new Label("Current Money: " + player.getMoney());
        moneyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: green;");

        SafeHouse safeHouse = new SafeHouse(player, "Safe House", primaryStage);
        Shop shop = new Shop(player, primaryStage);
        Forest forest = new Forest(player, primaryStage);
        Cave cave = new Cave(player, primaryStage);
        Castle castle = new Castle(player, primaryStage);

        Button safeHouseButton = new Button("Safe House");
        Button shopButton = new Button("Shop");
        Button forestButton = new Button("Forest");
        Button caveButton = new Button("Cave");
        Button castleButton = new Button("Castle");

        Label safeHouseStats = new Label("This is a safe area. Your health will be restored.");
        Label shopStats = new Label("This is a safe area. You can purchase weapons and armor here.");
        Label forestStats = new Label("You may encounter monsters deep into the forest...");
        Label caveStats = new Label("You may encounter monsters deep into the cave...");
        Label castleStats = new Label("You may encounter the strongest monsters deep into the castle...");

        for (Label label : new Label[]{safeHouseStats, shopStats, forestStats, caveStats, castleStats}) {
            label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        }

        safeHouseButton.setOnAction(e -> goToLocation(safeHouse));
        shopButton.setOnAction(e -> goToLocation(shop));
        forestButton.setOnAction(e -> goToLocation(forest));
        caveButton.setOnAction(e -> goToLocation(cave));
        castleButton.setOnAction(e -> goToLocation(castle));

        layout.getChildren().addAll(title, moneyLabel,
                safeHouseButton, safeHouseStats,
                shopButton, shopStats,
                forestButton, forestStats,
                caveButton, caveStats,
                castleButton, castleStats);

        Scene locationScene = new Scene(layout, 500, 500);
        primaryStage.setScene(locationScene);
    }

    private void goToLocation(Location location) {
        if (!location.onLocation()) {
            storyLabel.setText("You died! Game Over!");
            VBox layout = new VBox(10, storyLabel);
            layout.setStyle("-fx-padding: 20; -fx-alignment: left;");

            Button exitButton = new Button("Exit Game");
            exitButton.setOnAction(e -> primaryStage.close());

            layout.getChildren().add(exitButton);
            Scene gameOverScene = new Scene(layout, 500, 300);
            primaryStage.setScene(gameOverScene);
        } else {
            showLocationOptions(); // Refresh location and show updated money
        }
    }
}
