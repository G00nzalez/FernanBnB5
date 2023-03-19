package org.example;

import org.example.models.*;
import org.example.utils.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        Controller c = new Controller();
        mock(c);
        Object user = null;

        do {
            switch (menuInicio()) {
                case 1 -> { //Loggear un usuario
                    user = login(c);
                    if (user == null) System.out.println("Credenciales incorrectas");
                }
                case 2 -> { //Registrar un usuario nuevo
                    user = registro(c);
                    if (user == null)
                        System.out.println("Error al registrar su usuario." + '\n' + "Pruebe con otro email.");
                }
                case -1 -> {
                    System.out.println("Debe introducir un número para este menú");
                }
                default -> System.out.println("Opción incorrecta.");
            }

            // Si el usuario ya está loggeado
            if (user != null) {
                if (user instanceof User) menuUsuario(c, (User) user);
                if (user instanceof Propietario) menuPropietario(c, (Propietario) user);
                if (user instanceof Admin) menuAdmin(c, (Admin) user);
            }

            user = null;

        } while (true);

    }

    //mock
    private static void mock(Controller c) {
        c.addAdministrador("admin", "admin", "admin@fernanbnb.com");
        c.addAdministrador("admin2", "admin", "admin2@fernanbnb.com");
        c.addUsuario("user", "user", "user@fernanbnb.com");
        c.addUsuario("user2", "user", "user2@fernanbnb.com");
        c.addPropietario("propietario", "propietario", "propietario@fernanbnb.com");
        c.addPropietario("propietario2", "propietario", "propietario2@fernanbnb.com");

    }

    //Registro de un nuevo usuario.
    private static Object registro(Controller c) {
        int op = 0;
        String nombre, passw, email;

        System.out.println("===== REGISTRO =====");
        do {
            System.out.println("""
                    Seleccione su tipo de usuario
                    1. Usuario
                    2. Propietario""");
            try {
                op = Integer.parseInt(s.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opción incorrecta, debe introducir un número");
            }

        } while (op != 1 && op != 2 && op != 100);

        System.out.print("Nombre de usuario: ");
        nombre = s.nextLine();
        System.out.print("Contraseña: ");
        passw = s.nextLine();
        System.out.print("Email: ");
        email = s.nextLine();

        if (c.existeEmail(email)) return null;

        if (op == 1) return c.addUsuario(nombre, passw, email);
        if (op == 2) return c.addPropietario(nombre, passw, email);
        if (op == 100) return c.addAdministrador(nombre, passw, email);


        return null;
    }

    //Log de usuario.
    private static Object login(Controller c) {
        String passw, email;

        System.out.println("===== LOGGING =====");

        System.out.print("Introduzca su email: ");
        email = s.nextLine();
        System.out.print("Contraseña: ");
        passw = s.nextLine();

        return c.login(email, passw);

    }

    //Menu para los administradores.
    private static void menuAdmin(Controller c, Admin admin) {
        int op = 0;

        do {
            System.out.println("^^^^ MENU ADMINISTRADOR ^^^^");
            System.out.println("Bienvenido: " + admin.getNombre());
            System.out.println("Tiene " + c.numeroUsuarios() + " usuarios. Viviendas " + c.numeroViviendas() + ". Reservas " + c.numeroReservas());
            System.out.println("""
                    Menú de operaciones:
                    1. Ver todas las viviendas en alquiler.
                    2. Ver todos los usuarios.
                    3. Ver todas las reservas.
                    4. Ver perfil.
                    5. Modificar perfil.
                    6. Salir""");
            try {
                op = Integer.parseInt(s.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debe introducir un número en este menú.");
            }

            switch (op) {
                case 1 -> { //Ver todas las viviendas
                    ArrayList<Vivienda> mostrarViviendas = c.getAllViviendas();
                    if (mostrarViviendas.isEmpty()) System.out.println("Aún no se ha registrado ninguna vivienda");
                    else System.out.println(mostrarViviendas);
                    Utils.pulseParaContinuar();
                }
                case 2 -> { //Ver todos los usuarios
                    //Mostramos los usuarios normales.
                    ArrayList<User> usuarios = c.getAllUsuarios();
                    if (usuarios.isEmpty()) System.out.println("Aún no se ha registrado ningún usuario.");
                    else {
                        for (User u :
                                usuarios) {
                            System.out.println(u);
                        }
                    }
                    System.out.println();

                    //Mostramos los propietarios.
                    ArrayList<Propietario> propietarios = c.getAllPropietarios();
                    if (propietarios.isEmpty()) System.out.println("Aún no se ha registrado ningún propietario.");
                    else {
                        for (Propietario p :
                                propietarios) {
                            System.out.println(p);
                        }
                    }
                    System.out.println();
                    //Mostramos los admins
                    ArrayList<Admin> admins = c.getAllAdmins();
                    if (admins.isEmpty()) System.out.println("Aún no se ha registrado ningún administrador.");
                    else {
                        for (Admin a :
                                admins) {
                            System.out.println(a);
                        }
                    }
                    Utils.pulseParaContinuar();
                }
                case 3 -> { //Ver todas las reservas
                    ArrayList<Reserva> reservas = c.getReservas();
                    if (reservas.isEmpty()) System.out.println("No se ha realizado ninguna reserva aún.");
                    else {
                        for (Reserva r :
                                reservas) {
                            System.out.println(r);
                        }
                    }
                    Utils.pulseParaContinuar();
                }
                case 4 -> { //Ver perfil
                    System.out.println(admin);
                    Utils.pulseParaContinuar();
                }
                case 5 -> { //Modificar perfil
                    System.out.println("*** Modificación de perfil ***");
                    System.out.print("Introduce el nuevo nombre de perfil: ");
                    String nuevoNombre = s.nextLine();
                    System.out.print("Introduce la nueva contraseña: ");
                    String nuevoPassw = s.nextLine();
                    System.out.print("Introduce el nuevo email: ");
                    String nuevoEmail = s.nextLine();

                    if(c.existeEmail(nuevoEmail)){
                        System.out.println("Email ya en uso. Pruebe con otro.");
                        break;
                    }

                    Admin temp = new Admin(admin.getId(), nuevoNombre, nuevoPassw, nuevoEmail);
                    if (c.modificaPerfil(temp)) {
                        System.out.println("""
                                Perfil modificado con éxito
                                Cierre sesión y vuelva a iniciar para ver sus cambios reflejados.""");

                    } else System.out.println("Error al modificar el perfil. Inténtelo más tarde.");

                    Utils.pulseParaContinuar();
                }
                case 6 -> { //Cerrar sesión
                    System.out.print("Cerrando sesión ");
                    Utils.cerrarPrograma();
                }

            }
        } while (op != 6);
    }

    //Menu para los propietarios
    private static void menuPropietario(Controller c, Propietario propietario) {
        int op = 0;

        do {
            System.out.println("^^^^ MENU PROPIETARIO ^^^^");
            System.out.println("Bienvenido: " + propietario.getNombre() + ". Tiene " + c.totalViviendas(propietario) + " viviendas.");
            System.out.println("Tiene " + c.totalReservas(propietario) + " reservas en sus alojamientos.");
            System.out.println("""
                    Menú de operaciones:
                    1. Ver mis viviendas en alquiler.
                    2. Editar mis viviendas.
                    3. Ver las reservas de mis viviendas.
                    4. Establecer un periodo de no disponibilidad para una vivienda.
                    5. Ver mi perfil.
                    6. Modificar mi perfil.
                    7. Salir""");
            try {
                op = Integer.parseInt(s.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debe introducir un número en este menú.");
            }

            switch (op) {
                case 1 -> { //Ver viviendas en alquiler
                    ArrayList<Vivienda> viviendas = c.buscaViviendasByPropietario(propietario);
                    if (!viviendas.isEmpty()) {
                        for (Vivienda v :
                                viviendas) {
                            System.out.println(v);
                        }
                    } else System.out.println("No hay viviendas registradas aún");
                    Utils.pulseParaContinuar();
                }
                case 2 -> { //Editar viviendas
                    int maxOcupantes = 0, id = -1;
                    double precioNoche = 0.0;

                    System.out.println("--- Editar una vivienda ---");
                    do {
                        System.out.print("Introduzca la id de la vivienda (pulse 0 para registrar una nueva vivienda): ");
                        try {
                            id = Integer.parseInt(s.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Debe introducir un valor numérico.");
                        }
                    } while (id < 0);

                    if (id == 0) System.out.println("--- Registro de nueva vivienda ---");
                    System.out.print("Introduce el título: ");
                    String titulo = s.nextLine();
                    System.out.print("Introduce la descripción: ");
                    String descripcion = s.nextLine();
                    System.out.print("Introduce la localidad: ");
                    String localidad = s.nextLine();
                    System.out.print("Introduce la provincia: ");
                    String provincia = s.nextLine();
                    do {
                        System.out.print("Introduce el número máximo de ocupantes: ");
                        try {
                            maxOcupantes = Integer.parseInt(s.nextLine());

                        } catch (NumberFormatException e) {
                            System.out.println("Debe introducir un valor numérico.");
                        }
                    } while (maxOcupantes < 1);

                    do {
                        System.out.print("Introduce el precio por noche: ");
                        try {
                            precioNoche = Double.parseDouble(s.nextLine());

                        } catch (NumberFormatException e) {
                            System.out.println("Debe introducir un valor numérico.");
                        }
                    } while (precioNoche < 1);

                    //Si la id = 0 significa que es una vivienda nueva, entonces hay que generarle una id nuevo. Si es!= 0, introducimos la id que
                    Vivienda temp;
                    if (id == 0) {
                        temp = new Vivienda(c.generaIdVivienda(), titulo, descripcion, localidad, provincia, maxOcupantes, precioNoche);
                    } else {
                        temp = c.buscaViviendaByIdPropietario(id, propietario);
                    }

                    if (temp == null) System.out.println("Vivienda no encontrada. Compruebe la id de su vivienda.");
                    else {

                        if (propietario.addVivienda(temp)) {
                            if (id == 0) System.out.println("Vivienda registrada con éxito");
                            else System.out.println("Vivienda modificada con éxito.");
                        } else {
                            if (id == 0) System.out.println("Error al registrar su vivienda.");
                            else System.out.println("Error al modificar su vivienda. ");
                        }
                    }

                    Utils.pulseParaContinuar();
                }
                case 3 -> { //Ver reservas en viviendas
                    ArrayList<Vivienda> viviendas = c.getViviendasReservadasPropietario(propietario);
                    if (viviendas.isEmpty()) System.out.println("No han realizado ninguna reserva aún.");
                    for (Vivienda v :
                            viviendas) {
                        System.out.println(v);
                    }
                    Utils.pulseParaContinuar();
                }
                case 4 -> { //Establecer periodo de no disponibilidad
                    int id = -1;
                    System.out.println("=== PERIODO NO DISPONIBILIDAD ===");
                    do {
                        System.out.print("Introduce la id de la vivienda (0 para cancelar): ");
                        try {
                            id = Integer.parseInt(s.nextLine());

                        } catch (NumberFormatException e) {
                            System.out.println("Debe introducir un valor numérico.");
                        }
                        if (id == 0) break;

                    } while (id < 5000 || id > 6000);

                    Vivienda v = c.buscaViviendaId(id);
                    if (v != null) {
                        int day = -1, month = -1, year = -1, noches = -1;
                        System.out.print("Introduzca el día de entrada: ");
                        try {
                            day = Integer.parseInt(s.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Introduzca un valor numérico");
                        }
                        System.out.print("Introduzca el mes de entrada: ");
                        try {
                            month = Integer.parseInt(s.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Introduzca un valor numérico");
                        }
                        System.out.print("Introduzca el año de entrada: ");
                        try {
                            year = Integer.parseInt(s.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Introduzca un valor numérico");
                        }
                        System.out.print("Introduzca el número de noches: ");
                        try {
                            noches = Integer.parseInt(s.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Introduzca un valor numérico");
                        }

                        LocalDate fecha = LocalDate.of(year, month, day);

                        if (v.tieneDisponibilidad(fecha, noches)) {
                            c.addReserva(v, null, new Reserva(c.generaIdReserva(), v.getId(), propietario.getId(), fecha, noches, v.getPrecioNoche(), -1));
                        } else System.out.println("No se ha podido realizar la operación por falta de días hábiles.");
                    } else System.out.println("Vivienda no encontrada. Compruebe que ha insertado bien la id.");
                }
                case 5 -> { //Ver perfil
                    System.out.println(propietario);
                    Utils.pulseParaContinuar();
                }
                case 6 -> { //Modificar perfil
                    System.out.println("*** Modificación de perfil ***");
                    System.out.print("Introduce el nuevo nombre de perfil: ");
                    String nuevoNombre = s.nextLine();
                    System.out.print("Introduce la nueva contraseña: ");
                    String nuevoPassw = s.nextLine();
                    System.out.print("Introduce el nuevo email: ");
                    String nuevoEmail = s.nextLine();
                    
                    if(c.existeEmail(nuevoEmail)){
                        System.out.println("Email ya en uso. Pruebe con otro.");
                        break;
                    }

                    Propietario temp = new Propietario(propietario.getId(), nuevoNombre, nuevoPassw, nuevoEmail);
                    if (c.modificaPerfil(temp)) {
                        System.out.println("""
                                Perfil modificado con éxito
                                Cierre sesión y vuelva a iniciar para ver sus cambios reflejados.""");

                    } else System.out.println("Error al modificar el perfil. Inténtelo más tarde.");

                    Utils.pulseParaContinuar();
                }
                case 7 -> { //Cerrar
                    System.out.print("Cerrando sesión ");
                    Utils.cerrarPrograma();
                }
            }
        } while (op != 7);
    }

    //Menu para los usuarios.
    private static void menuUsuario(Controller c, User user) {
        int op = 0;

        do {
            System.out.println("^^^^ MENU USUARIO ^^^^");
            System.out.println("Bienvenido: " + user.getNombre() + ". Tiene " + c.totalReservasUsuario(user) + " reservas pendientes.");
            System.out.println("""
                    Menú de operaciones:
                    1. Búsqueda de alojamientos.
                    2. Ver mis reservas.
                    3. Modificar mis reservas.
                    4. Ver mi perfil.
                    5. Modificar mis datos.
                    6. Salir""");
            try {
                op = Integer.parseInt(s.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debe introducir un número en este menú.");
            }

            switch (op) {
                case 1 -> { //Búsqueda de alojamientos.
                    System.out.print("=== BUSQUEDA DE ALOJAMIENTOS ===");
                    System.out.print("Introduzca una descripción con la cual filtrar: ");
                    String parametro = s.nextLine();

                    ArrayList<Vivienda> viviendas = c.buscaViviendaByParametro(parametro);
                    if (viviendas.isEmpty())
                        System.out.println("No se ha encontrado ninguna vivienda con esa busqueda.");
                    else {
                        for (int i = 0; i < viviendas.size(); i++) {
                            System.out.println((i + 1) + ") " + viviendas.get(i).getTitulo());
                        }

                        int reserva = -1;

                        do {
                            System.out.print("¿Qué vivienda quiere reserva? Introduzca 0 para cancelar la reserva: ");
                            try {
                                reserva = Integer.parseInt(s.nextLine()) - 1;
                            } catch (NumberFormatException e) {
                                System.out.println("Debe introducir un valor numérico");
                            }

                            if (reserva > viviendas.size() + 1) System.out.println("Número no válido.");

                        } while (reserva == -1 || reserva < viviendas.size() + 1);

                        if (reserva == -1) System.out.println("Se ha cancelado su reserva.");
                        else {
                            int ocupantes = -1;
                            Vivienda v = viviendas.get(reserva);

                            do {
                                System.out.print("Introduzca el número de ocupantes: ");
                                try {
                                    ocupantes = Integer.parseInt(s.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Introduzca un valor numérico");
                                }

                            } while (ocupantes < 0);
                            if (ocupantes > v.getMaxOcupantes()) {
                                System.out.println("Ha introducido más ocupantes de los permitidos");
                                break;
                            } else {
                                int day = -1, month = -1, year = -1, noches = -1;
                                System.out.print("Introduzca el día de entrada: ");
                                try {
                                    day = Integer.parseInt(s.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Introduzca un valor numérico");
                                }
                                System.out.print("Introduzca el mes de entrada: ");
                                try {
                                    month = Integer.parseInt(s.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Introduzca un valor numérico");
                                }
                                System.out.print("Introduzca el año de entrada: ");
                                try {
                                    year = Integer.parseInt(s.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Introduzca un valor numérico");
                                }
                                System.out.print("Introduzca el número de noches: ");
                                try {
                                    noches = Integer.parseInt(s.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Introduzca un valor numérico");
                                }

                                LocalDate fecha = LocalDate.of(year, month, day);
                                if (v.tieneDisponibilidad(fecha, noches)) {

                                    Reserva r = new Reserva(c.generaIdReserva(), v.getId(), user.getId(), fecha, noches, v.getPrecioNoche(), ocupantes);
                                    c.addReserva(v, user, r);
                                } else System.out.println("Reserva no disponible.");

                            }
                        }
                    }

                    Utils.pulseParaContinuar();
                }
                case 2 -> { //Ver reservas.
                    ArrayList<Reserva> reservas = c.getReservasByUser(user);

                    if (!reservas.isEmpty()) {

                        for (Reserva r :
                                reservas) {
                            System.out.println(r);
                        }
                    } else System.out.println("No se han realizado reservas todavía.");

                    Utils.pulseParaContinuar();

                }
                case 3 -> { //Modificar reservas.
                    ArrayList<Reserva> reservas = c.getReservasByUser(user);
                    int reservaSeleccionada = -1;
                    if (!reservas.isEmpty()) {

                        for (int i = 0; i < reservas.size(); i++) {
                            System.out.println("Reserva número: " + (i + 1) + '\n'
                                    + reservas.get(i) + '\n' +
                                    "=========");
                        }

                        do {
                            System.out.print("Indica que reserva quieres modificar: ");
                            try {
                                reservaSeleccionada = Integer.parseInt(s.nextLine()) - 1;
                            } catch (NumberFormatException e) {
                                System.out.println("Debe introducir un valor numérico");
                            }
                        } while (reservaSeleccionada == -1 || reservaSeleccionada > reservas.size());

                        int id = -1;

                        System.out.println("=== MODIFICACIÓN DE RESERVA ===");

                        do {
                            System.out.print("Introduce la id de la vivienda (pulse 0 para borrar la reserva):  ");
                            try {
                                id = Integer.parseInt(s.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Introduzca un valor numérico.");
                            }
                        } while (id == -1);

                        if (id == 0){
                            if (user.deleteReserva(user.getReservas().get(reservaSeleccionada))) System.out.println("Reserva eliminada con éxito.");
                            else System.out.println("Error al eliminar la reserva.");
                            break;
                        }
                        Vivienda vivienda = c.buscaViviendaId(id);
                        if (vivienda == null) System.out.println("Vivienda no encontrada. Pruebe con otra id.");
                        else {
                            int ocupantesNuevo = 0;
                            double precioNuevo = 0;
                            System.out.print("Introduce el nuevo título: ");
                            String tituloNuevo = s.nextLine();
                            System.out.print("Introduce la nueva descripción: ");
                            String descripcionNueva = s.nextLine();
                            System.out.print("Introduce la nueva localidad: ");
                            String localidadNueva = s.nextLine();
                            System.out.print("Introduce la nueva provincia: ");
                            String provinciaNueva = s.nextLine();
                            do {
                                System.out.print("Introduce el número máximo de ocupantes: ");
                                try {
                                    ocupantesNuevo = Integer.parseInt(s.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Debe introducir un valor numérico.");
                                }
                            } while (ocupantesNuevo < 1);

                            do {
                                System.out.print("Introduce el número precio: ");
                                try {
                                    precioNuevo = Double.parseDouble(s.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Debe introducir un valor numérico.");
                                }
                            } while (precioNuevo < 1);

                            Vivienda viviendaNueva = new Vivienda(vivienda.getId(), tituloNuevo, descripcionNueva, localidadNueva, provinciaNueva, ocupantesNuevo, precioNuevo);
                            if (c.modificaVivienda(viviendaNueva)) System.out.println("Vivienda actualizada con éxito");
                            else
                                System.out.println("No se ha podido modificar la vivienda. Compruebe que no tenga reservas realizadas.");
                        }


                    } else System.out.println("No ha realizado reservas aún.");

                    Utils.pulseParaContinuar();
                }
                case 4 -> { //Ver perfil
                    System.out.println(user);
                    Utils.pulseParaContinuar();

                }
                case 5 -> { //Modificar datos
                    System.out.println("*** Modificación de perfil ***");
                    System.out.print("Introduce el nuevo nombre de perfil: ");
                    String nuevoNombre = s.nextLine();
                    System.out.print("Introduce la nueva contraseña: ");
                    String nuevoPassw = s.nextLine();
                    System.out.print("Introduce el nuevo email: ");
                    String nuevoEmail = s.nextLine();

                    if(c.existeEmail(nuevoEmail)){
                        System.out.println("Email ya en uso. Pruebe con otro.");
                        break;
                    }

                    User temp = new User(user.getId(), nuevoNombre, nuevoPassw, nuevoEmail);
                    if (c.modificaPerfil(temp)) {
                        System.out.println("""
                                Perfil modificado con éxito
                                Cierre sesión y vuelva a iniciar para ver sus cambios reflejados.""");

                    } else System.out.println("Error al modificar el perfil. Inténtelo más tarde.");

                    Utils.pulseParaContinuar();
                }
                case 6 -> { //Salir
                    System.out.print("Cerrando sesión ");
                    Utils.cerrarPrograma();
                    System.out.println();
                }
            }

        } while (op != 6);
    }


    //Menu de inicio.
    private static int menuInicio() {
        System.out.println("""
                 _______  _______ .______      .__   __.      ___      .__   __. .______   .__   __. .______  \s
                |   ____||   ____||   _  \\     |  \\ |  |     /   \\     |  \\ |  | |   _  \\  |  \\ |  | |   _  \\ \s
                |  |__   |  |__   |  |_)  |    |   \\|  |    /  ^  \\    |   \\|  | |  |_)  | |   \\|  | |  |_)  |\s
                |   __|  |   __|  |      /     |  . `  |   /  /_\\  \\   |  . `  | |   _  <  |  . `  | |   _  < \s
                |  |     |  |____ |  |\\  \\----.|  |\\   |  /  _____  \\  |  |\\   | |  |_)  | |  |\\   | |  |_)  |\s
                |__|     |_______|| _| `._____||__| \\__| /__/     \\__\\ |__| \\__| |______/  |__| \\__| |______/ \s""");

        System.out.println("""
                                
                ======================
                1. Logging.
                2. Registrar usuario.
                3. Salir""");
        System.out.print("Introduzca una opción: ");
        try {
            return Integer.parseInt(s.nextLine());

        } catch (NumberFormatException e) {
            return -1;
        }
    }
}