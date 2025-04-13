package com.example.lab2_telequiz_20213745.utils;

import com.example.lab2_telequiz_20213745.models.Question;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuestionRepository {

    public static List<Question> getQuestionsForCategory(String category) {
        List<Question> questions = new ArrayList<>();

        if (category.equalsIgnoreCase("Redes")) {
            questions.add(new Question("¿Qué protocolo se utiliza para cargar páginas web?",
                    Arrays.asList("HTTP", "FTP", "SMTP"), 0, "Redes"));
            questions.add(new Question("¿Cuál de estas es una dirección IP privada?",
                    Arrays.asList("192.168.1.1", "8.8.8.8", "172.217.20.14"), 0, "Redes"));
            questions.add(new Question("¿Qué dispositivo conecta redes diferentes y dirige el tráfico?",
                    Arrays.asList("Switch", "Router", "Hub"), 1, "Redes"));
            questions.add(new Question("¿Qué significa DNS?",
                    Arrays.asList("Domain Name System", "Data Network Service", "Digital Numbering System"), 0, "Redes"));
            questions.add(new Question("¿Qué tipo de red cubre un área pequeña, como una oficina?",
                    Arrays.asList("WAN", "MAN", "LAN"), 2, "Redes"));
        } else if (category.equalsIgnoreCase("Ciberseguridad")) {
            questions.add(new Question("¿Qué es un Ransomware?",
                    Arrays.asList("Software malicioso que encripta datos", "Tipo de firewall", "Algoritmo de cifrado"), 0, "Ciberseguridad"));
            questions.add(new Question("¿Cuál es el objetivo del phishing?",
                    Arrays.asList("Obtener información confidencial", "Mejorar la seguridad", "Optimizar la red"), 0, "Ciberseguridad"));
            questions.add(new Question("¿Qué protocolo cifra las comunicaciones web?",
                    Arrays.asList("HTTP", "HTTPS", "FTP"), 1, "Ciberseguridad"));
            questions.add(new Question("¿Qué algoritmo de cifrado es asimétrico y se usa comúnmente para firmas digitales?",
                    Arrays.asList("AES", "RSA", "DES"), 1, "Ciberseguridad"));
            questions.add(new Question("¿Para qué sirve un firewall?",
                    Arrays.asList("Permitir acceso a todos", "Filtrar tráfico de red", "Acelerar la conexión"), 1, "Ciberseguridad"));
        } else if (category.equalsIgnoreCase("Microondas")) {
            questions.add(new Question("¿En qué rango de frecuencias suelen operar las redes Wi-Fi?",
                    Arrays.asList("2.4 GHz y 5 GHz", "900 MHz y 1800 MHz", "50 Hz y 60 Hz"), 0, "Microondas"));
            questions.add(new Question("¿Qué problema es común en enlaces de microondas?",
                    Arrays.asList("Interferencia atmosférica", "Atraso en la transmisión", "Congestión de red"), 0, "Microondas"));
            questions.add(new Question("¿Qué es la zona de Fresnel en microondas?",
                    Arrays.asList("Área de interferencia en la señal", "Técnica de modulación", "Tipo de antena"), 0, "Microondas"));
            questions.add(new Question("¿Qué ventaja tienen las comunicaciones por microondas?",
                    Arrays.asList("Alta latencia", "Baja velocidad", "Instalación más sencilla"), 2, "Microondas"));
            questions.add(new Question("¿Qué dispositivo se usa para enfocar señales de microondas?",
                    Arrays.asList("Lente parabólica", "Placa base", "Circuito integrado"), 0, "Microondas"));
        }

        Collections.shuffle(questions);
        return questions;
    }
}
