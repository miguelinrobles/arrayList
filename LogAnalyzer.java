/**
 * Read web server data and analyse
 * 
 * 
 * @author David J. Barnes and Michael KÃ¶lling.
 * @version 2011.07.31
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;
    // Guarda el valor true cuando se ha ejecutado el método analyzeHourlyData()
    private boolean ejecutadoAnalyzeHourlyData;
    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader();
        ejecutadoAnalyzeHourlyData = false;
    }

    /**
     * Constructor que se le pasa el nombre del archivo de log a analizar.
     */
    public LogAnalyzer(String nombreFichero)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(nombreFichero);
        ejecutadoAnalyzeHourlyData = false;
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        ejecutadoAnalyzeHourlyData = true;
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        int hour = 0;
        while (hour < hourCounts.length) {
            System.out.println(hour + ": " + hourCounts[hour]);
            hour++;
        }
    }

    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }

    /**
     * Devuelve el número total de accesos al servidor web registrados en el archivo de log.
     * Si no se ha ejecutado antes el método analyzeHourlyData() devuelve -1 e informa de ello por pantalla.
     */
    public int numberOfAccesses()
    {
        int numeroAccesos = -1;
        if (ejecutadoAnalyzeHourlyData) {
            numeroAccesos = 0;
            for (int contador = 0; contador < hourCounts.length; contador++) {
                numeroAccesos += hourCounts[contador];
            }
        }
        else {
            System.out.println("Debes de ejecutar antes el método analyzeHourlyData()");
        }
        return numeroAccesos;
    }

    /**
     * Devuelve en qué hora el servidor tuvo que responder a más peticiones. 
     * Si hay empate devuelve la última de las horas. 
     * Si no ha habido accesos informa del hecho por pantalla y devuelve -1.
     */
    public int busiestHour()
    {
        int horaMasPeticiones = -1;
        if (ejecutadoAnalyzeHourlyData) {
            int numeroPeticiones = 0;
            for (int contador = 0; contador < hourCounts.length; contador++) {
                if (hourCounts[contador] > 0) {
                    if (hourCounts[contador] >= numeroPeticiones) {
                        numeroPeticiones = hourCounts[contador];
                        horaMasPeticiones = contador;
                    }
                }
            }
            if (numeroPeticiones == 0) {
                System.out.println("No se ha realizado ningún acceso al servidor");
            }
        }
        else {
            System.out.println("Debes de ejecutar antes el método analyzeHourlyData()");
        }
        return horaMasPeticiones;
    }
}
