
package modele;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Message
{
    /*public static String header = "";
    public static String message = "";
    public static AlertType type = AlertType.INFORMATION;*/
    
    public static boolean afficher( final AlertType type , final String header , final String message )
    {
        Alert monAlert = new Alert( type );
        monAlert.setTitle( "HighTech Market" );
        monAlert.setHeaderText( header );
        monAlert.setContentText( message );
        Optional<ButtonType> reponse = monAlert.showAndWait();
        
        if( reponse.isPresent() )   return reponse.get() == ButtonType.OK;
        return false;
    }
}
