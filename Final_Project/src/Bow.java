public class Bow {
    private String name;
    private int id;
    private int damage;
    private int price;

    public Bow(String name, int id, int damage, int price) {
        this.name = name;
        this.id = id;
        this.damage = damage;
        this.price = price;
    }

    public static Bow[] bowList() {
        Bow[] bowList = new Bow[3];
        bowList[0] = new Bow("Bow", 1, 2, 15);
        bowList[1] = new Bow("Sword", 2, 3, 25);
        bowList[2] = new Bow("Magic Staff", 3, 7, 35);

        return bowList;
    }

    public static Bow getBowObjById(int id) {
        for (Bow b : Bow.bowList()) {
            if (b.getId() == id) {
                return b;
            }
        }

        return null;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
