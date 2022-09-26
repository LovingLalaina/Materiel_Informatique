
package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert.AlertType;

public class ChiffreAffaire extends Client
{
    private int chiffreAffaire = 0;
    
    public int getChiffreAffaire()
    {
        return chiffreAffaire;
    }
    
    public static void afficherHistogramme( final String annee , BarChart Histogramme , CategoryAxis AxeClient , NumberAxis AxeChiffreAffaire )
    {
        Statement connexion = HighTechMarket.connecter();
        final String requeteSQL = "SELECT * , sum(pu*qte) AS chiffre_affaire FROM client LEFT OUTER JOIN achat ON client.num_client=achat.num_client LEFT OUTER JOIN materiel ON achat.num_materiel=materiel.num_materiel WHERE year(date_achat)='" + annee + "' GROUP BY client.num_client";
        ResultSet resultatSELECT;
        try
        {
            resultatSELECT = connexion.executeQuery( requeteSQL );
            XYChart.Series ModeleHistogramme = new XYChart.Series<>();
            boolean aucunResultat = true;
            while( resultatSELECT.next() )
            {
                ChiffreAffaire monClient = new ChiffreAffaire();
                monClient.setNumero( resultatSELECT.getInt( "num_client" ) );
                monClient.setNom( resultatSELECT.getString( "nom" ) );
                monClient.setChiffreAffaire( resultatSELECT.getInt( "chiffre_affaire" ) );
                ModeleHistogramme.getData().add( new XYChart.Data( monClient.colonneNumero().getValue() , monClient.getChiffreAffaire() ) );
                aucunResultat = false;
            }
            if( aucunResultat )
                Message.afficher( AlertType.INFORMATION, "REMARQUE" , "Aucun résultat ne correspond à la recherche :/" );
            else
            {
                Histogramme.getData().setAll( ModeleHistogramme );
            }
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
    }
    
    public StringProperty colonneChiffreAffaire()
    {
        return new SimpleStringProperty( String.valueOf( chiffreAffaire ) + " Fmg" );
    }
    
    public void setChiffreAffaire( final int chiffreAffaire )
    {
        this.chiffreAffaire = chiffreAffaire;
    }
}
