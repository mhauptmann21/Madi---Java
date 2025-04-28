
public class Inventory {
    private Bow bow;
    private Armor armor;

    public Inventory() {
        this.bow = new Bow("Fists", -1, 1, 0);
        this.armor = new Armor("None", -1, 0, 0);
    }

    public Bow getBow() {
        return this.bow;
    }

    public void setBow(Bow bow) {
        this.bow = bow;
    }

    public Armor getArmor() {
        return this.armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }
}
