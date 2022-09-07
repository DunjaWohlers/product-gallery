package de.neuefische.cgnjava222.productgallery.exception;

public class FileNotDeletedException extends RuntimeException {
    public FileNotDeletedException(String picturePublicID) {
        super("Löschen der Datei: " + picturePublicID + " fehlgeschlagen ");
    }
}
