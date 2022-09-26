
package modele;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert.AlertType;

public class Achat
{
    protected int id = 0;
    protected int numeroClient = 0;
    protected int numeroMateriel = 0;
    protected int quantite = 0;
    protected String dateAchat = "";

    public static void supprimer( final int id )
    {
        Statement connexion = HighTechMarket.connecter();
        final ResultSet resultatSELECT;
        String requeteSQL = "SELECT *, achat.num_materiel AS numeroMateriel FROM achat JOIN materiel ON achat.num_materiel=materiel.num_materiel WHERE achat.id='" + id + "'";
        final int stockFinal;
        try
        {
            resultatSELECT = connexion.executeQuery( requeteSQL );
            resultatSELECT.next();
            stockFinal = resultatSELECT.getInt( "stock" ) + resultatSELECT.getInt( "qte" );
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
            return;
        }
        requeteSQL = "UPDATE materiel SET stock='" + stockFinal + "' WHERE num_materiel IN (SELECT num_materiel FROM achat WHERE id='" + id + "')";
        try
        {
            connexion.executeUpdate( requeteSQL );
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
            return;
        }
        requeteSQL = "DELETE FROM achat WHERE id='" + id + "'";
        try
        {
            connexion.executeUpdate( requeteSQL );
            Message.afficher( AlertType.WARNING, "REMARQUE" , "Suppression effectuée" );
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
    }
    
    public static void modifierQuantite( final int id , final String quantite )
    {
        Statement connexion = HighTechMarket.connecter();
        final String requeteSQL = "UPDATE achat SET qte='" + quantite + "' WHERE id='" + id + "'";
        try
        {
            if( Integer.parseInt( quantite ) > 0 )
            {
                final int qte = Integer.parseInt( quantite );
                ResultSet resultatSELECT = connexion.executeQuery( "SELECT *, achat.num_materiel AS numMat FROM achat JOIN materiel ON achat.num_materiel=materiel.num_materiel WHERE id='" + id + "'" );
                resultatSELECT.next();
                final int ancienQte = resultatSELECT.getInt( "qte" );
                final int numMat = resultatSELECT.getInt( "numMat" );
                final int stockVirtuel = resultatSELECT.getInt( "stock" ) + ancienQte;
                
                if( qte >= stockVirtuel )
                    Message.afficher( AlertType.ERROR, "ATTENTION !!" , "Echec de la modification, stock insuffisant !!! " );
                else
                {
                   final int stockFinal = stockVirtuel - qte;
                   connexion.executeUpdate( "UPDATE materiel SET stock='" + stockFinal + "' WHERE num_materiel='" + numMat + "'" );
                   connexion.executeUpdate( requeteSQL );
                   Message.afficher( AlertType.INFORMATION, "REMARQUE" , "Modification reussie :)" );
                }
            }
            else
                Message.afficher( AlertType.INFORMATION, "REMARQUE" , "Veuillez entrer un nombre valide" );
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
        catch( Exception e )
        {
            Message.afficher( AlertType.INFORMATION, "REMARQUE" , "Veuillez entrer un nombre valide" );
        }
    }
    
    public static void modifierDateAchat( final int id , final String dateAchat )
    {
        Statement connexion = HighTechMarket.connecter();
        final String requeteSQL = "UPDATE achat SET date_achat='" + dateAchat + "' WHERE id='" + id + "'";
        try
        {
            connexion.executeUpdate( requeteSQL );
            Message.afficher( AlertType.INFORMATION, "REMARQUE" , "Modification reussie :)" );
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
    }
    
    public void ajouter( final String numeroClient , final String numeroMateriel , final String quantite , final String dateAchat )
    {
        Message resultat = new Message();
        Statement connexion = HighTechMarket.connecter();
        
        try
        {
            Integer.parseInt( numeroClient );
            Integer.parseInt( numeroMateriel );
            Integer.parseInt( quantite );
        }
        catch( Exception e )
        {
            Message.afficher( AlertType.ERROR, "REMARQUE" , "Veuillez remplir correctement les champs du formulaire" );
            return;
        }
        
        if( numeroClient.equals( "" ) || numeroMateriel.equals( "" ) || dateAchat.equals( "" ) || Integer.parseInt( quantite ) <= 0 )
        {
            Message.afficher( AlertType.ERROR , "REMARQUE" , "Veuillez remplir correctement les champs du formulaire" );
            return;
        }
        String requeteSQL = "SELECT * FROM client WHERE num_client='" + numeroClient + "'";
        try
        {
            ResultSet resultatSELECT = connexion.executeQuery( requeteSQL );
            resultatSELECT.next();
            if( resultatSELECT.getRow() <= 0 )
            {
               Message.afficher( AlertType.WARNING , "REMARQUE !!" , "Désolé ce client n'existe pas. Veuillez réessayer" );
               return;
            }
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
            return;
        }
        
        final int stock;
        requeteSQL = "SELECT * FROM materiel WHERE num_materiel='" + numeroMateriel + "'";
        try
        {
            ResultSet resultatSELECT = connexion.executeQuery( requeteSQL );
            resultatSELECT.next();
            if( resultatSELECT.getRow() <= 0 )
            {
               Message.afficher( AlertType.WARNING , "REMARQUE !!!" , "Désolé, il n'y a pas de matériel qui possède ce numero. Veuillez réessayer" );
               return ;
            }
            stock = resultatSELECT.getInt( "stock" );
            if( Integer.parseInt( quantite ) > stock )
            {
               Message.afficher( AlertType.WARNING , "REMARQUE !!!" , "Désolé, Le stock de ce matériel (" + stock + ") ne suffit pas pour cette quantité. Veuillez réessayer" );
               return; 
            }
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
            return;
        }
        
        final int stockFinal = stock - Integer.parseInt( quantite );
        requeteSQL = "UPDATE materiel SET stock='" + stockFinal + "' WHERE num_materiel='" + numeroMateriel + "'";
        try
        {
            connexion.executeUpdate( requeteSQL );
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
            return;
        }
        
        requeteSQL = "INSERT INTO achat ( num_client , num_materiel , qte , date_achat ) VALUES ( '" + numeroClient + "' , '" + numeroMateriel + "' , '" + quantite + "' , '" + dateAchat + "' )";
        try
        {
            connexion.executeUpdate( requeteSQL );
            Message.afficher( AlertType.INFORMATION , "REMARQUE" , "L'achat a bien été effectué" );
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
    }
    
    public int getId()
    {
        return id;
    }
    
    public StringProperty colonneId()
    {
        return new SimpleStringProperty( String.valueOf( id ) );
    }
     
    public int getNumeroClient()
    {
        return numeroClient;
    }
    
    public StringProperty colonneNumeroClient()
    {
        String resultat = ( numeroClient < 10 ) ? "0" + String.valueOf( numeroClient ) : String.valueOf( numeroClient );
        resultat = "CL" + resultat;
        return new SimpleStringProperty( resultat );
    }
    
    public int getNumeroMateriel()
    {
        return numeroMateriel;
    }
    
    public StringProperty colonneNumeroMateriel()
    {
        String resultat = ( numeroMateriel < 10 ) ? "0" + String.valueOf( numeroMateriel ) : String.valueOf( numeroMateriel );
        resultat = "M" + resultat;
        return new SimpleStringProperty( resultat );
    }
    
    public int getQuantite()
    {
        return quantite;
    }
    
    public StringProperty colonneQuantite()
    {
        return new SimpleStringProperty( String.valueOf( quantite ) );
    }
    
    public String getDateAchat()
    {
        return dateAchat;
    }
    
    public SimpleStringProperty colonneDateAchat()
    {
        return new SimpleStringProperty( dateAchat );
    }
    
    public void setId( final int id )
    {
        this.id = id;
    }
    
    public void setNumeroClient( final int numeroClient )
    {
        this.numeroClient = numeroClient;
    }
    
    public void setNumeroMateriel( final int numeroMateriel )
    {
        this.numeroMateriel = numeroMateriel;
    }
    
    public void setQuantite( final int quantite )
    {
        this.quantite = quantite;
    }
    
    public void setDateAchat( final String dateAchat )
    {
        this.dateAchat = dateAchat;
    }
}
