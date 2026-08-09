import java.util.Scanner;

public class CajeroAutomatico {
    public static void main(String[] args) {
        System.out.println("  //  SISTEMAS ATM  // \n");

        Scanner entrada = new Scanner(System.in);

        String titular = "SHEILA ALESANDRA GABRIEL OROZCO";
        String numeroCuenta = "1795";
        int pinCorrecto = 2026;
        double saldo = 1000.00;
        double comision = 10.00;
        int opcion;

        int cantidadDepositos = 0;
        double totalDepositado = 0;
        int cantidadRetiros = 0;
        double totalRetirado = 0;
        double totalComisiones = 0;
        int operacionesRechazadas = 0;
        int opcionesInvalidas = 0;


        boolean acceso = false;

        for (int intento = 1; intento <= 3; intento++) {
            System.out.print("Ingrese su PIN: ");
            int pin = entrada.nextInt();

            if (pin == pinCorrecto) {
                acceso = true;
                mostrarMensaje("Bienvenido Estimado Cliente" + titular);
                break;}
            else {
                System.out.println("PIN incorrecto.");

                if (intento < 3) {
                    System.out.println("Intentos restantes: " + (3 - intento));
                }
            }
        }

        if (!acceso) {
            System.out.println("Cuenta bloqueada.");
            entrada.close();
            return;
        }

        do {
            System.out.println("""
                    ================================
                         CAJERO AUTOMATICO 5B
                    ================================
                    1. Consultar saldo
                    2. Depositar dinero
                    3. Realizar retiro normal
                    4. Realizar retiro con comisión
                    5. Mostrar resumen de la sesión
                    6. Salir
                    ----------------------------------""");

            System.out.print("Seleccione una opcion: ");
            opcion = entrada.nextInt();

            switch (opcion) {

                case 1:
                    consultarSaldo(titular, numeroCuenta, saldo);
                    break;

                case 2:
                    double saldoAnterior = saldo;
                    double cantidad = validarDeposito(entrada);

                    saldo = depositar(saldo, cantidad);

                    cantidadDepositos++;
                    totalDepositado += cantidad;

                    System.out.println("¡Depósito realizado con éxito!");
                    System.out.println("Monto depositado: Q" + cantidad);
                    System.out.println("Saldo anterior: Q" + saldoAnterior);
                    System.out.println("Saldo actualizado: Q" + saldo);
                    break;

                case 3:
                    System.out.print("Ingrese el monto a retirar: ");
                    cantidad = entrada.nextDouble();

                    if (validarRetiro(cantidad, saldo)) {
                        double anterior = saldo;
                        saldo = retirar(saldo, cantidad);

                        cantidadRetiros++;
                        totalRetirado += cantidad;

                        System.out.println("¡Retiro realizado con éxito!");
                        System.out.println("Monto solicitado: Q" + cantidad);
                        System.out.println("Saldo anterior: Q" + anterior);
                        System.out.println("Total debitado: Q" + cantidad);
                        System.out.println("Saldo actualizado: Q" + saldo);
                    }

                    else { operacionesRechazadas++; }
                    break;

                case 4:
                    System.out.print("Ingrese el monto a retirar: ");
                    cantidad = entrada.nextDouble();

                    if (validarRetiro(cantidad, saldo)) {
                        if (cantidad + comision <= saldo) {
                            double anterior = saldo;
                            saldo = retirar(saldo, cantidad, comision);
                            cantidadRetiros++;
                            totalRetirado += cantidad;
                            totalComisiones += comision;

                            System.out.println("¡Retiro realizado con éxito!");
                            System.out.println("Monto solicitado: Q" + cantidad);
                            System.out.println("Comisión: Q" + comision);
                            System.out.println("Total debitado: Q" + (cantidad + comision));
                            System.out.println("Saldo anterior: Q" + anterior);
                            System.out.println("Saldo actualizado: Q" + saldo);}

                        else {
                            System.out.println("Fondos insuficientes para cubrir " + "el retiro más la comisión." );
                            operacionesRechazadas++;
                        }}

                        else { operacionesRechazadas++;}
                    break;

                     case 5:
                    mostrarResumen(
                            saldo,
                            cantidadDepositos,
                            totalDepositado,
                            cantidadRetiros,
                            totalRetirado,
                            totalComisiones,
                            operacionesRechazadas,
                            opcionesInvalidas);
                    break;

                //int para no perderme mas
                case 6:
                    mostrarResumen(
                            saldo,
                            cantidadDepositos,
                            totalDepositado,
                            cantidadRetiros,
                            totalRetirado,
                            totalComisiones,
                            operacionesRechazadas,
                            opcionesInvalidas );
                    System.out.println( "Gracias por usar el cajero." );
                    break;

                default:
                    System.out.println("Opción inexistente.");
                    opcionesInvalidas++;
                    continue;}
                }

                while (opcion != 6);
                System.out.println("Sesión finalizada.");}

