package com.abavilla.fpi.repo;

import com.abavilla.fpi.entity.IItem;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;

public interface IMongoRepo<I extends IItem> extends ReactivePanacheMongoRepository<I> {

}
