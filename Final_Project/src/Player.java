public class Player {
    private int damage;
    private int health;
    private int defaultHealth;
    private int money;
    private int defaultMoney;
    private String characterName;
    private String name;
    private Inventory inventory;

    public Player(String name) {
        this.name = name;
        this.inventory = new Inventory();
    }

    public void initPlayer(Character character) {
        this.setCharacterName(character.getName());
        this.setDamage(character.getDamage());
        this.sethealth(character.getHealth());
        this.setDefaultHealth(character.getHealth());
        this.setMoney(character.getMoney());
        this.setDefaultMoney(character.getMoney());
    }

    public int getTotalDamage() {
        return this.damage + this.getInventory().getBow().getDamage();
    }

    public int getDamage() {
        return this.damage + this.getInventory().getBow().getDamage();
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHealth() {
        return this.health;
    }

    public void sethealth(int health) {
        this.health = health;
    }

    public int getDefaultHealth() {
        return defaultHealth;
    }

    public void setDefaultHealth(int defaultHealth) {
        this.defaultHealth = defaultHealth;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getDefaultMoney() {
        return defaultMoney;
    }

    public void setDefaultMoney(int defaultMoney) {
        this.defaultMoney = defaultMoney;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}

