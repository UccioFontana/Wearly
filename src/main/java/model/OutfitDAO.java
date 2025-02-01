package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OutfitDAO {

   public boolean doSave(Outfit out){

       //creazione outfit
       try (Connection con = ConPool.getConnection()){
           PreparedStatement ps = con.prepareStatement("INSERT INTO Outfit (Nome,Descrizione) VALUES(?,?)");
           ps.setString(1,out.getNome());
           ps.setString(2,out.getDescrizione());

           int test = ps.executeUpdate();
           if (test != 1) {
               return false;
           }

           ConPool.closeConnection(con);

       }catch (SQLException e) {
           if (e.getErrorCode() == 1062) { // Codice di errore per duplicati in MySQL
               return false;
           }
           throw new RuntimeException(e);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

       //associazione idcapi ad idoutfit
       try (Connection con = ConPool.getConnection()){

           for(CapoAbbigliamento c: out.getListaCapi()){
               int idC= c.getId();

               PreparedStatement ps = con.prepareStatement("INSERT INTO DettagliOutfit (IdOutfit,IdCapo) VALUES(?,?)");
               ps.setInt(1,out.getId());
               ps.setInt(2,idC);

               int test = ps.executeUpdate();
               if (test != 1) {
                   return false;
               }
           }

           ConPool.closeConnection(con);
           return true;



       }catch (SQLException e) {
           if (e.getErrorCode() == 1062) { // Codice di errore per duplicati in MySQL
               return false;
           }
           throw new RuntimeException(e);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

   }

   public List<Outfit> getOutfits(){
       List<Outfit> list = new ArrayList<>();

       try (Connection con = ConPool.getConnection()){

           PreparedStatement ps = con.prepareStatement("SELECT * FROM Outfit ");

           ResultSet rs = ps.executeQuery();
           while (rs.next()){
               int id = rs.getInt("Id");
               String nome= rs.getString("Nome");
               String descrizione= rs.getString("Descrizione");
               Outfit out = new Outfit(nome,descrizione);
               out.setId(id);
               CapoAbbigliamentoDAO C = new CapoAbbigliamentoDAO();
               out.setListaCapi(C.getCapiByIdOufit(out.getId()));

               list.add(out);
           }

           ConPool.closeConnection(con);
           return list;

       }catch (SQLException e) {
           throw new RuntimeException(e);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }



   }
   //getCapiByIdOutfit


}
