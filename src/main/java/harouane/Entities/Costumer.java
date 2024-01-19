package harouane.Entities;

public class Costumer {
    static Long id= 0L;
    String name;
    Integer tier;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Costumer(String name, Integer tier) {
        Costumer.id++;
        this.name = name;
        this.tier = tier;
    }

    public Integer getTier() {
        return tier;
    }

    @Override
    public String toString() {
        return "Costumer{" +
                "name='" + name + '\'' +
                ", tier=" + tier +
                '}';
    }
}
