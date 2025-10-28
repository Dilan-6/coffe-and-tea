
package view;

import javax.swing.JOptionPane;

public class InventarioView {
    
    public void mostrarInventario() {
        
        String bienvenida = "Bienvenido al módulo de control de inventario";
        int max_producto = 10;
        String[] nombre_producto = new String[max_producto];
        String ficha_producto = "", confirmacion = "";
        String IngresoStock, IngresoStockMin, IngresoPrecio, continuar = "";
        double precio_unitario = 0; 
        int stock_actual = 0, stock_minimo = 0, opcion, contador = 0;
        boolean cancelado = false;
        
        
        JOptionPane.showMessageDialog(null, bienvenida);
        
       do
       {
           opcion = Integer.parseInt(JOptionPane.showInputDialog("1. Agregar producto " + "\n" +
                                              "2. Eliminar producto " + "\n" +
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
                stock_actual = Integer.parseInt(IngresoStock);
            }
            
            if (stock_actual < 0)
            {
                JOptionPane.showMessageDialog(null, "No se introdujo un valor valido");
            }
        }while (IngresoStock == null || IngresoStock.trim().equals("") || !IngresoStock.matches("\\d+") || stock_actual < 0);
        
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
                stock_minimo = Integer.parseInt(IngresoStockMin);
            }
            
            if (stock_minimo < 0)
            {
                JOptionPane.showMessageDialog(null, "No se introdujo un valor valido");
            }
            
        }while (IngresoStockMin == null || IngresoStockMin.trim().equals("") ||!IngresoStockMin.matches("\\d+") || stock_minimo < 0);
        
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
                precio_unitario = Double.parseDouble(IngresoPrecio);
            }
            
            if (precio_unitario < 0)
            {
                JOptionPane.showMessageDialog(null, "No se introdujo un valor valido");
            }
                
        }while (IngresoPrecio == null || IngresoPrecio.trim().equals("") || !IngresoPrecio.matches("\\d+(\\.\\d+)?") || precio_unitario < 0);
        
        
        ficha_producto = "Nombre: " + nombre_producto[i] + "\n" +
                        "Stock actual: " + stock_actual + "\n" +
                        "Stock mínimo: " +stock_minimo + "\n" +
                        "Precio unitario: " +precio_unitario;
        
        JOptionPane.showMessageDialog(null, "Datos registrados: " + "\n" +
                                      ficha_producto + "\n");
        
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
            case 2: JOptionPane.showMessageDialog(null, "Opción eliminar producto (en desarrollo)");
                    break;
            case 3: JOptionPane.showMessageDialog(null, "Volviendo a la pantalla principal");
                    break;
        }
           
       }while(opcion != 3);
        
    }
    
}
