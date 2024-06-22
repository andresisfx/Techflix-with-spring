package com.javaCursos.Techflix.Principal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class EjemploStreams {
    public  void muestraEjemplo (){
         List<String> nombres= Arrays.asList("Brenda","juan","alejo","alicia","andres");
         nombres.stream().
                 limit(4).//limita hasta el indice 4 tener en cuenta
                 filter(e->e.startsWith("a")).
                 map(e->e.toUpperCase()).
                 forEach(System.out::println);
    }
}
