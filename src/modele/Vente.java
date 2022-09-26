
package modele;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Vente extends Materiel
{
    private int quantite;
    private int numeroClient;
    private String dateDebut;
    private String dateFin;
    private int annee;
    private int mois;
    
    
    // ACCESSEURS DU BEAN VENTE
    public int getQuantite()
    {
        return quantite;
    }
    
    public StringProperty colonneQuantite()
    {
        return new SimpleStringProperty( String.valueOf( quantite ) );
    }
    
    public int getNumeroClient()
    {
        return numeroClient;
    }
    
    public String getDateDebut()
    {
        return dateDebut;
    }
    
    public String getDateFin()
    {
        return dateFin;
    }
    
    public int getAnnee()
    {
        return annee;
    }
    
    public int getMois()
    {
        return mois;
    }
    
    public void setQuantite( final int quantite )
    {
        this.quantite = quantite;
    }
    
    public void setNumeroClient( final int numeroClient )
    {
        this.numeroClient = numeroClient;
    }
    
    public void setDateDebut( final String dateDebut )
    {
        this.dateDebut = dateDebut;
    }
    
    public void setDateFin( final String dateFin )
    {
        this.dateFin =  dateFin;
    }
    
    public void setAnnee( final int annee )
    {
        this.annee =  annee;
    }
    
    public void setMois( final int mois )
    {
        this.mois =  mois;
    }
}
