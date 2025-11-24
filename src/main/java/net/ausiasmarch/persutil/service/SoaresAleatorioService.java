package net.ausiasmarch.persutil.service;

import org.springframework.stereotype.Service;

@Service
public class SoaresAleatorioService {

    public int GenerarNumeroAleatorioEnteroEnRango(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }   
    
}
//ATENCION FUE TOCADO EL ALEATORIO SERVICE RESTAURA EL PROYECTO DE RAFA EN CASA

    private final String[] PREGUNTAS_NEURO = {
        "Si la mente es un universo, ¿cuál es la estrella que guía tu conciencia?",
        "¿Cómo se reescribe el guion de un recuerdo que ya no te sirve?",
        "¿Qué patrón neuronal se activa cuando experimentas la 'belleza' de una idea?",
        "Si el cerebro es una red, ¿cuál es el nodo más resistente a la entropía?",
        "¿Qué sesgo cognitivo te impide ver la solución más obvia a tu problema actual?",
        "¿De qué manera tu neuroplasticidad está moldeando tu futuro en este instante?",
        "¿Cuál es el 'efecto placebo' que podrías aplicar conscientemente a tu vida?",
        "Si tus emociones son datos, ¿qué algoritmo utiliza tu mente para procesarlos?",
        "¿Cómo influye la arquitectura de tu sueño en la consolidación de tu identidad?",
        "¿Qué 'neurona espejo' te conecta más profundamente con el sufrimiento ajeno?"
    };

    public String getPreguntaNeuro() {
        return PREGUNTAS_NEURO[GenerarNumeroAleatorioEnteroEnRango(0, PREGUNTAS_NEURO.length - 1)];
    }

    public java.time.LocalDateTime getFechaCreacion() {
        return java.time.LocalDateTime.now().minusDays(GenerarNumeroAleatorioEnteroEnRango(1, 365));
    }

    public java.time.LocalDateTime getFechaModificacion(java.time.LocalDateTime fechaCreacion) {
        return fechaCreacion.plusHours(GenerarNumeroAleatorioEnteroEnRango(1, 24 * 30));
    }
