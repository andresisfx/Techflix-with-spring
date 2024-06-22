package com.javaCursos.Techflix.Principal;

import com.javaCursos.Techflix.Model.DatosEpisodio;
import com.javaCursos.Techflix.Model.DatosSerie;
import com.javaCursos.Techflix.Model.DatosTemporada;
import com.javaCursos.Techflix.Model.Episodio;
import com.javaCursos.Techflix.Service.ConsumoAPI;
import com.javaCursos.Techflix.Service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE= "https://www.omdbapi.com/?t=";
    private final String API_KEY= "&apikey=dc70ad0f";
    private ConvierteDatos conversor= new ConvierteDatos();
    public void muestraElMenu(){
        System.out.println("por favor escribe la serie que queres ver");
        var nombreSerie = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE+nombreSerie.replace(" ","+")+API_KEY);
        var datos = conversor.obtenDatos(json, DatosSerie.class);
        System.out.println(datos);
        List<DatosTemporada> temporadas=new ArrayList<>();
        for (int i = 1; i <=datos.totalTemporadas() ; i++) {
            json = consumoAPI.obtenerDatos(URL_BASE+nombreSerie.replace(" ","+")+"&season="+i+API_KEY);

            var datosTemporadas = conversor.obtenDatos(json,DatosTemporada.class);
            temporadas.add(datosTemporadas);
        }
       // temporadas.forEach(System.out::println);

        /*for (int i = 1; i < datos.totalTemporadas() ; i++) {
           List <DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            for (int j = 0; j <episodiosTemporada.size() ; j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }*/
       // temporadas.forEach(T->T.episodios().forEach(e-> System.out.println(e.titulo())));

        List <DatosEpisodio> datosEpisodio = temporadas.stream().
               flatMap(e->e.episodios().stream()).
                collect(Collectors.toList());  //si en lugar de esto solo colocamos ".toList" no prodremos hacer cambios a la lista

        //Top 5 episodios
       /* System.out.println("top 5 episodios");
        datosEpisodio.stream()
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .peek(e-> System.out.println("cmparador (menor ->Mayor)"))
                .filter(e->!e.evaluacion().equalsIgnoreCase("N/A"))//los que tengan evaluacion N/A no los muestra
                .peek(e-> System.out.println(" filtro (N/A) "  +e))
                .map(e->e.titulo().toUpperCase())
                .peek(e-> System.out.println("mayusculas"))
                .limit(6)
                .forEach(System.out::println);*/

        List<Episodio>episodios=  temporadas.stream()
                .flatMap(t->t.episodios().stream()
                        .map(d->new Episodio(t.numero(),d)))
                .collect(Collectors.toList());
//         episodios.forEach(System.out::println);

//buscando temporada a partir de una fecha específica
       /* System.out.println("Escribe el año a partir del cual quieres ver las temporadas");
        var fecha = teclado.nextInt();
        teclado.nextLine();
        LocalDate fechaDeBusqueda= LocalDate.of(fecha,1,1);*/

        DateTimeFormatter dtf= DateTimeFormatter.ofPattern("dd/MM/yyyy");
      /*  episodios.stream()
                .filter(e->e.getFechaDeLanzamiento()!=null&&e.getFechaDeLanzamiento().isAfter(fechaDeBusqueda))
                .peek(e-> System.out.println("primer filtro F.lanzamiento "  +e))
                .forEach(e-> System.out.println(
                        "temporada: "+ e.getTemporada()+
                        "  Episodio: " + e.getNumeroEpisodio()+
                                "  fechaDeLanzamiento: "+e.getFechaDeLanzamiento().format(dtf)
                ));*/
        //busqueda de episodios por pedazo de titulo buscar coincidencias

      /*  System.out.println("escriba el titulo del episodio");
        var busquedaPorTitulo = teclado.nextLine();
        Optional<Episodio> episodioEncontrado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(busquedaPorTitulo.toUpperCase()))
                .findFirst();
        if (episodioEncontrado.isPresent()){
            System.out.println("Episodio encontrado ");
            System.out.println(" los datos son: "+episodioEncontrado.get());
        }else{
            System.out.println("Episodio no encontrado :(");
        }*/

        Map<Integer,Double> evaluacionesPorTemporada= episodios.stream()
                .filter(e->e.getEvaluacion()>0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,Collectors.averagingDouble(Episodio::getEvaluacion)));
        System.out.println(evaluacionesPorTemporada);

        // sacar estadisticas predefinidas con java
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e->e.getEvaluacion()>0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));

        System.out.println("Media de las evaluaciones:  "+ est.getAverage() );
        System.out.println("episodio mejor evaluado:  "+ est.getMax() );
        System.out.println("episodio peor evaluado:  "+ est.getMin() );
    }

}
