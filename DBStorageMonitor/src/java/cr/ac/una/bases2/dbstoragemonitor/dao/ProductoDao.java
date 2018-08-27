package cr.ac.una.bases2.dbstoragemonitor.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author _Adrián_Prendas_
 */
public class ProductoDao extends ABaseDAO{
    private static ProductoDao uniqueInstance;
    private static final String CREATE_PRODUCT = "{call insertarProducto(?,?,?,?,?)}";
    private static final String READ_PRODUCTO_BY_TYPE = "{?=call buscarProductoTipo(?)}";
    private static final String READ_PRODUCTO_BY_NAME = "{?=call buscarProductoNombre(?)}";
    private static final String READ_PRODUCTO_BY_PK = "{?=call buscarProductoCodigo(?)}";
    private static final String READ_ALL_PRODUCTS = "{?=call listaProductos()}";
    private static final String UPDATE_PRODUCT = "{call modificarProducto(?,?,?,?,?)}";
    private static final String DELETE_PRODUCT = "{call eliminarProductos(?)}";
    
    private static final String LIST_PRODUCTS = "{?=call listaProductos()}";
    
    private ProductoDao(){
        super();
    }
    
    public static ProductoDao getInstance(){
        if(uniqueInstance == null)
            uniqueInstance = new ProductoDao();
        return uniqueInstance;
    }
    
