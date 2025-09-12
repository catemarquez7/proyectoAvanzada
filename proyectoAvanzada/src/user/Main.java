package user;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {

		int eleccion1, eleccion2, eleccion3, eleccion4, eleccion5, eleccion6 = 0;

		String[] iniciosesion = { "Iniciar Sesión", "Registrarse", "Salir" };

		do {
			eleccion1 = JOptionPane.showInternalOptionDialog(null, "Seleccione una opción: ", null, 0, 0, null,
					iniciosesion, iniciosesion[0]);

			switch (eleccion1) {
			case 0: // inicio de sesion

				String[] roles = { "Admin", "Cliente", "Encargado", "Atrás" };

				do {
					eleccion2 = JOptionPane.showInternalOptionDialog(null, "Seleccione una opción: ", null, 0, 0, null,
							roles, roles[0]);

					switch (eleccion2) {
					case 0: // Admin

						// inicia sesion ADMIN
						String usuario = JOptionPane.showInputDialog(
								"Ingrese su nombre de usuario:\nEn caso de querer ingresar, especificar: Admin");
						if (usuario.equalsIgnoreCase("Admin")) {
							String contraenia = JOptionPane.showInputDialog(
									"Ingrese su contraseña:\nEn caso de querer ingresar, especificar: Admin1");
							if (contraenia.equalsIgnoreCase("Admin1")) {

								// PROGRAMA ADMIN

							} else {
								JOptionPane.showMessageDialog(null,
										"La contraseña no es correcta, intente 						nuevamente");
								break;
							}
						} else {
							JOptionPane.showMessageDialog(null,
									"El nombre de usuario no es correcto, intente 						nuevamente");
							break;
						}
						break;

					case 1: // Cliente

						// inicia sesion CLIENTE
						String usuario2 = JOptionPane.showInputDialog(
								"Ingrese su nombre de usuario:\nEn caso de querer ingresar, especificar: Cliente");
						if (usuario2.equalsIgnoreCase("Cliente")) {
							String contraenia2 = JOptionPane.showInputDialog(
									"Ingrese su contraseña:\nEn caso de querer ingresar, especificar: Cliente1");
							if (contraenia2.equalsIgnoreCase("Cliente1")) {

								// PROGRAMA CLIENTE

							} else {
								JOptionPane.showMessageDialog(null,
										"La contraseña no es correcta, intente 						nuevamente");
								break;
							}
						} else {
							JOptionPane.showMessageDialog(null,
									"El nombre de usuario no es correcto, intente 						nuevamente");
							break;
						}
						break;

					case 2: // Encargado

						// inicia sesion ENCARGADO
						String usuario3 = JOptionPane.showInputDialog(
								"Ingrese su nombre de usuario:\nEn caso de querer ingresar, especificar: Encargado");
						if (usuario3.equalsIgnoreCase("Encargado")) {
							String contraenia3 = JOptionPane.showInputDialog(
									"Ingrese su contraseña:\nEn caso de querer ingresar, especificar: Encargado1");
							if (contraenia3.equalsIgnoreCase("Encargado1")) {
								// PROGRAMA ENCARGADO

								String[] acciones = { "Reservas", "Habitaciones", "Actividades", "Check-in",
										"Check-out", "Datos Cliente", "Historial", "Promociones", "Atrás" };

								do {
									eleccion3 = JOptionPane.showInternalOptionDialog(null,
											"Bienvenido Encargado!\nSeleccione una opción: ", null, 0, 0, null,
											acciones, acciones[0]);
									switch (eleccion3) {
									case 0: // Reservas
										String[] reservas = { "Ver reservas", "Modificar reservas", "Atrás" };

										do {
											eleccion4 = JOptionPane.showInternalOptionDialog(null,
													"Seleccione una opción: ", null, 0, 0, null, reservas, reservas[0]);
											switch (eleccion4) {
											case 0:
												JOptionPane.showMessageDialog(null,
														"Acá se visualizarán las reservas");
												break;
											case 1:
												JOptionPane.showMessageDialog(null,
														"Acá se modificarán las reservas");
												break;
											case 2:
												JOptionPane.showMessageDialog(null,
														"Vuelve al menú anterior");
												break;

											}

										} while (eleccion4 != 2);

										break;

									case 1: // Habitaciones
										String[] habitaciones = { "Ver habitaciones", "Modificar habitaciones",
												"Atrás" };
										do {
											eleccion5 = JOptionPane.showInternalOptionDialog(null,
													"Seleccione una opción: ", null, 0, 0, null, habitaciones,habitaciones[0]);
											switch (eleccion5) {
											case 0:
												JOptionPane.showMessageDialog(null,
														"Acá se visualizarán las habitaciones");
												break;
											case 1:
												JOptionPane.showMessageDialog(null,
														"Acá se modificarán las habitaciones");
												break;
											case 2:
												JOptionPane.showMessageDialog(null,
														"Vuelve al menú anterior");
												break;

											}

										} while (eleccion5 != 2);
										break;

									case 2: // Actividades
										String[] actividades = { "Ver actividades", "Modificar actividades", "Atrás" };
										do {
											eleccion6 = JOptionPane.showInternalOptionDialog(null,
													"Seleccione una opción: ", null, 0, 0, null, actividades,actividades[0]);
											switch (eleccion6) {
											case 0:
												JOptionPane.showMessageDialog(null,
														"Acá se visualizarán las actividades");
												break;
											case 1:
												JOptionPane.showMessageDialog(null,
														"Acá se modificarán las actividades");
												break;
											case 2:
												JOptionPane.showMessageDialog(null,
														"Vuelve al menú anterior");
												break;

											}

										} while (eleccion6 != 2);

										break;

									case 3: // Check in

										JOptionPane.showMessageDialog(null,
												"Acá se pedirán los datos y se relaizará el check-in");
										break;

									case 4: // Check-out

										JOptionPane.showMessageDialog(null,
												"Acá se pedirán los datos y se relaizará el check-out");
										break;

									case 5: // Datos cliente

										JOptionPane.showMessageDialog(null,
												"Acá se pedirán mostrarán los datos del cliente");
										break;

									case 6: // Historial

										JOptionPane.showMessageDialog(null,
												"Acá se mostrará el historial del encargado");
										break;

									case 7: // Promociones

										JOptionPane.showMessageDialog(null, "Acá se cargarán las promociones");
										break;

									case 8:

										JOptionPane.showMessageDialog(null, "Volver al menú");
										break;
									}
								} while (eleccion3 != 8);

							} else {
								JOptionPane.showMessageDialog(null,
										"La contraseña no es correcta, intente 						nuevamente");
								break;
							}
						} else {
							JOptionPane.showMessageDialog(null,
									"El nombre de usuario no es correcto, intente 						nuevamente");
							break;
						}
						break;

					case 3: // Atrás

						JOptionPane.showMessageDialog(null, "Volver al menú");
						break;
					}// fin 2do switch

				} while (eleccion2 != 3);
				break;

			case 1: // registrarse

				JOptionPane.showMessageDialog(null, "Aca se pedirán todos los datos de registro.");
				break;

			case 2:

				JOptionPane.showMessageDialog(null, "El programa cerrará");
				break;

			}// fin 1er switch

		} while (eleccion1 != 2);

	}

}
