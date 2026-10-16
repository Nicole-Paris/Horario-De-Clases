import java.util.Random;

public class AsignadorHorarios {

    static final int TOTAL_HORARIOS = 10;
    static final int TOTAL_MATERIAS = 5;

    static String[] nombreMaterias = {
        "Matematicas",
        "Programacion",
        "Ingles",
        "Bases de Datos",
        "Redes"
    };

    static String[] nombreProfesores = {
        "Arturo",
        "Vergara",
        "Paty",
        "Teresa",
        "Vergara"
    };

    static String[] nombreSalones = {
        "Salon 1",
        "Salon 2",
        "Salon 3",
        "Salon 1",
        "Salon 2"
    };

    static String[] nombreGrupos = {
        "Grupo A",
        "Grupo A",
        "Grupo B",
        "Grupo B",
        "Grupo C"
    };

    static String[] bloquesHora = {
        "7:00-8:00",
        "8:00-9:00",
        "9:00-10:00",
        "10:00-11:00"
    };

    static Random generador = new Random();

    public static void main(String[] args) {

        int[][] poblacionHorarios =
            new int[TOTAL_HORARIOS][TOTAL_MATERIAS];

        for (int horario = 0; horario < TOTAL_HORARIOS; horario++) {
            for (int materia = 0; materia < TOTAL_MATERIAS; materia++) {
                poblacionHorarios[horario][materia] =
                    generador.nextInt(bloquesHora.length);
            }
        }

        int mejorHorario = 0;
        int mayorAptitud = -1;

        System.out.println("ASIGNADOR DE HORARIOS ESCOLARES");

        for (int horario = 0; horario < TOTAL_HORARIOS; horario++) {

            int conflictos = calcularConflictos(
                poblacionHorarios[horario]
            );

            int aptitud = calcularAptitud(
                poblacionHorarios[horario]
            );

            System.out.println("\nHorario candidato " + (horario + 1));

            for (int materia = 0; materia < TOTAL_MATERIAS; materia++) {

                int bloque = poblacionHorarios[horario][materia];

                System.out.println(
                    nombreMaterias[materia]
                    + " | Profesor: " + nombreProfesores[materia]
                    + " | Salon: " + nombreSalones[materia]
                    + " | " + nombreGrupos[materia]
                    + " | Hora: " + bloquesHora[bloque]
                );
            }

            System.out.println("Total de conflictos: " + conflictos);
            System.out.println("Aptitud (Fitness): " + aptitud + "/100");

            if (aptitud > mayorAptitud) {
                mayorAptitud = aptitud;
                mejorHorario = horario;
            }
        }

        System.out.println("\nMEJOR HORARIO ENCONTRADO");
        System.out.println("Horario candidato: " + (mejorHorario + 1));
        System.out.println("Aptitud: " + mayorAptitud + "/100");
        System.out.println(
            "Conflictos: "
            + calcularConflictos(poblacionHorarios[mejorHorario])
        );

        for (int materia = 0; materia < TOTAL_MATERIAS; materia++) {

            int bloque = poblacionHorarios[mejorHorario][materia];

            System.out.println(
                nombreMaterias[materia]
                + " | Profesor: " + nombreProfesores[materia]
                + " | Salon: " + nombreSalones[materia]
                + " | " + nombreGrupos[materia]
                + " | Hora: " + bloquesHora[bloque]
            );
        }
    }

    public static int calcularConflictos(int[] asignacionHorario) {

        int totalConflictos = 0;

        for (int primeraMateria = 0;
             primeraMateria < TOTAL_MATERIAS;
             primeraMateria++) {

            for (int segundaMateria = primeraMateria + 1;
                 segundaMateria < TOTAL_MATERIAS;
                 segundaMateria++) {

                if (asignacionHorario[primeraMateria]
                    == asignacionHorario[segundaMateria]) {

                    boolean profesorOcupado =
                        nombreProfesores[primeraMateria].equals(
                            nombreProfesores[segundaMateria]
                        );

                    boolean salonOcupado =
                        nombreSalones[primeraMateria].equals(
                            nombreSalones[segundaMateria]
                        );

                    boolean grupoOcupado =
                        nombreGrupos[primeraMateria].equals(
                            nombreGrupos[segundaMateria]
                        );

                    if (profesorOcupado || salonOcupado || grupoOcupado) {
                        totalConflictos++;
                    }
                }
            }
        }

        return totalConflictos;
    }

    public static int calcularAptitud(int[] asignacionHorario) {

        int conflictos = calcularConflictos(asignacionHorario);

        int puntuacion = 100 - (conflictos * 10);

        if (puntuacion < 0) {
            puntuacion = 0;
        }

        return puntuacion;

    }
}