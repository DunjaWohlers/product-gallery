package de.neuefische.cgnjava222.productgallery.exception;

public class FileuploadException extends RuntimeException {
    public FileuploadException(String originalFileName) {
        super("File Upload der Datei: " + originalFileName + "wurde nicht durchgeführt.");
    }
}
