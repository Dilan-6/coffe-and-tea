
package view;

import javax.swing.JOptionPane;

public class InventarioView {
    
    public void mostrarInventario() {
        
        String bienvenida = "Bienvenido al módulo de control de inventario";
        int max_producto = 10;
        String[] nombre_producto = new String[max_producto], ficha_producto = new String[max_producto];
        String confirmacion = "", lista = "", modificacion = "", nuevoNombre = "", nuevoStock = "", nuevoMin = "", nuevoPrecio = "";
        String IngresoStock, IngresoStockMin, IngresoPrecio, continuar = "";
        double[] precio_unitario = new double[max_producto]; 
        int[] stock_actual = new int[max_producto];
        int[] stock_minimo = new int[max_producto];
        int opcion, contador = 0, modificarOpcion = 0, valor = 0, valorMin = 0;
        double valorPrecio = 0;
        boolean cancelado = false;
        
        
        JOptionPane.showMessageDialog(null, bienvenida);
        
       do
       {
           opcion = Integer.parseInt(JOptionPane.showInputDialog("1. Agregar producto " + "\n" +
                                              "2. Modificar producto " + "\n" +
                                              "3. Salir"));
           
           switch (opcion)
        {
            case 1: 
                for (int i = contador; i < max_producto; i++)
        {
             do 
        {
            nombre_producto[i] = JOptionPane.showInputDialog("Ingrese el nombre del producto");
        
            if (nombre_producto[i] == null )
            {
               JOptionPane.showMessageDialog(null, "Registro cancelado");
               cancelado = true; 
               break;
            }
            
            else if (nombre_producto[i].trim().equals("") || nombre_producto[i].matches(".*\\d.*"))
            {
                
                JOptionPane.showMessageDialog(null, "No se introdujo un nombre valido");
            } 
            
        }while (nombre_producto[i].trim().equals("") || nombre_producto[i].matches(".*\\d.*"));
        
        if (cancelado == true)
        {
            break; 
        }
        
        do 
        {
            IngresoStock = JOptionPane.showInputDialog("Ingrese stock");
            
            if (IngresoStock == null)
            {
                JOptionPane.showMessageDialog(null, "Registro cancelado");
                cancelado = true;
                break;
            }
            
            else if (IngresoStock.trim().equals("") || !IngresoStock.matches("\\d+"))
            {
                JOptionPane.showMessageDialog(null, "No se introdujo un valor valido");
            }
            else 
            {
                stock_actual[i] = Integer.parseInt(IngresoStock);
            }
            
            if (stock_actual[i] < 0)
            {
                JOptionPane.showMessageDialog(null, "No se introdujo un valor valido");
            }
        }while (IngresoStock == null || IngresoStock.trim().equals("") || !IngresoStock.matches("\\d+") || stock_actual[i] < 0);
        
        if (cancelado == true)
        {
            break; 
        }
        
        do 
        {
            IngresoStockMin = JOptionPane.showInputDialog("Ingrese el stock mínimo");
            
            if (IngresoStockMin == null)
            {
                JOptionPane.showMessageDialog(null, "Registro cancelado");
                cancelado = true;
                break;
            }
            
            else if (IngresoStockMin.trim().equals("") ||!IngresoStockMin.matches("\\d+"))
            {
                JOptionPane.showMessageDialog(null, "No se introdujo un valor valido");
            }
            else 
            {
                stock_minimo[i] = Integer.parseInt(IngresoStockMin);
            }
            
            if (stock_minimo[i] < 0)
            {
                JOptionPane.showMessageDialog(null, "No se introdujo un valor valido");
            }
            
        }while (IngresoStockMin == null || IngresoStockMin.trim().equals("") ||!IngresoStockMin.matches("\\d+") || stock_minimo[i] < 0);
        
        if (cancelado == true)
        {
            break; 
        }
        
        do 
        {
            
            IngresoPrecio = JOptionPane.showInputDialog("Ingrese el precio unitario");
            
            if (IngresoPrecio == null)
            {
                JOptionPane.showMessageDialog(null, "Registro cancelado");
                cancelado = true;
                break;
            }
            
            else if (IngresoPrecio.trim().equals("") || !IngresoPrecio.matches("\\d+(\\.\\d+)?"))
            {
                JOptionPane.showMessageDialog(null, "No se introdujo un valor valido");
            }
            else
            {
                precio_unitario[i] = Double.parseDouble(IngresoPrecio);
            }
            
            if (precio_unitario[i] < 0)
            {
                JOptionPane.showMessageDialog(null, "No se introdujo un valor valido");
            }
                
        }while (IngresoPrecio == null || IngresoPrecio.trim().equals("") || !IngresoPrecio.matches("\\d+(\\.\\d+)?") || precio_unitario[i] < 0);
        
        
        ficha_producto[i] = "Nombre: " + nombre_producto[i] + "\n" +
                        "Stock actual: " + stock_actual[i] + "\n" +
                        "Stock mínimo: " +stock_minimo[i] + "\n" +
                        "Precio unitario: " +precio_unitario[i];
        
        JOptionPane.showMessageDialog(null, "Datos registrados: " + "\n" +
                                      ficha_producto[i] + "\n");
        
        confirmacion = JOptionPane.showInputDialog("¿Guardar información del producto?");
        
       
        if (confirmacion == null)
        {
             JOptionPane.showMessageDialog(null, "Registro cancelado");
             
        }
        else if (confirmacion.equalsIgnoreCase("Si"))
        {   
            JOptionPane.showMessageDialog(null, "Producto registrado correctamente"); 
            contador++;
            continuar = JOptionPane.showInputDialog("¿Agregar otro producto?");
           
        }
         else if (confirmacion.equalsIgnoreCase("No"))
         {
             JOptionPane.showMessageDialog(null, "Producto no registrado");
             continuar = JOptionPane.showInputDialog("¿Continuar agregando producto?");
         }
        else
         {
             continuar = JOptionPane.showInputDialog("¿Continuar agregando producto?");
         }
         
         
        if (continuar.equalsIgnoreCase("No") || continuar.trim().equals("") || continuar == null)
        {
            JOptionPane.showMessageDialog(null, "Volviendo al menú de opciones...");
            break;
        }
        
        } break;
            case 2: 
        {
            if (contador == 0)
            {
                JOptionPane.showMessageDialog(null, "No hay productos registrados aún.");
                break;
            }

            lista = "";
            for (int i=0; i < contador; i++)
            {
                lista = lista + (i + 1) + ") " + ficha_producto[i] + "\n";
            }

            modificarOpcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "¿Qué producto quiere modificar? \n" + lista));

            if (modificarOpcion < 1 || modificarOpcion > contador)
            {
                JOptionPane.showMessageDialog(null, "Opción inválida.");
                break;
            }

            modificarOpcion--; 
            
            modificacion = JOptionPane.showInputDialog(
                    "¿Qué parte quiere modificar? \n (nombre / stock / minimo / precio)\n" +
                     ficha_producto[modificarOpcion]);

            if (modificacion == null)
            {
                JOptionPane.showMessageDialog(null, "Operación cancelada.");
                break;
            }

           
            if (modificacion.equalsIgnoreCase("nombre"))
            {
                
                do {
                    nuevoNombre = JOptionPane.showInputDialog("Introduzca nuevo nombre");

                    if (nuevoNombre == null)
                    {
                        JOptionPane.showMessageDialog(null, "Modificación cancelada.");
                        break;
                    }
                    else if (nuevoNombre.trim().equals("") || nuevoNombre.matches(".*\\d.*"))
                    {
                        JOptionPane.showMessageDialog(null, "Nombre inválido. No debe contener números ni estar vacío.");
                    }

                } while (nuevoNombre.trim().equals("") || nuevoNombre.matches(".*\\d.*"));

                if (nuevoNombre != null)
                {
                    nombre_producto[modificarOpcion] = nuevoNombre;
                }
            }

           
            else if (modificacion.equalsIgnoreCase("stock"))
            {
               
                do {
                    nuevoStock = JOptionPane.showInputDialog("Introduzca nuevo stock actual");

                    if (nuevoStock == null)
                    {
                        JOptionPane.showMessageDialog(null, "Modificación cancelada.");
                        break;
                    }
                    else if (!nuevoStock.matches("\\d+"))
                    {
                        JOptionPane.showMessageDialog(null, "Entrada inválida. Ingrese solo números enteros.");
                        continue;
                    }

                    valor = Integer.parseInt(nuevoStock);
                    if (valor < 0)
                    {
                        JOptionPane.showMessageDialog(null, "El stock no puede ser negativo.");
                    }

                } while (!nuevoStock.matches("\\d+") || valor < 0);

                if (nuevoStock != null)
                {
                    stock_actual[modificarOpcion] = valor;
                }
            }

            else if (modificacion.equalsIgnoreCase("minimo"))
            {
             
                do {
                    nuevoMin = JOptionPane.showInputDialog("Introduzca nuevo stock mínimo");

                    if (nuevoMin == null)
                    {
                        JOptionPane.showMessageDialog(null, "Modificación cancelada.");
                        break;
                    }
                    else if (!nuevoMin.matches("\\d+"))
                    {
                        JOptionPane.showMessageDialog(null, "Entrada inválida. Ingrese solo números enteros.");
                        continue;
                    }

                    valorMin = Integer.parseInt(nuevoMin);
                    if (valorMin < 0)
                    {
                        JOptionPane.showMessageDialog(null, "El stock mínimo no puede ser negativo.");
                    }

                } while (!nuevoMin.matches("\\d+") || valorMin < 0);

                if (nuevoMin != null)
                {
                    stock_minimo[modificarOpcion] = valorMin;
                }
            }

            else if (modificacion.equalsIgnoreCase("precio"))
            {
              
                do {
                    nuevoPrecio = JOptionPane.showInputDialog("Introduzca nuevo precio unitario");

                    if (nuevoPrecio == null)
                    {
                        JOptionPane.showMessageDialog(null, "Modificación cancelada.");
                        break;
                    }
                    else if (!nuevoPrecio.matches("\\d+(\\.\\d+)?"))
                    {
                        JOptionPane.showMessageDialog(null, "Entrada inválida. Ingrese un número válido.");
                        continue;
                    }

                    valorPrecio = Double.parseDouble(nuevoPrecio);
                    if (valorPrecio < 0)
                    {
                        JOptionPane.showMessageDialog(null, "El precio no puede ser negativo.");
                    }

                } while (!nuevoPrecio.matches("\\d+(\\.\\d+)?") || valorPrecio < 0);

                if (nuevoPrecio != null)
                {
                    precio_unitario[modificarOpcion] = valorPrecio;
                }
            }

            else
            {
                JOptionPane.showMessageDialog(null, "No válido \n. Solo se acepta:\n- nombre\n- stock\n- minimo\n- precio");
                break;
            }

            ficha_producto[modificarOpcion] =
                "Nombre: " + nombre_producto[modificarOpcion] + "\n" +
                "Stock actual: " + stock_actual[modificarOpcion] + "\n" +
                "Stock mínimo: " + stock_minimo[modificarOpcion] + "\n" +
                "Precio unitario: " + precio_unitario[modificarOpcion];

            JOptionPane.showMessageDialog(null, "Producto modificado correctamente.\n" + ficha_producto[modificarOpcion]);

        } break;
            case 3: JOptionPane.showMessageDialog(null, "Volviendo a la pantalla principal");
                    break;
        }
           
       }while(opcion != 3);
      
      
    }
    
}
