package Backend;

public class Dish {
    private String name;
    private Integer weight;
    private Double price;
    private Integer id;

    public Dish(String name,Integer weight,Double price,Integer id){
        this.name=name;
        this.weight=weight;
        this.price=price;
        this.id=id;
    }
    public Dish(String input){
        String[] tab = input.split(",");
        Dish obj =new Dish(tab[0],Integer.parseInt(tab[1]),Double.parseDouble(tab[2]),Integer.parseInt(tab[3]));
        this.name= obj.getName();
        this.weight=obj.getWeight();
        this.price= obj.getPrice();
        this.id=obj.getId();
    }
    public String getName() {
        return name;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name+','+weight+','+price+','+id+';';
    }
}
