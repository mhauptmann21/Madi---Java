public class Armor {
    private String name;
    private int id;
    private int block;
    private int price;

    public Armor(String name, int id, int block, int price) {
        this.name = name;
        this.id = id;
        this.block = block;
        this.price = price;
    }

    public static Armor[] armorList() {
        Armor[] armorList = new Armor[3];
        armorList[0] = new Armor("Leather", 1, 1, 15);
        armorList[1] = new Armor("Chain", 2, 3, 25);
        armorList[2] = new Armor("Silver", 3, 5, 35);

        return armorList;
    }

    public static Armor getArmorObjById(int id) {
        for (Armor a : Armor.armorList()) {
            if (a.getId() == id) {
                return a;
            }
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setName(int id) {
        this.id = id;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
