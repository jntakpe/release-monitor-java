package com.github.jntakpe.releasemonitorjava.repository;

import com.github.jntakpe.releasemonitorjava.model.Application;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ApplicationRepository extends ReactiveMongoRepository<Application, ObjectId> {

}
