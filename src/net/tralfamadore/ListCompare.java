package net.tralfamadore;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class: ListCompare
 * Created by billreh on 12/24/16.
 */
public class ListCompare {
    public static void main(String[] args) {
        List<String> cities = Arrays.asList("London", "Paris", "Tokyo", "New York");
        cities.sort(Comparator.comparingInt(String::length));
        System.out.println(cities);
        cities.sort(Collections.reverseOrder());
        System.out.println(cities);
        cities.sort( (c1, c2) -> {
            if(c2.length() > c1.length())
                return 1;
            else if(c2.length() < c1.length())
                return -1;
            else
                return 0;
        });
        System.out.println(cities);
        System.out.println(cities.stream().mapToInt(String::length).average().orElseGet(() -> 0.0));
        System.out.println(cities.stream().mapToInt(String::length).reduce(0, (a, b) -> a + b));
        System.out.println(cities.stream().mapToInt(String::length).reduce(0, Integer::sum));

        filter(cities, c -> c.length() > 5);

        Predicate<String> fiveLettersLong = s -> s.length() == 5;
        Predicate<String> startsWithT = s -> s.startsWith("T");

        filter(cities, fiveLettersLong.and(startsWithT));
        Map<String,Integer> map = cities.stream().collect(Collectors.toMap(s -> s, String::length));
        Map<String,Integer> map2 = cities.stream().collect(Collectors.toMap(Function.identity(), String::length));
        Map<Integer,List<String>> m2 = cities.stream().collect(Collectors.groupingBy(String::length));
        long count = cities.stream().filter(c -> c.length() > 5).count();
        System.out.println(map);
        System.out.println(map2);
        System.out.println(m2);
        System.out.println(count);

        StringJoiner joiner = new StringJoiner(", ");
        cities.sort(String::compareTo);
        cities.forEach(joiner::add);
        System.out.println(joiner.toString());
        String c = cities.stream().collect(Collectors.joining(", "));
        System.out.println(c);

        Map<Integer,String> m3 = cities.stream().collect(Collectors.toMap(String::length, Function.identity(), (e1, e2) -> e1 + " and " + e2));
        System.out.println(m3);

        List<String> moreCities = Arrays.asList("Philadelphia", "Milan", "Barcelona");
        List<String> allCities = Stream.of(cities, moreCities).flatMap(list -> list.stream()).collect(Collectors.toList());
        System.out.println(allCities);
    }

    private static void filter(List<String> list, Predicate<String> predicate) {
        list.stream().filter(predicate).forEach(System.out::println);
    }
}
