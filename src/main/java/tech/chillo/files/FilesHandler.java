package tech.chillo.files;

import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;

@Component
public class FilesHandler {
    final String basePath;

    public FilesHandler(@Value("${application.files.base-path}") final String basePath) {
        this.basePath = basePath;
    }

    @RabbitListener(
            queues = {"${spring.rabbitmq.template.queue}"},
            returnExceptions = "rabbitErrorHandler",
            errorHandler = "rabbitErrorHandler"
    )
    public void handleMessage(final Map<String, Object> params) throws IOException {
        final String filePath = String.valueOf(params.get("path"));
        if (filePath != null && !filePath.equals("null") && Strings.isNotEmpty(filePath)) {

            final String folder = filePath.substring(0, filePath.lastIndexOf("/"));
            Files.createDirectories(Paths.get(folder));
            final String fullPath = String.format("%s/%s", this.basePath, filePath);

            final String fileAsString = String.valueOf(params.get("file"));
            final byte[] decodedFile = Base64.getDecoder().decode(fileAsString);
,
            try (final OutputStream stream = new FileOutputStream(fullPath)) {
                stream.write(decodedFile);
            }
        }

    }
}
