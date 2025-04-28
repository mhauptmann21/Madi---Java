import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Shop extends NormalLocation {
    private Stage stage;

    public Shop(Player player, Stage stage) {
        super(player, "Shop", stage);
        this.stage = stage;
    }

    @Override
    public boolean onLocation() {
        Platform.runLater(this::showShopMenu);
        return true;
    }

    private void showShopMenu() {
        VBox menu = new VBox(15);
        menu.setStyle("-fx-padding: 20;");
        Label title = new Label("Welcome to the Shop!");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button weaponsButton = new Button("Weapons");
        Button armorButton = new Button("Armor");
        Button exitButton = new Button("Exit");

        weaponsButton.setOnAction(e -> showWeapons());
        armorButton.setOnAction(e -> showArmors());
        exitButton.setOnAction(e -> {
            backToMainScreen();
        });

        menu.getChildren().addAll(title, weaponsButton, armorButton, exitButton);

        Scene scene = new Scene(menu, 600, 400);
        stage.setScene(scene);
    }

    private void showWeapons() {
        VBox weaponsMenu = new VBox(15);
        weaponsMenu.setStyle("-fx-padding: 20;");
        Label label = new Label("Weapon List:");
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        for (Bow bow : Bow.bowList()) {
            Button bowButton = new Button(bow.getName() + " (Damage: " + bow.getDamage() + ", Price: " + bow.getPrice() + ")");
            bowButton.setOnAction(e -> buyBow(bow));
            weaponsMenu.getChildren().add(bowButton);
        }

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showShopMenu());
        weaponsMenu.getChildren().add(backButton);

        Scene scene = new Scene(weaponsMenu, 600, 400);
        stage.setScene(scene);
    }

    private void buyBow(Bow bow) {
        if (bow.getPrice() > getPlayer().getMoney()) {
            showMessage("You don't have enough money to buy " + bow.getName() + ".");
        } else {
            getPlayer().getInventory().setBow(bow);
            getPlayer().setMoney(getPlayer().getMoney() - bow.getPrice());
            showMessage(bow.getName() + " added to inventory!\nRemaining Money: " + getPlayer().getMoney());
        }
    }

    private void showArmors() {
        VBox armorsMenu = new VBox(15);
        armorsMenu.setStyle("-fx-padding: 20;");
        Label label = new Label("Armor List:");
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        for (Armor armor : Armor.armorList()) {
            Button armorButton = new Button(armor.getName() + " (Block: " + armor.getBlock() + ", Price: " + armor.getPrice() + ")");
            armorButton.setOnAction(e -> buyArmor(armor));
            armorsMenu.getChildren().add(armorButton);
        }

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showShopMenu());
        armorsMenu.getChildren().add(backButton);

        Scene scene = new Scene(armorsMenu, 600, 400);
        stage.setScene(scene);
    }

    private void buyArmor(Armor armor) {
        if (armor.getPrice() > getPlayer().getMoney()) {
            showMessage("You don't have enough money to buy " + armor.getName() + ".");
        } else {
            getPlayer().getInventory().setArmor(armor);
            getPlayer().setMoney(getPlayer().getMoney() - armor.getPrice());
            showMessage(armor.getName() + " added to inventory!\nRemaining Money: " + getPlayer().getMoney());
        }
    }

    private void showMessage(String message) {
        VBox messageBox = new VBox(15);
        messageBox.setStyle("-fx-padding: 20;");
        Label msg = new Label(message);
        msg.setStyle("-fx-font-size: 16px;");
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> showShopMenu());

        messageBox.getChildren().addAll(msg, okButton);

        Scene scene = new Scene(messageBox, 600, 400);
        stage.setScene(scene);
    }

    private void backToMainScreen() {
        VBox mainMenu = new VBox(15);
        mainMenu.setStyle("-fx-padding: 20;");
        Label label = new Label("Where do you want to go next?");
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button safeHouseButton = new Button("Safe House");
        Button shopButton = new Button("Shop");
        Button forestButton = new Button("Forest");
        Button caveButton = new Button("Cave");
        Button castleButton = new Button("Castle");

        safeHouseButton.setOnAction(e -> {
            NormalLocation safeHouse = new NormalLocation(getPlayer(), "Safe House", stage);
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

        mainMenu.getChildren().addAll(label, safeHouseButton, shopButton, caveButton, castleButton);

        Scene scene = new Scene(mainMenu, 600, 400);
        stage.setScene(scene);
    }
}
