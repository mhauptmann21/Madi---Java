import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.util.Random;

public class BattleLocation extends Location {
    private Monster monster;
    private String award;
    private int maxMonster;
    private Stage stage;
    private VBox layout;
    private TextArea outputArea;
    private Button fightButton, runButton, attackButton, escapeButton;
    private int rndMonster;

    public BattleLocation(Player player, String name, Monster monster, String award, int maxMonster, Stage stage) {
        super(player, name);
        this.monster = monster;
        this.award = award;
        this.maxMonster = maxMonster;
        this.stage = stage;
    }

    @Override
    public boolean onLocation() {
        Platform.runLater(() -> {
            rndMonster = this.randomMonsterNumber();
            layout = new VBox(10);
            outputArea = new TextArea();
            outputArea.setEditable(false);
            fightButton = new Button("Fight");
            runButton = new Button("Run");

            outputArea.appendText("---------------------------------------\n");
            outputArea.appendText("You have just reached the " + this.getName() + "\n");
            outputArea.appendText("There is a " + this.getMonster().getName() + " nearby. Be careful...\n");
            outputArea.appendText("---------------------------------------\n");

            fightButton.setOnAction(e -> combat(rndMonster));
            runButton.setOnAction(e -> {
                outputArea.appendText("You chose to run away!\n");
                // Optional: Implement running behavior
                backToMainScreen();
            });

            layout.getChildren().addAll(outputArea, fightButton, runButton);
            Scene scene = new Scene(layout, 600, 400);
            stage.setScene(scene);
        });
        return true;
    }

    public void combat(int monsterNumber) {
        layout.getChildren().clear();
        outputArea.clear();
        outputArea.appendText("Combat started!\n");

        attackButton = new Button("Attack");
        escapeButton = new Button("Escape");

        attackButton.setOnAction(e -> {
            handleAttack(monsterNumber);
        });

        escapeButton.setOnAction(e -> {
            outputArea.appendText("You escaped the battle!\n");
            backToMainScreen();
        });

        layout.getChildren().addAll(outputArea, attackButton, escapeButton);
    }

    private void handleAttack(int monsterNumber) {
        if (this.getMonster().getHealth() <= 0) {
            outputArea.appendText("Monster already defeated!\n");
            return;
        }

        int playerAttack = this.getPlayer().getTotalDamage();
        int monsterAttack = this.getMonster().getDamage();

        // Player attacks monster
        this.getMonster().setHealth(this.getMonster().getHealth() - playerAttack);
        outputArea.appendText("You attacked the monster!\n");
        afterHit();

        // Monster retaliates if still alive
        if (this.getMonster().getHealth() > 0) {
            outputArea.appendText("The monster attacked you!\n");
            int playerBlock = this.getPlayer().getInventory().getArmor().getBlock();
            monsterAttack = monsterAttack - playerBlock;
            if (monsterAttack < 0) monsterAttack = 0;
            this.getPlayer().sethealth(this.getPlayer().getHealth() - monsterAttack);
            afterHit();
        }

        // Check if player or monster is dead
        if (this.getPlayer().getHealth() <= 0) {
            outputArea.appendText("You have been defeated...\n");
            attackButton.setDisable(true);
            escapeButton.setDisable(true);
        } else if (this.getMonster().getHealth() <= 0) {
            outputArea.appendText("You defeated the monster!\n");
            this.getPlayer().setMoney(this.getPlayer().getMoney() + this.getMonster().getAward());
            outputArea.appendText("You picked up " + this.getMonster().getAward() + " money!\n");

            attackButton.setDisable(true);
            escapeButton.setDisable(true);

            // Optionally: move to next monster or finish
            backToMainScreen();
        }
    }

    public void playerStatus() {
        outputArea.appendText("---------------------------------------\n");
        outputArea.appendText(this.getPlayer().getCharacterName() + " Status:\n");
        outputArea.appendText("Health: " + this.getPlayer().getHealth() + "\n");
        outputArea.appendText("Attack Power: " + this.getPlayer().getTotalDamage() + "\n");
        outputArea.appendText("Defense Power: " + this.getPlayer().getInventory().getArmor().getBlock() + "\n");
    }

    public void monsterStatus() {
        outputArea.appendText("---------------------------------------\n");
        outputArea.appendText(this.getMonster().getName() + " Status:\n");
        outputArea.appendText("Health: " + this.getMonster().getHealth() + "\n");
        outputArea.appendText("Attack Damage: " + this.getMonster().getDamage() + "\n");
    }

    public void afterHit() {
        outputArea.appendText("Your health: " + this.getPlayer().getHealth() + "\n");
        outputArea.appendText(this.getMonster().getName() + "'s health: " + this.getMonster().getHealth() + "\n\n");
    }

    public void backToMainScreen() {
        Platform.runLater(() -> {
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
                outputArea.clear();
                outputArea.appendText("You traveled to the Safe House.\n");
                NormalLocation safeHouse = new NormalLocation(getPlayer(), "Safe House", stage);
                safeHouse.onLocation();
            });
    
            shopButton.setOnAction(e -> {
                outputArea.clear();
                outputArea.appendText("You traveled to the Shop.\n");
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
        });
    }
    

    public int randomMonsterNumber() {
        Random r = new Random();
        return r.nextInt(this.getMaxMonster()) + 1;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public int getMaxMonster() {
        return maxMonster;
    }

    public void setMaxMonster(int maxMonster) {
        this.maxMonster = maxMonster;
    }
}
