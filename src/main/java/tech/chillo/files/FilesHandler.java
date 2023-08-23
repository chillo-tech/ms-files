package tech.chillo.files;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;

@Slf4j
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
        log.info("filePath {}", filePath);
        if (filePath != null && !filePath.equals("null") && Strings.isNotEmpty(filePath)) {
            final String fullPath = String.format("%s/%s", this.basePath, filePath);
            final Path folder = Paths.get(fullPath).getParent();
            log.info("folder {}", folder);
            Files.createDirectories(folder);
            log.info("folder is created {}", Files.exists(folder));

            final String fileAsString = String.valueOf(params.get("file"));
            final byte[] decodedFile = Base64.getDecoder().decode(fileAsString);

            try (final OutputStream stream = new FileOutputStream(fullPath)) {
                stream.write(decodedFile);
            }
        }

    }
}
