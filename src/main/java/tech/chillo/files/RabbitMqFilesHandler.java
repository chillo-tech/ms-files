package tech.chillo.files;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Component
public class RabbitMqFilesHandler {
    final String basePath;

    public RabbitMqFilesHandler(@Value("${application.files.base-path}") final String basePath) {
        this.basePath = basePath;
    }

    @RabbitListener(

            queues = {"${spring.rabbitmq.template.queue}"},
            returnExceptions = "rabbitErrorHandler",
            errorHandler = "rabbitErrorHandler"
    )
    public void handleMessage(final Map<String, Object> params) throws IOException {
        final String fileAsString = String.valueOf(params.get("file"));
        final String path = String.valueOf(params.get("file"));
        final String fullPath = String.format("%s/%s/%s", this.basePath, path, fileAsString);
        final FileOutputStream outputStream = new FileOutputStream(fullPath);
        final byte[] decodedFile = Base64.getDecoder().decode(fileAsString);
        outputStream.write(decodedFile);

        outputStream.close();
    }

}
