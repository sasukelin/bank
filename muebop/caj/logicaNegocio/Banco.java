package logicaNegocio;

import java.util.LinkedList;
import java.util.Scanner;


public class Banco {
    private static LinkedList<CuentaBancaria> cuentasBancarias = new LinkedList<>();
    private static CuentaBancaria usuarioAutenticado;
    
    static Scanner lector = new Scanner(System.in);

    public static void  registrar(CuentaBancaria usuario) {
        if (!cuentasBancarias.contains(usuario)) {
            cuentasBancarias.add(usuario);

        } else {
            System.out.println("El Usuario " + usuario.getNombre()
                    + " ya existe. No se puede registrar nuevamente.");
        }
    }

    public static void autentificar(String nombreUsuario, String contraseña) {
        CuentaBancaria usuarioEncontrado = null;

        for (CuentaBancaria Usuario : cuentasBancarias) {
            if (Usuario.getNombre().equals(nombreUsuario)) {
                usuarioEncontrado = Usuario;
                break;
            }
        }
        if (usuarioEncontrado != null) {
            if (usuarioEncontrado.getContraseña().equals(contraseña)) {
                usuarioAutenticado = usuarioEncontrado;
                System.out.println("Autenticación exitosa para el usuario " + nombreUsuario);
                userActions();

            } else {
                System.out.println("Contraseña incorrecta ");
            }
        } else {
            System.out.println("El usuario no existe");
        }
    }

    public static void userActions(){
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n");
            System.out.println("Por favor, elija una opción:");
            System.out.println("1. Consultar saldo");
            System.out.println("2. Ingresar dinero");
            System.out.println("3. Retirar dinero");
            System.out.println("4. Realizar una transacción");
            System.out.println("0. Salir");
            System.out.println("\n");
            opcion = Integer.parseInt(lector.nextLine());

            switch (opcion) {
                case 1 -> System.out.println("Tu saldo es de " + usuarioAutenticado.getSaldo());
                case 2 -> {
                    System.out.println("Digite la cantidad a ingresar");
                    double cantidadIngresada = Double.parseDouble(lector.nextLine());
                    usuarioAutenticado.depositar(cantidadIngresada);
                }
                case 3 -> {
                    System.out.println("Digite la cantidad a retirar");
                    double cantidadRetirada = Double.parseDouble(lector.nextLine());
                    usuarioAutenticado.retirar(cantidadRetirada);
                }
                case 4 -> {
                    System.out.println("Ingrese el usuario al que desea enviar");
                    String usuarioDestinatario = lector.nextLine();
                    System.out.println("Ingrese la cantidad a enviar");
                    double cantidadEnviar = Double.parseDouble(lector.nextLine());
                    transferir(usuarioDestinatario, cantidadEnviar);
                }
                case 0 -> System.out.println("\nSaliendo de WolfBank. Hasta la próxima");
                default -> System.out.println("\nOpción no válida. Por favor, elija una opción válida.");
            }
        }
    }




    public static void transferir(String nombreDestinatario, double cantidad) {
        if (usuarioAutenticado != null) {
            CuentaBancaria destinatario = null;
            for (CuentaBancaria usuario : cuentasBancarias) {
                if (usuario.getNombre().equals(nombreDestinatario)) {
                    destinatario = usuario;
                    break;
                }
            }
            if (destinatario != null) {
                if (usuarioAutenticado.getSaldo() >= cantidad) {
                    double saldoOrigen = usuarioAutenticado.getSaldo();
                    double saldoDestinatario = destinatario.getSaldo();

                    usuarioAutenticado.setSaldo(saldoOrigen - cantidad);
                    destinatario.setSaldo(saldoDestinatario + cantidad);
                    System.out.println("Transferencia de " + cantidad + " realizada exitosamente");
                } else {
                    System.out.println("Saldo insuficiente");
                }
            }
        } else {
            System.out.println("El Destinatario " + nombreDestinatario + " no existe");
        }
    }
}
