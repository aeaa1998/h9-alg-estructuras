package com.algoritmos;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Mappeable<String, String> dictionary;
    private ArrayList<String> menu = new ArrayList<>(){{add("Traducir .txt"); add("Traducir texto en consola");}};
    private MappeableFactory<String, String> factory;
    public Controller() {
        this.factory = new MappeableFactory<String, String>();
        this.dictionary = factory.getMap();
    }

    public void init(){
        fillDictionary("Spanish.txt");
        boolean running = true;
        while (running){
            /* Funcion que retorna el valor de el indice de la viarbale menu*/
            switch (View.getView().selectOptions(menu, "Ingrese una de las opciones.", "Ingrese un valor valido.")){
                case 0:
                    /* Codigo para traducir nuestro archivo txt*/
                    break;
                case 1:
                    /* Codigo para traducir input de consola*/
                    break;
                default:
                    /* Decidio salir*/
                    View.getView().print("Gracias por usar nuestro traductor");
                    running = false;
                    break;
            }

        }
    }

    public void fillDictionary(String fileName){
        try {
            var mainPath = Controller.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            if (getOsName().startsWith("Windows")){
                if(String.valueOf(mainPath.charAt(0)).equals("/")) { mainPath = mainPath.substring(1, mainPath.length());}
            }
            List<String> strings = Files.readAllLines(Path.of(mainPath + fileName));
            for (String line:
                    strings) {
                var holder = new ArrayList<String>(){{ addAll(List.of(line.split("\t")));}};
                var english = holder.get(0);
                var spanish = holder.get(1);
                if (spanish.contains(", ")){
                    spanish = spanish.split(", ")[0];
                }
                if (spanish.contains("[")){
                    spanish = spanish.substring(0, spanish.indexOf("["));
                }
                dictionary.put(english.toLowerCase(), spanish);
            }
        } catch(URISyntaxException | IOException e){
            System.out.println("Revise bien que su archivo txt exista");
        }
    }

    public Mappeable<String, String> getDictionary() {
        return dictionary;
    }

    private  String getOsName()
    {
        return System.getProperty("os.name");
    }
}