    /*
    @Override
    public boolean create(Producto p){
        CallableStatement pstmt = null;
        boolean resp = true;
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            System.out.println("No se ha localizado el driver");
            e.printStackTrace();
            resp=false;
        } catch (SQLException e) {
            System.out.println("La base de datos no se encuentra disponible");
            e.printStackTrace();
            resp=false;
        }
        try{
            pstmt = conexion.prepareCall(CREATE_PRODUCT);                                                
            pstmt.setInt(1,p.getCodigo());
            pstmt.setString(2,p.getNombre());
            pstmt.setFloat(3,p.getPrecio());
            pstmt.setInt(4,((p.getImportado())?1:0));
            pstmt.setString(5,p.getTipo());
            pstmt.execute();//retorna true o false
        } catch (SQLException e) {
            System.out.println("Llave duplicada");
            e.printStackTrace();
            resp=false;
        }
        finally{
            try {
                if (pstmt!=null)
                    pstmt.close();                                    
                desconectar();
            } catch (SQLException e) {
                System.out.println("Estatutos invalidos o nulos");
                e.printStackTrace();
                resp=false;
            }
        }
        if(resp)
            System.out.println("se creo con exito: "+p.toString());
        return resp;
    }

    @Override
    public List<Producto> read(Producto p1) {
        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Producto p2 = null;
        CallableStatement pstmt=null;  
        try {
            conectar();
        }catch (ClassNotFoundException e) {
            System.out.println("No se ha localizado el driver");
        } catch (SQLException e) {
            System.out.println("La base de datos no se encuentra disponible");
        }
        try{
            if(!p1.getNombre().isEmpty()){
                pstmt = conexion.prepareCall(READ_PRODUCTO_BY_NAME);            
                pstmt.setString(2,p1.getNombre());            
            }else if(!p1.getTipo().isEmpty()){
                pstmt = conexion.prepareCall(READ_PRODUCTO_BY_TYPE);            
                pstmt.setString(2,p1.getTipo());            
            }else if(p1.getNombre().isEmpty()&&p1.getTipo().isEmpty()){
                pstmt = conexion.prepareCall(READ_PRODUCTO_BY_PK);            
                pstmt.setInt(2,p1.getCodigo());            
            }
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);            
            pstmt.execute();
            rs = (ResultSet)pstmt.getObject(1); 
            while (rs.next()) {
                p2 = new Producto();
                p2.setCodigo(rs.getInt("codigo"));
                p2.setNombre(rs.getString("nombre"));
                p2.setPrecio(rs.getFloat("precio"));
                p2.setImportado(((Integer.parseInt(rs.getString("importado"))==0)?false:true));
                p2.setTipo(rs.getString("tipo"));
                p2.setPorcentaje(Float.parseFloat(rs.getString("porcentaje")));
                p2.setImpuesto(Float.parseFloat(rs.getString("impuesto")));
                p2.setPrecioFinal(Float.parseFloat(rs.getString("preciofinal")));
                coleccion.add(p2);
            }
        }
        catch (SQLException e) {
            System.out.println("Sentencia no valida");
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                System.out.println("Estatutos invalidos o nulos");
               e.printStackTrace();
            }
        }
        return coleccion;
    }
    
    public Producto readPk(Integer key) {
        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Producto p2 = null;
        CallableStatement pstmt=null;  
        try {
            conectar();
        }catch (ClassNotFoundException e) {
            System.out.println("No se ha localizado el driver");
        } catch (SQLException e) {
            System.out.println("La base de datos no se encuentra disponible");
        }
        try{
            
            pstmt = conexion.prepareCall(READ_PRODUCTO_BY_PK);            
            pstmt.setInt(2,key);            
            
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);            
            pstmt.execute();
            rs = (ResultSet)pstmt.getObject(1); 
            while (rs.next()) {
                p2 = new Producto();
                p2.setCodigo(rs.getInt("codigo"));
                p2.setNombre(rs.getString("nombre"));
                p2.setPrecio(rs.getFloat("precio"));
                p2.setImportado(((Integer.parseInt(rs.getString("importado"))==0)?false:true));
                p2.setTipo(rs.getString("tipo"));
                p2.setPorcentaje(Float.parseFloat(rs.getString("porcentaje")));
                p2.setImpuesto(Float.parseFloat(rs.getString("impuesto")));
                p2.setPrecioFinal(Float.parseFloat(rs.getString("preciofinal")));
                coleccion.add(p2);
            }
        }
        catch (SQLException e) {
            System.out.println("Sentencia no valida");
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                System.out.println("Estatutos invalidos o nulos");
               e.printStackTrace();
            }
        }
        return (Producto)coleccion.get(0);
    }

    @Override
    public boolean update(Producto p) {
        PreparedStatement pstmt = null;
        boolean resp=true;
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            System.out.println("No se ha localizado el driver");
            e.printStackTrace();
            resp=false;
        } catch (SQLException e) {
            System.out.println("La base de datos no se encuentra disponible");
            e.printStackTrace();
            resp=false;
        }
        try {            
            pstmt = conexion.prepareStatement(UPDATE_PRODUCT);
            pstmt.setInt(1,p.getCodigo());
            pstmt.setString(2,p.getNombre());
            pstmt.setFloat(3,p.getPrecio());
            pstmt.setInt(4,((p.getImportado())?1:0));
            pstmt.setString(5,p.getTipo());            
            int resultado = pstmt.executeUpdate();
            if (resultado == 0)//si es diferente de 0 es porq si afecto un registro o mas
                resp=false;            
            else
               System.out.println("Modificacion Exitosa");            
        }catch (SQLException e) {
            System.out.println("Sentencia no valida");
            e.printStackTrace();
            resp=false;
        }
        finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
               resp=false;
            }
        }
        return resp;
    }

    @Override
    public boolean delete(Integer key) {
        boolean resp=true;
        ResultSet rs = null;     
        ArrayList coleccion = new ArrayList();
        Producto elcontacto = null;
        CallableStatement pstmt=null;  
        try {
            conectar();
        } catch (ClassNotFoundException e) {
           System.out.println("No se ha localizado el driver");
           return false;
        } catch (SQLException e) {
           System.out.println("La base de datos no se encuentra disponible");
           return false;
        }
        try {
            pstmt = conexion.prepareCall(DELETE_PRODUCT);                        
            pstmt.setInt(1,key);            
            pstmt.execute();
        }catch (SQLException e) {
            System.out.println("Sdentencia no valida");
           e.printStackTrace();
           resp=false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
                System.out.println("se elimino con exito: "+ key);
                return resp;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public List<Producto> readAll() {
        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Producto p2 = null;
        CallableStatement pstmt=null;  
        try {
            conectar();
        }catch (ClassNotFoundException e) {
            System.out.println("No se ha localizado el driver");
        } catch (SQLException e) {
            System.out.println("La base de datos no se encuentra disponible");
        }
        try{
            pstmt = conexion.prepareCall(READ_ALL_PRODUCTS);                
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);            
            pstmt.execute();
            rs = (ResultSet)pstmt.getObject(1); 
            while (rs.next()) {
                p2 = new Producto();
                p2.setCodigo(rs.getInt("codigo"));
                p2.setNombre(rs.getString("nombre"));
                p2.setPrecio(rs.getFloat("precio"));
                p2.setImportado(((Integer.parseInt(rs.getString("importado"))==0)?false:true));
                p2.setTipo(rs.getString("tipo"));
                p2.setPorcentaje(Float.parseFloat(rs.getString("porcentaje")));
                p2.setImpuesto(Float.parseFloat(rs.getString("impuesto")));
                p2.setPrecioFinal(Float.parseFloat(rs.getString("preciofinal")));
                coleccion.add(p2);
            }
        }
        catch (SQLException e) {
            System.out.println("Sentencia no valida");
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                System.out.println("Estatutos invalidos o nulos");
               e.printStackTrace();
            }
        }
        return coleccion;
    }
    */
}
