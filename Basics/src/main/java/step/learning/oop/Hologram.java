package step.learning.oop;

public class Hologram
        extends Literature {

    @Override
    public Hologram setTitle( String title ) {
        return (Hologram) super.setTitle( title ) ;
    }
}
