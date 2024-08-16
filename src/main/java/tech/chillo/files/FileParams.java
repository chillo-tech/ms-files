package tech.chillo.files;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("files")
public class FileParams {
    @Id
    private String id;
    private String path;
    private String message;
    private String name;
    private String file;

    public FileParams() {
    }

    public FileParams(String id, String path, String message, String name, String file) {
        this.id = id;
        this.path = path;
        this.message = message;
        this.name = name;
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
