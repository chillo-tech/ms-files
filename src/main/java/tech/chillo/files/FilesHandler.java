package tech.chillo.files;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/v1/files")
public class FilesHandler {
    final String basePath;

    public FilesHandler(@Value("${application.files.base-path}") final String basePath) {
        this.basePath = basePath;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void handleMessage(@RequestBody final Map<String, Object> params) throws IOException {
        final String filePath = String.valueOf(params.get("path"));
        log.info("File path {}", filePath);
        if (filePath != null && !filePath.equals("null") && Strings.isNotEmpty(filePath)) {
            final String fullPath = String.format("%s/%s", this.basePath, filePath);
            log.info("Full file path {}", fullPath);
            final Path folder = Paths.get(fullPath).getParent();
            Files.createDirectories(folder);

            final String fileAsString = String.valueOf(params.get("file"));
            final byte[] decodedFile = Base64.getDecoder().decode(fileAsString);
            final File fullPathAsFile = new File(fullPath);
            if (Files.exists(Paths.get(fullPath))) {
                FileUtils.delete(fullPathAsFile);
            }
            
            log.info("Write  file path {}", fullPath);
            FileUtils.writeByteArrayToFile(fullPathAsFile, decodedFile);
        }
    }
}
