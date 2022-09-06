package de.neuefische.cgnjava222.productgallery.exception;

public class FileNotDeletedException extends RuntimeException {
    public FileNotDeletedException(String picturePublicID, String productId) {
        super("Löschen der Datei: " + picturePublicID + "fehlgeschlagen (aus dem Produkt: " + productId + ")");
    }
}