    // NO SE QUE PONER ACA PERO ES PARA QUE NO ME PIERDA

    public static void consultarSaldo(
            String titular,
            String numeroCuenta,
            double saldo) {

        System.out.println("\n----------------------------------");
        System.out.println("Titular: " + titular);
        System.out.println("Cuenta: ****" + numeroCuenta);
        System.out.printf("Saldo disponible: Q%.2f%n", saldo);
        System.out.println("----------------------------------\n");}

    public static double validarDeposito(Scanner entrada) { double cantidad;
        System.out.print( "Ingrese la cantidad a depositar: " );

        cantidad = entrada.nextDouble(); while (cantidad <= 0 || cantidad > 5000) {
            if (cantidad <= 0) {
        System.out.println( "El depósito debe ser mayor que Q0.00." ); }
            else {
        System.out.println( "El depósito no puede superar Q5,000.00." ); }
        System.out.print( "Ingrese nuevamente la cantidad: " );
        cantidad = entrada.nextDouble(); }

        return cantidad;

    }


    public static double depositar( double saldo, double cantidad) {
        return saldo + cantidad;
    }

//RETIROO 2.0
public static boolean validarRetiro(double cantidad, double saldo) {
        if (cantidad <= 0) {
            System.out.println( "El monto debe ser mayor que Q0.00." );
            return false; }
        if (cantidad % 20 != 0) {
            System.out.println( "El monto debe ser múltiplo de Q20.00." );
            return false; }
        if (cantidad > 2000) {
            System.out.println( "El retiro no puede superar Q2,000.00." );
            return false; }
        if (cantidad > saldo) {
            System.out.println( "Fondos insuficientes." );
            return false; }
        return true; }


    public static double retirar( double saldo, double cantidad) {
        return saldo - cantidad; }

//RETIROOO
    public static double retirar( double saldo, double cantidad, double comision) {
        return saldo - cantidad - comision; }


    public static void mostrarMensaje( String mensaje) {
        System.out.println(mensaje); }

    public static void mostrarMensaje( String mensaje, double cantidad) {
        System.out.println ( mensaje + " Q" + cantidad ); }

//RESUMEEEEEN

    public static void mostrarResumen(
            double saldo,
            int cantidadDepositos,
            double totalDepositado,
            int cantidadRetiros,
            double totalRetirado,
            double totalComisiones,
            int operacionesRechazadas,
            int opcionesInvalidas) {

        System.out.println( "\n========== RESUMEN DEL ESTADO DE CUENTA DEL CLIENTE =======  ===" );
        System.out.printf( "Saldo inicial: Q%.2f\n", 1000.00 );
        System.out.println( "Cantidad de depósitos: " + cantidadDepositos );
        System.out.printf( "Total depositado: Q%.2f\n", totalDepositado );
        System.out.println( "Cantidad de retiros: " + cantidadRetiros );
        System.out.printf( "Total entregado en retiros: Q%.2f\n", totalRetirado );
        System.out.printf( "Total cobrado en comisiones: Q%.2f\n", totalComisiones );
        System.out.println( "Operaciones rechazadas: " + operacionesRechazadas );
        System.out.println( "Opciones inválidas: " + opcionesInvalidas );
        System.out.printf( "Saldo actual: Q%.2f%n", saldo );
        System.out.println( "========================================\n" ); } }