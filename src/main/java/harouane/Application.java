package harouane;

import com.github.javafaker.Faker;
import harouane.Entities.Costumer;
import harouane.Entities.Order;
import harouane.Entities.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {
        String boys="Boys";
        String baby="Baby";
        Faker faker =new Faker();
        Supplier<Integer> tierSupplier=()->{
            Random random= new Random();
            return random.nextInt(1,5);
        };

        Supplier<Double> doubleSupplier = ()->{
            Random random = new Random();
            return Math.floor((random.nextDouble(1, 200)) * 100) / 100;
        };

        Supplier<Costumer> newCostumer=()-> new Costumer(faker.name().firstName()+" "+faker.name().lastName(), tierSupplier.get());

        Supplier<Product> createNewProductBook= ()->{
            String books="Books";
            return new Product(faker.book().title(), books, doubleSupplier.get());
        };

        ArrayList<Product> allProducts = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            allProducts.add(createNewProductBook.get());
        }

        Product prodotto5 = new Product("Peluche di " + faker.cat().name(), baby, doubleSupplier.get());
        Product prodotto6 = new Product("Giocattolo: " + faker.dog().name(), baby, doubleSupplier.get());
        Product prodotto7 = new Product("Peluche di " + faker.animal().name(), baby, doubleSupplier.get());
        Product prodotto8 = new Product("Action figure di: " + faker.dragonBall().character(), boys, doubleSupplier.get());
        Product prodotto9 = new Product("Action figure del personaggio di Ritorno al futuro: " + faker.backToTheFuture().character(), boys, doubleSupplier.get());
        allProducts.add(prodotto5);
        allProducts.add(prodotto6);
        allProducts.add(prodotto7);
        allProducts.add(prodotto8);
        allProducts.add(prodotto9);

        ArrayList<Product> order1= new ArrayList<>();
        order1.add(createNewProductBook.get());
        order1.add(createNewProductBook.get());
        ArrayList<Product> order2= new ArrayList<>();
        order2.add(createNewProductBook.get());
        order2.add(prodotto5);
        ArrayList<Product> order3= new ArrayList<>();
        order3.add(prodotto9);
        ArrayList<Product> order4= new ArrayList<>();
        order4.add(prodotto6);
        order4.add(prodotto7);
        ArrayList<Product> order5= new ArrayList<>();
        order5.add(createNewProductBook.get());

        ArrayList<Order> allOrders = new ArrayList<>();

        Costumer costumer1= newCostumer.get();
        Costumer costumer2= newCostumer.get();

        allOrders.add(new Order("In arrivo", LocalDate.parse("2021-02-10"), LocalDate.parse("2024-01-10"), order1,costumer1));
        allOrders.add(new Order("In Elaborazione", LocalDate.parse("2021-03-10"),LocalDate.parse("2023-12-31"), order2,costumer2));
        allOrders.add(new Order("Spedito", LocalDate.parse("2024-01-10"),LocalDate.parse("2024-01-17"), order3,newCostumer.get()));
        allOrders.add(new Order("In arrivo", LocalDate.parse("2023-05-10"), LocalDate.parse("2024-01-11"), order4,costumer1));
        allOrders.add(new Order("In arrivo", LocalDate.parse("2024-03-20"),LocalDate.parse("2024-01-11"), order4,newCostumer.get()));
        allOrders.add(new Order("In arrivo", LocalDate.parse("2023-05-10"), LocalDate.parse("2024-01-11"), order5, costumer2));

        System.out.println("-------------------------------------Es 1--------------------------------------------");
        Map<String, List<Order>> ordersByCostumer = allOrders.stream().collect(Collectors.groupingBy(order -> order.getCostumer().getName()));
        ordersByCostumer.forEach((k, v)->System.out.println(k+": "+v));
        System.out.println("-------------------------------------Es 2--------------------------------------------");
        Map<String, Double> totalPriceEachCostumer = allOrders.stream()
                .collect(Collectors.groupingBy(order -> order.getCostumer().getName(),
                        Collectors.summingDouble(order-> order.getProducts().stream().map(prod->prod.getPrice())
                                .reduce(0.0, (partialSum, currentElem) -> partialSum + currentElem))));

        totalPriceEachCostumer.forEach((k, v)->System.out.println(k+": "+v));

        System.out.println("-------------------------------------Es 3--------------------------------------------");
        List<Product> tenMostValuableProd = allProducts.stream().sorted(Comparator.comparingDouble(Product::getPrice).reversed()).limit(10).toList();
        tenMostValuableProd.forEach(product -> System.out.println(product));
        System.out.println("-------------------------------------Es 4--------------------------------------------");
        Map<String, Double> avgOrders=  allOrders.stream().collect(Collectors.groupingBy(order ->order.getCostumer().getName(), Collectors.averagingDouble(order -> order.getProducts().stream().map(prod->prod.getPrice()).reduce(0.0, (partialSum, currentElem) -> partialSum + currentElem))));
        avgOrders.forEach((k, v)->System.out.println(k+": "+v));
        System.out.println("-------------------------------------Es 5--------------------------------------------");
        Map<String, List<Product>> productsByCategory= allProducts.stream().collect(Collectors.groupingBy(product->product.getCategory()));
        productsByCategory.forEach((k,v)->{System.out.println(k+" : "+v);});

    }
}
