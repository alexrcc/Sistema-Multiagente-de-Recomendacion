package model;

import java.sql.*;

public class Dao {
    
    public Connection conexion;
    //public final static String userDb = "root";
    //public final static String passDb = "";
//    public final static String userDb = "admin_alex@smawebserver";
//    public final static String passDb = "@l3xc0nd0y1994";
    public final static String userDb = "smaweb";
    public final static String passDb = "alexcondoy1994";
    //Conectar a la Base de datos
    public void conectar() throws SQLException,ClassNotFoundException{
         Class.forName("com.mysql.jdbc.Driver");
         //conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/sma_web",userDb, passDb);
         //conexion=DriverManager.getConnection("jdbc:mysql://smawebserver.mysql.database.azure.com:3306/sma_web",userDb, passDb);
         conexion=DriverManager.getConnection("jdbc:mysql://104.210.144.119:3306/sma_web",userDb, passDb);
    }
    //Desconectar a la Base de datos
    public void desconectar() throws SQLException, ClassNotFoundException{
        conexion.close();
    }
    
    //Metodo para consultar si un email y contraseñan pertenecen a una cuenta registrada
    public boolean isAcountExists(String email, String password) throws SQLException{
        String sql = "SELECT * FROM usuarios WHERE email='"+email+"' AND password='"+password+"'";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        return rs.next();
    }
    
    //Metodo para consultar si el email recibido ya esta registrado
    public boolean isEmailRegistered(String email) throws SQLException{
        String sql = "SELECT * FROM usuarios WHERE email='"+email+"'";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
 
        return rs.next();
    }
    
    //Metodo para registrar una cuenta
    public void registerUser(String email, String password, String name,int tipo,int inteligente) throws SQLException{
        String sql = "INSERT INTO `usuarios`(`email`,`password`,`name`,`registro_google`,`inteligente`) VALUES ('"+email+"','"+password+"','"+name+"','"+tipo+"','"+inteligente+"')";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.executeUpdate();
    }
    
    public String getNombre(String email) throws SQLException{
        String nombre = null;
        String sql = "SELECT name FROM usuarios WHERE email='"+email+"'";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();  
        if(rs.next()){
            return rs.getString("name");
        }
        return null;
    }
    
     public int getTipo(String email) throws SQLException{
        String sql = "SELECT registro_google FROM usuarios WHERE email='"+email+"'";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return rs.getInt("registro_google");
        }
        return -1;
    }
    
     public int getPefil(String email) throws SQLException{
        String sql = "SELECT inteligente FROM usuarios WHERE email='"+email+"'";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return rs.getInt("inteligente");
        }
        return -1;
    }
     public boolean setPerfilInteligente(String email,int p) throws SQLException{
        String sql = "UPDATE usuarios SET inteligente='"+p+"' WHERE email='"+email+"'";
        PreparedStatement ps = conexion.prepareStatement(sql);
        int rs = ps.executeUpdate();
        if(rs==1){
            return true;
        }
        return false;
    }
     
    public boolean isAdminExists(String user, String password) throws SQLException{
        String sql = "SELECT * FROM admin WHERE user='"+user+"' AND password='"+password+"'";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}
