
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start( Stage FenetrePrincipale ) throws Exception
    {
        Parent root = FXMLLoader.load( getClass().getResource( "MainXML.fxml") );
        Scene scene = new Scene( root );
        scene.getStylesheets().add( getClass().getResource( "MainXML.css" ).toExternalForm() );
        FenetrePrincipale.setTitle( "HighTech Market" );
        FenetrePrincipale.setScene( scene );
        FenetrePrincipale.setResizable( false );
        FenetrePrincipale.show();
        System.out.println( "Le Logiciel HighTech Market demarre" );
    }
    
    public static void main( String args[] )
    {
        launch( args );
    }
}
