package tech.chillo.files;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FilesRepository extends MongoRepository<FileParams, String> {// Il VA STOCKER UN ELEMENT DE TYPE ADRESSE QUI A UNE CLE PRIMAIRE DE TYPE STRING


}

