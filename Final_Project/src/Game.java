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

        // Input field to capture player name
        TextField inputField = new TextField();
        Button submitButton = new Button("Submit");

        // Set up layout for the start screen
        VBox layout = new VBox(10);
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
            // Entering Name
            player = new Player(inputText);
            storyLabel.setText("It's nice to meet you, " + player.getName() + "!\n\nIt is now time to choose who you will become.");
            showCharacterSelection();
            gameStage = 1;
        } else if (gameStage == 1) {
            // Choosing Character
            showCharacterSelection();
        } else if (gameStage == 2) {
            // Selecting Location
            showLocationOptions();
        }
    }

    private void selectCharacter(Character character) {
        player.initPlayer(character);
        storyLabel.setText("You chose: " + player.getCharacterName() + "!\n\nNow, where would you like to travel?");
        showLocationOptions();
        gameStage = 2;
    }

    private void showCharacterSelection() {
        VBox layout = new VBox(10);

        Label title = new Label("Choose your character:");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Create character instances to show their stats
        Samurai samurai = new Samurai();
        Archer archer = new Archer();
        Wizard wizard = new Wizard();

        // Create buttons
        Button samuraiButton = new Button("Samurai");
        Button archerButton = new Button("Archer");
        Button wizardButton = new Button("Wizard");

        // Show stats next to the buttons
        Label samuraiStats = new Label(
            "Damage: " + samurai.getDamage() +
            ", Health: " + samurai.getHealth() +
            ", Money: " + samurai.getMoney()
        );
        samuraiStats.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label archerStats = new Label(
            "Damage: " + archer.getDamage() +
            ", Health: " + archer.getHealth() +
            ", Money: " + archer.getMoney()
        );
        archerStats.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label wizardStats = new Label(
            "Damage: " + wizard.getDamage() +
            ", Health: " + wizard.getHealth() +
            ", Money: " + wizard.getMoney()
        );
        wizardStats.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Set actions when buttons are clicked
        samuraiButton.setOnAction(e -> selectCharacter(samurai));
        archerButton.setOnAction(e -> selectCharacter(archer));
        wizardButton.setOnAction(e -> selectCharacter(wizard));

        // Add everything
        layout.getChildren().addAll(
            title,
            samuraiButton, samuraiStats,
            archerButton, archerStats,
            wizardButton, wizardStats
        );

        Scene characterScene = new Scene(layout, 500, 400);
        primaryStage.setScene(characterScene);
    }

    private void showLocationOptions() {
        VBox layout = new VBox(10);

        Label title = new Label("Where would you like to go?");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Create location instances
        SafeHouse safeHouse = new SafeHouse(player, primaryStage);
        Shop shop = new Shop(player, primaryStage);
        Forest forest = new Forest(player, primaryStage);
        Cave cave = new Cave(player, primaryStage);
        Castle castle = new Castle(player, primaryStage);

        // Create buttons and labels with descriptions
        Button safeHouseButton = new Button("Safe House");
        Button shopButton = new Button("Shop");
        Button forestButton = new Button("Forest");
        Button caveButton = new Button("Cave");
        Button castleButton = new Button("Castle");

        Label safeHouseStats = new Label("This is a safe area. Your health will be restored.");
        safeHouseStats.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label shopStats = new Label("This is a safe area. You can purchase weapons and armor here.");
        shopStats.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label forestStats = new Label("You may encounter monsters deep into the forest...");
        forestStats.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label caveStats = new Label("You may encounter monsters deep into the cave...");
        caveStats.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label castleStats = new Label("You may encounter the strongest monsters deep into the castle...");
        castleStats.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Set button actions
        safeHouseButton.setOnAction(e -> goToLocation(safeHouse));
        shopButton.setOnAction(e -> goToLocation(shop));
        forestButton.setOnAction(e -> goToLocation(forest));
        caveButton.setOnAction(e -> goToLocation(cave));
        castleButton.setOnAction(e -> goToLocation(castle));

        // Add everything to layout
        layout.getChildren().addAll(
            title,
            safeHouseButton, safeHouseStats,
            shopButton, shopStats,
            forestButton, forestStats,
            caveButton, caveStats,
            castleButton, castleStats
        );

        Scene locationScene = new Scene(layout, 500, 400);
        primaryStage.setScene(locationScene);
    }

    private void goToLocation(Location location) {
        // Handle location interaction
        if (!location.onLocation()) {
            storyLabel.setText("You died! Game Over!");
            VBox layout = new VBox(10, storyLabel);
            Scene gameOverScene = new Scene(layout, 500, 300);
            primaryStage.setScene(gameOverScene);
        } else {
            showLocationOptions(); // Show options again if survived
        }
    }
}

